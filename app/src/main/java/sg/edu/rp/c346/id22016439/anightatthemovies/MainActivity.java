package sg.edu.rp.c346.id22016439.anightatthemovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etGenre, etYear;
    Spinner spinnerRating;
    Button btnInsert, btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        spinnerRating = findViewById(R.id.spinnerr);
        btnInsert = findViewById(R.id.buttonInsert);
        btnShow = findViewById(R.id.buttonShow);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                String yearStr = etYear.getText().toString();
                String selectedItem = spinnerRating.getSelectedItem().toString();

                if (!title.isEmpty() && !genre.isEmpty() && !yearStr.isEmpty()) {
                    int year = Integer.parseInt(yearStr);

                    DBHelper db = new DBHelper(MainActivity.this);
                    db.insertMovie(title, genre, year, selectedItem);
                    Toast.makeText(MainActivity.this, "Movie Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(intent);
            }
        });
    }
}