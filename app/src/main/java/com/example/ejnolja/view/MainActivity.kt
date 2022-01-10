package com.example.ejnolja.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejnolja.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 하단 탭이 눌렸을 때 화면을 전환하기 위해선 이벤트 처리하기 위해 BottomNavigationView 객체 생성
        var bnv_main = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // OnNavigationItemSelectedListener를 통해 탭 아이템 선택 시 이벤트를 처리
        bnv_main.run { setOnItemSelectedListener {
            when(it.itemId) {
                R.id.tab1 -> {
                    // 다른 프래그먼트 화면으로 이동하는 기능
                    val restFragment = MainRestFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, restFragment).commit()
                }
                R.id.tab2 -> {
                    val searchFragment = MainSearchFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, searchFragment).commit()
                }
                R.id.tab3 -> {
                    val reservationFragment = MainReservationFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, reservationFragment).commit()
                }
            }
            true
        }
            selectedItemId = R.id.tab1
        }
    }
}