

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//MANUALLY WRITTEN


/**
 *
 * @author s169626
 */
public class Disk {
    private int location;
    private Bot assignedBot;
    
    
    public Disk(int loc, Bot assignedBot){
        location            = loc;
        this.assignedBot    = assignedBot;
    }
    
    public int getLocation(){
        return location;
    }
    public Bot getAssignedBot(){
        return assignedBot;
    }
    public void setLocation(int loc){
        location = loc;
    }
    public void setAssignedBot(Bot bot){
        assignedBot = bot;
    }
}
