package com.cookandroid.teamproject1.plan.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * 작성자 : 원도혜
 * diary api 연동
 */

interface PlanService {
    @GET("/api/v1/plans/plan-list?")
    fun getDiaryPlan(
        @Header("X-ACCESS-TOKEN") jwt: String,
        @Header("X-REFRESH-TOKEN") refreshToken: Int
//        @Query("Status") status: String
    ) : Call<ResponseDiaryPlanData>
}