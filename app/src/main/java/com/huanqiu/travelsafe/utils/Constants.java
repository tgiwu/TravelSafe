package com.huanqiu.travelsafe.utils;

import android.net.Uri;

import com.nuance.speechkit.PcmFormat;

/**
 * Created by Administrator on 2016/7/1.
 */
public class Constants {
    public static String REQUEST_VERIFITION_CODE_FORGET = "forget";
    public static String REQUEST_VERIFITION_CODE_REGISTRY = "start_registry_bg";
    public static String DEBUG_KEY = "api";
    public static String DEBUG_SECRET = "12345";

    public static final String NUANCE_APP_KEY = "6614df15fa3f48d2f30f2db782e29e42221ee491222e9a7d3d58ecf3f693185ca8f872a237ba752bcaa2e24c85818ea79f7338c2a1d23fde2a2bd220beea7d66";
    public static final String NUANCE_APP_ID = "NMDPTRIAL_third_hqvoyage_com20160718033704";
    public static final String NUANCE_SERVER_HOST = "sslsandbox.nmdp.nuancemobility.net";
    public static final String NUANCE_SERVER_PORT = "443";
    public static final Uri NUANCE_SERVER_URI = Uri.parse("nmsps://" + NUANCE_APP_ID + "@" + NUANCE_SERVER_HOST + ":" + NUANCE_SERVER_PORT);

    public static final PcmFormat PCM_FORMAT = new PcmFormat(PcmFormat.SampleFormat.SignedLinear16, 16000, 1);

    public static final String BAIDU_TRANSLATION_APP_ID = "20160716000025316";
    public static final String BAIDU_TRANSLATION_KEY = "JjaPM9wDzGxoRpPKLCZd";
}
