package bootpay.example.capston_design;
// 이 액티비티는 앱의 메인 화면으로 다양한 기능을 제공한다.
// 제공하는 기능들
//1.구매자의 배송지를 설정한다.
//2. 사용자가 구매할 제품의 목록을 볼 수 있다.
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.BootpayKeyValue;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capston_design.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;




public class MainActivity extends Activity {

    private ArrayList<Dictionary> mArrayList;
    private CustomAdapter mAdapter;

    // 사용자가 주문할 총 주문금액이다.
    //todo:에버노트의 공부로그 5월24일 오후5시07분 참조. 원치 않는 상황의 에러 때문에 더 이상 시간을 끌 수 없어서 static 을 씀.
    public static int customer_total_price=0;

    //리사이클러뷰의 아이템을 추가하기위한 객체다.
    Dictionary data2;

    //부트페이 결제를 하기위한 application_id 값이다.
    //내꺼
    //private String application_id = "609b9a9fd8c1bd001e2bad45";
    //예제거
    private String application_id = "59a4d326396fa607cbe75de5";

    //Daum 위치 API 를 사용하고 인텐트 결과를 받아오기위해 필요한 상수다.
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    //사용자가 구매할 총 금액을 표현하는 TextView다.
    //todo:에버노트의 공부로그 5월24일 오후5시07분 참조. 원치 않는 상황의 에러 때문에 더 이상 시간을 끌 수 없어서 static 을 씀.
    public static TextView total_price;

    //배송자의 정보들(배송지, 이름, 전화번호)을 불러온다. 만약 아무런 값도 없다면 '결제하기' 를 누른 시점에 설정시키게 하고 값을 저장한다.
    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor; // 에디터

    //선택 삭제를 표현하는 TextView 다. 사용자가 선택한 아이템들을 삭제할 수 있다.
    TextView delete_choosen;

    //사용자의 배송지를 설정하는 TextView 이다.
    TextView textView;

    //결제 버튼이다.
    Button button_pay;

    //아래 세개의 변수는 사용자 정보에 관한 변수들이다. shredpreference 로 불러와서 그 값을 초기화 한다.
    //사용자 주소
    String user_address;

    //사용자 이름
    String user_name;

    //사용자 전화번호
    String user_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("MainAcitivty","OnCreate");

        //사용자 정보를 불러오기위한 shared_preference초기화
        pref = getSharedPreferences("user_data", Activity.MODE_PRIVATE);
        editor = pref.edit();

        delete_choosen=(TextView)findViewById(R.id.textView3);
        button_pay=(Button)findViewById(R.id.button_pay);

        total_price=(TextView)findViewById(R.id.user_total_price);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();

        mAdapter = new CustomAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        //todo : 아래 세줄의 코드가 어떤 역할을 하는건지 정확하게 모르겠다.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Button buttonInsert = (Button)findViewById(R.id.button_main_insert);



//        //사용자의 배송지를 설정하는 TextView 다. 클릭하면 Daum 주소설정 api 를 사용하게 된다.
//        user_address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(MainActivity.this, WebViewActivity.class);
//                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
//            }
//        });

        //결제하기 버튼이다.
        button_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //사용자 정보가 shared 에 저장되어있지 않으면 설정시키러 보낸다.
                if(user_address=="none" || user_name=="none" || user_phone=="none"){
                    Intent intent = new Intent(getApplicationContext(), Pay_Activity.class);
                    startActivity(intent);
                    Toast myToast = Toast.makeText(MainActivity.this,"이런! 사용자 정보가 없군요! 사용자 정보를 초기화 해주세요!", Toast.LENGTH_SHORT);
                    myToast.show();
                }

                //저장되어 있다면 결제화면으로 넘긴다.
                else{

                    for(int i=0;i<mArrayList.size();i++){
                        Log.v("MainAcitivty","for i :"+i);
                        Log.v("MainAcitivty",mArrayList.get(i).product_name);
                        Log.v("MainAcitivty",String.valueOf(mArrayList.get(i).item_count));
                        Log.v("MainAcitivty",mArrayList.get(i).product_price);
                    }

                    goRequest();
                }





            }
        });

        //QR코드 스캔 버튼을 눌렀을때 발생하는 이벤트를 설정한다.
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //아래 코드들은 카메라로 넘어가서 QR코드 스캔 기능을 해준다.
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });

        //'선택 삭제' 버튼을 눌려서 사용자가 원하는 상품들을 제외한다.
        delete_choosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mAdapter.Choosen_Item_Delete();
                Log.v("MainActivity","mArrayList.size() "+mArrayList.size());

                for(int i=mArrayList.size();i>0;i--){
                    Log.v("MainAcitivty","i "+(i-1));
                    Log.v("MainActivity","list_data "+(i-1)+" "+mArrayList.get(i-1).getCheck_box());

                    if(mArrayList.get(i-1).getCheck_box()==true){
                        mAdapter.RemoveItem(i-1,Integer.parseInt(mArrayList.get(i-1).getProduct_price()));
                    }
                }

            }
        });


