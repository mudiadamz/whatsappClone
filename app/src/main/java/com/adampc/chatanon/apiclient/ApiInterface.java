package com.adampc.chatanon.apiclient;

import com.adampc.chatanon.ui.models.Chat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vihaan on 21/05/17.
 */

public interface ApiInterface {

    @GET("https://api.myjson.com/bins/1eh55t")
    Call<ArrayList<Chat>> getChats();
}
