package com.example.crudapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Sepatu
import kotlinx.android.synthetic.main.activity_sepatu.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SepatuActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var sepatuAdapter: SepatuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sepatu)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadSepatu()
    }

    fun loadSepatu(){
        CoroutineScope(Dispatchers.IO).launch {
            val allSepatu = db.sepatuDao().getAllSepatu()
            Log.d("SepatuActivity", "dbResponse: $allSepatu")
            withContext(Dispatchers.Main) {
                sepatuAdapter.setData(allSepatu)
            }
        }
    }

    fun setupListener() {
        btn_createSepatu.setOnClickListener {
           intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        sepatuAdapter = SepatuAdapter(arrayListOf(), object: SepatuAdapter.OnAdapterListener {
            override fun onClick(sepatu: Sepatu) {
                // read detail
                intentEdit(sepatu.id, Constant.TYPE_READ)
            }

            override fun onDelete(sepatu: Sepatu) {
                // delete data
                deleteDialog(sepatu)
            }

            override fun onUpdate(sepatu: Sepatu) {
                // edit data
                intentEdit(sepatu.id, Constant.TYPE_UPDATE)
            }

        })
        list_sepatu.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = sepatuAdapter
        }
    }

    fun intentEdit(sepatuId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditSepatuActivity::class.java)
                .putExtra("intent_id", sepatuId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(sepatu: Sepatu) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.sepatuDao().deleteSepatu(sepatu)
                    loadSepatu()
                }
            }
        }
        alertDialog.show()
    }
}