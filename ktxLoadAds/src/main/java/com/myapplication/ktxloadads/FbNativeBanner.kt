/*
 * Copyright (C) 2020 Grow Solution
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myapplication.kotlinadsloader

import android.content.Context
import android.util.Log
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.NativeAdListener
import com.facebook.ads.NativeBannerAd

/**
 * Created by grow solution on 2/12/2020.
 * display Fb Native sdk Ads ..
 * Method for load FB Native banner Ads ..
 **/

object FbNativeBanner {
    var isFbNativeAdLoaded = false
    var nativeBannerAd: NativeBannerAd? = null


    fun pre_load_Ad(context: Context?, fb_native_banner: String?) {
        loadAd(context, fb_native_banner)
    }

    fun loadAd(context: Context?, fb_native_banner: String?) {
        isFbNativeAdLoaded = false
        //AdSettings.addTestDevice("9f34c1b2-f8cf-4d74-9045-4284496c1a75");
        loadFbNative(context, fb_native_banner)
    }
    fun getFbNativeAd(context: Context?): NativeBannerAd? {
        Log.w("msg", "fb loadNativeAd")
        return try {
            if (nativeBannerAd != null) {
                if (nativeBannerAd!!.isAdLoaded()) {
                    nativeBannerAd
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun loadFbNative(context: Context?, fb_native_banner: String?) {
        try {
            nativeBannerAd = NativeBannerAd(context, fb_native_banner)
            /*  nativeBannerAd.setAdListener(new NativeAdListener() {
                 @Override
                 public void onMediaDownloaded(Ad ad) {
                     Log.e("msg", "Native ad finished downloading all assets.");
                 }

                 @Override
                 public void onError(Ad ad, AdError adError) {
                     Log.w("msg", "AdError==" + adError.getErrorMessage());
                     isFbNativeAdLoaded = false;
                 }

                 @Override
                 public void onAdLoaded(Ad ad) {
                     Log.w("msg", "Fb_Native_load");
                     isFbNativeAdLoaded = true;
                 }

                 @Override
                 public void onAdClicked(Ad ad) {
                 }

                 @Override
                 public void onLoggingImpression(Ad ad) {
                 }
             });
             nativeBannerAd.loadAd();*/
            val nativeAdListener: NativeAdListener = object : NativeAdListener {
                override fun onMediaDownloaded(ad: Ad) {
                    // Native ad finished downloading all assets
                }

                override fun onError(ad: Ad, adError: AdError) {
                    // Native ad failed to load
                    isFbNativeAdLoaded = false
                }

                override fun onAdLoaded(ad: Ad) {
                    // Native ad is loaded and ready to be displayed
                    isFbNativeAdLoaded = true
                }

                override fun onAdClicked(ad: Ad) {
                    // Native ad clicked
                }

                override fun onLoggingImpression(ad: Ad) {
                    // Native ad impression
                }
            }

            // Request an ad
            nativeBannerAd!!.loadAd(
                nativeBannerAd!!.buildLoadAdConfig()
                    .withAdListener(nativeAdListener)
                    .build()
            )
        } catch (e: java.lang.Exception) {
        }
    }

}