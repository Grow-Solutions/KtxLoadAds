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
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd

/**
 * Created by grow solution on 2/12/2020.
 * display Big Admob NativeAd Ads ..
 * Method for load Big Admob NativeAd Ads ..
 **/

object BigAdmobNativeAdLoader {
    var isAdmobNativeAdLoaded = false
    var AdmobNativeAd: UnifiedNativeAd? = null
    var mcallback: adCallback? = null

    fun loadAd(
        context: Context?,
        admob_native: String?,
        admob_native_reLoad: String?,
        mcallback1: adCallback?
    ) {
        Log.w("msg", "Greedy  Admob BIG Native loadAd ")
        mcallback = mcallback1
        if (AdmobNativeAd == null) {
            loadNativeAds(context, admob_native, admob_native_reLoad)
        } else {
            mcallback!!.adLoaded()
        }
    }

    fun reloadAds(context: Context?, admob_native: String?, admob_native_reLoad: String?) {
        Log.w("msg", "Greedy Admob  BIG Native reloadAds ")
        if (AdmobNativeAd != null) {
            AdmobNativeAd!!.destroy()
            AdmobNativeAd = null
            loadNativeAds(context, admob_native, admob_native_reLoad)
        }
    }

    interface adCallback {
        fun adLoaded()
        fun adFaildToLoad()
    }

    fun loadNativeAds(context: Context?, admob_native: String?, admob_native_reLoad: String?) {
        try {
            val adLoader = AdLoader.Builder(context, admob_native)
                .forUnifiedNativeAd { unifiedNativeAd ->
                    isAdmobNativeAdLoaded = true
                    AdmobNativeAd = unifiedNativeAd
                    mcallback!!.adLoaded()
                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(errorCode: Int) {
                        Log.w("msg", "onUnifiedNativeAdLoaded prefixAd" + errorCode);

                        isAdmobNativeAdLoaded = false
                        loadNativeAdsReLoad(context, admob_native_reLoad)
                        //                        mcallback.adFaildToLoad();
                    }
                })
                .withNativeAdOptions(NativeAdOptions.Builder().build())
                .build()
            adLoader.loadAd(AdRequest.Builder().build())
        } catch (e: Exception) {
        }
    }

    fun loadNativeAdsReLoad(context: Context?, admob_native_reLoad: String?) {
        val adLoader = AdLoader.Builder(context, admob_native_reLoad)
            .forUnifiedNativeAd { unifiedNativeAd ->
                isAdmobNativeAdLoaded = true
                AdmobNativeAd = unifiedNativeAd
                mcallback!!.adLoaded()
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {
                    Log.w("msg", "onUnifiedNativeRELoaded prefixAd" + errorCode);
                    isAdmobNativeAdLoaded = false
                    mcallback!!.adFaildToLoad()
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    fun isreadytoshow(): Boolean {
        return if (isAdmobNativeAdLoaded && AdmobNativeAd != null) {
            true
        } else {
            false
        }
    }

}