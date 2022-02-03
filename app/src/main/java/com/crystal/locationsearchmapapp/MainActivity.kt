package com.crystal.locationsearchmapapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.crystal.locationsearchmapapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        adapter = SearchRecyclerAdapter{
            Toast.makeText(this, "clicked...${it.BuildingName}", Toast.LENGTH_SHORT).show()
        }
        with(binding){
            emptyResultTextView.isVisible = false
            searchRecyclerView.adapter = adapter
            val list = (0..10).map{
                SearchResultEntity(
                    "주소 : $it",
                    "이름: $it",
                LocationLatLanEntity(it.toFloat(),it.toFloat()))
            }
            adapter.submitList(list)
        }

    }


}