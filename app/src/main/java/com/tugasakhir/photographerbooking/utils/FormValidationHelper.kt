package com.tugasakhir.photographerbooking.utils

import android.util.Log
import android.util.Patterns
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Helper class to validate the inputs, still find a way to manage multiple input with internal
 * validation so user just passing the input and return the errors message,
 * but still working on it :).
 *
 *
 * Sketch Project Studio
 * Created by Angga on 16/04/2016 13.29.
 */
object FormValidationHelper {
    /**
     * Find out the object is empty.
     *
     * @param value general input
     * @return boolean
     */
    fun isEmpty(value: Any?): Boolean {
        return isEmpty(value, false)
    }

    /**
     * Check if object is empty or not, null, blank, 0, false is empty
     * depends on data type or how the object can be casted.
     *
     * @param value         needs to be checked
     * @param isIgnoreSpace is include space or not
     * @return boolean
     */
    fun isEmpty(value: Any?, isIgnoreSpace: Boolean): Boolean {
        return if (value == null) {
            true
        } else if (value is Boolean) {
            !(value as Boolean?)!!
        } else if (value is String) {
            if (isIgnoreSpace) {
                value.toString().trim { it <= ' ' }.isEmpty()
            } else value.toString().isEmpty()
        } else {
            try {
                val result = value.toString().toInt()
                result == 0
            } catch (e: NumberFormatException) {
                false
            }
        }
    }

    /**
     * Check if email string is valid format.
     *
     * @param email input string
     * @return boolean email format validation
     */
    fun isValidEmail(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Check if string accept as url format, still imperfect but i'm working on it.
     *
     * @param url input string
     * @return boolean url format is valid or not
     */
    fun isValidUrl(url: String): Boolean {
        val urlPattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]".toRegex()
        return url.matches(urlPattern)
    }

    /**
     * Check if string only contains alpha dash numeric underscore and hyphen.
     *
     * @param value input string
     * @return boolean
     */
    fun isAlphaDash(value: String): Boolean {
        return value.matches("^[a-zA-Z0-9-_]*$".toRegex())
    }

    /**
     * Check if string only contain alpha dash.
     *
     * @param value input string
     * @return boolean
     */
    fun isAlphaNumeric(value: String): Boolean {
        return value.matches("^[a-zA-Z0-9]*$".toRegex())
    }

    /**
     * Person name limit character alphabet and some punctuations.
     *
     * @param value input string
     * @return boolean
     */
    fun isPersonName(value: String): Boolean {
        return value.matches("^[a-zA-Z '.,]*$".toRegex())
    }

    /**
     * Check if date string can be parse to date format.
     *
     * @param date input string
     * @return boolean
     */
    fun isValidDate(date: String?): Boolean {
        return try {
            val df: DateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            true
        } catch (e: ParseException) {
            Log.e("Parse Date Exception", e.message.toString())
            false
        }
    }

    /**
     * Check if input string only contain signed numeric.
     *
     * @param value input string
     * @return boolean
     */
    fun isNumeric(value: Any): Boolean {
        return isNumeric(value, false)
    }

    /**
     * Check if input string only contain numeric signed and unsigned number depends on parameter.
     *
     * @param value        input string
     * @param isSignedOnly is allow signed number only or both
     * @return boolean
     */
    fun isNumeric(value: Any, isSignedOnly: Boolean): Boolean {
        return try {
            val result = value.toString().toInt()
            !isSignedOnly || result >= 0
        } catch (e: NumberFormatException) {
            false
        }
    }
    /**
     * Check is input has minimum characters.
     *
     * @param value       input string
     * @param minValue    min length of characters
     * @param ignoreSpace count character without space or empty string
     * @return boolean
     */
    /**
     * Check is input has minimum characters including space.
     *
     * @param value    input string
     * @param minValue min length of characters
     * @return boolean
     */
    @JvmOverloads
    fun minLength(value: String, minValue: Int, ignoreSpace: Boolean = false): Boolean {
        return if (ignoreSpace) {
            value.trim { it <= ' ' }.length >= minValue
        } else value.length >= minValue
    }
    /**
     * Check is input reach maximum characters.
     *
     * @param value       input string
     * @param maxValue    number of maximum characters
     * @param ignoreSpace count characters exclude space or empty string
     * @return boolean
     */
    /**
     * Check is input reach maximum characters.
     *
     * @param value    input string
     * @param maxValue number of maximum characters
     * @return boolean
     */
    @JvmOverloads
    fun maxLength(value: String, maxValue: Int, ignoreSpace: Boolean = false): Boolean {
        return if (ignoreSpace) {
            value.trim { it <= ' ' }.length <= maxValue
        } else value.length <= maxValue
    }
    /**
     * Check character in range minimum and maximum characters with ignoring
     * space and empty string option.
     *
     * @param value         input string
     * @param minValue      number of minimum characters
     * @param maxValue      number of maximum characters
     * @param isIgnoreSpace ignoring space or empty string
     * @return boolean
     */
    /**
     * Check character in range minimum and maximum characters.
     *
     * @param value    input string
     * @param minValue number of minimum characters
     * @param maxValue number of maximum characters
     * @return boolean
     */
    @JvmOverloads
    fun rangeLength(
        value: String,
        minValue: Int,
        maxValue: Int,
        isIgnoreSpace: Boolean = false
    ): Boolean {
        return if (isIgnoreSpace) {
            value.trim { it <= ' ' }.length in minValue..maxValue
        } else value.length in minValue..maxValue
    }

