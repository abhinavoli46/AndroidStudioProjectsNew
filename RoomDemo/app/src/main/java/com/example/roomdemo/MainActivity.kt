package com.example.roomdemo

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.databinding.DialogupdateBinding

import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Now we can interact with database with the help of Dao instance
        val employeeDao = (application as EmployeeApp).db.employeeDao()

        //Button for adding record
        binding?.addButton?.setOnClickListener {
            addRecord(employeeDao)
        }
        //Coroutine to fetch data and converting into Arraylist which can be passed to adapter to inflate recycler view.
        //This thread runs in background.
        lifecycleScope.launch {
            employeeDao.fetchAllEmployees().collect{
                //Convert the list into arraylist
                val list = ArrayList(it)
                setUpDataInRecyclerView(list,employeeDao)
            }
        }
    }

    private fun addRecord(employeeDao:EmployeeDao){
        //Fetch name and email from the activity
        val name = binding?.nameedittext?.text.toString()
        val email = binding?.emailedittext?.text.toString()

        if(email.isNotEmpty() && name.isNotEmpty()){
            lifecycleScope.launch {
                //insert using the upsert function declared in Dao Interface
                employeeDao.upsert(EmployeeEntity(name = name,email = email))
                Toast.makeText(applicationContext,"Record Added",Toast.LENGTH_SHORT).show()
                //After record is added clear the text from edittext
                binding?.nameedittext?.text?.clear()
                binding?.emailedittext?.text?.clear()
            }
        }
        else{
            Toast.makeText(applicationContext,"Entries cannot be blank",Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpDataInRecyclerView(employeesList:ArrayList<EmployeeEntity>,employeeDao: EmployeeDao){
        if(employeesList.isNotEmpty()){
            //Taking an instance of Adapter defined for the recycler view.
            val myAdapter = MyEmployeeRecyclerAdapter(employeesList,
                {
                updateId->
                updateRecordDialog(updateId,employeeDao)
                },
                {
                    deleteId ->
                    deleteRecordDialog(deleteId,employeeDao)
                }
            )
            binding?.recyclerView?.layoutManager = LinearLayoutManager(this)
            binding?.recyclerView?.adapter = myAdapter
            binding?.recyclerView?.visibility = View.VISIBLE
        }
        else {
            binding?.recyclerView?.visibility = View.GONE
        }
    }

    //Functionality of Update Button
    private fun updateRecordDialog(id:Int,employeeDao: EmployeeDao){
        //Create a dialog and set its Content defined in xml file
        val updateDialog = Dialog(this,R.style.update_dialog)
        updateDialog.setCancelable(false)
        val binding : DialogupdateBinding = DialogupdateBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)
        lifecycleScope.launch {

            employeeDao.fetchEmployeeById(id).collect{
                if(it!=null) {
                    binding.nameeditdialog.setText(it.name)
                    binding.emaileditdialog.setText(it.email)
                }
            }
        }
        //UpdateButtonInsideDialog
        binding.updatedialogbutton.setOnClickListener{
            val name = binding.nameeditdialog.text.toString()
            val email = binding.emaileditdialog.text.toString()

            if(name.isNotEmpty() && email.isNotEmpty()){
                lifecycleScope.launch {
                    employeeDao.upsert(EmployeeEntity(id = id,name = name,email = email))
                    Toast.makeText(applicationContext,"Record Updated",Toast.LENGTH_LONG).show()
                    updateDialog.dismiss()
                }
            }
            else{
                Toast.makeText(applicationContext,"Entries cannot be blank",Toast.LENGTH_LONG).show()
            }
        }
        //CancelButtonInsideDialog
        binding.cancelbuttoneditdialog.setOnClickListener{
            updateDialog.dismiss()
        }
        updateDialog.show()

    }
    //Functionality of Delete Button
    private fun deleteRecordDialog(id:Int,employeeDao: EmployeeDao){
        //Creating builder for alert dialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Record?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        //"If user clicks yes"
        builder.setPositiveButton("Yes") {dialogInterface,_ ->
            lifecycleScope.launch {
                employeeDao.delete(EmployeeEntity(id))
                Toast.makeText(applicationContext,"Record Deleted Successfully",Toast.LENGTH_LONG).show()
            }
            dialogInterface.dismiss()
        }
        //"If user clicks no"
        builder.setNegativeButton("No"){dialogInterface,_->
            dialogInterface.dismiss()
        }
        //Creating Builder for Alert Dialog(end)

        //Creating Alert dialog from builder
        val alertDialog:AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}