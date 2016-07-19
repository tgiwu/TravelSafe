package com.huanqiu.travelsafe.utils;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.BuildConfig;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/6/12.
 */
public class Utils {
    public static String buildUserAgent() {
        StringBuilder stringBuilder = new StringBuilder("ezutrip.com");
        stringBuilder.append("/");
        stringBuilder.append(BuildConfig.VERSION_NAME);
        stringBuilder.append("_");
        stringBuilder.append(BuildConfig.VERSION_CODE);
        stringBuilder.append("/Android/");
        stringBuilder.append(Build.VERSION.RELEASE);
        stringBuilder.append("/");
        stringBuilder.append(Build.MODEL);
        stringBuilder.append("/");
        TelephonyManager telephonyManager = (TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        stringBuilder.append(telephonyManager.getDeviceId());
        return stringBuilder.toString();
    }

    public static String buildDebugAuth() {
        return Base64.encodeToString((Constants.DEBUG_KEY + ":" + Constants.DEBUG_SECRET).getBytes(), Base64.DEFAULT);
    }

    public static boolean checkPhoneNumber(String number) {
        boolean result = true;
        result &= !TextUtils.isEmpty(number);
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        result &= p.matcher(number).matches();
        return result;
    }

    public static boolean checkPassword(String password) {
        boolean result = true;
        result &= !TextUtils.isEmpty(password);
        Pattern pattern = Pattern.compile("^[a-zA-Z]\\w{5,15}$");
        result &= pattern.matcher(password).matches();
        return result;
    }

    public static boolean checkAuth(String auth) {
        boolean result = true;
        result &= !TextUtils.isEmpty(auth);
        Pattern pattern = Pattern.compile("^[0-9]*$");
        result &= pattern.matcher(auth).matches();
        return result;
    }

    public static boolean checkNickname(String nickname) {
        return true;
    }

    public static String getBaiduTranslationSign(String q, int randomInt) {
        String seed = Constants.BAIDU_TRANSLATION_APP_ID + q + randomInt + Constants.BAIDU_TRANSLATION_KEY;
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(seed.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();

    }
}
