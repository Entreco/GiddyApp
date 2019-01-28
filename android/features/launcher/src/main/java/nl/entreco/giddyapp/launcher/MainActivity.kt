package nl.entreco.giddyapp.launcher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import nl.entreco.giddyapp.core.LaunchHelper
import nl.entreco.giddyapp.core.base.BaseActivity

class MainActivity : BaseActivity() {

    companion object {
        fun launch(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.viewer).setOnClickListener {
            LaunchHelper.launchViewer(this, null)
        }

        findViewById<Button>(R.id.creator).setOnClickListener {
            LaunchHelper.launchCreator(this, null)
        }
    }
}