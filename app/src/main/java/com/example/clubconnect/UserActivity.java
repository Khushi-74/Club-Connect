package com.example.clubconnect;
import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class UserActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<user> userArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage);
progressDialog = new ProgressDialog(this);
progressDialog.setCancelable(false);
progressDialog.setMessage("fetching data");
progressDialog.show();

        recyclerView = findViewById(R.id.recyclerViewClubs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        userArrayList=new ArrayList<user>();
        myAdapter = new MyAdapter(UserActivity.this,userArrayList);
        recyclerView.setAdapter(myAdapter);
        EventChangeListner();
    }

    private void EventChangeListner() {
        db.collection("clubs").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.d(TAG, "onEvent: can not fetch");;
                }
                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        userArrayList.add(dc.getDocument().toObject(user.class));
                    }
                    myAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }
        });
    }

    public static class ItemModel {
        private String ItemName, ItemDesc, ItemId;
        private ArrayList<String> ImageUrls;

        // we need empty constructor
        public ItemModel() {
        }

        public ItemModel(String itemName, String itemDesc, String itemId, ArrayList<String> imageUrls) {
            ItemName = itemName;
            ItemDesc = itemDesc;
            ItemId = itemId;
            ImageUrls = imageUrls;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String itemName) {
            ItemName = itemName;
        }

        public String getItemDesc() {
            return ItemDesc;
        }

        public void setItemDesc(String itemDesc) {
            ItemDesc = itemDesc;
        }

        public String getItemId() {
            return ItemId;
        }

        public void setItemId(String itemId) {
            ItemId = itemId;
        }

        public ArrayList<String> getImageUrls() {
            return ImageUrls;
        }

        public void setImageUrls(ArrayList<String> imageUrls) {
            ImageUrls = imageUrls;
        }
    }
}
