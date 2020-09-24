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
import android.widget.Button;
import android.widget.ImageView;
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

public class SelectProfile extends AppCompatActivity {

    private Context ctx;
    private ArrayList<Profile> profiles;
    private String PROFILES = "Profiles";

    private ListView list;
    private Button addProf;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);
        ctx = this;
        setTitle("Select/Add a profile");
        loadList();

        list = findViewById(R.id.ProfileList);
        addProf = findViewById(R.id.addProf);

        if(profiles.size() > 0){
            ProfileAdapter profileAdapter = new ProfileAdapter(this, R.layout.profile_item, profiles);
            list.setAdapter(profileAdapter);
            list.setDividerHeight(3);
        }
        else{ //just to display the 'no profile' message with a pre-made layout
            ArrayList<String> emptyStringTemp = new ArrayList<>();
            emptyStringTemp.add("");
            emptyProfileAdapter emptyProfileAdapter = new emptyProfileAdapter(this, R.layout.profiles_are_empty, emptyStringTemp);
            list.setDivider(null);
            list.setAdapter(emptyProfileAdapter);
        }


        addProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProfileCreator();
            }
        });

        ConstraintLayout layout_MainMenu = findViewById( R.id.profileSelect);
        layout_MainMenu.getForeground().setAlpha( 0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(profiles.size() > 0){
                    Intent intent = new Intent(ctx, MainActivity.class);
                    View v = getViewByPosition(position, list);
                    TextView namefield = v.findViewById(R.id.NameText);
                    String nameID = namefield.getText().toString();
                    intent.putExtra("DataIDProfile", nameID);
                    startActivity(intent);
                }

            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                if(profiles.size() > 0){
                    Intent intent = new Intent(ctx, DeleteProfile.class);
                    ConstraintLayout layout_MainMenu = findViewById( R.id.profileSelect);
                    layout_MainMenu.getForeground().setAlpha(100);
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
        String json = sharedPreferences.getString(PROFILES, null);
        Type type = new TypeToken<ArrayList<Profile>>() {}.getType();
        profiles = gson.fromJson(json, type);
        if(profiles == null ||profiles.size() == 0){
            profiles = new ArrayList<>();
        }
    }

    private void launchProfileCreator(){
        Intent intent = new Intent(this, AddProfile.class);
        startActivity(intent);
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}

class emptyProfileAdapter extends ArrayAdapter<String>{
    private Context mContext;
    int resource;

    public emptyProfileAdapter(@NonNull Context context, int resource, ArrayList<String> empty) {
        super(context, resource, empty);
        mContext = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String text = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        return convertView;
    }
}


class ProfileAdapter extends ArrayAdapter<Profile> {

    private Context mContext;
    int resource;

    public ProfileAdapter(@NonNull Context context, int resource, ArrayList<Profile> objects) {
        super(context, resource, objects);
        mContext = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String Name = getItem(position).getNAME();
        int id =getItem(position).getIconID();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        TextView NameText = convertView.findViewById(R.id.NameText);
        ImageView image = convertView.findViewById(R.id.profileIcon);
        NameText.setText(Name);
        image.setImageResource(id);
        return convertView;
    }


}

