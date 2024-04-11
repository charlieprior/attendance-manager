package edu.duke.ece651.team2.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class PasswordDAOTest {

    DAOFactory factory = new DAOFactory();
    PasswordDAO passwordDAO = new PasswordDAO(factory);


    // @Test
    // public void testGet(){
    //     assertNull(passwordDAO.get(2));
    //     assertEquals("123456",passwordDAO.get(1).getPassword());
    // }
}
