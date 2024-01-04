package com.example.tp2christianlima;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestUnitaire {
    @Test
    public void Test1() throws Exception {
        //Test 1
            user usager = new user("Christian", "Christian@hotmail.com", "1234567890");

            assertEquals("Christian", usager.getNomComplet());
            assertEquals("Christian@hotmail.com", usager.getCourriel());
            assertEquals("1234567890", usager.getMdp());
    }

    @Test
    public void Test2() throws Exception{
        //Test 2
        AuthentificationBidon authActivity = mock(AuthentificationBidon.class);
        when(authActivity.getUserConnectToken()).thenReturn("UserConnecter");

        String jeton = authActivity.getUserConnectToken();

        assertEquals("UserConnecter", jeton);
    }
}

