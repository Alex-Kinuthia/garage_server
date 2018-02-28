package com.example.alex.garage_server;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ResetPasswordActivity extends AppCompatActivity {

        private EditText inputEmail;

       private Button getBtnBack;

        private Button btnReset, btnBack;

        private FirebaseAuth auth;

        private ProgressBar progressBar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_reset_password);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            inputEmail = (EditText) findViewById(R.id.email);

            btnReset = (Button) findViewById(R.id.btn_reset_password);

            btnBack = (Button) findViewById(R.id.btn_back);

            progressBar = (ProgressBar) findViewById(R.id.progressBar);

            auth = FirebaseAuth.getInstance();

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            btnBack = (Button) findViewById(R.id.btn_back);
            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = inputEmail.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    auth.sendPasswordResetEmail(email)

                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    }

                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                    btnBack.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    });
                }
            });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(android.provider.Settings.ACTION_SETTINGS);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}