package com.dss.dsboxplus.recyclerview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.repo.response.SubUser;

import java.util.ArrayList;
import java.util.Random;

public class SubUserViewAdapter extends RecyclerView.Adapter<SubUserViewAdapter.SubUserViewHolder> {
    private ArrayList<SubUser> userList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public SubUserViewAdapter(ArrayList<SubUser> userList) {
        this.userList = userList;
    }

    public void updateUserList(ArrayList<SubUser> newList) {
        userList.clear();
        userList.addAll(newList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public SubUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_user_item_list, parent, false);
        return new SubUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubUserViewHolder holder, int position) {

        SubUser userData = userList.get(position);
        holder.tvSubUserName.setText(userData.getUsername());
        holder.tvSubUserContact.setText(userData.getMobileno());
        String initials = getInitials(userData.getUsername());
        holder.tvClientPhoto.setText(initials);
        int randomColor = getRandomColor();
        holder.tvClientPhoto.setBackgroundColor(randomColor);
    }

    private int getRandomColor() {
        Random rand = new Random();
        return Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    private String getInitials(String clientName) {
        String[] nameParts = clientName.split(" ");
        StringBuilder initials = new StringBuilder();

        for (String part : nameParts) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
            }
        }

        return initials.toString().toUpperCase();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(SubUser subUser);
    }

    class SubUserViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubUserName, tvSubUserContact, tvClientPhoto;

        public SubUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubUserName = itemView.findViewById(R.id.tvSubUserName);
            tvSubUserContact = itemView.findViewById(R.id.tvSubUserContact);
            tvClientPhoto = itemView.findViewById(R.id.tvClientPhoto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(userList.get(getAdapterPosition()));
                }
            });
        }
    }
}
