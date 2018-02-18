package tridivide.com.pokemonapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import tridivide.com.pokemonapp.R
import tridivide.com.pokemonapp.model.Pokemon


class PokemonCardAdapter(context: Context, resource: Int, list: ArrayList<Pokemon>): ArrayAdapter<Pokemon>(context, resource, list) {

    private var m_resource: Int = 0
    private var m_list: ArrayList<Pokemon>
    private var m_vi: LayoutInflater
    private var m_context: Context = context

    init{
        this.m_resource = resource
        this.m_list = list
        this.m_vi = m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var returnView: View? = null
        if (convertView == null) {
            returnView = try {
                m_vi.inflate(m_resource, null)

            } catch (e: Exception) {
                e.printStackTrace()
                View(context)
            }
        }

        val pokemon: Pokemon? = if(count > position) getItem(position) else null

        val name: TextView? = returnView?.findViewById(R.id.pokemon_card_name)
        name?.text = pokemon?.name ?: ""


        val typeOne: TextView? = returnView?.findViewById(R.id.pokemon_card_type_one)
        typeOne?.text = pokemon?.typeOne ?: ""


        val typeTwo: TextView? = returnView?.findViewById(R.id.pokemon_card_type_two)
        typeTwo?.text = pokemon?.typeTwo ?: ""


        val index: TextView? = returnView?.findViewById(R.id.pokemon_card_dex_number)
        index?.text = pokemon?.index ?: ""


        return returnView
    }

}