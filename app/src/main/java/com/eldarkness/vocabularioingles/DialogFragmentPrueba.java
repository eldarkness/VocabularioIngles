package com.eldarkness.vocabularioingles;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.util.ArrayList;



public class DialogFragmentPrueba extends DialogFragment implements DialogInterface{

    int item = -1;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        if(getArguments() == null){
            return super.onCreateDialog(savedInstanceState);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Selecciona una categoria:");

        CharSequence[] cs = getArguments().getStringArrayList("ListaCategorias").toArray(new String[0]);
        final int[] checkedItem = new int[]{-1};


        builder.setSingleChoiceItems(cs, checkedItem[0], new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // al seleccionar se podria quizas meter en una variable el string que se desee y luego mandarlo con la interfaz
                // En el which esta el indice del item que se ha seleccionado asi que debo trabajar aqui con el arraylist original
                Bundle bundle = getArguments();
                bundle.putString("Categoria",getArguments().getStringArrayList("ListaCategorias").get(which));



            }
        });



        return builder.create();
    }

    /*public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.layout_dialog_fragment, container, false);
    }*/


    /*public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elige una categoria para las palabras");


        if (getArguments() != null) {
            ArrayList<String> listaCategorias = getArguments().getStringArrayList("ListaCategorias");
            for (String s : listaCategorias) {
                TextView textView = new TextView(getActivity());
                textView.setText(s);
                builder.setView(textView);
            }

        }
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });




    }*/

    public Dialog onCreateDialogPrueba(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elige una categoria para las palabras");
        ArrayList<String> listaCategorias = getArguments().getStringArrayList("ListaCategorias");

        if (getArguments() != null) {
            for (String s : listaCategorias) {
                TextView textView = new TextView(getActivity());
                textView.setText(s);
                builder.setView(textView);
            }

        }
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });


        // Create the AlertDialog object and return it
        return builder.create();
    }


    @Override
    public void cancel() {

    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    public interface DialogListener{
        void onFinishEditDialog(int which);
    }
}
