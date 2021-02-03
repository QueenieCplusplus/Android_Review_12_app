package com.katepatty.katesmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//import androidx.lifecycle.SavedStateViewModelFactory
// viewModelScope 與 Paging 和 cache 有關
//import androidx.lifecycle.viewModelScope

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider

import kotlin.random.Random.Default.nextInt

import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.lang.Math.random

class MainActivity : AppCompatActivity() {

    private val kvm: KViewModel by lazy {
        ViewModelProvider(this).get(KViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //txt!!.text = arrayOf(kvm.saveState())[0].toString()
        //txt.text = kvm.saveState().toString()
        //update val
        kvm.changeNumber()
        kvm.changeStr()
        //render updated val
        txt.text = kvm.string.toString()
        txt2.text = "今日獲得購物金為台幣："
        txt3.text = kvm.number.toString() + "元"
        Timber.log(1, "msg"+kvm.saveState())

        //var vm = ViewModelProvider(this, SavedStateViewModelFactory(this.application, this)).get(KViewModel::class.java)
    }


    /*override fun onSaveInstanceState(outState: Bundle) {
        //if(::vm.isInitialized)
        vm.saveState()
        super.onSaveInstanceState(outState)
    }*/

    override fun onDestroy() {
        super.onDestroy()
    }

}

class KViewModel(private val state: SavedStateHandle) : ViewModel() {

    ///------------credential data---------------
    // data obj
    //private val menu = listOf("Microsoft", "Apple", "Android", "Google", "Tesla")
    //private val forTech = nextInt(from = 1, until = 3).let{ menu[it]}
    var number: Int = 0
    var string: String? = null
    private val int = (1..3).shuffled().last()
    private val luckyWho = listOf("牡羊寶寶", "金牛寶寶", "雙子寶寶", "巨蟹寶寶", "獅子寶寶", "處女寶寶", " 天秤寶寶", "天蠍寶寶", "射手寶寶", "摩羯寶寶", "水瓶寶寶", "雙魚寶寶" )
    //private val str = ('a'..'b').random()
    //private val int = random(10)

    // getLiveData() observe the changing data val, which is wrapped in so called LiveData obj
    private val liveData = state.getLiveData("liveData", int)


    ///------------open method---------------
    // retrieve and update using state and wrapped in saveState() called method
    fun saveState() {
        state.set("liveData", liveData.value)
    }

    fun changeNumber(){
        number = (100..999).shuffled().last()
    }

    fun changeStr(){

        string = nextInt(from = 0, until = 11).let{ luckyWho[it]}
    }
}

fun random(n: Int) = (Math.random() * n).toInt()