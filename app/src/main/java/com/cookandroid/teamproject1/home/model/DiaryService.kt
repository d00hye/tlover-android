package com.cookandroid.teamproject1.home.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * 홈화면 다이어리 목록 조회 api
 * 홈 화면 여행 취향 다이어리 목록 조회 추가
 * 작성자 : 윤성식
 */
interface DiaryService {
    @GET("/api/v1/diaries/get-diary-prefer")
    fun getDiary(
        @Header("X-ACCESS-TOKEN") jwt: String,
        @Header("X-REFRESH-TOKEN") refreshToken: Int
    ): Call<ResponseAllDiaryData>
}