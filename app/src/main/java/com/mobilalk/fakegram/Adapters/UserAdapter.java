package com.mobilalk.fakegram.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobilalk.fakegram.Model.User;
import com.mobilalk.fakegram.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> usersList;
    private boolean isFragment;

    private String fireURL = "https://fakegram-eff4c-default-rtdb.europe-west1.firebasedatabase.app";
    private FirebaseUser firebaseUser;

    public UserAdapter(Context context, List<User> usersList, boolean isFragment) {
        this.context = context;
        this.usersList = usersList;
        this.isFragment = isFragment;
    }

    public UserAdapter(Context context, List<User> usersList, boolean isFragment, FirebaseUser firebaseUser) {
        this.context = context;
        this.usersList = usersList;
        this.isFragment = isFragment;
        this.firebaseUser = firebaseUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        User user = usersList.get(position);
        holder.followButton.setVisibility(View.VISIBLE);
        holder.username.setText(user.getUsername());
        holder.fullname.setText(user.getFullname());

        Picasso.get().load(user.getProfileImage()).placeholder(R.mipmap.ic_launcher).into(holder.profileImage);
        isFollowed(user.getId(), holder.followButton);

        if (user.getId().equals(firebaseUser.getUid())) {
            holder.followButton.setVisibility(View.GONE);
        }

        holder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.followButton.getText().toString().equals("Follow")) {
                    FirebaseDatabase.getInstance(fireURL).getReference()
                            .child("Follow").child((firebaseUser.getUid())).child("Following").child(user.getId()).setValue(true);

                    FirebaseDatabase.getInstance(fireURL).getReference().child("Follow")
                            .child(user.getId()).child("Followers").child(firebaseUser.getUid()).setValue(true);

                } else {
                    FirebaseDatabase.getInstance(fireURL).getReference()
                            .child("Follow").child((firebaseUser.getUid())).child("Following").child(user.getId()).removeValue();

                    FirebaseDatabase.getInstance(fireURL).getReference().child("Follow")
                            .child(user.getId()).child("Followers").child(firebaseUser.getUid()).removeValue();
                }
            }
        });
    }

    private void isFollowed(String id, Button followButton) {
        DatabaseReference reference = FirebaseDatabase.getInstance(fireURL).getReference().child("Follow").child(firebaseUser.getUid())
                .child("Following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(id).exists()) {
                    followButton.setText("Unfollow");
                } else {
                    followButton.setText("Follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView profileImage;
        public TextView username;
        public TextView fullname;
        public Button followButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iniViewHolder();
        }

        private void iniViewHolder() {
            profileImage = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.username);
            fullname = itemView.findViewById(R.id.full_name);
            followButton = itemView.findViewById(R.id.follow_button);
        }
    }
}
