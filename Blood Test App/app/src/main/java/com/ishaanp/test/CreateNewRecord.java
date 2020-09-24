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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateNewRecord extends AppCompatActivity {

    private String dataKey = "";

    private ListView lv;
    private CustomAdapter customAdapter;
    private ArrayList<EditModel> DataList; //this is the default data arraylist with listview Item data models
    private Context ctx = this;

    private EditText addField;
    private EditText addValue;
    private Button addBTN;
    private EditText addUnit;


    private SaveListModel mSaveListModel;
    private ArrayList<SaveListModel> savedRecords = new ArrayList<>();

    //to calculate date
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.saveBTN){
            SaveList();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_record);
        calculateDate();
        getID();//responsible for getting correct sharedpref ID based on previously selected profile

        setTitle("Create Record");
        loadList();

        lv =  findViewById(R.id.listView);
        addField = findViewById(R.id.AddField);
        addValue = findViewById(R.id.AddValue);
        addUnit = findViewById(R.id.unitText);
        addBTN = findViewById(R.id.addBtn);

        DataList = populateList();
        customAdapter = new CustomAdapter(this,DataList);
        customAdapter.dataKey = dataKey;
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
                    customAdapter = new CustomAdapter(ctx, DataList);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    //default fields and empty values
    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();
        String[] fields ={"Name:","_ ","Amylase:", "Biotin:", "chloride:", "Creatinine:", "Folic Acid:", "Iron:", "Lipase:", "Niacin:", "Protein:", "Urea:", "Vitamin-B2:", "Vitamin-B1:", "Vitamin-E:", "Vitamin-B 6:", "Vitamin-B 12:", "Vitamin D:", "WBC Count:", "RBC Count:", "Hematocrit:" ,"Hemoglobin:", "MCV:", "MCH:", "MCHC:", "RDW/RCDW:", "Platelet Count:"};

        String[] units = {"", "", "U/L", "ng/L", "mEq/L", "mg/dL", "ng/mL", "mcg/dL", "U/L", "μg/mL", "g/dL", "mg/dL", "µg/dL", "μg/dL", "mg/L", "µg/L", "pg/mL", "ng/mL", "/mcL", "/mcL", "%", "g/dL", "fL", "pg", "g/dL", "%", "mcL"};
        double[] minValues = {9999999, 9999999, 3, 200, 97, 0.84, 2.7, 60, 0, 0.5, 6, 7, 4, 2.5,5.5,5,190,20,5000,4000000, 36, 12.0, 80, 27.5, 33.4, 11.8, 150000};
        double[] maxValues = {9999999, 9999999, 110, 1200, 107, 1.21, 17.0, 170, 160, 8.91, 8.3, 20,24,7.5,17,50,950,50,10000, 6100000, 50, 15.5, 100, 33.2, 35.5, 16.1, 450000};

        for(int i = 0; i < fields.length; i++){
            EditModel editModel = new EditModel();

            editModel.setEditTextValue(""); //all values are blank as a default

            editModel.setFieldName(fields[i]);
            editModel.setUnits(units[i]);
            editModel.setMinValue(minValues[i]); //min and max values in editmodel are used to compare in the textchangedlistener in customadapter to listen for values which go outside the reference range for the particular test item
            editModel.setMaxValue(maxValues[i]); //9999999 for min and max means that this is not to be compared
            list.add(editModel);
        }

        return list;
    }

    //loop through list and get data of each element
    private void SaveList() {

        if(customAdapter.DataList.get(0).getEditTextValue().toString().matches("")){
            Toast.makeText(this, "Enter a name for the record at the top!", Toast.LENGTH_LONG).show();
        }
        else if(!checkInList(customAdapter.DataList.get(0).getEditTextValue())){ //checks if there is a record with same name already
            Toast.makeText(this, "Record with name: " + customAdapter.DataList.get(0).getEditTextValue() + " Already exists.", Toast.LENGTH_LONG).show();
        }
        else{
            //lv.smoothScrollToPosition(0);
            //get all values and store them inside a saveList data model

            ArrayList<String> fields = new ArrayList<>();
            ArrayList<String> values = new ArrayList<>();
            ArrayList<String> units = new ArrayList<>();

            for (int i = 0; i < customAdapter.getCount(); i++) {
                fields.add(customAdapter.DataList.get(i).getFieldName());
                values.add(customAdapter.DataList.get(i).getEditTextValue());
                units.add(customAdapter.DataList.get(i).getUnits());
            }
            mSaveListModel = new SaveListModel(fields, values, units);
            mSaveListModel.setDate(date);
            mSaveListModel.setRecordName(customAdapter.DataList.get(0).getEditTextValue());


            savedRecords.add(mSaveListModel);

            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(savedRecords);
            editor.putString(dataKey, json);
            editor.apply();

            Toast.makeText(this, "Record Saved", Toast.LENGTH_LONG).show();


        }

    }

    private void loadList(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(dataKey, null);
        Type type = new TypeToken<ArrayList<SaveListModel>>() {}.getType();
        savedRecords = gson.fromJson(json, type);
        if(savedRecords == null || savedRecords.size() == 0){
            savedRecords = new ArrayList<>();
        }
    }

    private boolean checkInList(String name){

        for(int i = 0; i < savedRecords.size(); i++){
            if(name.equals(savedRecords.get(i).getRecordName())){
                return false;
            }
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

    void calculateDate(){
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        date = dateFormat.format(calendar.getTime());
    }
    void getID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            dataKey = bundle.getString("DataIDProfile");
        }
    }


}
