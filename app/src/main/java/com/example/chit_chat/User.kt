package com.example.chit_chat

class User {
    var Email:String?=null
    var Name:String?=null
    var Uid:String?=null
    constructor(){}
    constructor(Name:String?,Email:String?,Uid:String?){
        this.Email=Email
        this.Name=Name
        this.Uid=Uid
    }
}