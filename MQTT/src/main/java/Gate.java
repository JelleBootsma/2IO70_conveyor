

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author s169626
 */
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;


import java.util.concurrent.LinkedBlockingQueue;
/**
 * This example code demonstrates how to perform simple state
 * control of a GPIO pin on the Raspberry Pi.
 *
 * @author Robert Savage
 */
public class Gate implements Runnable {

    LinkedBlockingQueue<String> blockingQueue;
    GpioController gpio = GpioFactory.getInstance();
    // provision gpio pin #01 as an output pin and turn on
    final GpioPinDigitalOutput pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "forwards", PinState.LOW);

    public Gate(LinkedBlockingQueue<String> blockingQueue){

        this.blockingQueue = blockingQueue;

    }
    
    @Override
    public void run(){
        while (true) {
            try{

                blockingQueue.take();

            } catch (Exception e){
                System.out.println("BlockingQueue exception " + e.toString());
            }

            sendOpenSignalToPin();
            if(blockingQueue.isEmpty()){
                sendCloseSignalToPin();
            }

        }
    }

    public void sendOpenSignalToPin() {



        System.out.println("pin1 state" + pin1.getState().toString());
        pin1.high();
        System.out.println("pin1 state" + pin1.getState().toString());
        System.out.println("--> GPIO state should be: ON");
        try {
            Thread.sleep(1000);
        } catch (Exception e){
            System.out.println("Sleep thread error " + e.toString());
        }

    }
    public void sendCloseSignalToPin() {
        
        
        
        // create gpio controller
        
        pin1.low();
        // provision gpio pin #01 as an output pin and turn on

        

        System.out.println("--> GPIO state should be: OFF");
    }

    
    
}