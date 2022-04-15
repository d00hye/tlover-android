package com.cookandroid.teamproject1

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.Toast

import com.cookandroid.teamproject1.databinding.SignUpingBinding
import java.util.regex.Pattern
import kotlin.concurrent.timer

class SignUpingActivity : AppCompatActivity() {

    lateinit var sbinding: SignUpingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        sbinding = SignUpingBinding.inflate(layoutInflater)
        setContentView(sbinding.root)

        super.onCreate(savedInstanceState)


        sbinding.btnConfirm.setOnClickListener {
            startActivity(Intent(this,CreateAccountActivity::class.java))
        }

        //전화번호 입력확인
        sbinding.signupingPnum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sbinding.btnCtf.isEnabled = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(Pattern.matches("^\\d{11}\$", sbinding.signupingPnum.text.toString())) {
                    sbinding.btnCtf.isEnabled = true
                    sbinding.btnCtf.setBackgroundResource(R.drawable.confirm_btn_background_clicked)
                    sbinding.btnCtf.setTextColor(Color.WHITE)
                }
                else {
                    sbinding.btnCtf.setBackgroundResource(R.drawable.confirm_btn_background)
                    sbinding.btnCtf.setTextColor(Color.parseColor("#ADB5BD"))
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        //확인 버튼 클릭시 새로운 필드와 버튼 출력
        sbinding.btnCtf.setOnClickListener{
            sbinding.btnCtf.visibility=View.INVISIBLE
            sbinding.btnConfirm.visibility= View.VISIBLE
            sbinding.btnTransport.visibility = View.VISIBLE
            sbinding.signTextCtf.visibility = View.VISIBLE
            sbinding.signupingCtfnumber.visibility = View.VISIBLE
            sbinding.textTimer.visibility = View.VISIBLE

            val toastView = layoutInflater.inflate(R.layout.custom_toast,null)

            toastView.run {

            }
            val t2 = Toast(this)
            t2.view = toastView
            t2.show()
            t2.setGravity(Gravity.BOTTOM,0,0)
            t2.duration = Toast.LENGTH_SHORT

        }

        //인증번호 입력 필드(버튼 색 변경 및 경고 메세지 출력)
        sbinding.signTextCtf.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(sbinding.signTextCtf.text.toString() != "") {
                    sbinding.btnTransport.setBackgroundResource(R.drawable.confirm_repetition_btn_background_clicked)
                    sbinding.btnTransport.setTextColor(Color.WHITE)
                    sbinding.btnTransport.isEnabled = true
                }


                if(Pattern.matches("^\\d{4}\$", sbinding.signTextCtf.text.toString())) {
                    sbinding.signUpMsgctf.visibility = View.VISIBLE
                    sbinding.btnConfirm.isEnabled = true
                    sbinding.btnConfirm.setBackgroundResource(R.drawable.confirm_btn_background_clicked)
                    sbinding.btnConfirm.setTextColor(Color.WHITE)
                }
                else {
                    sbinding.btnConfirm.isEnabled = false
                    sbinding.btnConfirm.setBackgroundResource(R.drawable.confirm_btn_background)
                    sbinding.btnConfirm.setTextColor(Color.parseColor("#ADB5BD"))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        //전화번호 텍스트 필드 포커스된 경우
        sbinding.signupingPnum.setOnFocusChangeListener (object : View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (p1) {
                    sbinding.signupingPnum.setBackgroundResource(R.drawable.phone_requ_selected)
                }
                else {
                    sbinding.signupingPnum.setBackgroundResource(R.drawable.phone_requ)
                }
            }

        })

        //인증번호 텍스트 필드 포커스된 경우
        sbinding.signTextCtf.setOnFocusChangeListener (object : View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (p1) {
                    sbinding.signTextCtf.setBackgroundResource(R.drawable.phone_requ_selected)
                }
                else {
                    sbinding.signTextCtf.setBackgroundResource(R.drawable.phone_requ)
                }
            }

        })

        //뒤로가기 버튼
        sbinding.signUpingBackImg.setOnClickListener {
            startActivity(Intent(this,FirstTitleActivity::class.java))
        }

        //재전송 버튼
        sbinding.btnTransport.setOnClickListener {
            val toastView = layoutInflater.inflate(R.layout.custom_toast,null)

            toastView.run {

            }
            val t2 = Toast(this)
            t2.view = toastView
            t2.show()
            t2.setGravity(Gravity.BOTTOM,0,0)
            t2.duration = Toast.LENGTH_SHORT
        }
    }


}
