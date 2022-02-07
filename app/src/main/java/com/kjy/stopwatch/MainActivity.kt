package com.kjy.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.kjy.stopwatch.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {


    // 타이머를 시작할 때 호출할 start() 메서드를 작성

    // 시간을 계산할 변수를 0으로 초기화
    private var time = 0

    // timerTask 변수를 null을 허용하는 Timer 타입으로 선언
    private var timerTask: Timer? = null

    private var isRunning = false



    private var lap = 1                 // 변수 lap을 1로 초기화


    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // onCreate 메서드에 시작과 일시정지 이벤트 구현
        // FAB이 클릭되면 타이머가 동작 중인지를 저장하는 isRunning 변수의 값을 변경 시켜 타이머를 시작 or 일시정지시킴.

        binding.fab.setOnClickListener {

            isRunning = !isRunning

            if (isRunning) {
                start()

            } else {
                pause()
            }

        }

        binding.lapButton.setOnClickListener {
            recordLapTime()                 // 레코드 버튼 이벤트
        }

        binding.resetFab.setOnClickListener {
            reset()                         // 리셋 버튼 이벤트
        }
    }

    private fun pause() {
            // FAB을 클릭하면 FAB의 이미지를 시작 이미지로 교체한다.
            binding.fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            // 타이머를 취소한다.
            timerTask?.cancel()
        }

    private fun start() {
        // 타이머를 시작하는 FAB를 누르면 FAB의 이미지를 일시정지 이미지로 변경.
        binding.fab.setImageResource(R.drawable.ic_baseline_pause_24)
        // 0.01 초마다 다음의 변수를 증가시킴
        timerTask = timer(period = 10) {
            time++
            // 초와 밀리초를 계산하고 runOnUiThread로 감서 UI조작이 가능하게 한다.
            val sec = time / 100            // 나눗셈의 몫
            val milli = time % 100          // 나눗셈의 나머지

            runOnUiThread {
                binding.secTextView.text = "$sec"
                binding.milliTextView.text = "$milli"
            }
        }
    }

    private fun recordLapTime() {

        // 현재 시간을 지역 변수에 저장하고, 동적으로 TextView를 생성하여 텍스트값을 '1 LAP: 0.00' 의 형태로 만듬
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAP : ${lapTime / 100}.${lapTime % 100}"

        // 맨 위에 랩타임 추가
        binding.lapLayout.addView(textView, 0)
        lap++


    }

    private fun reset() {

        timerTask?.cancel()         // 실행 중인 타이머 취소

        // 모든 변수와 화면 표시 초기화

        time = 0
        isRunning = false

        // 리셋 버튼 클릭 시작 이미지를 교체함과 동시에 초와 밀리초를 0으로 초기화

        binding.fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)

        binding.secTextView.text = "0"
        binding.milliTextView.text = "00"


        binding.lapLayout.removeAllViews()
        lap = 1






    }

}