package com.example.chatapplication

import android.app.Application
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket

class SocketCreate : Application() {

    private var mSocket: Socket? = IO.socket("http://192.168.0.108:4000")

        fun getSocket(): Socket? {
            return mSocket
        }

}