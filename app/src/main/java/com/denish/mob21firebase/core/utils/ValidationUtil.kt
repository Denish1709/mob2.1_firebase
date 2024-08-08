package com.denish.mob21firebase.core.utils

import com.denish.mob21firebase.data.model.Field

object ValidationUtil {
    fun validate(vararg fields: Field): String? {
        fields.forEach { field ->
            if(!Regex(field.regExp).matches(field.value)) {
              return field.errMsg
            }
        }
        return null
    }
}