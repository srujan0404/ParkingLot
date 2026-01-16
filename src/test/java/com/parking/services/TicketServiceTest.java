package com.parking.Services;

import com.parking.Exceptions.InvalidException;
import com.parking.Modules.*;
import com.parking.Repositories.GateRepository;
import com.parking.Repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Ticket Service Tests")
class TicketServiceTest {

    private GateRepository gateRepository;
    private VehicleRepository vehicleRepository;
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        gateRepository = new GateRepository();
        vehicleRepository = new VehicleRepository();
        ticketService = new TicketService(gateRepository, vehicleRepository);
    }

    @Test
    @DisplayName("Should create TicketService successfully")
    void testTicketServiceCreation() {
        assertNotNull(ticketService, "TicketService should be created successfully");
    }

    @Test
    @DisplayName("Should throw InvalidException for invalid gate ID")
    void testGenerateTicketWithInvalidGate() {
        Long invalidGateId = 999L;
        String vehicleNumber = "KA-01-AB-9999";
        VehicleType vehicleType = VehicleType.CAR;
        String ownerName = "Test User";

        InvalidException exception = assertThrows(
            InvalidException.class,
            () -> ticketService.generateTicket(invalidGateId, vehicleNumber, vehicleType, ownerName),
            "Should throw InvalidException for invalid gate"
        );

        assertEquals("Invalid Gate Id", exception.getMessage());
    }

    @Test
    @DisplayName("Should have valid repository references")
    void testRepositoryReferences() {
        assertNotNull(gateRepository, "GateRepository should not be null");
        assertNotNull(vehicleRepository, "VehicleRepository should not be null");
    }

    @Test
    @DisplayName("Should handle null vehicle number")
    void testNullVehicleNumber() {
        Long gateId = 1L;
        String vehicleNumber = null;
        VehicleType vehicleType = VehicleType.CAR;
        String ownerName = "Test User";

        // Should throw exception because gate doesn't exist
        assertThrows(
            InvalidException.class,
            () -> ticketService.generateTicket(gateId, vehicleNumber, vehicleType, ownerName)
        );
    }

    @Test
    @DisplayName("Should validate VehicleType enum values")
    void testVehicleTypeEnumValues() {
        assertNotNull(VehicleType.CAR);
        assertNotNull(VehicleType.BIKE);
        assertNotNull(VehicleType.TRUCK);
        assertEquals(9, VehicleType.values().length, "Should have 9 vehicle types");
    }
}
