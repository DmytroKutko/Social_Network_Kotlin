package com.example.myapplication.view.fragments.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.itemsRow.ImageItem
import com.example.myapplication.model.Image
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

    private var adapter: GroupAdapter<ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setInitialData()
        loadImagesFromFirebase()
    }


    private fun loadImagesFromFirebase() {
        val imagesRef = FirebaseDatabase.getInstance().getReference("/images")
        imagesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    val image = it.getValue(Image::class.java) ?: return
                    adapter!!.add(ImageItem(image))
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    private fun setInitialData() {
        activity?.title = "Gallery"
        adapter = GroupAdapter()
        rvGallery.adapter = adapter
        rvGallery.layoutManager = GridLayoutManager(activity, 3)
    }
}
