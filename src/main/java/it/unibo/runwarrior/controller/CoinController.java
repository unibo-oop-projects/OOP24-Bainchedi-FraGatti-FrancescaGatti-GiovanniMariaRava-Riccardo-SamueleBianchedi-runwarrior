package it.unibo.runwarrior.controller;


import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



import it.unibo.runwarrior.model.Coin;
public class CoinController {
    List<Coin> coinList;
    public CoinController(){
        coinList = new ArrayList<>(); 
    }

    public List<int[]> loadCoinFromFile(String pathFile){
        List<int[]> coinCoordinates = new ArrayList<>();
        try(InputStream is = getClass().getClassLoader().getResourceAsStream(pathFile);
         BufferedReader fileReader = new BufferedReader(new InputStreamReader(is))){
            if (is == null) {
            System.err.println("File non trovato nel classpath: " + pathFile);
            return coinCoordinates;
            }
            String line;
            while((line = fileReader.readLine()) != null){
                line = line.trim();
                if(!line.isEmpty() && line.contains(",")){
                    String[] parts = line.split(",");
                    int row = Integer.parseInt(parts[0].trim());
                    int col = Integer.parseInt(parts[1].trim());
                    coinCoordinates.add(new int[]{row, col});
                }
            }
        }catch(IOException | NumberFormatException e){
            e.printStackTrace();
        }
        return coinCoordinates;
    }

    public void initCoinsFromFile(String pathFile){
        List<int[]> coords = loadCoinFromFile("CoinCoordinates_map1.txt");
        for(int[] coord : coords){
            int row = coord[0];
            int col = coord[1];
            addCoins(row, col);
        }
    }

    public void addCoins(int row, int col){
        Coin coin = new Coin(row, col);
        coin.loadCoinImage();
        coinList.add(coin);
    }

    public void drawAllCoins(Graphics g, int tileSize){
        for(Coin coin : coinList){
            if(!coin.isCollected()){
                System.out.println("Disegno moneta a colonna " + coin.getCol() + ", riga " + coin.getRow());
                int x = coin.getCol() * tileSize;
                int y = coin.getRow() * tileSize;
                g.drawImage(coin.coinImage, x, y, tileSize, tileSize, null);
            }
        }
    }

}
