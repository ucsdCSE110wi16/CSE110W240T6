package teamjamin.ffs.Chat_Function;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import teamjamin.ffs.R;
import teamjamin.ffs.ChatFunction;

import java.util.List;

/**
 * Created by Jessica on 2/27/16.
 */

public class UsersChatAdapter extends RecyclerView.Adapter<UsersChatAdapter.ViewHolderUsers> {

    private List<UsersChatModel> mList;
    private Context mcontext;
    private String userName;
    private String createdAt;

    //constructor
    public UsersChatAdapter(Context contexts, List<UsersChatModel> users) {
        mList = users;
        mcontext = contexts;
    }

    @Override
    public ViewHolderUsers onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate layout for each row
        return new ViewHolderUsers(mcontext, LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolderUsers holder, int position) {

        UsersChatModel users = mList.get(position);

        //set avatar
        int userAvatar = ChatHelper.getDrawableAvatarId(users.getAvatarId());
        Drawable avatar = ContextCompat.getDrawable(mcontext,userAvatar);
        holder.getUserPhoto().setImageDrawable(avatar);

        //set user name
        holder.getUserFirstName().setText(users.getFirstName());

        //set presence status
        holder.getStatusConnection().setText(users.getConnection());

        //set presence text color
        if (users.getConnection().equals(ReferenceUrl.ONLINE)) {
            //green
            holder.getStatusConnection().setTextColor(Color.parseColor("#00FF00"));
        }
        else {
            //red
            holder.getStatusConnection().setTextColor(Color.parseColor("#FF0000"));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void refill(UsersChatModel users) {
        //add each user and notify recycler view about the change
        mList.add(users);
        notifyDataSetChanged();
    }

    public void setNameAndCreatedAt(String name, String created) {
        userName = name;
        createdAt = created;
    }

    public void changeUser(int index, UsersChatModel user) {
        //handle change on each user and notify change
        mList.set(index,user);
        notifyDataSetChanged();
    }

    /* ViewHolderUsers */
    public class ViewHolderUsers extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView avatar;
        private TextView name;
        private TextView connection;
        private Context context;

        //constructor
        public ViewHolderUsers(Context contexts, View items) {
            super(items);
            avatar = (ImageView) items.findViewById(R.id.userPhotoProfile);
            name = (TextView) items.findViewById(R.id.userFirstNameProfile);
            connection = (TextView) itemView.findViewById(R.id.connectionStatus);
            context = contexts;

            //attach click listiner to entire row view
            itemView.setOnClickListener(this);
        }

        public ImageView getUserPhoto() {
            return avatar;
        }

        public TextView getUserFirstName() {
            return name;
        }

        public TextView getStatusConnection() {
            return connection;
        }

        @Override
        public void onClick(View view) {

            //handle click on each row
            int position = getLayoutPosition(); //get row position
            UsersChatModel user = mList.get(position); //get use object

            //provide current user username and time created
            user.setCurrentUserName(userName);
            user.setCurrentUserCreatedAt(createdAt);

            //create chat activity
            Intent chatIntent = new Intent(context,ChatFunction.class);

            //attach date to activity as a parcelable object
            chatIntent.putExtra(ReferenceUrl.PASS_INFO,user);

            //start new activity
            context.startActivity(chatIntent);
        }
    }
}
