package tridivide.com.pokemonapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import tridivide.com.pokemonapp.adapters.PokemonCardAdapter
import tridivide.com.pokemonapp.model.Pokemon
import tridivide.com.pokemonapp.model.PokemonModel
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), Observer {

    private var m_pokemonListAdapter: PokemonCardAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        PokemonModel

        val dataList: ListView = findViewById(R.id.pokemon_list)

        val data:ArrayList<Pokemon> = ArrayList()
        m_pokemonListAdapter = PokemonCardAdapter(this, R.layout.pokemon_card_item, data)
        dataList.adapter = m_pokemonListAdapter


    }

    override fun update(p0: Observable?, p1: Any?) {
        m_pokemonListAdapter?.clear()

        val data = PokemonModel.getData()
        if (data != null) {
            m_pokemonListAdapter?.clear()
            m_pokemonListAdapter?.addAll(data)
            m_pokemonListAdapter?.notifyDataSetChanged()
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
