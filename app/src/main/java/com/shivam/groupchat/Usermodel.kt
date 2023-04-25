package com.shivam.groupchat

class Usermodel() {
    lateinit var id:String
    lateinit var name:String
    lateinit var email:String
    lateinit var pass:String

    constructor(   id:String,
                   name:String,
                   email:String,
                   pass:String):this(){
        this.id=id
        this.email=email
        this.name=name
        this.pass=pass
    }
}