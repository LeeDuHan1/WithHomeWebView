package com.withhome360.www.withhomewebview;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    public WebView webView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
//        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        settings.setJavaScriptEnabled(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setPluginState(WebSettings.PluginState.ON);
//        settings.setSupportMultipleWindows(true);
        settings.setCacheMode(settings.LOAD_NO_CACHE);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDomStorageEnabled(true);

        webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        progressDialog = new ProgressDialog(this);

        CustomWebVeiwClient viewClient = new CustomWebVeiwClient(this, progressDialog);
        CustomWebChromeClient chromeClient = new CustomWebChromeClient(this,webView);
        webView.setWebViewClient(viewClient);
        webView.setWebChromeClient(chromeClient);


        setContentView(webView);
        webView.loadUrl("http://www.withhome360.com");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack() ){
            webView.goBack();
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (!webView.canGoBack())){
            new AlertDialog.Builder(this)
                    .setTitle("프로그램 종료")
                    .setMessage("프로그램을 종료하시겠습니까?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    })
                    .setNegativeButton("아니오",  null).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}
