package com.veygard.stockapi.domain.repository


object ConstantsRepository {
    const val attribute = "goods_attr"
    const val fullItemPattern = "(\\d+);(\\d{13});([^;]+);([^;]+);(\\d+.\\d+);([^;]+);"
    const val stockIdPattern = "goods_attr id=\"(\\d+)\""
    const val attrIdPattern = "attr_id=\"(\\d+)\""
    const val attrValuePattern = "attr_id=\"\\d+\">(.+)</goods_attr>"
}