package com.crystal.locationsearchmapapp.utillity

import com.crystal.locationsearchmapapp.Key
import com.crystal.locationsearchmapapp.Url
import com.crystal.locationsearchmapapp.response.address.AddressInfoResponse
import com.crystal.locationsearchmapapp.response.search.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ApiService {
    /*
    https://apis.openapi.sk.com/tmap/pois
    ?version={version}
    &page={page}
    &count={count}
    &searchKeyword={searchKeyword}
    &areaLLCode={areaLLCode}
    &areaLMCode={areaLMCode}
    &resCoordType={resCoordType}
    &searchType={searchType}
    &searchtypCd={searchtypCd}&radius={radius}
    &reqCoordType={reqCoordType}&centerLon={centerLon}
    &centerLat={centerLat}&multiPoint={multiPoint}
    &callback={callback}
    &appKey={appKey}
     */
    @GET(Url.GET_TMAP_LOCATION)
    suspend fun getSearchLocation(
        @Header("appKey") appKey: String = Key.TMAP_APP,
        @Query("version") version: Int = 1,
        @Query("callback") callback: String? = null,
        @Query("count") count: Int = 20,
        @Query("searchKeyword") keyword: String,
        @Query("areaLLCode") areaLLCode: String? = null,
        @Query("areaLMCode") areaLMCode: String? = null,
        @Query("resCoordType") resCoordType: String? = null,
        @Query("searchType") searchType: String? = null,
        @Query("multiPoint") multiPoint: String? = null,
        @Query("searchtypCd") searchtypCd: String? = null,
        @Query("radius") radius: String? = null,
        @Query("reqCoordType") reqCoordType: String? = null,
        @Query("centerLon") centerLon: String? = null,
        @Query("centerLat") centerLat: String? = null
    ): Response<SearchResponse>

    @GET(Url.GET_TMAP_REVERSE_GEO_CODE)
    suspend fun getReverseGeoCode(
        @Header("appKey") appKey: String = Key.TMAP_APP,
        @Query("version") version: Int = 1,
        @Query("callback") callback: String? = null,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("coordType") coordType: String? = null,
        @Query("addressType") addressType: String? = null
    ): Response<AddressInfoResponse>

}