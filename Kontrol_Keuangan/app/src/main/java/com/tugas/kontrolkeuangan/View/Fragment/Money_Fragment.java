package com.tugas.kontrolkeuangan.View.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.tugas.kontrolkeuangan.Database.AppDatabase;
import com.tugas.kontrolkeuangan.Database.Entity.Hutang;
import com.tugas.kontrolkeuangan.Database.Entity.Keuangan;
import com.tugas.kontrolkeuangan.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Money_Fragment extends Fragment {
    private final Locale localeID = new Locale("in", "ID");
    private TextView tvTotalMoney, tvTotal, tvTotalLend;
    public EditText etBCA, etShopeePay, etOVO, etGoPay, etDana, etCash, etSaham;
    private String nowBCA="", nowShopeePay="", nowOVO="", nowGoPay="", nowDana="", nowCash="", nowSaham="", nowNominal, Pilihan;
    List<Keuangan> keuangans;
    Button btnSave;
    AppDatabase database;
    LinearLayout bottombox;
    long totalhutang;
    BottomSheetBehavior bottomSheetBehavior;
    private final static String TAG = "Money_Fragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money, container, false);

        etBCA=view.findViewById(R.id.bca);
        etShopeePay=view.findViewById(R.id.shopeepay);
        etOVO=view.findViewById(R.id.ovo);
        etGoPay=view.findViewById(R.id.gopay);
        etDana=view.findViewById(R.id.dana);
        etSaham=view.findViewById(R.id.saham);
        etCash=view.findViewById(R.id.cash);
        tvTotalMoney=view.findViewById(R.id.totalmoney);
        tvTotalLend=view.findViewById(R.id.totallend);
        tvTotal=view.findViewById(R.id.grandmoney);
        btnSave=view.findViewById(R.id.save);
        bottombox=view.findViewById(R.id.bottombox);

        bottomSheetBehavior=BottomSheetBehavior.from(bottombox);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight(80);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback(){
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                bottomSheetBehavior.setPeekHeight(80);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        database = AppDatabase.getInstance(requireContext());

        SetFirstData();
        SetAllField();
        SetHutang();
        SetTotal();


        etBCA.addTextChangedListener(new Et_Changer(etBCA));
        etBCA.setOnClickListener(v -> Dialog_Et_Manipulator(etBCA));

        etShopeePay.addTextChangedListener(new Et_Changer(etShopeePay));
        etShopeePay.setOnClickListener(v -> Dialog_Et_Manipulator(etShopeePay));

        etOVO.addTextChangedListener(new Et_Changer(etOVO));
        etOVO.setOnClickListener(v -> Dialog_Et_Manipulator(etOVO));

        etGoPay.addTextChangedListener(new Et_Changer(etGoPay));
        etGoPay.setOnClickListener(v -> Dialog_Et_Manipulator(etGoPay));

        etDana.addTextChangedListener(new Et_Changer(etDana));
        etDana.setOnClickListener(v -> Dialog_Et_Manipulator(etDana));

        etSaham.addTextChangedListener(new Et_Changer(etSaham));
        etSaham.setOnClickListener(v -> Dialog_Et_Manipulator(etSaham));

        etCash.addTextChangedListener(new Et_Changer(etCash));
        etCash.setOnClickListener(v -> Dialog_Et_Manipulator(etCash));
        btnSave.setOnClickListener(v->{
            UpdateAll();
            SetTotal();
        });
        return view;
    }

    private String Formatting(long money){
        return NumberFormat.getCurrencyInstance(localeID).format(money);
    }

    @SuppressLint("DefaultLocale")
    private String SeparatorThousand(Editable view){
        String s = null;
        try {
            String cleanString = view.toString().replaceAll("[,.]", "");
            s = String.format("%,d", Long.parseLong(cleanString));
            s = s.replaceAll(",",".");
        } catch (NumberFormatException e) {Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show(); }
        return s;
    }

    public void SetAllField(){
        keuangans =database.KeuanganDAO().getAllkeuangan();
        Keuangan keuangan = keuangans.get(0);
        Log.d(TAG, "setALL: "+keuangan.BCA);
        etBCA.setText(keuangan.BCA);
        etShopeePay.setText(keuangan.ShopeePay);
        etOVO.setText(keuangan.OVO);
        etGoPay.setText(keuangan.GoPay);
        etDana.setText(keuangan.Dana);
        etSaham.setText(keuangan.Saham);
        etCash.setText(keuangan.Cash);
    }

    public void SetFirstData(){
        keuangans =database.KeuanganDAO().getAllkeuangan();
        if(keuangans.size()==0) {
            Keuangan keuangan = new Keuangan();
            keuangan.BCA = "0";
            keuangan.ShopeePay = "0";
            keuangan.OVO = "0";
            keuangan.GoPay = "0";
            keuangan.Dana = "0";
            keuangan.Saham = "0";
            keuangan.Cash = "0";
            database.KeuanganDAO().insertAllkeuangan(keuangan);
        }
    }

    public void UpdateAll(){
        Keuangan keuangan = keuangans.get(0);
        int id = keuangan.id;
        database.KeuanganDAO().updateALLkeuangan(
                id,
                etBCA.getText().toString(),
                etShopeePay.getText().toString(),
                etOVO.getText().toString(),
                etGoPay.getText().toString(),
                etDana.getText().toString(),
                etSaham.getText().toString(),
                etCash.getText().toString());
        Toast.makeText(getActivity(), "DATA SAVED", Toast.LENGTH_SHORT).show();
    }

    public void SetTotal() {
        long valueBCA = Long.parseLong(etBCA.getText().toString().replaceAll("[,.]", ""));
        long valueShopeePay = Long.parseLong(etShopeePay.getText().toString().replaceAll("[,.]", ""));
        long valueOVO = Long.parseLong(etOVO.getText().toString().replaceAll("[,.]", ""));
        long valueGoPay = Long.parseLong(etGoPay.getText().toString().replaceAll("[,.]", ""));
        long valueDana = Long.parseLong(etDana.getText().toString().replaceAll("[,.]", ""));
        long valueSaham = Long.parseLong(etSaham.getText().toString().replaceAll("[,.]", ""));
        long valueCash = Long.parseLong(etCash.getText().toString().replaceAll("[,.]", ""));
        long totalmoney = valueBCA + valueShopeePay + valueOVO + valueGoPay + valueDana + valueSaham + valueCash;
        tvTotalMoney.setText(Formatting(totalmoney));
        tvTotal.setText(Formatting(totalmoney+totalhutang));
    }

    public void SetHutang(){
        List<Hutang> hutangs = database.KeuanganDAO().getAllhutang();
        totalhutang = 0;
        for (Hutang hutang:hutangs) {
            totalhutang+=Long.parseLong(hutang.Hutang.replaceAll("[,.]", ""));
        }
        tvTotalLend.setText(Formatting(totalhutang));
    }

    public View SetDialogView(){
        LayoutInflater inflater = this.getLayoutInflater();
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
                }else if(view.toString().equals("0")){
                    return;
                }
                else if(!view.toString().equals(nowNominal)) {
                    nowNominal = SeparatorThousand(view);
                    etNominal.setText(nowNominal);
                    etNominal.setSelection(nowNominal.length());
                }
            }
        });
        return dialogView;
    }

    public AlertDialog.Builder Dialog_Nominal_Input(String Pilihan, String[]pilihans, EditText et){
        View VNominal= SetDialogView();
        EditText etNominal=VNominal.findViewById(R.id.nominal);
        AlertDialog.Builder Input = new AlertDialog.Builder(getActivity());
        Input.setTitle("Masukan Jumlah Uang");
        Input.setView(VNominal);

        Input.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                long valueNominal= Long.parseLong(etNominal.getText().toString().replaceAll("[,.]", ""));
                long valueCurrent = Long.parseLong(et.getText().toString().replaceAll("[,.]", ""));
                long total_value;
                if(Pilihan.equals(pilihans[0])){
                    total_value=valueCurrent+valueNominal;
                }else{
                    total_value=valueCurrent-valueNominal;
                }
                et.setText(String.valueOf(total_value));
            }
        });

        Input.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        });

        return Input;
    }

    public void Dialog_Et_Manipulator(EditText et){
        String[] pilihans = {"Ditambah", "Dikurang"};
        nowNominal="";
        AlertDialog.Builder Choose = new AlertDialog.Builder(getActivity());
        Choose.setTitle("Dikurang / Ditambah?");
        Choose.setItems(pilihans, (dialog, which) -> {
            Pilihan = pilihans[which];
            Dialog_Nominal_Input(Pilihan,pilihans,et).show();
        });
        Choose.show();
    }

    private class Et_Changer implements TextWatcher {
        EditText et;
        Et_Changer(EditText editText) {this.et=editText;}
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable view) {
            if (view.toString().equals("")) {
                et.setText("0");
                et.setSelection(1);
            } else if (view.toString().equals("0")) {
                return;
            } else {
                switch (et.getId()) {
                    case R.id.bca:
                        if (!view.toString().equals(nowBCA)) {
                            nowBCA = SeparatorThousand(view);
                            et.setText(nowBCA);
                            et.setSelection(nowBCA.length());
                        }
                        break;
                    case R.id.shopeepay:
                        if (!view.toString().equals(nowShopeePay)) {
                            nowShopeePay = SeparatorThousand(view);
                            et.setText(nowShopeePay);
                            et.setSelection(nowShopeePay.length());
                        }
                        break;
                    case R.id.ovo:
                        if (!view.toString().equals(nowOVO)) {
                            nowOVO = SeparatorThousand(view);
                            et.setText(nowOVO);
                            et.setSelection(nowOVO.length());
                        }
                        break;
                    case R.id.gopay:
                        if (!view.toString().equals(nowGoPay)) {
                            nowGoPay = SeparatorThousand(view);
                            et.setText(nowGoPay);
                            et.setSelection(nowGoPay.length());
                        }
                        break;
                    case R.id.dana:
                        if (!view.toString().equals(nowDana)) {
                            nowDana = SeparatorThousand(view);
                            et.setText(nowDana);
                            et.setSelection(nowDana.length());
                        }
                        break;
                    case R.id.saham:
                        if (!view.toString().equals(nowSaham)) {
                            nowSaham = SeparatorThousand(view);
                            et.setText(nowSaham);
                            et.setSelection(nowSaham.length());
                        }
                        break;
                    case R.id.cash:
                        if (!view.toString().equals(nowCash)) {
                            nowCash = SeparatorThousand(view);
                            et.setText(nowCash);
                            et.setSelection(nowCash.length());
                        }
                        break;
                    default:
                        Toast.makeText(getActivity(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

}
