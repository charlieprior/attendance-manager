package edu.duke.ece651.team2.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.internal.InputStreams;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.duke.ece651.team2.client.controller.ClientSideView;
import edu.duke.ece651.team2.client.controller.GeneralController;
import edu.duke.ece651.team2.shared.Password;


public class GeneralControllerTest {

    @Test
    public void testSendObjectInt() throws IOException {
        OutputStream mockOut = mock(OutputStream.class);
        Socket mockSocket = mock(Socket.class);
        when(mockSocket.getOutputStream()).thenReturn(mockOut);
        ObjectOutputStream mockObjOut = new ObjectOutputStream(mockOut);

        GeneralController controller = new GeneralController();
        controller.setOut(mockObjOut);
        List<String> test = new ArrayList<>();
        test.add("a");

        // Call the method
        controller.sendObject(123);
        controller.sendObject(test);
    }

    @Test
    public void testDisconnectFromServer() throws IOException {
        // Mock dependencies
        ObjectOutputStream mockOut = mock(ObjectOutputStream.class);
        ObjectInputStream mockIn = mock(ObjectInputStream.class);
        Socket mockSocket = mock(Socket.class);

        // Create GeneralController instance
        GeneralController controller = new GeneralController();
        controller.setOut(mockOut);
        controller.setIn(mockIn);
        controller.setSocket(mockSocket);

        // Set up the socket as open
        when(mockSocket.isClosed()).thenReturn(false);

        // Call the method
        controller.disconnectFromServer();

        // Verify interactions
        verify(mockOut).close();
        verify(mockIn).close();
        verify(mockSocket).close();
        assertFalse(controller.getConnected());
    }



    @Test
    public void testLogin() throws IOException, ClassNotFoundException {
        String[] credentials = {"123", "password"};

        // Create in-memory streams
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
        objectOutputStream.writeObject("1:someSerializedData"); 
        objectOutputStream.flush(); 
    
        byte[] serializedData = outStream.toByteArray(); 
        ByteArrayInputStream inStream = new ByteArrayInputStream(serializedData);
        ObjectInputStream objectInputStream = new ObjectInputStream(inStream);
    
        // Mock ObjectMapper
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.writeValueAsString(any(Password.class))).thenReturn("someSerializedPassword");
    
        // Create instance of GeneralController with in-memory streams and mock ObjectMapper
        GeneralController controller = new GeneralController();
        controller.setOut(objectOutputStream);
        controller.setIn(objectInputStream);
        controller.setMapper(objectMapper);
    
        // Call the method under test
        int userType = controller.login(credentials);
    
        // Assertion
        assertEquals(1, userType); 

        // Create in-memory streams
        outStream = new ByteArrayOutputStream();
        objectOutputStream = new ObjectOutputStream(outStream);
        objectOutputStream.writeObject("2:someSerializedData"); 
        objectOutputStream.flush(); 
    
        serializedData = outStream.toByteArray(); 
        inStream = new ByteArrayInputStream(serializedData);
        objectInputStream = new ObjectInputStream(inStream);
    
        controller.setOut(objectOutputStream);
        controller.setIn(objectInputStream);

        // Call the method under test
        userType = controller.login(credentials);
    
        // Assertion
        assertEquals(2, userType); 

        // Create in-memory streams
        outStream = new ByteArrayOutputStream();
        objectOutputStream = new ObjectOutputStream(outStream);
        objectOutputStream.writeObject("3:someSerializedData"); 
        objectOutputStream.flush(); 
    
        serializedData = outStream.toByteArray(); 
        inStream = new ByteArrayInputStream(serializedData);
        objectInputStream = new ObjectInputStream(inStream);
    
        controller.setOut(objectOutputStream);
        controller.setIn(objectInputStream);

        // Call the method under test
        userType = controller.login(credentials);
    
        // Assertion
        assertEquals(0, userType); 

    }
}


