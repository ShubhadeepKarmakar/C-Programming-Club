package com.example.cprogrammingclub.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.cprogrammingclub.MainActivity
import com.example.cprogrammingclub.authentication.AuthenticationViewModel
import com.example.cprogrammingclub.authentication.LoginActivity
import com.example.cprogrammingclub.databinding.FragmentProfileBinding
import com.example.cprogrammingclub.progressbar.ProgressViewModel
import com.example.usertodatabase.utils.AppPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding?=null
    private val binding get() = _binding!!

    @Inject
    lateinit var appPreference: AppPreference

    private val progressViewModel by activityViewModels<ProgressViewModel>()
    private val authenticationViewModel by activityViewModels<AuthenticationViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val parentActivity = requireActivity() as MainActivity
        parentActivity.hideBottomNavAndToolBar()
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        progressViewModel.createProgress()//Check Point
//        progressViewModel.getProgress()//Check Point

        progressObserver()
        authenticationViewModel.getUserData()
        userObserver()

        binding.btnClaimCertificate.setOnClickListener {
            buttonWithIntent("https://www.cprogrammingclub.com/")
        }

        binding.btnGithub.setOnClickListener {
            buttonWithIntent("https://github.com/ShubhadeepKarmakar/cprogrammingclub")
        }
        binding.btnWebsite.setOnClickListener {
            buttonWithIntent("https://www.cprogrammingclub.com/")
        }
        binding.btnAbout.setOnClickListener {
            buttonWithIntent("https://www.cprogrammingclub.com/")
        }
        binding.btnUpdate.setOnClickListener {
            buttonWithIntent("https://www.cprogrammingclub.com/")
        }
        binding.btnLogout.setOnClickListener {
         authenticationViewModel.logout()
            appPreference.setSharedPerferences("") //for logout
            val lIntent=Intent(requireContext(),LoginActivity::class.java)
            startActivity(lIntent)
        }

    }
    private fun progressObserver() {
        progressViewModel.progressLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBarPercent.progress= it
            binding.progressPercent.text= "$it%"
        })
    }
    private fun userObserver() {
        authenticationViewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            binding.name.text = it.get(0).name
            binding.email.text = it.get(0).emailId
        })
    }
    private fun buttonWithIntent(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        val parentActivity = requireActivity() as MainActivity
        parentActivity.showBottomNavAndToolBar()
    }
}