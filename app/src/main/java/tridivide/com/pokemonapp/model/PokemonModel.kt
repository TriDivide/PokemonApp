package tridivide.com.pokemonapp.model

import android.util.Log
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList



object PokemonModel: Observable() {

    private var m_valueDataListener: ValueEventListener? = null     // The data listener that gets the data from the database
    private var m_pokemonList: ArrayList<Pokemon>? = ArrayList()    // Pokemon cache

    //gets the database location reference for later repeated use
    private fun getDatabaseRef(): DatabaseReference? {
        return FirebaseDatabase.getInstance().reference.child("pokemon")
    }

    //called on object initialisation
    init {
        if (m_valueDataListener != null) {
            getDatabaseRef()?.removeEventListener(m_valueDataListener)
        }
        m_valueDataListener = null
        Log.i("PokemonModel", "dataInit line 27")


        m_valueDataListener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                try {
                    Log.i("pokemonModel", "data updated line 28")
                    val data: ArrayList<Pokemon> = ArrayList()
                    if (dataSnapshot != null) {
                        for (snapshot: DataSnapshot in dataSnapshot.children) {
                            try {
                                data.add(Pokemon(snapshot))
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        m_pokemonList = data
                        Log.i("pokemonModel","data updated there are " + m_pokemonList!!.size + " pokemon in the list")
                        setChanged()
                        notifyObservers()
                    } else {
                        throw Exception("data snapshot is null line 31")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onCancelled(p0: DatabaseError?) {
                if (p0 != null) {
                    Log.i("pokemonModel", "line 33 Data update cancelled, err = ${p0.message}, detail = ${p0.details}")
                }
            }
        }
        getDatabaseRef()?.addValueEventListener(m_valueDataListener)
    }

    fun getData(): ArrayList<Pokemon>? {
        return m_pokemonList
    }


}
