package com.artesaniasclient.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.artesaniasclient.R;
import com.artesaniasclient.adapter.adpCompany;
import com.artesaniasclient.adapter.adpCrafts;
import com.artesaniasclient.interfaces.ICompanyComunication;
import com.artesaniasclient.model.Company;
import com.artesaniasclient.model.Craft;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_my_companies#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_my_companies extends Fragment implements ICompanyComunication {

    private FirebaseFirestore refFirestore;
    private adpCompany adapter;
    RecyclerView rcvCompanies;
    static String cat = "Todos";
    ArrayAdapter<CharSequence> adapterCat;
    Spinner cbbCategories;
    private ArrayList<Company> companiesList;
    ArrayAdapter<CharSequence> adp;
    String[] categories = new String[11];

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_my_companies() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_my_companies.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_my_companies newInstance(String param1, String param2) {
        fragment_my_companies fragment = new fragment_my_companies();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_companies, container, false);
        //Vincular instancia del recyclerview
        rcvCompanies = (RecyclerView)view.findViewById(R.id.rcvCompanies);
        //Definir la forma de la lista vertical
        rcvCompanies.setLayoutManager(new LinearLayoutManager(getContext()));
        refFirestore = FirebaseFirestore.getInstance();
        // Inflate the layout for this fragment
        return view;
    }

    /*private void getAllMyCompanies(String email){
        companiesList = new ArrayList<>();
        refFirestore.collection("company")
                .whereEqualTo("useremail", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            companiesList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                String address = document.getString("address");
                                String businessname = document.getString("businessname");
                                String city = document.getString("city");
                                String dateregistry = document.getString("dateregistry");
                                boolean isactive = Boolean.parseBoolean(document.get("isactive").toString());
                                String ruc = document.getString("ruc");
                                String useremail = document.getString("useremail");
                                companiesList.add(new Company(id, address, businessname, city, dateregistry, isactive, ruc, useremail));
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            adapter = new adpCompany(getContext(),companiesList);
                            rcvCompanies.setAdapter(adapter);
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }*/



    @Override
    public void add_company_success(Company c, String message) {

    }

    @Override
    public void get_company_success(ArrayList<Company> companies, String message) {

    }

    @Override
    public void delete_company_success(Company c, String message) {

    }

    @Override
    public void get_companies_by_useremail_success(ArrayList<Company> companiesList, String message) {
        if (companiesList == null) {
            Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
        } else {
            adapter = new adpCompany(getContext(),companiesList);
            rcvCompanies.setAdapter(adapter);
        }
    }
}