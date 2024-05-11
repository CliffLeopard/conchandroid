package com.cliff.conch.scene

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.ProxyConfig
import androidx.webkit.ProxyController
import androidx.webkit.SafeBrowsingResponseCompat
import androidx.webkit.TracingConfig
import androidx.webkit.TracingConfig.CATEGORIES_WEB_DEVELOPER
import androidx.webkit.TracingController
import androidx.webkit.WebResourceErrorCompat
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewClientCompat
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import com.cliff.conch.databinding.ActivityWebViewScreenShotBinding
import com.cliff.conch.tools.ScreenShooter
import com.orhanobut.logger.Logger
import java.util.concurrent.Executor

class WebViewScreenShotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewScreenShotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewScreenShotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initWebView()
        binding.web.loadUrl("https://www.baidu.com")
        binding.flow.setOnClickListener {
            val bitmap = ScreenShooter.takeFullPageScreenshotOfWebView(binding.web)
            Logger.d(bitmap)
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "RequiresFeature")
    private fun initWebView() {
        binding.swipe.setOnRefreshListener {
            binding.web.reload()
            binding.swipe.isRefreshing = false
        }

        if (WebViewFeature.isFeatureSupported(WebViewFeature.TRACING_CONTROLLER_BASIC_USAGE)) {
            val tracingController = TracingController.getInstance()
            tracingController.start(
                TracingConfig.Builder()
                    .addCategories(CATEGORIES_WEB_DEVELOPER).build()
            )
        }
        WebView.enableSlowWholeDocumentDraw()
        binding.web.apply {
            settings.apply {
                javaScriptEnabled = true
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false // no zoom button
                loadWithOverviewMode = true
                useWideViewPort = true
                domStorageEnabled = true
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setAlgorithmicDarkeningAllowed(this, true)
                }
            }

            webViewClient = CustomWebViewClient(this@WebViewScreenShotActivity)
            webChromeClient = CustomWebChromeClient(this@WebViewScreenShotActivity)
        }

        if (WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROCESS)) {
            Logger.d(TAG, "isMultiProcessEnabled: " + WebViewCompat.isMultiProcessEnabled())
        }

        if (WebViewFeature.isFeatureSupported(WebViewFeature.START_SAFE_BROWSING)) {
            WebViewCompat.startSafeBrowsing(this.applicationContext) { value ->
                Logger.d(TAG, "WebViewCompat.startSafeBrowsing: $value")
            }
        }

        if (WebViewFeature.isFeatureSupported(WebViewFeature.PROXY_OVERRIDE)) {
            val proxyConfig = ProxyConfig.Builder()
                .addProxyRule("proxy1.com")
                .addProxyRule("proxy2.com", ProxyConfig.MATCH_HTTP)
                .addProxyRule("proxy3.com", ProxyConfig.MATCH_HTTPS)
                .addBypassRule("www.google.*") // プロキシ設定除外のホスト
                .build()
            val executor = Executor { Logger.d(TAG, "${Thread.currentThread().name} : executor") }
            val listener = Runnable { Logger.d(TAG, "${Thread.currentThread().name} : listener") }
            ProxyController.getInstance().setProxyOverride(proxyConfig, executor, listener)
            ProxyController.getInstance().clearProxyOverride(executor, listener)
        }
    }

    inner class CustomWebViewClient internal constructor(private val activity: WebViewScreenShotActivity) :
        WebViewClientCompat() {
        override fun onPageCommitVisible(view: WebView, url: String) {
            super.onPageCommitVisible(view, url)
            Logger.d(TAG, "onPageCommitVisible: $url")
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceErrorCompat
        ) {
            super.onReceivedError(view, request, error)
            Logger.d(TAG, "onReceivedError: $error")
        }

        override fun onReceivedHttpError(
            view: WebView,
            request: WebResourceRequest,
            errorResponse: WebResourceResponse
        ) {
            super.onReceivedHttpError(view, request, errorResponse)
            Logger.d(TAG, "onReceivedHttpError: $errorResponse")
        }

        override fun onSafeBrowsingHit(
            view: WebView,
            request: WebResourceRequest,
            threatType: Int,
            callback: SafeBrowsingResponseCompat
        ) {
            super.onSafeBrowsingHit(view, request, threatType, callback)
            Logger.d(TAG, "onSafeBrowsingHit: $threatType")
        }

        //
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
            Logger.d(TAG, "shouldOverrideUrlLoading: $url")
            return false
        }

        override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
            super.doUpdateVisitedHistory(view, url, isReload)
            Logger.d(TAG, "doUpdateVisitedHistory: $activity.webView.url")
        }
    }

    inner class CustomWebChromeClient internal constructor(private val activity: WebViewScreenShotActivity) :
        WebChromeClient() {

        override fun onJsAlert(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            return false
        }

        override fun onJsPrompt(
            view: WebView?,
            url: String?,
            message: String?,
            defaultValue: String?,
            result: JsPromptResult?
        ): Boolean {
            return false
        }

        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            view ?: return false
            val href = view.handler.obtainMessage()
            view.requestFocusNodeHref(href)
            val url = href.data.getString("url")
            view.stopLoading()
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity.startActivity(browserIntent)
            return true
        }
    }

    companion object {
        const val TAG = "WebViewScreenShotActivity"
    }
}

//        enableEdgeToEdge()
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }