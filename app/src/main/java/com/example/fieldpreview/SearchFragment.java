package com.example.fieldpreview;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private double lat;
    private double lon;
    private ArrayList<Field> fields = new ArrayList<>();
    private ArrayList<Field> soccer = new ArrayList<>();
    private ArrayList<Field> volley = new ArrayList<>();
    private ArrayList<Field> padel = new ArrayList<>();
    private FieldsAdapter fieldsAdapter = new FieldsAdapter(fields);
    private FieldsAdapter soccerAdapter = new FieldsAdapter(soccer);
    private FieldsAdapter volleyAdapter = new FieldsAdapter(volley);
    private FieldsAdapter padelAdapter = new FieldsAdapter(padel);
    private boolean status = false;
    private String ris;

    public SearchFragment() {}

    public static SearchFragment newInstance(String param1, String param2, String param3, String param4) {
        SearchFragment fragment = new SearchFragment();
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText editText = view.findViewById(R.id.searchInput);
        Button search = view.findViewById(R.id.searchSubmit);
        Button soccerBtn = view.findViewById(R.id.soccerBtn);
        Button volleyBtn = view.findViewById(R.id.volleyBtn);
        Button padelBtn = view.findViewById(R.id.padelBtn);
        Button[] buttons = new Button[]{soccerBtn, volleyBtn, padelBtn};
        MapView mapView = view.findViewById(R.id.mapFieldsView);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        search.setOnClickListener(v -> {
            if (fields.size() > 0) {
                fields.clear();
            }
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Fields")
                    .whereEqualTo("city", editText.getText().toString())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    fields.add(new Field(document.get("image").toString(),
                                                    document.get("title").toString(),
                                                    document.get("address").toString(),
                                                    document.get("days").toString(),
                                                    document.get("hours").toString()));
                                }
                                fieldsAdapter.notifyDataSetChanged();
                                db.collection("Cities")
                                                .whereEqualTo("name", editText.getText().toString())
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                lat = (double) document.get("lat");
                                                                lon = (double) document.get("lon");
                                                            }
                                                        } else {
                                                            Log.w(TAG, "Error getting documents.", task.getException());
                                                            Toast.makeText(SearchFragment.this.getContext(), "Nessuna città trovata", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                mapView.getMapboxMap().setCamera(
                                        new CameraOptions.Builder()
                                                .center(Point.fromLngLat(lon, lat))
                                                .pitch(0.0)
                                                .zoom(9.0)
                                                .bearing(0.0)
                                                .build()
                                );
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                                Toast.makeText(SearchFragment.this.getContext(), "Nessun risultato trovato", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        });
        recyclerView.setAdapter(fieldsAdapter);
        fieldsAdapter.setOnItemClickListener((v, position) -> {
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
                    mParam4,
                    "search");
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .setReorderingAllowed(true)
                    .commit();
        });
        soccerAdapter.setOnItemClickListener((v, position) -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            DetailsFragment detailsFragment = DetailsFragment.newInstance(
                    soccer.get(position).img,
                    soccer.get(position).title,
                    soccer.get(position).address,
                    soccer.get(position).days,
                    soccer.get(position).hours,
                    mParam1,
                    mParam2,
                    mParam3,
                    mParam4,
                    "search");
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .setReorderingAllowed(true)
                    .commit();
        });
        volleyAdapter.setOnItemClickListener((v, position) -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            DetailsFragment detailsFragment = DetailsFragment.newInstance(
                    volley.get(position).img,
                    volley.get(position).title,
                    volley.get(position).address,
                    volley.get(position).days,
                    volley.get(position).hours,
                    mParam1,
                    mParam2,
                    mParam3,
                    mParam4,
                    "search");
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .setReorderingAllowed(true)
                    .commit();
        });
        padelAdapter.setOnItemClickListener((v, position) -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            DetailsFragment detailsFragment = DetailsFragment.newInstance(
                    padel.get(position).img,
                    padel.get(position).title,
                    padel.get(position).address,
                    padel.get(position).days,
                    padel.get(position).hours,
                    mParam1,
                    mParam2,
                    mParam3,
                    mParam4,
                    "search");
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .setReorderingAllowed(true)
                    .commit();
        });
        soccerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soccer.size() == 0) {
                    status = true;
                }
                else {
                    status = false;
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Fields")
                        .whereEqualTo("city", editText.getText().toString())
                        .whereEqualTo("category", soccerBtn.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        if (status == true)
                                            soccer.add(new Field(document.get("image").toString(),
                                                    document.get("title").toString(),
                                                    document.get("address").toString(),
                                                    document.get("days").toString(),
                                                    document.get("hours").toString()));
                                    }
                                    soccerAdapter.notifyDataSetChanged();
                                    for (Button btn : buttons) {
                                        btn.setBackgroundColor(Color.GREEN);
                                        btn.setTextColor(Color.WHITE);
                                        if (btn.getText().toString().equals("Calcio")) {
                                            btn.setBackgroundColor(Color.WHITE);
                                            btn.setTextColor(Color.GREEN);
                                        }
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                    Toast.makeText(SearchFragment.this.getContext(), "Nessun risultato trovato", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                recyclerView.setAdapter(soccerAdapter);
            }
        });
        volleyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (volley.size() == 0) {
                    status = true;
                }
                else {
                    status = false;
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Fields")
                        .whereEqualTo("city", editText.getText().toString())
                        .whereEqualTo("category", volleyBtn.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        if (status == true)
                                            volley.add(new Field(document.get("image").toString(),
                                                    document.get("title").toString(),
                                                    document.get("address").toString(),
                                                    document.get("days").toString(),
                                                    document.get("hours").toString()));
                                    }
                                    volleyAdapter.notifyDataSetChanged();
                                    for (Button btn : buttons) {
                                        btn.setBackgroundColor(Color.GREEN);
                                        btn.setTextColor(Color.WHITE);
                                        if (btn.getText().toString().equals("Pallavolo")) {
                                            btn.setBackgroundColor(Color.WHITE);
                                            btn.setTextColor(Color.GREEN);
                                        }
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                    Toast.makeText(SearchFragment.this.getContext(), "Nessun risultato trovato", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                recyclerView.setAdapter(volleyAdapter);
            }
        });
        padelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (padel.size() == 0) {
                    status = true;
                }
                else {
                    status = false;
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Fields")
                        .whereEqualTo("city", editText.getText().toString())
                        .whereEqualTo("category", padelBtn.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        if (status == true)
                                            padel.add(new Field(document.get("image").toString(),
                                                    document.get("title").toString(),
                                                    document.get("address").toString(),
                                                    document.get("days").toString(),
                                                    document.get("hours").toString()));
                                    }
                                    padelAdapter.notifyDataSetChanged();
                                    for (Button btn : buttons) {
                                        btn.setBackgroundColor(Color.GREEN);
                                        btn.setTextColor(Color.WHITE);
                                        if (btn.getText().toString().equals("Padel")) {
                                            btn.setBackgroundColor(Color.WHITE);
                                            btn.setTextColor(Color.GREEN);
                                        }
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                    Toast.makeText(SearchFragment.this.getContext(), "Nessun risultato trovato", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                recyclerView.setAdapter(padelAdapter);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}