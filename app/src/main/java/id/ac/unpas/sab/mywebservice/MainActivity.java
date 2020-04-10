package id.ac.unpas.sab.mywebservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText editTextNrp;
    private EditText editTextNama;

    private Button buttonAdd;
    private Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inisialisasi dari View
        editTextNrp = (EditText) findViewById(R.id.editTextNrp);
        editTextNama = (EditText) findViewById(R.id.editTextNama);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }



    private void addMahasiswa(){

        final String nrp = editTextNrp.getText().toString().trim();
        final String nama = editTextNama.getText().toString().trim();


        class AddMahasiswa extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(konfigurasi.KEY_MHS_NRP,nrp);
                params.put(konfigurasi.KEY_MHS_NAMA,nama);


                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddMahasiswa ae = new AddMahasiswa();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addMahasiswa();
        }

        if(v == buttonView){
            startActivity(new Intent(this,TampilSemuaMhs.class));
        }
    }
}
