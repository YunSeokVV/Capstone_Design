package bootpay.example.capston_design;
//이 클래스는 다음 위치 서비스 API 를 사용하기위한 액티비티다.
//출처 : https://onlyfor-me-blog.tistory.com/213
//html 파일은 이호민의 apache 에 daum.html 에 저장되있다.
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.capston_design.R;

public class WebViewActivity extends Activity {

    private WebView browser;

    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String user_position) {
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("user_position", user_position);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        browser = (WebView) findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                browser.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        browser.loadUrl("http://18.191.86.204/daum.html");
    }
}