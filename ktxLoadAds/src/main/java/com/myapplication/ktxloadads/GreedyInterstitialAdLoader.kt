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
import com.greedygame.core.adview.modals.AdRequestErrors
import com.greedygame.core.interstitial.general.GGInterstitialAd
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener

/**
 * Created by grow solution on 2/12/2020.
 * display Greedy sdk ads ..
 * load Greedy Interstitial ad ..
 * check if ad is Loaded ..
 * check if ad is ready to Show ..
 * Show Greedy Interstitial ad ..
 **/

object GreedyInterstitialAdLoader {
    var ggInterstitialAd: GGInterstitialAd? = null
    var isggIntAdLoaded = false
    var ggIntAdLoaded = false
    var ggclose = false
    var myadfinish: adfinish? = null


    fun loadAd(context: Context?, greedy_int: String?) {
        greedyloadAd(context, greedy_int)
    }

    fun isAdLoaded(activity: Context?): Boolean {
        return try {
            if (ggInterstitialAd != null && ggInterstitialAd!!.isAdLoaded) {
                ggIntAdLoaded = true
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }


    interface adfinish {
        fun adfinished()
    }

    fun onDestroy() {}

    fun showAd(context: Context?, myadfinish1: adfinish?) {
        Log.w("msg", "Greedy  Interstrial showAd ");
        myadfinish = myadfinish1;
        if (ggInterstitialAd != null && isAdLoaded(context)) {
            ggInterstitialAd!!.loadAd(object : GGInterstitialEventsListener {
                override fun onAdLeftApplication() {
                    // Interstitial ad displayed callback
                }

                override fun onAdClosed() {
                    // Setting flag to false to not show the ad again. This covers the case of opening
                    // and ad that is already loaded
                    myadfinish!!.adfinished()
                }

                override fun onAdOpened() {

                }

                override fun onAdLoadFailed(cause: AdRequestErrors) {
                    Log.w("msg", "Greedy  Interstrial Ad onAdLoadFailed " + cause.toString())
                    myadfinish!!.adfinished()
                }

                override fun onAdLoaded() {
                    Log.w("msg", "Greedy  Interstrial Ad onAdLoaded ")
                    myadfinish!!.adfinished()
                }

            })
            ggInterstitialAd!!.show();
        } else {
            Log.w("msg", "Greedy  Interstrial Ad onAdLoaded  else");
            ggclose = true
            myadfinish!!.adfinished();
        }
    }

    fun greedyloadAd(context: Context?, greedy_int: String?){
        Log.w("msg", "Greedy  greedy_int -- " + greedy_int);
        ggInterstitialAd = GGInterstitialAd(context!!, greedy_int!!)
        ggInterstitialAd!!.loadAd(object : GGInterstitialEventsListener {
            override fun onAdLeftApplication() {
            }

            override fun onAdClosed() {
                greedyloadAd(context, greedy_int)
            }

            override fun onAdOpened() {

            }

            override fun onAdLoadFailed(cause: AdRequestErrors) {
                isggIntAdLoaded = false
            }

            override fun onAdLoaded() {
                isggIntAdLoaded = true;
            }

        })
    }
}