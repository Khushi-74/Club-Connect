package com.example.clubconnect;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

    public class AdminActivity extends AppCompatActivity {

        private FirebaseFirestore firestore;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.adminpage);

            firestore = FirebaseFirestore.getInstance();

            Button btnCreateClub = findViewById(R.id.btnCreateClub);
            Button btnpostevent = findViewById(R.id.btnPostEvent);
            btnCreateClub.setOnClickListener(v -> showCreateClubDialog());

            btnpostevent.setOnClickListener(v -> {
                Intent intent = new Intent(AdminActivity.this,postImage.class);
                startActivity(intent);
            });

            ImageButton btn = findViewById(R.id.logoutadminbtn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ins = new Intent(AdminActivity.this,MainActivity.class);
                    startActivity(ins);
                }
            });
        }

        private void showCreateClubDialog() {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_create_club);
            dialog.setTitle("Create Club");

            EditText etClubName = dialog.findViewById(R.id.etClubName);
            EditText etLeader = dialog.findViewById(R.id.etLeader);
            EditText etDescription = dialog.findViewById(R.id.etDescription);
            Button btnSave = dialog.findViewById(R.id.btnSave);


            btnSave.setOnClickListener(v -> {
                String clubName = etClubName.getText().toString().trim();
                String leader = etLeader.getText().toString().trim();
                String description = etDescription.getText().toString().trim();

                // Save the club details to Firestore here
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                assert currentUser != null;
                String loggedInUserEmail = currentUser.getEmail();
                CollectionReference usersCollection = firestore.collection("users");
                Query query = usersCollection.whereEqualTo("email", loggedInUserEmail);
                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            String documentId = documentSnapshot.getId();
                            CollectionReference clubsCollection = firestore.collection("clubs");

                            // Generate a new club ID
                            String clubId = clubsCollection.document().getId();

                            // Get the club details from the input fields
                            String clubName1 = etClubName.getText().toString();
                            String leader1 = etLeader.getText().toString();
                            String description1 = etDescription.getText().toString();

                            // Create a Map with the club data
                            Map<String, Object> clubData = new HashMap<>();
                            clubData.put("clubName", clubName1);
                            clubData.put("leader", leader1);
                            clubData.put("description", description1);
                            clubData.put("userId", documentId);

                            // Save the club data in the "clubs" collection
                            clubsCollection.document(clubId)
                                    .set(clubData)
                                    .addOnSuccessListener(aVoid -> Toast.makeText(AdminActivity.this, "Club created", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Toast.makeText(AdminActivity.this, "Failed to create club", Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        Toast.makeText(AdminActivity.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                    }
                });

                // Dismiss the dialog
                dialog.dismiss();
            });



            dialog.show();
        }
    }




