package org.example.modell;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameModellTest {

    private GameModell modell;

    @BeforeEach
    public void init(){
        try {
            modell = new GameModell("username","könnyű");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void BombGenerateTest(){
        try {
            modell = new GameModell("username","könnyű");
        } catch (IOException e) {
            e.printStackTrace();
        }
        modell.BombGenerate("könnyű");
        int x =modell.bombs[1].length;
        assertEquals(11,x);
        int bombs=0;
        for(int i = 0 ;i <=10;i++)
            for(int j=0; j<=10;j++)
                if(modell.getBombs(i,j) && modell.getRevealed1(i,j)){
                    bombs++;
                }
        assertEquals(10,bombs);
 }

    @Test
    public void set_to_zeroTest(){
     try {
         modell = new GameModell("username","könnyű");
     } catch (IOException e) {
         e.printStackTrace();
     }
     int allfalse=0;
     modell.set_to_zero();
     for (int row = 1; row <= 10; row++) {
         {for (int collom = 1; collom <= 10; collom++)
             if (!modell.getBombs(row, collom) && !modell.getFlagged(row, collom) && !modell.getRevealed1(row, collom) && !modell.getRevealed(row, collom))
             {
                 allfalse++;}
         }
     }
             assertEquals(100,allfalse);

 }
        @Test
    public void inboxXTest(){
            try {
                modell = new GameModell("username","könnyű");
            } catch (IOException e) {
                e.printStackTrace();
            }
           int p=  modell.inboxX(0,0);
            assertEquals(-1,p);
            int q = modell.inboxX(21,69);
            assertEquals(1,q);
        }
        @Test
    public void inboxYTest(){
            try {
                modell = new GameModell("username","könnyű");
            } catch (IOException e) {
                e.printStackTrace();
            }
            int p=  modell.inboxX(0,0);
            int q = modell.inboxX(21,69);
            assertEquals(-1,p);
            assertEquals(1,q);
        }
}