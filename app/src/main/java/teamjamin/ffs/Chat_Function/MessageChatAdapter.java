package teamjamin.ffs.Chat_Function;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import teamjamin.ffs.R;

import java.util.List;

/**
 * Created by Jessica on 2/27/16.
 */
public class MessageChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MessageChatModel> mList;
    private static final int SENDER = 0;
    private static final int RECIPIENT = 1;

    //constructor
    public MessageChatAdapter(List<MessageChatModel> list) {
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getRecipientOrSenderStatus() == SENDER) {
            Log.e("Adapter", "sender");
            return SENDER;
        }
        else {
            return RECIPIENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case SENDER:
                View viewSender = inflater.inflate(R.layout.sender_message,viewGroup,false);
                viewHolder = new ViewHolderSender(viewSender);
                break;
            case RECIPIENT:
                View viewRecipient = inflater.inflate(R.layout.recipient_message,viewGroup,false);
                viewHolder = new ViewHolderSender(viewRecipient);
                break;
            default:
                View viewSenderDefault = inflater.inflate(R.layout.sender_message,viewGroup,false);
                viewHolder = new ViewHolderSender(viewSenderDefault);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case SENDER:
                ViewHolderSender viewHolderSender = (ViewHolderSender) viewHolder;
                configureSenderView(viewHolderSender,position);
                break;
            case RECIPIENT:
                ViewHolderRecipient viewHolderRecipient = (ViewHolderRecipient) viewHolder;
                configureRecipientView(viewHolderRecipient,position);
                break;
        }
    }

    private void configureSenderView(ViewHolderSender viewHolderSender, int position) {
        MessageChatModel senderMessage = mList.get(position);
        viewHolderSender.getSenderMessageTextView().setText(senderMessage.getMessage());
    }

    private void configureRecipientView(ViewHolderRecipient viewHolderRecipient, int position) {
        MessageChatModel recipientMessage = mList.get(position);
        viewHolderRecipient.getRecipientMessageTextView().setText(recipientMessage.getMessage());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void refillAdapter(MessageChatModel message) {
        //add new message to chat list
        mList.add(message);

        //refresh view
        notifyItemInserted(getItemCount() - 1);
    }

    public void refillFirstTimeAdapter(List<MessageChatModel> list) {
        //add new message to list
        mList.clear();
        mList.addAll(list);

        //refresh view
        notifyItemInserted(getItemCount() - 1);
    }

    public void cleanUp() {
        mList.clear();
    }

    /* Sender ViewHolder */
    public class ViewHolderSender extends RecyclerView.ViewHolder {
        private TextView senderText;

        //constructor
        public ViewHolderSender(View item) {
            super(item);
            senderText = (TextView)item.findViewById(R.id.senderMessage);
        }

        public TextView getSenderMessageTextView() {
            return senderText;
        }

        public void setSenderMessageTextView(TextView message) {
            senderText = message;
        }
    }

    /* Recipient ViewHolder */
    public class ViewHolderRecipient extends RecyclerView.ViewHolder {
        private TextView recipientText;

        //constructor
        public ViewHolderRecipient(View item) {
            super(item);
            recipientText = (TextView)item.findViewById(R.id.recipientMessage);
        }

        public TextView getRecipientMessageTextView() {
            return recipientText;
        }

        public void setRecipientMessageTextView(TextView message) {
            recipientText = message;
        }

    }
}
