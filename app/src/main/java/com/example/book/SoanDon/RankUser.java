package com.example.book.SoanDon;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.book.R;
import com.example.book.SoanDon.adapter.AdapterRankUser;
import com.example.book.SoanDon.models.Rank;
import com.example.book.SoanDon.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class RankUser extends AppCompatActivity {

    ListView listView;
    Button btnThem, btnXoa, btnSua;
    EditText txtNameRank, txtMoneyRank;

    ArrayList<Rank> list;
    AdapterRankUser adapterRankUser;

    ArrayList<User> listUser;
    ArrayList<String> listUserID;
    Rank rank = new Rank();
    String idRank = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packer_rank_user);
        setControl();
        list = new ArrayList<>();
        listUser = new ArrayList<>();
        listUserID = new ArrayList<>();
        adapterRankUser = new AdapterRankUser(getApplicationContext(), R.layout.packer_rank_item, list);
        listView.setAdapter(adapterRankUser);

        // toolbarr
        Toolbar toolbar = findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // l???y data
        FirebaseDatabase.getInstance().getReference("rank_users")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Rank rankUser = snapshot.getValue(Rank.class);
                        list.add(rankUser);
                        selectRank();
                        adapterRankUser.notifyDataSetChanged();
                        setNameRankUser();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Rank rank = snapshot.getValue(Rank.class);
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getId().equals(rank.getId())) {
                                list.set(i, rank);
                                adapterRankUser.notifyDataSetChanged();
                            }
                        }
                        setNameRankUser();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        Rank rank = snapshot.getValue(Rank.class);
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getId().equals(rank.getId())) {
                                list.remove(i);
                                adapterRankUser.notifyDataSetChanged();

                            }
                        }
                        setNameRankUser();
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // l???y danh s??ch c??c user:
        FirebaseDatabase.getInstance().getReference("users")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        User user = snapshot.getValue(User.class);
                        listUser.add(user);
                        listUserID.add(snapshot.getKey());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        btnThem.setEnabled(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int money = 0;
                String name = "";
                String id = "";
                try {
                    money = Integer.parseInt(txtMoneyRank.getText().toString());
                    name = txtNameRank.getText().toString();
                    id = UUID.randomUUID().toString();
                    // ho??n t???t ki???m tra v?? th??m:
                    if (checkInputText()) {
                        // hi???n dialog l???i:

                    } else {
                        Rank rank_ = new Rank(id, name, money);
                        // Th??m v??o database:
                        FirebaseDatabase.getInstance().getReference("rank_users")
                                .child(rank_.getId()).setValue(rank_);
                        // C???p nh???t l???i rank:
                        Thread.sleep(200);
                        selectRank();
                        setNameRankUser();
                        txtNameRank.setText("");
                        txtMoneyRank.setText("");
                        // hi???n dialog th??m th??nh c??ng

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("rank_users")
                        .child(rank.getId()).removeValue();

                btnThem.setEnabled(true);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                setNameRankUser();
                txtNameRank.setText("");
                txtMoneyRank.setText("");
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtNameRank.getText().toString();
                int money = Integer.parseInt(txtMoneyRank.getText().toString());
                if (name.trim().equals("") || money == 0) {
                    // hi???n l???i


                } else {
                    Rank rank1 = new Rank(idRank, name, money);

                    FirebaseDatabase.getInstance().getReference("rank_users")
                            .child(idRank).setValue(rank1);
                    btnThem.setEnabled(true);
                    btnSua.setEnabled(false);
                    btnXoa.setEnabled(false);
                    setNameRankUser();

                    txtMoneyRank.setText("");
                    txtNameRank.setText("");
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                rank = list.get(i);
                idRank = list.get(i).getId();
                txtNameRank.setText(rank.getName());
                txtMoneyRank.setText(rank.getMoney() + "");

                btnThem.setEnabled(false);
                btnSua.setEnabled(true);
                btnXoa.setEnabled(true);

            }
        });

    }

    public boolean checkInputText() {
        // ki???m tra kh??ng parse ???????c s???
        int money = Integer.parseInt(txtMoneyRank.getText().toString());
        // ki???m tra t??n h???ng d?? c?? v?? s??? ti???n ???? c??:
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(txtNameRank.getText().toString())
                    || money == list.get(i).getMoney()) {
                return true;
            }
        }
        // ki???m tra t??n h???ng r???ng
        if (txtNameRank.getText().toString().trim().equals("")) {
            return true;
        }
        return false;
    }

    public void setNameRankUser() {
        selectRank();
        // S???a l???i rank c???a c??c user:
        for (int i = 0; i < listUser.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (listUser.get(i).getMoneyBuy() >= list.get(j).getMoney()) {
                    FirebaseDatabase.getInstance().getReference("users")
                            .child(listUserID.get(i)).child("rank").setValue(list.get(j).getName());
                }
            }
        }
        // s???a l???i h???ng nh??? nh???t
        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getMoneyBuy() < list.get(0).getMoney()) {
                FirebaseDatabase.getInstance().getReference("users")
                        .child(listUserID.get(i)).child("rank").setValue(list.get(0).getName());
            }
        }
    }

    public void selectRank() {
        Collections.sort(list);
    }

    private void setControl() {
        listView = findViewById(R.id.lvRankUser);
        btnThem = findViewById(R.id.btnThemRank);
        btnXoa = findViewById(R.id.btnXoaRank);
        btnSua = findViewById(R.id.btnSuaRank);
        txtNameRank = findViewById(R.id.txtNameRank);
        txtMoneyRank = findViewById(R.id.txtMoneyRank);
    }
}