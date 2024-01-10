package com.dss.dsboxplus.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.repo.response.SubUser;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.model.AddSubNewUserDataModel;
import com.dss.dsboxplus.profile.subUser.SuperUserSetting;

import java.util.ArrayList;

public class SubUserViewAdapter extends RecyclerView.Adapter<SubUserViewAdapter.SubUserViewHolder> {
    private ArrayList<SubUser> userList=new ArrayList<>();
    public SubUserViewAdapter(ArrayList<SubUser> userList) {
        this.userList = userList;
    }

    public void updateUserList(ArrayList<SubUser> newList) {
        userList.clear();
        userList.addAll(newList);
        notifyDataSetChanged();
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


    class SubUserViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubUserName, tvSubUserContact, tvClientPhoto;

        public SubUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubUserName = itemView.findViewById(R.id.tvSubUserName);
            tvSubUserContact = itemView.findViewById(R.id.tvSubUserContact);
            tvClientPhoto = itemView.findViewById(R.id.tvClientPhoto);
        }
    }
}
