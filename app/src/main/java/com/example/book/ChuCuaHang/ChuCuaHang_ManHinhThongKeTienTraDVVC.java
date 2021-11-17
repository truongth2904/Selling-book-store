package com.example.book.ChuCuaHang;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.ChuCuaHang.sotienphaitraDVVC.TienTraDVVC;
import com.example.book.ChuCuaHang.sotienphaitraDVVC.TienTraDVVCAdapter;
import com.example.book.R;
import com.example.book.XuLyHD.DonHangChoXuLy.Bill;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChuCuaHang_ManHinhThongKeTienTraDVVC extends AppCompatActivity {
    private Spinner spnSTPTDVVCthang, spnSTPTDVVCnam;
    private ListView lvThongKeSTPTDVVC;
    private Context context;
    private ArrayList<TienTraDVVC> listTienPhaiTraDVVC;
    private Button btnThongKe;
    private RadioButton rdbTKSTPTDVVCTheoNgay, rdbTKSTPTDVVCTheoThang;
    private EditText edtTKSTPTDVVCthongketheongay;
    private ArrayList<String> mKey = new ArrayList<>();
    private TextView tvTongTienPhaiTraDVVC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chu_cua_hang_____man_hinh_thong_ke_tien_tra_d_v_v_c);
        setControl();
        setEvent();
    }

    private void setEvent() {
        // Định dạng số
        NumberFormat currentLocale = NumberFormat.getInstance();
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        // Biến FireBase
        DatabaseReference databill = FirebaseDatabase.getInstance().getReference();
        //Biến Context
        context = this;
        // đổ lữ liệu cho spinner
        String[] dataspinnerThang = {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
        };
        String[] dataspinnerNam = {
                "2019", "2020", "2021"
        };
        //Gán Dữ liệu vào Adapter
        ArrayAdapter<String> adapterThang = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, dataspinnerThang);
        ArrayAdapter<String> adapterNam = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, dataspinnerNam);
        //set adapter spinner
        adapterNam.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        adapterThang.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnSTPTDVVCthang.setAdapter(adapterThang);
        spnSTPTDVVCnam.setAdapter(adapterNam);
        spnSTPTDVVCthang.setEnabled(false);
        spnSTPTDVVCnam.setEnabled(false);

        //set dữ liệu cho ListView Thống Kê và Dữ Liệu Cho Adapter
        listTienPhaiTraDVVC = new ArrayList<>();
        TienTraDVVCAdapter tienTraDVVCAdapter = new TienTraDVVCAdapter(context, R.layout.item_adapter_tientradonvivanchuyen, listTienPhaiTraDVVC);
        lvThongKeSTPTDVVC.setAdapter(tienTraDVVCAdapter);
          /*

        Xử Lý Chọn Phương Thức Thống Kê (Thống Kê Theo Ngày/ Thống Kê Theo Tháng)

         */
        rdbTKSTPTDVVCTheoNgay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rdbTKSTPTDVVCTheoNgay.isChecked() == true) {
                    edtTKSTPTDVVCthongketheongay.setEnabled(true);
                } else {
                    edtTKSTPTDVVCthongketheongay.setEnabled(false);
                }
            }
        });
        rdbTKSTPTDVVCTheoThang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rdbTKSTPTDVVCTheoThang.isChecked() == true) {
                    spnSTPTDVVCnam.setEnabled(true);
                    spnSTPTDVVCthang.setEnabled(true);
                } else {
                    spnSTPTDVVCthang.setEnabled(false);
                    spnSTPTDVVCnam.setEnabled(false);
                }
            }
        });

         /*

        Xử Lý Sự Kiện Cho Button Thống Kê

         */

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdbTKSTPTDVVCTheoNgay.isChecked() == true || rdbTKSTPTDVVCTheoThang.isChecked() == true) {
                    if (rdbTKSTPTDVVCTheoNgay.isChecked() == true) {
                        if (edtTKSTPTDVVCthongketheongay.getText().toString().trim().equals("") == false) {
                            listTienPhaiTraDVVC.clear();
                            tienTraDVVCAdapter.notifyDataSetChanged();
                            mKey.clear();
                            databill.child("bills").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                    if (snapshot.getValue(Bill.class).getDate().contains(edtTKSTPTDVVCthongketheongay.getText()) == true) {
                                        if (snapshot.getValue(Bill.class).getStatus() == 7) {
                                            String MaDH = snapshot.getValue(Bill.class).getId();
                                            double tongGiaTriDonHang = snapshot.getValue(Bill.class).getTotalMoney() - snapshot.getValue(Bill.class).getDiscount();
                                            Double tienPhaiTra = tongGiaTriDonHang / 10;
                                            listTienPhaiTraDVVC.add(new TienTraDVVC(MaDH, tongGiaTriDonHang, tienPhaiTra));
                                            tienTraDVVCAdapter.notifyDataSetChanged();
                                            //
                                            String key = snapshot.getKey();
                                            mKey.add(key);
                                            tvTongTienPhaiTraDVVC.setText("Tổng Số Tiền Phải Trả Là: " + tongTienPhaiTraDVVC(listTienPhaiTraDVVC));
                                        }
                                    }
                                }

                                @Override
                                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    // lấy địa chỉ id của đối tượng vừa bị thay đổi bên trong mảng mkey
                                    String key = snapshot.getKey();
                                    int index = mKey.indexOf(key);
                                    // thay đổi dữ liệu trong gridview giống với dữ liệu trên firebase
                                    if (snapshot.getValue(Bill.class).getStatus() == 7 && snapshot.getValue(Bill.class).getDate().contains(edtTKSTPTDVVCthongketheongay.getText()) == true
                                            && mKey.contains(snapshot.getKey()) == false) {
                                        String MaDH = snapshot.getValue(Bill.class).getId();
                                        double tongGiaTriDonHang = snapshot.getValue(Bill.class).getTotalMoney() - snapshot.getValue(Bill.class).getDiscount();
                                        Double tienPhaiTra = tongGiaTriDonHang / 10;
                                        TienTraDVVC tienTraDVVC = new TienTraDVVC(MaDH, tongGiaTriDonHang, tienPhaiTra);

                                        listTienPhaiTraDVVC.add(tienTraDVVC);
                                        mKey.add(snapshot.getKey());
                                    } else if (snapshot.getValue(Bill.class).getStatus() == 7 && snapshot.getValue(Bill.class).getDate().contains(edtTKSTPTDVVCthongketheongay.getText()) == true
                                            && mKey.contains(snapshot.getKey()) == true) {
                                        String MaDH = snapshot.getValue(Bill.class).getId();
                                        double tongGiaTriDonHang = snapshot.getValue(Bill.class).getTotalMoney() - snapshot.getValue(Bill.class).getDiscount();
                                        Double tienPhaiTra = tongGiaTriDonHang / 10;
                                        TienTraDVVC tienTraDVVC = new TienTraDVVC(MaDH, tongGiaTriDonHang, tienPhaiTra);
                                        listTienPhaiTraDVVC.set(index, tienTraDVVC);

                                    } else if (snapshot.getValue(Bill.class).getStatus() != 7 && snapshot.getValue(Bill.class).getDate().contains(edtTKSTPTDVVCthongketheongay.getText()) == false && mKey.contains(snapshot.getKey()) == true ||
                                            snapshot.getValue(Bill.class).getStatus() == 7 && snapshot.getValue(Bill.class).getDate().contains(edtTKSTPTDVVCthongketheongay.getText()) == false && mKey.contains(snapshot.getKey()) == true ||
                                            snapshot.getValue(Bill.class).getStatus() != 7 && snapshot.getValue(Bill.class).getDate().contains(edtTKSTPTDVVCthongketheongay.getText()) == true && mKey.contains(snapshot.getKey()) == true) {
                                        listTienPhaiTraDVVC.remove(index);
                                        mKey.remove(key);
                                    }
                                    tienTraDVVCAdapter.notifyDataSetChanged();
                                    tvTongTienPhaiTraDVVC.setText("Tổng Số Tiền Phải Trả Là: " + tongTienPhaiTraDVVC(listTienPhaiTraDVVC));
                                    Toast.makeText(context, "Đã có sự thay đổi dữ liệu từ hệ thống", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                                    String key = snapshot.getKey();
                                    int index = mKey.indexOf(key);

                                    listTienPhaiTraDVVC.remove(index);
                                    tienTraDVVCAdapter.notifyDataSetChanged();
                                    tvTongTienPhaiTraDVVC.setText("Tổng Số Tiền Phải Trả Là: " + tongTienPhaiTraDVVC(listTienPhaiTraDVVC));
                                }

                                @Override
                                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            listTienPhaiTraDVVC.clear();
                            tienTraDVVCAdapter.notifyDataSetChanged();
                            Toast.makeText(context, "Vui Lòng Nhập Ngày Để Thống Kê", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (rdbTKSTPTDVVCTheoThang.isChecked() == true) {
                        String time;
                        listTienPhaiTraDVVC.clear();
                        tienTraDVVCAdapter.notifyDataSetChanged();
                        mKey.clear();
                        if (Integer.parseInt(spnSTPTDVVCthang.getSelectedItem().toString()) < 10) {
                            time = spnSTPTDVVCnam.getSelectedItem().toString() + "/" + "0" + spnSTPTDVVCthang.getSelectedItem().toString();
                        } else {
                            time = spnSTPTDVVCnam.getSelectedItem().toString() + "/" + spnSTPTDVVCthang.getSelectedItem().toString();
                        }
                        databill.child("bills").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                if (snapshot.getValue(Bill.class).getDate().contains(time) == true) {
                                    if (snapshot.getValue(Bill.class).getStatus() == 7) {
                                        String MaDH = snapshot.getValue(Bill.class).getId();
                                        double tongGiaTriDonHang = snapshot.getValue(Bill.class).getTotalMoney() - snapshot.getValue(Bill.class).getDiscount();
                                        Double tienPhaiTra = tongGiaTriDonHang / 10;
                                        listTienPhaiTraDVVC.add(new TienTraDVVC(MaDH, tongGiaTriDonHang, tienPhaiTra));
                                        tienTraDVVCAdapter.notifyDataSetChanged();
                                        //
                                        String key = snapshot.getKey();
                                        mKey.add(key);
                                        tvTongTienPhaiTraDVVC.setText("Tổng Số Tiền Phải Trả Là: " + tongTienPhaiTraDVVC(listTienPhaiTraDVVC));
                                    }
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                // lấy địa chỉ id của đối tượng vừa bị thay đổi bên trong mảng mkey
                                String key = snapshot.getKey();
                                int index = mKey.indexOf(key);
                                // thay đổi dữ liệu trong gridview giống với dữ liệu trên firebase
                                if (snapshot.getValue(Bill.class).getStatus() == 7 && snapshot.getValue(Bill.class).getDate().contains(time) == true
                                        && mKey.contains(snapshot.getKey()) == false) {
                                    String MaDH = snapshot.getValue(Bill.class).getId();
                                    double tongGiaTriDonHang = snapshot.getValue(Bill.class).getTotalMoney() - snapshot.getValue(Bill.class).getDiscount();
                                    Double tienPhaiTra = tongGiaTriDonHang / 10;
                                    TienTraDVVC tienTraDVVC = new TienTraDVVC(MaDH, tongGiaTriDonHang, tienPhaiTra);
                                    listTienPhaiTraDVVC.add(tienTraDVVC);
                                    mKey.add(snapshot.getKey());

                                } else if (snapshot.getValue(Bill.class).getStatus() == 7 && snapshot.getValue(Bill.class).getDate().contains(time) == true
                                        && mKey.contains(snapshot.getKey()) == true) {
                                    String MaDH = snapshot.getValue(Bill.class).getId();
                                    double tongGiaTriDonHang = snapshot.getValue(Bill.class).getTotalMoney() - snapshot.getValue(Bill.class).getDiscount();
                                    Double tienPhaiTra = tongGiaTriDonHang / 10;
                                    TienTraDVVC tienTraDVVC = new TienTraDVVC(MaDH, tongGiaTriDonHang, tienPhaiTra);
                                    listTienPhaiTraDVVC.set(index, tienTraDVVC);
                                } else if (snapshot.getValue(Bill.class).getStatus() != 7 && snapshot.getValue(Bill.class).getDate().contains(edtTKSTPTDVVCthongketheongay.getText()) == false && mKey.contains(snapshot.getKey()) == true ||
                                        snapshot.getValue(Bill.class).getStatus() == 7 && snapshot.getValue(Bill.class).getDate().contains(edtTKSTPTDVVCthongketheongay.getText()) == false && mKey.contains(snapshot.getKey()) == true ||
                                        snapshot.getValue(Bill.class).getStatus() != 7 && snapshot.getValue(Bill.class).getDate().contains(edtTKSTPTDVVCthongketheongay.getText()) == true && mKey.contains(snapshot.getKey()) == true) {
                                    listTienPhaiTraDVVC.remove(index);
                                    mKey.remove(key);
                                }
                                tienTraDVVCAdapter.notifyDataSetChanged();
                                tvTongTienPhaiTraDVVC.setText("Tổng Số Tiền Phải Trả Là: " + tongTienPhaiTraDVVC(listTienPhaiTraDVVC));
                                Toast.makeText(context, "Đã có sự thay đổi dữ liệu từ hệ thống", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                                String key = snapshot.getKey();
                                int index = mKey.indexOf(key);
                                listTienPhaiTraDVVC.remove(index);
                                tienTraDVVCAdapter.notifyDataSetChanged();
                                tvTongTienPhaiTraDVVC.setText("Tổng Số Tiền Phải Trả Là: " + tongTienPhaiTraDVVC(listTienPhaiTraDVVC));
                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                } else {
                    Toast.makeText(context, "Vui Lòng Chọn Phương Thức Để Thống Kê.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String tongTienPhaiTraDVVC(ArrayList<TienTraDVVC> tienTraDVVC) {
        // Định dạng số
        NumberFormat currentLocale = NumberFormat.getInstance();
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        double tongTien = 0;
        for (int i = 0; i < tienTraDVVC.size(); i++) {
            tongTien += tienTraDVVC.get(i).getSoTienPhaiTra();
        }
        return en.format(tongTien) + "VNĐ";
    }

    private void setControl() {
        spnSTPTDVVCnam = findViewById(R.id.spnTKSTPTDVVCnam);
        spnSTPTDVVCthang = findViewById(R.id.spnTKSTPTDVVCthang);
        lvThongKeSTPTDVVC = findViewById(R.id.lvthongkesotienphaitraDVVC);
        btnThongKe = findViewById(R.id.btnTKSTPTDVVCThongKe);
        rdbTKSTPTDVVCTheoNgay = findViewById(R.id.rdbTKSTPTDVVCthongketheongay);
        rdbTKSTPTDVVCTheoThang = findViewById(R.id.rdbTKSTPTDVVCthongketheothang);
        edtTKSTPTDVVCthongketheongay = findViewById(R.id.edtTKSTPTDVVCthongketheongay);
        tvTongTienPhaiTraDVVC = findViewById(R.id.tvTongSoTienPhaiTraDVVC);
    }
}