package com.example.tp2christianlima;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.example.tp2christianlima.R;
import com.example.tp2christianlima.Activity_Inscription;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

@RunWith(RobolectricTestRunner.class)
public class Test3 {
    //Test3
    Activity_Inscription activity;
    TextInputEditText tiet_nomCompletInscription, tiet_courrielInscription, tiet_mdpInscription, tiet_mdpValidationInscription;
    Button btn_inscription;

    @Before
    public void SetUp() {
        activity = Robolectric.buildActivity(Activity_Inscription.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void Test3Suite() throws Exception{
        //Test 3 Si on ecrit rien dans les champ

        btn_inscription = activity.findViewById(R.id.btn_inscription);
        tiet_nomCompletInscription = activity.findViewById(R.id.tiet_nomCompletInscription);
        tiet_courrielInscription = activity.findViewById(R.id.tiet_courrielInscription);
        tiet_mdpInscription = activity.findViewById(R.id.tiet_mdpInscription);
        tiet_mdpValidationInscription = activity.findViewById(R.id.tiet_mdpValidationInscription);

        tiet_nomCompletInscription.setText("TestValidation");
        tiet_courrielInscription.setText("test@test.com");
        tiet_mdpInscription.setText("1234567890");
        tiet_mdpValidationInscription.setText("1234567890");

        btn_inscription.performClick();

        assertEquals(tiet_nomCompletInscription.getText().toString(), "TestValidation");

        /*assertNull(tiet_nomCompletInscription.getError());
        assertNull(tiet_courrielInscription.getError());
        assertNull(tiet_mdpInscription.getError());
        assertNull(tiet_mdpValidationInscription.getError());*/
    }

    /*@Test
    public void Test3Suite2() throws Exception{
        //Test 3 Si on ecrit dans les champ

        btn_inscription = activity.findViewById(R.id.btn_inscription);
        tiet_nomCompletInscription = activity.findViewById(R.id.tiet_nomCompletInscription);
        tiet_courrielInscription = activity.findViewById(R.id.tiet_courrielInscription);
        tiet_mdpInscription = activity.findViewById(R.id.tiet_mdpInscription);
        tiet_mdpValidationInscription = activity.findViewById(R.id.tiet_mdpValidationInscription);

        tiet_nomCompletInscription.setText("TestValidation");
        tiet_courrielInscription.setText("test@test.com");
        tiet_mdpInscription.setText("1234567890");
        tiet_mdpValidationInscription.setText("1234567890");

        btn_inscription.performClick();
        ShadowActivity shadowActivity = shadowOf(activity);
        Intent intentionConnexion = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(intentionConnexion);
        MatcherAssert.assertThat(shadowIntent.getIntentClass().getName(),
                equalTo(Test3.class.getName()));

        assertNotNull(tiet_nomCompletInscription.getText().toString());
        assertNotNull(tiet_courrielInscription.getText().toString());
        assertNotNull(tiet_mdpInscription.getText().toString());
        assertNotNull(tiet_mdpValidationInscription.getText().toString());
    }*/


}
