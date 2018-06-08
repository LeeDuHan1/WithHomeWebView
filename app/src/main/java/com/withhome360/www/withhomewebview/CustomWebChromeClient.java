package com.withhome360.www.withhomewebview;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;

/**
 * Created by BARto on 2017-10-20.
 */

public class CustomWebChromeClient extends WebChromeClient {
    private View mCustomView;
    private Activity mActivity;
    private int mOriginalOrientation;
    private FullscreenHolder mFullscreenContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCollback;
    private WebView mWebView;

    public CustomWebChromeClient(Activity activity, WebView webView) {
        this.mActivity = activity;
        this.mWebView = webView;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        result.confirm();
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        if (mCustomView != null) {
            callback.onCustomViewHidden();
            return;
        }
        mOriginalOrientation = mActivity.getRequestedOrientation();
        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
        Log.d("뷰크기",decor.getHeight()+" ");

        FrameLayout.LayoutParams flp =  new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,decor.getHeight());

        flp.height = decor.getHeight()-40;
        flp.setMargins(0,40,0,0);

        mFullscreenContainer = new FullscreenHolder(mActivity);
        mFullscreenContainer.addView(view, flp);
        decor.addView(mFullscreenContainer, flp);
        mCustomView = view;
        mCustomViewCollback = callback;
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
        if (mCustomView == null) {
            return;
        }

        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
        decor.removeView(mFullscreenContainer);
        mFullscreenContainer.removeAllViews();
//        mFullscreenContainer = null;
        mCustomView = null;
        mCustomViewCollback.onCustomViewHidden();
        mWebView.reload();
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

//    public void setRequestedOrientation(int orientation){
//        mActivity.setRequestedOrientation(orientation);
//    }

    private static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

}
