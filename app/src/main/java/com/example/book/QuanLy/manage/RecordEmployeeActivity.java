package com.example.book.QuanLy.manage;import android.os.Bundle;import android.widget.ArrayAdapter;import android.widget.Spinner;import androidx.appcompat.app.AppCompatActivity;import com.example.book.R;public class RecordEmployeeActivity extends AppCompatActivity {    String listspinner[] = {"Quản lý","Nhân viên giao hàng","Nhân viên xử lý đơn","Nhân viên đóng gói","Chủ cửa hàng"};    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.manage_record_employee_layout);        setTitle("Hồ sơ nhân viên");        Spinner spinner=findViewById(R.id.spinnerRecordEmployee);        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listspinner);        spinner.setAdapter(spinnerAdapter);    }}