package com.example.bookfinderapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookfinderapp.R
import com.example.bookfinderapp.data.db.entity.BookMarkEntity
import com.example.bookfinderapp.databinding.ItemBooksBinding
import com.example.bookfinderapp.utility.loadCenterCrop

class BookListAdapter(
    private val callback: (data: BookMarkEntity) -> Unit
)  : RecyclerView.Adapter<BookListAdapter.BookItemViewHolder>() {

    private val adapterList = mutableListOf<BookMarkEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val view = ItemBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bindData(adapterList[position])
    }

    override fun getItemCount() = adapterList.size

    fun addAllSet(list: List<BookMarkEntity>) {
        val startPosition = itemCount
        adapterList.addAll(list)
        val endPosition = itemCount
        notifyItemRangeInserted(startPosition, endPosition)
    }

    fun clear() {
        adapterList.clear()
        notifyDataSetChanged()
    }

    inner class BookItemViewHolder(
        private val binding: ItemBooksBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: BookMarkEntity) = with(binding) {

            if (data.imageLinks.isEmpty()) { bookImage.setImageResource(R.drawable.ic_img_not) }
            else  bookImage.loadCenterCrop(data.imageLinks)

            bookTitle.text = data.title

            root.setOnClickListener {
                callback.invoke(data)
            }
        }
    }
}