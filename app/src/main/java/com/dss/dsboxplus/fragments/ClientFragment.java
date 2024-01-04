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
import com.dss.dsboxplus.clients.AddNewClientsActivity;
import com.dss.dsboxplus.clients.ClientDetailsActivity;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.model.ClientsDataModel;
import com.dss.dsboxplus.recyclerview.ClientsViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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
    private ArrayList<Client> clientList=new ArrayList<>();

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
        searchView = view.findViewById(R.id.svSearchInClients);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//              filterList(newText);
                return true;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNewClientsActivity.class);
                startActivity(intent);
            }
        });

        initView(view);
//        prepareData();
        loadData();

    }

    private void filterList(String newText) {
        ArrayList<ClientsDataModel> filteredList = new ArrayList<>();
        for (ClientsDataModel clientsDataModel : clientsList) {
            if (clientsDataModel.getNameOfClient().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(clientsDataModel);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
//            clientsViewAdapter.setFilteredList(filteredList);
        }
    }


    private void initView(View view) {
        rvClientRecyclerView = view.findViewById(R.id.rvClientRecyclerView);
        clientsViewAdapter = new ClientsViewAdapter();
        clientsViewAdapter.setOnClientSelectedListner(this);

    }

    private void loadData() {
        clientsViewAdapter.setClientsList(clientList);
        rvClientRecyclerView.setAdapter(clientsViewAdapter);
        clientsViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClientSelectedI(Client clientData) {
        Intent intent = new Intent(getActivity(), ClientDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("CLIENTS", clientData);
        intent.putExtra("CLIENTS_BUNDLE", bundle);
        startActivity(intent);
    }

    public void setClientsList(ArrayList<Client> estimateList) {
        this.clientList = clientList;
    }

    public ArrayList<Client> getEstimateList() {
        return clientList;
    }

    public void setClientList(ArrayList<Client> clientList) {
        this.clientList = clientList;
    }

    public ArrayList<Client> getClientList() {
        return clientList;
    }
}