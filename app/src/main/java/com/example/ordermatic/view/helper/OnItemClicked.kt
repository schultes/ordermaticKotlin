package com.example.ordermatic.view.helper

import com.example.ordermatic.model.Dish

interface OnItemClicked {
    fun onClick(value: Dish)
    fun onRemove(value: Dish)
}