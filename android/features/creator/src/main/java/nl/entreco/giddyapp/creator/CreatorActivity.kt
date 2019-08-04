package nl.entreco.giddyapp.creator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import nl.entreco.giddyapp.creator.databinding.ActivityCreatorBinding
import nl.entreco.giddyapp.creator.di.CreatorComponent
import nl.entreco.giddyapp.creator.di.CreatorInjector.fromModule
import nl.entreco.giddyapp.creator.di.CreatorModule
import nl.entreco.giddyapp.creator.ui.bottom.BottomProgressModel
import nl.entreco.giddyapp.libcore.base.BaseActivity
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.libcore.launch.LaunchHelper
import nl.entreco.giddyapp.libpicker.ImagePickerResult

class CreatorActivity : BaseActivity(), DiProvider<CreatorComponent> {

    private lateinit var binding: ActivityCreatorBinding
    private val component by fromModule { CreatorModule() }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val animator by lazy { component.animator() }
    private val picker by lazy { component.picker() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_creator)
        binding.viewModel = viewModel
        viewModel.state().observe(this, stateObserver)
        viewModel.events().observe(this, eventObserver)
        viewModel.snacks().observe(this, snackObserver)
    }

    private val stateObserver = Observer<CreatorState> { state ->
        viewModel.currentState.set(BottomProgressModel(state))
        animator.render(state) { id ->
            LaunchHelper.launchViewer(this, null, id)
            finish()
        }
    }

    private val eventObserver = Observer<CreatorState.Event> { event ->
        when (event) {
            is CreatorState.Event.PickCamera -> picker.selectImage(true)
            is CreatorState.Event.PickGallery -> picker.selectImage(false)
        }
    }

    private val snackObserver = Observer<String> { item ->
        Snackbar.make(binding.root, item, Snackbar.LENGTH_INDEFINITE)
            .setAction("Login") {
                // TODO entreco - 2019-07-13: Should add Login Dialog here...
                finish()
            }.show()
    }

    override fun onResume() {
        super.onResume()
        setSupportActionBar(binding.includeToolbarCreator.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.creator_title)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) cancel()
        return super.onOptionsItemSelected(item)
    }

    private fun cancel() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun onBackPressed() {
        viewModel.popSate()
        super.onBackPressed()
    }

    override fun get(): CreatorComponent =component

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        picker.get(requestCode, resultCode, data) { result ->
            when (result) {
                is ImagePickerResult.Success -> viewModel.imageSelected(result.images)
                is ImagePickerResult.Failed -> viewModel.imageRejected(result.reason)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}