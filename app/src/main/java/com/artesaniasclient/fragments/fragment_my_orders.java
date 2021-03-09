package com.artesaniasclient.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.artesaniasclient.R;
import com.artesaniasclient.adapter.adpCrafts;
import com.artesaniasclient.adapter.adpMyOrders;
import com.artesaniasclient.model.Company;
import com.artesaniasclient.model.Craft;
import com.artesaniasclient.model.Order;
import com.artesaniasclient.model.User;
import com.artesaniasclient.utils.Util;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_my_orders#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_my_orders extends Fragment implements AdapterView.OnItemSelectedListener {

    private FirebaseFirestore refFireStore;
    private adpMyOrders adapter;
    RecyclerView rcvOrders;
    private ArrayList<Order> orderList;
    ArrayList<Craft> craft;
    ArrayList<User> users;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_my_orders() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_my_orders.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_my_orders newInstance(String param1, String param2) {
        fragment_my_orders fragment = new fragment_my_orders();
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
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        //Vincular instancia del recyclerview
        rcvOrders = (RecyclerView) view.findViewById(R.id.rcvOrders);
        //Definir la forma de la lista vertical
        rcvOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        refFireStore = FirebaseFirestore.getInstance();
        orderList = new ArrayList<>();
        users = getUsers();
        craft = getCraft();
        getAllMyOrders();
        // Inflate the layout for this fragment
        return view;
    }

    public ArrayList<Craft> getCraft(){
        ArrayList<Craft> list = new ArrayList<>();
        refFireStore.collection("crafts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            System.err.println("Listen failed:" + error);
                            return;
                        }
                        for (DocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                Craft craft = doc.toObject(Craft.class);
                                craft.setId(doc.getId());
                                list.add(craft);
                            }
                        }
                    }
                });
        return list;
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> list = new ArrayList<>();
        refFireStore.collection("user")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            System.err.println("Listen failed:" + error);
                            return;
                        }
                        for (DocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                User user = doc.toObject(User.class);
                                user.setId(doc.getId());
                                list.add(user);
                            }
                        }
                    }
                });
        return list;
    }

    public void getAllMyOrders(){
        refFireStore.collection("orders")
                .whereEqualTo("userclient", Util.getUserConnect(getContext()).getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            System.err.println("Listen failed:" + error);
                            return;
                        }
                        if(orderList != null) orderList.clear();
                        for (DocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                String idcraft = doc.getString("craft");

                                /*for(int c = 0; c < craft.size(); c++){
                                    Craft craftModel = craft.get(c);
                                    if(craftModel.getId().equals(idcraft)){
                                        namecraft = craftModel.getNamecraft();
                                    }
                                }*/
                                Order ord = doc.toObject(Order.class);
                                //ord.setCraft(namecraft);
                                orderList.add(ord);
                            }
                            //Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                        adapter = new adpMyOrders(getContext(), orderList, craft);
                        rcvOrders.setAdapter(adapter);
                        adapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int itemSelect = rcvOrders.getChildAdapterPosition(v);
                                Order o = orderList.get(itemSelect);
                                String namecraft = "";
                                for(int c = 0; c < craft.size(); c++) {
                                    Craft craftModel = craft.get(c);
                                    if (craftModel.getId().equals(o.getCraft())) {
                                        namecraft = craftModel.getNamecraft();
                                    }
                                }
                                String numberUser = "";
                                for(int c = 0; c < users.size(); c++) {
                                    User userModel = users.get(c);
                                    if (userModel.getEmail().equals(o.getUsercraftsman())) {
                                        numberUser = userModel.getPhone();
                                    }
                                }
                                openWhatsApp(numberUser, "Hola! He realizado un pedido en ARTESANÍAS ECUADOR, con el detalle:\n\n" +
                                        "CÓDIGO PEDIDO: " + o.getId() +
                                        "\nFECHA: " + o.getOrderdate() +
                                        "\nARTESANÍA: " + namecraft +
                                        "\nCANTIDAD: " + o.getQuantity() +
                                        "\nVALOR TOTAL: " + o.getPrice() +
                                        "\n\nCUENTA ARTESANÍAS ECUADOR:\n" + o.getUserclient());
                            }
                        });
                    }
                });
    }

    private void openWhatsApp(String numero, String mensaje) {
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}