//        //EditText 에 입력을 완료하고 나면 cursor 를 다시 disable 한다.
//        detail_position.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                Log.v("MainActivity","onEditorAction 호출");
//                String inText = textView.getText().toString();
//                // Do Something...
//                textView.setInputType(EditorInfo.TYPE_NULL);// setCursorVisible(true); 도 가능하다.
//                return true;
//            }
//        });

    }       //onCreate end

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("MainAcitivty","onStart");

        // 2. 저장해둔 값 불러오기 ("식별값", 초기값) -> 식별값과 초기값은 직접 원하는 이름과 값으로 작성.
        user_address = pref.getString("user_address","none");
        user_name = pref.getString("user_name","none");
        user_phone = pref.getString("user_phone","none");

        Log.v("MainAcitivty","user_address "+user_address);
        Log.v("MainAcitivty","user_name "+user_name);
        Log.v("MainAcitivty","user_phone "+user_phone);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("MainAcitivty","onStop");

    }


    //    //결제 기능 실행
    public void goRequest() {
//        runOnUiThread();

//        BootpayRestService

//        Spinner mySpinner = (Spinner) findViewById(R.id.your_spinner);
//        String text = mySpinner.getSelectedItem().toString();
        BootUser bootUser = new BootUser().setPhone("010-1234-5678"); // 구매자 정보
        BootExtra bootExtra = new BootExtra().setQuotas(new int[] {0,2,3});  // 일시불, 2개월, 3개월 할부 허용, 할부는 최대 12개월까지 사용됨 (5만원 이상 구매시 할부허용 범위)
        Double price = 1000d;
        try {
            //price = Double.parseDouble(edit_price.getText().toString());    //edit_price = 결제금액
            price = Double.parseDouble(String.valueOf(customer_total_price)); //금액 합계
        } catch (Exception e){}


        //PG 회사 선택후 설정되는 문자열 변수다.
        String pg = BootpayKeyValue.getPGCode("KCP");

        //결제수단 선택후 설정되는 문자열 변수다
        String method = BPValue.methodToString("부트페이 간편결제");        //spinner_method = 부트페이 간편결제

        //결제UX 선택후 설정되는 문자열 변수다
        UX ux = UX.valueOf("PG_DIALOG");        // spinner_ux=PG_DIALOG
        Context context = this;

        BootpayAnalytics.init(this, application_id);

        // 사용자가 설정한 결제 설정을 기반으로 결제를 진행하는 역할을 하는 코드다.
        Bootpay.init(getFragmentManager())
                .setContext(context)
                .setApplicationId(application_id) // 해당 프로젝트(안드로이드)의 application id 값
                .setPG(pg) // 결제할 PG 사
                .setBootUser(bootUser)
                .setBootExtra(bootExtra)
//                .setUserPhone("010-1234-5678") // 구매자 전화번호
                .setUX(ux)
                .setMethod(method) // 결제수단
                .setName("payment") // 결제할 상품명
                .setOrderId("1234") // 결제 고유번호expire_month
//                .setAccountExpireAt("2018-09-22") // 가상계좌 입금기간 제한 ( yyyy-mm-dd 포멧으로 입력해주세요. 가상계좌만 적용됩니다. 오늘 날짜보다 더 뒤(미래)여야 합니다 )

                .setPrice(price) // 결제할 금액

                //아래 두 코드는 예제 프로젝트의 코드다.
                .addItem("마우's 스", 1, "ITEM_CODE_MOUSE", 100d) // 주문정보에 담길 상품정보, 통계를 위해 사용
                .addItem("키보드", 1, "ITEM_CODE_KEYBOARD", 200d, "패션", "여성상의", "블라우스") // 주문정보에 담길 상품정보, 통계를 위해 사용

                //.addItem("츄파츕스 마트", 1, "ITEM_CODE_MOUSE", 100d)

                .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                    @Override
                    public void onConfirm(@Nullable String data) {

                        if (true) Bootpay.confirm(data); // 재고가 있을 경우.
                        else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                        Log.d("confirm", data);
                    }
                })
                .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                    @Override
                    public void onDone(@Nullable String data) {
                        Log.d("done", data);

                        Toast myToast = Toast.makeText(MainActivity.this,"결제가 완료되었습니다!!", Toast.LENGTH_SHORT);
                        myToast.show();

                        //구매 완료후 제품목록을 전부 지운다.
                        for(int i=0;i<mArrayList.size();i++){
                                mAdapter.RemoveItem(i,Integer.parseInt(mArrayList.get(i).getProduct_price()));
                        }

                        InsertData task = new InsertData();
                        //task.execute("http://" + IP_ADDRESS + "/insert.php", name,country);
                        task.execute("http://" + "18.191.86.204/yoon_seok/insert.php", user_name,user_address,user_phone,String.valueOf(customer_total_price));

                    }
                })
                .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                    @Override
                    public void onReady(@Nullable String data) {
                        Log.d("ready", data);
                    }
                })
                .onCancel(new CancelListener() { // 결제 취소시 호출
                    @Override
                    public void onCancel(@Nullable String data) {
                        Log.d("cancel", data);
                    }
                })
                .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                    @Override
                    public void onError(@Nullable String data) {

                        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                        Log.d("error", data);
                    }
                })
                .onClose(
                        new CloseListener() { //결제창이 닫힐때 실행되는 부분
                            @Override
                            public void onClose(String data) {
                                Log.d("close", "close");
                            }
                        })
                .request();

    }

    //카메라로 QR코드 스캔이 끝나고 다시 메인액티비티로 돌아왔을때 해야할 일들을 정의한다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Initialize intent data
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );

        if (requestCode == 49374) {
            Log.v("MainActivity","QR코드 상황 로그찍기 성공");
            Intent intent = new Intent(MainActivity.this, Detail_Information.class);
            intent.putExtra("QR_data",intentResult.getContents());
            startActivityForResult(intent, 1);



        }
        else if(requestCode == 1){
            if(resultCode==RESULT_OK){
                String product_name_result = data.getStringExtra("product_name_");
                String product_price_result = data.getStringExtra("product_price_");
                String product_description_result = data.getStringExtra("product_description_");
                String product_url_result = data.getStringExtra("product_url_");
                String product_total_price_result = data.getStringExtra("product_total_price_");
                int product_count__result=data.getIntExtra("product_count_", 1);

                Log.v("MainActivity","QR코드에 있던 데이터들");
                Log.v("MainActivity","product_name_result "+product_name_result);
                Log.v("MainActivity","product_price_result "+product_price_result);
                Log.v("MainActivity","product_description_result "+product_description_result);
                Log.v("MainActivity","product_url_result "+product_url_result);
                Log.v("MainActivity","product_total_price_result "+product_total_price_result);
                Log.v("MainActivity","product_count__result "+product_count__result);

                data2 = new Dictionary(product_url_result,product_name_result,String.valueOf(Integer.parseInt(product_total_price_result)), product_description_result,product_count__result,false);
                data2.setImage(product_url_result);

                //mArrayList.add(0, dict); //RecyclerView의 첫 줄에 삽입
                mArrayList.add(0,data2); // RecyclerView의 마지막 줄에 삽입
                mAdapter.notifyDataSetChanged();
                mAdapter.ToastMessage(MainActivity.this);


                //todo : 더 이상 시간을 지체할 수 없어서 static 을 사용함. 여유되면 고칠것.
                customer_total_price=customer_total_price+Integer.parseInt(product_total_price_result);
                Log.v("mainActivity","customer_total_price "+customer_total_price);
                total_price.setText(String.valueOf(customer_total_price));

            }

        }

        //사용자의 위치를 표현한다.
        else if(requestCode==SEARCH_ADDRESS_ACTIVITY){
            if (resultCode == RESULT_OK) {
                String user_position = data.getExtras().getString("user_position");
                if (user_position != null) {
                    Log.v("MainActivity","user_position : "+user_position);
                    textView.setText(user_position);
                    //address=textView.getText().toString();
                    //Log.v("MainActivity","address : "+address);
                }
            }
        }


    }       //onActivityResult end

    // MySQL 과 통신을 하기위한 AsyncTask
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d("MainActivity", "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {
            Log.v("MainActivity","doInBackgorund 메소드 호출");
            String user_name = (String)params[1];
            String address = (String)params[2];
            String user_phone = (String)params[3];
            String o_total_price = (String)params[4];

            String serverURL = (String)params[0];

            Log.v("MainActivity","user_name "+user_name);
            Log.v("MainActivity","address "+address);
            Log.v("MainActivity","user_phone "+user_phone);
            Log.v("MainActivity","o_total_price "+o_total_price);

            String postParameters = "user_name=" + user_name + "&address=" + address + "&user_phone=" + user_phone + "&o_total_price=" +o_total_price;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("MainActivity", "POST response code - " + responseStatusCode);

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
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d("MainActivity", "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

}