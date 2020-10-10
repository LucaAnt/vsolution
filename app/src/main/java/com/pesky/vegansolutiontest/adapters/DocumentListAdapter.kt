package com.pesky.vegansolutiontest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pesky.vegansolutiontest.R
import com.pesky.vegansolutiontest.model.MonclerDocument

class DocumentListAdapter(var documentList : List<MonclerDocument> = listOf()) : Adapter<DocumentViewHolder>() {

    var listener : IMonclerDocumentsListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_document,parent,false)
        return DocumentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int){
        holder.bindData(documentList[position])
        listener?.let {
            holder.setOnItemClickListener(it)
        }
    }

    override fun getItemCount(): Int = documentList.size


}
class DocumentViewHolder(itemView: View) : ViewHolder(itemView) {
    fun bindData(document : MonclerDocument)
    {
        itemView.findViewById<TextView>(R.id.item_document_title).setText(document.mediaTitleCustom)
    }

    fun setOnItemClickListener(listener : IMonclerDocumentsListener)
    {
        itemView.setOnClickListener { listener.onItemClick(adapterPosition) }
    }
}

interface IMonclerDocumentsListener{
    fun onItemClick(position: Int)
    //fun onItemSwipe()
}

