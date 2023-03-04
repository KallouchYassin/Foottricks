package com.example.foottricks.model

data class Users(
    var lastname: String? =null,
    var firstname: String? =null,
    var email:String?=null,
    var phone:String?=null,
    var uuid:String?=null,
    var teamId:String?=null,
    var role:String?=null,
    var imageUri:String?=null,
    var rc:String?="0",
    var yc:String?="0",
    var time:String?="0",
    var note:String?="0",
    var avg:String?="0",
    var goals:String?="0",
    var assist:String?="0",
    var present:Long?=0,
    var convocated:Long?=0,
    var absent:Long?=0,
    var team_name:String?=null,
)
