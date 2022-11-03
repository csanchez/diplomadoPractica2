package com.csanchez.practica2.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.csanchez.practica2.R
import com.csanchez.practica2.databinding.ActivityEditMonsterBinding
import com.csanchez.practica2.db.DbMonsters
import com.csanchez.practica2.model.Monster

class EditMonsterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityEditMonsterBinding
    var monster: Monster? = null
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMonsterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        if(bundle!=null){
            id = bundle.getInt("ID",0)
        }

        var db = DbMonsters(this)

        monster = db.getMonster(id)

        monster?.let { monster ->
            with(binding) {
                editMonsterName.setText(monster.name)
                editMonsterOrigin.setText(monster.origin)
                editMonsterDescription.setText(monster.description)
                editMonsterGenrer.setSelection(monster.genrer)
                when(monster.genrer){
                    1 -> monsterGengerImage.setImageDrawable(getDrawable(R.drawable.icon_kaiju))
                    2 -> monsterGengerImage.setImageDrawable(getDrawable(R.drawable.icon_myth))
                    3 -> monsterGengerImage.setImageDrawable(getDrawable(R.drawable.icon_spacial))
                    4 -> monsterGengerImage.setImageDrawable(getDrawable(R.drawable.icon_paranormal))
                }


                editMonsterGenrer.onItemSelectedListener = this@EditMonsterActivity

                updateMonster.setOnClickListener() {
                    this@EditMonsterActivity.updateMonster()
                }
            }
        }


    }

    fun updateMonster(){
        val db = DbMonsters(this)

        with(binding){
            when{
                editMonsterName.text.toString().isEmpty() -> {
                    editMonsterName.error = getString(R.string.valor_requerido)
                    Toast.makeText(this@EditMonsterActivity, getString(R.string.llene_todos_los_datos), Toast.LENGTH_SHORT).show()
                }
                editMonsterOrigin.text.toString().isEmpty() -> {
                    editMonsterOrigin.error = getString(R.string.valor_requerido)
                    Toast.makeText(this@EditMonsterActivity, getString(R.string.llene_todos_los_datos), Toast.LENGTH_SHORT).show()
                }

                editMonsterDescription.text.toString().isEmpty() -> {
                    editMonsterDescription.error = getString(R.string.valor_requerido)
                    Toast.makeText(this@EditMonsterActivity, getString(R.string.llene_todos_los_datos), Toast.LENGTH_SHORT).show()
                }
                editMonsterGenrer.selectedItemPosition==0 ->{

                    Toast.makeText(this@EditMonsterActivity,  getString(R.string.indica_tipo_mosntruo), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val updated = db.updateMonster(id,editMonsterName.text.toString(), editMonsterOrigin.text.toString(), editMonsterDescription.text.toString(),editMonsterGenrer.selectedItemPosition )

                    if(updated){
                        Toast.makeText(this@EditMonsterActivity, getString(R.string.monstruo_actualizado ), Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@EditMonsterActivity, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@EditMonsterActivity, getString(R.string.error_aztualizar ), Toast.LENGTH_SHORT).show()
                    }
                }
            }
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

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MonsterInfoActivity::class.java)
        monster?.let { intent.putExtra("ID", it.id) }
        startActivity(intent)
        finish()

    }
}