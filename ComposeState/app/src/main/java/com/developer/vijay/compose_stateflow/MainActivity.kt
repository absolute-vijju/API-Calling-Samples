package com.developer.vijay.compose_stateflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developer.vijay.compose_stateflow.ui.theme.ComposeStateFlowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStateFlowTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CallAPI()
                }
            }
        }
    }

    @Composable
    fun EachRow(post: PostResponse.PostResponseItem) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                Text(text = post.title, fontSize = 30.sp)
                Text(text = post.body, fontSize = 15.sp)
            }
        }
    }

    @Composable
    fun CallAPI() {
        when (val response = viewModel.apiResponse.value) {
            is Resource.Error -> {
                Text(text = response.message!!, fontSize = 40.sp)
            }
            is Resource.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                val postResponse = response.data as PostResponse
                Column {
                    Text(text = "Post List", fontSize = 40.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn {
                        items(postResponse.size) {
                            EachRow(post = postResponse[it])
                        }
                    }
                }
            }
        }
    }
}