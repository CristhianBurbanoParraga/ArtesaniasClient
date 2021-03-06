package com.artesaniasclient.fragments;

import android.content.Context;
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
import com.artesaniasclient.adapter.adpMyCrafts;
import com.artesaniasclient.controller.CompanyController;
import com.artesaniasclient.interfaces.ICompanyComunication;
import com.artesaniasclient.model.Company;
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
 * Use the {@link fragment_tab_my_crafts#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_tab_my_crafts extends Fragment implements AdapterView.OnItemSelectedListener {

    private final Context mContext;

    String id, name;
    private FirebaseFirestore refFireStore;
    private adpMyCrafts adapter;
    CompanyController controller;
    RecyclerView rcvCrafts;
    static String cat = "Todos";
    ArrayAdapter<CharSequence> adapterCat;
    Spinner cbbCategories;
    private ArrayList<Craft> craftList;
    ArrayList<Company> ac;
    ArrayAdapter<CharSequence> adp;
    String[] categories = new String[11];

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_tab_my_crafts(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment fragment_tab_my_crafts.
     */
    // TODO: Rename and change types and number of parameters
    /*public static fragment_tab_my_crafts newInstance(String param1, String param2) {

        fragment_tab_my_crafts fragment = new fragment_tab_my_crafts(mContext);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            id = getArguments().getString("id");
            name = getArguments().getString("name");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getAllMyCrafts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_my_crafts, container, false);

        //codigo copiado de daniela
        llenarSpinner();
        craftList = new ArrayList<>();

        adapterCat = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_spinner_item, categories);
        cbbCategories = (Spinner)view.findViewById(R.id.cbbCategory2);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //como se muestran los datos
        cbbCategories.setAdapter(adapterCat);
        cbbCategories.setOnItemSelectedListener(this);
        //Vincular instancia del recyclerview
        rcvCrafts = (RecyclerView)view.findViewById(R.id.rcvMyCrafts);
        //Definir la forma de la lista vertical
        rcvCrafts.setLayoutManager(new LinearLayoutManager(getContext()));
        refFireStore = FirebaseFirestore.getInstance();
        ac = getCompany();
        //getAllMyCrafts();
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

    private ArrayList<Company> getCompany(){
        ArrayList<Company> list = new ArrayList<>();
        refFireStore.collection("company")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //list.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                String address = document.getString("address");
                                String businessname = document.getString("businessname");
                                String city = document.getString("city");
                                String dateregistry = document.getString("dateregistry");
                                boolean isactive = Boolean.parseBoolean(document.get("isactive").toString());
                                String ruc = document.getString("ruc");
                                String useremail = document.getString("useremail");
                                list.add(new Company(id, address, businessname, city, dateregistry, isactive, ruc, useremail));
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return list;
    }

    private void getCrafts(){

    }

    public void getAllMyCrafts(){
        refFireStore.collection("crafts")
                .whereEqualTo("company", id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            System.err.println("Listen failed:" + error);
                            return;
                        }

                        craftList.clear();
                        for (DocumentSnapshot document : value) {
                            String id = document.getId();
                            String category = document.getString("category");
                            String company = document.getString("company");
                            String datedisabled = document.getString("datedisabled");
                            String dateregistry = document.getString("dateregistry");
                            String description = document.getString("description");
                            String imageurl = document.getString("imageurl");
                            boolean isactive = document.getBoolean("isactive");
                            String namecraft = document.getString("namecraft");
                            double price = document.getDouble("price");
                            Integer quantity = Integer.parseInt(document.get("quantity").toString());
                            craftList.add(new Craft(category,id,company, datedisabled, dateregistry,description,imageurl,
                                    isactive,namecraft,price,quantity));
                        }
                        adapter = new adpMyCrafts(getContext(), craftList);
                        rcvCrafts.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cat = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        cat = "Todos";
    }
}