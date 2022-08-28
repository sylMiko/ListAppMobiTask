package com.client.majchrzak_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KoordynatorActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private FloatingActionButton floatingactionbutton;

    private DatabaseReference reference;


    private String tid = "";
    private String task, description;
    private String status = "Nieaktywne";
    private String key = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koordynator);

        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        reference = FirebaseDatabase.getInstance().getReference().child("zadania");

        floatingactionbutton = findViewById(R.id.fab);



        floatingactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dodajZadanie();

            }
        });

        //toolbar = findViewById(R.id.activity_home);
        //recyclerView = findViewById(R.id.tasksRecyclerView);

    }



    private void dodajZadanie() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.new_task, null);
        alert.setView(view);

        AlertDialog dialog = alert.create();
        dialog.setCancelable(false);

        Button zapisz = view.findViewById(R.id.savebtn);
        Button cofnij = view.findViewById(R.id.cancelbtn);


        EditText opis = view.findViewById(R.id.opis);
        EditText zadanie = view.findViewById(R.id.zadanie);
        EditText nazwisko = view.findViewById(R.id.nazwisko);





        zapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sstatus = status.trim();
                String szadanie = zadanie.getText().toString().trim();
                String sopis = opis.getText().toString().trim();
                String snazwisko = nazwisko.getText().toString().trim();
                String sopistester = "";
                String sstatustester = "";
                String sstatusdeweloper = "";

                String id = reference.push().getKey();

                Model model = new Model(id, szadanie, sopis, sstatus, snazwisko, sopistester, sstatustester, sstatusdeweloper);
                reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(KoordynatorActivity.this, "Dodawanie udalo sie", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            status = "Nieaktywny";
                        } else {
                            Toast.makeText(KoordynatorActivity.this, "Dodawanie nie udalo sie", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }

        });
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(reference, Model.class).build();

        FirebaseRecyclerAdapter<Model, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Model, MyViewHolder>(options) {

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_lay, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull Model model) {
                holder.setZadanie(model.getZadanie());
                holder.setOpis(model.getOpis());
                holder.setNazwisko(model.getNazwisko());
                holder.setStatus(model.getStatus());
                holder.setStatustester(model.getStatustester());
                holder.setStatusdev(model.getStatusdeweloper());

                
                holder.myView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        key = getRef(position).getKey();

                        updateTask();

                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View myView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myView = itemView;
        }

        public void setZadanie(String zadanie) {
            TextView tviewzadanie = myView.findViewById(R.id.tvzadanie);
            tviewzadanie.setText(zadanie);
        }

        public void setOpis(String opis) {
            TextView tviewopis = myView.findViewById(R.id.tvopis);
            tviewopis.setText(opis);
        }

        public void setNazwisko(String opis) {
            TextView tviewnazwisko = myView.findViewById(R.id.tvnazwisko);
            tviewnazwisko.setText(opis);
        }

        public void setStatus(String opis) {
            TextView tviewstatus = myView.findViewById(R.id.tvstatus);
            if(opis.equals("Aktywny")){
                tviewstatus.setTextColor(Color.GREEN);
            }
            else {
                tviewstatus.setTextColor(Color.RED);
            }
            tviewstatus.setText(opis);
        }

        public void setStatustester(String opis) {
            TextView tviewstatustester = myView.findViewById(R.id.tvstatustest);
            tviewstatustester.setText(opis);
        }

        public void setStatusdev(String opis) {
            TextView tviewstatusdev = myView.findViewById(R.id.tvstatusdev);
            tviewstatusdev.setText(opis);
        }

    }

    public void onRadioButtonClicked(View widok) {
        boolean checked = ((RadioButton) widok).isChecked();
        switch (widok.getId()) {
            case R.id.statusradio:
                if(checked)
                    status = "Aktywny";
                break;
            case R.id.editstatusradio:
                if(checked)
                    status = "Aktywny";
                break;
            case R.id.editstatusradionieakty:
                if(checked)
                    status = "Nieaktywny";
                break;
            case R.id.statusradionieakty:
                if(checked)
                    status = "Nieaktywny";
                break;
        }

        //if (checked) status = "Aktywne";
        //else status = "Nieaktywne";
    }

    private void updateTask() {
        AlertDialog.Builder alertsec = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.update_delete, null);
        alertsec.setView(view);

        AlertDialog dialog = alertsec.create();

        Button save = view.findViewById(R.id.savebtn);
        Button delete = view.findViewById(R.id.deletebtn);

        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                reference.child(key).child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(KoordynatorActivity.this, "Dodawanie udalo sie", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            status = "Nieaktywny";
                        }
                        else{
                            Toast.makeText(KoordynatorActivity.this, "Dodawanie nie udalo sie", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            status = "Nieaktywny";
                        }
                    }
                });

            }
        });

        delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                reference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(KoordynatorActivity.this, "Usuwanie udalo sie", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(KoordynatorActivity.this, "Usuwanie nie udalo sie", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });

            }
        });

        dialog.show();
    }
}