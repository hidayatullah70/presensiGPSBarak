package com.barak.presensi;

import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int checkCount = 0;

    private final Runnable hideBannerRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (getBridge() != null) {
                    WebView webView = getBridge().getWebView();
                    if (webView != null) {
                        String js = "(function() {" +
                                "var el = document.getElementById('warning-bar-table');" +
                                "if (el) el.style.setProperty('display', 'none', 'important');" +
                                "var w = document.getElementById('warning');" +
                                "if (w) w.style.setProperty('display', 'none', 'important');" +
                                "var st = document.getElementById('hide-gas-banner-css');" +
                                "if (!st && document.head) {" +
                                "  st = document.createElement('style');" +
                                "  st.id = 'hide-gas-banner-css';" +
                                "  st.innerHTML = '#warning-bar-table, #warning, .warning-bar { display: none !important; height: 0 !important; max-height: 0 !important; overflow: hidden !important; }';" +
                                "  document.head.appendChild(st);" +
                                "}" +
                                "})()";
                        webView.evaluateJavascript(js, null);
                    }
                }
            } catch (Exception ignored) {}

            checkCount++;
            if (checkCount < 30) {
                handler.postDelayed(this, 500);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        checkCount = 0;
        handler.post(hideBannerRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(hideBannerRunnable);
    }
}
