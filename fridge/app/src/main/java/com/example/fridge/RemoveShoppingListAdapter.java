package com.example.fridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RemoveShoppingListAdapter extends RecyclerView.Adapter<RemoveShoppingListAdapter.RemoveShoppingListAdapterVH>{
    public MyAdapterListener onClickListener;
    private List<ShoppingList> shoppingListList;
    private Context context;
    private SelectedList selectedList;
    String [] products = {"Pork", "Ham", "Chicken", "Fish"};

    public RemoveShoppingListAdapter(List<ShoppingList> shoppingListList, SelectedList selectedList, MyAdapterListener listener) {
        this.shoppingListList = shoppingListList;
        this.selectedList = selectedList;
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public RemoveShoppingListAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new RemoveShoppingListAdapterVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_remove_user,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RemoveShoppingListAdapterVH holder, int position) {
        ShoppingList shoppingList = shoppingListList.get(position);
        String listName = shoppingList.getName();
        holder.listNameTV.setText(listName);
    }

    @Override
    public int getItemCount() {
        return shoppingListList.size();
    }

    public interface SelectedList{
        void selectedList(ShoppingList shoppingList);
    }

    public interface MyAdapterListener {
        void switchOnClick(View v, int position);
    }

    public class RemoveShoppingListAdapterVH extends RecyclerView.ViewHolder{
        TextView listNameTV;
        Switch productSwitch;
        public RemoveShoppingListAdapterVH(final View itemView) {
            super(itemView);
            listNameTV = itemView.findViewById(R.id.UsernameRecyclerviewRemoveUser);
            productSwitch = itemView.findViewById(R.id.UserSwitchRemoveUser);

            productSwitch.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onClickListener.switchOnClick(v, getAdapterPosition());
                }
            });
        }
    }
}