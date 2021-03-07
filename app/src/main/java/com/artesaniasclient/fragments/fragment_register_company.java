package com.artesaniasclient.fragments;

import android.content.Context;
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
import com.artesaniasclient.model.MailJob;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class fragment_register_company extends Fragment implements ICompanyComunication {

    FirebaseAuth auth;
    private CompanyController companyController;

    private EditText txtNameBussines;
    private EditText txtRuc;
    private EditText txtCity;
    private EditText txtAddress;
    private EditText txtEmailSolicitud;
    private EditText txtClaveSolicitud;
    Button buttonRegistry;
    Button buttonCancel;

    View view;


    public fragment_register_company() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register_company, container, false);
        companyController = new CompanyController(this);
        buttonRegistry = view.findViewById(R.id.registrar_company);
        buttonCancel = view.findViewById(R.id.cancelar);
        txtNameBussines = view.findViewById(R.id.namebussines);
        txtRuc = view.findViewById(R.id.ruc);
        txtCity = view.findViewById(R.id.city);
        txtAddress = view.findViewById(R.id.address);
        txtEmailSolicitud = view.findViewById(R.id.username);
        txtClaveSolicitud = view.findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();

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
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        company.setDateregistry(date);
        company.setIsactive(false);
        company.setUseremail(auth.getCurrentUser().getEmail());
        companyController.addCompany(company);
        /*SendRegistrationRequestCompany("cbbp1997@gmail.com",
                "Solicitud de registro de Empresa de artesanías",
                "Solicito uno aprobación para registrar mi emprasa a Artesanías Ecuador, cuyos datos de la misma serían los siguientes: \n" +
                        "Nombre de la empresa: " + company.getBusinessname() + "\n" +
                        "RUC: " + company.getRuc() + "\n" +
                        "Ciudad: " + company.getCity() + "\n" +
                        "Dirección: " + company.getAddress() + "\n" +
                        "Cuenta Artesanías Ecuador asociada: " + company.getUseremail());*/
        new MailJob(txtEmailSolicitud.getText().toString(), txtClaveSolicitud.getText().toString()).execute(
                new MailJob.Mail(txtEmailSolicitud.getText().toString(), "cbbp1997@gmail.com",
                        "Solicitud de registro de Empresa de artesanías",
                        "Solicito uno aprobación para registrar mi emprasa a Artesanías Ecuador, cuyos datos de la misma serían los siguientes: \n" +
                                "Nombre de la empresa: " + company.getBusinessname() + "\n" +
                                "RUC: " + company.getRuc() + "\n" +
                                "Ciudad: " + company.getCity() + "\n" +
                                "Dirección: " + company.getAddress()));
        Toast.makeText(getActivity().getApplicationContext(), "Solicitud realizada con exito, obtendra una respuestas en los siguientes dias", Toast.LENGTH_SHORT).show();
    }

    public void SendRegistrationRequestCompany (String toEmail, String subject, String message) {
        // Defino mi Intent y hago uso del objeto ACTION_SEND
        Intent intent = new Intent(Intent.ACTION_SEND);
        // Defino los Strings Email, Asunto y Mensaje con la función putExtra
        intent.putExtra(Intent.EXTRA_EMAIL,
                new String[] { toEmail });
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        // Establezco el tipo de Intent
        intent.setType("message/rfc822");
        // Lanzo el selector de cliente de Correo
        getActivity().startActivity(Intent.createChooser(intent,"Elije un cliente de Correo..."));
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