package com.cookandroid.teamproject1.plan.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.cookandroid.teamproject1.R
import com.cookandroid.teamproject1.databinding.FragmentPlanViewBinding
import com.cookandroid.teamproject1.id.viewmodel.SignUpViewModel.Companion.TAG
import com.cookandroid.teamproject1.plan.model.PlanAcceptDataModel
import com.cookandroid.teamproject1.plan.model.ResponsePlanViewData
import com.cookandroid.teamproject1.plan.model.ResponsePlanWriteData
import com.cookandroid.teamproject1.plan.view.adapter.PlanAcceptRVAdapter
import com.cookandroid.teamproject1.plan.viewmodel.PlanDetailViewModel
import com.cookandroid.teamproject1.util.ServiceCreator
import com.cookandroid.teamproject1.util.TloverApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * plan 상세보기 프래그먼트
 */
class PlanViewFragment : Fragment(){
    private var mBinding : FragmentPlanViewBinding?=null
    private lateinit var planAcceptRVAdapter :PlanAcceptRVAdapter
    private var dataList = mutableListOf<PlanAcceptDataModel>()
    private lateinit var viewModel : PlanDetailViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlanViewBinding.inflate(inflater, container, false)
        mBinding = binding
        dataList.clear()

        activity?.run{
            viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                .get(PlanDetailViewModel::class.java)
        }
        //뒤로가기버튼
        mBinding?.signUpingBackImg?.setOnClickListener{
            it.findNavController().navigate(R.id.action_planViewFragment_to_diaryFragment)
        }

        mBinding?.fragmentPlanDiaryWriteBt?.setOnClickListener {
            it.findNavController().navigate(R.id.action_planViewFragment_to_diaryWritingFragment)
        }

        return mBinding?.root
    }

    override fun onStart() {
        val args : PlanViewFragmentArgs by navArgs()
        val planId = args.planId
//        val planStartDate = args.planStartDate
        viewModel.updatePlanId(planId.toInt())
        Log.d(TAG, "onViewCreated: $planId")

        val call: Call<ResponsePlanViewData> = ServiceCreator.planService.getDiaryPlanView(
            TloverApplication.prefs.getString("jwt", "null"),
            TloverApplication.prefs.getString("refreshToken", "null").toInt(),
            planId.toInt()
//            viewModel.currentPlanId.value
        )

        println(mBinding?.planDetailView?.planStartDate)

        call.enqueue(object: Callback<ResponsePlanViewData> {
            override fun onResponse(
                call: Call<ResponsePlanViewData>,
                response: Response<ResponsePlanViewData>
            ) {
                if(response.code() == 200){
                    Log.e("reponse", "200!!~~~")
                    mBinding?.planDetailView = response.body()?.data

                    viewModel.updatePlanStartDate(response.body()?.data?.planStartDate)

                    for (i in 0 until response.body()?.data?.users?.size!!){
                        dataList.add(PlanAcceptDataModel(response.body()?.data?.users!![i]))
                    }
                    planAcceptRVAdapter.setDataList(dataList)
//                    var planAcceptDataList = mutableListOf(response.body()?.data!!)
//                    planAcceptRVAdapter.planAcceptList = planAcceptDataList

                }
                planAcceptRVAdapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<ResponsePlanViewData>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }
        })

        super.onStart()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**
         * 계획 상세보기 api 연결
         * 작성자 : 윤성식
         */

        val args : PlanViewFragmentArgs by navArgs()
        val planId = args.planId
//        val planStartDate = args.planStartDate
        viewModel.updatePlanId(planId.toInt())
        Log.d(TAG, "onViewCreated: $planId")


        planAcceptRVAdapter = PlanAcceptRVAdapter(requireContext())
        mBinding?.fragmentPlanDiaryWriteFrRv?.layoutManager = GridLayoutManager(requireContext(), 4)
        mBinding?.fragmentPlanDiaryWriteFrRv?.adapter = planAcceptRVAdapter


        //공유할 사람 초대하는 프래그먼트로 이동
        //planId 를 전달해야함. -> 프래그먼트 이동시 다시 x버튼을 클릭 했을 때 이 화면으로 돌아와야하기 떄문에 planId를 계속 전달해야함
        mBinding?.fragmentPlanViewNewFriendLayout?.setOnClickListener{
            val action = PlanViewFragmentDirections.actionPlanViewFragmentToPlanFriendInviteFragment(planId)
            it.findNavController().navigate(action)
        }



        // 다이어리 작성 프래그먼트로 이동
        mBinding?.fragmentPlanDiaryWriteBt?.setOnClickListener(){
            val action = PlanViewFragmentDirections.actionPlanViewFragmentToDiaryWritingFragment(planId)
            it.findNavController().navigate(action)
        }

        /**
         * 0524 계획 삭제 api 연동
         * 작성자 : 윤성식
         */
        mBinding?.fragmentPlanViewDeleteIv?.setOnClickListener {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("삭제하시겠습니까?")
            builder.setMessage("삭제한 여행 계획은 복구할 수 없습니다.")
            builder.setCancelable(false)
            builder.setPositiveButton("삭제", DialogInterface.OnClickListener { dialog, id ->
                val call: Call<ResponsePlanWriteData> = ServiceCreator.planService.deletePlan(
                    TloverApplication.prefs.getString("jwt", "null"),
                    TloverApplication.prefs.getString("refreshToken", "null").toInt(),
                    planId.toInt()
                )

                call.enqueue(object : Callback<ResponsePlanWriteData> {
                    override fun onResponse(
                        call: Call<ResponsePlanWriteData>,
                        response: Response<ResponsePlanWriteData>
                    ) {
                        if (response.code() == 200) {
                            Log.e("reponse", "200!!~~~")
                            Toast.makeText(requireActivity(), "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                            dialog.cancel()
                        } else if (response.code() == 403) {
                            Toast.makeText(
                                requireActivity(),
                                "해당 계획에 삭제 권한이 없는 유저입니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            dialog.cancel()

                        }
                    }

                    override fun onFailure(call: Call<ResponsePlanWriteData>, t: Throwable) {
                        Log.d(TAG, "onFailure: $t")

                    }
                })
                it.findNavController().navigate(R.id.action_planViewFragment_to_diaryFragment)

            })
            builder.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            var alert: AlertDialog = builder.create()
//            alert.setIcon(R.drawable.vector_delete)
//            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#A2A2A8"))
            alert.show()
        }
    }
}