package com.dicoding.picodiploma.storyapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.storyapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: AllViewModel
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "Main Activity"
    }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
         setContentView(binding.root)

         setupViewModel()

         val layoutManager = LinearLayoutManager(this)
         binding.rvUser.layoutManager = layoutManager

         getAllStories()
    }
    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[AllViewModel::class.java]
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.addstory -> {
                val intent = Intent(this, AddStoriesActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_language -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                return true
            }

            R.id.menu_logout -> {
                mainViewModel.logout()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return true
    }
    private fun getAllStories() {
        mainViewModel.getUser().observe(this) {
            if (it != null) {
                val client = ApiConfig.getApiService().getALLStories("Bearer " + it.token)
                client.enqueue(object: Callback<StoriesResponse> {
                    override fun onResponse(
                        call: Call<StoriesResponse>,
                        response: Response<StoriesResponse>
                    ) {
                        val responseBody = response.body()
                        Log.d(TAG, "onResponse: $responseBody")
                        if(response.isSuccessful && responseBody?.message == "Stories fetched successfully") {
                            setDataStories(responseBody.listStory)
                            Toast.makeText(this@MainActivity, getString(R.string.loadsuccess), Toast.LENGTH_SHORT).show()
                        } else {
                            Log.e(TAG, "onFailure1: ${response.message()}")
                            Toast.makeText(this@MainActivity, getString(R.string.loadfail), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                        Log.e(TAG, "onFailure2: ${t.message}")
                        Toast.makeText(this@MainActivity, getString(R.string.loadfail), Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun setDataStories(items: List<AllStory>) {
        val listStories = ArrayList<StoryList>()
        for(item in items) {
            val story = StoryList(
                item.name,
                item.photoUrl,
                item.description
            )
            listStories.add(story)
        }
        val adapter = MainStoryAdapter(listStories)
        binding.rvUser.adapter=adapter
    }
}