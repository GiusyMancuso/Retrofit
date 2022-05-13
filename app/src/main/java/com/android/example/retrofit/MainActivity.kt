package com.android.example.retrofit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("{user}")
    suspend fun listRepos(@Path("user") user: String?): RepoResult
}
class RepoResult : ArrayList<Repo>()

data class Repo(
    val abv: Double,
    val attenuation_level: Double,
    val boil_volume: BoilVolume,
    val brewers_tips: String,
    val contributed_by: String,
    val description: String,
    val ebc: Int,
    val first_brewed: String,
    val food_pairing: List<String>,
    val ibu: Double,
    val id: Int,
    val image_url: String,
    val ingredients: Ingredients,
    val method: Method,
    val name: String,
    val ph: Double,
    val srm: Double,
    val tagline: String,
    val target_fg: Int,
    val target_og: Double,
    val volume: Volume
)

data class BoilVolume(
    val unit: String,
    val value: Int
)

data class Ingredients(
    val hops: List<Hop>,
    val malt: List<Malt>,
    val yeast: String
)

data class Method(
    val fermentation: Fermentation,
    val mash_temp: List<MashTemp>,
    val twist: String
)

data class Volume(
    val unit: String,
    val value: Int
)

data class Hop(
    val add: String,
    val amount: Amount,
    val attribute: String,
    val name: String
)

data class Malt(
    val amount: AmountX,
    val name: String
)

data class Amount(
    val unit: String,
    val value: Double
)

data class AmountX(
    val unit: String,
    val value: Double
)

data class Fermentation(
    val temp: Temp
)

data class MashTemp(
    val duration: Int,
    val temp: TempX
)

data class Temp(
    val unit: String,
    val value: Int
)

data class TempX(
    val unit: String,
    val value: Int
)

class MainActivity : AppCompatActivity() {

    val retrofit = Retrofit.Builder().baseUrl("https://api.punkapi.com/v2/").addConverterFactory(
        GsonConverterFactory.create()).build()
    val gitHubService = retrofit.create(GitHubService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrieveRepos()
    }

    fun retrieveRepos(){
        lifecycleScope.launch{
            try{
                val repos = gitHubService.listRepos("beers")
                showRepos(repos)
            } catch (e: Exception){
                Log.e("MainActivity", "Error retrieving repos: $e")
                Snackbar.make(findViewById(R.id.main_view), "Error retrieving repos",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry"){ retrieveRepos() }.show()
            }
        }
    }

    fun showRepos(repoResults: List<Repo>){
        Log.d("MainActivity", "list of repos received, size: ${repoResults.size}")
        val list = findViewById<RecyclerView>(R.id.repo_list)
        list.visibility = View.VISIBLE
        val adapter = RepoAdapter(repoResults)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
    }

}