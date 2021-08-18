package blue.project.bigmanspizzaadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Button addCategoriesToBakeryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set title
        setTitle("Add Categories");

        // Initialize views
        initViews();

        addCategoriesToBakeryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddBakeryItemCategoriesActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Initialize views
     */
    private void initViews() {
        addCategoriesToBakeryButton = findViewById(R.id.button_add_categories_to_cake);
    }
}