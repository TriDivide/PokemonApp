package tridivide.com.pokemonapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import tridivide.com.pokemonapp.model.Pokemon
import tridivide.com.pokemonapp.model.PokemonModel
import java.util.*

class MainActivity : AppCompatActivity(), Observer {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        PokemonModel
    }

    override fun update(p0: Observable?, p1: Any?) {
        val data = PokemonModel.getData()
        if (data != null) {
            for (d: Pokemon in data) {
                Log.i("updatedData", d.name)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        PokemonModel.addObserver(this)
    }

    override fun onResume() {
        super.onResume()
        PokemonModel.addObserver(this)
    }

    override fun onStop() {
        super.onStop()
        PokemonModel.deleteObserver(this)
    }

    override fun onPause() {
        super.onPause()
        PokemonModel.deleteObserver(this)
    }


}
