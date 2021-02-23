package com.artesaniasclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.artesaniasclient.controller.UserController;
import com.artesaniasclient.interfaces.IUserComunication;
import com.artesaniasclient.model.User;
import com.artesaniasclient.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class activity_principal extends AppCompatActivity implements IUserComunication {

    FirebaseFirestore refFireStore;
    CollectionReference refCollection;
    Toolbar toolbar;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    TextView txtusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        UserController.iUserComunication = this;
        txtusername = findViewById(R.id.lblUsername);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        String email = "";
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            email = currentUser.getEmail();
            UserController.getUserForEmail(db, email);
        }

        //redirectActivity(currentUser);
        //updateUI(currentUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.btnLogIn) {
            Intent intent = new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if(id == R.id.btnContacts) {
            Intent intent = new Intent(this, activity_contacts.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void login(User user) {
        txtusername.setText(user.getUsername());
        Log.d(
                "activity_principal", "User" + " => " + user.getUsername());

    }

    @Override
    public void get_users(ArrayList<User> users) {

    }
}