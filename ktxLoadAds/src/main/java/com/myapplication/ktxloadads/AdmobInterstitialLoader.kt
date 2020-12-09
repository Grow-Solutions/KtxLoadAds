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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd

/**
 * Created by grow solution on 2/12/2020.
 * display Admob  InterstitialAd sdk ads ..
 * load AdMob Interstitial ad ..
 * check if ad is Loaded ..
 * check if ad is ready to Show ..
 * Show AdMob Interstitial ad ..
 **/

object AdmobInterstitialLoader {
    var myadfinish: adfinish? = null
    var isAdmobIntAdLoaded = false
    private var AdmobIntAdLoaded = false
    var mAdmobInterstitialAd: InterstitialAd? = null
    var adclose = false

    fun loadAd(context: Context?, admob_intrestial_ad: String?, admob_intrestial_reLoad: String?) {
        Log.w("msg", "Admob Interstrial  Ad Loaded ")
        if (admob_intrestial_ad != null && admob_intrestial_reLoad != null) {
            loadAdmobInt(context, admob_intrestial_ad, admob_intrestial_reLoad)
        }
    }

    fun isAdLoaded(activity: Context?): Boolean {
        Log.w("msg", "Admob  Interstrial isAdLoaded  Ad Loaded ")
        return if (mAdmobInterstitialAd != null && mAdmobInterstitialAd!!.isLoaded) {
            AdmobIntAdLoaded = true
            true
        } else {
            false
        }
    }
    fun loadAdmobInt(
        context: Context?,
        admob_intrestial_ad: String,
        admob_intrestial_reLoad: String
    ) {
        try {
            mAdmobInterstitialAd = InterstitialAd(context)
            Log.w("msg", "DownloadIntAdmobLoader  intrestial_ad --" + admob_intrestial_ad)

            mAdmobInterstitialAd!!.setAdUnitId(admob_intrestial_ad)
            mAdmobInterstitialAd!!.loadAd(AdRequest.Builder().build())
            mAdmobInterstitialAd!!.setAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    isAdmobIntAdLoaded = true
                }

                override fun onAdClosed() {
                    loadAdmobInt(context, admob_intrestial_ad, admob_intrestial_reLoad)
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    isAdmobIntAdLoaded = false
                    reLoadAdmobInt(context, admob_intrestial_reLoad)
                    Log.w("msg", "DownloadIntAdmobLoader  onAdFailedToLoad --" + errorCode)

                }

                override fun onAdLeftApplication() {}
                override fun onAdOpened() {}
            })
        } catch (e: OutOfMemoryError) {
        }
    }

    fun reLoadAdmobInt(context: Context?, admob_intrestial_reLoad: String) {
        try {
            mAdmobInterstitialAd = InterstitialAd(context)
            Log.w("msg", "DownloadIntAdmobLoader  reload intrestial_ad_back --  " + admob_intrestial_reLoad)

            mAdmobInterstitialAd!!.setAdUnitId(admob_intrestial_reLoad)
            mAdmobInterstitialAd!!.loadAd(AdRequest.Builder().build())
            mAdmobInterstitialAd!!.setAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    Log.w("msg", "DownloadIntAdmobLoader  reload onAdLoaded -- ")
                    isAdmobIntAdLoaded = true
                }

                override fun onAdClosed() {
                    reLoadAdmobInt(context, admob_intrestial_reLoad)
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    isAdmobIntAdLoaded = false
                    Log.w("msg", "DownloadIntAdmobLoader  reload onAdLoaded  errorCode" + errorCode)

                }

                override fun onAdLeftApplication() {}
                override fun onAdOpened() {}
            })
        } catch (e: OutOfMemoryError) {
        }
    }


    interface adfinish {
        fun adfinished()
    }

    fun onDestroy() {}

    fun showAd(context: Context?, myadfinish1: adfinish) {
        Log.w("msg", "Admob Interstrial showAd ")
        myadfinish = myadfinish1
        if (mAdmobInterstitialAd != null && mAdmobInterstitialAd!!.isLoaded()) {
            Log.w("msg", "Admob  ")
            mAdmobInterstitialAd!!.setAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.w("msg", " Admob  looooad ")
                    myadfinish!!.adfinished()
                }

                override fun onAdFailedToLoad(i: Int) {
                    super.onAdFailedToLoad(i)
                    Log.w("msg", "Admob  failed--  " + i)
                    myadfinish!!.adfinished()
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                    myadfinish!!.adfinished()
                }
            })
            mAdmobInterstitialAd!!.show()
        } else {
            Log.w("msg", " Greedy Admob  ellseeeeeeeee ")
            adclose = true
            myadfinish!!.adfinished()
        }
    }

}