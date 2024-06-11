package kz.assylkhanov.fileupload

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import kz.assylkhanov.fileupload.ui.theme.FIleUploadTheme
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FIleUploadTheme {
                val coroutineScope = rememberCoroutineScope()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Upload",
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                val fileToUpload = File(applicationContext.cacheDir, "tempFile.png")
                                fileToUpload.createNewFile()
                                fileToUpload.outputStream().use {
                                    assets.open("screenshot.png").copyTo(it)
                                }
                                val multipartBody =
                                    MultipartBody.Part.createFormData(
                                        "file",
                                        fileToUpload.name,
                                        fileToUpload.asRequestBody()
                                    )
                                RetrofitApi.service.uploadFile(multipartBody)
                                fileToUpload.delete()
                            }
                        })

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FIleUploadTheme {
        Greeting("Android")
    }
}
