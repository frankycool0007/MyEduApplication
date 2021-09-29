package com.tecxpert.myeduapplication.utill;

import android.content.Context;
import android.content.SharedPreferences;

import com.tecxpert.myeduapplication.model.User;

public class PrefManager {

    private static PrefManager instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private PrefManager(Context context) {
        super();
        this.preferences = context.getSharedPreferences("AAA_PREF", Context.MODE_PRIVATE);
        this.editor = this.preferences.edit();
    }
    public static PrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new PrefManager(context);
        }
        return instance;
    }
    public void setString(String stringkey,String stringvalue){
        instance.editor.putString(stringkey,stringvalue);
        instance.editor.commit();
    }
    public String getString(String stringkey){
      return   instance.preferences.getString(stringkey," ");
    }
    public int getInt( String intkey){
        return instance.preferences.getInt(intkey,0);
    }
    public void setInt(String intkey ,int intvalue){
         instance.editor.putInt(intkey,intvalue);
         instance.editor.commit();
    }
    public boolean getbool( String boolkey){
        return instance.preferences.getBoolean(boolkey,false);
    }
    public void setbool(String boolkey ,boolean boolvalue){
        instance.editor.putBoolean(boolkey,boolvalue);
        instance.editor.commit();
    }
    public void deletepref(){
        instance.editor.clear();
        instance.editor.apply();
    }
    public void setUser(User user){
        instance.editor.putString("user_name",user.getName());
        instance.editor.putString("user_email",user.getEmail());
        instance.editor.putString("user_password",user.getPassword());
        instance.editor.putString("user_board",user.getBoard());
        instance.editor.putString("user_class",user.getClass_name());
        instance.editor.commit();
    }
    public User getuser(){
        if (!instance.getString("user_name").equals(" ")) {
            User user = new User(instance.getString("user_name"),
                    instance.getString("user_email"),
                    instance.getString("user_password"),
                    instance.getString("user_board"),
                    instance.getString("user_class")
            );
            return user;
        }
        else {
            return null;
        }

    }
}
