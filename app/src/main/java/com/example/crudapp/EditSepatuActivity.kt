package com.example.crudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Sepatu
import kotlinx.android.synthetic.main.activity_edit_sepatu.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditSepatuActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var sepatuId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sepatu)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_saveSepatu.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.sepatuDao().addSepatu(
                    Sepatu(0, txt_merk.text.toString(), Integer.parseInt(txt_stok.text.toString()), Integer.parseInt(txt_harga.text.toString()) )
                )
                finish()
            }
        }
        btn_updateSepatu.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.sepatuDao().updateSepatu(
                    Sepatu(sepatuId, txt_merk.text.toString(), Integer.parseInt(txt_stok.text.toString()), Integer.parseInt(txt_harga.text.toString()) )
                )
                finish()
            }
        }
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_updateSepatu.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_saveSepatu.visibility = View.GONE
                btn_updateSepatu.visibility = View.GONE
                getSepatu()
            }
            Constant.TYPE_UPDATE -> {
                btn_saveSepatu.visibility = View.GONE
                getSepatu()
            }
        }
    }

    fun getSepatu() {
        sepatuId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
           val sepatus =  db.sepatuDao().getSepatu( sepatuId )[0]
            txt_merk.setText( sepatus.merk )
            txt_stok.setText( sepatus.stok.toString() )
            txt_harga.setText( sepatus.harga.toString() )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}