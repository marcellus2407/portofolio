package com.example.bluedoll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.bluedoll.adapter.SpinnerAdapter;
import com.example.bluedoll.model.Doll;
import com.example.bluedoll.model.imageDoll;

import java.util.ArrayList;

public class EditDollsFragment extends Fragment {
    Spinner spinnerImages;
    SpinnerAdapter spinnerAdapter;
    EditText name,desc;
    Button edit;
    ArrayList<imageDoll> imageDolls = new ArrayList<>();
    Doll doll;
    int imgdoll, idx_current;
    public EditDollsFragment(Doll doll){
        this.doll=doll;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_dolls, container, false);
        initDataSpinner();
        MainActivity main = (MainActivity) getActivity();
        name = view.findViewById(R.id.editName);
        desc = view.findViewById(R.id.editDesc);
        edit = view.findViewById(R.id.btnEdit);
        name.setText(doll.getName());
        desc.setText(doll.getDescription());
        idx_current = main.dolls.indexOf(doll);
        spinnerImages = view.findViewById(R.id.editspinnerImages);
        spinnerAdapter = new SpinnerAdapter(requireContext(),imageDolls);
        spinnerImages.setAdapter(spinnerAdapter);
        spinnerImages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageDoll clickedItem = (imageDoll) parent.getItemAtPosition(position);
                imgdoll = clickedItem.getImage();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().isEmpty()&&!desc.getText().toString().isEmpty()) {
                    main.dolls.set(idx_current,new Doll(name.getText().toString(), imgdoll, desc.getText().toString(), doll.getCreator(), doll.getEmailcreator()));
                    Toast.makeText(getActivity(), "Doll has been edited", Toast.LENGTH_LONG).show();
                    main.loadFragment(new ViewDollsFragment());
                }else{
                    Toast.makeText(getActivity(), "Input All Field", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
    public void initDataSpinner(){
        imageDolls.add(new imageDoll(R.drawable.dolls_a,"Type A"));
        imageDolls.add(new imageDoll(R.drawable.dolls_b,"Type B"));
        imageDolls.add(new imageDoll(R.drawable.dolls_c,"Type C"));
        imageDolls.add(new imageDoll(R.drawable.dolls_d,"Type D"));
        imageDolls.add(new imageDoll(R.drawable.dolls_e,"Type E"));
        imageDolls.add(new imageDoll(R.drawable.dolls_f,"Type F"));
    }
}