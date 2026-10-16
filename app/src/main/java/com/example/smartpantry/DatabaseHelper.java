package com.example.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "expiry TEXT)");

        db.execSQL("CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "ingredients TEXT NOT NULL, " +
                "method TEXT NOT NULL)");

        seedRecipes(db);
    }

    private void seedRecipes(SQLiteDatabase db) {
        addRecipe(db, "Pasta",
                "pasta:1;tomato:2;onion:1",
                "Boil the pasta. Cook the onion and tomato, then combine with the pasta.");

        addRecipe(db, "Omelette",
                "egg:2;onion:1;cheese:1",
                "Beat the eggs, add onion and cheese, then cook in a pan until set.");

        addRecipe(db, "Rice and Vegetables",
                "rice:1;carrot:1;onion:1",
                "Cook the rice. Stir-fry the vegetables and serve with the rice.");
    }

    private void addRecipe(SQLiteDatabase db, String name, String ingredients, String method) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("ingredients", ingredients);
        values.put("method", method);
        db.insert("recipes", null, values);
    }

    public long addPantry(String name, double quantity, String unit, String expiry) {
        ContentValues values = new ContentValues();
        values.put("name", name.trim().toLowerCase(Locale.US));
        values.put("quantity", quantity);
        values.put("unit", unit.trim());
        values.put("expiry", expiry == null ? "" : expiry.trim());
        return getWritableDatabase().insert("pantry", null, values);
    }

    public int updatePantry(int id, String name, double quantity, String unit, String expiry) {
        ContentValues values = new ContentValues();
        values.put("name", name.trim().toLowerCase(Locale.US));
        values.put("quantity", quantity);
        values.put("unit", unit.trim());
        values.put("expiry", expiry == null ? "" : expiry.trim());
        return getWritableDatabase().update("pantry", values, "id=?",
                new String[]{String.valueOf(id)});
    }

    public int deletePantry(int id) {
        return getWritableDatabase().delete("pantry", "id=?",
                new String[]{String.valueOf(id)});
    }

    public Cursor getPantry() {
        return getReadableDatabase().query("pantry", null, null, null, null, null, "name ASC");
    }

    public Cursor getRecipes() {
        return getReadableDatabase().query("recipes", null, null, null, null, null, "name ASC");
    }

    public Cursor getRecipe(int id) {
        return getReadableDatabase().query("recipes", null, "id=?",
                new String[]{String.valueOf(id)}, null, null, null);
    }

    public boolean canMake(String recipeIngredients) {
        Map<String, Double> pantry = new HashMap<>();
        Cursor cursor = getPantry();

        try {
            while (cursor.moveToNext()) {
                String name = normalize(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
                pantry.put(name, pantry.getOrDefault(name, 0.0) + quantity);
            }
        } finally {
            cursor.close();
        }

        for (String item : recipeIngredients.split(";")) {
            String[] parts = item.split(":", 2);
            if (parts.length != 2) return false;

            String name = normalize(parts[0]);
            double required;
            try {
                required = Double.parseDouble(parts[1]);
            } catch (NumberFormatException e) {
                return false;
            }

            if (!pantry.containsKey(name) || pantry.get(name) < required) {
                return false;
            }
        }
        return true;
    }

    public static String normalize(String value) {
        if (value == null) return "";
        String s = value.trim().toLowerCase(Locale.US);

        if (s.endsWith("ies") && s.length() > 3) {
            return s.substring(0, s.length() - 3) + "y";
        }
        if (s.endsWith("oes") && s.length() > 3) {
            return s.substring(0, s.length() - 2);
        }
        if (s.endsWith("s") && !s.endsWith("ss") && s.length() > 1) {
            return s.substring(0, s.length() - 1);
        }
        return s;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS pantry");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(db);
    }
}
