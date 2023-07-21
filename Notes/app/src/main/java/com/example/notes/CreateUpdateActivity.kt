package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notes.databinding.ActivityCreateUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateUpdateActivity : AppCompatActivity() {
    private lateinit var database:DatabaseReference
    private lateinit var binding : ActivityCreateUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Firebase.database.reference
        if(intent.getStringExtra("MODE").equals("CREATE"))
        //On clicking add button
        {
            binding.addNoteButtton.text = "ADD NOTE"
            title = "add"
            binding.addNoteButtton.setOnClickListener{
                val key = database.child("Notes").push().key
                database.child("Notes").child(key!!).setValue(Data(binding.noteEditText.text.toString(),key)).addOnCompleteListener {
                        task ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Note Added Successfully",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                    else{
                        Toast.makeText(this,task.exception?.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }

            else{
                title = "update"
            binding.addNoteButtton.text = "UPDATE NOTE"
                binding.noteEditText.setText( intent.getStringExtra("DATA"))
                binding.addNoteButtton.setOnClickListener{
                    val database : DatabaseReference = Firebase.database.getReference("Notes").child(intent.getStringExtra("KEY")!!)
                    val data = Data(binding.noteEditText.text.toString(),intent.getStringExtra("KEY")!!)
                    database.setValue(data).addOnCompleteListener {
                            task ->
                        if(task.isSuccessful){
                            Toast.makeText(this,"Note Updated Successfully",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity::class.java))
                        }
                        else{
                            Toast.makeText(this,task.exception?.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }

        }


    }

}