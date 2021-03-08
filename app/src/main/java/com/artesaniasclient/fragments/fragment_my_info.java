package com.artesaniasclient.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.artesaniasclient.R;
import com.artesaniasclient.model.Craft;
import com.artesaniasclient.model.User;
import com.artesaniasclient.ui.login.LoginActivity;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_my_info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_my_info extends Fragment {

    TextView txtUserName;
    TextView txtUserLastName;
    TextView txtUserAddress;
    TextView txtUserPhone;
    Button btnPremium;
    Button btnModificar;
    Button btnCambiarClave;

    User user;
    Bundle bundle  = new Bundle();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_my_info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_my_info.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_my_info newInstance(String param1, String param2) {
        fragment_my_info fragment = new fragment_my_info();
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
    public void onStart() {
        super.onStart();
        txtUserName.setText(user.getFirstname());
        txtUserLastName.setText(user.getLastname());
        txtUserAddress.setText(user.getAddress());
        txtUserPhone.setText(user.getPhone());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        txtUserName = view.findViewById(R.id.infoname);
        txtUserLastName = view.findViewById(R.id.infoapellidos);
        txtUserAddress = view.findViewById(R.id.infodir);
        txtUserPhone = view.findViewById(R.id.infocel);
        btnPremium = view.findViewById(R.id.premium);
        btnModificar = view.findViewById(R.id.modify_myinfo);
        btnCambiarClave = view.findViewById(R.id.cambiarclave);

        user = new User();
        String datos = getArguments().getString("datos");
        user = new Gson().fromJson(datos, User.class);
        bundle.putString("datos", new Gson().toJson(user));

        btnPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), fragment_pasar_premium.class);
                startActivity(intent);
            }
        });

        btnCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea el nuevo fragmento y la transacción.
                Fragment nuevoFragmento = new fragment_cambiar_clave();
                nuevoFragmento.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction()
                        .replace(R.id.content, nuevoFragmento);

                // Commit a la transacción
                transaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}