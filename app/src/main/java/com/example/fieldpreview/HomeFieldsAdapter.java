package com.example.fieldpreview;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFieldsAdapter extends RecyclerView.Adapter<HomeFieldsAdapter.HomeFieldViewHolder> {

    private final ArrayList<Field> fields;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HomeFieldsAdapter(ArrayList<Field> fields) {
        this.fields = fields;
    }

    @NonNull
    @Override
    public HomeFieldsAdapter.HomeFieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mio_layout_home, parent, false);
        return new HomeFieldsAdapter.HomeFieldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFieldsAdapter.HomeFieldViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(fields.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fields.size();
    }

    static class HomeFieldViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView title;
        private final ProgressBar progressBar;

        public HomeFieldViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.homeFieldImage);
            title = itemView.findViewById(R.id.homeFieldTitle);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        public void bind(Field field) {
            Picasso.get()
                    .load(field.img)
                    .into(img, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        @Override
                        public void onError(Exception e){
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
            title.setText(field.title);
        }
    }
}
