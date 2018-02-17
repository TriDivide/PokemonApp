package tridivide.com.pokemonapp.model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

public class PokemonModel: Observable() {
    private static var _model: PokemonModel? = null
    private var m_valueDataListener: ValueEventListener? = null


    public static fun getInstance(): PokemonModel {
        if (_model == null) {
            _model = PokemonModel()
        }
        return _model
    }

    private constructor() {
        dataChanged()
    }

    private fun getDatabaseRef(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference.child("pokemon")
    }

    private synchronized fun dataChanged(callback: DataModelCallback<Pokemon>) {
        if (m_valueDataListener != null) {
            getDatabaseRef().removeEventListener(m_valueDataListener)
        }

        m_valueDataListener = null


    }
}
