package com.cookandroid.teamproject1.id.view.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cookandroid.teamproject1.*
import com.cookandroid.teamproject1.databinding.SelectThemeBinding
import com.cookandroid.teamproject1.id.model.RequestUserData
import com.cookandroid.teamproject1.id.model.ResponseUserData
import com.cookandroid.teamproject1.id.model.SelectDataModel
import com.cookandroid.teamproject1.id.model.SelectDestData
import com.cookandroid.teamproject1.id.view.adapter.SelectRVAdapter
import com.cookandroid.teamproject1.id.viewmodel.SignUpViewModel
import com.cookandroid.teamproject1.util.ServiceCreator
import com.cookandroid.teamproject1.util.TloverApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectThemeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var selectRVAdapter: SelectRVAdapter
    private var dataList = mutableListOf<SelectDataModel>()
    private var selectdata = mutableListOf<SelectDataModel>()

    private val selectThemeArray : ArrayList<String> = arrayListOf()

    private lateinit var sharedViewModel: SignUpViewModel

    lateinit var binding: SelectThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding = SelectThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.selectThemeGrid
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 3)
        selectRVAdapter = SelectRVAdapter(applicationContext)
        recyclerView.adapter = selectRVAdapter

        dataList.add(SelectDataModel("????????????"))
        dataList.add(SelectDataModel("??????"))
        dataList.add(SelectDataModel("????????????"))
        dataList.add(SelectDataModel("??????"))
        dataList.add(SelectDataModel("????????????"))
        dataList.add(SelectDataModel("????????????"))
        dataList.add(SelectDataModel("????????? ??????"))
        dataList.add(SelectDataModel("????????? ??????"))
        dataList.add(SelectDataModel("????????? ??????"))
        dataList.add(SelectDataModel("?????? ??????"))
        dataList.add(SelectDataModel("?????? ??????"))
        dataList.add(SelectDataModel("?????????"))

        selectRVAdapter.setDataList(dataList)

        binding.selectThemeBtnConfirm.setOnClickListener {

            selectdata = selectRVAdapter.getSelectData()

            if (selectdata.size==0){
                val toastView = layoutInflater.inflate(R.layout.custom_select_toast,null)
                toastView.run {

                }
                val t2 = Toast(this)
                t2.view = toastView
                t2.show()
                t2.setGravity(Gravity.BOTTOM,0,0)
                t2.duration = Toast.LENGTH_SHORT

                return@setOnClickListener
            }

            for (i in 0 until selectdata.size) {
                selectThemeArray.add(selectdata[i].title)
//                Log.i("string", selectdata[i].title)
            }

            val data = intent.getSerializableExtra("selectDestKey") as SelectDestData
//            val selectThemeData = SelectThemeData(
//                data.idText,
//                data.pwText,
//                data.nicknameText,
//                data.pNumText,
//                data.destArray,
//                selectThemeArray
//            )
//            startActivity(Intent(this, SelectThemeActivity::class.java))
            val intent = Intent(this, SignInActivity::class.java)
//            intent.putExtra("selectThemeKey", selectThemeData)

            /**
             * ?????????: ?????????
             * signUpService ???????????? API ??????
             */

            val requestUserData = RequestUserData(
                data.idText,
                data.pwText,
                data.nicknameText,
                data.pNumText,
                data.destArray,
                selectThemeArray
            )

            val call: Call<ResponseUserData> = ServiceCreator.signUpService.postSignUp(requestUserData)

            call.enqueue(object: Callback<ResponseUserData> {
                override fun onResponse(
                    call: Call<ResponseUserData>,
                    response: Response<ResponseUserData>
                ) {
                    if(response.code() == 200){
                        Log.e("signup_server_test", "200")
                        TloverApplication.prefs.setString("message", response.body()?.message.toString())
                        Toast.makeText(this@SelectThemeActivity, "???????????? ?????? ???????????????. ?????????????????????.", Toast.LENGTH_SHORT).show()
                    }
                    else if(response.code() == 409){
                        Toast.makeText(this@SelectThemeActivity, "??????????????? ??????????????????", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Log.e("signup_server_test", "responseFail")
                    }
                }

                override fun onFailure(call: Call<ResponseUserData>, t: Throwable) {
                    Log.e("signup_server_test", "fail")
                }
            })

//            requestUserData.userId = sharedViewModel.getA().toString()

//            println(sharedViewModel.getA())
//            println("gg")

            startActivity(intent)

        }



        fun changeConfirmButton() {
            binding.selectThemeBtnConfirm.setBackgroundResource(R.drawable.confirm_btn_background_clicked)
            binding.selectThemeBtnConfirm.setTextColor(Color.WHITE)
        }

    }

}


