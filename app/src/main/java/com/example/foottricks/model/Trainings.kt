package com.example.foottricks.model

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class Trainings(

    var training_name: String? =null,
    var training_description:String?=null,
    var begin_date: Date?=null,
    var end_date: Date?=null,
    var appointment_time_minute:String?=null,
    var appointment_time_hour:String?=null,
    var listOfdays:ArrayList<Int>?=null,
    var recurr_time_begin_minute:String?=null,
    var recurr_time_begin_hour:String?=null,
    var recurr_time_end_minute:String?=null,
    var recurr_time_end_hour:String?=null,
    var match_place:String?=null,
    var teamId:String?=null,
    var UUID:String?=null,
    var pending:HashMap<String,Users>?=null,
    var absent:HashMap<String,Users>?=null,
    var present:HashMap<String,Users>?=null
)
