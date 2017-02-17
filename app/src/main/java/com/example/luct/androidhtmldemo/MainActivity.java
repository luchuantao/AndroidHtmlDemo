package com.example.luct.androidhtmldemo;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * HTML中的javascript脚本调用Android本地代码
 * Android 调用HTML中的javascript脚本并传递参数
 * HTML中的javascript脚本调用Android本地代码并传递参数
 * 实现Android调用JS脚本是非常简单的，直接Webview调用loadUrl方法，
 * 里面是JS的方法名，并可以传入参数，
 * javascript：xxx()方法名需要和JS方法名相同
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //启用javaScript
        webview.getSettings().setJavaScriptEnabled(true);
        //从 assets目录下面加载HTML
        webview.loadUrl("file:///android_asset/web.html");
        /**
         * webview绑定javascriptInterface
         * 第一个参数是自定义类对象，映射成JS对象，这里我直接传this，
         * 第二个参数是别名，JS脚本通过这个别名来调用java的方法，这个别名跟HTML代码中也是对应的。
         * onclick="window.android.startFunction()"  都是 android
         */
        webview.addJavascriptInterface(MainActivity.this, "android");
    }

    @OnClick({R.id.button, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                webview.loadUrl("javascript:javacalljs()");
                break;
            case R.id.button2:
                webview.loadUrl("javascript:javacalljswith(" + "'调用有参函数'" + ")");
                break;
        }
    }

    @JavascriptInterface
    public void startFunction() {
        Toast.makeText(MainActivity.this, "show", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void startFunction(final String text) {
        new AlertDialog.Builder(MainActivity.this).setMessage(text).show();
    }
}
