package org.example.modell;


import org.tinylog.Logger;

public class SecondaryModell {

   private String username;


    public void setUsername(String username) {
        this.username = username;
        Logger.info(username+" felhasználónév beállítva");

    }

   public SecondaryModell(){

   }
}
