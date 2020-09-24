package com.ishaanp.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CompareSelectedField extends AppCompatActivity {

    private String dataKey = "";
    ArrayList<SaveListModel> savedData = new ArrayList<>();
    ArrayList<CompareFieldObject> fields = new ArrayList<>(); //this is used for the listview
    private ListView fieldsListView;

    private String selectedField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_selected_field);

        fieldsListView = findViewById(R.id.ListOfSelectedField);

        getID();
        loadList();
        getSelectedField();
        populateList();
        initializeList();
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
    void getSelectedField(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            selectedField = bundle.getString("SelectedField");
        }
    }
    void populateList(){
        for(int i = 0; i < savedData.size(); i++){
            SaveListModel s = savedData.get(i);
            for(int j = 0; j < s.getFields().size(); j++){
                if(s.getFields().get(j).equalsIgnoreCase(selectedField)){ //find only the selected field
                    CompareFieldObject c = new CompareFieldObject(s.getDate(), s.getFields().get(j), s.getValue().get(j));
                    if(!c.getValue().equals(""))
                        fields.add(c);
                }
            }
        }
    }
    void initializeList(){
        CompareFieldAdapter compareFieldAdapter = new CompareFieldAdapter(this, R.layout.selected_field_info, fields);
        fieldsListView.setAdapter(compareFieldAdapter);
    }
}

class CompareFieldAdapter extends ArrayAdapter<CompareFieldObject> {

    private Context mContext;
    int resource;

    public CompareFieldAdapter(@NonNull Context context, int resource, ArrayList<CompareFieldObject> objects) {
        super(context, resource, objects);
        mContext = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String date = getItem(position).getDate();
        String name = getItem(position).getName();
        String value = getItem(position).getValue();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        TextView dateTXT = convertView.findViewById(R.id.dateText);
        TextView Field = convertView.findViewById(R.id.FieldName);
        TextView valueTXT = convertView.findViewById(R.id.Value);

        dateTXT.setText(date);
        Field.setText(name + ":");
        valueTXT.setText(value);

        return convertView;
    }
}
