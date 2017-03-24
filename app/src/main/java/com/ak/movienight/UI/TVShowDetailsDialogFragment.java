package com.ak.movienight.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.ak.movienight.R;

/**
 * Created by AK on 23/03/2017.
 */

public class TVShowDetailsDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        String overview=getArguments().getString("OVERVIEW");
        String title=getArguments().getString("TITLE");
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(overview)
                .setPositiveButton(R.string.dialog_ok_message, null);
        return builder.create();
    }
}
