package com.crystal.locationsearchmapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.crystal.locationsearchmapapp.databinding.ActivityMainBinding
import com.crystal.locationsearchmapapp.model.SearchResultEntity
import com.crystal.locationsearchmapapp.response.search.Poi
import com.crystal.locationsearchmapapp.response.search.Pois
import com.crystal.locationsearchmapapp.utillity.RetrofitUtil
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
import com.crystal.locationsearchmapapp.model.LocationLatLanEntity as LocationLatLanEntity

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchRecyclerAdapter

    companion object {
        val SEARCH_RESULT_EXTRA_KEY = "search result extra key"
    }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()
        initViews()
        bindViews()
        initData()
    }

    private fun initViews() {
        adapter = SearchRecyclerAdapter{
            Toast.makeText(this, "clicked...${it.BuildingName}", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MapActivity::class.java).apply {
                putExtra(SEARCH_RESULT_EXTRA_KEY, it) //Parcelize 기 때문에 intent로 넘겨서 받을 수 있음.
            })

        }
        with(binding){
            emptyResultTextView.isVisible = false
            searchRecyclerView.adapter = adapter
        }
    }

    private fun bindViews() = with(binding){
        searchButton.setOnClickListener {
            searchKeyword(searchEditText.text.toString())
        }
    }

    private fun searchKeyword(keyword: String) {
        //비동기
        launch(coroutineContext) {
            try {
                withContext(Dispatchers.IO){
                    val response = RetrofitUtil.apiService.getSearchLocation(
                        keyword = keyword
                    )
                    if(response.isSuccessful){
                        val body = response.body()
                        withContext(Dispatchers.Main){
                            Log.d("t map api response", body.toString())
                            body?.let {
                                setData(it.searchPoiInfo.pois)
                            }
                        }
                    }
                }
            }catch (e: Exception){
                Log.e("Error", e.message.toString())
            }
        }
    }

    private fun initData() {
        adapter.notifyDataSetChanged()
    }

    private fun setData(pois: Pois) {
        val list = pois.poi.map{
            SearchResultEntity(
                BuildingName = it.name?:"빌드명 없음",
                FullAddress = makeMainAddress(it),
                locationLatLan = LocationLatLanEntity(it.noorLat, it.noorLon)
            )
        }
        adapter.submitList(list)
    }

    private fun makeMainAddress(poi: Poi): String =
        if (poi.secondNo?.trim().isNullOrEmpty()) {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    poi.firstNo?.trim()
        } else {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    (poi.firstNo?.trim() ?: "") + " " +
                    poi.secondNo?.trim()
        }
}