package com.example.smartpantry;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
public class AddEditIngredientActivity extends AppCompatActivity {
    DatabaseHelper db; EditText name,qty,unit,expiry; int id=-1;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);setContentView(R.layout.activity_add_edit);
        db=new DatabaseHelper(this);
        name=findViewById(R.id.etName);qty=findViewById(R.id.etQuantity);
        unit=findViewById(R.id.etUnit);expiry=findViewById(R.id.etExpiry);
        id=getIntent().getIntExtra("id",-1);
        if(id!=-1) load();
        findViewById(R.id.btnSave).setOnClickListener(v->save());
    }
    void load(){
        Cursor c=db.getPantry(); while(c.moveToNext()){
            if(c.getInt(c.getColumnIndexOrThrow("id"))==id){
                name.setText(c.getString(c.getColumnIndexOrThrow("name")));
                qty.setText(String.valueOf(c.getDouble(c.getColumnIndexOrThrow("quantity"))));
                unit.setText(c.getString(c.getColumnIndexOrThrow("unit")));
                expiry.setText(c.getString(c.getColumnIndexOrThrow("expiry"))); break;
            }
        } c.close();
    }
    void save(){
        String n=name.getText().toString().trim(), q=qty.getText().toString().trim(), u=unit.getText().toString().trim();
        if(n.isEmpty()||q.isEmpty()||u.isEmpty()){Toast.makeText(this,"Name, quantity and unit are required",Toast.LENGTH_LONG).show();return;}
        try{
            double quantity=Double.parseDouble(q); if(quantity<=0) throw new NumberFormatException();
            if(id==-1) db.addPantry(n,quantity,u,expiry.getText().toString().trim());
            else db.updatePantry(id,n,quantity,u,expiry.getText().toString().trim());
            finish();
        }catch(NumberFormatException e){Toast.makeText(this,"Enter a valid positive quantity",Toast.LENGTH_LONG).show();}
    }
}
