package com.neznaika_77.food_delivery
// класс для хранения значений элементов item_layout.xml
data class ListItem (
        var image_id: Int,
        var titleText:String,
        var contentText:String,
        var priceText:Int,
        )