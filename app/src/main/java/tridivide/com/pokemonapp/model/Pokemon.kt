package tridivide.com.pokemonapp.model

import com.google.firebase.database.DataSnapshot
import kotlin.collections.HashMap


data class Pokemon(private val snapshot: DataSnapshot?) {

    lateinit var id: String
    lateinit var name: String
    lateinit var typeOne: String

    var typeTwo: String? = ""
    var index: String? = ""

    var imageRefs: HashMap<String, String>? = HashMap()

    init {
        try {
            if (snapshot != null) {
                createPokemonFromSnapshot(snapshot)
            }
        } catch( e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createPokemonFromSnapshot(snapshot: DataSnapshot) {
        try {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            name = data["name"] as String
            typeOne = data["typeOne"] as String

            typeTwo = data["typeTwo"] as String
            //index = data["index"] as String?
            imageRefs = data["imageRefs"] as HashMap<String, String>
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toMap(): HashMap<String, Any> {
        val map: HashMap<String, Any> = HashMap()
        map["name"] = name
        map["typeOne"] = typeOne
        map["typeTwo"] = typeTwo ?: ""
        map["imageRefs"] = imageRefs ?: ""
        return map
    }
}