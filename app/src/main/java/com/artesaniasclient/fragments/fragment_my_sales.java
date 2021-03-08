package com.artesaniasclient.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.artesaniasclient.R;

import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_my_sales#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_my_sales extends Fragment {

    Button btn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_my_sales() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_my_sales.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_my_sales newInstance(String param1, String param2) {
        fragment_my_sales fragment = new fragment_my_sales();
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
        View view = inflater.inflate(R.layout.fragment_my_sales, container, false);
        // Inflate the layout for this fragment
        btn = view.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp("995040419","Si Jose lee esto de nuevo, significa que funciona\nOe ponte a programar jajaja...");
            }
        });
        return view;
    }

    private void openWhatsApp(String numero,String mensaje){
        try{
            PackageManager packageManager = getActivity().getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=+593 "+ numero +"&text=" + URLEncoder.encode(mensaje, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            //i.putExtra(Intent.EXTRA_STREAM, Uri.parse("https://firebasestorage.googleapis.com/v0/b/artesanias-304016.appspot.com/o/images%2F36070IMG-20210207-WA0000.jpeg?alt=media&token=81b5233c-94d7-4a4e-b8bd-faade8fa3ef8"));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }else {
                Toast.makeText(getContext(), "Error NO whatsapp", Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e) {
            System.out.println("ERROR WHATSAPP" + e.toString());
            Toast.makeText(getContext(), "Error NO whatsapp", Toast.LENGTH_SHORT).show();
        }
    }



}