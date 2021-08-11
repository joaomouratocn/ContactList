package br.com.programadorjm.contactlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.programadorjm.contactlist.databinding.LayoutListBinding
import java.util.*

class ContactAdapter(private val dataSource:List<Contact>, private val listener:OnClick) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = LayoutListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.name.text = dataSource[position].name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        holder.initiaLetter.text = dataSource[position].name.substring(0,1)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        holder.itemView.setOnClickListener{listener.itemClickListener(dataSource[position])}
    }

    override fun getItemCount(): Int = dataSource.size

    class ContactViewHolder(binding: LayoutListBinding) : RecyclerView.ViewHolder(binding.root){
        val name:TextView = binding.txtName
        val initiaLetter:TextView = binding.txtInitialLetter
    }

    interface OnClick{
        fun itemClickListener(contact: Contact)
    }
}