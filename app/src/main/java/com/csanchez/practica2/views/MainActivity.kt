package com.csanchez.practica2.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.csanchez.practica2.R
import com.csanchez.practica2.adapters.MonstersAdapter
import com.csanchez.practica2.databinding.ActivityMainBinding
import com.csanchez.practica2.model.Monster
import com.csanchez.practica2.db.DbMonsters

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listmonsters: ArrayList<Monster>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addNewMonsterButton.setOnClickListener() {

            startActivity(Intent(this@MainActivity, AddNewMonsterActivity::class.java))

        }

        val dbMonsters = DbMonsters(this)

        var listMonsters = dbMonsters.getMonsters()

        val monstersAdapter = MonstersAdapter(this, listMonsters)
        binding.monstersList.layoutManager = LinearLayoutManager(this)
        binding.monstersList.adapter = monstersAdapter





    }

    fun selectedGame(monster: Monster){
        val intent = Intent(this, MonsterInfoActivity::class.java)
        intent.putExtra("ID", monster.id)
        startActivity(intent)

    }
    override fun onBackPressed() {

        finishAffinity()

    }




}