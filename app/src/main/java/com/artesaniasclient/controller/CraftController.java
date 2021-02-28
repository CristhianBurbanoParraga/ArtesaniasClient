package com.artesaniasclient.controller;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.artesaniasclient.interfaces.ICraft;
import com.artesaniasclient.model.Company;
import com.artesaniasclient.model.Craft;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

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

    public void addCraft(Craft craft) {
        // Add a new document with a generated ID
        db.collection("crafts")
                .add(craft)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        craft.setId(documentReference.getId());
                        if (craft.getId() == null) {
                            getiCraft().add_craft_success(null, "Error al crear la artesania");
                        } else {
                            getiCraft().add_craft_success(craft, "Artesania creada exitosamente");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getiCraft().add_craft_success(null, getMessageTask(e));
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void UploadFile (String nameimage, Craft craft, Uri uri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference riversRef = storageRef.child("images/" + uri.getLastPathSegment() + nameimage);

        UploadTask uploadTask = riversRef.putFile(uri);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    craft.setImageurl(downloadUri.toString());
                    addCraft(craft);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    private String getMessageTask(Exception exception) {
        String message = null;
        if (exception != null) {
            message = exception.getMessage();
        }
        return message;
    }

}
