package tridivide.com.pokemonapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.add_pokemon_layout.*
import org.jetbrains.anko.toast
import tridivide.com.pokemonapp.model.Pokemon

import tridivide.com.pokemonapp.model.PokemonModel
import tridivide.com.pokemonapp.model.PokemonModel.savePokemon


class AddPokemonActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_pokemon_layout)
        PokemonModel

        add_pokemon_save.setOnClickListener { savePokemon() }

    }

    private fun savePokemon() {
        if (add_pokemon_name.text == null || add_pokemon_name.text.length < 0) {
            toast("please add name")
            return
        }
        if (add_pokemon_type_one.text == null || add_pokemon_type_one.text.length < 0) {
            toast("please add type")
            return
        }

        val pokemon = Pokemon()
        pokemon.name = add_pokemon_name.text.toString()
        pokemon.typeOne = add_pokemon_type_one.text.toString()
        pokemon.typeTwo = add_pokemon_two?.text.toString()

        PokemonModel.savePokemon(pokemon, OnCompleteListener { task ->
            if (task.isComplete) {
                finish()
            }
        })
    }
}