package nl.entreco.giddyapp.profile.matches

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libcore.base.BaseActivity
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.profile.R
import nl.entreco.giddyapp.profile.databinding.ActivityMatchesBinding
import nl.entreco.giddyapp.profile.di.MatchComponent
import nl.entreco.giddyapp.profile.di.MatchModule
import nl.entreco.giddyapp.profile.di.ProfileInjector.fromModule
import nl.entreco.giddyapp.profile.matches.details.MatchDetailFragment


class MatchActivity : BaseActivity(), DiProvider<MatchComponent> {

    private val component by fromModule { MatchModule }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val loader: ImageLoader by lazy { component.loader() }
    private val snapHelper = LinearSnapHelper()
    private val adapter by lazy {
        MatchPagerAdapter(loader) { userLike ->
            handleSelected(userLike)
        }
    }

    private fun handleSelected(userLike: UserLike) {
        adapter.setSelectedItem(userLike)
        showSelected(userLike)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMatchesBinding>(this, R.layout.activity_matches)
        binding.viewModel = viewModel

        observeMatches()
        observeProfile(intent)
        observeSelectedItem()
        setupPager(binding.profileMatchRecycler)
    }

    private fun observeProfile(intent: Intent?) {
        viewModel.retrieveMatches(intent?.getStringExtra(EXTRA_UID) ?: "")
    }

    private fun observeMatches() {
        viewModel.matches().observe(this, Observer { list ->
            adapter.postItems(list)
            if (list.isNotEmpty()) {
                viewModel.selectedItem(0)
            }
        })
    }

    private fun observeSelectedItem() {
        viewModel.selected().observe(this, Observer { userLike ->
            handleSelected(userLike)
        })
    }

    private fun showSelected(userLike: UserLike) {
        Log.i("SELECTED", "YEAH: position:$userLike")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.match_container, MatchDetailFragment.newInstance(userLike))
            .commit()
    }

    private fun setupPager(recycler: RecyclerView) {
        recycler.adapter = adapter
        snapHelper.attachToRecyclerView(recycler)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.attachSnapHelperWithListener(
            snapHelper,
            SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE,
            object : OnSnapPositionChangeListener {
                override fun onSnapPositionChange(position: Int) {
//                    viewModel.selectedItem(position)
                }
            })
    }

    fun RecyclerView.attachSnapHelperWithListener(
        snapHelper: SnapHelper,
        behavior: SnapOnScrollListener.Behavior = SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
        onSnapPositionChangeListener: OnSnapPositionChangeListener
    ) {
        snapHelper.attachToRecyclerView(this)
        val snapOnScrollListener =
            SnapOnScrollListener(snapHelper, behavior, onSnapPositionChangeListener)
        addOnScrollListener(snapOnScrollListener)
    }

    override fun get(): MatchComponent = component

    companion object {
        private const val EXTRA_UID = "EXTRA_UID"
        fun launch(activity: Activity, uid: String) {
            val intent = Intent(activity, MatchActivity::class.java).apply {
                putExtra(EXTRA_UID, uid)
            }
            activity.startActivity(intent)
        }
    }
}