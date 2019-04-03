

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

    private int pickPoint;
    private int dropPoint;
    private int botNr;
    public Bot(int pickPoint, int dropPoint, int botNr){
        this.pickPoint = pickPoint;
        this.dropPoint = dropPoint;
        this.botNr = botNr;
        
    }
    public int getPickPoint(){
        return pickPoint;
    }
    public int getDropPoint() {return dropPoint; }
    public int getBotNr() { return botNr;}
    
}
