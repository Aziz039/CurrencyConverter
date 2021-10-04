package com.example.jsonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Response
import java.lang.Exception
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    // declare variables
    private lateinit var clMain: ConstraintLayout
    private lateinit var tv_date: TextView
    private lateinit var ti_value: TextInputEditText
    private lateinit var spinner_countries: Spinner
    private lateinit var bt_convert: Button
    private lateinit var tv_result: TextView
    private var date: String = ""
    private var usd: Float = 0.0f
    private var jpy: Float = 0.0f
    private var sar: Float = 0.0f
    private var egp: Float = 0.0f
    private var aud: Float = 0.0f
    private var cad: Float = 0.0f

    private val curr = arrayOf(
        "Saudi riyal (sar)",
        "United States Dollar (usd)",
        "Japanese Yen (jpy)",
        "Egyptian Pound (egp)",
        "Australian Dollar (aud)",
        "Canadian Dollar (cad)")

    private var selectedCur: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init variables
        initVars()

        // API handler
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: retrofit2.Call<CurrencyDetails?>? = apiInterface!!.doGetListResources()
        call?.enqueue(object : retrofit2.Callback<CurrencyDetails?> {
            override fun onResponse(
                call: retrofit2.Call<CurrencyDetails?>?,
                response: Response<CurrencyDetails?>
            ) {
                val x: CurrencyDetails? = response.body()
                try {
                    date = x?.date.toString()
                    usd = x?.eur?.usd!!.toFloat()
                    jpy = x?.eur?.jpy!!.toFloat()
                    sar = x?.eur?.sar!!.toFloat()
                    egp = x?.eur?.egp!!.toFloat()
                    aud = x?.eur?.aud!!.toFloat()
                    cad = x?.eur?.cad!!.toFloat()
                    selectedCur = sar
                } catch (e: Exception) {
                    Log.d("MainActivityAPI", "Caught an error in API ")
                }

            }
            override fun onFailure(call: retrofit2.Call<CurrencyDetails?>, t: Throwable) {
                Log.d("MainActivityAPI", "API failed!")
                call.cancel()
            }
        })

        // handle spinner
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, curr
        )
        spinner_countries.adapter = adapter

        spinner_countries.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                when (position) {
                    0 -> selectedCur = sar
                    1 -> selectedCur = usd
                    2 -> selectedCur = jpy
                    3 -> selectedCur = egp
                    4 -> selectedCur = aud
                    5 -> selectedCur = cad
                    else -> selectedCur = sar
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedCur = sar
            }
        }

        // onClickListeners
        bt_convert.setOnClickListener { handle_converter() }
    }

    // init variables
    fun initVars() {
        clMain = findViewById(R.id.clMain)
        tv_date = findViewById(R.id.tv_date)
        ti_value = findViewById(R.id.ti_value)
        spinner_countries = findViewById(R.id.spinner_countries)
        bt_convert = findViewById(R.id.bt_convert)
        tv_result = findViewById(R.id.tv_result)
    }

    // button handler
    fun handle_converter() {
        if (ti_value.text.isNullOrBlank()) {
            Snackbar.make(clMain, "Please insert a value..", Snackbar.LENGTH_SHORT).show()
        } else {
            tv_date.text = "Date: $date"
            tv_result.text = "Results: ${ti_value.text.toString().toFloat() * selectedCur}"
        }
    }

}