    /**
     * Check minimum integer value.
     *
     * @param value    input string
     * @param minValue number of minimum value
     * @return boolean
     */
    fun minValue(value: Int, minValue: Int): Boolean {
        return value >= minValue
    }

    /**
     * Check minimum float value.
     *
     * @param value    input string
     * @param minValue number of minimum value
     * @return boolean
     */
    fun minValue(value: Float, minValue: Float): Boolean {
        return value >= minValue
    }

    /**
     * Check minimum double value.
     *
     * @param value    input string
     * @param minValue number of minimum value
     * @return boolean
     */
    fun minValue(value: Double, minValue: Double): Boolean {
        return value >= minValue
    }

    /**
     * Check maximum integer value.
     *
     * @param value    input string
     * @param maxValue number of maximum value
     * @return boolean
     */
    fun maxValue(value: Int, maxValue: Int): Boolean {
        return value <= maxValue
    }

    /**
     * Check maximum float value.
     *
     * @param value    input string
     * @param maxValue number of maximum value
     * @return boolean
     */
    fun maxValue(value: Float, maxValue: Float): Boolean {
        return value <= maxValue
    }

    /**
     * Check maximum double value.
     *
     * @param value    input string
     * @param maxValue number of maximum value
     * @return boolean
     */
    fun maxValue(value: Double, maxValue: Double): Boolean {
        return value <= maxValue
    }

    /**
     * Check integer value in range.
     *
     * @param value    input string
     * @param minValue number of minimum value
     * @param maxValue number of maximum value
     * @return boolean
     */
    fun rangeValue(value: Int, minValue: Int, maxValue: Int): Boolean {
        return value in minValue..maxValue
    }

    /**
     * Check float value in range.
     *
     * @param value    input string
     * @param minValue number of minimum value
     * @param maxValue number of maximum value
     * @return boolean
     */
    fun rangeValue(value: Float, minValue: Float, maxValue: Float): Boolean {
        return value in minValue..maxValue
    }

    /**
     * Check double value in range.
     *
     * @param value    input string
     * @param minValue number of minimum value
     * @param maxValue number of maximum value
     * @return boolean
     */
    fun rangeValue(value: Double, minValue: Double, maxValue: Double): Boolean {
        return value >= minValue && value <= maxValue
    }

    /**
     * Check if string is not member of collection data.
     *
     * @param value   input string
     * @param dataSet collection of string data
     * @return boolean
     */
    fun isUnique(value: String, dataSet: Array<String>): Boolean {
        for (data in dataSet) {
            if (data == value) {
                return false
            }
        }
        return true
    }

    /**
     * Check if integer is not member of collection data.
     *
     * @param value   input integer
     * @param dataSet collection of integer data
     * @return boolean
     */
    fun isUnique(value: Int, dataSet: IntArray): Boolean {
        for (data in dataSet) {
            if (data == value) {
                return false
            }
        }
        return true
    }

    /**
     * Check if a string member of data set, ignoring number of data matched.
     *
     * @param value   input string
     * @param dataSet collection of string data
     * @return boolean
     */
    fun isMemberOf(value: String, dataSet: Array<String>): Boolean {
        for (data in dataSet) {
            if (data == value) {
                return true
            }
        }
        return false
    }

    /**
     * Check if a integer member of data set, ignoring number of data matched.
     *
     * @param value   input string
     * @param dataSet collection of integer data
     * @return boolean
     */
    fun isMemberOf(value: Int, dataSet: IntArray): Boolean {
        for (data in dataSet) {
            if (data == value) {
                return true
            }
        }
        return false
    }

    /**
     * Custom rule by passing regular expression.
     *
     * @param value input string
     * @param regex rule
     * @return boolean
     */
    fun isValid(value: String, regex: String): Boolean {
        return value.matches(regex.toRegex())
    }

    /**
     * Build slug from string title like "The beautiful day in 1992" turns "the-beautiful-day-in-1992"
     * or "Super massive black hole O'creaz MO on July" turns "super-massive-black-hole-o-creaz-mo-on-july"
     *
     * @param title article title
     * @return slug string
     */
    fun createSlug(title: String): String {
        val trimmed = title.trim { it <= ' ' }
        val slug = trimmed
            .replace("[^a-zA-Z0-9-]".toRegex(), "-")
            .replace("-+".toRegex(), "-")
            .replace("^-|-$".toRegex(), "")
        return slug.toLowerCase(Locale.ROOT)
    }

    /**
     * Convenience contract template used to validate process
     */
    interface ViewValidation {
        /**
         * Use for populate inputs before validation.
         */
        fun preValidation()

        /**
         * Check input with rules here.
         *
         * @return boolean
         */
        fun onValidateView(): Boolean

        /**
         * Do something after validate the inputs.
         *
         * @param isValid status validation could be achieved by run onValidateView and
         * passing accumulate of success and fail result validator method.
         */
        fun postValidation(isValid: Boolean)
    }
}
