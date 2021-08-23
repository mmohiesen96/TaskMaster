package com.example.taskmaster;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mainRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void addTaskButtonTest() {
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.spinner)).check(matches(isDisplayed()));
    }
    @Test
    public void testList(){
        onView(withId(R.id.list1)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddTask(){
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.editTextTextPersonName)).perform(typeText("hello test") , closeSoftKeyboard());
        onView(withId(R.id.editTextTextPersonName2)).perform(typeText("test") , closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.list1)).perform(actionOnItemAtPosition(0 ,click()));
        onView(withText("hello test")).check(matches(isDisplayed()));


    }

    @Test
    public void editUserNameTest(){
        onView(withId(R.id.button5)).perform(click());
        onView(withId(R.id.editTextTextPersonName3)).perform(typeText("test"));
        onView(withId(R.id.button4)).perform(click());
        Espresso.pressBack();
        onView(withText("test")).check(matches(isDisplayed()));
    }

}
