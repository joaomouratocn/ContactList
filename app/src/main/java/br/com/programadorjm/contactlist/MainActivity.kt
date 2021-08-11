package br.com.programadorjm.contactlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.main_toolbar))
        actionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_main, ContactListFragment.newInstance(), "fragment-list")
                .commit()
        }
    }
}