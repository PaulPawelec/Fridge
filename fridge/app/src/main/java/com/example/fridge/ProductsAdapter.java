package com.example.fridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsAdapterVH>{
    public MyAdapterListener onClickListener ;
    private List<Products> productsList;
    private Context context;
    private SelectedProduct selectedProduct;

    public ProductsAdapter(List<Products> productsList, SelectedProduct selectedProduct, MyAdapterListener listener) {
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
    public ProductsAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ProductsAdapterVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product,null));
        //View view = (LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product,parent,false));
        //return new ProductsAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapterVH holder, int position) {
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
        void plusOnClick(View v, int position);
        void minusOnClick(View v, int position);
    }

    public class ProductsAdapterVH extends RecyclerView.ViewHolder{
        TextView tvProductName;
        Button plus;
        Button minus;
        TextView quantity;
        public ProductsAdapterVH(final View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.ProductNameRecyclerviewAddProductsToList);
            plus = itemView.findViewById(R.id.PlusProductButton);
            minus = itemView.findViewById(R.id.MinusProductButton);
            quantity = itemView.findViewById(R.id.ProductQuantityText);

            plus.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onClickListener.plusOnClick(v, getAdapterPosition());
                }
            });
            minus.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onClickListener.minusOnClick(v, getAdapterPosition());
                }
            });
        }
    }
}
