package teamjamin.ffs;

/**
 * Created by ilia on 3/11/2016.
 */

import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ChatTest{

    private String testMessage="testMessage";
    private SendMessageTest sendMessageTest  = new SendMessageTest();

    @Rule
    public ActivityTestRule<ChatActivity> chatActivityTestRule =
            new ActivityTestRule<>(ChatActivity.class);


    @Before
    public void setUser(){
        String email = "testListener@test.com";
        String password = "password";
        NavigateToChatTest chatTestSetup = new NavigateToChatTest();
        chatTestSetup.login(email, password);
        chatTestSetup.NavigatetoChat();

        //set up message sender
        sendMessageTest.setUser();

    }

    @Test
    public void recieveMessage(){
        sendMessageTest.sendMessage();
        onView(withId(R.id.chat_recycler_view))
                .check(matches(withText(testMessage)));
    }


}

@RunWith(AndroidJUnit4.class)
class SendMessageTest{

    protected String testMessage="testMessage";
    private ChatActivity chatActivity = new ChatActivity();
    @Rule


    @Before
    public void setUser() {
        /* sets up test by loging in and navigating to chat screen*/
        String email = "testSender@test.com";
        String password = "password";
        NavigateToChatTest chatTestSetup = new NavigateToChatTest();
        chatTestSetup.login(email, password);
        chatTestSetup.NavigatetoChat();
    }

    @Test
    public void sendMessage(){

        /*asks for message to be sent*/
        onView(withId(R.id.chat_user_message))
                .perform(typeText(testMessage), closeSoftKeyboard());
        onView(withId(R.id.sendUserMessage))
                .perform(click());

        /*check to see if message is displayed*/
        onView(withId(R.id.chat_recycler_view))
                .check(matches(withText(testMessage)));
    }

}

@RunWith(AndroidJUnit4.class)
class NavigateToChatTest{
    @Rule
    public ActivityTestRule<LoginActivity> loginTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void login(String email, String password){
        /*logs into the application with specified user, assumes succesful login*/
        onView(withId(R.id.input_email)).perform(typeText(email));
        onView(withId(R.id.input_password)).perform(typeText(password));
        onView(withId(R.id.btn_login)).perform(click());

    }

    @Test
    public void NavigatetoChat(){
        /* attempts to navigate to chat*/
        onView(withId(R.id.chatBtn))
                .perform(click());
        /* checks to see if on login screen*/
        onView(withId(R.id.chat_user_message))
                .check((ViewAssertion) isDisplayed());
    }
}