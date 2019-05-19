package com.technofreak.amit.unitconverter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AreaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private final String[] length = {"Square Millimeter (mm^2)","Square Meter (m^2)", "Square Kilometer (km^2)",
            "Square Mile (sq mi)", "Square Inch (sq in)", "Square Foot (sq ft)","Hectare (ha)","Acre (ac)"};
    private EditText editText;
    private ListView listView;
    double value;
    int position;
    Spanned[] ans = new Spanned[7]; //chng
    double[] arr;
    final double[] L0 = {1e-6,1e-12,3.861e-13,.0016,1.0764e-5,1e-10,2.4711e-10};
    final double[] L1 = {1e6,1e-6,3.861e-7,1550.0031,10.7639,1e-4,2.4711e-4};
    final double[] L2 = {1e12,1e6,.3861,1550.0031e6,10763.9104e3,100,247.1054};
    final double[] L3 = {2.59e12,2589.9881e3,2.59,4014.48959e6,27878.3999e3,258.9988,640};
    final double[] L4 = {645.16,6.4516e-4,6.4516e-10,2.491e-10,.0069,6.4516e-8,1.5942e-7};
    final double[] L5 = {92903.04,.0929,9.2903e-8,3.587e-8,144,9.2903e-6,2.2957e-5};
    final double[] L6 = {10e9,10000,.01,.0039,15500.031e3,107639.1042,2.4711};
    final double[] L7 = {4047.8564e6,4046.8564,.004,.0016,6272.6399e3,43560,.4047};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_length);
        setTitle("Area");  //chng
        spinner = (Spinner) findViewById(R.id.spin);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spinner, length);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        editText = (EditText) findViewById(R.id.value);
        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
        if (position == 0) {  //chng
            arr = L0;
        } else if (position==1){
            arr=L1;
        } else if (position==2){
            arr=L2;
        } else if (position==3){
            arr=L3;
        } else if (position==4){
            arr=L4;
        } else if (position==5){
            arr=L5;
        } else if (position==6){
            arr=L6;
        } else if (position==7){
            arr=L7;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void calculate(View view) {
        //hiding soft keys
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        try {
            String v = editText.getText().toString();
            if (v.length() > 0) {
                value = Double.parseDouble(v);
                convert(value);

                ArrayAdapter<Spanned> listAdapter = new ArrayAdapter<Spanned>(this, R.layout.row_unit, ans) {
                    @NonNull
                    @Override
                    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                        View view;
                        TextView tv;
                        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        if (convertView == null) {
                            view = mInflater.inflate(R.layout.row_unit, null);
                        } else {
                            view = convertView;
                        }
                        tv = (TextView) view.findViewById(R.id.listRow);
                        tv.setText(getItem(position));
                        return tv;
                    }
                };

                listView.setAdapter(listAdapter);
            }
        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void convert(double value) {
        for (int i = 0, j = 0; i < arr.length; i++, j++) {
            if (j == position) j++;
            double d = value * arr[i];
            String st = String.format("%.4e", d);
            int m = st.indexOf("e");
            String st1 = st.substring(m + 1, st.length());
            st = st.substring(0, m);
            if(st.endsWith("000")) st = st.substring(0,(st.length()-3));
            String a;
            int power = Integer.parseInt(st1);
            if (power == 0) {
                a = st + " " + length[j];
            } else if ((power > 0 && power <= 4) || (power >= -2 && power < 0)) {
                a = String.format("%.4f " + length[j], d);
            } else {
                a = st + " x 10<sup><small>" + power + "</small></sup>" + " " + length[j];
            }
            ans[i] = Html.fromHtml(a);
        }
    }

    public void clear(View view) {
        editText.setText(null);
        listView.setAdapter(null);
    }
}
