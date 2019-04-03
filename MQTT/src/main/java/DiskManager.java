

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.LinkedList;
/**
 *
 * @author s169626
 */
public class DiskManager {
    
    private LinkedList<Disk> diskList;
    private Belt belt;
    public DiskManager(Belt belt){

        diskList = new LinkedList<Disk>();
        this.belt = belt;
    }
    
    public void movement(){
        LinkedList disksToBeRemoved = new LinkedList<Disk>();
        for(Disk disk : diskList){
            int x = disk.getLocation();
            disk.setLocation((x+1)%50);
            System.out.println("disk location " + disk.getLocation());
            System.out.println("assignedBot location " + disk.getAssignedBot().getPickPoint());
            if(disk.getLocation() == disk.getAssignedBot().getPickPoint()){
                arrived(disk);
                disksToBeRemoved.addLast(disk);
            }
        }
        disksToBeRemoved.removeAll(disksToBeRemoved);
        
    }
    
    public void addDiskToList(Disk diskToAdd){
        diskList.add(diskToAdd);
    }
    
    public void cleanUpList(){
        diskList = new LinkedList<Disk>();
    }
    
    public void arrived(Disk disk){


        if(disk.getAssignedBot().getBotNr() == 2){
            belt.iDisk2Com.out.diskArrived.action();
        }
        if(disk.getAssignedBot().getBotNr() == 3){

            belt.iDisk3Com.out.diskArrived.action();

        }
        if(disk.getAssignedBot().getBotNr() == 4){
            belt.iDisk4Com.out.diskArrived.action();
        }
    }
    private void removeFromList(Disk diskToRemove){
        for(Disk disk : diskList){
            if(disk == diskToRemove){
                diskList.remove(disk);
                break;
            }
        }
    }
}
