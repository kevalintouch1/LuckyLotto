package com.megalotto.megalotto.helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class PicModeSelectDialogFragment extends DialogFragment {
    private IPicModeSelectListener iPicModeSelectListener;
    private final String[] picMode = {Constants.PicModes.CAMERA, Constants.PicModes.GALLERY};


    public interface IPicModeSelectListener {
        void onPicModeSelected(String str);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(this.picMode, new DialogInterface.OnClickListener() {
            @Override
            public final void onClick(DialogInterface dialogInterface, int i) {
                PicModeSelectDialogFragment.this.m303x4a8047a(dialogInterface, i);
            }
        });
        return builder.create();
    }

    
    public void m303x4a8047a(DialogInterface dialog, int which) {
        IPicModeSelectListener iPicModeSelectListener = this.iPicModeSelectListener;
        if (iPicModeSelectListener != null) {
            iPicModeSelectListener.onPicModeSelected(this.picMode[which]);
        }
    }

    public void setiPicModeSelectListener(IPicModeSelectListener iPicModeSelectListener) {
        this.iPicModeSelectListener = iPicModeSelectListener;
    }
}
