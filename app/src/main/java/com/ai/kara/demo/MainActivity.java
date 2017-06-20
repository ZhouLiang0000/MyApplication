package com.ai.kara.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView webview;
    private String cacheDirPath = null;
    private String url;
    private static final String APP_CACHE_DIRNAME = "/webcache";
    private String oldCookie = "keyticketCookieName"+"="+"我是ket值&我是拼接的ticket";
    private String keyCookie = "keyCookieName"+"="+"我是ket";
    private String ticketCookie = "ticketCookieName"+"="+"我是ticket";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        url = "http://10.19.15.37:14501/mobileapps/kara-workorder-mobileweb";
        cacheDirPath = this.getCacheDir().getAbsolutePath() + APP_CACHE_DIRNAME;
        webview = (WebView) findViewById(R.id.webview);
        /**
         * 设置此WebView支持js
         */
        webview.getSettings().setJavaScriptEnabled(true);
        /**
         * 解决网页https中含有http资源加载不出来，从5.0之后不允许混合模式
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /**
         * 设置是否加载网页图片
         */
        webview.getSettings().setBlockNetworkImage(false);
        /**
         * 设置javascript来自动打开的窗口
         */
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        /**
         * 开启 database storage API 功能
         */
        webview.getSettings().setDatabaseEnabled(true);
        /**
         * 设置网页自适应屏幕
         */
        webview.getSettings().setSupportZoom(true);
        /************* 开启 DOM storage API 功能start ******************/
        webview.getSettings().setDomStorageEnabled(true);
        webview.requestFocus();
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setLoadsImagesAutomatically(true);
        /************* 开启 DOM storage API 功能end ********************/
        /**
         * 滚动条水平不显示
         */
        webview.setHorizontalScrollBarEnabled(false);
        /**
         * 滚动条垂直不显示
         */
        webview.setVerticalScrollBarEnabled(false);
        /**
         * 设置编码格式
         */
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        /**
         * 启用地理定位
         */
        webview.getSettings().setGeolocationEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        /**
         * 设置定位的数据库路径
         */
        webview.getSettings().setGeolocationDatabasePath(dir);
        /**
         * 没有setSupportMultipleWindows的话window.open不会增加新窗口
         */
        webview.getSettings().setSupportMultipleWindows(true);
        /**
         * 开启Application Cache功能
         */
        webview.getSettings().setAppCacheEnabled(true);
        /**
         * 设置提高渲染的优先级
         */
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        /**
         * 设置缓存的模式 LOAD_CACHE_ONLY:不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT:根据cache-control决定是否从网络上取数据。
         * LOAD_CACHE_NORMAL:APIlevel17中已经废弃,从API level 11开始作用同LOAD_DEFAULT模式
         * LOAD_NO_CACHE:不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        /**
         * 设置数据库缓存路径
         */
        webview.getSettings().setDatabasePath(cacheDirPath);
        /**
         * 设置 Application Caches 缓存目录
         */
        webview.getSettings().setAppCachePath(cacheDirPath);
        /**
         * 设置可以访问文件
         */
        webview.getSettings().setAllowFileAccess(true);
        /********* 解决了加载地图白屏的问题start *********/
        webview.getSettings().setAllowContentAccess(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        /**
         * 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
         */
        webview.setWebViewClient(new MyWebViewClient());
        showUrl(url);
    }
    /**
     * @Title webViewClient
     * @Description 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
     * @author 周亮
     * @version 1.0 2015年7月21日下午2:56:30
     */
    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
        {
            handler.proceed();// 接受证书支持https链接
        }
    }
    private void showUrl(String url){
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, oldCookie+"; path=/");
        cookieManager.setCookie(url, keyCookie+"; domain=.asiainfo.com; path=/");
        cookieManager.setCookie(url, ticketCookie+"; domain=.asiainfo.com; path=/");
        webview.loadUrl(url);
    }
}
