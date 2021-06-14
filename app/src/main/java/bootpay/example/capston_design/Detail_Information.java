package bootpay.example.capston_design;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capston_design.R;

import static java.lang.Integer.parseInt;

public class Detail_Information extends Activity {

    //아래 두 TextView 는 사용자가 구매할 상품의 개수를 조절하는 버튼이다.
    TextView minus_btn;
    TextView plus_button;

    //사용자가 구매할 상품들의 총 가격을 의미하는 TextView다.
    TextView total_price;

    //상품에대한 상세설명을 표현하는 TextView이다.
    TextView prodcut_explain;

    //사용자가 구매할 상품의 개수를 표현하는TextView 다.
    TextView prdouct_count_txt;

    //상품명을 표현하는 TextView이다
    TextView prodcut_name;

    //사용자가 상품을 구매목록에 추가하는데 동의를 하는 버튼
    TextView yes;

    //사용자가 상품을 구매목록에 추가하는데 반대 하는 버튼
    ImageView textView7;

    //상품이미지를 표현하는 ImageView이다.
    ImageView prodcut_image;

    // 사용자가 구매할 물건의 개수를 표현한 변수다.
    int product_count=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__information);

        prodcut_name = (TextView) findViewById(R.id.prodcut_name);
        total_price = (TextView) findViewById(R.id.total_price);
        prodcut_explain = (TextView) findViewById(R.id.prodcut_explain);
        minus_btn = (TextView) findViewById(R.id.minus_btn);
        plus_button = (TextView) findViewById(R.id.plus_button);
        prdouct_count_txt = (TextView) findViewById(R.id.prdouct_count);
        yes = (TextView) findViewById(R.id.yes);
        textView7 = (ImageView) findViewById(R.id.textView7);
        prodcut_image=(ImageView) findViewById(R.id.prodcut_image);


        prdouct_count_txt.setText(String.valueOf(product_count));



        Intent secondIntent = getIntent();

        //QR 코드로 받아온 문자열 데이터다.
        String qr_data=secondIntent.getStringExtra("QR_data");

        ////////////////////// 아래 코드들은 QR코드에 담긴 상품의 정보(상품명,가격,상품정보,이미지url)를 추출해내기 위한 절차다////////

        Log.v("MainActivity","intentResult.getContents()"+qr_data);
        // @@@를 기준으로 문자열을 추출할 것이다. 먼저 @@@ 의 인덱스를 찾는다
        int idx = qr_data.indexOf("@@@");
        // 상품명을 추출해서 변수에 담는다
        String product_name = qr_data.substring(0, idx);
        // 상품명을 제외한 뒷부분을 추출
        // 아래 substring은 @@@ 바로 뒷부분인 n부터 추출된다.
        String total_data = qr_data.substring(idx+3);
        // @@@를 기준으로 문자열을 추출할 것이다. 이번에는 상품가격을 추출한다.
        idx = total_data.indexOf("@@@");
        // 상품가격을 추출해서 변수에 담는다
        String product_price = total_data.substring(0, idx);
        // @@@를 기준으로 문자열을 추출할 것이다. @@@ 의 인덱스를 찾는다
        idx = total_data.indexOf("@@@");
        // 상품가격뒤의 @@@을 제외한 뒷부분을 추출. 이 변수에는 상품설명과 상품의 이미지url 이 담긴다.
        // 아래 substring은 @@@ 바로 뒷부분인 n부터 추출된다.
        String total_data2 = total_data.substring(idx+3);
        idx = total_data2.indexOf("@@@");
        // 상품정보를 추출해서 변수에 담는다
        String product_description = total_data2.substring(0, idx);
        // 상품url 정보를 추출해서 변수에 담는다.
        String product_url=total_data2.substring(idx+3);

        ////////////////////// 위의 코드들은 QR코드에 담긴 상품의 정보(상품명,가격,상품정보,이미지url)를 추출해내기 위한 절차다////////

        //추출해낸 결과들을 결과로 확인한다.
        Log.v("Detail_Information","product_name : "+product_name);
        Log.v("Detail_Information","product_price : "+product_price);
        Log.v("Detail_Information","product_description : "+product_description);
        Log.v("Detail_Information","product_url : "+product_url);

        //받아온 QR코드 데이터를 바탕으로 상품명을 출력한다.
        prodcut_name.setText(product_name);

        //ImageView 에 웹에서 갖고온 이미지를 표현하기위해 glide 라이브러리를 참조하였다. (참고 사이트 : https://jizard.tistory.com/179)
        // Glide로 이미지 표시하기
        Glide.with(this).load(product_url).into(prodcut_image);

        //받아온 QR코드 데이터를 바탕으로 상품상세 설명을 출력한다.
        prodcut_explain.setText(product_description);

        //총 금액을 표현한다.
        total_price.setText(String.valueOf(product_count*parseInt(product_price)));

        //구매 취소
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast myToast = Toast.makeText(getApplicationContext(),"구매를 취소하였습니다", Toast.LENGTH_SHORT);
                myToast.show();
            }
       });



        //사용자가 "네"를 선택
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra("product_name_", product_name);
                resultIntent.putExtra("product_price_", product_price);
                resultIntent.putExtra("product_description_", product_description);
                resultIntent.putExtra("product_url_", product_url);
                //resultIntent.putExtra("product_total_price_", String.valueOf(product_count*parseInt(product_price)));
                resultIntent.putExtra("product_total_price_", String.valueOf(product_count*parseInt(product_price)));
                resultIntent.putExtra("product_count_", product_count);


                setResult(RESULT_OK, resultIntent);

                finish();   // 현재 엑티비티종료.

                //Intent myIntent = new Intent(Detail_Information.this, MainActivity.class);
            }
        });

        //사용자가 구매할 물건의 개수를 -1 시키는 버튼이다. 최소 1개 이상의 물품을 선택해야 한다.
        minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_count--;
                if(product_count<=0){
                    product_count=1;
                    Toast myToast = Toast.makeText(getApplicationContext(),"최소한 한개이상의 물건을 구매하셔야 합니다!", Toast.LENGTH_SHORT);
                    myToast.show();

                    //총 금액을 표현한다.
                    total_price.setText(String.valueOf(product_count*parseInt(product_price)));
                }
                else{
                    prdouct_count_txt.setText(String.valueOf(product_count));

                    //총 금액을 표현한다.
                    total_price.setText(String.valueOf(product_count*parseInt(product_price)));
                }
            }
        });

        //사용자가 구매할 물건의 개수를 +1 시키는 버튼이다.
        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_count++;
                prdouct_count_txt.setText(String.valueOf(product_count));

                //총 금액을 표현한다.
                total_price.setText(String.valueOf(product_count*parseInt(product_price)));
            }
        });


    }       //onCreate endZ

}