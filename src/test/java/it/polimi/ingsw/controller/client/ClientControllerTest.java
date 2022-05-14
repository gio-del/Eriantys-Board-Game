package it.polimi.ingsw.controller.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientControllerTest {
    @Test
    void validIpTest() {
        assertTrue(ClientController.isValidIp("localhost"));
        assertFalse(ClientController.isValidIp("1232"));
        assertFalse(ClientController.isValidIp("255.255.255.255."));
        assertFalse(ClientController.isValidIp("255.255.255.256"));
        assertTrue(ClientController.isValidIp("127.0.0.1"));
    }

    @Test
    void validPortTest() {
        assertTrue(ClientController.isValidPort(1234));
        assertFalse(ClientController.isValidPort(-1));
        assertFalse(ClientController.isValidPort(65536));
        assertTrue(ClientController.isValidPort(65535));
    }
}
