package com.cookandroid.teamproject1.home.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookandroid.teamproject1.R
import com.cookandroid.teamproject1.databinding.FragmentHomeBinding
import com.cookandroid.teamproject1.home.model.HomeDataModel
import com.cookandroid.teamproject1.home.view.adapter.HomeRVAdapter
import com.cookandroid.teamproject1.util.TloverApplication

class HomeFragment : Fragment(){

    private var mBinding : FragmentHomeBinding?= null
    private var dataList = ArrayList<HomeDataModel>()

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        mBinding = binding

//        mbinding.Fragment.text = getString(R.string.home_user_hi).format(InfraApplication.prefs.getString("userNickName", "null"))
        // RVAdapter
        mBinding!!.fragmentHomeMainFirstTxt.text = "%s님, 안녕하세요!".format(TloverApplication.prefs.getString("userNickname", "null"))

        dataList.apply{
            add(HomeDataModel("title1", R.drawable.img1_item_home_random,"2022.04.28","Peter","Queens"))
            add(HomeDataModel("title2", R.drawable.img2_item_home_random,"2022.06.03","Tony","NewYork"))
            add(HomeDataModel("title1", R.drawable.img1_item_home_random,"2022.04.28","Peter","Queens"))
            add(HomeDataModel("title2", R.drawable.img2_item_home_random,"2022.06.03","Tony","NewYork"))
            add(HomeDataModel("title1", R.drawable.img1_item_home_random,"2022.04.28","Peter","Queens"))
            add(HomeDataModel("title2", R.drawable.img2_item_home_random,"2022.06.03","Tony","NewYork"))

        }

        val homeRVAdapter = HomeRVAdapter(dataList)
        binding.fragmentHomeTitleRandomRv.adapter = homeRVAdapter
        binding.fragmentHomeTitleRandomRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding?.fragmentHomeMainFirstBtn?.setOnClickListener(){
            //nav_graph 에서 프래그먼트 이동할 것을 이어준 후 이렇게 적어주면 프래그먼트간 이동 끝
            it.findNavController().navigate(R.id.action_homeFragment_to_planWriteFragment)

        }
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }


}