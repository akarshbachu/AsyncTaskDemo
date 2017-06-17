package akarshbachu.asynctaskdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText fnum,snum;
    Button mul;
    TextView result;
    String MulUrl="http://www.telusko.com/addition.htm?t1=2&t2=3";
    String output="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fnum=(EditText)findViewById(R.id.fnum);
        snum=(EditText)findViewById(R.id.snum);
        result=(TextView)findViewById(R.id.res);
        mul=(Button)findViewById(R.id.mul);
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=Integer.parseInt(fnum.getText().toString());
                int j=Integer.parseInt(snum.getText().toString());
                fnum.setText("");
                snum.setText("");
                MulUrl="http://www.telusko.com/addition.htm?t1="+i+"&t2="+j;
                new MultiplyInBackground().execute();
            }
        });
    }
    //creating async method to send nums to server and get result back, which is done in background without disturbing UI Thread
    public class MultiplyInBackground extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(MulUrl);
                HttpURLConnection con=(HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();//here req is sent to server

                //getting response from server
                BufferedReader bf=new BufferedReader(new InputStreamReader(con.getInputStream()));
                String res=bf.readLine();
                output=res;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            result.setText(output);
            super.onPostExecute(s);
        }

    }
}
