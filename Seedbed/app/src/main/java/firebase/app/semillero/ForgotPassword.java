package firebase.app.semillero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    EditText insertNewPassword, editTextEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth = FirebaseAuth.getInstance();
        insertNewPassword = (EditText)findViewById(R.id.insertNewPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        findViewById(R.id.sendNewPassword).setOnClickListener(this);
        findViewById(R.id.TextViewLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.sendNewPassword:
                userPassword();
                break;
            case R.id.TextViewLogin:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void userPassword() {
        String email=editTextEmail.getText().toString().trim();
        String password=insertNewPassword.getText().toString().trim();
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            return;
        }
        if(password.isEmpty()){
            insertNewPassword.setError("Password is required");
            insertNewPassword.requestFocus();
            return;
        }
        if (password.length()<6) {
            insertNewPassword.setError("Length of password should be 6 or more");
            insertNewPassword.requestFocus();
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
if (task.isSuccessful()){
    Toast.makeText(ForgotPassword.this,"New Password set to your email",Toast.LENGTH_LONG).show();
}else{
    Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
}
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
