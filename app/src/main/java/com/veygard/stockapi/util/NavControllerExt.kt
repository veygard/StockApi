package com.veygard.stockapi.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections

/*
* Не даёт делать повторную навигацию, если она уже в процессе.
* Решает:
* Navigation action/destination cannot be found from the current destination Destination
*/
fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run {
        navigate(direction)
    }
}