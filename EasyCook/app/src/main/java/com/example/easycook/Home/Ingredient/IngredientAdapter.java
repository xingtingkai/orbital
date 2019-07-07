package com.example.easycook.Home.Ingredient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easycook.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class IngredientAdapter extends FirestoreRecyclerAdapter<IngredientItem, IngredientAdapter.IngredientViewHolder> {

    private OnItemClickListener listener;

    public IngredientAdapter(@NonNull FirestoreRecyclerOptions<IngredientItem> options) {
        super(options);
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ingredient_item,
                parent, false);
        return new IngredientViewHolder(mItemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull IngredientViewHolder ingredientViewHolder, int position, @NonNull IngredientItem ingredientItem) {

        ingredientViewHolder.ingredientName.setText(ingredientItem.getIngredientName());
        ingredientViewHolder.ingredientWeight.setText(ingredientItem.getWeight() + " " + ingredientItem.getUnits());
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    /**
     * Creates a new custom view holder to hold the view to display in
     * the RecyclerView.
     */
    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientName;
        public TextView ingredientWeight;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
            ingredientWeight = itemView.findViewById(R.id.ingredient_weight);

            // when recycler view is clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
