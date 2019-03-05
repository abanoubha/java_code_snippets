package com.abanoubhanna.java;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;

public class Code extends AppCompatActivity {

    static String codeNo;
    CodeView codeView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_view);

        findViewById(R.id.linearLayout).setHorizontalScrollBarEnabled(true);

        codeView = (CodeView) findViewById(R.id.code_view);
        codeNo = Objects.requireNonNull(getIntent().getExtras()).getString("snippet");
        showCode(findViewById(R.id.javac));

        MobileAds.initialize(this, "ca-app-pub-4971969455307153~7831315762");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4971969455307153/1680840380");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public String getCode(String inFile) {
        String tContents = "";
        try {
            InputStream stream = getAssets().open(inFile);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }
        return tContents;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Intent back = new Intent(Code.this,MainActivity.class);
            startActivity(back);
            finish();
        }
    }

    public void showCode(View v){
        String script = getCode("code" + codeNo);
        codeView.setOptions(Options.Default.get(this)
                .withLanguage("java")
                .withCode(script)
                .withTheme(ColorTheme.MONOKAI)
        );

    }
    public void showXml(View v){
        String xml = getCode("view"+codeNo);
        codeView.setOptions(Options.Default.get(this)
                .withLanguage("xml")
                .withCode(xml)
                .withTheme(ColorTheme.MONOKAI)
        );
    }

    void copy2Clipboard(CharSequence text){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("copy text", text);
        if (clipboard != null){
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(this," Code Copied ", Toast.LENGTH_LONG).show();
    }

    public void copyCode(View v) {
        copy2Clipboard(getCode("code"+codeNo));
    }

    public void copyXml(View v) {
        copy2Clipboard(getCode("view"+codeNo));
    }
}
