package com.adampc.chatanon.ui.homescreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adampc.vihaan.whatsappclone.R;
import com.adampc.chatanon.ui.Database;
import com.adampc.chatanon.ui.chatscreen.ChatActivity;
import com.adampc.chatanon.ui.models.User;
import com.adampc.chatanon.ui.models.UserChat;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;


public class UserChatsFragment extends Fragment {

    private static final String EXTRA_TAB_NAME = "tab_name";

    static UserChatsFragment newInstance() {
        Bundle args = new Bundle();
        args.putString(EXTRA_TAB_NAME, "");
        UserChatsFragment fragment = new UserChatsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        assert getArguments() != null;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        getUserChats();
    }

    private void initViews()
    {
        initRecyclerView();

    }

    private RecyclerView mRecyclerView;

    private void initRecyclerView()
    {
        View view = getView();
        if(null!=view){
            mRecyclerView= getView().findViewById(R.id.chatsRecyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    private void getUserChats()
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Log.i(TAG, "getUserChats: "+firebaseUser);
        assert firebaseUser != null;
        Query userQuery = FirebaseDatabase.getInstance().getReference().child(Database.NODE_USER_CHATS).child(firebaseUser.getUid());

        // Set click listener for the whole post view
        // Launch PostDetailActivity
        // Bind Post to ViewHolder, setting OnClickListener for the star button
        FirebaseRecyclerAdapter<UserChat, UserChatViewHolder> mAdapter = new FirebaseRecyclerAdapter<UserChat, UserChatViewHolder>(UserChat.class, R.layout.item_user_chat,
                UserChatViewHolder.class, userQuery) {
            @Override
            protected void populateViewHolder(final UserChatViewHolder viewHolder, final UserChat userChat, final int position) {

                // Set click listener for the whole post view
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        DatabaseReference userNode = FirebaseDatabase.getInstance().getReference().child(Database.NODE_USERS).child(userChat.getUid());

                        userNode.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                User user = dataSnapshot.getValue(User.class);
                                Intent intent = new Intent(getActivity(), ChatActivity.class);
                                intent.putExtra(ChatActivity.EXTRAS_USER, user);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToUserChat(userChat, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {

                    }
                });
            }

            @Override
            public void onViewDetachedFromWindow(UserChatViewHolder holder) {
                holder.onViewDetachedFromWindow();
                super.onViewDetachedFromWindow(holder);
            }
        };

        mRecyclerView.setAdapter(mAdapter);
    }
}
