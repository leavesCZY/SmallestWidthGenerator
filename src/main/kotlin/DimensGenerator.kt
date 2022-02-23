import java.io.File
import java.io.FileOutputStream
import kotlin.math.min

/**
 * @Author: leavesCZY
 * @Date: 2021/08/24 10:46
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */

private const val XML_FILE_NAME = """dimens.xml"""
private const val XML_HEADER = """<?xml version="1.0" encoding="utf-8"?>"""
private const val XML_RESOURCE_START = """<resources>"""
private const val XML_SW_DP_TAG = """<string name="sw_dp">%ddp</string>"""
private const val XML_DIMEN_TEMPLATE_TO_DP = """<dimen name="DIMEN_%ddp">%.2fdp</dimen>"""
private const val XML_DIMEN_TEMPLATE_TO_PX = """<dimen name="DIMEN_%dpx">%.2fdp</dimen>"""
private const val XML_RESOURCE_END = """</resources>"""

private const val DESIGN_WIDTH_DP = 375
private const val DESIGN_HEIGHT_DP = 667

private const val DESIGN_WIDTH_PX = 1080
private const val DESIGN_HEIGHT_PX = 1920

fun main() {
    val designWidthDp = min(DESIGN_WIDTH_DP, DESIGN_HEIGHT_DP)
    val srcDirFileDp = File("src-dp")
    makeDimens(designWidthDp, srcDirFileDp, XML_DIMEN_TEMPLATE_TO_DP)

    val designWidthPx = min(DESIGN_WIDTH_PX, DESIGN_HEIGHT_PX)
    val srcDirFilePx = File("src-px")
    makeDimens(designWidthPx, srcDirFilePx, XML_DIMEN_TEMPLATE_TO_PX)
}

private fun makeDimens(designWidth: Int, srcDirFile: File, xmlDimenTemplate: String) {
    if (srcDirFile.exists() && !srcDirFile.deleteRecursively()) {
        return
    }
    srcDirFile.mkdirs()
    val smallestWidthList = mutableListOf<Int>().apply {
        for (i in 320..460 step 10) {
            add(i)
        }
    }.toList()
    for (smallestWidth in smallestWidthList) {
        makeDimensFile(designWidth, smallestWidth, xmlDimenTemplate, srcDirFile)
    }
}

private fun makeDimensFile(designWidth: Int, smallestWidth: Int, xmlDimenTemplate: String, srcDirFile: File) {
    val dimensFolderName = "values-sw" + smallestWidth + "dp"
    val dimensFile = File(srcDirFile, dimensFolderName)
    dimensFile.mkdirs()
    val fos = FileOutputStream(dimensFile.absolutePath + File.separator + XML_FILE_NAME)
    fos.write(generateDimens(designWidth, smallestWidth, xmlDimenTemplate).toByteArray())
    fos.flush()
    fos.close()
}

private fun generateDimens(designWidth: Int, smallestWidth: Int, xmlDimenTemplate: String): String {
    val sb = StringBuilder()
    sb.append(XML_HEADER)
    sb.append("\n")
    sb.append(XML_RESOURCE_START)
    sb.append("\n")
    sb.append("    ")
    sb.append(String.format(XML_SW_DP_TAG, smallestWidth))
    sb.append("\n")
    for (i in 1..designWidth) {
        val dpValue = i.toFloat() * smallestWidth / designWidth
        sb.append("    ")
        sb.append(String.format(xmlDimenTemplate, i, dpValue))
        sb.append("\n")
    }
    sb.append(XML_RESOURCE_END)
    return sb.toString()
}