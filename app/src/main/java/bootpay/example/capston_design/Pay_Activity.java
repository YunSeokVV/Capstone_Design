package bootpay.example.capston_design;
//이 액티비티에서는 웹뷰를 사용해 이니시스 결제 api 를 실행한다.
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capston_design.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class Pay_Activity extends Activity {
//    final String application_id = "5c99d9b8b6d49c516e19099a"; //dev
//    final String application_id = "5b9f51264457636ab9a07cdc"; //dev2
//private ApiPresenter presenter;
//    ApiPresenter present;

//    private final String application_id = "5b14c0ffb6d49c40cda92c4e"; //pro
//    private final String application_id = "c42bf24f74b40034c5f484"; //pro

    //내꺼
    //private String application_id = "609b9a9fd8c1bd001e2bad45";
    //예제거
    private String application_id = "59a4d326396fa607cbe75de5";
    Context context;

    Spinner spinner_pg;
    Spinner spinner_method;
    Spinner spinner_ux;
    EditText edit_price;
    EditText edit_non_tax;

    //사용자의 전화번호를 입력하는 EditText 이다.
    EditText user_phone_number;

    //사용자의 이름을 입력하는 EditText
    EditText customer_name;

    //사용자의 상세주소지를 입력하는 EditText
    EditText user_detail_address;

    //사용자의 주소지를 설정하는 텍스트뷰
    TextView user_address;

    //사용자의 정보들을 저장하는 버튼
    Button btn_save;

    //Daum 위치 API 를 사용하고 인텐트 결과를 받아오기위해 필요한 상수다.
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    //배송자의 정보들(배송지, 이름, 전화번호)을 불러온다. 만약 아무런 값도 없다면 '결제하기' 를 누른 시점에 설정시키게 하고 값을 저장한다.
    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor; // 에디터

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_);
        BootpayAnalytics.init(this, application_id);

        Log.v("Pay_Acitivity","onCreate 호출");

        this.context = this;

        // 1. Shared Preference 초기화
        pref = getSharedPreferences("user_data", Activity.MODE_PRIVATE);
        editor = pref.edit();

        spinner_pg = findViewById(R.id.spinner_pg);
        spinner_method = findViewById(R.id.spinner_method);
        spinner_ux = findViewById(R.id.spinner_ux);
        edit_price = findViewById(R.id.edit_price);
        edit_non_tax = findViewById(R.id.edit_non_tax);
        user_address = findViewById(R.id.user_address);
        btn_save = findViewById(R.id.btn_save);
        user_detail_address = findViewById(R.id.user_detail_address);
        customer_name = findViewById(R.id.customer_name);
        user_phone_number = findViewById(R.id.user_phone_number);

        //사용자의 배송지를 설정하는 TextView 다. 클릭하면 Daum 주소설정 api 를 사용하게 된다.
        user_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Pay_Activity.this, WebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });

        //사용자의 배송지를 설정하는 TextView 다. 클릭하면 Daum 주소설정 api 를 사용하게 된다.
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("user_address", user_address.getText().toString()+user_detail_address.getText().toString());
                editor.putString("user_name", customer_name.getText().toString());
                editor.putString("user_phone", user_phone_number.getText().toString());
                editor.apply();

                Log.v("MainAcitivty","xcv "+user_address+user_detail_address.getText().toString());

                finish();
            }
        });

    }       //onCreate end


    //결제 기능 실행
    public void goRequest(View v) {
//        runOnUiThread();

//        BootpayRestService

//        Spinner mySpinner = (Spinner) findViewById(R.id.your_spinner);
//        String text = mySpinner.getSelectedItem().toString();
        BootUser bootUser = new BootUser().setPhone("010-1234-5678"); // 구매자 정보
        BootExtra bootExtra = new BootExtra().setQuotas(new int[] {0,2,3});  // 일시불, 2개월, 3개월 할부 허용, 할부는 최대 12개월까지 사용됨 (5만원 이상 구매시 할부허용 범위)
        Double price = 1000d;
        try {
            price = Double.parseDouble(edit_price.getText().toString());        //edit_price = 결제금액
        } catch (Exception e){}


        //PG 회사 선택후 설정되는 문자열 변수다.
        String pg = BootpayKeyValue.getPGCode(spinner_pg.getSelectedItem().toString());

        //결제수단 선택후 설정되는 문자열 변수다
        String method = BPValue.methodToString(spinner_method.getSelectedItem().toString());

        //결제UX 선택후 설정되는 문자열 변수다
        UX ux = UX.valueOf(spinner_ux.getSelectedItem().toString());
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
                .setName("괴물쥐화이팅") // 결제할 상품명
                .setOrderId("1234") // 결제 고유번호expire_month
//                .setAccountExpireAt("2018-09-22") // 가상계좌 입금기간 제한 ( yyyy-mm-dd 포멧으로 입력해주세요. 가상계좌만 적용됩니다. 오늘 날짜보다 더 뒤(미래)여야 합니다 )

                .setPrice(price) // 결제할 금액
                .addItem("마우's 스", 1, "ITEM_CODE_MOUSE", 100d) // 주문정보에 담길 상품정보, 통계를 위해 사용
                .addItem("키보드", 1, "ITEM_CODE_KEYBOARD", 200d, "패션", "여성상의", "블라우스") // 주문정보에 담길 상품정보, 통계를 위해 사용
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
                        Log.v("MainActivity","결제가 완료됐다");
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

        //사용자의 위치를 표현한다.
        if(requestCode==SEARCH_ADDRESS_ACTIVITY){
            if (resultCode == RESULT_OK) {
                String user_position = data.getExtras().getString("user_position");
                if (user_position != null) {
                    Log.v("MainActivity","user_position : "+user_position);

                    user_address.setText(user_position);
                }
            }
        }


    }       //onActivityResult end

}

