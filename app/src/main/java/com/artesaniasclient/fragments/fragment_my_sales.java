package com.artesaniasclient.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.artesaniasclient.R;
import com.artesaniasclient.adapter.adpMyOrders;
import com.artesaniasclient.adapter.adpMySales;
import com.artesaniasclient.model.Craft;
import com.artesaniasclient.model.Order;
import com.artesaniasclient.model.User;
import com.artesaniasclient.utils.Util;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_my_sales#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_my_sales extends Fragment implements AdapterView.OnItemSelectedListener {

    private FirebaseFirestore refFireStore;
    private adpMySales adapter;
    RecyclerView rcvSales;
    private ArrayList<Order> orderList;
    ArrayList<Craft> craft;
    ArrayList<User> users;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_my_sales() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_my_sales.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_my_sales newInstance(String param1, String param2) {
        fragment_my_sales fragment = new fragment_my_sales();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View view = inflater.inflate(R.layout.fragment_my_sales, container, false);
        //Vincular instancia del recyclerview
        rcvSales = (RecyclerView) view.findViewById(R.id.rcvSales);
        //Definir la forma de la lista vertical
        rcvSales.setLayoutManager(new LinearLayoutManager(getContext()));
        refFireStore = FirebaseFirestore.getInstance();
        orderList = new ArrayList<>();
        //users = getUsers();
        craft = getCraft();
        getAllMySales();
        // Inflate the layout for this fragment
        return view;
    }

    public ArrayList<Craft> getCraft(){
        ArrayList<Craft> list = new ArrayList<>();
        refFireStore.collection("crafts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            System.err.println("Listen failed:" + error);
                            return;
                        }
                        for (DocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                Craft craft = doc.toObject(Craft.class);
                                craft.setId(doc.getId());
                                list.add(craft);
                            }
                        }
                    }
                });
        return list;
    }

    public void getAllMySales(){
        refFireStore.collection("orders")
                .whereEqualTo("usercraftsman", Util.getUserConnect(getContext()).getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            System.err.println("Listen failed:" + error);
                            return;
                        }
                        if(orderList != null) orderList.clear();
                        for (DocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                Order ord = doc.toObject(Order.class);
                                orderList.add(ord);
                            }
                        }
                        adapter = new adpMySales(getContext(), orderList, craft);
                        rcvSales.setAdapter(adapter);
                        adapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int itemSelect = rcvSales.getChildAdapterPosition(v);
                                Order o = orderList.get(itemSelect);
                                o.setState("Enviado");
                                //llamar mètdo update state
                            }
                        });
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}