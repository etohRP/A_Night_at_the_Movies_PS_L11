package sg.edu.rp.c346.id22016439.anightatthemovies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText etId, etTitle, etGenre, etYear;
    Spinner spinnerRating;
    Button btnUpdate, btnDelete, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etId = findViewById(R.id.etId);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        spinnerRating = findViewById(R.id.spinnerr);

        Intent intent = getIntent();
        Movie data = (Movie) intent.getSerializableExtra("movie");
        etId.setText(String.valueOf(data.getId()));
        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etYear.setText(String.valueOf(data.getYear()));
        spinnerRating.setSelection(getIndex(spinnerRating, data.getRating()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                String yearStr = etYear.getText().toString();
                String selectedItem = spinnerRating.getSelectedItem().toString();

                if (title.isEmpty() || genre.isEmpty() || yearStr.isEmpty()) {
                    // Display an error message indicating that all fields are compulsory
                    Toast.makeText(EditActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    int year = Integer.parseInt(yearStr);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditActivity.this);
                    alertDialogBuilder.setTitle("Confirm Update");
                    alertDialogBuilder.setMessage("Are you sure you want to update the movie?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DBHelper db = new DBHelper(EditActivity.this);
                            data.setTitle(title);
                            data.setGenre(genre);
                            data.setYear(year);
                            data.setRating(selectedItem);
                            db.updateMovie(data);
                            db.close();
                            finish();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing or handle cancellation
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the movie " + data.getTitle()+"?");
                myBuilder.setCancelable(true);

                myBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                // Configure the 'negative' button
                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(EditActivity.this);
                        dbh.deleteMovie(data.getId());
                        finish();

                    }
                });
//                myBuilder.setNeutralButton("Delete", null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes?");
                myBuilder.setCancelable(true);

                //configure the 'positive' button
                myBuilder.setPositiveButton("Do not discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                //configure the 'negative' button
                myBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

    }
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}