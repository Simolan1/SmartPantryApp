package com.example.smartpantry;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
public class PantryActivity extends AppCompatActivity {
    DatabaseHelper db;
    PantryAdapter adapter;
    ArrayList<PantryItem> items=new ArrayList<>();
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b); setContentView(R.layout.activity_pantry);
        db=new DatabaseHelper(this);
        RecyclerView rv=findViewById(R.id.recyclerPantry);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new PantryAdapter(this,items,db);
        rv.setAdapter(adapter);
        findViewById(R.id.fabAdd).setOnClickListener(v ->
                startActivity(new Intent(this, AddEditIngredientActivity.class)));
        load();
    }
    @Override protected void onResume(){ super.onResume(); if(db!=null) load(); }
    void load(){
        items.clear(); Cursor c=db.getPantry();
        while(c.moveToNext()) items.add(new PantryItem(
                c.getInt(c.getColumnIndexOrThrow("id")),
                c.getString(c.getColumnIndexOrThrow("name")),
                c.getDouble(c.getColumnIndexOrThrow("quantity")),
                c.getString(c.getColumnIndexOrThrow("unit")),
                c.getString(c.getColumnIndexOrThrow("expiry"))));
        c.close(); adapter.notifyDataSetChanged();
    }
}
