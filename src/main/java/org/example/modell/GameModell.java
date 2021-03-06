package org.example.modell;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.example.Rekords;
import org.tinylog.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
public class GameModell {

    public String nehezseg;
    private int Score = 0;
    private int counter;
    public int spacing;
    public Rekords rekordok = new Rekords() ;
    String filename = "Rekordok.json";
    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public String username;
    public boolean[][] bombs;
    private int[][] neighbours;
    private  boolean[][] flagged;
    private boolean[][] revealed;
    private Random rand = new Random();
    public int timer = 1;
    public int timercounter = 0;
    public int stop = 0;
    public int revealedcounter = 1;
    public boolean[][] revealed1;
    public Gson gson = new Gson();
    FileOutputStream outputStream;

    /**
     * Ez a metódus meghatározza azt, hogy egy adott mező mellett hány akna van.
     *
     */

    public void howmuchneighbour() {
        Logger.info("Mezők aknásmezőkkel való szomszédainak meghatározása!");
        for (int i = 1; i <= 10; i++)
            for (int j = 1; j <= 10; j++) {
                if (bombs[j][i]) {
                    neighbours[i - 1][j - 1]++;
                    neighbours[i - 1][j]++;
                    neighbours[i - 1][j + 1]++;
                    neighbours[i][j - 1]++;
                    neighbours[i][j + 1]++;
                    neighbours[i + 1][j - 1]++;
                    neighbours[i + 1][j]++;
                    neighbours[i + 1][j + 1]++;
                }

            }
    }

    /**
     * Ez a metódus meghatározza, hogy a klikkelt mező mellett hány akna van.
     *
     * @param x a mező sorszáma
     * @param y a mező oszlopszáma
     * @return Integer, ami az aknák száma
     */
    public int neighbournumber(int x, int y) {
        int i = 0;
        if (x == 1 && y == 10) {
            if (bombs[x][y - 1])
                i++;
            if (bombs[x + 1][y - 1])
                i++;
            if (bombs[x + 1][y])
                i++;
        } else if (x == 10 && y == 1) {
            if (bombs[x][y + 1])
                i++;
            if (bombs[x - 1][y + 1])
                i++;
            if (bombs[x - 1][y])
                i++;
        } else if (x == 1 && y == 1) {
            if (bombs[x][y + 1])
                i++;
            if (bombs[x + 1][y + 1])
                i++;
            if (bombs[x + 1][y])
                i++;
        } else if (x == 10 && y == 10) {
            if (bombs[x][y - 1])
                i++;
            if (bombs[x - 1][y - 1])
                i++;
            if (bombs[x - 1][y])
                i++;
        } else if (x == 10 && y != 1) {

            if (bombs[x][y - 1])
                i++;
            if (bombs[x][y + 1])
                i++;
            if (bombs[x - 1][y - 1])
                i++;
            if (bombs[x - 1][y + 1])
                i++;
            if (bombs[x - 1][y])
                i++;
        } else if (x != 1 && y == 10) {

            if (bombs[x][y - 1])
                i++;
            if (bombs[x + 1][y - 1])
                i++;
            if (bombs[x - 1][y - 1])
                i++;
            if (bombs[x - 1][y])
                i++;
            if (bombs[x + 1][y])
                i++;
        } else if (x == 1 && y != 10) {
            if (bombs[x][y - 1])
                i++;
            if (bombs[x][y + 1])
                i++;
            if (bombs[x + 1][y - 1])
                i++;
            if (bombs[x + 1][y])
                i++;
            if (bombs[x + 1][y + 1])
                i++;
        } else if (y == 1 && x != 10) {
            if (bombs[x + 1][y])
                i++;
            if (bombs[x - 1][y])
                i++;
            if (bombs[x + 1][y + 1])
                i++;
            if (bombs[x - 1][y + 1])
                i++;
            if (bombs[x][y + 1])
                i++;
        } else {
            if (bombs[x - 1][y - 1])
                i++;
            if (bombs[x - 1][y])
                i++;
            if (bombs[x - 1][y + 1])
                i++;
            if (bombs[x][y - 1])
                i++;
            if (bombs[x][y + 1])
                i++;
            if (bombs[x + 1][y - 1])
                i++;
            if (bombs[x + 1][y])
                i++;
            if (bombs[x + 1][y + 1])
                i++;
        }
        return i;
    }


