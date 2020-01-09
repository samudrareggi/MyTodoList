package com.reggisam.mtl;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reggisam.mtl.model.Task;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyListTask";
    private DatabaseReference database;

    private EditText etTitle, etDate, etDesc;
    private ProgressDialog loading;
    private Button btn_cancel, btn_save;

    private String sPid, sPtitle, sPdesc, sPdate;

    TextView titlepage;
    Button btSave, btCanc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlepage = findViewById(R.id.title_page);
        btSave = findViewById(R.id.btSave);
        btCanc = findViewById(R.id.btCanc);
        Typeface Osmane = Typeface.createFromAsset(getAssets(),"font/Osmane.otf");
        titlepage.setTypeface(Osmane);
        btSave.setTypeface(Osmane);
        btCanc.setTypeface(Osmane);

        database = FirebaseDatabase.getInstance().getReference();

        sPid = getIntent().getStringExtra("id");
        sPtitle = getIntent().getStringExtra("title");
        sPdesc = getIntent().getStringExtra("desc");
        sPdate = getIntent().getStringExtra("date");

        etTitle = findViewById(R.id.et_title);
        etDate = findViewById(R.id.et_dat);
        etDesc = findViewById(R.id.et_desc);
        btn_save = findViewById(R.id.btSave);
        btn_cancel = findViewById(R.id.btCanc);

        etTitle.setText(sPtitle);
        etDate.setText(sPdate);
        etDesc.setText(sPdesc);

        if (sPid.equals("")){
            btn_save.setText("Save");
            btn_cancel.setText("Cancel");
        } else {
            btn_save.setText("Update");
            btn_cancel.setText("Delete");
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Stitle = etTitle.getText().toString();
                String Sdate = etDate.getText().toString();
                String Sdesc = etDesc.getText().toString();

                if (btn_save.getText().equals("Save")){
                    // perintah save

                    if (Stitle.equals("")) {
                        etTitle.setError("Harap isi title");
                        etTitle.requestFocus();
                    } else if (Sdesc.equals("")) {
                        etDesc.setError("Harap isi deskripsi");
                        etDesc.requestFocus();
                    } else if (Sdate.equals("")) {
                        etDate.setError("Harap isi timeline");
                        etDate.requestFocus();
                    } else {
                        loading = ProgressDialog.show(MainActivity.this,
                                null,
                                "Please wait...",
                                true,
                                false);

                        submitUser(new Task(
                                Stitle,
                                Sdesc,
                                Sdate));

                    }
                } else {
                    // perintah edit
                    if (Stitle.equals("")) {
                        etTitle.setError("Harap isi title");
                        etTitle.requestFocus();
                    } else if (Sdesc.equals("")) {
                        etDesc.setError("Harap isi deskripsi");
                        etDesc.requestFocus();
                    } else if (Sdate.equals("")) {
                        etDate.setError("Harap isi timeline");
                        etDate.requestFocus();
                    } else {
                        loading = ProgressDialog.show(MainActivity.this,
                                null,
                                "Please wait...",
                                true,
                                false);

                        editUser(new Task(
                                Stitle,
                                Sdesc,
                                Sdate), sPid);
                    }
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_cancel.getText().equals("Cancel")) {
                    //tutup page
                    finish();
                } else {
                    // delete
                    database.child("Task")
                            .child(sPid)
                            .removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(MainActivity.this, "Data berhasil dihapus",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }

            }
        });
    }

    private void submitUser(Task task) {
        database.child("Task")
                .push()
                .setValue(task)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        loading.dismiss();

                        etTitle.setText("");
                        etDesc.setText("");
                        etDate.setText("");

                        Toast.makeText(MainActivity.this,
                                "Data Berhasil ditambahkan",
                                Toast.LENGTH_SHORT).show();

                    }

                });
    }

    private void editUser(Task task, String id) {
        database.child("Task")
                .child(id)
                .setValue(task)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        loading.dismiss();

                        etTitle.setText("");
                        etDesc.setText("");
                        etDate.setText("");

                        Toast.makeText(MainActivity.this,
                                "Data Berhasil diedit",
                                Toast.LENGTH_SHORT).show();

                    }

                });
    }
}
