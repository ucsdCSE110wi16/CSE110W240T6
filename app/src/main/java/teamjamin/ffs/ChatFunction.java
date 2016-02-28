package teamjamin.ffs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import teamjamin.ffs.Chat_Function.ReferenceUrl;
import teamjamin.ffs.Chat_Function.UsersChatModel;
import teamjamin.ffs.Chat_Function.MessageChatModel;
import teamjamin.ffs.Chat_Function.MessageChatAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatFunction extends Activity {

    private static final String TAG = ChatFunction.class.getSimpleName();

    private RecyclerView chatRecycle;
    private TextView     messageText;
    private MessageChatAdapter adapter;

    /* Sender and Recipient status*/
    private static final int SENDER_STATUS = 0;
    private static final int RECIPIENT_STATUS = 1;

    /* Recipient uid */
    private String recipientId;

    /* Sender uid */
    private String senderId;

    /* unique Firebase ref for this chat */
    private Firebase firebase;

    /* Listen to change in chat in firabase */
    private ChildEventListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Get information from the previous activity
        Intent getUsersData=getIntent();
        UsersChatModel usersDataModel = getUsersData.getParcelableExtra(ReferenceUrl.USERS);

        // Set recipient uid
        recipientId = usersDataModel.getRecipientUid();

        // Set sender uid;
        senderId = usersDataModel.getCurrentUserUid();

        // Reference to recyclerView and text view
        chatRecycle = (RecyclerView)findViewById(R.id.chat_recycler_view);
        messageText = (TextView)findViewById(R.id.chat_user_message);

        // Set recyclerView and adapter
        chatRecycle.setLayoutManager(new LinearLayoutManager(this));
        chatRecycle.setHasFixedSize(true);

        // Initialize adapter
        List<MessageChatModel>  emptyMessageChat = new ArrayList<MessageChatModel>();
        adapter = new MessageChatAdapter(emptyMessageChat);

        // Set adapter to recyclerView
        chatRecycle.setAdapter(adapter);

        // Initialize firebase for this chat
        firebase = new Firebase(ReferenceUrl.FIREBASE_URL).child(ReferenceUrl.CHAT).child(usersDataModel.getChatRef());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

        listener = firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {

                if(dataSnapshot.exists()){
                    MessageChatModel newMessage=dataSnapshot.getValue(MessageChatModel.class);
                    if(newMessage.getSender().equals(senderId)){
                        newMessage.setRecipientOrSenderStatus(SENDER_STATUS);
                    }
                    else{
                        newMessage.setRecipientOrSenderStatus(RECIPIENT_STATUS);
                    }
                    adapter.refillAdapter(newMessage);
                    chatRecycle.scrollToPosition(adapter.getItemCount() - 1);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "I am onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "I am onStop");

        // Remove listener
        if(listener !=null) {
            // Remove listener
            firebase.removeEventListener(listener);
        }
        // Clean chat message
        adapter.cleanUp();

    }


    public void sendMessageToFireChat(View sendButton){
        String senderMessage = messageText.getText().toString();
        senderMessage=senderMessage.trim();

        if(!senderMessage.isEmpty()){

            // Log.e(TAG, "send message");

            // Send message to firebase
            Map<String, String> newMessage = new HashMap<String, String>();
            newMessage.put("sender", senderId); // Sender uid
            newMessage.put("recipient",recipientId); // Recipient uid
            newMessage.put("message",senderMessage); // Message

            firebase.push().setValue(newMessage);

            // Clear text
            messageText.setText("");

        }
    }


}