package com.ishaanp.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SelectWhatToCompare extends AppCompatActivity {

    //this class gathers all the different fields that the person has entered and makes a list of them with no duplicates. The user then selects one, and all the past entries of that data field, for example Vitamin D, will show up by date and record name


    private String dataKey = "";
    private Context ctx;

    ArrayList<String> fields = new ArrayList<>(); //this is used for the listview
    ArrayList<SaveListModel> savedData = new ArrayList<>(); //this is used to find all the fields the user might want to compare

    private ListView fieldsListView;
    CompareAdapter mCompareAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_what_to_compare);
        setTitle("Select what you want to compare");
        ctx = this;

        fieldsListView = findViewById(R.id.fieldsListView);

        getID();
        loadList();
        populateList();
        initializeListView();

        fieldsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedField = mCompareAdapter.getItem(position); //pass this into next activity
                Intent intent = new Intent(ctx, CompareSelectedField.class);
                intent.putExtra("DataIDProfile", dataKey);
                intent.putExtra("SelectedField", selectedField);
                startActivity(intent);
            }
        });

    }

    void populateList(){
        for(int i = 0; i < savedData.size(); i++){
            SaveListModel s = savedData.get(i); //temporary savelist model
            for(int j = 0; j < s.getFields().size(); j++){ //go through all the fields and get each field. Make sure no duplicates
                String current = s.getFields().get(j);
                if(notInList(current) && !current.equalsIgnoreCase("Name:") && current.indexOf("_") < 0){
                    fields.add(current);
                }
            }
        }
    }
    boolean notInList(String toCheck){
        for(int i = 0; i < fields.size(); i++){ //if it finds a match, return false, otherwise return true
            if(fields.get(i).equalsIgnoreCase(toCheck)){
                return false;
            }
        }
        return true;
    }
    private void loadList(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(dataKey, null);
        Type type = new TypeToken<ArrayList<SaveListModel>>() {}.getType();
        savedData = gson.fromJson(json, type);
        if(savedData == null ||savedData.size() == 0){
            savedData = new ArrayList<>();
        }
    }
    void getID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            dataKey = bundle.getString("DataIDProfile");
        }
    }

    private void initializeListView(){
        mCompareAdapter = new CompareAdapter(this, R.layout.fields_display, fields);
        fieldsListView.setAdapter(mCompareAdapter);
    }
}

class CompareAdapter extends ArrayAdapter<String> {

    private Context mContext;
    int resource;

    public CompareAdapter(@NonNull Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        mContext = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String theField = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        TextView nameOfField = convertView.findViewById(R.id.nameOfField);

        nameOfField.setText(theField);

        return convertView;
    }
}

