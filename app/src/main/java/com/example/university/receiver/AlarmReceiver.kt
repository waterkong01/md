package com.example.university.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.university.Constant.CHANNEL_ID
import com.example.university.Constant.NOTIFICATION_ID
import com.example.university.MainActivity
import com.example.university.R
import com.example.university.database.CalendarDataEntity

class AlarmReceiver : BroadcastReceiver() {

    var notificationManager: NotificationManager? = null

    override fun onReceive(context: Context, intent: Intent) {
        val data: CalendarDataEntity = intent.getSerializableExtra("calendarDataEntity") as CalendarDataEntity
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 알림 채널 생성
        createNotificationChannel()

        // 알림 표시
        deliverNotification(context, data)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,  // 채널의 아이디
                "채널 이름입니다.",  // 채널의 이름
                NotificationManager.IMPORTANCE_HIGH /*
                1. IMPORTANCE_HIGH = 알림음이 울리고 헤드업 알림으로 표시
                2. IMPORTANCE_DEFAULT = 알림음 울림
                3. IMPORTANCE_LOW = 알림음 없음
                4. IMPORTANCE_MIN = 알림음 없고 상태줄 표시 X
                 */
            )
            notificationChannel.enableLights(true) // 불빛
            notificationChannel.lightColor = Color.RED // 색상
            notificationChannel.enableVibration(true) // 진동 여부
            notificationChannel.description = "채널의 상세정보입니다." // 채널 정보
            notificationManager!!.createNotificationChannel(notificationChannel)
        }
    }

    private fun deliverNotification(context: Context, data: CalendarDataEntity) {
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,  // requestCode
            contentIntent,  // 알림 클릭 시 이동할 인텐트
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE /*
            1. FLAG_UPDATE_CURRENT : 현재 PendingIntent를 유지하고, 대신 인텐트의 extra data는 새로 전달된 Intent로 교체
            2. FLAG_CANCEL_CURRENT : 현재 인텐트가 이미 등록되어있다면 삭제, 다시 등록
            3. FLAG_NO_CREATE : 이미 등록된 인텐트가 있다면, null
            4. FLAG_ONE_SHOT : 한번 사용되면, 그 다음에 다시 사용하지 않음
            */
        )
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background) // 아이콘
            .setContentText(data.memo) // 메모 내용
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        notificationManager!!.notify(NOTIFICATION_ID, builder.build())
    }
}