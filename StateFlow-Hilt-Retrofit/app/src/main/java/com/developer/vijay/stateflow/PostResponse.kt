package com.developer.vijay.stateflow


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostResponse : ArrayList<PostResponse.PostResponseItem>(){
    data class PostResponseItem(
        @Expose
        @SerializedName("body")
        var body: String = "",
        @Expose
        @SerializedName("id")
        var id: Int = 0,
        @Expose
        @SerializedName("title")
        var title: String = "",
        @Expose
        @SerializedName("userId")
        var userId: Int = 0
    )
}