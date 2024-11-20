package com.cliff.conch.scene

import android.content.pm.ApplicationInfo
import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Button
import android.widget.LinearLayout
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivityHiddenApiBinding
import com.cliff.hidden.HiddenApi


class HiddenApiActivity : BaseActivity<ActivityHiddenApiBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addCase("Invoke a restricted method:") {
            HiddenApi.invoke(ApplicationInfo::class.java, ApplicationInfo(), "usesNonSdkApi")
        }
        addCase("Invoke restricted constructor") {
            val instance =
                HiddenApi.newInstance(Class.forName("android.app.IActivityManager\$Default") /*, args*/)
        }
        addCase("Get all methods including restricted ones from a class") {

        }
        addCase("Get all non-static fields including restricted ones from a class") {

        }
        addCase("Get all static fields including restricted ones from a class") {

        }
        addCase("Get specific class method or class constructor") {
            val ctor = HiddenApi.getDeclaredConstructor(ClipDrawable::class.java /*, args */)
            val method = HiddenApi.getDeclaredMethod(
                ApplicationInfo::class.java,
                "getHiddenApiEnforcementPolicy" /*, args */
            )
        }
        addCase("Add a class to exemption list:") {
            HiddenApi.addHiddenApiExemptions(
                "Landroid/content/pm/ApplicationInfo;", // one specific class
                "Ldalvik/system", // all classes in packages dalvik.system
                "Lx" // all classes whose full name is started with x
            );
        }
    }

    override fun initBinding() {
        binding = ActivityHiddenApiBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }

    private fun addCase(title: String, action: () -> Unit) {
        val parameter = LinearLayout.LayoutParams(MATCH_PARENT, 300)
        val button = Button(this@HiddenApiActivity)
        button.text = title
        button.setTextColor(resources.getColor(android.R.color.black, null))
        button.gravity = Gravity.CENTER
        button.setOnClickListener { action() }
        binding.caseContainer.addView(button, parameter)
        val container = binding.caseContainer.layoutParams
        container.height += 300
        binding.caseContainer.layoutParams = container
    }
}