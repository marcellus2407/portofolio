package com.example.bluedoll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluedoll.adapter.RcAdapter;
import com.example.bluedoll.model.Doll;

public class ViewDollsFragment extends Fragment {
    RecyclerView rcDolls;
    RcAdapter adapter;
    MainActivity main;
    TextView noDoll;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_dolls, container, false);
        rcDolls = view.findViewById(R.id.rcDolls);
        noDoll = view.findViewById(R.id.noDoll);
        main = (MainActivity) getActivity();
        rcDolls.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new RcAdapter(ViewDollsFragment.this,requireContext());
        adapter.setList(main.dolls);
        rcDolls.setAdapter(adapter);
        if(main.dolls.size()>0){
            noDoll.setVisibility(View.GONE);
        }
        return view;
    }
    public void detail(Doll doll){
        main.loadFragment(new DetailDollsFragment(doll));
    }
    public void edit(Doll doll){
        if(main.creator.getEmail().equals(doll.getEmailcreator())||main.creator.getRole().equals("admin")){
            main.loadFragment(new EditDollsFragment(doll));
        }else{
            Toast.makeText(getActivity(), "You're not creator or admin!", Toast.LENGTH_LONG).show();
        }
    }
    public void delete(Doll doll){
        if(main.creator.getEmail().equals(doll.getEmailcreator())||main.creator.getRole().equals("admin")){
            main.dolls.remove(doll);
            adapter.setList(main.dolls);
            adapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "Doll has been deleted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "You're not creator or admin!", Toast.LENGTH_LONG).show();
        }
    }
}