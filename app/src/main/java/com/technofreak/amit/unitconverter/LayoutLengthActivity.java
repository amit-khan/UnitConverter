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

public class LayoutLengthActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private final String[] length = {"Nanometer (nm)", "Micrometer (μm)", "Millimeter (mm)", "Centimeter (cm)",
            "Meter (m)", "Kilometer (km)", "Mile (mi)", "Inch (in)", "Foot (ft)"};
    private EditText editText;
    private ListView listView;
    double value;
    int position;
    Spanned[] ans = new Spanned[8];
    double[] arr;
    final double[] nano = {0.001, 1.0e-6, 1.0e-7, 1.0e-9, 1.0e-12, 6.2137e-13, 3.937e-8, 3.2808e-9};
    final double[] micro = {1000.0,0.001,10.0e-5,1.0e-6,1.0e-9,6.2137e-10,3.937e-5,3.2808e-6};
    final double[] mm = {1.0e6,1000.0,0.1,0.001,1.0e-6,6.2137e-7,0.0394,0.00328};
    final double[] cm = {1.0e7,10000.0,10.0,0.01,1.0e-5,6.2137e-6,0.3937,0.0328};
    final double[] m = {1.0e9,1.0e6,1000.0,100.0,1.0e-3,6.2137e-4,39.3701,3.2808};
    final double[] km = {1.0e12,1.0e9,1.0e6,1.0e5,1000.0,0.6214,3.937e4,3280.8399};
    final double[] mi = {1.6093e12,1.6093e9,1.6093e6,1.6093e5,1609.344,1.6093,63360.0,5280.0};
    final double[] in = {2.54e7,25400.0,25.3,2.54,0.0254,2.54e-5,1.5783e-5,0.0833};
    final double[] ft = {3.048e8,304800.0,304.8,30.48,0.3048,3.048e-4,1.8939e-4,12.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_length);
        setTitle("Length");
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
        if (position == 0) {
            arr = nano;
        } else if (position==1){
            arr=micro;
        } else if (position==2){
            arr=mm;
        } else if (position==3){
            arr=cm;
        } else if (position==4){
            arr=m;
        } else if (position==5){
            arr=km;
        } else if (position==6){
            arr=mi;
        } else if (position==7){
            arr=in;
        } else if (position==8){
            arr=ft;
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
