package com.dss.dsboxplus.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.model.AddSubNewUserDataModel;

import java.util.ArrayList;
import java.util.List;

public class SubUserViewAdapter extends RecyclerView.Adapter<SubUserViewAdapter.SubUserViewHolder> {
    private ArrayList<AddSubNewUserDataModel> dataList;
    private ArrayList<UserData> userList = new ArrayList<>();

//    public SubUserViewAdapter(ArrayList<AddSubNewUserDataModel> dataList) {
//        this.dataList = dataList;
//    }
    public SubUserViewAdapter(ArrayList<UserData> userList){
        this.userList=userList;
    }

    @NonNull
    @Override
    public SubUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_user_item_list, parent, false);
        return new SubUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubUserViewHolder holder, int position) {
//        AddSubNewUserDataModel data = dataList.get(position);
//        holder.tvSubUserName.setText(data.getUsername());
//        holder.tvSubUserContact.setText(data.getContact());
        UserData userData=userList.get(position);
        holder.tvSubUserName.setText(userData.getUsername());
        holder.tvSubUserContact.setText(userData.getMobileno());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class SubUserViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubUserName, tvSubUserContact;

        public SubUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubUserName = itemView.findViewById(R.id.tvSubUserName);
            tvSubUserContact = itemView.findViewById(R.id.tvSubUserContact);
        }
    }
}
