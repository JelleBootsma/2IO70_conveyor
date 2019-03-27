

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.LinkedList;

import com.pi4j.io.gpio.RaspiPin;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author s169626
 */
public class Main {

        private Locator locator = new Locator();
        
        private Runtime runtime = new Runtime();
        LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
        

        private Bot bot2 = new Bot(10, 2);
        private Bot bot3 = new Bot(20, 3);
        private Bot bot4 = new Bot(30, 4);
        
        
        Belt belt;
        private DiskManager diskManager = new DiskManager(belt);
        ClientClass mqtt = new ClientClass("tcp://192.168.0.2");
        LinkedList<String> gpioInputQueue = new LinkedList<String>();

        Engine engine = new Engine();


    public void main(){

        Thread gateThread = new Thread(new Gate(blockingQueue));
        gateThread.start();

        locator.set(runtime);
        belt = new Belt(locator);


        
        

        //Engines
        belt.iBeltEngines.in.stop = this::stopEngine;
        belt.iBeltEngines.in.start = this::startEngine;
        
        belt.iGate.in.openGate = this::openGate;
        belt.iGate.in.closeGate = this::closeGate;

        
        //OUTgoing MQTT
        belt.mQTTBot2.in.takeItemSig      = this::bot2TakeItemSig;
        belt.mQTTBot2.in.startSequenceSig = this::bot2StartSequenceSig;
        belt.mQTTBot2.in.reboot           = this::bot2Reboot;
        
        belt.mQTTBot3.in.takeItemSig            = this::bot3TakeItemSig;
        belt.mQTTBot3.in.placeItemGrantedSig    = this::bot3PlaceItemGrantedSig;
        belt.mQTTBot3.in.placeItemDeniedSig     = this::bot3PlaceItemDeniedSig;
        belt.mQTTBot3.in.reboot                 = this::bot3Reboot;
        
        belt.mQTTBot4.in.takeItemSig            = this::bot4TakeItemSig;
        belt.mQTTBot4.in.placeItemGrantedSig    = this::bot4PlaceItemGrantedSig;
        belt.mQTTBot4.in.placeItemDeniedSig     = this::bot4PlaceItemDeniedSig;
        belt.mQTTBot4.in.reboot                 = this::bot4Reboot;
        
        //Cleanup unlogical Dezyne Choice (When extra time I'll fix this [TIJS])
        belt.iDisk2Com.in.assignedD = () -> { newDiskAssigned(bot2, 0); };
        belt.iDisk2Com.in.assigned3 = () -> { newDiskAssigned(bot2, 10); };
        belt.iDisk2Com.in.assigned4 = () -> { newDiskAssigned(bot2, 20); };

        belt.iDisk3Com.in.assignedD = () -> { newDiskAssigned(bot3, 0); };
        belt.iDisk3Com.in.assigned3 = () -> { newDiskAssigned(bot3, 10); };
        belt.iDisk3Com.in.assigned4 = () -> { newDiskAssigned(bot3, 20); };

        belt.iDisk4Com.in.assignedD = () -> { newDiskAssigned(bot4, 0); };
        belt.iDisk4Com.in.assigned3 = () -> { newDiskAssigned(bot4, 10); };
        belt.iDisk4Com.in.assigned4 = () -> { newDiskAssigned(bot4, 20); };

        belt.iDisk2Com.in.reboot = this::unAssignDisks;
        belt.iDisk3Com.in.reboot = this::unAssignDisks;
        belt.iDisk4Com.in.reboot = this::unAssignDisks;

        belt.iDisk2Com.in.unAssign = () -> {};
        belt.iDisk3Com.in.unAssign = () -> {};
        belt.iDisk4Com.in.unAssign = () -> {};






       
       (new Thread(new Processor(gpioInputQueue, diskManager))).start();
        mqtt.connect();
        mqtt.subscribe("Bot2_T",1);
        mqtt.subscribe("Bot3_T",1);
        mqtt.subscribe("Bot4_T",1);

        while(true){

            if(!gpioInputQueue.isEmpty()){

                if (gpioInputQueue.getFirst().equals("reboot")) {
                    belt.iBeltcontroller.in.reboot.action();
                    diskManager.cleanUpList();
                }
                if (gpioInputQueue.getFirst().equals("dropped")) {
                    belt.iBeltcontroller.in.dropped.action();
                }
                if (gpioInputQueue.getFirst().equals("startSequence")) {
                    belt.iBeltcontroller.in.dropped.action();
                }

                gpioInputQueue.removeFirst();
            }


            if(!mqtt.MessageQueue.isEmpty()){
                if(mqtt.MessageQueue.getFirst()[0].equals("Bot2_T")){
                    if(mqtt.MessageQueue.getFirst()[1].equals("sequenceReceived")){
                        System.out.print("ok");
                        belt.mQTTBot2.out.sequenceReceived.action();
                    }
                    if(mqtt.MessageQueue.getFirst()[1].equals("sequenceProcessed")){
                        belt.mQTTBot2.out.sequenceProcessed.action();
                    }
                    if (mqtt.MessageQueue.getFirst()[1].equals("reboot")) {
                        belt.iBeltcontroller.in.reboot.action();
                        diskManager.cleanUpList();
                    }
                    if (mqtt.MessageQueue.getFirst()[1].equals("dropped")) {
                        belt.iBeltcontroller.in.dropped.action();
                    }
                    if (mqtt.MessageQueue.getFirst()[1].equals("startSequence")) {
                        belt.iBeltcontroller.in.dropped.action();
                    }
                }
                if(mqtt.MessageQueue.getFirst()[0].equals("Bot3_T")){
                    if(mqtt.MessageQueue.getFirst()[1].equals("available")){
                        belt.mQTTBot3.out.availableSig.action();
                    }
                    if(mqtt.MessageQueue.getFirst()[1].equals("placeItem")){
                        belt.mQTTBot3.out.placeItemSig.action();
                    }
                    if(mqtt.MessageQueue.getFirst()[1].equals("placedItem")){
                        belt.mQTTBot3.out.placeItemSig.action();
                    }
                }
                if(mqtt.MessageQueue.getFirst()[0].equals("Bot4_T")){
                    if(mqtt.MessageQueue.getFirst()[1].equals("available")){
                        belt.mQTTBot4.out.availableSig.action();
                    }
                    if(mqtt.MessageQueue.getFirst()[1].equals("placeItem")){
                        belt.mQTTBot4.out.placeItemSig.action();
                    }
                    if(mqtt.MessageQueue.getFirst()[1].equals("placedItem")){
                        belt.mQTTBot4.out.placeItemSig.action();
                    }
                }
                 System.out.println("loop" + mqtt.MessageQueue.getFirst()[1]);
                 mqtt.MessageQueue.removeFirst();
            }


            try{
                Thread.sleep(100);
            } catch (Exception e){
                System.out.println(e);
            }

        }
    }
    
