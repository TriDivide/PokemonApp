package tridivide.com.pokemonapp.model

import com.google.firebase.database.DataSnapshot
import kotlin.collections.HashMap


data class Pokemon(val snapshot: DataSnapshot?) {

    var id: String? = ""
    var name: String? = ""
    var typeOne: String? = ""

    var typeTwo: String? = ""
    var index: String? = ""

    init {
        try {
            val data: HashMap<String, Any>? = snapshot?.value as HashMap<String, Any>?

            id = snapshot?.key ?: ""
            name = data?.get("name") as String?
            typeOne = data?.get("typeOne") as String?

            typeTwo = data?.get("typeTwo") as String?
            //index = data["index"] as String?

        } catch( e: Exception) {
            e.printStackTrace()
        }
    }

    fun toMap(): HashMap<String, Any> {
        val map: HashMap<String, Any> = HashMap()
        map["name"] = name ?: ""
        map["typeOne"] = typeOne ?: ""
        map["typeTwo"] = typeTwo ?: ""

        return map
    }
}