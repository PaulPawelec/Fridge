package com.example.fridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesAdapterVH>{
    private List<Categories> categoriesList;
    private Context context;
    private SelectedCategory selectedCategory;

    public CategoriesAdapter(List<Categories> categoriesList, SelectedCategory selectedCategory) {
           this.categoriesList = categoriesList;
           this.selectedCategory = selectedCategory;
       }
    public List<Categories> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Categories> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoriesAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CategoriesAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_categories,null));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapterVH holder, int position) {
        Categories product = categoriesList.get(position);
        String productName = product.getName();
        holder.tvProductName.setText(productName);
        if (productName.equals("Meat")) holder.imProductIcon.setImageResource(R.drawable.meat);
        else if(productName.equals("Dairy Products")) holder.imProductIcon.setImageResource(R.drawable.dairy);
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public interface SelectedCategory{
        void selectedCategory(Categories product);
    }

    public class CategoriesAdapterVH extends RecyclerView.ViewHolder{
        TextView tvProductName;
        ImageView imProductIcon;
        public CategoriesAdapterVH(@NonNull View itemView){
            super(itemView);
            tvProductName=itemView.findViewById(R.id.CategoryNameRecyclerviewAddProductsToList);
            imProductIcon=itemView.findViewById(R.id.CategoryIconRecyclerviewAddProductsToList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedCategory.selectedCategory(categoriesList.get(getAdapterPosition()));
                }
            });
        }
    }
}
