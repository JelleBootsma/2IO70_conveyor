public class main {

    public static void main(String[] args) {
        System.out.println("hello world");

        ClientClass mqtt = new ClientClass("tcp://192.168.0.2");
        mqtt.connect();

        mqtt.subscribe("test", 1);
        int x = 0;
        while(x < 10){

            if(!mqtt.MessageQueue.isEmpty()){
                System.out.println(mqtt.MessageQueue.getFirst()[1]);
                mqtt.MessageQueue.removeFirst();
            }
            else{
                mqtt.publish("test", "sampleMessage", 1);
                mqtt.publish("test", "sampleMessage1", 1);
                mqtt.publish("test", "sampleMessage2", 1);
                mqtt.publish("test", "sampleMessage3", 1);
            }
            x += 1;
        }



    }
}
