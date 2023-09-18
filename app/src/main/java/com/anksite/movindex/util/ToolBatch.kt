package com.anksite.movindex.util

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import java.text.NumberFormat
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

        fun formatThousand(number: Int?): String {
            return if (number != null) {
                NumberFormat.getInstance().format(number)
            } else {
                ""
            }
        }
    }

    object EspressoIdlingResource {
        private val RESOURCE = "GLOBAL"
        private val countingIdlingResource = CountingIdlingResource(RESOURCE)

        val idlingresource: IdlingResource
            get() = countingIdlingResource

        fun increment() {
            countingIdlingResource.increment()
        }

        fun decrement() {
            countingIdlingResource.decrement()
        }
    }
}
