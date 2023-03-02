package com.example.foottricks.model

data class Users(
    var lastname: String? =null,
    var firstname: String? =null,
    var email:String?=null,
    var phone:String?=null,
    var uuid:String?=null,
    var teamId:String?=null,
    var role:String?=null,
    var team_name:String?=null,
    var imageUri:String?=null,
    var rc:String?=null,
    var yc:String?=null,
    var time:String?=null,
    var note:String?=null,
    var avg:String?=null,
    var goals:String?=null,
    var assist:String?=null,
    var present:Long?=0,
    var convocated:Long?=0,
    var absent:Long?=0
)
