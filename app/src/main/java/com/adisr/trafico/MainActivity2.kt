package com.adisr.trafico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main2.*
enum class ProviderType{
    BASIC
}
//Falta arreglar el post en la firebase y revisar el codigo de este mismo si no funciona
class MainActivity2 : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        db.collection("posts").addSnapshotListener { value, error ->
            val posts = value!!.toObjects(Post::class.java)
            posts.forEachIndexed { index, post ->
                post.uid = value.documents[index].id

            }
            rv.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity2)
                adapter = PostAdapter(this@MainActivity2, posts)
            }


        }

    }
}