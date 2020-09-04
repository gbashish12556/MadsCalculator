package com.example.madscalculator;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final String TAG = "MainActivityScreenTest";
    private IdlingResource mIdlingResource;


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {

        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
        }
    };

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }


    @Test
    public void check_if_error_message_is_hidden(){
        onView(withId(R.id.error_message)).check(matches(not(isDisplayed())));
    }

    @Test
    public void check_if_valid_login_is_working(){

        //Click on recycler view first position
        onView(withId(R.id.userName))
                .perform(typeText("Ashish12"),closeSoftKeyboard());

        onView(withId(R.id.userPassword))
                .perform(typeText("123456$"),closeSoftKeyboard());

        onView(withId(R.id.loginButton))
                .perform(click());

        Intents.init();
        onView(withId(R.id.equal_button)).check(matches(isDisplayed()));
        Intents.release();

    }

    @Test
    public void check_if_invalid_login_displays_error(){

        //Click on recycler view first position
        onView(withId(R.id.userName))
                .perform(typeText("Ashish1"),closeSoftKeyboard());

        onView(withId(R.id.userPassword))
                .perform(typeText("12345$"),closeSoftKeyboard());

        onView(withId(R.id.loginButton))
                .perform(click());

        onView(withId(R.id.error_message)).check(matches(isDisplayed()));

    }

}