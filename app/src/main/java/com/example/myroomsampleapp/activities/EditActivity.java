package com.example.myroomsampleapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myroomsampleapp.R;
import com.example.myroomsampleapp.constants.Constants;
import com.example.myroomsampleapp.database.AppDatabase;
import com.example.myroomsampleapp.database.AppExecutors;
import com.example.myroomsampleapp.model.Person;

public class EditActivity extends AppCompatActivity {
    EditText
     name, email, pincode, city, phnumber;
    Button button;
    int mPErsonId;
    Intent intent;
    private AppDatabase mDb;
    @Override
    protected void onCreate(final Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        mDb=AppDatabase.getInstance(getApplicationContext());
        intent=getIntent();
        if(intent!=null && intent.hasExtra(Constants.UPDATE_Person_Id)){
            button.setText("update");
            mPErsonId = intent.getIntExtra(Constants.UPDATE_Person_Id,-1);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Person person=mDb.personDao().loadPersonByID(mPErsonId);
                    populateUI(person);
                }
            });
        }

    }
    private void populateUI(Person person) {

        if (person == null) {
            return;
        }

        name.setText(person.getName());
        email.setText(person.getEmail());
        phnumber.setText(person.getNumber());
        pincode.setText(person.getPincode());
        city.setText(person.getCity());
    }

    private void initViews() {
        name=findViewById(R.id.edit_name);
        email = findViewById(R.id.edit_email);
        pincode = findViewById(R.id.edit_pincode);
        city = findViewById(R.id.edit_city);
        phnumber = findViewById(R.id.edit_number);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onSavebuttonclicked();
            }
        });
    }

    private void onSavebuttonclicked() {
        final Person person=new Person(name.getText().toString(),
                                       email.getText().toString(),
                                       phnumber.getText().toString(),
                                        pincode.getText().toString(),
                                        city.getText().toString());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(!intent.hasExtra(Constants.UPDATE_Person_Id)){
                    mDb.personDao().insertPerson(person);
                }
                else{
                    person.setId(mPErsonId);
                    mDb.personDao().updatePerson(person);
                }
                finish();
            }
        });
    }


}
