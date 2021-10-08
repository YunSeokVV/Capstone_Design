package bootpay.example.capston_design;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bootpay.example.capston_design.Utils.PurchaseAdapter;
import bootpay.example.capston_design.Utils.PurchaseData;
import bootpay.example.capston_design.Utils.RecyclerDecoration_Height;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.example.capston_design.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Product_purchase_list extends AppCompatActivity {

    String IP_ADDRESS = "52.78.19.35";
    String TAG = "phptest";

    Toolbar toolbar;
    RecyclerView recycler_view;
    ArrayList<PurchaseData> mArrayList;
    PurchaseAdapter mAdapter;

    //MySQL 에서 갖고온 데이터를 Json 형태로 담아오는 문자열
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_purchase_list);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        setSupportActionBar(toolbar);
        toolbar.setTitle("구매 내역");
        toolbar.setTitleTextColor(Color.BLACK);
        //툴바의 뒤로가기 버튼을 활성화 한다
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mArrayList = new ArrayList<>();

        mAdapter = new PurchaseAdapter(this, mArrayList);
        recycler_view.setAdapter(mAdapter);

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson_orderInformation.php","김모씨");

        //아래 두 코드는 리사이클러뷰의 아이템 간격을 조절해주는 코드다.
        RecyclerDecoration_Height decoration_height = new RecyclerDecoration_Height(60);
        recycler_view.addItemDecoration(decoration_height);
        recycler_view.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
    }

    @Override
    //좌측 상단의 왼쪽으로 향하는 화살표 버튼. 이전 화면으로 되돌아 간다.
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home/*지정한 id*/){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Product_purchase_list.this,
                    "데이터 로딩중입니다", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response - " + result);

            if (result == null){

                Log.d("error",errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String name = (String)params[1];

            String serverURL = params[0];
            //String postParameters = params[1];
            String postParameters = "name=" + name;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult(){

        String TAG_JSON="webnautes";
        String TAG_OID = "o_id";
        String TAG_USER_NAME = "user_name";
        String TAG_ADDRESS = "address";
        String TAG_USER_PHONE = "user_phone";
        String TAG_O_TIME = "o_time";
        String TAG_STATUS = "status";
        String TAG_O_TOTAL_PRICE ="o_total_price";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String o_id = item.getString(TAG_OID);
                String user_name = item.getString(TAG_USER_NAME);
                String address = item.getString(TAG_ADDRESS);
                String user_phone = item.getString(TAG_USER_PHONE);
                String o_time = item.getString(TAG_O_TIME);
                String status = item.getString(TAG_STATUS);
                String o_total_price = item.getString(TAG_O_TOTAL_PRICE);


                PurchaseData purchaseData = new PurchaseData();

                purchaseData.setDeliver_status(status+"    ");
                purchaseData.setOrder_time("결제 시간   "+o_time);
                purchaseData.setTotal_price("총 금액 "+o_total_price+" 원");

                mArrayList.add(purchaseData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}