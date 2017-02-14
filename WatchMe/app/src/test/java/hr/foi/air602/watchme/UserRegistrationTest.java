package hr.foi.air602.watchme;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Mateo on 14.2.2017..
 */
public class UserRegistrationTest {

    @Test
    public void isValidEmail() throws Exception {
        Boolean output;
        UserRegistration registracija = new UserRegistration();
        String ulazTest1 = "123456789";
        String ulazTest2 = "123456789@test.test";
        String ulazTest3 = "test123@test";
        String ulazTest4 = "test.test";
        String ulazTest5 = "test.test@test";
        String ulazTest6 = "test";
        String ulazTest7 = "test123@test.test";
        String ulazTest8 = "test123@test_test";
        String ulazTest9 = "test123@test.test";
        String ulazTest10 = "TEST123@TEST.TEST";
        output = registracija.isValidEmail(ulazTest1);
        assertEquals(false, output);
        output = registracija.isValidEmail(ulazTest2);
        assertEquals(true, output);
        output = registracija.isValidEmail(ulazTest3);
        assertEquals(false, output);
        output = registracija.isValidEmail(ulazTest4);
        assertEquals(false, output);
        output = registracija.isValidEmail(ulazTest5);
        assertEquals(false, output);
        output = registracija.isValidEmail(ulazTest6);
        assertEquals(false, output);
        output = registracija.isValidEmail(ulazTest7);
        assertEquals(true, output);
        output = registracija.isValidEmail(ulazTest8);
        assertEquals(false, output);
        output = registracija.isValidEmail(ulazTest9);
        assertEquals(true, output);
        output = registracija.isValidEmail(ulazTest10);
        assertEquals(true, output);
    }

    @Test
    public void isValidPassword() throws Exception {
        String input[] = {"123","pass1","password", "PaSSW1", "password123", " "};
        Boolean output;
        Boolean expecteds[] = {false, false, true, false, true, false};
        UserRegistration registracija = new UserRegistration();
        for(int i = 0; i < input.length; i++){
            output = registracija.isValidPassword(input[i]);
            assertEquals(expecteds[i], output);
        }

    }

}