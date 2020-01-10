package com.reggisam.mtl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reggisam.mtl.adapter.RequestAdapterRecyclerView;
import com.reggisam.mtl.map.MapActivity;
import com.reggisam.mtl.model.Task;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private DatabaseReference database;
    private FirebaseAuth auth;

    private ArrayList<Task> daftarTask;
    private RequestAdapterRecyclerView requestAdapterRecyclerView;

    private RecyclerView rc_list_request;
    private ProgressDialog loading;

    FloatingActionButton fab_edit, fab_addd, fab_map, fab_img;
    Animation fabOpen, fabClose, rotateFw, rotateRv;
    boolean isOpen = false;

    TextView titlepage, subtitle, endtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        titlepage = findViewById(R.id.title_page);
        subtitle = findViewById(R.id.subtitle_page);
        endtitle = findViewById(R.id.endpage);

        database = FirebaseDatabase.getInstance().getReference();

        rc_list_request = findViewById(R.id.rc_list_request);

        fab_edit = findViewById(R.id.fab_edit);
        fab_addd = findViewById(R.id.fab_addd);
        fab_map = findViewById(R.id.fab_map);
        fab_img = findViewById(R.id.fab_img);
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateFw = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateRv = AnimationUtils.loadAnimation(this, R.anim.rotate_reverse);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_list_request.setLayoutManager(mLayoutManager);
        rc_list_request.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(ListActivity.this,
                null,
                "Please wait...",
                true,
                false);

        Typeface Osmane = Typeface.createFromAsset(getAssets(),"font/Osmane.otf");
        titlepage.setTypeface(Osmane);
        subtitle.setTypeface(Osmane);
        endtitle.setTypeface(Osmane);

        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });

        fab_addd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();

                startActivity(new Intent(ListActivity.this, MainActivity.class)
                        .putExtra("id", "")
                        .putExtra("title", "")
                        .putExtra("desc", "")
                        .putExtra("date", ""));
            }
        });

        fab_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                startActivity(new Intent(ListActivity.this, MapActivity.class));
            }
        });

        fab_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                startActivity(new Intent(ListActivity.this, ImageActivity.class));
            }
        });

        database.child("Task").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                daftarTask = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam DATA
                     * Dan juga menyimpan primary key pada DATA
                     * untuk keperluan Edit dan Delete data
                     */
                    Task task = noteDataSnapshot.getValue(Task.class);
                    task.setKey(noteDataSnapshot.getKey());

                    /**
                     * Menambahkan DATA yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    daftarTask.add(task);
                }

                /**
                 * Inisialisasi adapter dan data hotel dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                requestAdapterRecyclerView = new RequestAdapterRecyclerView(daftarTask, ListActivity.this);
                rc_list_request.setAdapter(requestAdapterRecyclerView);
                loading.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
                loading.dismiss();
            }
        });
    }

    private void animateFab(){
        if (isOpen)
        {
            fab_edit.startAnimation(rotateFw);
            fab_addd.startAnimation(fabClose);
            fab_map.startAnimation(fabClose);
            fab_img.startAnimation(fabClose);
            fab_addd.setClickable(false);
            fab_map.setClickable(false);
            isOpen=false;
        } else{
            fab_edit.startAnimation(rotateRv);
            fab_addd.startAnimation(fabOpen);
            fab_map.startAnimation(fabOpen);
            fab_img.startAnimation(fabOpen);
            fab_addd.setClickable(true);
            fab_map.setClickable(true);
            isOpen=true;
        }
    }
}
