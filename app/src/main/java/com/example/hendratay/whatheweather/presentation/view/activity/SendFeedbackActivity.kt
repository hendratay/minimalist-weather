package com.example.hendratay.whatheweather.presentation.view.activity

import android.accounts.AccountManager
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.example.hendratay.whatheweather.R
import kotlinx.android.synthetic.main.activity_sendfeedback.*
import android.content.Intent



class SendFeedbackActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sendfeedback)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon?.setColorFilter(resources.getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
        setupSpinnerFrom()
        setupSpinnerAbout()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sendfeedback, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        R.id.send -> {
            sendFeedback()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setupSpinnerFrom() {
        val emailPattern = Patterns.EMAIL_ADDRESS
        val accounts = AccountManager.get(this).accounts
        val accountList = ArrayList<String>()
        for (account in accounts) {
            if (emailPattern.matcher(account.name).matches()) accountList.add(account.name)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, accountList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_from.adapter = adapter
    }

    private fun setupSpinnerAbout() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.feedback_about))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_about.adapter = adapter
    }

    private fun sendFeedback() {
        // todo: send email without using intent
    }

}