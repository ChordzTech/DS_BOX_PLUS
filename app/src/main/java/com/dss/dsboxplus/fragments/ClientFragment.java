package com.dss.dsboxplus.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.clients.AddNewClients;
import com.dss.dsboxplus.model.ClientsViewModel;
import com.dss.dsboxplus.recyclerview.ClientsViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FloatingActionButton add;
    private RecyclerView rvClientRecyclerView;
    private ClientsViewAdapter clientsViewAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Users_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientFragment newInstance() {
        ClientFragment fragment = new ClientFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clients_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add = view.findViewById(R.id.fabAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNewClients.class);
                startActivity(intent);
            }
        });


        initView(view);
        prepareData();
        loadData();
    }


    private void initView(View view) {
        rvClientRecyclerView = view.findViewById(R.id.rvClientRecyclerView);
        clientsViewAdapter = new ClientsViewAdapter();
//        clientsViewAdapter.setClientsList(this);
        rvClientRecyclerView.setAdapter(clientsViewAdapter);
    }

    private void loadData() {
        clientsViewAdapter.setClientsList(prepareData());
        clientsViewAdapter.notifyDataSetChanged();
    }

    private ArrayList<ClientsViewModel> prepareData() {
        ArrayList<ClientsViewModel> clientsList = new ArrayList<>();
        clientsList.add(new ClientsViewModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "Pankaj", "7972546880", "1"));
        clientsList.add(new ClientsViewModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "Anurag", "7972546880", "5"));
        clientsList.add(new ClientsViewModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "Vishal", "7972546880", "2"));
        clientsList.add(new ClientsViewModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "AJ", "7972546880", "7"));
        clientsList.add(new ClientsViewModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "JJ", "7972546880", "10"));
        return clientsList;
    }

}