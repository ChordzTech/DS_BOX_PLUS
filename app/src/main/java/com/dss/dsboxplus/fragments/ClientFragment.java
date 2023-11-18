package com.dss.dsboxplus.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.clients.AddNewClients;
import com.dss.dsboxplus.clients.ClientDetailsActivity;
import com.dss.dsboxplus.model.ClientsDataModel;
import com.dss.dsboxplus.recyclerview.ClientsViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientFragment extends Fragment implements ClientsViewAdapter.OnClientSelectedI {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FloatingActionButton add;
    private ArrayList<ClientsDataModel> clientsList;
    private RecyclerView rvClientRecyclerView;
    private SearchView searchView;

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
        searchView=view.findViewById(R.id.svSearchInClients);
 

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
        clientsViewAdapter.setOnClientSelectedListner(this);
        rvClientRecyclerView.setAdapter(clientsViewAdapter);
    }

    private void loadData() {

        clientsViewAdapter.setClientsList(prepareData());
        clientsViewAdapter.notifyDataSetChanged();
    }

    private ArrayList<ClientsDataModel> prepareData() {
        ArrayList<ClientsDataModel> clientsList = new ArrayList<>();
        clientsList.add(new ClientsDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "Bunty", "7972546880", "1"));
        clientsList.add(new ClientsDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "Anurag", "7972546880", "5"));
        clientsList.add(new ClientsDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "Cat", "7972546880", "2"));
        clientsList.add(new ClientsDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "XYZ", "7972546880", "7"));
        clientsList.add(new ClientsDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "Aa", "7972546880", "7"));
        clientsList.add(new ClientsDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "Estimate", "7972546880", "7"));
        clientsList.add(new ClientsDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "Kallu", "7972546880", "7"));
        clientsList.add(new ClientsDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "Shubham", "7972546880", "7"));
        clientsList.add(new ClientsDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "John", "7972546880", "7"));
        clientsList.add(new ClientsDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "John", "7972546880", "5"));
        clientsList.add(new ClientsDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s", "MNO", "7972546880", "10"));
        Collections.sort(clientsList, new Comparator<ClientsDataModel>() {
            @Override
            public int compare(ClientsDataModel clientsDataModel, ClientsDataModel t1) {
                return clientsDataModel.getNameOfClient().compareTo(t1.getNameOfClient());
            }
        });

        return clientsList;

    }


    @Override
    public void onClientSelectedI(ClientsDataModel clientsDataModel) {
        Intent intent = new Intent(getActivity(), ClientDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("CLIENTS", clientsDataModel);
        intent.putExtra("CLIENTS_BUNDLE", bundle);
        startActivity(intent);
    }
}