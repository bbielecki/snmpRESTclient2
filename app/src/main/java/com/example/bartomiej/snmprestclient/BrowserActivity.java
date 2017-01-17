package com.example.bartomiej.snmprestclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class BrowserActivity extends AppCompatActivity {

    EditText oidText;
    Button nextButton;
    Button backButton;
    String str;
    TextView[] tableTextViews;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    TextView text6;
    TextView text7;
    TextView text8;
    TextView text9;
    TextView text10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        tableTextViews = new TextView[25];
        oidText = (EditText) findViewById(R.id.oidText);
        tableTextViews[0] = (TextView) findViewById(R.id.text1);
        tableTextViews[1] = (TextView) findViewById(R.id.text2);
        tableTextViews[2] = (TextView) findViewById(R.id.text3);
        tableTextViews[3] = (TextView) findViewById(R.id.text4);
        tableTextViews[4] = (TextView) findViewById(R.id.text5);
        /*
        tableTextViews[5] = (TextView) findViewById(R.id.text6);
        tableTextViews[6] = (TextView) findViewById(R.id.text7);
        tableTextViews[7] = (TextView) findViewById(R.id.text8);
        tableTextViews[8] = (TextView) findViewById(R.id.text9);
        tableTextViews[9] = (TextView) findViewById(R.id.text10);*/
    }


    /*@Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }
    */
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/

    public void onClickOK (View view){
        str = oidText.getText().toString();
        new HttpRequestTask().execute();


    }

    public class HttpRequestTask extends AsyncTask<Void, Void, SnmpMessage> {
        @Override
        protected SnmpMessage doInBackground(Void... params) {
            try {
                final String url = "http://10.78.16.95:8080/snmp/get/" + str + "/";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                SnmpMessage snmpMessage = restTemplate.getForObject(url, SnmpMessage.class);
                return snmpMessage;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(SnmpMessage snmpMessage) {

            for (int i=0;i<1;i++){
                tableTextViews[i].setText(String.valueOf(snmpMessage.getAddress()));
                tableTextViews[i+1].setText(String.valueOf(snmpMessage.getOid()));
            }

            oidText  = (EditText) findViewById(R.id.oidText);
        }
    }
}
