package com.example.fridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MarkProductAsBoughtAdapter extends RecyclerView.Adapter<MarkProductAsBoughtAdapter.MarkProductAsBoughtAdapterVH>{
    public MyAdapterListener onClickListener;
    private List<Products> productsList;
    private Context context;
    private SelectedProduct selectedProduct;
    String [] products = {"Pork", "Ham", "Chicken", "Fish"};

    public MarkProductAsBoughtAdapter(List<Products> productsList, SelectedProduct selectedProduct, MyAdapterListener listener) {
           this.productsList = productsList;
           this.selectedProduct = selectedProduct;
           this.onClickListener = listener;
       }

    public List<Products> getCategoriesList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public MarkProductAsBoughtAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MarkProductAsBoughtAdapterVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_mark_as_bought,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MarkProductAsBoughtAdapterVH holder, int position) {
        Products product = productsList.get(position);
        String productName = product.getName();
        holder.tvProductName.setText(productName);
        holder.quantity.setText(String.valueOf(product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public interface SelectedProduct{
        void selectedProducts(Products product);
    }

    public interface MyAdapterListener {
        void switchOnClick(View v, int position);
    }

    public class MarkProductAsBoughtAdapterVH extends RecyclerView.ViewHolder{
        TextView tvProductName;
        Switch productSwitch;
        TextView quantity;
        public MarkProductAsBoughtAdapterVH(final View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.ProductNameRecyclerviewMarkAsBought);
            productSwitch = itemView.findViewById(R.id.MarkAsBoughtSwitch);
            quantity = itemView.findViewById(R.id.ProductQuantityMarkAsBought);

            productSwitch.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onClickListener.switchOnClick(v, getAdapterPosition());
                }
            });
        }
    }
}
