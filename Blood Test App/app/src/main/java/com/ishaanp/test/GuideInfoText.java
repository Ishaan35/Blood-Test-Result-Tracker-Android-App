package com.ishaanp.test;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GuideInfoText extends AppCompatActivity {

    String data = "";
    String path = "";

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_info_text);
        tv = findViewById(R.id.info);

        tv.setMovementMethod(new ScrollingMovementMethod());



        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("SelectedInfoFile")!= null)
        {
            String temp = bundle.getString("SelectedInfoFile");
            temp = temp.toLowerCase();
            path = temp.replaceAll(" ", "_");
            path = path.replaceAll("-" , "_");
            path = path.replaceAll("/", "_");

            try {
                readFile();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }



    }


    void readFile() throws NoSuchFieldException, IllegalAccessException {
        StringBuffer buffer = new StringBuffer();

        int ID = R.raw.class.getField(path).getInt(null);

        InputStream is = this.getResources().openRawResource(ID);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if(is != null){
            try{
                while((data=reader.readLine()) != null){
                    buffer.append(data + "\n");
                }
                data = buffer.toString();
                is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            tv.setText(Html.fromHtml(data));
        }

    }
}
