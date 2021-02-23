package com.artesaniasclient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artesaniasclient.adapter.adpCrafts;
import com.artesaniasclient.model.Craft;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_crafts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_crafts extends Fragment {

    private FirebaseFirestore refFireStore;
    private adpCrafts mAdapter;
    RecyclerView rcvCrafts;
    private ArrayList<Craft> craftList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_crafts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_crafts.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_crafts newInstance(String param1, String param2) {
        fragment_crafts fragment = new fragment_crafts();
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
        View view = inflater.inflate(R.layout.fragment_crafts, container, false);
        //Vincular instancia del recyclerview
        rcvCrafts = (RecyclerView)view.findViewById(R.id.rcvCrafts);
        //Definir la forma de la lista vertical
        rcvCrafts.setLayoutManager(new LinearLayoutManager(getContext()));
        // Inflate the layout for this fragment
        return view;
    }

    private void getDispositivesFromFireStore(){
        refFireStore.collection("dispositivos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            craftList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getString("id");
                                String company = document.getString("company");
                                String datedisabled = document.getString("datedisabled");
                                String dateregistry = document.getString("dateregistry");
                                String description = document.getString("description");
                                String imageurl = document.getString("imageurl");
                                boolean isactive = Boolean.parseBoolean(document.getString("isactive"));
                                String namecraft = document.getString("namecraft");
                                float price = Float.parseFloat(document.getString("price"));
                                Integer quantity = Integer.parseInt(document.getString("quantity"));
                                craftList.add(new Craft(company, datedisabled, dateregistry,description,imageurl,
                                        isactive,namecraft,price,quantity));
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            mAdapter = new adpCrafts(craftList, R.layout.item_catalogo);
                            rcvCrafts.setAdapter(mAdapter);
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}