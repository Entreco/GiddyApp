package nl.entreco.giddyapp.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import nl.entreco.giddyapp.history.databinding.ActivityHistoryBinding
import nl.entreco.giddyapp.history.di.HistoryComponent
import nl.entreco.giddyapp.history.di.HistoryInjector.fromModule
import nl.entreco.giddyapp.history.di.HistoryModule
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libcore.base.BaseActivity
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.libcore.launch.LaunchHelper
import nl.entreco.giddyapp.libimg.loader.ImageLoader


class HistoryActivity : BaseActivity(), DiProvider<HistoryComponent> {

    private lateinit var binding: ActivityHistoryBinding
    private val component by fromModule { HistoryModule }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val loader: ImageLoader by lazy { component.loader() }
    private val snapHelper = LinearSnapHelper()
    private val pagerAdapter by lazy { DetailsPagerAdapter(supportFragmentManager, lifecycle) }
    private val adapter by lazy {
        HistoryAdapter(loader) { userLike ->
            handleSelected(userLike, Type.Selecting)
        }
    }

    private fun handleSelected(userLike: UserLike, type: Type) {
        val position = pagerAdapter.positionForItem(userLike)
        when (type) {
            is Type.Initial -> pageTo(position)
            is Type.Selecting -> pageTo(position)
            is Type.Paging -> scrollTo(position)
        }

        adapter.setSelectedItem(userLike)
        viewModel.loadDetails(userLike)
        supportActionBar?.title = userLike.horseName
        showSelected(userLike)
    }

    private fun pageTo(position: Int) {
        binding.matchPager.setCurrentItem(position, true)
    }

    private fun scrollTo(position: Int) {
        binding.includeRecycler.matchRecycler.smoothScrollToPosition(position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history)
        binding.viewModel = viewModel

        observeMatches()
        observeProfile(intent)
        observeSelectedItem()
        setupRecycler(binding.includeRecycler.matchRecycler)
        setupPager(binding.matchPager)

        setSupportActionBar(binding.includeToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_matches, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_delete -> viewModel.deleteMatch(currentUser(intent)){
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeProfile(intent: Intent?) {
        viewModel.retrieveMatches(currentUser(intent))
    }

    private fun currentUser(intent: Intent?) =
        intent?.getStringExtra(LaunchHelper.HISTORY_EXTRA_UID) ?: ""

    private fun observeMatches() {
        viewModel.matches().observe(this, Observer { list ->
            adapter.postItems(list)
            pagerAdapter.postItems(list)
            if (list.isNotEmpty()) {
                viewModel.selectedItem(0, Type.Initial)
            }
        })
    }

    private fun observeSelectedItem() {
        viewModel.selected().observe(this, Observer { pair ->
            pair.first?.let {
                handleSelected(it, pair.second)
            }
        })
    }

    private fun showSelected(userLike: UserLike) {
        Log.i("SELECTED", "YEAH: position:$userLike")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.header_container, HistoryHeaderFragment.newInstance(userLike))
            .commit()
    }

    private fun setupPager(pager: ViewPager2) {
        pager.adapter = pagerAdapter
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                pagerAdapter.itemAtPosition(position)?.let { userLike ->
                    handleSelected(userLike, Type.Paging)
                }
            }
        })
    }

    private fun setupRecycler(recycler: RecyclerView) {
        recycler.adapter = adapter
        snapHelper.attachToRecyclerView(recycler)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun get(): HistoryComponent = component
}