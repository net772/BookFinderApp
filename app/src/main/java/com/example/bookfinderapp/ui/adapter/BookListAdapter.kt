package com.example.bookfinderapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookfinderapp.data.response.Book
import com.example.bookfinderapp.databinding.ItemBooksBinding
import com.example.bookfinderapp.utility.loadCenterCrop

class BookListAdapter(
    private val callback: (data: Book) -> Unit
)  : RecyclerView.Adapter<BookListAdapter.BookItemViewHolder>() {

    private val adapterList = mutableListOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val view = ItemBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bindData(adapterList[position])
    }

    override fun getItemCount() = adapterList.size

    fun set(list: List<Book>) {
        adapterList.addAll(list)
        notifyDataSetChanged()
    }

    inner class BookItemViewHolder(
        private val binding: ItemBooksBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: Book) = with(binding) {
            bookImage.loadCenterCrop(data.volumeInfo.imageLinks.thumbnail)
            bookTitle.text = data.volumeInfo.title

            root.setOnClickListener {
                callback.invoke(data)
            }
        }
    }
}