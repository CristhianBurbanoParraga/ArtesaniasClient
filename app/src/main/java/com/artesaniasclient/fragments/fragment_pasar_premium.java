package com.artesaniasclient.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.artesaniasclient.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_pasar_premium#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_pasar_premium extends Fragment implements AdapterView.OnItemSelectedListener {

    String[] formas = new String[4];
    ArrayAdapter<CharSequence> adp;
    ArrayAdapter<CharSequence> adapterFormaPago;
    Spinner cbbFormaPago;
    static String fp = "Mensual";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_pasar_premium() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_pasar_premium.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_pasar_premium newInstance(String param1, String param2) {
        fragment_pasar_premium fragment = new fragment_pasar_premium();
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
        View view = inflater.inflate(R.layout.fragment_pasar_premium, container, false);
        llenarSpinner();
        adapterFormaPago = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_spinner_item, formas);
        cbbFormaPago = (Spinner)view.findViewById(R.id.formapago);
        adapterFormaPago.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //como se muestran los datos
        cbbFormaPago.setAdapter(adapterFormaPago);
        cbbFormaPago.setOnItemSelectedListener(this);
        return view;
    }

    private void llenarSpinner(){
        adp = ArrayAdapter.createFromResource(getContext(),R.array.formapago, android.R.layout.simple_spinner_item);
        int i = 1;
        while(i<formas.length){
            formas[i] = (String) adp.getItem(i-1);
            i=i+1;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        fp = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}