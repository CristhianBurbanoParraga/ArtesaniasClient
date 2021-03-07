package com.artesaniasclient.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.artesaniasclient.R;
import com.artesaniasclient.controller.CraftController;
import com.artesaniasclient.interfaces.ICraft;
import com.artesaniasclient.interfaces.Updateable;
import com.artesaniasclient.model.Craft;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_tab_registrer_crafts#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_tab_registrer_crafts extends Fragment implements ICraft, AdapterView.OnItemSelectedListener, Updateable {

    private final Context mContext;

    String id, name;
    String nombreimg;
    static String cat = "Todos";
    View view;

    private static final int PICK_IMAGE = 100;
    private CraftController craftController;
    Button btnimagen;
    ImageView imagen;
    Uri imageUri;

    EditText txtNameArte;
    EditText txtCantArte;
    EditText txtPrecioArte;
    EditText txtDescription;
    Button registerbutton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_tab_registrer_crafts(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            id = getArguments().getString("id");
            name = getArguments().getString("name");

        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_registrer_crafts, container, false);

        craftController = new CraftController(this);

        imagen = (ImageView) view.findViewById(R.id.imgart);
        btnimagen = (Button) view.findViewById(R.id.seleccionarimg);
        registerbutton = view.findViewById(R.id.register);
        @SuppressLint("WrongViewCast") Spinner spinner = (Spinner) view.findViewById(R.id.categoriaarte);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categoria, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        txtNameArte = view.findViewById(R.id.namearte);
        txtCantArte = view.findViewById(R.id.cantarte);
        txtPrecioArte = view.findViewById(R.id.precioarte);
        txtDescription = view.findViewById(R.id.description);



        btnimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarcrafts();
            }
        });
        return view;
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imagen.setImageURI(imageUri);
            Cursor returnCursor = getContext().getContentResolver().query(imageUri, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            nombreimg = returnCursor.getString(nameIndex);
        }

    }


    public void registrarcrafts () {
        Craft craft = new Craft();
        craft.setNamecraft(txtNameArte.getText().toString());
        craft.setCategory(cat);
        craft.setQuantity(Integer.parseInt(txtCantArte.getText().toString()));
        craft.setPrice(Double.parseDouble(txtPrecioArte.getText().toString()));
        craft.setDescription(txtDescription.getText().toString());
        craft.setCompany(id);
        //Date date = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        craft.setDateregistry(date);
        //craftController.addCraft(craft);
        craftController.UploadFile(nombreimg, craft, imageUri);

    }

    @Override
    public void get_craft_success(ArrayList<Craft> crafts, String message) {

    }

    @Override
    public void add_craft_success(Craft craft, String message) {

    }

    @Override
    public void set_craft_success(Craft craft, String message) {

    }

    @Override
    public void delete_craft_success(Craft crafts, String message) {

    }

    @Override
    public void get_craft_by_company_success(ArrayList<Craft> crafts, String message) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cat = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void update() {
        String idcraft = getArguments().getString("idcraft");
        if (idcraft != null){
            idcraft = idcraft;
        }
    }
}