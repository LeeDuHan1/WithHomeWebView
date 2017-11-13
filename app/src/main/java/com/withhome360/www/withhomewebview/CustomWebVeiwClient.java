package com.withhome360.www.withhomewebview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by BARto on 2017-10-19.
 */

public class CustomWebVeiwClient extends WebViewClient {
    ProgressDialog progressDialog;
    Activity activity;

    public CustomWebVeiwClient(Activity activity, ProgressDialog progressDialog){
        this.progressDialog = progressDialog;
        this.activity = activity;
    }
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(url != null && url.startsWith("tel:")){
            try{
                Intent intent_call = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                activity.startActivity(intent_call);
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            view.loadUrl(url);
        }
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        progressDialog.dismiss();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failIngUrl) {
        Toast.makeText(activity, "서버에 연결할 수 없습니다.", Toast.LENGTH_LONG).show();
    }
}

