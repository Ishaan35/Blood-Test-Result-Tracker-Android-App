package com.ishaanp.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EditSavedRecord extends AppCompatActivity {

    private String dataKey = "";

    private ListView lv;
    private CustomAdapter customAdapter;
    private ArrayList<EditModel> DataList; //this is the default data arraylist with listview Item data models
    private Context ctx = this;

    private EditText addField;
    private EditText addValue;
    private EditText addUnit;
    private Button addBTN;

    private int SavedRecordIndex;


    private SaveListModel mSaveListModel;
    private ArrayList<SaveListModel> savedRecords = new ArrayList<>();

    double[] minValues = {9999999, 9999999, 3, 200, 97, 0.84, 2.7, 60, 0, 0.5, 6, 7, 4, 2.5,5.5,5,190,20,5000,4000000, 36, 12.0, 80, 27.5, 33.4, 11.8, 150000};
    double[] maxValues = {9999999, 9999999, 110, 1200, 107, 1.21, 17.0, 170, 160, 8.91, 8.3, 20,24,7.5,17,50,950,50,10000, 6100000, 50, 15.5, 100, 33.2, 35.5, 16.1, 450000};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_saved_record);
        getID();
        lv = (ListView) findViewById(R.id.listView);
        addField = findViewById(R.id.AddField);
        addValue = findViewById(R.id.AddValue);
        addUnit = findViewById(R.id.addUnit);
        addBTN = findViewById(R.id.addBtn);

        DataList = new ArrayList<>();
        setTitle("View or edit record");

        //sent data from last activity and loading the listview with data
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            SavedRecordIndex = bundle.getInt("RecordIndex");
            String SavedName = bundle.getString("RecordName");
            loadList();
            if(savedRecords.get(SavedRecordIndex) != null){
                //dump data from savedRecords(index) using a for loop to create a bunch of Editmodels
                for(int i = 0; i < savedRecords.get(SavedRecordIndex).getFields().size(); i++){
                    EditModel e = new EditModel();
                    e.setFieldName(savedRecords.get(SavedRecordIndex).getFields().get(i));
                    e.setEditTextValue(savedRecords.get(SavedRecordIndex).getValue().get(i));
                    e.setUnits(savedRecords.get(SavedRecordIndex).getUnits().get(i));
                    if(i < minValues.length && minValues[i] < 9999999){
                        e.setMinValue(minValues[i]);
                        e.setMaxValue(maxValues[i]);
                    }
                    DataList.add(e);
                }
            }
            setTitle("View or edit record");
        }



        customAdapter = new CustomAdapter(this,DataList);
        lv.setAdapter(customAdapter);

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(addField.getText().toString()) && !TextUtils.isEmpty(addValue.getText().toString()) && !TextUtils.isEmpty(addUnit.getText().toString()) && checkField(addField.getText().toString())){
                    String newfield = addField.getText().toString();
                    String newValue = addValue.getText().toString();
                    String newUnit = addUnit.getText().toString();

                    EditModel newData = new EditModel();
                    newData.setFieldName(newfield);
                    newData.setEditTextValue(newValue);
                    newData.setUnits(newUnit);


                    //update list
                    DataList.add(newData);
                    CustomAdapter customAdapter = new CustomAdapter(ctx, DataList);
                    lv.setAdapter(customAdapter);

                    //go to bottom and disable keyboard
                    addField.getText().clear();
                    addValue.getText().clear();
                    addUnit.getText().clear();
                    addValue.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    addField.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    addUnit.onEditorAction(EditorInfo.IME_ACTION_DONE);

                    lv.setSelection(lv.getCount() - 1);

                }
                else if(!checkField(addField.getText().toString())){
                    Toast.makeText(ctx, "Field already exists", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(addField.getText().toString())){
                    Toast.makeText(ctx, "Enter a field name and then press the add button", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(addValue.getText().toString())){
                    Toast.makeText(ctx, "Enter a value for the field and then press the add button", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(addUnit.getText().toString())){
                    Toast.makeText(ctx, "Enter a unit of measurement for the field and then press the add button", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadList(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(dataKey, null);
        Type type = new TypeToken<ArrayList<SaveListModel>>() {}.getType();
        savedRecords = gson.fromJson(json, type);
        if(savedRecords == null ||savedRecords.size() == 0){
            savedRecords = new ArrayList<>();
        }
    }

    private void saveList(){
        ArrayList<String> fields = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        ArrayList<String> units = new ArrayList<>();

        for (int i = 0; i < customAdapter.getCount(); i++) {
            fields.add(customAdapter.DataList.get(i).getFieldName());
            values.add(customAdapter.DataList.get(i).getEditTextValue());
            units.add(customAdapter.DataList.get(i).getUnits());
        }
        mSaveListModel = new SaveListModel(fields, values, units);
        mSaveListModel.setRecordName(customAdapter.DataList.get(0).getEditTextValue());


        //date
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
        String date = df.format(calendar.getTime());

        mSaveListModel.setDate(date);
        savedRecords.set(SavedRecordIndex, mSaveListModel);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(savedRecords);
        editor.putString(dataKey, json);
        editor.apply();

        Toast.makeText(this, "Changes Saved", Toast.LENGTH_LONG).show();
    }

    void getID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            dataKey = bundle.getString("DataIDProfile");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.saveBTN){
            saveList();
        }
        return true;
    }

    private boolean checkField(String field){
        field.trim();
        for(int i = 0; i < DataList.size(); i++){
            String compare = DataList.get(i).getFieldName().trim();
            if(compare.equalsIgnoreCase(field)){
                return false;
            }
        }
        return true;
    }
}
