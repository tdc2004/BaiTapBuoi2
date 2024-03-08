package com.chinhdev.lab1;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Signout extends AppCompatActivity {
    FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private CityAdapter cityAdapter;
    private List<Map<String, Object>> list;
    FloatingActionButton btn_add;
    Button btn_them, btn_huy;
    TextInputEditText txt_name,txt_country,txt_popu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signout);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(v -> {
            View view = LayoutInflater.from(this).inflate(R.layout.item_add,null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();
            btn_them = view.findViewById(R.id.btn_them);
            btn_huy=view.findViewById(R.id.btn_huy);
            txt_name = view.findViewById(R.id.edt_city);
            txt_country = view.findViewById(R.id.edt_country);
            txt_popu = view.findViewById(R.id.edt_populationk);
            txt_name = view.findViewById(R.id.edt_city);
            btn_them.setOnClickListener(v1 -> {
                String cityName = txt_name.getText().toString();
                String countryName = txt_country.getText().toString();
                String population = txt_popu.getText().toString();
                if (!cityName.isEmpty() && !countryName.isEmpty() && !population.isEmpty()) {
                    CollectionReference cities = firestore.collection("cities");
                    Map<String, Object> newCity = new HashMap<>();
                    newCity.put("name", cityName);
                    newCity.put("country", countryName);
                    newCity.put("population", Integer.parseInt(population));
                    cities.add(newCity).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                alertDialog.dismiss();
                                docDulieu();
                                Toast.makeText(Signout.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "Error adding document", task.getException());
                            }

                        }
                    });
                }else {
                    Toast.makeText(Signout.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            });
            btn_huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();

                }
            });
            alertDialog.show();

        });

        list = new ArrayList<>();

        // Khởi tạo Adapter và gán cho RecyclerView
        cityAdapter = new CityAdapter(list);
        recyclerView.setAdapter(cityAdapter);

        ghiDulieu();

        docDulieu();
        findViewById(R.id.btnlogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signout.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void ghiDulieu() {
        CollectionReference cities = firestore.collection("cities");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "San Francisco");
        data1.put("country", "USA");
        data1.put("population", 860000);
        cities.document("SF").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "Los Angeles");
        data2.put("country", "USA");
        data2.put("population", 3900000);
        cities.document("LA").set(data2);
        Map<String, Object> data3 = new HashMap<>();
        data3.put("name", "Washington D.C.");
        data3.put("country", "USA");
        data3.put("population", 680000);
        cities.document("DC").set(data3);

        Map<String, Object> data4 = new HashMap<>();
        data4.put("name", "Tokyo");
        data4.put("country", "Japan");
        data4.put("population", 9000000);
        cities.document("TOK").set(data4);

        Map<String, Object> data5 = new HashMap<>();
        data5.put("name", "Beijing");
        data5.put("country", "China");
        data5.put("population", 21500000);
        cities.document("BJ").set(data5);


    }

    private void docDulieu () {
        firestore.collection("cities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list.clear(); // Xóa dữ liệu cũ
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> cityData = document.getData();
                                list.add(cityData);
                            }
                            cityAdapter.notifyDataSetChanged(); // Cập nhật dữ liệu mới
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}