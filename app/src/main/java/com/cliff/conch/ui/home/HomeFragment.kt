package com.cliff.conch.ui.home

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.conch.box.service.ProxyService
import com.cliff.conch.databinding.FragmentHomeBinding
import com.orhanobut.logger.Logger

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Logger.i("开启通知:授权通知")
                startNotificationService()
            } else {
                Logger.i("开启通知:拒绝授权")
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotification() {
        Logger.i("开启通知:checkNotification")
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // 检测该应用是否有通知权限
        when (manager.areNotificationsEnabled()) {
            true -> {
                startNotificationService()
                Toast.makeText(context, "点击了通知按钮", Toast.LENGTH_SHORT).show()
            }

            false -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }

    private fun startNotificationService() {
        Logger.i("开启前台Service:${Build.VERSION.SDK_INT}")
        context?.startForegroundService(
            Intent(
                context?.applicationContext,
                ProxyService::class.java
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotification()
        } else {
            Logger.i("不开启通知:${Build.VERSION.SDK_INT}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = SectionAdapter(requireContext())
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
        binding.list.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        homeViewModel.sections.observe(viewLifecycleOwner) {
            Logger.d("observe data:" + it.size)
            adapter.submitList(it)
        }
        Logger.d(homeViewModel.sections.value?.size)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}