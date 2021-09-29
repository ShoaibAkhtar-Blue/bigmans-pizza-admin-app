package blue.project.bigmanspizzaadminapp;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import blue.project.bigmanspizzaadminapp.Model.ItemDetail;

public class AddBakeryItemCategoriesActivity extends AppCompatActivity {
    private final String LOG_TAG = AddBakeryItemCategoriesActivity.class.getSimpleName();

    private EditText itemNameEditText;
    private EditText itemPriceEditText;
    private EditText itemDescriptionEditText;
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
            // Log information
            Log.i(LOG_TAG, "button pressed!");
            Log.i(LOG_TAG, "Bakery Item: " + bakeryItem);

            // Get item information
            String itemName = itemNameEditText.getText().toString().trim();
            String itemPrice = itemPriceEditText.getText().toString().trim();
            String itemDescription = itemDescriptionEditText.getText().toString().trim();

            // Validate information
            if (itemName != null && !itemName.equals("")) {
                if (itemPrice != null && !itemPrice.equals("")) {
                    if (itemDescription != null && !itemDescription.equals("")) {
                        // Get table name for Storing the data
                        DatabaseReference table_reference = firebaseDatabase.getReference(bakeryItem);

                        // Store data in the database
                        table_reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String iName = itemName;
                                String iPrice = itemPrice;
                                String iDescription = itemDescription;

                                // Validate data
                                if (itemName.contains(".")) {
                                    iName = itemName.replace(".", "-");
                                }
                                if (itemPrice.contains(".")) {
                                    iPrice = itemPrice.replace(".", "-");
                                }
                                if (itemDescription.contains(".")) {
                                    iDescription = itemDescription.replace(".", "-");
                                }

                                // Check for duplicate entry in the database table
                                if (snapshot.child(iName).exists()) {
                                    Toast.makeText(AddBakeryItemCategoriesActivity.this, "This item already exists!", Toast.LENGTH_SHORT).show();
                                } else {
                                    try {
                                        // Log information
                                        Log.i(LOG_TAG, "Item Name: " + iName);
                                        Log.i(LOG_TAG, "Item Price: " + iPrice);
                                        Log.i(LOG_TAG, "Item Description: " + iDescription);

                                        ItemDetail itemDetail = new ItemDetail(iName, iPrice, iDescription);
                                        table_reference.child(iName).setValue(itemDetail).addOnCompleteListener(task -> Toast.makeText(AddBakeryItemCategoriesActivity.this, bakeryItem + " Added!", Toast.LENGTH_SHORT).show());
                                    } catch (DatabaseException databaseException) {
                                        Toast.makeText(AddBakeryItemCategoriesActivity.this, "Error: " + databaseException.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        Toast.makeText(this, "Enter Item Description!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Enter Item Price!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Enter Item Name!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Initialize the views
     */
    private void initViews() {
        itemNameEditText = findViewById(R.id.editText_item_name);
        itemPriceEditText = findViewById(R.id.editText_item_price);
        itemDescriptionEditText = findViewById(R.id.editText_item_description);
        addItemButton = findViewById(R.id.button_add_tem);
        bakeryItemSpinner = (Spinner) findViewById(R.id.spinner_bakery_item);
    }
}