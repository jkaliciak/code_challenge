package dev.jakal.codechallenge.ui.events

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.jakal.codechallenge.R
import dev.jakal.codechallenge.databinding.FragmentEventsBinding
import dev.jakal.codechallenge.ui.common.model.Event
import dev.jakal.codechallenge.ui.common.viewBinding
import dev.jakal.codechallenge.ui.events.adapter.VideoEventsAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventsFragment : Fragment(R.layout.fragment_events) {

    private val binding by viewBinding(FragmentEventsBinding::bind)
    private val viewModel: EventsViewModel by viewModels()
    private val adapter = VideoEventsAdapter { viewModel.onVideoEventClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        binding.events.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.events.addItemDecoration(dividerItemDecoration)

        binding.swipeRefresh.setOnRefreshListener { viewModel.fetchVideoEvents() }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { state -> updateUi(state) }
        }

        lifecycleScope.launch {
            viewModel.event
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .filterNotNull()
                .collect { event -> handleEvent(event) }
        }
    }

    private fun updateUi(uiState: UiState) {
        adapter.submitList(uiState.videoEvents)
        binding.swipeRefresh.isRefreshing = uiState.isLoading
    }

    private fun handleEvent(event: Event<UiEvent>) {
        event.getContentIfNotHandled()?.let { uiEvent ->
            when (uiEvent) {
                is UiEvent.Error.General -> Snackbar.make(
                    binding.root,
                    getString(R.string.general_error_message),
                    Snackbar.LENGTH_LONG
                ).show()
                is UiEvent.Navigation.OpenVideo -> Toast.makeText(
                    requireContext(),
                    "List item clicked ${uiEvent.videoEvent.title}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

