import java.io.File
import java.io.FileOutputStream
import kotlin.math.min

/**
 * @Author: leavesC
 * @Date: 2021/08/21 20:03
 * @Desc:
 * @Github£ºhttps://github.com/leavesC
 */

private const val XML_FILE_NAME = "dimens.xml"
private const val XML_HEADER = """<?xml version="1.0" encoding="utf-8"?>"""
private const val XML_RESOURCE_START = """<resources>"""
private const val XML_SW_DP_TAG = """<string name="sw_dp">%ddp</string>"""
private const val XML_DIMEN_TEMPLATE = """<dimen name="DIMEN_%dPX">%.2fdp</dimen>"""
private const val XML_RESOURCE_END = """</resources>"""
private const val DESIGN_WIDTH_PX = 1080
private const val DESIGN_HEIGHT_PX = 1920

fun main() {
    val designWidthPx = min(DESIGN_WIDTH_PX, DESIGN_HEIGHT_PX)
    val swDimensList = mutableListOf<Int>().apply {
        for (i in 320..460 step 10) {
            add(i)
        }
    }.toList()
    for (value in swDimensList) {
        val file = File("")
        makeAll(designWidthPx, value, file.absolutePath)
    }
}

private fun makeAll(designWidthPx: Int, swDimensDp: Int, buildDir: String) {
    val folderName = "values-sw" + swDimensDp + "dp"
    val file = File(buildDir + File.separator + folderName)
    if (!file.exists()) {
        file.mkdirs()
    }
    val fos = FileOutputStream(file.absolutePath + File.separator + XML_FILE_NAME)
    fos.write(makeDimens(swDimensDp, designWidthPx).toByteArray())
    fos.flush()
    fos.close()
}

private fun makeDimens(swDimensDp: Int, designWidthPx: Int): String {
    val sb = StringBuilder()
    sb.append(XML_HEADER)
    sb.append("\n")
    sb.append(XML_RESOURCE_START)
    sb.append("\n")
    sb.append("    ")
    sb.append(String.format(XML_SW_DP_TAG, swDimensDp))
    sb.append("\n")
    for (i in 1..designWidthPx) {
        val dpValue = i.toFloat() * swDimensDp / designWidthPx
        sb.append("    ")
        sb.append(String.format(XML_DIMEN_TEMPLATE, i, dpValue))
        sb.append("\n")
    }
    sb.append(XML_RESOURCE_END)
    return sb.toString()
}