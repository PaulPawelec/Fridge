package com.example.fridge;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class SelectedList extends AppCompatDialogFragment {
    EditText userIdET;
    private DialogListener dialogListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_user_enter_id,null);

        builder.setView(view)
                .setTitle("Add user to shopping list")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userId = userIdET.getText().toString();
                        dialogListener.applytext(userId);
                    }
                });
        userIdET = view.findViewById(R.id.EnterUserId);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dialogListener = (DialogListener) context;
    }

    public interface DialogListener{
        void applytext(String userId);
    }
}