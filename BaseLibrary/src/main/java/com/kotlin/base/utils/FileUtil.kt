package com.kotlin.freak_core.util.file

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object FileUtil {

    /**
     * 从raw包下读取数据
     * @param context
     * @param rawName   R.raw.jx
     * @return
     */
    fun readFileFromRaw(context: Context, rawName: Int): String {
        try {
            val inputReader = InputStreamReader(context.resources.openRawResource(rawName))
            val bufReader = BufferedReader(inputReader)
            var line: String?
            var result = ""

            do {
                line = bufReader.readLine()
                line?.let { result += line }
            } while (line != null)
//            while ((line = bufReader.readLine()) != null)
//                result += line
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * @param activity
     * @param fileName  main.json
     * @return
     */
    fun getFileStremFromAsserts(context: Context, fileName: String): String {

        try {
            //从assets获取json文件
            val isr =
                InputStreamReader(context.assets.open(fileName))
            //字节流转字符流
            val bfr = BufferedReader(isr)
            var line: String?
            val stringBuilder = StringBuilder()
//            while ((line = bfr.readLine()) != null) {
//                stringBuilder.append(line)
//            }//将JSON数据转化为字符串
            do {
                line = bfr.readLine()
                line?.let { stringBuilder.append(line) }
            } while (line != null)
            return stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

}