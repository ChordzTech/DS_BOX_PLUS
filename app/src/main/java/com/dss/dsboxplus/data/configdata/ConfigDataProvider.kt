package com.dss.dsboxplus.data.configdata

import com.dss.dsboxplus.data.repo.response.AppConfigResponse
import com.dss.dsboxplus.data.repo.response.EstimateListResponse
import com.dss.dsboxplus.data.repo.response.SubscriptionDetailsResponse

object ConfigDataProvider{
    var appConfigResponse: AppConfigResponse? = null
    var subResponse:SubscriptionDetailsResponse? = null
    var qrCodeBase64:String ? =null
    var estimateListResponse:EstimateListResponse? =null
}
