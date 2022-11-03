package com.csanchez.practica2.views

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.csanchez.practica2.R
import com.csanchez.practica2.databinding.ActivityMonsterInfoBinding
import com.csanchez.practica2.db.DbMonsters
import com.csanchez.practica2.model.Monster


class MonsterInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMonsterInfoBinding
    var monster: Monster? = null
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonsterInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        if(bundle!=null){
            id = bundle.getInt("ID",0)
        }

        var db = DbMonsters(this)

        monster = db.getMonster(id)

        monster?.let{ monster ->
            with(binding){
                monsterName.setText(monster.name)
                monsterOrigin.setText(monster.origin)
                monsterDescription.setText(monster.description)



                var monsterGenres = resources.getStringArray(R.array.monsters_types_array)

                when(monster.genrer){
                    1 -> {
                        monsterGenrer.setText(monsterGenres[1].toString())
                        monsterGengerImage.setImageDrawable(getDrawable(R.drawable.icon_kaiju))
                    }
                    2 -> {
                        monsterGenrer.setText(monsterGenres[2].toString())
                        monsterGengerImage.setImageDrawable(getDrawable(R.drawable.icon_myth))
                    }
                    3 -> {
                        monsterGenrer.setText(monsterGenres[3].toString())
                        monsterGengerImage.setImageDrawable(getDrawable(R.drawable.icon_spacial))
                    }
                    4 -> {
                        monsterGenrer.setText(monsterGenres[4].toString())
                        monsterGengerImage.setImageDrawable(getDrawable(R.drawable.icon_paranormal))
                    }
                }


                //para que se desactive el teclado en los edittext
                monsterName.inputType = InputType.TYPE_NULL
                monsterOrigin.inputType = InputType.TYPE_NULL
                monsterDescription.inputType = InputType.TYPE_NULL
                monsterGenrer.inputType = InputType.TYPE_NULL
            }
        }

        binding.editMonsterButton.setOnClickListener(){
            val intent = Intent(this@MonsterInfoActivity, EditMonsterActivity::class.java)
            monster?.let { it1 ->
                intent.putExtra("ID", it1.id)

            }
            startActivity(intent)

        }

        binding.deleteMonsterButton.setOnClickListener() {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirmar_eliminar ))
                .setMessage(getString(R.string.confirmar_eliminar2 ))
                .setPositiveButton(getString(R.string.confirmar_eliminar_ok ), DialogInterface.OnClickListener { dialog, which ->
                    if(db.deleteMonster(id)){
                        Toast.makeText(this@MonsterInfoActivity, getString(R.string.monstruo_eliminado ), Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@MonsterInfoActivity, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@MonsterInfoActivity, getString(R.string.error_eliminar ), Toast.LENGTH_SHORT).show()
                    }
                })
                .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}