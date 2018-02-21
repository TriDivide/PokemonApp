package tridivide.com.pokemonapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import kotlinx.android.synthetic.main.main_layout.*
import tridivide.com.pokemonapp.adapters.PokemonCardAdapter
import tridivide.com.pokemonapp.model.Pokemon
import tridivide.com.pokemonapp.model.PokemonModel
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), Observer {

    private var mPokemonListAdapter: PokemonCardAdapter? = null
    private var mPokemonObserver: Observer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        PokemonModel

        val dataList: ListView = findViewById(R.id.pokemon_list)

        add_pokemon.setOnClickListener { addPokemon() }

        val data:ArrayList<Pokemon> = ArrayList()
        mPokemonListAdapter = PokemonCardAdapter(this, R.layout.pokemon_card_item, data)
        dataList.adapter = mPokemonListAdapter


    }

    private fun addPokemon() {
        val intent = Intent(this, AddPokemonActivity::class.java)
        startActivity(intent)
    }

    override fun update(p0: Observable?, p1: Any?) {

    }

    override fun onResume() {
        super.onResume()
        mPokemonObserver = Observer { _, _ -> refreshList() }
        PokemonModel.addObserver(mPokemonObserver)
        refreshList()
    }

    private fun refreshList() {
        mPokemonListAdapter?.clear()

        val data = PokemonModel.getData()
        if (data != null) {
            mPokemonListAdapter?.clear()
            mPokemonListAdapter?.addAll(data)
            mPokemonListAdapter?.notifyDataSetChanged()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mPokemonObserver != null) {
            PokemonModel.deleteObserver(mPokemonObserver)
        }
    }

    override fun onPause() {
        super.onPause()
        PokemonModel.deleteObserver(this)
    }


}
