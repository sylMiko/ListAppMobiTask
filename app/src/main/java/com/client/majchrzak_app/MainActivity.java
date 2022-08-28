package com.client.majchrzak_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText email = findViewById(R.id.et_mail);
        EditText password = findViewById(R.id.et_password);
        Button login = findViewById(R.id.btn_login);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email3 = email.getText().toString().trim();
                String haslo3 = password.getText().toString().trim();

                if (!isEmptybool(email3, haslo3)) {
                    Toast.makeText(MainActivity.this, "Podaj wszystkie dane!", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email3, haslo3).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Logowanie udane", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                assert user != null;
                                String userID = user.getUid();
                                if(userID.equals("BoQMqeuXSyZjVmmh6HBPBeY5fFa2")) {
                                    startActivity(new Intent(MainActivity.this, KoordynatorActivity.class));
                                }
                                else if(userID.equals("2Kk8UNCjdPeVKoODkD4Ut0ma5Rg2")){
                                    startActivity(new Intent(MainActivity.this, TesterActivity.class));
                                }
                                else {
                                    startActivity(new Intent(MainActivity.this, DeweloperActivity.class));
                                }
                                } else {

                                Toast.makeText(MainActivity.this, "Logowanie nieudane", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
    boolean isEmptybool(String a, String b) {
        return !a.isEmpty() && !b.isEmpty();
    }

}