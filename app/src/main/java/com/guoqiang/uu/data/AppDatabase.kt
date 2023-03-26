package com.guoqiang.uu.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date

/**
 * author: zgq
 * date: 2023/3/25 15:47
 * destcription:
 */
@Database(
    entities = [User::class, Message::class, MessageSession::class, UserMessageSessionRef::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun messageSessionDao(): MessageSessionDao
    abstract fun userMessageSessionRefDao(): UserMessageSessionRefDao

    companion object {
        private const val DATABASE_NAME = "robot-db"

        @Volatile
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        GlobalScope.launch {
                            getInstance(context).initUserData(context)
                            getInstance(context).initAIMajordomoMessage(context)
                            Log.d("zgq", "数据库初始化数据成功")
                        }

                    }
                })
                .build()
        }

        const val ROBOT_UID = "robot"
        const val VISITOR_UID = "visitor"
    }


    private fun initUserData(context: Context) {
        val date = Date()
        val robot = User(
            ROBOT_UID, "robot", "robot",
            null, null, null, null, null, date, date
        )
        val visitor = User(
            VISITOR_UID, "visitor", "visitor",
            null, null, null, null, null, date, date
        )
        getInstance(context).userDao().insertUsers(arrayListOf(robot, visitor))
    }

    private fun initAIMajordomoMessage(context: Context) {
        val sessionId = "AIMajordomo"

        //初始化消息会话
        val messageSessionDate = Date()
        val messageSession = MessageSession(
            sessionId,
            messageSessionDate,
            messageSessionDate, 1
        )
        getInstance(context).messageSessionDao().insertMessageSession(messageSession)

        //用户信息
        val userDate = Date()
        val userMessage = Message(
            "visitor", "过年家里催婚怎么办？", 2, userDate, userDate, VISITOR_UID, sessionId
        )

        //机器人信息
        val robotDate = Date()
        val robotMessage = Message(
            "robot",
            "1．进行沟通：与家人沟通是很重要的。告诉他们你对结婚和家庭生活的想法，并与他们讨论你的实际情况。\n" +
                    "2．站稳自己的立场：不要因为家人的压力而做出任何你不想做的事。要坚守你的原则和信仰，保持自己的独立性和自主性。\n" +
                    "3．寻找支持：在面对催婚的压力时，可以寻找朋友和亲密的人的支持和鼓励。\n" +
                    "4．保持冷静：在处理这种情况时，一定要保持冷静，避免因情绪失控而造成不必要的冲突。\n" +
                    "5．考虑外界因素：要考虑外界因素，例如职业、经济等，在这些因素的影响下决定是否结婚。\n",
            1,
            robotDate,
            robotDate,
            ROBOT_UID,
            sessionId
        )

        getInstance(context).messageDao().insertAll(arrayListOf(userMessage, robotMessage))


        //初始化用户会话关联表
        val visitorUserMessageSessionRefDate = Date()
        val visitorUserMessageSessionRef = UserMessageSessionRef(
            VISITOR_UID,
            sessionId,
            visitorUserMessageSessionRefDate,
            visitorUserMessageSessionRefDate
        )

        val robotUserMessageSessionRefDate = Date()
        val robotUserMessageSessionRef = UserMessageSessionRef(
            ROBOT_UID,
            sessionId,
            robotUserMessageSessionRefDate,
            robotUserMessageSessionRefDate
        )

        getInstance(context).userMessageSessionRefDao().insert(visitorUserMessageSessionRef)
        getInstance(context).userMessageSessionRefDao().insert(robotUserMessageSessionRef)

    }
}
