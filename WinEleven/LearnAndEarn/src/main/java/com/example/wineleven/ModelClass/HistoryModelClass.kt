package com.example.wineleven.ModelClass

class HistoryModelClass{
    var TimeAndDate : String = ""
    var coin : String = ""
    var isDebit:Boolean = false
    constructor()
    constructor(TimeAndDate: String, coin: String, isDebit: Boolean) {
        this.TimeAndDate = TimeAndDate
        this.coin = coin
        this.isDebit = isDebit
    }


}

