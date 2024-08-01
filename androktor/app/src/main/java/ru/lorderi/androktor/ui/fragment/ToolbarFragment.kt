package ru.lorderi.androktor.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.lorderi.androktor.R
import ru.lorderi.androktor.R.string
import ru.lorderi.androktor.databinding.FragmentToolbarBinding
import ru.lorderi.androktor.ui.viewmodel.ToolbarViewModel

@AndroidEntryPoint
class ToolbarFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentFragmentManager.beginTransaction()
            .setPrimaryNavigationFragment(this)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentToolbarBinding.inflate(inflater, container, false)
        navController =
            requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()


        drawerLayout = binding.drawerLayout

        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            binding.toolbar,
            string.nav_open,
            string.nav_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_my_resume -> {
                    navController.navigate(R.id.action_BottomMenuFragment_to_myResumeFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> false
            }
        }

        val appBarConfiguration =
            AppBarConfiguration(navController.graph, drawerLayout = drawerLayout)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        val toolbarViewModel by activityViewModels<ToolbarViewModel>()

        val beforeNavIcon = binding.toolbar.navigationIcon


        toolbarViewModel.cancellation
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { cancellation ->
                if (cancellation) {
                    binding.toolbar.setNavigationIcon(R.drawable.baseline_clear_24)
                    binding.toolbar.setNavigationOnClickListener {
                        toolbarViewModel.changeCancellation()
                    }
                } else {
                    binding.toolbar.setNavigationIcon(beforeNavIcon)
                    binding.toolbar.setNavigationOnClickListener {
                        if (appBarConfiguration.topLevelDestinations
                                .contains(navController.currentDestination?.id)
                        ) {
                            drawerLayout.openDrawer(GravityCompat.START)
                        } else {
                            navController.navigateUp()
                        }
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }
}