package com.artesaniasclient.controller;

import com.artesaniasclient.interfaces.ICraft;
import com.artesaniasclient.model.Company;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class CraftController {
    private final String TAG = "CraftController";
    private ICraft iCraftComunication;
    private FirebaseFirestore db;

    public CraftController() {
        initFirebase();
    }

    private void initFirebase() {
        db = FirebaseFirestore.getInstance();
    }

    public CraftController(ICraft iCraftComunication) {
        this.iCraftComunication = iCraftComunication;
        initFirebase();
    }

    public ICraft getiCraft() {
        return iCraftComunication;
    }

    public void setiCraft(ICraft iCraftComunication) {
        this.iCraftComunication = iCraftComunication;
    }

    public void setup() {
        // [START get_firestore_instance]
        db = FirebaseFirestore.getInstance();
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        // [END set_firestore_settings]
    }

    public void addCraft(Company company) {

    }


}