    private void startEngine(){
        //TODO write to pins
        System.out.println("Engine started");
        engine.startEngine();
    }
    private void stopEngine(){
        System.out.println("Engine stopped");
        engine.stopEngine();
    }
    private void openGate(){

        blockingQueue.add("open");
        
    }
    private void closeGate(){
        //NO implementation needed by means of the QUeue!!!

    }

    //MQTT
    private void bot2TakeItemSig(){
        sendOverMqtt("takeItem", 2);
    }
    private void bot2StartSequenceSig(){
        sendOverMqtt("startSequence", 2);
    }
    private void bot2Reboot(){
        sendOverMqtt("reboot", 2);
    }
    
    
    private void bot3TakeItemSig(){
        sendOverMqtt("takeItem", 3);
    }
    private void bot3PlaceItemGrantedSig(){
        sendOverMqtt("placeItemGranted", 3);
    }
    private void bot3PlaceItemDeniedSig(){
        sendOverMqtt("placeItemDenied", 3);
    }
    private void bot3Reboot(){
        sendOverMqtt("reboot", 3);
    }
    
    private void bot4TakeItemSig(){
        sendOverMqtt("takeItem", 4);
    }
    private void bot4PlaceItemGrantedSig(){
        sendOverMqtt("placeItemGranted", 4);
    }
    private void bot4PlaceItemDeniedSig(){
        sendOverMqtt("placeItemDenied", 4);
    }
    private void bot4Reboot(){
        sendOverMqtt("reboot", 4);
    }

    private void unAssignDisks(){
        diskManager.cleanUpList();
    }

    private void newDiskAssigned(Bot bot, int location){
        Disk disk = new Disk(location, bot);
        diskManager.addDiskToList(disk);
    }

    //Generalizations
    private void sendOverMqtt(String message, int groupNr){
        //TODO implement this function
        if(groupNr == 2){
            mqtt.publish("Bot2_R", message,1);
        }
        else if(groupNr == 3){
            mqtt.publish("Bot3_R", message,1);
        }
        else if(groupNr == 4){
            mqtt.publish("Bot4_R", message,1);
        }
    }
    
    
}
