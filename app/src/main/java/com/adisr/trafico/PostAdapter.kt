package com.adisr.trafico
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.card_post.view.*
import java.text.SimpleDateFormat

class PostAdapter(private val activity: Activity, private val dataset: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    private val auth =  FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    class ViewHolder(val layout: View):RecyclerView.ViewHolder(layout)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val layout = LayoutInflater.from(parent.context).inflate(R.layout.card_post,parent, false)
        return ViewHolder(layout)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = dataset[position]
        val likes = post.likes!!.toMutableList()
        var liked = likes.contains(auth.uid)

        holder.layout.namePeron_tv.text = post.userName
        holder.layout.post_tv.text = post.post

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm a")

        holder.layout.fecha_tv.text = sdf.format(post.date)
        setColor(liked, holder.layout.like_btn)


        holder.layout.like_btn.setOnClickListener {
            liked = !liked
            setColor(liked, holder.layout.like_btn)

            if (liked) likes.add(auth.uid!!)
            else likes.remove(auth.uid)

            val doc = db.collection("posts").document(post.uid!!)

            db.runTransaction {

                it.update(doc, "likes", likes)
                null
            }
        }


         holder.layout.share_btn.setOnClickListener {

            val sendIntent = Intent().apply {
                action=Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.post)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            activity.startActivity(shareIntent)
        }
    }

        private fun setColor(liked: Boolean, likeButton: Button) {
        if (liked) likeButton.setTextColor(ContextCompat.getColor(activity, R.color.teal_700))
        else likeButton.setTextColor(Color.BLACK)
  }
}

