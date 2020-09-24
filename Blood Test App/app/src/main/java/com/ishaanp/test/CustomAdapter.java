package com.ishaanp.test;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<EditModel> DataList;

    public String dataKey;
    private Context ctx;

    public CustomAdapter(Context context, ArrayList<EditModel> DataList) {

        this.context = context;
        this.DataList = DataList;
        ctx = this.context;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public Object getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final ViewHolder2 holder2;
        final ViewHolder3 holder3;


        if(DataList.get(position).getFieldName().indexOf("_") < 0){
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.lv_item, null, true);

                holder.inputText = (EditText) convertView.findViewById(R.id.editid);
                holder.fieldName = (TextView) convertView.findViewById(R.id.field);
                holder.unitText = (TextView) convertView.findViewById(R.id.units);
                convertView.setTag(holder);
            }else {
                // the getTag returns the viewHolder object set as a tag to the view
                holder = (ViewHolder)convertView.getTag();
            }

            if(DataList.get(position).getFieldName().equals("Name:")){
                holder.inputText.setText(DataList.get(position).getEditTextValue());
                holder.fieldName.setText(DataList.get(position).getFieldName());
            }
            else{
                holder.inputText.setText(DataList.get(position).getEditTextValue());
                holder.fieldName.setText(DataList.get(position).getFieldName());
                holder.unitText.setText(DataList.get(position).getUnits());
                holder.inputText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            }


            holder.inputText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    DataList.get(position).setEditTextValue(holder.inputText.getText().toString());

                    if(!TextUtils.isEmpty(holder.inputText.getText().toString()) && DataList.get(position).getMinValue() < 9999999){
                        double inputVal = Double.parseDouble(holder.inputText.getText().toString());

                        if(inputVal < DataList.get(position).getMinValue() || inputVal > DataList.get(position).getMaxValue()){
                            DrawableCompat.setTint(holder.inputText.getBackground(), ContextCompat.getColor(ctx, R.color.Red));
                        }
                        else if(inputVal >= DataList.get(position).getMinValue() && inputVal <= DataList.get(position).getMaxValue()){
                            DrawableCompat.setTint(holder.inputText.getBackground(), ContextCompat.getColor(ctx, R.color.Green));
                        }
                    }


                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        else{
                holder2 = new ViewHolder2();
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.lv_item_header, null, true);
                holder2.Header = (TextView) convertView.findViewById(R.id.HeaderTitle);

            convertView.setTag(holder2);

            String title = DataList.get(position).getFieldName();
            holder2.Header.setText(title.substring(1));
        }



        return convertView;
    }

    private class ViewHolder {

        protected EditText inputText;
        protected TextView fieldName;
        protected TextView unitText;
    }
    private class ViewHolder2{
        protected TextView Header;
    }
    private class ViewHolder3{
        protected Button addField;
    }




}
