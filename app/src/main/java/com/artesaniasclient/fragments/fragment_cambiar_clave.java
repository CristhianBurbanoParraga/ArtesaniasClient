package com.artesaniasclient.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.artesaniasclient.R;
import com.artesaniasclient.model.User;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_cambiar_clave#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_cambiar_clave extends Fragment {

    User user;
    TextView txtUserEmail;
    Button btnCambiarClave;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_cambiar_clave() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_cambiar_clave.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_cambiar_clave newInstance(String param1, String param2) {
        fragment_cambiar_clave fragment = new fragment_cambiar_clave();
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
        txtUserEmail.setText(user.getEmail());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cambiar_clave, container, false);;
        user = new User();
        String datos = getArguments().getString("datos");
        user = new Gson().fromJson(datos, User.class);
        txtUserEmail = view.findViewById(R.id.email);
        btnCambiarClave = view.findViewById(R.id.cambiarclave);

        btnCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}