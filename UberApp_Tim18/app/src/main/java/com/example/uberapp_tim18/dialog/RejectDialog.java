package com.example.uberapp_tim18.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.uberapp_tim18.R;

public class RejectDialog extends DialogFragment {

    private EditText editText;
    private Button confirmButton;
    private Button cancelButton;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.reject_dialog, null);

        EditText editText = view.findViewById(R.id.text_area_reject);
        Button confirmButton = view.findViewById(R.id.confirm_button_reject);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = String.valueOf(editText.getText());
                System.out.println(reason);
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
