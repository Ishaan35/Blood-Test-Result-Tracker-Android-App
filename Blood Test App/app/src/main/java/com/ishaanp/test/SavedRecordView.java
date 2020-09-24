package com.ishaanp.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SavedRecordView extends AppCompatActivity {

    private String dataKey = "";

    private ListView dataRecordList;
    private ArrayList<SaveListModel> savedRecords = new ArrayList<>();

    private Context ctx;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_record_view);
        getID();
        setTitle("Saved Records");
        ConstraintLayout layout_MainMenu = findViewById( R.id.savedRecordConstraint);
        layout_MainMenu.getForeground().setAlpha( 0);
        ctx = this;
        dataRecordList = findViewById(R.id.dataRecordList);
        loadList();

        if(savedRecords.size() > 0){
            SavedListAdapter savedListAdapter = new SavedListAdapter(this, R.layout.saved_record_list_item, savedRecords);
            dataRecordList.setAdapter(savedListAdapter);
            dataRecordList.setDividerHeight(3);
        }
        else{
            ArrayList<String> tempEmpty = new ArrayList<String>();
            tempEmpty.add("");
            emptyProfileAdapter emptyProfileAdapter = new emptyProfileAdapter(this, R.layout.saved_records_are_empty, tempEmpty);
            dataRecordList.setAdapter(emptyProfileAdapter);
            dataRecordList.setDivider(null);
        }


        dataRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(savedRecords.size() > 0){
                    Intent intent = new Intent(ctx, EditSavedRecord.class);
                    intent.putExtra("RecordIndex", position);
                    intent.putExtra("RecordName", savedRecords.get(position).name);
                    intent.putExtra("DataIDProfile", dataKey);
                    startActivity(intent);
                }

            }
        });
        dataRecordList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(savedRecords.size() > 0){
                    ConstraintLayout layout_MainMenu = findViewById( R.id.savedRecordConstraint);
                    layout_MainMenu.getForeground().setAlpha(100);
                    Intent intent = new Intent(ctx, DeleteRecord.class);
                    intent.putExtra("Position", position);
                    intent.putExtra("DataIDProfile", dataKey);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                }

                return true;
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

    private void getID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            dataKey = bundle.getString("DataIDProfile");
        }
    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("DataIDProfile", dataKey);
        startActivity(intent);
    }



}

class emptySavedListAdapter extends ArrayAdapter<String>{
    private Context mContext;
    int resource;
    public emptySavedListAdapter(@NonNull Context context, int resource, ArrayList<String> empty) {
        super(context, resource, empty);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        return convertView;
    }
}


class SavedListAdapter extends ArrayAdapter<SaveListModel> {

    private Context mContext;
    int resource;

    public SavedListAdapter(@NonNull Context context, int resource, ArrayList<SaveListModel> objects) {
        super(context, resource, objects);
        mContext = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nameOfRecord = getItem(position).getRecordName();
        String date = getItem(position).getDate();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        TextView recordName = convertView.findViewById(R.id.RecordName);
        TextView dateTV = convertView.findViewById(R.id.Date);
        recordName.setText(nameOfRecord);
        dateTV.setText(date);
        return convertView;
    }

}
