




 Code Guide For All ADS SDK:

 Facebook Ads : 

 Facebook Banner Ads :



 Put on Xml :
     
     <LinearLayout
            android:id="@+id/fb_banner"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"/>


// As a First parameter pass the Activiy
// As a Second parameter pass Facebook Banner ID (LinearLayout) From xml

 BannerAds.loadFbBannerAds(activity , fb_banner, " Your Facebook Banner Id ")



 Facebook Interstitial Ads :

 Put on onCreate of Activity

 FbInterstitialAdLoader.loadAd(activity ," Your Facebook Interstitial ID ")

 Put onClick  :


  
            if (FbInterstitialAdLoader.isAdLoaded(activity)) {

                FbInterstitialAdLoader.showAd(activity, object : FbInterstitialAdLoader.adfinish {

                    override fun adfinished() {

                        FbInterstitialAdLoader.loadAd(activity, "Your Facebook Interstitial Add ID")

                        // write your code here....
                    }
                })
            }




 For Admob Banner Ads : 

 Put on Xml :
     
    <FrameLayout
            android:id="@+id/admob_banner"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
             />

 // As a First parameter pass the Activiy
 // As a Second parameter pass Admob Banner ID (FramLayout) From xml
 
 BannerAds.loadAdmob_BannerADs(activity , admob_banner, "Your Admob Banner Id","Your Second Admob Banner Id")


 Admob Interstitial Ads :

 Put on onCreate of Activity

 AdmobInterstitialLoader.loadAd(activity ,"Your Admob Interstitial Id","Your Second Admob Interstitial Id")
               

 Put onClick :


            if (AdmobInterstitialLoader.isAdLoaded(activity)) {

                AdmobInterstitialLoader.showAd(activity, object : AdmobInterstitialLoader.adfinish {

                override fun adfinished() {

                    AdmobInterstitialLoader.loadAd(activity ,"Your Admob Interstitial Id","Your Second Admob Interstitial Id")

                    // write your code here....
                   } 
                })

            }

  
 For Greedy Banner Ads : 



 Put on Xml :
     
    
        <LinearLayout
            android:id="@+id/greedy_banner"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:orientation="horizontal" />

            

 // As a First parameter pass the Activiy
 // As a Second parameter pass Greedy Banner ID (LinearLayout) From xml

 BannerAds.loadGreedyBanner(activity , greedy_banner, "Your Greedy Banner Id")


 Greedy Interstitial Ads :

 Put on onCreate of Activity

 GreedyInterstitialAdLoader.loadAd(activity ,"Your Greedy Interstitial Id")
               

 Put onClick :

            if (GreedyInterstitialAdLoader.isAdLoaded(activity)) {

                GreedyInterstitialAdLoader.showAd(activity, object : GreedyInterstitialAdLoader.adfinish {

                    override fun adfinished() {

                    GreedyInterstitialAdLoader.loadAd(activity ,"Your Greedy Interstitial Id")

                    // write your code here....
                    
                    }
                })
            }

  



 Mobile Ads SDK for Android
 
 This repository contains open source examples and developer resources for the Google Mobile Ads SDK.
 
 Mobile Ads SDK Developers forum
 
 The forum provides the latest SDK announcements and updates as well as technical SDK support for our Android developers.
 
 
 
 Documentation
 
 How does it work?
 Mobile Ads Sdk helps you monetize your mobile app through in-app advertising. Ads can be displayed in a number of formats and are seamlessly added to platform native UI components.
 
 Before you can display ads within your app, you'll need to create an AdMob ,FB ,Greedy account and activate one or more ad unit IDs. This is a unique identifier for the places in your app  where ads are displayed.
 
 Google Mobile Ads SDK which helps app developers gain insights about their users and maximize ad revenue. To do so, the default integration of the Mobile Ads SDK collects information such as  device information.
 
 
 Examples 
 
 Banner: Kotlin
 
 Interstitial: Kotlin

 Rewarded Video: Kotlin

 Native Advanced: Kotlin# KtSdkAdsLoad
# KtxLoadAds
