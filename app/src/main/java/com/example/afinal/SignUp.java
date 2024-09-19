package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.afinal.APIService.APIService;
import com.example.afinal.Constructor.User;
import com.example.afinal.Response.RegisterResponse;
import com.example.afinal.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    EditText regInputUsername;
    EditText regInputEmail;
    EditText regInputPassword;
    Button regRegBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        regInputUsername = findViewById(R.id.signUpUsernameInput);
        regInputEmail  = findViewById(R.id.signUpEmailInput);
        regInputPassword = findViewById(R.id.signUpPasswordInput);
        regRegBtn = findViewById(R.id.signUpButton);

        regRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = regInputUsername.getText().toString().trim();
                String email = regInputEmail.getText().toString().trim();
                String password = regInputPassword.getText().toString();

                User user = new User(username, password,email);

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignUp.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Retrofit Request
                APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
                Call<RegisterResponse> call = apiService.registerUser(user);

                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if(response.isSuccessful()){
                            RegisterResponse registerResponse = response.body();
                            if (registerResponse != null && "success".equals(registerResponse.getStatus())){
                                Toast.makeText(SignUp.this,"Registration Successful",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignUp.this, LogIn.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SignUp.this, "Registration Failed: " + registerResponse.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SignUp.this,"Registration Failed" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable throwable) {
                        Log.e("Signup", "Registration Failed", throwable);
                        Toast.makeText(SignUp.this,"Error" + throwable.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}