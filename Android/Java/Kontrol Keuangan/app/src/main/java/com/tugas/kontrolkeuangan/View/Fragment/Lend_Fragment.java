package com.tugas.kontrolkeuangan.View.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.tugas.kontrolkeuangan.Database.AppDatabase;
import com.tugas.kontrolkeuangan.Database.Entity.Hutang;
import com.tugas.kontrolkeuangan.Database.Entity.Keuangan;
import com.tugas.kontrolkeuangan.R;
import com.tugas.kontrolkeuangan.View.Adapter.RecyclerHutangAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Lend_Fragment extends Fragment {

    AppDatabase database;
    EditText etTanggal, etHutang, etNama;
    AutoCompleteTextView input_sumber_dana;
    Calendar calendar = Calendar.getInstance();
    Animation box_input_in_Anim, box_input_out_Anim;
    LinearLayout input_box;
    Button btnSave, btnCancel;
    ToggleButton toggleinput;
    RecyclerView RvHutang;
    private String nowHutang="", nowNominal="",Pilihan;
    List<Hutang> hutangs;
    RecyclerHutangAdapter adapter;
    Hutang HutangforUpdate;
    ArrayList<String> sumber_dana = new ArrayList<>();
    private final static String TAG = "Lend_Fragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lend, container, false);
        input_box = view.findViewById(R.id.input_box);
        etTanggal = view.findViewById(R.id.tanggal);
        etHutang = view.findViewById(R.id.hutang);
        etNama = view.findViewById(R.id.nama);

        input_sumber_dana = view.findViewById(R.id.sumber);

        sumber_dana.add("BCA");
        sumber_dana.add("Shopee Pay");
        sumber_dana.add("OVO");
        sumber_dana.add("Go Pay");
        sumber_dana.add("Dana");
        sumber_dana.add("Saham");
        sumber_dana.add("Cash");
        ArrayAdapter<String> adaptersumber = new ArrayAdapter<>(requireContext(),R.layout.support_simple_spinner_dropdown_item,sumber_dana);
        input_sumber_dana.setAdapter(adaptersumber);
        input_sumber_dana.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                Keyboard_Hider();
            }
        });
        btnSave = view.findViewById(R.id.savehutang);
        btnCancel = view.findViewById(R.id.cancelupdate);
        toggleinput = view.findViewById(R.id.toggle_input_box);
        RvHutang = view.findViewById(R.id.RvHutang);
        box_input_in_Anim = AnimationUtils.loadAnimation(getContext(),R.anim.input_box_in_animation);
        box_input_out_Anim = AnimationUtils.loadAnimation(getContext(),R.anim.input_box_out_animation);
        RvHutang.setLayoutManager(new LinearLayoutManager(requireContext()));
        setAdapterHutang();

        etTanggal.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                Show_Calendar();
                Keyboard_Hider();
            }
        });

        etTanggal.setOnClickListener(v -> Show_Calendar());

        etHutang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable view) {
                if(view.toString().equals("")){
                    etHutang.setText("0");
                    etHutang.setSelection(1);
                }else if(!view.toString().equals(nowHutang)&&!view.toString().equals("0")) {
                    nowHutang = SeparatorThousand(view);
                    etHutang.setText(nowHutang);
                    etHutang.setSelection(nowHutang.length());
                }
            }
        });
        etHutang.setOnClickListener(v -> Dialog_Et_Manipulator(etHutang));

        toggleinput.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //input_box.setVisibility(View.VISIBLE);
                toggleinput.setBackgroundResource(R.drawable.toggle_on);
                toggleinput.setTextColor(getResources().getColor(R.color.white));
                input_box.startAnimation(box_input_in_Anim);
            } else {
                //input_box.setVisibility(View.GONE);
                toggleinput.setBackgroundResource(R.drawable.toggle_off);
                toggleinput.setTextColor(getResources().getColor(R.color.black));
                input_box.startAnimation(box_input_out_Anim);

            }
        });

        box_input_in_Anim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
                input_box.setVisibility(View.VISIBLE);
                toggleinput.setEnabled(false);
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                toggleinput.setEnabled(true);
            }
        });

        box_input_out_Anim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
                toggleinput.setEnabled(false);
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                input_box.setVisibility(View.GONE);
                toggleinput.setEnabled(true);
                if(HutangforUpdate!=null){
                    CleanUpdate();
                }else{
                    Reset_Input();
                }
            }
        });
        btnSave.setOnClickListener(v -> {
            if(!etNama.getText().toString().equals("")&&
                    !etHutang.getText().toString().equals("")&&
                    !etTanggal.getText().toString().equals("")){
                long valueHutang = Long.parseLong(etHutang.getText().toString().replaceAll("[,.]", "")), valueHutangBefore = 0;
                if(HutangforUpdate!=null){
                    valueHutangBefore = Long.parseLong(HutangforUpdate.Hutang.replaceAll("[,.]", ""));
                    Update_Data_Hutang();
                }else {
                    Add_Data_Hutang();
                }
                Update_Keuangan(input_sumber_dana.getText().toString(),valueHutangBefore-valueHutang);
                Keyboard_Hider();
                if(input_box.getVisibility()==View.VISIBLE){
                    toggleinput.performClick();
                }
            }else{
                Toast.makeText(getActivity(), "INPUT ALL THE FIELD", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(v-> {
            CleanUpdate();
            Keyboard_Hider();
        });

        return view;
    }
    @SuppressLint("DefaultLocale")
    public void Update_Keuangan(String Checker, long ValueUpdater){
        List<Keuangan> keuangans=database.KeuanganDAO().getAllkeuangan();
        Keuangan keuangan = keuangans.get(0);
        long valueUang;
        if(Checker.equals(sumber_dana.get(0))){
            valueUang = Long.parseLong(keuangan.BCA.replaceAll("[,.]", ""));
            keuangan.BCA = String.format("%,d", (valueUang + ValueUpdater));
            keuangan.BCA=keuangan.BCA.replaceAll(",", ".");
        }else if(Checker.equals(sumber_dana.get(1))){
            valueUang = Long.parseLong(keuangan.ShopeePay.replaceAll("[,.]", ""));
            keuangan.ShopeePay=String.format("%,d", (valueUang + ValueUpdater));
            keuangan.ShopeePay=keuangan.ShopeePay.replaceAll(",", ".");
        }else if(Checker.equals(sumber_dana.get(2))){
            valueUang = Long.parseLong(keuangan.OVO.replaceAll("[,.]", ""));
            keuangan.OVO=String.format("%,d", (valueUang + ValueUpdater));
            keuangan.OVO=keuangan.OVO.replaceAll(",", ".");
        }else if(Checker.equals(sumber_dana.get(3))){
            valueUang = Long.parseLong(keuangan.GoPay.replaceAll("[,.]", ""));
            keuangan.GoPay=String.format("%,d", (valueUang + ValueUpdater));
            keuangan.GoPay=keuangan.GoPay.replaceAll(",", ".");
        }else if(Checker.equals(sumber_dana.get(4))){
            valueUang = Long.parseLong(keuangan.Dana.replaceAll("[,.]", ""));
            keuangan.Dana=String.format("%,d", (valueUang + ValueUpdater));
            keuangan.Dana=keuangan.Dana.replaceAll(",", ".");
        }else if(Checker.equals(sumber_dana.get(5))){
            valueUang = Long.parseLong(keuangan.Saham.replaceAll("[,.]", ""));
            keuangan.Saham=String.format("%,d", (valueUang + ValueUpdater));
            keuangan.Saham=keuangan.Saham.replaceAll(",", ".");
        }else if(Checker.equals(sumber_dana.get(6))){
            valueUang = Long.parseLong(keuangan.Cash.replaceAll("[,.]", ""));
            keuangan.Cash=String.format("%,d", (valueUang + ValueUpdater));
            keuangan.Cash=keuangan.Cash.replaceAll(",", ".");
        }
        database.KeuanganDAO().updateALLkeuangan(
                keuangan.id,
                keuangan.BCA,
                keuangan.ShopeePay,
                keuangan.OVO,
                keuangan.GoPay,
                keuangan.Dana,
                keuangan.Saham,
                keuangan.Cash
        );
    }
    public void Keyboard_Hider(){
        final InputMethodManager imm = (InputMethodManager) requireActivity().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
    }
    public void Add_Data_Hutang(){
            Hutang hutang = new Hutang();
            hutang.Hutang = etHutang.getText().toString();
            hutang.Nama = etNama.getText().toString();
            hutang.Tanggal = etTanggal.getText().toString();
            new Add_Data_Hutang_Worker(hutang).execute();
    }

    public void Update_Data_Hutang(){
            HutangforUpdate.Hutang = etHutang.getText().toString();
            HutangforUpdate.Nama = etNama.getText().toString();
            HutangforUpdate.Tanggal = etTanggal.getText().toString();
            new Update_Data_Hutang_Worker(HutangforUpdate).execute();
    }

    public void set_Update_Data_Hutang(Hutang hutang){
        etHutang.setText(hutang.Hutang);
        etNama.setText(hutang.Nama);
        etTanggal.setText(hutang.Tanggal);
        HutangforUpdate=hutang;
        btnSave.setText(R.string.Update);
        btnCancel.setVisibility(View.VISIBLE);
        if(input_box.getVisibility()!=View.VISIBLE){
            toggleinput.performClick();
        }
    }

    public void Delete_Data_Hutang(final Hutang item){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder = new AlertDialog.Builder(requireContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(requireContext());
        }
        builder.setTitle("Menghapus Data")
                .setMessage("Anda yakin ingin menghapus data ini?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> new Delete_Data_Hutang_Worker(item).execute())
                .setNegativeButton(android.R.string.no, (dialogInterface, which) -> dialogInterface.cancel())
                .setIcon(R.drawable.ic_baseline_delete_forever_24);
        android.app.AlertDialog.Builder Choose = new android.app.AlertDialog.Builder(getActivity());
        Choose.setTitle("Dibayarkan Ke?");
        Choose.setItems(sumber_dana.toArray(new String[0]), (dialog, which) -> {
            Pilihan = sumber_dana.get(which);
            builder.show();
        });
        Choose.show();
    }

    private void Show_Calendar(){
        etTanggal.setShowSoftInputOnFocus(false);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view1, year, month, dayOfMonth) -> {
                    month = month + 1;
                    String date;
                    String m,d;
                    if (month < 10) {
                        m="0"+month;
                    } else {
                        m= String.valueOf(month);
                    }
                    if (dayOfMonth < 10) {
                        d="0"+dayOfMonth;
                    } else {
                        d= String.valueOf(dayOfMonth);
                    }
                    date = d + "-" + m + "-" + year;
                    etTanggal.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        if(!datePickerDialog.isShowing()) {
            datePickerDialog.show();
        }
    }

    private void Reset_Input(){
        etHutang.setText("");
        etNama.setText("");
        etTanggal.setText("");
        input_sumber_dana.setText(null);
    }

    @SuppressLint("DefaultLocale")
    private String SeparatorThousand(@NonNull Editable view){
        String s = null;
        try {
            String cleanString = view.toString().replaceAll("[,.]", "");
            s = String.format("%,d", Long.parseLong(cleanString));
            s = s.replaceAll(",",".");
        }catch (NumberFormatException e) { Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();}
        return s;
    }

    private void setAdapterHutang(){
        database = AppDatabase.getInstance(requireContext());
        hutangs = database.KeuanganDAO().getAllhutang();
        adapter = new RecyclerHutangAdapter(Lend_Fragment.this,requireContext());
        adapter.setList(hutangs);
        RvHutang.setAdapter(adapter);
    }

    private void updateAdapterHutang(){
        hutangs = database.KeuanganDAO().getAllhutang();
        adapter.setList(hutangs);
        adapter.notifyDataSetChanged();
    }

    private void CleanUpdate(){
        Reset_Input();
        btnSave.setText(R.string.Save);
        btnCancel.setVisibility(View.GONE);
        HutangforUpdate=null;
    }

    class Add_Data_Hutang_Worker extends AsyncTask<Void,Void,Long> {
        private final Hutang item;
        public Add_Data_Hutang_Worker(final Hutang item) {
            this.item = item;
        }

        @Override
        protected Long doInBackground(Void... voids){
            database.KeuanganDAO().insertAllhutang(item);
            return null;
        }

        protected void onPostExecute(Long along){
            super.onPostExecute(along);
            updateAdapterHutang();
            Toast.makeText(getActivity(), "HUTANG ADDED", Toast.LENGTH_SHORT).show();
            Reset_Input();
        }
    }

    class Update_Data_Hutang_Worker extends AsyncTask<Void,Void,Long> {
        Hutang item;
        public Update_Data_Hutang_Worker(Hutang hutang) {
            this.item=hutang;
        }

        @Override
        protected Long doInBackground(Void... voids){
            database.KeuanganDAO().updateALLhutang(item.id,item.Nama,item.Hutang,item.Tanggal);
            return null;
        }

        protected void onPostExecute(Long along){
            super.onPostExecute(along);
            updateAdapterHutang();
            Toast.makeText(getActivity(), "HUTANG UPDATED", Toast.LENGTH_SHORT).show();
            CleanUpdate();
        }
    }

    class Delete_Data_Hutang_Worker extends AsyncTask<Void,Void,Long> {
        private final Hutang item;
        public Delete_Data_Hutang_Worker(final Hutang item) {
            this.item = item;
        }

        @Override
        protected Long doInBackground(Void... voids){
            database.KeuanganDAO().deletehutang(item);
            long valueHutang=Long.parseLong(item.Hutang.replaceAll("[,.]", ""));
            Update_Keuangan(Pilihan,valueHutang);
            return null;
        }

        protected void onPostExecute(Long along){
            super.onPostExecute(along);
            updateAdapterHutang();
            Toast.makeText(getActivity(), "HUTANG DELETED", Toast.LENGTH_SHORT).show();
        }
    }

    public View SetDialogView(){
        View dialogView = View.inflate(requireContext(),R.layout.input_nominal_dialog, null);
        EditText etNominal = dialogView.findViewById(R.id.nominal);
        etNominal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable view) {
                if(view.toString().equals("")){
                    etNominal.setText("0");
                    etNominal.setSelection(1);
                }else if(!view.toString().equals(nowNominal)&&!view.toString().equals("0")) {
                    nowNominal = SeparatorThousand(view);
                    etNominal.setText(nowNominal);
                    etNominal.setSelection(nowNominal.length());
                }
            }
        });
        return dialogView;
    }

    public android.app.AlertDialog.Builder Dialog_Nominal_Input(String Pilihan, String[]pilihans, EditText et){
        View VNominal= SetDialogView();
        EditText etNominal=VNominal.findViewById(R.id.nominal);
        android.app.AlertDialog.Builder Input = new android.app.AlertDialog.Builder(getActivity());
        Input.setTitle("Masukan Jumlah Uang");
        Input.setView(VNominal);

        Input.setPositiveButton("OK", (dialog, whichButton) -> {
            long valueNominal= Long.parseLong(etNominal.getText().toString().replaceAll("[,.]", ""));
            long valueCurrent = Long.parseLong(et.getText().toString().replaceAll("[,.]", ""));
            long total_value;
            if(Pilihan.equals(pilihans[0])){
                total_value=valueCurrent+valueNominal;
            }else{
                total_value=valueCurrent-valueNominal;
            }
            et.setText(String.valueOf(total_value));
        });

        Input.setNegativeButton("Cancel", (dialog, whichButton) -> {});

        return Input;
    }

    public void Dialog_Et_Manipulator(EditText et){
        String[] pilihans = {"Ditambah", "Dikurang"};
        nowNominal="";
        android.app.AlertDialog.Builder Choose = new android.app.AlertDialog.Builder(getActivity());
        Choose.setTitle("Dikurang / Ditambah?");
        Choose.setItems(pilihans, (dialog, which) -> {
            Pilihan = pilihans[which];
            Dialog_Nominal_Input(Pilihan,pilihans,et).show();
        });
        Choose.show();
    }
}
