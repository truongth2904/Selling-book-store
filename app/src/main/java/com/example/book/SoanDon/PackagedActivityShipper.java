package com.example.book.SoanDon;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.AdapterView;import android.widget.ArrayAdapter;import android.widget.ListView;import androidx.annotation.NonNull;import androidx.annotation.Nullable;import androidx.appcompat.app.AppCompatActivity;import com.example.book.R;import com.example.book.SoanDon.models.Shipper;import com.example.book.XuLyHD.DonHangChoXuLy.Bill;import com.google.firebase.database.ChildEventListener;import com.google.firebase.database.DataSnapshot;import com.google.firebase.database.DatabaseError;import com.google.firebase.database.DatabaseReference;import com.google.firebase.database.FirebaseDatabase;import java.util.ArrayList;public class PackagedActivityShipper extends AppCompatActivity {    ArrayList<String> list = new ArrayList<>();    ArrayAdapter<String> adapter;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.packager_packaged_layout);        ListView listView = findViewById(R.id.listPackaged);        setTitle("Chọn shipper");        adapter = new ArrayAdapter<String>(this, R.layout.packager_unpacked_items, R.id.txtDonhang, list);        listView.setAdapter(adapter);        getDataInDatabase();        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {            @Override            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {                String id_bill = getIntent().getStringExtra("id_bill");                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();                mDatabase.child("bills").child(id_bill).child("shipper").setValue(list.get(position).split(" ")[1]);                mDatabase.child("bills").child(id_bill).child("status").setValue(3);                onBackPressed();            }        });    }    private void getDataInDatabase() {        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();        mDatabase.child("shipper").addChildEventListener(new ChildEventListener() {            @Override            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {                Shipper shipper = snapshot.getValue(Shipper.class);                list.add(shipper.getName() + " " + shipper.getId());                adapter.notifyDataSetChanged();            }            @Override            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {            }            @Override            public void onChildRemoved(@NonNull DataSnapshot snapshot) {            }            @Override            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {            }            @Override            public void onCancelled(@NonNull DatabaseError error) {            }        });    }}