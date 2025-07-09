package it.unibo.runwarrior.controller;


import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import it.unibo.runwarrior.model.Coin;
public class CoinController {
    List<Coin> coinList;
    public CoinController(){
        coinList = new ArrayList<>(); 
    }

    public List<int[]> loadCoinFromFIle(String pathFile){
        List<int[]> coinCoordinates = new ArrayList<>();
        try(BufferedReader fileReader = new BufferedReader(new FileReader(pathFile))){
            String line; 
            while((line = fileReader.readLine())!=null){
                line=line.trim(); //Rimuove gli spazi bianchi iniziali e finali dalla stringa line.
                if(!line.isEmpty() && line.contains(",")){
                    String[] parts = line.split(","); //Divide la stringa line in più parti, separandola ogni volta che trova una virgola
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
        List<int[]> coords = loadCoinFromFIle("CoinCoordinates_map1.txt");
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
                int x = coin.getCol() * tileSize;
                int y = coin.getRow() * tileSize;
                g.drawImage(coin.coinImage, x, y, tileSize, tileSize, null);
            }
        }
    }

}
