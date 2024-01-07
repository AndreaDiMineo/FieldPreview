package com.example.fieldpreview;

import static android.content.ContentValues.TAG;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;

    public ProfileFragment() {}

    public static ProfileFragment newInstance(String param1, String param2, String param3, String param4) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name = view.findViewById(R.id.name);
        TextView surname = view.findViewById(R.id.surname);
        TextView email = view.findViewById(R.id.mail);
        TextView psw = view.findViewById(R.id.psw);
        TextView nbf = view.findViewById(R.id.noBookingFields);
        name.setText(mParam1);
        surname.setText(mParam2);
        email.setText("Email: " + mParam3);
        psw.setText("Password: " + mParam4);
        RecyclerView recyclerView = view.findViewById(R.id.bookingList);
        ArrayList<Field> fields = new ArrayList<>();
        BookingFieldsAdapter bookingFieldsAdapter = new BookingFieldsAdapter(fields);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Bookings")
                .whereEqualTo("user", mParam3)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                fields.add(new Field(
                                        document.get("image").toString(),
                                        document.get("title").toString(),
                                        document.get("date").toString(),
                                        document.get("hours").toString()));
                            }
                            bookingFieldsAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            nbf.setText("Nessun campo prenotato al momento");
                        }
                    }
                });
        recyclerView.setAdapter(bookingFieldsAdapter);
        bookingFieldsAdapter.setOnItemClickListener((view2, position) -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            DetailsFragment detailsFragment = DetailsFragment.newInstance(
                    fields.get(position).img,
                    fields.get(position).title,
                    fields.get(position).address,
                    fields.get(position).days,
                    fields.get(position).hours,
                    mParam1,
                    mParam2,
                    mParam3,
                    mParam4);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .setReorderingAllowed(true)
                    .commit();
        });
        Button logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileFragment.this.getContext(), MainActivity.class);
            startActivity(intent);
        });
        Button delete = view.findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            db.collection("Users")
                    .whereEqualTo("email", name.getText().toString())
                    .whereEqualTo("password", surname.getText().toString())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(ContentValues.TAG, document.getId() + " => " + document.getData());
                                    db.collection("Users").document(document.getId()).delete();
                                }
                            } else {
                                Log.w(ContentValues.TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
            Intent intent = new Intent(ProfileFragment.this.getContext(), MainActivity.class);
            startActivity(intent);
        });
    }
}