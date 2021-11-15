package com.asrivastava.lunchtime.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.asrivastava.lunchtime.presentation.components.RestaurantListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    val activity = activity as MainActivity
                    RestaurantListScreen(activity.viewModel) {
                        activity.showFragment(RestaurantMapFragment())
                    }
                }
            }
        }
    }

//    @Preview
//    @Composable
//    fun UiPreview(viewModel: NearByRestaurantsViewModel) {
//        RestaurantListScreen(viewModel)
//    }
}
