

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author s169626
 */
public class Bot {
    private int fixedLocation;
    private int botNr;
    public Bot(int fixedLocation, int botNr){
        this.fixedLocation = fixedLocation;
        this.botNr = botNr;
        
    }
    public int getFixedLocation(){
        return fixedLocation;
    }
    public int getBotNr() { return botNr;}
    
}
