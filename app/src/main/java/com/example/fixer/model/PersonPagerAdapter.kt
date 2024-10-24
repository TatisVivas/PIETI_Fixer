package com.example.fixer.model


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fixer.CurrentServiceActivity
import com.example.fixer.CustomerActivity
import com.example.fixer.databinding.ItemPersonBinding

data class Person(
    val name: String,
    val description: String,
    val photoResId: Int,
    val jobDescription: String,
    val rating: Float)


class PersonPagerAdapter(private val personList: List<Person>) : RecyclerView.Adapter<PersonPagerAdapter.PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = personList[position]
        holder.bind(person)
    }

    override fun getItemCount(): Int = personList.size

    class PersonViewHolder(private val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.personName.text = person.name
            binding.personJobDescription.text = person.jobDescription
            binding.personRating.rating = person.rating
            binding.personImage.setImageResource(person.photoResId)
            binding.hireButton.setOnClickListener {
                // Handle hire button click
                Toast.makeText(binding.root.context, "Ha contatado a ${person.name}. Espere un mensaje de aceptación por parte del trabajador.", Toast.LENGTH_SHORT).show()
                val context = binding.root.context
                val intent = Intent(context, CustomerActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}