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
import androidx.appcompat.app.AppCompatActivity
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdListener

/**
 * Created by grow solution on 2/12/2020.
 * display Facebook sdk ads ..
 * load FB Interstitial ad ..
 * check if ad is Loaded ..
 * check if ad is ready to Show ..
 * Show FB Interstitial ad ..
 **/


object FbInterstitialAdLoader {
    var myadfinish: adfinish? = null
    var adclose = false
    var isFbIntAdLoaded = false
    var FbInterstitialAd: InterstitialAd? = null

    fun loadAd(context: Context?, fb_interestial: String) {
        loadFbInt(context, fb_interestial)
    }

    fun isAdLoaded(activity: Context?): Boolean {
        return try {
            if (FbInterstitialAd != null && FbInterstitialAd!!.isAdLoaded) {
                isFbIntAdLoaded = true
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }


    fun isreadytoShowAd(context: AppCompatActivity?, fb_interestial: String): Boolean {
        if (isAdLoaded(context)) {
            return true
        } else {
            loadAd(context, fb_interestial)
        }
        return false
    }


    fun loadFbInt(context: Context?, fb_interestial: String) {
        try {
            Log.w("msg", "DownloadClickIntFbAdLoader  fb_interestial --" + fb_interestial)

            FbInterstitialAd = InterstitialAd(context, fb_interestial)
            val interstitialAdListener: InterstitialAdListener = object : InterstitialAdListener {
                override fun onInterstitialDisplayed(ad: Ad) {
                    // Interstitial ad displayed callback
                }

                override fun onInterstitialDismissed(ad: Ad) {
                    // Interstitial dismissed callback
                }

                override fun onError(ad: Ad, adError: AdError) {
                    // Ad error callback
                    isFbIntAdLoaded = false
                }

                override fun onAdLoaded(ad: Ad) {
                    isFbIntAdLoaded = true
                }

                override fun onAdClicked(ad: Ad) {
                    // Ad clicked callback
                }

                override fun onLoggingImpression(ad: Ad) {
                    // Ad impression logged callback
                }
            }

            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown
            FbInterstitialAd!!.loadAd(
                FbInterstitialAd!!.buildLoadAdConfig()
                    .withAdListener(interstitialAdListener)
                    .build()
            )
        } catch (e: Exception) {
        }
    }

    interface adfinish {
        fun adfinished()
    }

    fun onDestroy() {
        if (FbInterstitialAd != null) {
            FbInterstitialAd!!.destroy()
        }
    }

    fun showAd(context: Context?, myadfinish1: adfinish?) {
        myadfinish = myadfinish1
        Log.w("msg", "showAd ")
        if (FbInterstitialAd != null && FbInterstitialAd!!.isAdLoaded) {
            try {
                val interstitialAdListener: InterstitialAdListener =
                    object : InterstitialAdListener {
                        override fun onInterstitialDisplayed(ad: Ad) {
                            // Interstitial ad displayed callback
                        }

                        override fun onInterstitialDismissed(ad: Ad) {
                            // Interstitial dismissed callback
                            myadfinish!!.adfinished()
                        }

                        override fun onError(ad: Ad, adError: AdError) {
                            // Ad error callback
                            myadfinish!!.adfinished()
                            Log.w("msg", "DownloadClickIntFbAdLoader adError---" + adError.errorMessage)
                        }

                        override fun onAdLoaded(ad: Ad) {
                            Log.w("msg", "DownloadClickIntFbAdLoader onAdLoaded--- ")
                        }

                        override fun onAdClicked(ad: Ad) {
                            // Ad clicked callback
                        }

                        override fun onLoggingImpression(ad: Ad) {
                            // Ad impression logged callback
                        }
                    }
                FbInterstitialAd!!.buildLoadAdConfig()
                    .withAdListener(interstitialAdListener)
                    .build()
                FbInterstitialAd!!.show()
            } catch (e: Exception) {
            }
        } else {
            adclose = true
            myadfinish!!.adfinished()
        }
    }
}
