package com.artesaniasclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.artesaniasclient.controller.CompanyController;
import com.artesaniasclient.interfaces.ICompanyComunication;
import com.artesaniasclient.model.Company;
import com.artesaniasclient.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Date;

public class activity_empresa extends AppCompatActivity implements ICompanyComunication {

    private CompanyController companyController;

    private EditText txtNameBussines;
    private EditText txtRuc;
    private EditText txtCity;
    private EditText txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        companyController = new CompanyController(this);
        companyController.setActivity(this);

        txtNameBussines = findViewById(R.id.namebussines);
        txtRuc = findViewById(R.id.ruc);
        txtCity = findViewById(R.id.city);
        txtAddress = findViewById(R.id.address);

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
        Intent intent = new Intent(activity_empresa.this, activity_principal.class);
        startActivity(intent);
    }

    @Override
    public void add_company_success(Company c, String message) {
        if (c == null && c.getId() == null) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        } else {
            message = "Empresa registrada exitosamente";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity_empresa.this, activity_principal.class);
            startActivity(intent);
        }
    }

    @Override
    public void get_company_success(ArrayList<Company> companies, String message) {

    }

    @Override
    public void delete_company_success(Company c, String message) {

    }
}