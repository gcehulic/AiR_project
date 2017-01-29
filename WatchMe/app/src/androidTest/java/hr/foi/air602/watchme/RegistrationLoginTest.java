package hr.foi.air602.watchme;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
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

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnRegistrirajSe), withText("Registriraj se"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextIme), isDisplayed()));
        appCompatEditText2.perform(replaceText("Tester"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editTextPrezime), isDisplayed()));
        appCompatEditText3.perform(replaceText("Tester1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editTextEmail), isDisplayed()));
        appCompatEditText4.perform(replaceText("tester.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.editTextKorIme), isDisplayed()));
        appCompatEditText5.perform(replaceText("tester1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.editTextLozinka), isDisplayed()));
        appCompatEditText6.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btnRegistrirajSe), withText("Registriraj se"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.editTextLozinka), withText("12345"), isDisplayed()));
        appCompatEditText7.perform(pressImeActionButton());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.editTextEmail), withText("tester.com"), isDisplayed()));
        appCompatEditText8.perform(replaceText("tester@test.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.editTextEmail), withText("tester@test.com"), isDisplayed()));
        appCompatEditText9.perform(pressImeActionButton());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.editTextKorIme), withText("tester1"), isDisplayed()));
        appCompatEditText10.perform(replaceText("tester1"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btnRegistrirajSe), withText("Registriraj se"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.editTextLozinka), withText("12345"), isDisplayed()));
        appCompatEditText11.perform(replaceText("1234567"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btnRegistrirajSe), withText("Registriraj se"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btnReg), withText("Registracija"), isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.editTextIme), isDisplayed()));
        appCompatEditText12.perform(click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.editTextIme), isDisplayed()));
        appCompatEditText13.perform(replaceText("Tester"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.editTextPrezime), isDisplayed()));
        appCompatEditText14.perform(replaceText("Tester2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.editTextEmail), isDisplayed()));
        appCompatEditText15.perform(replaceText("tester@test.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.editTextKorIme), isDisplayed()));
        appCompatEditText16.perform(replaceText("tester1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.editTextLozinka), isDisplayed()));
        appCompatEditText17.perform(replaceText("12345678"), closeSoftKeyboard());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.btnRegistrirajSe), withText("Registriraj se"), isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.editTextKorIme), withText("tester1"), isDisplayed()));
        appCompatEditText18.perform(replaceText("tester2"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btnRegistrirajSe), withText("Registriraj se"), isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.btnLogin), withText("Prijava"), isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.kor_ime), isDisplayed()));
        appCompatEditText19.perform(click());

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.kor_ime), isDisplayed()));
        appCompatEditText20.perform(replaceText("tester1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText21 = onView(
                allOf(withId(R.id.kor_ime), withText("tester1"), isDisplayed()));
        appCompatEditText21.perform(pressImeActionButton());

        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.password), isDisplayed()));
        appCompatEditText22.perform(pressImeActionButton());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btnLogin), withText("Prijava"), isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.password), isDisplayed()));
        appCompatEditText23.perform(click());

        ViewInteraction appCompatEditText24 = onView(
                allOf(withId(R.id.password), isDisplayed()));
        appCompatEditText24.perform(replaceText("12345678"), closeSoftKeyboard());

        ViewInteraction appCompatEditText25 = onView(
                allOf(withId(R.id.password), withText("12345678"), isDisplayed()));
        appCompatEditText25.perform(pressImeActionButton());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.btnLogin), withText("Prijava"), isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction appCompatEditText26 = onView(
                allOf(withId(R.id.password), withText("12345678"), isDisplayed()));
        appCompatEditText26.perform(click());

        ViewInteraction appCompatEditText27 = onView(
                allOf(withId(R.id.password), withText("12345678"), isDisplayed()));
        appCompatEditText27.perform(replaceText("1234567"), closeSoftKeyboard());

        ViewInteraction appCompatEditText28 = onView(
                allOf(withId(R.id.password), withText("1234567"), isDisplayed()));
        appCompatEditText28.perform(pressImeActionButton());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.btnLogin), withText("Prijava"), isDisplayed()));
        appCompatButton12.perform(click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Odjava"), isDisplayed()));
        appCompatTextView.perform(click());

    }

}
