package com.cookandroid.teamproject1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cookandroid.teamproject1.databinding.FirstTitleBinding
import com.cookandroid.teamproject1.id.view.activity.SelectDestActivity
import com.cookandroid.teamproject1.id.view.activity.SignInActivity
import com.cookandroid.teamproject1.id.view.activity.SignUpingActivity

class FirstTitleActivity : AppCompatActivity() {

    lateinit var binding: FirstTitleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = FirstTitleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textSignup.setOnClickListener {
            startActivity(Intent(this, SignUpingActivity::class.java))
        }

        binding.textSignin.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }


    }
}