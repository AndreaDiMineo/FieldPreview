package com.example.fieldpreview;

import static android.content.ContentValues.TAG;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.graphics.Color;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    private static final String ARG_PARAM8 = "param8";
    private static final String ARG_PARAM9 = "param9";
    private static final String ARG_PARAM10 = "param10";
    private int mYear, mMonth, mDay, mHour, mMinute, count;
    private boolean status = false;
    private boolean bookingStatus = false;
    private boolean existed = false;
    private boolean selectedHours = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;
    private String mParam7;
    private String mParam8;
    private String mParam9;
    private String mParam10;
    private String date;
    private String hours;
    private int numP = 1;

    public DetailsFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String param9, String param10) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putString(ARG_PARAM7, param7);
        args.putString(ARG_PARAM8, param8);
        args.putString(ARG_PARAM9, param9);
        args.putString(ARG_PARAM10, param10);
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
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
            mParam7 = getArguments().getString(ARG_PARAM7);
            mParam8 = getArguments().getString(ARG_PARAM8);
            mParam9 = getArguments().getString(ARG_PARAM9);
            mParam10 = getArguments().getString(ARG_PARAM10);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView calendarImage = view.findViewById(R.id.calendarImage);
        ImageView detailsImage = view.findViewById(R.id.detailsImage);
        ImageView plus = view.findViewById(R.id.plus);
        ImageView minus = view.findViewById(R.id.minus);
        TextView partecipants = view.findViewById(R.id.partecipants);
        ProgressBar progressBar = view.findViewById(R.id.detailsProgressBar);
        RecyclerView recyclerView = view.findViewById(R.id.bookingList);
        ArrayList<Field> fields = new ArrayList<>();
        BookingFieldsAdapter bookingFieldsAdapter = new BookingFieldsAdapter(fields);
        Picasso.get()
                .load(mParam1)
                .into(detailsImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onError(Exception e){
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
        Picasso.get()
                .load(R.drawable.calendar)
                .into(calendarImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onError(Exception e){
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
        Picasso.get()
                .load(R.drawable.minus)
                .into(minus, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onError(Exception e){
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
        Picasso.get()
                .load(R.drawable.plus)
                .into(plus, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onError(Exception e){
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
        TextView calendar = view.findViewById(R.id.calendar);
        TextView detailsTitle = view.findViewById(R.id.detailsTitle);
        detailsTitle.setText(mParam2);
        TextView detailsAddress = view.findViewById(R.id.detailsAddress);
        detailsAddress.setText(mParam3);
        TextView detailsHours = view.findViewById(R.id.detailsHours);
        detailsHours.setText(mParam4 + " " + mParam5);
        Button booking = view.findViewById(R.id.booking);
        Button detailsBack = view.findViewById(R.id.detailsBack);
        partecipants.setText(String.valueOf(numP));
        detailsBack.setOnClickListener(v -> {
            if (mParam10.equals("home")) {
                numP = 1;
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }
            else if (mParam10.equals("search")) {
                numP = 1;
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SearchFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }
            else if (mParam10.equals("profile")) {
                numP = 1;
                FragmentManager fragmentManager = getParentFragmentManager();
                ProfileFragment profileFragment = ProfileFragment.newInstance(
                        mParam6,
                        mParam7,
                        mParam8,
                        mParam9
                );
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, profileFragment)
                        .setReorderingAllowed(true)
                        .commit();
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Fields")
                .whereEqualTo("title", mParam2)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                detailsAddress.setText(document.get("address").toString());
                                detailsHours.setText("Orari: " + document.get("hours").toString());
                                db.collection("Bookings")
                                        .whereEqualTo("title", mParam2)
                                        .whereEqualTo("user", mParam8)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                                        booking.setText("Annulla prenotazione");
                                                        booking.setBackgroundColor(Color.WHITE);
                                                        booking.setTextColor(Color.GREEN);
                                                        booking.setClickable(true);
                                                        bookingStatus = true;
                                                        calendar.setTextColor(R.color.black);
                                                        calendar.setText(mParam4 + " " + mParam5);
                                                        partecipants.setText(document.get("numPartecipants").toString());
                                                        minus.setVisibility(View.INVISIBLE);
                                                        minus.setClickable(false);
                                                        plus.setVisibility(View.INVISIBLE);
                                                        plus.setClickable(false);
                                                    }
                                                    bookingFieldsAdapter.notifyDataSetChanged();
                                                } else {
                                                    Log.w(TAG, "Error getting documents.", task.getException());
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        minus.setOnClickListener(v -> {
            if (numP == 1) {
                numP = 1;
            }
            else {
                numP--;
            }
            partecipants.setText(String.valueOf(numP));
        });
        plus.setOnClickListener(v -> {
            numP++;
            partecipants.setText(String.valueOf(numP));
        });
        calendarImage.setOnClickListener(v -> {
            if (bookingStatus == false) {
                if (status == false && count == 0) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(DetailsFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            calendar.setTextColor(R.color.black);
                            calendar.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            status = true;
                            count = 1;
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();
                }

                if (status == true && count == 1) {
                    final Calendar c2 = Calendar.getInstance();
                    mHour = c2.get(Calendar.HOUR_OF_DAY);
                    mMinute = c2.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(DetailsFragment.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            calendar.append(" " + hourOfDay + ":" + minute);
                            hours = hourOfDay + ":" + minute;
                            status = false;
                        }
                    }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
                if (date != "" && hours != "") {
                    status = false;
                    count = 0;
                    booking.setClickable(true);
                }
                else {
                    booking.setClickable(false);
                }
            }
        });
        booking.setOnClickListener(v -> {
            if (bookingStatus == false) {
                /*db.collection("Bookings")
                        .whereEqualTo("date", date)
                        .whereEqualTo("hours", hours)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        Toast.makeText(DetailsFragment.this.getContext(), "Prenotazione già occupata", Toast.LENGTH_LONG).show();
                                        existed = true;
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });*/
                if (existed == false) {
                    Date currentDateHours = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String[] currentDateTime = dateFormat.format(currentDateHours).split(" ");
                    String[] fieldDate = date.split("/");
                    String[] fieldHours = hours.split(":");
                    String[] currentDate = currentDateTime[0].split("/");
                    String[] currentHours = currentDateTime[1].split(":");
                    //String[] fieldSchedule = mParam5.split(":");
                    /*if (Integer.parseInt(fieldHours[0]) >= Integer.parseInt(fieldSchedule[0]) &&
                       Integer.parseInt(fieldHours[1]) <= Integer.parseInt(fieldSchedule[1])) {*/
                    if (Integer.parseInt(currentDate[0]) == Integer.parseInt(fieldDate[0]) &&
                            Integer.parseInt(currentDate[1]) == Integer.parseInt(fieldDate[1]) &&
                            Integer.parseInt(currentDate[2]) == Integer.parseInt(fieldDate[2]) &&
                            Integer.parseInt(currentHours[0]) >= Integer.parseInt(fieldHours[0]) &&
                            Integer.parseInt(currentHours[1]) >= Integer.parseInt(fieldHours[1])) {
                        selectedHours = true;
                        Toast.makeText(DetailsFragment.this.getContext(), "Orario precedente a quello attuale", Toast.LENGTH_LONG).show();
                    }
                        /*selectedHours = true;
                        Toast.makeText(DetailsFragment.this.getContext(), "Orario al di fuori dell'apertura del campo", Toast.LENGTH_LONG).show();
                    //}*/
                    else if (selectedHours == false) {
                        HashMap<String, Object> field = new HashMap<>();
                        field.put("title", mParam2);
                        field.put("image", mParam1);
                        field.put("date", date);
                        field.put("hours", hours);
                        field.put("numPartecipants", String.valueOf(numP));
                        field.put("user", mParam8);
                        db.collection("Bookings")
                                .add(field)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                        Toast.makeText(DetailsFragment.this.getContext(), "Campo prenotato", Toast.LENGTH_LONG).show();
                                        booking.setText("Annulla prenotazione");
                                        booking.setBackgroundColor(Color.WHITE);
                                        booking.setTextColor(Color.GREEN);
                                        minus.setVisibility(View.INVISIBLE);
                                        minus.setClickable(false);
                                        plus.setVisibility(View.INVISIBLE);
                                        plus.setClickable(false);
                                        bookingStatus = true;
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                    }
                    selectedHours = false;
                }
            }
            else {
                db.collection("Bookings")
                        .whereEqualTo("title", mParam2)
                        .whereEqualTo("user", mParam8)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(ContentValues.TAG, document.getId() + " => " + document.getData());
                                        db.collection("Bookings").document(document.getId()).delete();
                                    }
                                    Toast.makeText(DetailsFragment.this.getContext(), "Prenotazione campo annullata", Toast.LENGTH_LONG).show();
                                    booking.setText("Prenota");
                                    booking.setBackgroundColor(Color.GREEN);
                                    booking.setTextColor(Color.WHITE);
                                    booking.setClickable(false);
                                    bookingStatus = false;
                                    calendar.setText("");
                                    minus.setVisibility(View.VISIBLE);
                                    minus.setClickable(true);
                                    plus.setVisibility(View.VISIBLE);
                                    plus.setClickable(true);
                                    partecipants.setText(String.valueOf(1));
                                } else {
                                    Log.w(ContentValues.TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}