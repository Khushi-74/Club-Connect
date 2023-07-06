package com.example.clubconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        mAuth = FirebaseAuth.getInstance();
        Button registerButton = findViewById(R.id.buttonRegister);
           Button register_login = findViewById(R.id.Register_loginButton);
           register_login.setOnClickListener(v -> {
               Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
               startActivity(intent);
           });
        registerButton.setOnClickListener(v -> {
            EditText editTextUsername = findViewById(R.id.editTextUsername);
            EditText editTextEmail = findViewById(R.id.editTextEmail);
            EditText editTextPassword = findViewById(R.id.editTextPassword);
            Spinner spinner = findViewById(R.id.user_type_spinner);
            editTextUsername.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, task -> {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        CollectionReference usersRef = db.collection("users");

                        String name = editTextUsername.getText().toString();


                        String userType = spinner.getSelectedItem().toString();

                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("email", email);
                        user.put("userType", userType);

                        usersRef
                                .add(user)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("Firestore", "Data saved successfully");
                                    Toast.makeText(this, "firestore data saved", Toast.LENGTH_SHORT).show();
                                    editTextUsername.setText(""); // Clear username field
                                    editTextEmail.setText(""); // Clear email field
                                    editTextPassword.setText(""); // Clear password field
                                    // Handle successful data save, such as displaying a success message
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "\"Firestore\", \"Error saving data: \" + e.getMessage()", Toast.LENGTH_SHORT).show();
                                    Log.e("Firestore", "Error saving data: " + e.getMessage());
                                    // Handle failed data save, such as displaying an error message
                                });

                        if (task.isSuccessful()) {
                            // User registration successful
                            mAuth.getCurrentUser();
                            // Proceed with any additional steps or UI changes
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                            startActivity(intent);

                        } else {
                            // User registration failed
                            String errorMessage = Objects.requireNonNull(task.getException()).getMessage();
                            Toast.makeText(RegisterActivity.this, "Registration failed: " + errorMessage,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });


    }
}
