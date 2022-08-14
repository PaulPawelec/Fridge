package com.example.fridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddShoppingList  extends AppCompatDialogFragment {
    EditText listNameET;
    private DialogListener dialogListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_shopping_list,null);

        builder.setView(view)
                .setTitle("Add new shopping list")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String listName = listNameET.getText().toString();
                        dialogListener.applyTextAddShoppingList(listName);
                    }
                });
        listNameET = view.findViewById(R.id.EnterListNameAddList);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dialogListener = (DialogListener) context;
    }

    public interface DialogListener{
        void applyTextAddShoppingList(String listName);
    }
}
