package com.artesaniasclient.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.artesaniasclient.R;
import com.artesaniasclient.activity_principal;
import com.artesaniasclient.controller.CompanyController;
import com.artesaniasclient.interfaces.ICompanyComunication;
import com.artesaniasclient.model.Company;

import java.util.ArrayList;
import java.util.Date;

public class fragment_register_company extends Fragment implements ICompanyComunication {

    private CompanyController companyController;

    private EditText txtNameBussines;
    private EditText txtRuc;
    private EditText txtCity;
    private EditText txtAddress;
    Button buttonRegistry;
    Button buttonCancel;


    public fragment_register_company() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_company, container, false);
        companyController = new CompanyController(this);
        buttonRegistry = view.findViewById(R.id.registrar_company);
        buttonCancel = view.findViewById(R.id.cancelar);
        txtNameBussines = view.findViewById(R.id.namebussines);
        txtRuc = view.findViewById(R.id.ruc);
        txtCity = view.findViewById(R.id.city);
        txtAddress = view.findViewById(R.id.address);

        buttonRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registry_company();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_company();
            }
        });

        return view;
    }

    public void registry_company() {
        Company company = new Company();
        company.setBusinessname(txtNameBussines.getText().toString());
        company.setRuc(txtRuc.getText().toString());
        company.setCity(txtCity.getText().toString());
        company.setAddress(txtAddress.getText().toString());
        company.setDateregistry(new Date().toString());
        company.setIsactive(false);
        companyController.addCompany(company);
    }

    public void cancel_company() {
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