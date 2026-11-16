package com.barak.presensi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int checkCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWebView();
    }

    private void setupWebView() {
        try {
            if (getBridge() != null) {
                WebView webView = getBridge().getWebView();
                if (webView != null) {
                    WebSettings settings = webView.getSettings();
                    settings.setJavaScriptEnabled(true);
                    settings.setDomStorageEnabled(true);
                    settings.setDatabaseEnabled(true);
                    settings.setMediaPlaybackRequiresUserGesture(false);
                    CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
                }
            }
        } catch (Exception ignored) {}
    }

    private final Runnable hideBannerRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (getBridge() != null) {
                    WebView webView = getBridge().getWebView();
                    if (webView != null) {
                        // Sembunyikan HANYA baris peringatan di paling atas, jangan sembunyikan seluruh table karena iframe aplikasi ada di baris kedua table tersebut
                        String js = "(function() {" +
                                "var w = document.getElementById('warning');" +
                                "if (w) w.style.setProperty('display', 'none', 'important');" +
                                "var st = document.getElementById('hide-gas-banner-css');" +
                                "if (!st && document.head) {" +
                                "  st = document.createElement('style');" +
                                "  st.id = 'hide-gas-banner-css';" +
                                "  st.innerHTML = '#warning, .warning-bar, #warning-bar-table > tbody > tr:first-child, #warning-bar-table > tr:first-child { display: none !important; height: 0 !important; max-height: 0 !important; overflow: hidden !important; }';" +
                                "  document.head.appendChild(st);" +
                                "}" +
                                "})()";
                        webView.evaluateJavascript(js, null);
                    }
                }
            } catch (Exception ignored) {}

            checkCount++;
            if (checkCount < 20) {
                handler.postDelayed(this, 500);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        setupWebView();
        checkCount = 0;
        handler.post(hideBannerRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(hideBannerRunnable);
    }
}
