package com.parking.services;

import com.parking.exceptions.InvalidException;
import com.parking.modules.*;
import com.parking.repositories.GateRepository;
import com.parking.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Ticket Service Tests")
class TicketServiceTest {

    @Mock
    private GateRepository gateRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ticketService = new TicketService(gateRepository, vehicleRepository);
    }

    @Test
    @DisplayName("Should generate ticket successfully for new vehicle")
    void testGenerateTicketForNewVehicle() throws InvalidException {
        Long gateId = 1L;
        String vehicleNumber = "KA-01-AB-1234";
        VehicleType vehicleType = VehicleType.CAR;
        String ownerName = "John Doe";

        Gates gate = new Gates();
        gate.setId(gateId);
        Operator operator = new Operator();
        operator.setName("Operator1");
        gate.setOperator(operator);

        when(gateRepository.findById(gateId)).thenReturn(Optional.of(gate));
        when(vehicleRepository.findByNumber(vehicleNumber)).thenReturn(Optional.empty());
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(i -> i.getArguments()[0]);

        Ticket ticket = ticketService.generateTicket(gateId, vehicleNumber, vehicleType, ownerName);

        assertNotNull(ticket, "Ticket should not be null");
        assertNotNull(ticket.getEntryTime(), "Entry time should be set");
        assertNotNull(ticket.getVehicle(), "Vehicle should be set");
        assertEquals(vehicleNumber, ticket.getVehicle().getVehicleNumber(), "Vehicle number should match");
        assertEquals(gate, ticket.getGeneratedAt(), "Gate should match");
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    @DisplayName("Should generate ticket for existing vehicle")
    void testGenerateTicketForExistingVehicle() throws InvalidException {
        Long gateId = 1L;
        String vehicleNumber = "KA-01-AB-5678";
        VehicleType vehicleType = VehicleType.BIKE;
        String ownerName = "Jane Doe";

        Gates gate = new Gates();
        gate.setId(gateId);
        Operator operator = new Operator();
        operator.setName("Operator2");
        gate.setOperator(operator);

        Vehicle existingVehicle = new Vehicle();
        existingVehicle.setVehicleNumber(vehicleNumber);
        existingVehicle.setVehicleType(vehicleType);
        existingVehicle.setOwnerName(ownerName);

        when(gateRepository.findById(gateId)).thenReturn(Optional.of(gate));
        when(vehicleRepository.findByNumber(vehicleNumber)).thenReturn(Optional.of(existingVehicle));

        Ticket ticket = ticketService.generateTicket(gateId, vehicleNumber, vehicleType, ownerName);

        assertNotNull(ticket, "Ticket should not be null");
        assertEquals(existingVehicle, ticket.getVehicle(), "Should use existing vehicle");
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    @DisplayName("Should throw InvalidException for invalid gate ID")
    void testGenerateTicketWithInvalidGate() {
        Long invalidGateId = 999L;
        String vehicleNumber = "KA-01-AB-9999";
        VehicleType vehicleType = VehicleType.CAR;
        String ownerName = "Test User";

        when(gateRepository.findById(invalidGateId)).thenReturn(Optional.empty());

        InvalidException exception = assertThrows(
            InvalidException.class,
            () -> ticketService.generateTicket(invalidGateId, vehicleNumber, vehicleType, ownerName),
            "Should throw InvalidException for invalid gate"
        );

        assertEquals("Invalid Gate Id", exception.getMessage());
        verify(vehicleRepository, never()).findByNumber(anyString());
    }

    @Test
    @DisplayName("Should set correct gate and operator information")
    void testTicketGateAndOperatorInformation() throws InvalidException {
        Long gateId = 2L;
        String vehicleNumber = "MH-02-CD-4567";
        VehicleType vehicleType = VehicleType.TRUCK;
        String ownerName = "Bob Smith";

        Gates gate = new Gates();
        gate.setId(gateId);
        gate.setGateNumber(5);
        
        Operator operator = new Operator();
        operator.setId(10L);
        operator.setName("Operator3");
        gate.setOperator(operator);

        when(gateRepository.findById(gateId)).thenReturn(Optional.of(gate));
        when(vehicleRepository.findByNumber(vehicleNumber)).thenReturn(Optional.empty());
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(i -> i.getArguments()[0]);

        Ticket ticket = ticketService.generateTicket(gateId, vehicleNumber, vehicleType, ownerName);

        assertNotNull(ticket.getGeneratedAt(), "Generated at gate should be set");
        assertNotNull(ticket.getGeneratedBy(), "Generated by operator should be set");
        assertEquals(gate, ticket.getGeneratedAt(), "Gate should match");
        assertEquals(operator, ticket.getGeneratedBy(), "Operator should match");
    }

    @Test
    @DisplayName("Should handle null vehicle number gracefully")
    void testGenerateTicketWithNullVehicleNumber() {
        Long gateId = 1L;
        String vehicleNumber = null;
        VehicleType vehicleType = VehicleType.CAR;
        String ownerName = "Test User";

        Gates gate = new Gates();
        gate.setId(gateId);
        when(gateRepository.findById(gateId)).thenReturn(Optional.of(gate));
        when(vehicleRepository.findByNumber(null)).thenReturn(Optional.empty());
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(i -> i.getArguments()[0]);

        assertDoesNotThrow(
            () -> ticketService.generateTicket(gateId, vehicleNumber, vehicleType, ownerName),
            "Should handle null vehicle number"
        );
    }
}

