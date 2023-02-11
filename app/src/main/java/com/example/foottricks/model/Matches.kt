package com.example.foottricks.model

import java.util.Date

data class Matches(
    var opponent: String? =null,
    var championship_day:String?=null,
    var begin_date:Date?=null,
    var end_date:Date?=null,
    var appointment_time_minute:String?=null,
    var appointment_time_hour:String?=null,
    var match_place:String?=null,
    var match_side:String?=null,
    var teamId:String?=null,
    var UUID:String?=null



)
