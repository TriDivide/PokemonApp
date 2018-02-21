package tridivide.com.pokemonapp.model

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
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

    fun savePokemon(p: Pokemon, imagesToAdd: ArrayList<Bitmap>?, onComplete: OnCompleteListener<Void>? ) {
        val reference = getDatabaseRef()?.push()
        val pokemonId = reference?.key
        reference?.updateChildren(p.toMap())?.addOnCompleteListener { task ->
            if (task.isComplete) {
                val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("/"+pokemonId+"/")
                if (imagesToAdd != null) {
                    for (image in imagesToAdd) {
                        val outputStream = ByteArrayOutputStream()
                        image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                        val data: ByteArray = outputStream.toByteArray()
                        storageRef.child(getSaltString(10)).putBytes(data).addOnFailureListener { _ ->
                            Log.i("images", "unable to upload image line 84")
                        }.addOnCompleteListener{ task ->
                            Log.i("images", "message uploaded line 86")
                            val downloadUrl: Uri? = task.result.downloadUrl
                            if (downloadUrl != null) {
                                val url: String = downloadUrl.toString()
                                val imageRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("/pokemon/" + pokemonId + "/images/").push()
                                imageRef.setValue(url)

                                if(p.imageRefs == null) {
                                    p.imageRefs = HashMap()
                                }
                                p.imageRefs!!.put(imageRef.key, url)
                            }
                        }
                    }
                }
                onComplete?.onComplete(task)
                setChanged()
                notifyObservers()
            }
        }

    }

    private fun getSaltString(length: Int): String {
        val saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        val salt = StringBuilder()
        val rnd = Random()
        while (salt.length < length) { // length of the random string.
            val index = (rnd.nextFloat() * saltChars.length) as Int
            salt.append(saltChars[index])
        }
        return salt.toString()
    }


}
