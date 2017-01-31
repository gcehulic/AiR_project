package hr.foi.air602.watchme;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegistrationLoginTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void registrationLoginTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btnReg), withText("Registracija"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextIme), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextIme), isDisplayed()));
        appCompatEditText2.perform(replaceText("mateo"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editTextIme), withText("mateo"), isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editTextPrezime), isDisplayed()));
        appCompatEditText4.perform(replaceText("cindric"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.editTextPrezime), withText("cindric"), isDisplayed()));
        appCompatEditText5.perform(pressImeActionButton());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.editTextEmail), isDisplayed()));
        appCompatEditText6.perform(replaceText("matcindri@foi.hr"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.editTextEmail), withText("matcindri@foi.hr"), isDisplayed()));
        appCompatEditText7.perform(pressImeActionButton());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.editTextKorIme), isDisplayed()));
        appCompatEditText8.perform(replaceText("matcindri"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.editTextKorIme), withText("matcindri"), isDisplayed()));
        appCompatEditText9.perform(pressImeActionButton());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.editTextLozinka), isDisplayed()));
        appCompatEditText10.perform(replaceText("1111111"), closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.editTextLozinka), withText("1111111"), isDisplayed()));
        appCompatEditText11.perform(pressImeActionButton());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnRegistrirajSe), withText("Registriraj se"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.kor_ime), isDisplayed()));
        appCompatEditText12.perform(click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.kor_ime), isDisplayed()));
        appCompatEditText13.perform(replaceText("matcindri"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.kor_ime), withText("matcindri"), isDisplayed()));
        appCompatEditText14.perform(pressImeActionButton());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.password), isDisplayed()));
        appCompatEditText15.perform(replaceText("1111111"), closeSoftKeyboard());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.password), withText("1111111"), isDisplayed()));
        appCompatEditText16.perform(pressImeActionButton());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btnLogin), withText("Prijava"), isDisplayed()));
        appCompatButton3.perform(click());

    }

}
