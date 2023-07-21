package com.example.a7minuteworkoutapp

class ExerciseModel (
    private var id : Int,
    private var name: String,
    private var image: Int,
    private var isCompleted : Boolean,
    private var isSelected : Boolean
        )
{
    fun getId() : Int {
        return this.id
    }
    fun setId(id : Int) {
        this.id = id
    }
    fun getName() : String {
        return this.name
    }
    fun setName(name : String) {
        this.name = name
    }
    fun getImage() : Int {
        return image
    }
    fun setImage(image : Int) {
        this.image = image
    }
    fun getisCompleted() : Boolean {
        return this.isCompleted
    }
    fun setisCompleted(isCompleted:Boolean) {
        this.isCompleted = isCompleted
    }
    fun getisSelected() : Boolean {
        return this.isSelected
    }
    fun setisSelected(isSelected:Boolean) {
        this.isSelected = isSelected
    }
}