package com.ishaanp.test;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class Guide extends AppCompatActivity {

    private ArrayList<String> infoSelect = new ArrayList<>();
    private ArrayList<String> currentInfo = new ArrayList<>();
    ListView infoList;
    guideAdapter adapter;

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        infoList = findViewById(R.id.List);
        populateList();
        initListView();
        ctx= this;

        infoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info = currentInfo.get(position);
                Intent intent = new Intent(ctx, GuideInfoText.class);
                intent.putExtra("SelectedInfoFile", info);
                startActivity(intent);
            }
        });
    }


    void populateList(){
        infoSelect = new ArrayList<>();
        currentInfo = new ArrayList<>();
        infoSelect.add("Acid phosphatase");
        infoSelect.add("Alanine aminotransferase");
        infoSelect.add("Amylase");
        infoSelect.add("Aspartate transminase");
        infoSelect.add("Adrenocorticotropic hormone");
        infoSelect.add("Alkaline phosphatase");
        infoSelect.add("Ammonia");
        infoSelect.add("Ascorbic acid");
        infoSelect.add("Biotin");
        infoSelect.add("Blood urea nitrogen");
        infoSelect.add("Ceruloplasmin");
        infoSelect.add("Chloride");
        infoSelect.add("Creatine kinase");
        infoSelect.add("Diabetes Mellitus");
        infoSelect.add("Folic Acid");
        infoSelect.add("Gamma Glutamyl Transpeptidase");
        infoSelect.add("Glycosylated Hemoglobin");
        infoSelect.add("Hematuria");
        infoSelect.add("High Density Lipoproteins");
        infoSelect.add("Iron and iron binding capacity in serum");
        infoSelect.add("Lactate dehydrogenase");
        infoSelect.add("Lipase");
        infoSelect.add("Low Density Lipoproteins");
        infoSelect.add("Niacin");
        infoSelect.add("Protein");
        infoSelect.add("Prothrombin time test");
        infoSelect.add("Schilling test");
        infoSelect.add("Serum calcium");
        infoSelect.add("Serum phosphate");
        infoSelect.add("Sodium");
        infoSelect.add("Thyroid function");
        infoSelect.add("Total biliruben");
        infoSelect.add("Triglycerides");
        infoSelect.add("Urea");
        infoSelect.add("Vitamin-B2");
        infoSelect.add("Vitamin-B1");
        infoSelect.add("Vitamin-E");
        infoSelect.add("Vitamin-B6");
        infoSelect.add("Vitamin-B12");
        infoSelect.add("Vitamin-D");
        infoSelect.add("Vitamin-K");


        infoSelect.add("APT test");
        infoSelect.add("Auto Hemolysis Test");
        infoSelect.add("WBC");
        infoSelect.add("RBC");
        infoSelect.add("Hematocrit");
        infoSelect.add("Hemoglobin");
        infoSelect.add("MCV");
        infoSelect.add("MCH");
        infoSelect.add("RDW/RCDW");
        infoSelect.add("Platelet Count");
        infoSelect.add("Eosinophil Count");
        infoSelect.add("Fetal Hemoglobin");
        infoSelect.add("Glucose-6-Phosphate Dehydrogenase");
        infoSelect.add("Reticulocyte Count");
        infoSelect.add("Blood Viscosity");
        infoSelect.add("Adrenal antibody");
        infoSelect.add("Alpha 1-antitrypsin");

        for(int i = 0; i < infoSelect.size(); i++){
            currentInfo.add(infoSelect.get(i));
        }
    }
    void initListView(){
        adapter = new guideAdapter(this, R.layout.guide_list_item, currentInfo);
        infoList.setAdapter(adapter);
    }


    class guideAdapter extends ArrayAdapter<String> {

        private Context mContext;
        int resource;

        public guideAdapter(@NonNull Context context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            mContext = context;
            this.resource = resource;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String nameOfInfo = getItem(position);

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(resource, parent, false);

            TextView Info = convertView.findViewById(R.id.infoName);
            Info.setText(nameOfInfo);
            return convertView;
        }




    }

}