    /**
     * Ez a metódus mindent visszaállít kezdeti állapotba.
     */
    public void set_to_zero() {
        for (int row = 1; row <= 10; row++)
            for (int collom = 1; collom <= 10; collom++) {
                bombs[row][collom] = false;
                flagged[row][collom] = false;
                neighbours[row][collom] = 0;
                revealed[row][collom] = false;
                revealed1[row][collom] = false;

            }
        Logger.info("Újra kezdés!");
    }

    /**
     * Ez a metódus legenerálja az aknák helyét véletlenszerűen a nehézség függvényébe.
     * Ha a nehézség {@code könnyű} akkor 10 aknák generál ha {@code közepes} akkor 40
     * aknát ha {@code nehéz} akkor 99 aknát
     *
     * @param nehezseg a játék nehézsége.
     */
    public void BombGenerate(String nehezseg) {
        Logger.info("Aknák elhelyezése a pályán!");
        if (nehezseg.equals("könnyű")) {

            bombs = new boolean[11][11];
            neighbours = new int[15][15];
            flagged = new boolean[11][11];
            revealed = new boolean[11][11];
            revealed1 = new boolean[11][11];
            int i = 1;
            int q = 0;
            for (int row = 1; row <= 10; row++) {
                for (int collom = 1; collom <= 10; collom++) {
                    bombs[row][collom] = false;
                    flagged[row][collom] = false;
                    neighbours[row][collom] = 0;
                    revealed[row][collom] = false;
                    revealed1[row][collom] = false;

                }

            }


            while (i <= 10) {
                int random1 = rand.nextInt((10 - 1) + 1) + 1, random2 = rand.nextInt((10 - 1) + 1) + 1;
                if (random1 >= 1 && random1 <= 10 && random2 >= 1 && random2 <= 10) {

                    if (bombs[random1][random2] != true) {
                        bombs[random1][random2] = true;
                        revealed1[random1][random2] = true;
                        i++;
                    }
                }
            }
        }
        if (nehezseg.equals("közepes")) {
            bombs = new boolean[17][17];
            neighbours = new int[17][17];
            flagged = new boolean[17][17];
            int i = 1;
            for (int row = 1; row <= 16; row++)
                for (int collom = 1; collom <= 16; collom++)
                    bombs[row][collom] = false;
            while (i <= 40) {
                int random1 = rand.nextInt((16 - 1) + 1) + 1, random2 = rand.nextInt((16 - 1) + 1) + 1;
                if (random1 >= 1 && random1 <= 16 && random2 >= 1 && random2 <= 16) {
                    if (bombs[random1][random2] != true) {
                        bombs[random1][random2] = true;
                        i++;
                    }
                }
            }
        }
        if (nehezseg.equals("nehéz")) {
            bombs = new boolean[31][17];
            neighbours = new int[31][17];
            flagged = new boolean[31][17];
            int i = 1;
            for (int row = 1; row <= 30; row++)
                for (int collom = 1; collom <= 16; collom++)
                    bombs[row][collom] = false;
            while (i <= 99) {
                int random1 = rand.nextInt((30 - 1) + 1) + 1, random2 = rand.nextInt((16 - 1) + 1) + 1;
                if (random1 >= 1 && random1 <= 30 && random2 >= 1 && random2 <= 16) {
                    if (bombs[random1][random2] != true) {
                        bombs[random1][random2] = true;
                        i++;
                    }
                }
            }
        }
    }



    /**
     * Ez a konstruktor beállítja a felhasználónevet és a nehézséget.
     * Legenerálja az aknákat és a mezőkhöz meghatározza hány aknak van mellettük
     *
     * @param username a játékos felhasználóneve
     * @param nehezseg a játék nehézségi szintje
     * @throws IOException ide pl azt se tudom mit kéne írni
     */
    public GameModell(String username, String nehezseg) throws IOException {

        this.username = username;
        this.nehezseg = nehezseg;

        BombGenerate(nehezseg);
        howmuchneighbour();
        counter++;


    }

