package com.example.clubconnect;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventView extends AppCompatActivity {
    private ArrayList<eventdata> eventDataList;
    private EventAdapter adapter;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventview);

        // Initialize the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerviewevents);

        // Set the layout manager for the RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance();

        // Fetch data from Firestore and populate the eventDataList
        fetchEventData();

        // Create the adapter instance
        adapter = new EventAdapter(this, eventDataList);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchEventData() {
        eventDataList = new ArrayList<>();

        // Assuming your Firestore collection name is "events"
        firestore.collection("Events")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Extract the data from each document and create an EventData object
                            List<String> imageUrls = (List<String>) document.get("imageUrls");

                            String itemName = document.getString("itemName");
                            String itemDesc = document.getString("itemDesc");
                            String itemId = document.getString("itemId");

                            eventdata ev = new eventdata((imageUrls), itemName, itemDesc, itemId);

                            // Add the eventData object to the list
                            eventDataList.add(ev);
                        }

                        // Notify the adapter that the data set has changed
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "unable to fetch at the moment", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
