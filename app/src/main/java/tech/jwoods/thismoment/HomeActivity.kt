package tech.jwoods.thismoment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import tech.jwoods.thismoment.data.MomentRepository

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        momentsRecycler.layoutManager = GridLayoutManager(this, 2)

        val momentAdapter = MomentAdapter()
        momentsRecycler.adapter = momentAdapter

        val momentRepository = MomentRepository()
        momentAdapter.submitList(momentRepository.getMoments())
    }
}