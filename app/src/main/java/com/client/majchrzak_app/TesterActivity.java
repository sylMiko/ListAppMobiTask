package com.client.majchrzak_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TesterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private String key = "";
    private String statustester = "";
    private String opistester = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);

        recyclerView = findViewById(R.id.recyclerviewtester);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        reference = FirebaseDatabase.getInstance().getReference().child("zadania");
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

                if (model.getStatus().equals("Nieaktywny")) {
                    holder.rootview.setLayoutParams(holder.params);
                } else {


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
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public LinearLayout.LayoutParams params;
        public CardView rootview;
        View myView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myView = itemView;
            params = new LinearLayout.LayoutParams(0, 0);
            rootview = itemView.findViewById(R.id.cardview);
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

    private void updateTask() {
        AlertDialog.Builder alertsec = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.status_tester, null);
        alertsec.setView(view);

        AlertDialog dialog = alertsec.create();

        Button aktualizuj = view.findViewById(R.id.aktualizujbutton);


        EditText opistestera = view.findViewById(R.id.opistestered);
        aktualizuj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                reference.child(key).child("statustester").setValue(statustester).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(TesterActivity.this, "Dodawanie udalo sie", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        } else {
                            Toast.makeText(TesterActivity.this, "Dodawanie nie udalo sie", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    }
                });


                String sopistestera = opistestera.getText().toString().trim();
                System.out.println(sopistestera);
                reference.child(key).child("opistester").setValue(sopistestera).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(TesterActivity.this, "Dodawanie udalo sie", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        } else {
                            Toast.makeText(TesterActivity.this, "Dodawanie nie udalo sie", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    }
                });

            }
        });

        dialog.show();

    }

    public void onRadioButtonClicked(View widok) {
        boolean checked = ((RadioButton) widok).isChecked();
        switch (widok.getId()) {
            case R.id.radiorozpocznij:
                if (checked)
                    statustester = "RozpocznijTester";
                break;
            case R.id.radiozakoncz:
                if (checked)
                    statustester = "ZakończTester";
                break;
            case R.id.radioblad:
                if (checked)
                    statustester = "BłądTester";
                break;
        }
    }
}