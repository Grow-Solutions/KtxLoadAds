/*
  * Copyright (C) 2019 Grow Solution
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
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.greedygame.core.adview.GGAdview
import com.greedygame.core.adview.interfaces.AdLoadCallback
import com.greedygame.core.adview.modals.AdRequestErrors


/**
 * Created by grow solution on 2/12/2020.
 * display Big Greedy NativeAd Ads ..
 * Method for load Big Greedy Native  Ads ..
 **/

object BigGreedyNativeAdLoader {

    fun loadAd(
        context: Context?,
        nativeUnit: FrameLayout,
        loaderContainer: LinearLayout,
        iLayHeight: Int,
        greedy_native: String,
        mcallback1: adCallback?
    ) {
            loadGreedyNativeAd(
                context,
                nativeUnit,
                iLayHeight,
                loaderContainer,
                greedy_native
            )
    }

    fun reloadAds(context: Context?) {}

    interface adCallback {
        fun adLoaded()
        fun adFaildToLoad()
    }

    fun isreadytoshow(): Boolean {
        return false
    }

    fun loadGreedyNativeAd(
        context: Context?,
        nativeUnit: FrameLayout,
        height: Int,
        loaderContainer: LinearLayout,
        greedy_native: String
    ) {
        val ggAdView = GGAdview(context!!)
        ggAdView.unitId = greedy_native
        ggAdView.adsMaxHeight = height
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height)
        nativeUnit.addView(ggAdView, layoutParams)
        ggAdView.loadAd(object : AdLoadCallback {
            override fun onAdLoaded() {
                nativeUnit.visibility = View.VISIBLE
                loaderContainer.visibility = View.GONE
            }

            override fun onAdLoadFailed(adRequestErrors: AdRequestErrors) {
                Log.w("msg", "Native Failed== " + adRequestErrors.toString())
                nativeUnit.visibility = View.GONE
                loaderContainer.visibility = View.VISIBLE
            }

            override fun onUiiOpened() {
                //                Toast.makeText(context, "Exit ad Uii OPened", Toast.LENGTH_SHORT).show();
            }

            override fun onUiiClosed() {
                //                Toast.makeText(context, "Exit ad Uii Closed", Toast.LENGTH_SHORT).show();
            }

            override fun onReadyForRefresh() {
            }
        })
    }
}