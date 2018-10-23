package com.example.hendratay.whatheweather.presentation.view.activity

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.example.hendratay.whatheweather.BuildConfig
import com.example.hendratay.whatheweather.R
import kotlinx.android.synthetic.main.activity_sendfeedback.*
import com.example.hendratay.whatheweather.presentation.view.utils.Gmail
import kotlin.concurrent.thread
import android.content.IntentSender
import com.google.android.gms.auth.api.credentials.*

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
                    val credential = data?.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
                    txt_from.text = credential?.id
                }
                Activity.RESULT_CANCELED -> finish()
            }
        }
    }

    private fun setupTextFrom() {
        val mCredentialsClient = Credentials.getClient(this)
        val hintRequest = HintRequest.Builder()
                .setHintPickerConfig(CredentialPickerConfig.Builder()
                        .setShowCancelButton(false)
                        .build())
                .setEmailAddressIdentifierSupported(true)
                .setAccountTypes(IdentityProviders.GOOGLE)
                .build()
        val intent = mCredentialsClient.getHintPickerIntent(hintRequest)
        try {
            startIntentSenderForResult(intent.intentSender, 1, null, 0, 0, 0)
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