package com.artesaniasclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.artesaniasclient.fragments.FragmentRegisterCrafts;
import com.artesaniasclient.fragments.fragment_crafts;
import com.artesaniasclient.fragments.fragment_my_companies;
import com.artesaniasclient.fragments.fragment_my_crafts;
import com.artesaniasclient.fragments.fragment_my_orders;
import com.artesaniasclient.fragments.fragment_register_company;
import com.artesaniasclient.model.User;
import com.artesaniasclient.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class activity_principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    NavigationView navView;
    ImageView imgToolbar;
    DrawerLayout drawerLayout;
    Fragment fragment;
    boolean fragmentTransaction;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    User user = null;
    static int groupMenu = 1;
    final int itemVerCatalogo = 1, itemCrearEmpresa = 2, itemPedidos = 3, itemCrearArtesania = 4, itemVerMisEmpresas = 5, itemVerMisArtesanias = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Intent i = getIntent();
        //user = (User) i.getSerializableExtra("user");

        toolbar = findViewById(R.id.toolbar);
        imgToolbar = findViewById(R.id.imgToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
        getSupportActionBar().setTitle("");

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        fragment = new fragment_crafts();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_crafts);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        fragmentTransaction = false;
        fragment = null;
        switch (menuItem.getItemId()) {
            case itemVerCatalogo:
                fragment = new fragment_crafts();
                fragmentTransaction = true;
                break;
            case itemCrearEmpresa:
                fragment = new fragment_register_company();
                fragmentTransaction = true;
                break;
            case itemPedidos:
                fragment = new fragment_my_orders();
                fragmentTransaction = true;
                break;
            case itemCrearArtesania:
                fragment = new FragmentRegisterCrafts();
                fragmentTransaction = true;
                break;
            case itemVerMisEmpresas:
                fragment = new fragment_my_companies();
                fragmentTransaction = true;
                break;
        }
        if (fragmentTransaction) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
            menuItem.setChecked(true);
            getSupportActionBar().setTitle("");
        }
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        String email = "";
        Menu m = navView.getMenu();
        m.removeGroup(1);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            email = currentUser.getEmail();
            Gson gson = new Gson();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String userJSON = sharedPreferences.getString(getString(R.string.CURRENT_USER_KEY_STORE), gson.toJson(new User()));
            user = gson.fromJson(userJSON, User.class);
            if (user != null) {
                if (user.getUsertype() != null && user.getUsertype().equals("Artesano")) {
                    m.add(groupMenu, itemVerCatalogo, itemVerCatalogo, "Ver Catálogo").setIcon(R.drawable.icon_developer_team);
                    m.add(groupMenu, itemCrearEmpresa, itemCrearEmpresa, "Registrar Empresa").setIcon(R.drawable.icon_developer_team);
                    m.add(groupMenu, itemCrearArtesania, itemCrearArtesania, "Registrar Artesanía").setIcon(R.drawable.icon_developer_team);
                    m.add(groupMenu, itemVerMisEmpresas, itemVerMisEmpresas, "Mis Empresas").setIcon(R.drawable.icon_developer_team);
                    //m.add(groupMenu, itemVerMisArtesanias, itemVerMisArtesanias, "Mis Artesanías").setIcon(R.drawable.icon_developer_team);
                } else {
                    m.add(groupMenu, itemVerCatalogo, itemVerCatalogo, "Ver Catálogo").setIcon(R.drawable.icon_developer_team);
                    m.add(groupMenu, itemCrearEmpresa, itemCrearEmpresa, "Registrar Empresa").setIcon(R.drawable.icon_developer_team);
                    m.add(groupMenu, itemPedidos, itemPedidos, "Mis Pedidos").setIcon(R.drawable.icon_developer_team);
                }
            } else {
                logout();
            }
        } else {   //en caso de no estar logeado se deshabilita el navigation view
            imgToolbar.setImageResource(R.drawable.iconarte2);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            toolbar.setNavigationIcon(null);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        //redirectActivity(currentUser);
        //updateUI(currentUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem m = menu.findItem(R.id.btnUser);
        MenuItem ml = menu.findItem(R.id.btnLogIn);
        if (user != null && user.getId() != null && user.getId().length() > 0) {
            m.setTitle(user.getUsername());
            ml.setIcon(R.drawable.icon_logout);
            ml.setTitle("Cerrar Sesión");
        } else {
            m.setTitle("");
            ml.setIcon(R.drawable.icon_login);
            ml.setTitle("Iniciar Sesión");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        if (id == R.id.btnLogIn) {
            if (user != null && user.getId() != null && user.getId().length() > 0) {
                logout();
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }
        if (id == R.id.btnContacts) {
            Intent intent = new Intent(this, activity_contacts.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAuth.signOut();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.CURRENT_USER_KEY_STORE));
        editor.apply();
        Intent intent = new Intent(this, activity_principal.class);
        startActivity(intent);
    }
}