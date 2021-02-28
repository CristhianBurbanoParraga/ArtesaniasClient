package com.artesaniasclient.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.artesaniasclient.R;
import com.artesaniasclient.adapter.adpCrafts;
import com.artesaniasclient.interfaces.ICraft;
import com.artesaniasclient.model.Craft;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_crafts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_crafts extends Fragment implements AdapterView.OnItemSelectedListener, ICraft {

    private FirebaseFirestore refFireStore;
    private adpCrafts adapter;
    RecyclerView rcvCrafts;
    static String cat = "Todos";
    ArrayAdapter<CharSequence> adapterCat;
    Spinner cbbCategories;
    private ArrayList<Craft> craftList;
    ArrayAdapter<CharSequence> adp;
    String[] categories = new String[11];

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
        llenarSpinner();
        adapterCat = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_spinner_item, categories);
        cbbCategories = (Spinner)view.findViewById(R.id.cbbCategory);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //como se muestran los datos
        cbbCategories.setAdapter(adapterCat);
        cbbCategories.setOnItemSelectedListener(this);
        //Vincular instancia del recyclerview
        rcvCrafts = (RecyclerView)view.findViewById(R.id.rcvCrafts);
        //Definir la forma de la lista vertical
        rcvCrafts.setLayoutManager(new LinearLayoutManager(getContext()));
        refFireStore = FirebaseFirestore.getInstance();
        // Inflate the layout for this fragment
        return view;
    }

    private void llenarSpinner(){
        categories[0] = "Todos";
        adp = ArrayAdapter.createFromResource(getContext(),R.array.categoria, android.R.layout.simple_spinner_item);
        int i = 1;
        while(i<categories.length){
            categories[i] = (String) adp.getItem(i-1);
            i=i+1;
        }
    }

    private void getCrafts(){
        craftList = new ArrayList<>();
        if (cat.equals("Todos"))
            getAllCrafts();
        else
            getCraftsbyCategory();
    }

    private void getAllCrafts(){
        refFireStore.collection("crafts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            craftList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                String category = document.getString("category");
                                String company = document.getString("company");
                                String datedisabled = document.getString("datedisabled");
                                String dateregistry = document.getString("dateregistry");
                                String description = document.getString("description");
                                String imageurl = document.getString("imageurl");
                                boolean isactive = Boolean.parseBoolean(document.get("isactive").toString());
                                String namecraft = document.getString("namecrafts");
                                double price = document.getDouble("price");
                                Integer quantity = Integer.parseInt(document.get("quantity").toString());
                                craftList.add(new Craft(category,id,company, datedisabled, dateregistry,description,imageurl,
                                        isactive,namecraft,price,quantity));
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            adapter = new adpCrafts(getContext(),craftList);
                            rcvCrafts.setAdapter(adapter);
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void getCraftsbyCategory(){
        refFireStore.collection("crafts")
                .whereEqualTo("category", cat)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            System.err.println("Listen failed:" + error);
                            return;
                        }
                        for (DocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                String id = doc.getId();
                                String category = doc.getString("category");
                                String company = doc.getString("company");
                                String datedisabled = doc.getString("datedisabled");
                                String dateregistry = doc.getString("dateregistry");
                                String description = doc.getString("description");
                                String imageurl = doc.getString("imageurl");
                                boolean isactive = doc.getBoolean("isactive");
                                String namecraft = doc.getString("namecrafts");
                                double price = doc.getDouble("price");
                                Integer quantity = Integer.parseInt(doc.get("quantity").toString());
                                craftList.add(new Craft(category, id, company, datedisabled, dateregistry,description,imageurl,
                                        isactive,namecraft,price,quantity));
                            }
                        }
                        adapter = new adpCrafts(getContext(), craftList);
                        rcvCrafts.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        cat = adapterView.getItemAtPosition(i).toString();
        getCrafts();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {cat = "Todos"; }

    @Override
    public void get_craft_success(ArrayList<Craft> crafts, String message) {

    }

    @Override
    public void add_craft_success(Craft craft, String message) {

    }

    @Override
    public void set_craft_success(Craft craft, String message) {

    }

    @Override
    public void delete_craft_success(Craft crafts, String message) {

    }
}