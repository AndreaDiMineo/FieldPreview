package com.example.fieldpreview;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.fieldpreview.databinding.ActivityRegisterBinding;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    protected ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.registerSubmit.setOnClickListener(v -> {
            String name = binding.name.getText().toString();
            String surname = binding.surname.getText().toString();
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            // Create a new user with a first and last name
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            HashMap<String, Object> user = new HashMap<>();
            user.put("name", name);
            user.put("surname", surname);
            user.put("email", email);
            user.put("password", password);
            // Add a new document with a generated ID
            db.collection("Users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Intent intent = new Intent(Register.this, Homepage.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        });
    }
}