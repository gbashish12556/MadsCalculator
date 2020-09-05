package com.example.madscalculator;

import androidx.test.espresso.IdlingResource;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class CalculatorActivityTest {

    private static final String TAG = "CalculatorActivityScreenTest";
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<CalculatorActivity> mActivityTestRule = new ActivityTestRule<CalculatorActivity>(CalculatorActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
        }
    };

    @Test
    public void check_on_activtity_launch_fields_are_displayed_correctly(){
        onView(withId(R.id.error_message)).check(matches(not(isDisplayed())));
        onView(withId(R.id.equal_button)).check(matches(isDisplayed()));
    }

    @Test
    public void check_valid_expressions_are_evaluted_correctly(){
        onView(withId(R.id.edit_text)).perform(typeText("5+3/2-1"),closeSoftKeyboard());
        onView(withId(R.id.equal_button)).perform(click());
        onView(withId(R.id.edit_text)).check(matches(withText("3.0")));

        onView(withText("CLR")).perform(click());

        onView(withId(R.id.edit_text)).perform(typeText("10/2/4+1"),closeSoftKeyboard());
        onView(withId(R.id.equal_button)).perform(click());
        onView(withId(R.id.edit_text)).check(matches(withText("1.0")));
    }

    @Test
    public void check_invalid_expressions_shows_error(){
        onView(withId(R.id.edit_text)).perform(typeText("5+3/2+-1"),closeSoftKeyboard());
        onView(withId(R.id.equal_button)).perform(click());
        onView(withId(R.id.error_message)).check(matches(isDisplayed()));
    }


    @Test
    public void check_if_clear_button_clears_editText(){
        onView(withId(R.id.edit_text)).perform(typeText("5+3/2+1"),closeSoftKeyboard());
        onView(withId(R.id.equal_button)).perform(click());
        onView(withText("CLR")).perform(click());
        onView(withId(R.id.edit_text)).check(matches(withText("")));
    }

    @Test
    public void check_if_ans_button_restores_last_ans(){
        onView(withId(R.id.edit_text)).perform(typeText("1+3/2-1"),closeSoftKeyboard());
        onView(withId(R.id.equal_button)).perform(click());
        onView(withText("CLR")).perform(click());
        onView(withText("ANS")).perform(click());
        onView(withId(R.id.edit_text)).check(matches(withText("1.0")));
    }
    
}