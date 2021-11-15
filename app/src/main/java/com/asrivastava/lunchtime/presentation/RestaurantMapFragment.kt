package com.asrivastava.lunchtime.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.asrivastava.lunchtime.R
import com.asrivastava.lunchtime.domain.model.Restaurant
import com.asrivastava.lunchtime.presentation.components.ToggleViewButton
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@AndroidEntryPoint
class RestaurantMapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        view.findViewById<ComposeView>(R.id.composeButtonView).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    Box {
                        Row(
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .align(Alignment.BottomCenter)
                        ) {
                            ToggleViewButton(
                                iconId = R.drawable.ic_baseline_format_list_bulleted_24,
                                titleId = R.string.go_to_list_view,
                                onClicked = {
                                    (activity as MainActivity).showFragment(RestaurantListFragment())
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private val callback = OnMapReadyCallback { map ->
        map.setInfoWindowAdapter(RestaurantMarkerAdapter(requireContext()))
        val viewModel = (activity as MainActivity).viewModel
        lifecycleScope.launchWhenResumed {
            viewModel.restaurants.collect { result ->
                if (result.restaurants.isNotEmpty()) {
                    val markers = result.restaurants.map { it.toMarkerOptions() }.map { map.addMarker(it) }
                    result.restaurants.first().let {
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLngBounds(
                                    LatLng(it.southWest.lat, it.southWest.lng),
                                    LatLng(it.northEast.lat, it.northEast.lng)
                                ).center, 13f
                            )
                        )
                        markers.first()?.showInfoWindow()
                    }
                }
            }
        }
    }

    private fun Restaurant.toMarkerOptions() = MarkerOptions()
        .position(LatLng(location.lat, location.lng))
        .title(name)
        .snippet(Json.encodeToString(this))

    class RestaurantMarkerAdapter(
        private val context: Context
    ) : GoogleMap.InfoWindowAdapter {

        override fun getInfoContents(marker: Marker): View? {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.map_marker, null)
            val restaurant: Restaurant = Json.decodeFromString(marker.snippet!!)
            view.findViewById<TextView>(R.id.rest_title).text = restaurant.name
            view.findViewById<RatingBar>(R.id.rest_rating_bar).apply {
//                max = restaurant.rating.toInt()
                numStars = restaurant.rating.toInt()
//                (progressDrawable as LayerDrawable) .also {
//                    getDrawable(0).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
//                    getDrawable(1).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
//                    getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP)
//                }
            }

            view.findViewById<TextView>(R.id.rest_user_rating).text = "(${restaurant.totalUserRatings})"
            val dollars = restaurant.priceLevel?.let {
                (0..it).joinToString("") { "$" } + " · "
            } ?: ""
            val thirdLine = "$dollars${
                restaurant.businessStatus.lowercase()
                    .replaceFirstChar { ch -> ch.uppercase() }
            }"
            view.findViewById<TextView>(R.id.rest_price_level).text = thirdLine
            return view
        }

        override fun getInfoWindow(marker: Marker): View? {
            return null
        }
    }
}
