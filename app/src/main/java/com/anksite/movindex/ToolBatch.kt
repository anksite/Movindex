package com.anksite.movindex

import java.text.SimpleDateFormat
import java.util.Locale

class ToolBatch {
    companion object{
        fun formatDate(strDate: String): String {
            val date = SimpleDateFormat("yyy-MM-dd", Locale.US).parse(strDate)
            return SimpleDateFormat("MMM dd, yy", Locale.US).format(date)
        }

        fun formatDateFull(strDate: String): String {
            val date = SimpleDateFormat("yyy-MM-dd", Locale.US).parse(strDate)
            return SimpleDateFormat("MMMM dd, yyy", Locale.US).format(date)
        }
    }
}