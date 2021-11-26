package com.example.book.QuanLy.manage;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.Button;import android.widget.ImageView;import androidx.appcompat.app.AppCompatActivity;import com.example.book.ChuCuaHang.ChangePassChuCuaHang;import com.example.book.R;import com.google.firebase.auth.FirebaseAuth;public class ManageActivity extends AppCompatActivity {    ImageView imgUser, imgEmployee, imgBlacklist, imgBlock, imgMessage;Button btnLogout;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.manage_layout);        setTitle("Quản lý");      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);        //Mapping        Mapping();        //Event        imgUser.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Intent intent = new Intent(ManageActivity.this, UserActivity.class);                startActivity(intent);            }        });        imgEmployee.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Intent intent = new Intent(ManageActivity.this, EmployeeActivity.class);                startActivity(intent);            }        });        imgBlacklist.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Intent intent = new Intent(ManageActivity.this, BlacklistActivity.class);                startActivity(intent);            }        });        imgMessage.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Intent intent = new Intent(ManageActivity.this, MessageActivity.class);                startActivity(intent);            }        });        btnLogout.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                FirebaseAuth.getInstance().signOut();                finish();            }        });        findViewById(R.id.btnChangepass).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                startActivity(new Intent(getApplicationContext(), ChangePassQuanLy.class));            }        });    }    private void Mapping() {        imgUser = findViewById(R.id.imgUser);        imgEmployee = findViewById(R.id.imgEmployee);        imgBlacklist = findViewById(R.id.imgBlacklist);        imgMessage = findViewById(R.id.imgMessage);        btnLogout = findViewById(R.id.btnLogout);    }}