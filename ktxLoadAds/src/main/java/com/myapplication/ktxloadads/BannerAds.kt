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

//import android.app.Activity
//import android.util.Log
//import android.view.View
//import android.widget.FrameLayout
//import android.widget.LinearLayout
//import com.facebook.ads.*
//import com.google.android.gms.ads.AdRequest

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdSize
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.greedygame.core.adview.GGAdview
import com.greedygame.core.adview.interfaces.AdLoadCallback
import com.greedygame.core.adview.modals.AdRequestErrors


/**
 * Created by grow solution on 2/12/2020.
 * display Admob Banner sdk Ads ..
 * Method for load FB banner Ads ..
 * Method for load Greedy banner Ads ..
 * Method for load AdMob banner Ads ..
 **/

object BannerAds {
    var adView: com.facebook.ads.AdView? = null
    var mAdView: AdView? = null

    fun loadFbBannerAds(activity: Activity?, fb_banner_container: LinearLayout, fb_banner: String) {
        Log.w("msg", "loadGreedyBanner fb_banner " + fb_banner);
        adView = com.facebook.ads.AdView(activity, fb_banner, AdSize.BANNER_HEIGHT_50)
        fb_banner_container.addView(adView)
        adView!!.loadAd()
        val adListener: com.facebook.ads.AdListener = object : com.facebook.ads.AdListener {
            override fun onError(ad: Ad, adError: AdError) {
                Log.w("msg", "Load fb Banner adError-  " + adError)

                fb_banner_container.visibility = GONE
            }

            override fun onAdLoaded(ad: Ad) {
                Log.w("msg", "Load fb Banner onAdLoaded ")
                fb_banner_container.visibility = VISIBLE
            }

            override fun onAdClicked(ad: Ad) {}
            override fun onLoggingImpression(ad: Ad) {}
        }
        adView!!.loadAd(
            adView!!.buildLoadAdConfig().withAdListener(adListener).build()
        )
    }

    fun loadGreedyBanner(
        activity: Activity,
        greedy_banner_container: LinearLayout,
        greedy_banner: String
    ) {
        val ggAdView = GGAdview(activity)
        Log.w("msg", "loadGreedyBanner fb_banner " + greedy_banner);
        ggAdView.unitId = greedy_banner
        ggAdView.adsMaxHeight = 55
        greedy_banner_container.visibility = VISIBLE
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 55)
        greedy_banner_container.addView(ggAdView, layoutParams)
        ggAdView.loadAd(object : AdLoadCallback {
            override fun onReadyForRefresh() {
                Log.w("msg", "Ad Ready for refresh");
            }

            override fun onUiiClosed() {
                Log.w("msg", "Uii closed");
            }

            override fun onUiiOpened() {
                Log.w("msg", "Uii Opened");
            }

            override fun onAdLoadFailed(cause: AdRequestErrors) {
                Log.w("msg", "Ad Load Failed-  " + cause)
                greedy_banner_container.visibility = GONE
            }

            override fun onAdLoaded() {
                greedy_banner_container.visibility = VISIBLE
                Log.w("msg", "Ad Loaded")
            }
        })
    }

    fun loadAdmob_BannerADs(
        activity: Activity?,
        adMob_banner_container: FrameLayout,
        admob_banner: String,
        admob_banner_reload: String?
    ) {
        mAdView = AdView(activity)
        mAdView!!.setAdSize(com.google.android.gms.ads.AdSize.BANNER)
        Log.w("msg", "loadAdmob_BannerADs admob_banner " + mAdView);
        mAdView!!.setAdUnitId(admob_banner)
        adMob_banner_container.addView(mAdView)
        val adRequest = AdRequest.Builder().build()
        mAdView!!.loadAd(adRequest)
        //      mAdView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
        mAdView!!.setAdListener(object : com.google.android.gms.ads.AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                adMob_banner_container.visibility = View.VISIBLE
                mAdView!!.setVisibility(View.VISIBLE)
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
                mAdView!!.setVisibility(View.GONE)
                adMob_banner_container.visibility = View.GONE
                Log.w("msg", "loadAdmob_BannerADs onAdFailedToLoad" + errorCode);
                if (admob_banner_reload != null) {
                    reLoadAdmob_BannerADs(
                        activity,
                        adMob_banner_container,
                        admob_banner_reload
                    )
                }
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        })
    }

    fun reLoadAdmob_BannerADs(activity: Activity?, adMob_banner_container: FrameLayout, admob_banner_reload: String) {
        mAdView = AdView(activity)
        mAdView!!.setAdSize(com.google.android.gms.ads.AdSize.BANNER)
        Log.w("msg", "reLoadAdmob_BannerADs admob_banner_back" + admob_banner_reload);
        mAdView!!.setAdUnitId(admob_banner_reload)
        adMob_banner_container.addView(mAdView)
        val adRequest = AdRequest.Builder().build()
        mAdView!!.loadAd(adRequest)
        mAdView!!.setAdListener(object : com.google.android.gms.ads.AdListener() {
            override fun onAdLoaded() {
                adMob_banner_container.visibility = View.VISIBLE
                mAdView!!.setVisibility(View.VISIBLE)
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
                mAdView!!.setVisibility(View.GONE)
                adMob_banner_container.visibility = View.GONE
                Log.w("msg", "reLoadAdmob_BannerADsonAdFailedToLoad" + errorCode);

            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {}
        })
    }

    fun destroyAd() {
        if (mAdView != null) {
            mAdView!!.destroy()
        }
        if (adView != null) {
            adView!!.destroy()
        }
    }

    fun resumeAd() {
        if (mAdView != null) {
            mAdView!!.resume()
        }
    }

    fun pauseAd() {
        if (mAdView != null) {
            mAdView!!.pause()
        }
    }
}