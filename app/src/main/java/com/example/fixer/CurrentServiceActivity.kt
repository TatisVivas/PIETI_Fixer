package com.example.fixer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.fixer.databinding.ActivityCurrentServiceBinding
import com.example.fixer.model.Person
import com.example.fixer.model.PersonPagerAdapter

class CurrentServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCurrentServiceBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: PersonPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = binding.viewPager
        adapter = PersonPagerAdapter(getPersonList())
        viewPager.adapter = adapter
    }

    private fun getPersonList(): List<Person> {
        return listOf(
            Person("John Doe", "Description 1", R.drawable.johndoe,
                "John Doe tiene más de 15 años de experiencia, experto en reparaciones e instalaciones rápidas y eficaces. Conocido por su atención al detalle y compromiso con la satisfacción del cliente.",
                4.5f),
            Person("Jane Smith", "Description 2", R.drawable.janesmith,
                "Jane Smith es una fontanera confiable con 10 años de experiencia. Especializada en instalaciones sostenibles y reparaciones eficientes, es muy valorada por su trato cercano y resultados garantizados.",
                3.0f),
            Person("Alice Johnson", "Description 3", R.drawable.aliciajohnson,
                "Alice Johnson, con 12 años de experiencia, se destaca en sistemas avanzados de fontanería. Ofrece soluciones modernas y duraderas, con un servicio rápido y una excelente atención al cliente.",
                4.0f)
        )
    }
}