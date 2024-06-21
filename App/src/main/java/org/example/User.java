package org.example;
public class User {
    // Static variable to store the user ID
    private static String user_id;

    //getter
    public static String getUserId(){
        return user_id;
    }

    //setter
    public static void setUserId(String id){
        user_id = id;
    }


}

