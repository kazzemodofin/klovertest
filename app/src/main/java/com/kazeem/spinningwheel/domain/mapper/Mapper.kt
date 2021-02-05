package com.kazeem.spinningwheel.domain.mapper

import com.kazeem.spinningwheel.data.model.ApiModel

fun ApiModel.mapToDomain() = com.kazeem.spinningwheel.domain.model.ApiModel (
    id = id,
    displayText = displayText,
    value = value,
    currency = currency
)