package com.example.fieldpreview;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    public HomeFragment() {}

    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView img = view.findViewById(R.id.homeFieldImage);
        TextView title = view.findViewById(R.id.homeFieldTitle);
        RecyclerView firstRecyclerView = view.findViewById(R.id.homeFieldFirstList);
        RecyclerView secondRecyclerView = view.findViewById(R.id.homeFieldSecondList);
        ArrayList<Field> firstFields = new ArrayList<>();
        ArrayList<Field> secondFields = new ArrayList<>();
        HomeFieldsAdapter firstFieldsAdapter = new HomeFieldsAdapter(firstFields);
        HomeFieldsAdapter secondFieldsAdapter = new HomeFieldsAdapter(secondFields);
        firstFields.add(new Field(
                "https://lh5.googleusercontent.com/p/AF1QipNxxeAyQe_ZVLeYXaLAO3edU3tE-RmOf7vSTZbp=w408-h543-k-no",
                "Green Calcio", null, null));
        firstFields.add(new Field(
                "https://lh5.googleusercontent.com/p/AF1QipPOzyUiMkK4LPHQTEXPPptfdd1GgQa199NKfAdb=w408-h544-k-no",
                "Campo Sportivo Leone XIII", null, null));
        firstFields.add(new Field(
                "https://lh5.googleusercontent.com/p/AF1QipPN6Fct2HLYFzKtcGPtwl2ATXF1hRLTLd5h9r0=w408-h306-k-no",
                "Centro Sportivo Scarioni", null, null));
        firstRecyclerView.setAdapter(firstFieldsAdapter);
        secondFields.add(new Field(
                "https://lh5.googleusercontent.com/p/AF1QipN-i2ZSwxW7ThEIG1n3muBD-qdYJl9AWvDbaBQ=w408-h306-k-no",
                "Campo Sportivo Comunale n.1", null, null));
        secondFields.add(new Field(
                "https://lh5.googleusercontent.com/p/AF1QipPNcOIwSnBbbHpw1_FMDc4ENXP4mH6eHHzp4ECp=w408-h544-k-no",
                "Centro Sportivo Pozzo", null, null));
        secondFields.add(new Field(
                "https://streetviewpixels-pa.googleapis.com/v1/thumbnail?panoid=ShRImNxOBgXdRB3BcQ4Miw&cb_client=search.gws-prod.gps&w=408&h=240&yaw=298.65527&pitch=0&thumbfov=100",
                "Campetto da calcio Parco Serenella", null, null));
        secondRecyclerView.setAdapter(secondFieldsAdapter);
        firstFieldsAdapter.setOnItemClickListener((v, position) -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            DetailsFragment detailsFragment = DetailsFragment.newInstance(
                    firstFields.get(position).img,
                    firstFields.get(position).title,
                    firstFields.get(position).address,
                    firstFields.get(position).days,
                    firstFields.get(position).hours,
                    null,
                    null,
                    mParam1,
                    null,
                    "home");
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .setReorderingAllowed(true)
                    .commit();
        });
        secondFieldsAdapter.setOnItemClickListener((v, position) -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            DetailsFragment detailsFragment = DetailsFragment.newInstance(
                    secondFields.get(position).img,
                    secondFields.get(position).title,
                    secondFields.get(position).address,
                    secondFields.get(position).days,
                    secondFields.get(position).hours,
                    null,
                    null,
                    mParam1,
                    null,
                    "home");
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .setReorderingAllowed(true)
                    .commit();
        });
    }
}