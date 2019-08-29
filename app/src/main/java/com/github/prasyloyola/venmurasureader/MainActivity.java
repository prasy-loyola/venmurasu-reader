package com.github.prasyloyola.venmurasureader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Pattern imageUrlPattern = Pattern.compile("(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png|swg)", Pattern.CASE_INSENSITIVE);
    private WebView webView;
    private String startingUrl = "https://venmurasu.in/%e0%ae%b5%e0%af%86%e0%ae%a3%e0%af%8d%e0%ae%ae%e0%af%81%e0%ae%b0%e0%ae%9a%e0%af%88-%e0%ae%86%e0%ae%b0%e0%ae%ae%e0%af%8d%e0%ae%aa%e0%ae%a4%e0%af%8d%e0%ae%a4%e0%ae%bf%e0%ae%b2%e0%af%8d-%e0%ae%87%e0%ae%b0/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.mainWebView);
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {


                if (android.util.Patterns.WEB_URL.matcher(url).matches() && !imageUrlPattern.matcher(url).matches() ) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("lastViewedUrl", url);
                    editor.commit();
                }
//                else if(url != null && imageUrlPattern.matcher(url).matches() ){
//
//                    view.loadDataWithBaseURL(null, "<style>img{display: inline;height: auto;max-width: 100%;}</style>"+String.format("<img src=\"%s\"/>", url), "text/html", "UTF-8", sharedPreferences.getString("lastViewedUrl",startingUrl));
//                }
            }


        });
        String url = sharedPreferences.getString("lastViewedUrl", startingUrl);
        url = (!android.util.Patterns.WEB_URL.matcher(url).matches() || imageUrlPattern.matcher(url).matches() )? startingUrl : url;
        webView.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


}
