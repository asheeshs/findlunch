package com.asrivastava.lunchtime.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.asrivastava.lunchtime.R
import com.asrivastava.lunchtime.domain.util.LocationProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var locationProvider: LocationProvider

    val viewModel: NearByRestaurantsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchView = findViewById<SearchView>(R.id.searchView)
        val searchEditText = searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        searchEditText.setTextColor(resources.getColor(R.color.grey))
        searchEditText.setHintTextColor(resources.getColor(R.color.grey))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return query?.let {
                    searchView.clearFocus()
                    viewModel.onSearch(it)
                    true
                } ?: false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        start()
    }

    private fun start() {
        if (locationProvider.hasLocationPermission) {
            showFragment(RestaurantMapFragment())
        } else {
            requestPermissionLauncher.launch(LocationProvider.LOCATION_PERMISSION)
        }
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, fragment, fragment.tag)
            commit()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showFragment(RestaurantListFragment())
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.location_alert_title))
                    .setMessage(getString(R.string.location_alert_message))
                    .setPositiveButton(getString(R.string.location_alert_grant)) { _, _ -> goToAppSettings() }
                    .setNegativeButton(getString(R.string.location_alert_exit)) { _, _ -> finish() }
                    .create()
                    .show()
            }
        }

    private fun goToAppSettings() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, LocationProvider.LOCATION_PERMISSION)) {
            start()
        } else {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
            finish()
        }
    }
}
