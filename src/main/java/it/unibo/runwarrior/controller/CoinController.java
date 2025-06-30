package it.unibo.runwarrior.controller;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import it.unibo.runwarrior.model.Coin;
public class CoinController {
    List<Coin> coinList;
    BufferedImage coinImage; 
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

    public void addCoins(int row, int col){

    }

}
