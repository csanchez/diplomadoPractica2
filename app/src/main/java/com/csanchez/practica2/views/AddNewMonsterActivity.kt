package com.csanchez.practica2.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.csanchez.practica2.R
import com.csanchez.practica2.databinding.ActivityAddNewMonsterBinding
import com.csanchez.practica2.databinding.ActivityMainBinding
import com.csanchez.practica2.db.DbMonsters

class AddNewMonsterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  { //

    private lateinit var binding: ActivityAddNewMonsterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewMonsterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val spinner = binding.monsterGenrer


        ArrayAdapter.createFromResource(
            this,
            R.array.monsters_types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this

        binding.insertMonster.setOnClickListener() {
            this.insertMonster()
        }

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
         var imageHeader = binding.monsterGengerImage
        when(pos){
            0 -> {

            }
            1 -> {
                imageHeader.setImageDrawable(getDrawable(R.drawable.icon_kaiju))
            }
            2 -> {
                imageHeader.setImageDrawable(getDrawable(R.drawable.icon_myth))
            }
            3 -> {
                imageHeader.setImageDrawable(getDrawable(R.drawable.icon_spacial))
            }
            4 -> {
                imageHeader.setImageDrawable(getDrawable(R.drawable.icon_paranormal))
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun insertMonster(){
        val db = DbMonsters(this)

        with(binding){
            when{
                monsterName.text.toString().isEmpty() -> {
                    monsterName.error = getString(R.string.valor_requerido)
                    Toast.makeText(this@AddNewMonsterActivity, getString(R.string.llene_todos_los_datos), Toast.LENGTH_SHORT).show()
                }
                monsterOrigin.text.toString().isEmpty() -> {
                    monsterOrigin.error = getString(R.string.valor_requerido)
                    Toast.makeText(this@AddNewMonsterActivity, getString(R.string.llene_todos_los_datos), Toast.LENGTH_SHORT).show()
                }

                monsterDescription.text.toString().isEmpty() -> {
                    monsterDescription.error = getString(R.string.valor_requerido)
                    Toast.makeText(this@AddNewMonsterActivity, getString(R.string.llene_todos_los_datos), Toast.LENGTH_SHORT).show()
                }
                monsterGenrer.selectedItemPosition==0 ->{

                    Toast.makeText(this@AddNewMonsterActivity, getString(R.string.indica_tipo_mosntruo) , Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val id = db.insertMonster(monsterName.text.toString(), monsterOrigin.text.toString(), monsterDescription.text.toString(),monsterGenrer.selectedItemPosition )

                    if(id>0){
                        Toast.makeText(this@AddNewMonsterActivity, getString(R.string.mosntruo_guardado), Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@AddNewMonsterActivity, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@AddNewMonsterActivity, getString(R.string.error_guardar) , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@AddNewMonsterActivity, MainActivity::class.java))
    }
}