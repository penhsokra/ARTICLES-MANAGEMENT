package camdev.sokra.topnews.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import camdev.sokra.topnews.R;

public class LoginActivity extends AppCompatActivity {
    private EditText edName,edPassword;
    Button btnLogiin;
    private String name="sokra";
    private String password="123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edName = findViewById(R.id.edName);
        edPassword = findViewById(R.id.edPassword);
        btnLogiin = findViewById(R.id.btnLogin);

        btnLogiin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edName.getText().toString().equals(name) && edPassword.getText().toString().equals(password)){
                    SharedPreferences shf = getSharedPreferences("Login_SharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shf.edit();
                    editor.putString("LOGIN", "LOGIN");
                    editor.commit();
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
