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

public class PowerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private final String[] length = {"Watt (W)","Kilowatt (kW)", "Calorie/second (cal/s)","Horsepower (hp)"};
    private EditText editText;
    private ListView listView;
    double value;
    int position;
    Spanned[] ans = new Spanned[3]; //chng
    double[] arr;
    final double[] L0 = {.001,.2388,.0013};
    final double[] L1 = {1000,238.8459,1.341};
    final double[] L2 = {4.1868,.004186,.0056};
    final double[] L3 = {745.6999,.74569,178.1074};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_length);
        setTitle("Power");  //chng
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
