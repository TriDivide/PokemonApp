package tridivide.com.pokemonapp.model

import com.google.firebase.database.DataSnapshot
import kotlin.collections.HashMap


class Pokemon(snapshot: DataSnapshot) {

    lateinit var id: String
    lateinit var name: String
    lateinit var typeOne: String

    var typeTwo: String? = ""

    init {
        try {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any> ?: throw Exception("no data for pokemon")

            id = snapshot.key
            name = data["name"] as String
            typeOne = data["typeOne"] as String

            typeTwo = data["typeTwo"] as String?

        } catch( e: Exception) {
            e.printStackTrace()
        }
    }
}