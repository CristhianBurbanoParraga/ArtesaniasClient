package com.artesaniasclient.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.artesaniasclient.R;
import com.artesaniasclient.activity_empresa;
import com.artesaniasclient.activity_principal;
import com.artesaniasclient.controller.CompanyController;
import com.artesaniasclient.interfaces.ICompanyComunication;
import com.artesaniasclient.model.Company;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_register_company#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_register_company extends Fragment implements ICompanyComunication {

    private CompanyController companyController;

    private EditText txtNameBussines;
    private EditText txtRuc;
    private EditText txtCity;
    private EditText txtAddress;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_register_company() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_register_company.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_register_company newInstance(String param1, String param2) {
        fragment_register_company fragment = new fragment_register_company();
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
        View view = inflater.inflate(R.layout.fragment_register_company, container, false);
        companyController = new CompanyController(this);

        txtNameBussines = view.findViewById(R.id.namebussines);
        txtRuc = view.findViewById(R.id.ruc);
        txtCity = view.findViewById(R.id.city);
        txtAddress = view.findViewById(R.id.address);
        return view;
    }

    public void registry_company(View view) {
        Company company = new Company();
        company.setBusinessname(txtNameBussines.getText().toString());
        company.setRuc(txtRuc.getText().toString());
        company.setCity(txtCity.getText().toString());
        company.setAddress(txtAddress.getText().toString());
        company.setDateregistry(new Date().toString());
        company.setIsactive(false);
        companyController.addCompany(company);
    }

    public void cancel_company(View view) {
        Intent intent = new Intent(getContext(), activity_principal.class);
        startActivity(intent);
    }

    @Override
    public void add_company_success(Company c, String message) {
        if (c == null && c.getId() == null) {
            Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        } else {
            message = "Empresa registrada exitosamente";
            Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), activity_principal.class);
            startActivity(intent);
        }
    }

    @Override
    public void get_company_success(ArrayList<Company> companies, String message) {

    }

    @Override
    public void delete_company_success(Company c, String message) {

    }

    @Override
    public void get_companies_by_useremail_success(ArrayList<Company> companiesList, String message) {

    }
}