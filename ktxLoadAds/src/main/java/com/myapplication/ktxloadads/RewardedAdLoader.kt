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

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener

/**
 * Created by grow solution on 2/12/2020.
 **/

object RewardedAdLoader {

    var mRewardedVideoAd: RewardedVideoAd? = null

    interface mCallBack {
        fun done(isReworded: Boolean)
    }

    fun loadAd(context: Context?, admob_reward: String?, admob_reward_back: String?) {
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context)
        if (admob_reward != null && admob_reward_back != null) {
            loadRewardedVideoAd(context, admob_reward, admob_reward_back)
        }
    }

    fun isAdLoaded(context: Context?): Boolean {
        return if (mRewardedVideoAd != null && mRewardedVideoAd!!.isLoaded) {
            true
        } else {
            false
        }
    }

    fun destroyAd(context: Context?) {
        if (mRewardedVideoAd != null) {
            mRewardedVideoAd!!.destroy(context)
        }
        mRewardedVideoAd = null
    }

    var isRewarded = false

    fun showVideoAd(
        context: Context?,
        mCallBack: mCallBack,
        admob_rewarded: String,
        admob_reward_back: String
    ) {
        isRewarded = false
        mRewardedVideoAd!!.setRewardedVideoAdListener(object :
            RewardedVideoAdListener {
            override fun onRewardedVideoAdLoaded() {}
            override fun onRewardedVideoAdOpened() {}
            override fun onRewardedVideoStarted() {}
            override fun onRewardedVideoAdClosed() {
                loadRewardedVideoAd(context, admob_rewarded, admob_reward_back)
                Log.w("msg", "onRewardedVideoAdClosed")
                mCallBack.done(isRewarded)
            }

            override fun onRewarded(rewardItem: RewardItem) {
                Log.w("msg", "onRewarded")
                isRewarded = true
            }

            override fun onRewardedVideoAdLeftApplication() {}

            @SuppressLint("WrongConstant")
            override fun onRewardedVideoAdFailedToLoad(i: Int) {
                Toast.makeText(context, "Video AD is not available ... please try again", 1).show()
                mCallBack.done(isRewarded)
            }

            override fun onRewardedVideoCompleted() {}
        })
        mRewardedVideoAd!!.show()
    }

    fun loadRewardedVideoAd(context: Context?, admob_rewarded: String, admob_reward_back: String) {
        val adRequest = AdRequest.Builder().build()
        Log.w("msg", "admob_rewarded==" + admob_rewarded)

        mRewardedVideoAd!!.loadAd(admob_rewarded, adRequest)
        mRewardedVideoAd!!.setRewardedVideoAdListener(object :
            RewardedVideoAdListener {
            override fun onRewardedVideoAdLoaded() {
                Log.w("msg", "onRewardedVideoAdLoaded")
            }

            override fun onRewardedVideoAdOpened() {}
            override fun onRewardedVideoStarted() {}
            override fun onRewardedVideoAdClosed() {
                loadRewardedVideoAd(context, admob_rewarded, admob_reward_back)
                Log.w("msg", "onRewardedVideoAdClosed")
            }

            override fun onRewarded(rewardItem: RewardItem) {
                Log.w("msg", "onRewarded")
                isRewarded = true
            }

            override fun onRewardedVideoAdLeftApplication() {}
            override fun onRewardedVideoAdFailedToLoad(i: Int) {
                Log.w("msg", "onRewardedVideoAdFailedToLoad  " +i)

                isRewarded = false
                reLoadRewardedVideoAd(context, admob_reward_back)
            }

            override fun onRewardedVideoCompleted() {}
        })
    }


    fun reLoadRewardedVideoAd(context: Context?, admob_reward_back: String) {
        val adRequest = AdRequest.Builder().build()
        Log.w("msg", "reLoadRewardedVideoAd admob_rewarded==" + admob_reward_back)

        mRewardedVideoAd!!.loadAd(admob_reward_back, adRequest)
        mRewardedVideoAd!!.setRewardedVideoAdListener(object :
            RewardedVideoAdListener {
            override fun onRewardedVideoAdLoaded() {
                Log.w("msg", "reLoadRewardedVideoAd")
            }
            override fun onRewardedVideoAdOpened() {}
            override fun onRewardedVideoStarted() {}
            override fun onRewardedVideoAdClosed() {
                reLoadRewardedVideoAd(context, admob_reward_back)
                Log.w("msg", "reLoadRewardedVideoAd")
            }

            override fun onRewarded(rewardItem: RewardItem) {
                Log.w("msg", "onRewarded")
                isRewarded = true
            }

            override fun onRewardedVideoAdLeftApplication() {}
            override fun onRewardedVideoAdFailedToLoad(i: Int) {
                Log.w("msg", "onReLoadwardedVideoAdFailedToLoad  " + i)
                isRewarded = false
            }

            override fun onRewardedVideoCompleted() {}
        })
    }
}