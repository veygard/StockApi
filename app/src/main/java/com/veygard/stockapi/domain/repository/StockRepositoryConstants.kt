package com.veygard.stockapi.domain.repository


object StockRepositoryConstants {
    const val attribute = "goods_attr"
    const val stockIdPattern = "goods_attr id=\"(\\d+)\""
    const val attrIdPattern = "attr_id=\"(\\d+)\""
    const val attrValuePattern = "attr_id=\"\\d+\">(.+)</goods_attr>"
}