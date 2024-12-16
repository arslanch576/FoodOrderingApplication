package com.coderobust.foodorderingapplication.ui.fooditems

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.coderobust.foodorderingapplication.R
import com.coderobust.foodorderingapplication.databinding.ActivityAddFoodItemsBinding
import com.coderobust.foodorderingapplication.databinding.ActivityMainBinding
import com.coderobust.foodorderingapplication.model.repositories.FoodItemRepository
import com.coderobust.foodorderingapplication.ui.FoodItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class AddFoodItemsActivity : AppCompatActivity() {
    private var uri: Uri? = null
    lateinit var binding: ActivityAddFoodItemsBinding;
    val viewModel: AddFoodItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.isSaved.collect {
                if (it == true) {
                    Toast.makeText(
                        this@AddFoodItemsActivity,
                        "Food item saved successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                if (it != null) {
                    Log.d("testing", "onCreate: $it")
                    Toast.makeText(this@AddFoodItemsActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonSaveFood.setOnClickListener {
            val name = binding.editTextFoodName.text.toString().trim()
            val description = binding.editTextFoodDescription.text.toString().trim()
            val price = binding.editTextFoodPrice.text.toString().trim()
            val category = binding.editTextFoodCategory.text.toString().trim()

            // Validation
            if (name.isEmpty() || description.isEmpty() || price.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val priceValue = price.toInt()

            // Create the food item object (replace with your logic to save the data)
            val foodItem = FoodItem()
            foodItem.name = name
            foodItem.description = description
            foodItem.price = priceValue
            foodItem.category = category

            if (uri == null)
                viewModel.saveFoodItem(foodItem)
            else
                viewModel.uploadImageAndSaveFoodItem(getRealPathFromURI(uri!!)!!, foodItem)

        }

        binding.buttonUploadImage.setOnClickListener {
            chooseImageFromGallery()
        }

    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            uri = result.data?.data
            if (uri != null) {
                binding.imageFoodPreview.setImageURI(uri)
            } else {
                Log.e("Gallery", "No image selected")
            }
        }
    }

    private fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun getRealPathFromURI(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex)
            }
        }
        return null
    }
}