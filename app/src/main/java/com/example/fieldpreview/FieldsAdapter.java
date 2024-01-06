package com.example.fieldpreview;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FieldsAdapter extends RecyclerView.Adapter<FieldsAdapter.FieldViewHolder> {

    private final ArrayList<Field> fields;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public FieldsAdapter(ArrayList<Field> fields) {
        this.fields = fields;
    }

    @NonNull
    @Override
    public FieldsAdapter.FieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mio_layout_item, parent, false);
        return new FieldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FieldsAdapter.FieldViewHolder holder, @SuppressLint("RecyclerView") int position) {
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

    static class FieldViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView title;
        private final TextView address;
        private final TextView hours;
        private final ProgressBar progressBar;

        public FieldViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.fieldImage);
            title = itemView.findViewById(R.id.fieldTitle);
            address = itemView.findViewById(R.id.fieldAddress);
            hours = itemView.findViewById(R.id.fieldHours);
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
            address.setText(field.address);
            hours.setText("Orari: " + field.days + " " + field.hours);
        }
    }
}
