package com.example.clubconnect;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public void fetchUserTypeFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();

        CollectionReference usersCollection = db.collection("users");
        Query query = usersCollection.whereEqualTo("email", email);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                    String userType = documentSnapshot.getString("userType");
                    if (userType != null) {
                        // Perform your logic based on the user type
                        if (userType.equals("Admin")) {
                            Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
                            startActivity(adminIntent);
                        } else if (userType.equals("User")) {
                            Intent userIntent = new Intent(MainActivity.this, userActions.class);
                            startActivity(userIntent);
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "User document does not exist", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch user document: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore.setLoggingEnabled(true);


        mAuth = FirebaseAuth.getInstance();
         Button forgotPasswordButton =findViewById(R.id.forgot_password);
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            EditText editTextEmail = findViewById(R.id.email_edittext);
            EditText editTextPassword = findViewById(R.id.password_edittext);

            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(MainActivity.this, task -> {
                        if (email.isEmpty() || password.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        }
                        else{
                        if (task.isSuccessful()) {
                            // User login successful

                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                            fetchUserTypeFromFirestore();
                            // Navigate to home activity

                        } else {
                            // User login failed
                            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }});
        });
forgotPasswordButton.setOnClickListener(v -> {
    Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
    startActivity(intent);
});
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> {
            // Create an intent to open the RegisterActivity
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }


    }


