package com.cinemacgp.fragment;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cinemacgp.database.DatabaseHelper;
import com.cinemacgp.databinding.FragmentFormRentalBinding;
import com.cinemacgp.model.Movie;
import com.cinemacgp.utils.UtilsMovieAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FormRentalFragment extends Fragment {
    FragmentFormRentalBinding binding;
    ArrayList<Movie> movies;
    ArrayList<String> moviesList;
    Movie movieSelected;
    Calendar calendar = Calendar.getInstance();
    DatabaseHelper db;
    int idUser;
    public FormRentalFragment(ArrayList<Movie> movies, DatabaseHelper db,int idUser){
        this.movies=movies;
        moviesList=new ArrayList<>();
        for(Movie movie:movies){
            moviesList.add(movie.id+" - "+movie.title);
        }
        this.db=db;
        this.idUser=idUser;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormRentalBinding.inflate(getLayoutInflater());
        binding.actvMovie.setAdapter(new ArrayAdapter<>
                (requireContext(), android.R.layout.select_dialog_item, moviesList));
        binding.actvLocation.setAdapter(new ArrayAdapter<>
                (requireContext(), android.R.layout.select_dialog_item,
                        new String[]{"Cinema CGP Alpha","Cinema CGP Beta"}));
        binding.actvStudio.setAdapter(new ArrayAdapter<>
                (requireContext(), android.R.layout.select_dialog_item,
                        new String[]{"Studio A","Studio B", "Studio C"}));
        binding.actvSession.setAdapter(new ArrayAdapter<>
                (requireContext(), android.R.layout.select_dialog_item,
                        new String[]{"Session 1 : 11:00-14:00","Session 2 : 14:30-17:30", "Session 3 : 18:00-21:00"}));
        binding.etDate.setOnClickListener(view -> showCalendar());
        binding.actvMovie.setOnItemClickListener((parent, arg1, pos, id) ->{
                String idMovie = binding.actvMovie.getText().toString();
                movieSelected = getMovie(Integer.parseInt(idMovie.substring(0,idMovie.indexOf(" - "))));
                binding.ivMovie.setVisibility(View.VISIBLE);
                if(movieSelected !=null){
                    Glide.with(requireContext())
                        .load(UtilsMovieAPI.IMAGE_URL + movieSelected.image)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.ivMovie);
                }
            }
        );
        binding.btnOrder.setOnClickListener(view -> {
            if(isValid()){
                if(!db.isStudioNotBooked(binding.actvLocation.getText().toString(),
                        binding.actvStudio.getText().toString(),
                        binding.actvSession.getText().toString(),
                        binding.etDate.getText().toString())){
                    Toast.makeText(requireContext(),"Studio is Booked, choose another studio or reschedule",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(db.rental(idUser,
                        movieSelected.id,
                        binding.actvLocation.getText().toString(),
                        binding.actvStudio.getText().toString(),
                        binding.actvSession.getText().toString(),
                        binding.etDate.getText().toString()
                )) {
                    Toast.makeText(requireContext(),"Rental Successfull",Toast.LENGTH_LONG).show();
                    reset();
                }else{
                    Toast.makeText(requireContext(),"Rental Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
        return binding.getRoot();
    }
    private void reset(){
        binding.actvMovie.setText(null,false);
        binding.actvLocation.setText(null,false);
        binding.actvStudio.setText(null,false);
        binding.actvSession.setText(null,false);
        binding.etDate.setText(null);
        binding.ivMovie.setVisibility(View.GONE);
        movieSelected=null;
    }
    private boolean isValid() {
        boolean valid = true;
        if(movieSelected==null){
            binding.tilMovie.setError("Select a Movie!");
            valid=false;
        }else {
            binding.tilMovie.setErrorEnabled(false);
        }
        if(binding.actvLocation.getText().toString().isEmpty()){
            binding.tilLocation.setError("Select a Location!");
            valid=false;
        }else {
            binding.tilLocation.setErrorEnabled(false);
        }
        if(binding.actvStudio.getText().toString().isEmpty()){
            binding.tilStudio.setError("Select a Studio!");
            valid=false;
        }else {
            binding.tilStudio.setErrorEnabled(false);
        }
        if(binding.actvSession.getText().toString().isEmpty()){
            binding.tilSession.setError("Select a Session!");
            valid=false;
        }else {
            binding.tilSession.setErrorEnabled(false);
        }
        if(binding.etDate.getText().toString().isEmpty()){
            binding.tilDate.setError("Select a Date!");
            valid=false;
        }else {
            binding.tilDate.setErrorEnabled(false);
        }
        return valid;
    }
    private void showCalendar(){
        binding.etDate.setShowSoftInputOnFocus(false);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view1, year, month, dayOfMonth) -> {
                    binding.etDate.setText(new SimpleDateFormat("dd MMMM yyyy").format(new GregorianCalendar(year,month,dayOfMonth).getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        if(!datePickerDialog.isShowing()) {
            datePickerDialog.show();
        }
    }
    private Movie getMovie(int id){
        for(Movie movie:movies){
            if(movie.id==id){
                return movie;
            }
        }
        return null;
    }
}