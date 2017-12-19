package com.adham.jsonrecycler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ContactsRecyclerActivity extends AppCompatActivity {

    ArrayList<Contact> contacts;
    ContactsRecyclerAdapter myAdapter;
    Intent in;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_recycler);

        in = getIntent();
        response = in.getStringExtra("con");
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("contacts");
            contacts = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject contactObject = jsonArray.getJSONObject(i);
                Contact contact = new Contact();
                contact.setId(contactObject.getString("id"));
                contact.setName(contactObject.getString("name"));
                contact.setEmail(contactObject.getString("email"));
                contact.setAddress(contactObject.getString("address"));
                contact.setGender(contactObject.getString("gender"));
                JSONObject phoneObject = contactObject.getJSONObject("phone");
                contact.setMobile(phoneObject.getString("mobile"));
                contact.setHome(phoneObject.getString("home"));
                contact.setOffice(phoneObject.getString("office"));
                contacts.add(contact);
            }
            Log.d("Response", response);
        } catch (JSONException e) {
            Log.d("Response", e.getMessage());
        }

        myAdapter = new ContactsRecyclerAdapter(contacts, this);
        RecyclerView recyclerView = findViewById(R.id.MyRecyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myAdapter);
    }

    // Search menu in the Recycler view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        MenuItem item = menu.getItem(0);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(ContactsRecyclerActivity.this, "RR", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}
