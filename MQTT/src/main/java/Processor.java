

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;


import java.util.LinkedList;

/**
 *
 * @author s169626
 */
public class Processor implements Runnable{
    
    private Belt belt;
    private Bot bot2;
    private Bot bot3;
    private Bot bot4;

    private LinkedList<String> gpioInputQueue;
    private DiskManager diskManager;

    private Display window;

    
    public Processor(LinkedList<String> gpioInputQueue, DiskManager diskManager){
        this.gpioInputQueue = gpioInputQueue;
        this.diskManager = diskManager;
        this.window = new Display(this.diskManager);
        this.window.Build();

        
    }
    
    @Override
    public void run(){
        
        try{
            startGpio();
        }
        catch(Exception e){
            System.out.println("An exception with GPIO has occured " + e.toString());
        }
        
    }
    private void startGpio() throws InterruptedException{
        final GpioController gpio = GpioFactory.getInstance();
        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput dropper = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);

        // set shutdown state for this input pin
        dropper.setShutdownOptions(true);
        // create and register gpio pin listener
        dropper.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                if(event.getState().isHigh()){
                    gpioInputQueue.addLast("dropped");
                }
            }

        });
        
        final GpioPinDigitalInput reboot = gpio.provisionDigitalInputPin(RaspiPin.GPIO_16, PinPullResistance.PULL_DOWN);

        // set shutdown state for this input pin
        reboot.setShutdownOptions(true);
        // create and register gpio pin listener
        reboot.addListener(new GpioPinListenerDigital(){
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event){
                // display pin state on console
                gpioInputQueue.addLast("emergency");
                

            }

        });
        
        final GpioPinDigitalInput sequence = gpio.provisionDigitalInputPin(RaspiPin.GPIO_10, PinPullResistance.PULL_DOWN);

        // set shutdown state for this input pin
        sequence.setShutdownOptions(true);
        // create and register gpio pin listener
        sequence.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                gpioInputQueue.addLast("sequence");
            }

        });

        final GpioPinDigitalInput ticker = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_DOWN);

        // set shutdown state for this input pin
        ticker.setShutdownOptions(true);
        // create and register gpio pin listener
        ticker.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                diskManager.movement();
                window.Refresh();
            }

        });

        // keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(500);
        }
        
        
    }
    
    public void sleepThread(int miliseconds) throws InterruptedException{
        try{
            Thread.sleep(miliseconds);
        }
        catch(Exception e){
            System.out.println("Incorrect parameter " + e.toString());
        }
    }
    
    
    
}
