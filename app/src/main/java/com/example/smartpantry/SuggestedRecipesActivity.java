package com.example.smartpantry;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;
public class SuggestedRecipesActivity extends AppCompatActivity {
    DatabaseHelper db; LinearLayout container; TextView empty;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);setContentView(R.layout.activity_suggestions);
        db=new DatabaseHelper(this);container=findViewById(R.id.recipeContainer);empty=findViewById(R.id.emptyText);
        load();
    }
    @Override protected void onResume(){super.onResume();if(db!=null)load();}
    void load(){
        container.removeAllViews();int count=0;Cursor c=db.getRecipes();
        while(c.moveToNext()){
            int id=c.getInt(c.getColumnIndexOrThrow("id"));
            String name=c.getString(c.getColumnIndexOrThrow("name"));
            String ingredients=c.getString(c.getColumnIndexOrThrow("ingredients"));
            if(db.canMake(ingredients)){
                Button b=new Button(this);b.setText(name);
                b.setOnClickListener(v->{
                    android.content.Intent i=new android.content.Intent(this,RecipeDetailActivity.class);
                    i.putExtra("id",id);startActivity(i);
                });container.addView(b);count++;
            }
        }c.close();empty.setVisibility(count==0?TextView.VISIBLE:TextView.GONE);
    }
}