    /**
     * Ez a metódus azt nézi, hogy ahova klikkeltünk az melyik négyzetbe van ha benne van.
     * @param mx x koordináta.
     * @param my y koordináta.
     * @return visszaadja melyik sorba klikkeltünk.
     */
    public  int inboxX(int mx, int my) {
        if (nehezseg.equals("könnyű")) {


            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    int p = i * 50 + 50 - spacing;
                    int k = j * 50 - spacing + 100;
                    int c = spacing + j * 50 + 50;
                    if (mx >= spacing + i * 50 && mx < p && my >= c && my < k)
                        return i + 1;
                }
        } else if (nehezseg.equals("közepes")) {
            Logger.info("Közepes szint");

        } else if (nehezseg.equals("közepes")) {
            Logger.info("Közepes szint");

        }
        return -1;
    }

    /**
     * Ez a metódus azt nézi meg, hogy ahova klikkeltünk az benne van-e egy négyzetbe az y tengelyen.
     * @param mx x koordináta.
     * @param my y koordináta.
     * @return visszaadja azt h melyik oszlop az.
     */
    public  int inboxY(int mx, int my) {
        if (nehezseg.equals("könnyű")) {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    int p = i * 50 + 50 - spacing;
                    int k = j * 50 - spacing + 100;
                    int c = spacing + j * 50 + 50;
                    if (mx >= spacing + i * 50 && mx < p && my >= c && my < k)
                        return j + 1;
                }
        } else if (nehezseg.equals("közepes")) {

        } else if (nehezseg.equals("közepes")) {

        }
        return -1;
    }

    public boolean getFlagged(int x, int y) {
        return flagged[x][y];
    }

    public boolean getRevealed(int x, int y) {
        return revealed[x][y];
    }

    public void setFlagged(int x, int y, boolean value) {
        flagged[x][y] = value;
    }

    public int getTimecounter() {
        return timercounter;
    }

    public void setTimecounter(int i) {
        timercounter = i;
    }

    public boolean getBombs(int inboxX, int inboxY) {
        return bombs[inboxX][inboxY];
    }

    public int getScore() {
        return Score;
    }

    public  boolean getRevealed1(int i, int j) {
        return revealed1[i][j];
    }

    public  void setRevealedcounter(int i) {
        revealedcounter = i;
    }

    public  int getREvealedcounter() {
        return revealedcounter;
    }

    public  void updateRevealedcounter(int i) {
        revealedcounter += i;
    }

    public  void setRevealedvalue(int x, int y, boolean value) {
        revealed[x][y] = value;
    }

    public  void setRevealed1value(int x, int y, boolean value) {
        revealed1[x][y] = value;
    }

    public void updateScorevalue(int i) {
        Score += i;
    }

    public String getnehezseg() {
        return nehezseg;
    }

    public void setScore(int i) {
        Score=i;
    }

    public  void updateStop() {
        stop++;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int i) {
        stop=i;
    }

    public  int getCounter() {
        return counter;
    }

    /**
     * Ez a metódus a rekordok objektum time,score és username listájához adja a paraméterként kapott értékeket.
     * @param timeinsecond az idő másodpercekben.
     * @param score a pontok.
     * @param username a játékosneve.
     */
    public void addToRekords(int timeinsecond, int score, String username){
        rekordok.time.add(timeinsecond);
        rekordok.score.add(score);
        rekordok.username.add(username);
        sortrekords();

        }

    /**
     * Rendezik a rekordok objektum listáit pontok alapján.
     */
    public void sortrekords(){
        Rekords.concurrentSort(rekordok.score,rekordok.score,rekordok.time,rekordok.username);
        }


    /**
     * Ez a metódus beleír a Rekordok.json fájlba.
     */
    public void write(){

            System.out.println(gson.toJson(rekordok));
            String s = gson.toJson(rekordok);

            try {
                outputStream = new FileOutputStream("Rekordok.json");
                outputStream.write(s.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    /**
     * Ez a metódus olvas a filename fájlból.
     */
        public void read(){
            try {
                JsonReader reader = new JsonReader(new FileReader(filename));
                rekordok = gson.fromJson(reader,Rekords.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


