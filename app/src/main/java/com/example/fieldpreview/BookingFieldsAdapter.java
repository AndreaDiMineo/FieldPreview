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

public class BookingFieldsAdapter extends RecyclerView.Adapter<BookingFieldsAdapter.BookingFieldViewHolder> {

    private final ArrayList<Field> fields;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public BookingFieldsAdapter(ArrayList<Field> fields) {
        this.fields = fields;
    }

    @NonNull
    @Override
    public BookingFieldsAdapter.BookingFieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mio_layout_item, parent, false);
        return new BookingFieldsAdapter.BookingFieldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingFieldsAdapter.BookingFieldViewHolder holder, @SuppressLint("RecyclerView") int position) {
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

    static class BookingFieldViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView title;
        private final TextView hours;
        private final ProgressBar progressBar;

        public BookingFieldViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.fieldImage);
            title = itemView.findViewById(R.id.fieldTitle);
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
            hours.setText("Orari: " + field.days + " " + field.hours);
        }
    }
}
