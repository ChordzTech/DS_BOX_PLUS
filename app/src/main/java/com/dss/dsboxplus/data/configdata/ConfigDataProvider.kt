package com.dss.dsboxplus.data.configdata

import android.graphics.Bitmap
import com.dss.dsboxplus.data.repo.response.AppConfigResponse
import com.dss.dsboxplus.data.repo.response.ClientListResponse
import com.dss.dsboxplus.data.repo.response.EstimateListResponse
import com.dss.dsboxplus.data.repo.response.SubscriptionDetailsResponse
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse

object ConfigDataProvider{
    var userDetails: UserDetailsResponse? = null
    var appConfigResponse: AppConfigResponse? = null
    var subResponse:SubscriptionDetailsResponse? = null
    var qrCodeBase64:String ? =null
    var estimateListResponse:EstimateListResponse? =null
    var clientListResponse:ClientListResponse? =null
    var profilePic:Bitmap?=null
}
