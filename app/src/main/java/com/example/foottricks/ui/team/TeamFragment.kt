package com.example.foottricks.ui.team

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.foottricks.activity.AttendanceActivity
import com.example.foottricks.activity.LoginActivity
import com.example.foottricks.databinding.FragmentTeamBinding


class TeamFragment : Fragment() {
    private var _binding: FragmentTeamBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.cardAttendance.setOnClickListener {
            startActivity(Intent(requireContext(), AttendanceActivity::class.java))

        }


        return root    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}