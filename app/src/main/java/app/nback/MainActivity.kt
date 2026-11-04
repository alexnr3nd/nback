package app.nback

import android.app.Activity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        actionBar?.hide()
        val files = assets.list("") ?: emptyArray()
        val page = files.firstOrNull { it.endsWith(".html") }
        if (page == null) {
            val msg = TextView(this)
            msg.text = "Jogo não encontrado. Arquivos na pasta assets: " + files.joinToString()
            msg.setPadding(48, 120, 48, 48)
            setContentView(msg)
            return
        }
        val web = WebView(this)
        web.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = false
            allowContentAccess = false
            blockNetworkLoads = true
            mediaPlaybackRequiresUserGesture = false
        }
        web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(v: WebView, r: WebResourceRequest) = true
        }
        setContentView(web)
        web.loadUrl("file:///android_asset/" + page)
    }
}
