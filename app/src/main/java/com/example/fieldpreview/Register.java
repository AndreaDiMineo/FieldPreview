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
import android.widget.Button;
import android.widget.EditText;

import com.example.fieldpreview.databinding.ActivityRegisterBinding;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerSubmit = findViewById(R.id.registerSubmit);
        EditText nameText = findViewById(R.id.name);
        EditText surnameText = findViewById(R.id.surname);
        EditText emailText = findViewById(R.id.email);
        EditText passwordText = findViewById(R.id.password);
        registerSubmit.setOnClickListener(v -> {
            String name = nameText.getText().toString();
            String surname = surnameText.getText().toString();
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            HashMap<String, Object> user = new HashMap<>();
            user.put("name", name);
            user.put("surname", surname);
            user.put("email", email);
            user.put("password", password);
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