package com.example.simplereader_v1.Adapters

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplereader_v1.MainActivity
import com.example.simplereader_v1.MyPdfActivity
import com.example.simplereader_v1.database.models.LibraryBook
import com.example.simplereader_v1.databinding.LibraryBookItemBinding
import com.pspdfkit.configuration.activity.PdfActivityConfiguration
import com.pspdfkit.configuration.activity.ThumbnailBarMode
import com.pspdfkit.ui.PdfActivityIntentBuilder

class LibraryBooksFragmentAdapter(val context : FragmentActivity) : ListAdapter<LibraryBook,LibraryBooksFragmentAdapter.LibraryBookViewHolder>(
    DiffCallBack){
     init {
         Log.d("TagD","init")
         Log.d("TagD","init$itemCount")

     }

    class LibraryBookViewHolder(var binding : LibraryBookItemBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryBookViewHolder {
        Log.d("TagD","onCreateAdapter")
        return LibraryBookViewHolder(
            LibraryBookItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: LibraryBookViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            thumbnail.setImageURI(Uri.parse(item.thumbnailUri))
            Log.d("TagD",item.thumbnailUri)
            thumbnail.setOnClickListener(){
                Log.d("TagD","onClick")
                val config = PdfActivityConfiguration
                    .Builder(context)
                    .setThumbnailBarMode(ThumbnailBarMode.THUMBNAIL_BAR_MODE_NONE)
                    .build()
                val intent = PdfActivityIntentBuilder
                    .fromUri(context,Uri.parse(item.locationUri))
                    .configuration(config)
                    .activityClass(MyPdfActivity::class.java)
                    .build()

                val bundle = Bundle()
                bundle.putString(MainActivity.LOCATION_URI,item.locationUri)
                bundle.putString(MainActivity.THUMNAIL_URI,item.thumbnailUri)
                bundle.putString(MainActivity.LIBRARY_BOOK_NAME,item.name)
                bundle.putInt(MainActivity.ID,item.id)

                intent.putExtras(bundle)
                context.startActivity(intent)


            }
            delete.setOnClickListener(){

            }

        }
    }

    companion object{
        private val DiffCallBack = object : DiffUtil.ItemCallback<LibraryBook>(){
            override fun areItemsTheSame(oldItem: LibraryBook, newItem: LibraryBook): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: LibraryBook, newItem: LibraryBook): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}