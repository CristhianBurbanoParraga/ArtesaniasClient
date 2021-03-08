package com.artesaniasclient.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.artesaniasclient.R;
import com.artesaniasclient.model.Craft;
import com.artesaniasclient.model.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmet_detail_craft#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmet_detail_craft extends Fragment {

    Craft craf;
    String craftSelec;

    ImageView imgCraft;
    TextView txtCraftCat;
    TextView txtCraftName;
    TextView txtCrafQuantity;
    TextView txtCraftPrice;
    TextView txtCraftDescription;
    Button btnComprar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmet_detail_craft() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmet_detail_craft.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmet_detail_craft newInstance(String param1, String param2) {
        fragmet_detail_craft fragment = new fragmet_detail_craft();
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
        txtCraftCat.setText(craf.getCategory());
        txtCraftName.setText(craf.getNamecraft());
        txtCrafQuantity.setText(craf.getQuantity().toString());
        txtCraftPrice.setText(String.valueOf(craf.getPrice()));
        txtCraftDescription.setText(craf.getDescription());
        Picasso.get().load(craf.getImageurl()).into(imgCraft);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmet_detail_craft, container, false);

        imgCraft = view.findViewById(R.id.imgartesania);
        txtCraftCat = view.findViewById(R.id.catartesania);
        txtCraftName = view.findViewById(R.id.nameartesania);
        txtCrafQuantity = view.findViewById(R.id.cantartesania);
        txtCraftPrice = view.findViewById(R.id.precioartesania);
        txtCraftDescription = view.findViewById(R.id.description);
        btnComprar = view.findViewById(R.id.comprar);

        craf = new Craft();
        craftSelec = getArguments().getString("craftSelec");
        craf = new Gson().fromJson(craftSelec, Craft.class);

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    //Regresar a ver catálogo
    public void regresar(View view) {
        /* FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
        Fragment fragment = fragment_crafts.newInstance("","");
        this.getFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .addToBackStack(null) .commit(); */
    }
}