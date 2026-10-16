package com.example.smartpantry;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class RecipeDetailActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);setContentView(R.layout.activity_recipe_detail);
        DatabaseHelper db=new DatabaseHelper(this);
        int id=getIntent().getIntExtra("id",-1);Cursor c=db.getRecipe(id);
        if(c.moveToFirst()){
            ((TextView)findViewById(R.id.recipeTitle)).setText(c.getString(c.getColumnIndexOrThrow("name")));
            ((TextView)findViewById(R.id.recipeIngredients)).setText(format(c.getString(c.getColumnIndexOrThrow("ingredients"))));
            ((TextView)findViewById(R.id.recipeMethod)).setText(c.getString(c.getColumnIndexOrThrow("method")));
        }c.close();
    }
    String format(String raw){
        StringBuilder s=new StringBuilder();
        for(String x:raw.split(";")){String[] p=x.split(":");s.append("• ").append(p[0]).append(" (required: ").append(p[1]).append(")\\n");}
        return s.toString();
    }
}
