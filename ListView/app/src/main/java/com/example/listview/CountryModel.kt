package com.example.listview
class CountryModel
{
    var countryName : String = ""
    var cupWins : String = ""
    var flag_img : Int = 0

    constructor(countryName:String,cupWins:String,flag_img:Int)
    {
        this.countryName = countryName
        this.cupWins = cupWins
        this.flag_img = flag_img
    }
}