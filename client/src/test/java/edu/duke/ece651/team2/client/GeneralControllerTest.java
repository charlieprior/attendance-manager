package edu.duke.ece651.team2.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.team2.client.controller.GeneralController;

public class GeneralControllerTest {

    @Test
    public void testSendObjectInt() throws IOException {
        // Mock dependencies
        // ObjectOutputStream mockOut = mock(ObjectOutputStream.class);
        OutputStream mockOut = mock(OutputStream.class);
        Socket mockSocket = mock(Socket.class);
        when(mockSocket.getOutputStream()).thenReturn(mockOut);
        ObjectOutputStream mockObjOut = new ObjectOutputStream(mockOut);

        // Create GeneralController instance with mocked dependencies
        GeneralController controller = new GeneralController();
        // controller.setOut(new ObjectOutputStream(mockSocket.getOutputStream()));
        controller.setOut(mockObjOut);

        // Call the method
        controller.sendObject(123);
    }

}
