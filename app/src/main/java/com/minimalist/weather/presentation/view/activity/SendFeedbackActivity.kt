package com.minimalist.weather.presentation.view.activity

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.minimalist.weather.BuildConfig
import kotlinx.android.synthetic.main.activity_sendfeedback.*
import com.minimalist.weather.presentation.view.utils.Gmail
import kotlin.concurrent.thread
import android.content.IntentSender
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.common.AccountPicker
import com.minimalist.weather.R

class SendFeedbackActivity: AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_EMAIL = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sendfeedback)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon?.setColorFilter(resources.getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP)
        setupTextFrom()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE_EMAIL) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val email = data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
                    txt_from.text = email
                }
                Activity.RESULT_CANCELED -> finish()
            }
        }
    }

    private fun setupTextFrom() {
        try {
            val intent = AccountPicker.newChooseAccountIntent(
                    null, null, arrayOf(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE), true,
                    null, null, null, null)
            intent.putExtra("overrideTheme", 1)
            startActivityForResult(intent, REQUEST_CODE_EMAIL)
        } catch (e: IntentSender.SendIntentException) {
        }
    }

    private fun setupSpinnerAbout() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.feedback_about))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_about.adapter = adapter
    }

    private fun sendFeedback() {
        val progressDialog = progressDialog()
        thread {
            runOnUiThread { progressDialog.show() }
            try {
                val sender = Gmail(BuildConfig.APP_GMAIL, BuildConfig.APP_GMAIL_PASS)
                sender.sendMail(spinner_about.selectedItem.toString(),
                        input_feedback.text.toString(),
                        txt_from.text.toString(),
                        BuildConfig.CUSTOMER_SERVICE_GMAIL)
            } catch (e: Exception) {
            }
            runOnUiThread { progressDialog.dismiss() }
        }
    }

    private fun progressDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setView(layoutInflater.inflate(R.layout.dialog_progress, null))
        return builder.create()
    }

}