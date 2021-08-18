package blue.project.bigmanspizzaadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import blue.project.bigmanspizzaadminapp.Model.CakeItem;
import blue.project.bigmanspizzaadminapp.Model.ItemDetail;

public class AddBakeryItemCategoriesActivity extends AppCompatActivity {
    private final String LOG_TAG = AddBakeryItemCategoriesActivity.class.getSimpleName();

    private EditText itemNameEditText;
    private EditText itemPriceEditText;
    private Button addItemButton;
    private Spinner bakeryItemSpinner;
    private String bakeryItem;

    // Initialize Firebase
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bakery_item_categories);

        initViews();

        // Set spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bakeryItems, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bakeryItemSpinner.setAdapter(adapter);
        bakeryItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bakeryItem = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addItemButton.setOnClickListener(view -> {
            Log.i(LOG_TAG, "button pressed!");
            Log.i(LOG_TAG, "Bakery Item: " + bakeryItem);

            String itemName = itemNameEditText.getText().toString().trim();
            String itemPrice = itemPriceEditText.getText().toString().trim();

            Log.i(LOG_TAG, "Cake Name: " + itemName + " Cake Price: " + itemPrice);

            DatabaseReference table_reference = firebaseDatabase.getReference(bakeryItem);

            ItemDetail itemDetail = new ItemDetail(itemName, itemPrice);
            table_reference.child(itemName).setValue(itemDetail).addOnCompleteListener(task -> Toast.makeText(AddBakeryItemCategoriesActivity.this, bakeryItem + " Added!", Toast.LENGTH_SHORT).show());
        });
    }

    private void initViews() {
        itemNameEditText = findViewById(R.id.editText_item_name);
        itemPriceEditText = findViewById(R.id.editText_item_price);
        addItemButton = findViewById(R.id.button_add_tem);
        bakeryItemSpinner = (Spinner) findViewById(R.id.spinner_bakery_item);
    }
}