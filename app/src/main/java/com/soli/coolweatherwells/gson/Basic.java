package com.soli.coolweatherwells.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {
    // the @SerializedName annotation's definition is in Gson library, please google or chatgpt to learn it.
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
