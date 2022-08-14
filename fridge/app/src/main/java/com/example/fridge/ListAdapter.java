package com.example.fridge;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListAdapterVH>{
    MyAdapterListener onClickListener;
    private List<ShoppingList> shoppingListsList;
    private Context context;
    private SelectedList selectedList;
    int adapterPosition;
    String IDshoplist;

    public ListAdapter(List<ShoppingList> shoppingListsList, SelectedList selectedList, MyAdapterListener listener) {
        this.shoppingListsList = shoppingListsList;
        this.selectedList = selectedList;
        onClickListener = listener;
    }
    public List<ShoppingList> getCategoriesList() {
        return shoppingListsList;
    }

    public void setCategoriesList(List<ShoppingList> shoppingLists) {
        this.shoppingListsList = shoppingLists;
    }

    @NonNull
    @Override
    public ListAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ListAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_shopping_list,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterVH holder, int position) {
        ShoppingList list = shoppingListsList.get(position);
        String productName = list.getName();
        holder.tvProductName.setText(productName);
    }

    @Override
    public int getItemCount() {
        return shoppingListsList.size();
    }

    public interface SelectedList{
        void selectedList(ShoppingList shoppingList);
    }

    public interface MyAdapterListener {
        void itemOnClick(View v, int position);
    }

    public class ListAdapterVH extends RecyclerView.ViewHolder{
        TextView tvProductName;
        public ListAdapterVH(@NonNull final View itemView){
            super(itemView);
            tvProductName=itemView.findViewById(R.id.ListNameRecyclerviewAddUsersToList);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    selectedList.selectedList(shoppingListsList.get(getAdapterPosition()));
                    setIDshoplist(shoppingListsList.get(getAdapterPosition()).getId());
                    onClickListener.itemOnClick(v,getAdapterPosition());
                }
            });
        }
    }

    public int getPosition(){
        return adapterPosition;
    }
    public String getIDShopList(){
        return IDshoplist;
    }

    public void setIDshoplist(String IDshoplist) {
        this.IDshoplist = IDshoplist;
    }
}