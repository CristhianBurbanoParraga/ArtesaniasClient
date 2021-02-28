package com.artesaniasclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.artesaniasclient.controller.CraftController;
import com.artesaniasclient.interfaces.ICraft;
import com.artesaniasclient.model.Craft;

import java.io.File;
import java.util.ArrayList;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class RegisterCrafts extends AppCompatActivity implements ICraft {
    private static final int PICK_IMAGE = 100;
    private CraftController craftController;
    Button btnimagen;
    ImageView imagen;
    Uri imageUri;

    EditText txtNameArte;
    EditText txtCantArte;
    EditText txtPrecioArte;
    EditText txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_crafts);
        imagen = (ImageView) findViewById(R.id.imgart);
        btnimagen = (Button) findViewById(R.id.seleccionarimg);
        @SuppressLint("WrongViewCast") Spinner spinner = (Spinner) findViewById(R.id.categoriaarte);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categoria, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        txtNameArte = findViewById(R.id.namearte);
        txtCantArte = findViewById(R.id.cantarte);
        txtPrecioArte = findViewById(R.id.precioarte);
        txtDescription = findViewById(R.id.description);

        btnimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imagen.setImageURI(imageUri);
        }
    }

    public void registrarcrafts (View view) {
        Craft craft = new Craft();
        craft.setNamecraft(txtNameArte.getText().toString());
        craft.setQuantity(Integer.parseInt(txtCantArte.getText().toString()));
        craft.setPrice(Double.parseDouble(txtPrecioArte.getText().toString()));
        craft.setDescription(txtDescription.getText().toString());
        craftController.addCraft(craft);
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
}
