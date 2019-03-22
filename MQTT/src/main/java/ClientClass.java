import org.eclipse.paho.client.mqttv3.*;
import java.util.UUID;
import java.util.LinkedList;


public class ClientClass implements MqttCallback{
    IMqttClient client;
    LinkedList<String[]> MessageQueue;

    public ClientClass(String serverURI){
        MessageQueue = new LinkedList();
        try {
            String clientID = UUID.randomUUID().toString();
            client = new MqttClient(serverURI, clientID);
        }
        catch (Exception e){
            System.out.println("Failed to instantiate object");
            System.out.println(e.toString());
        }
    }

    public void connect(){
        try{
            client.connect();
            System.out.println("Connected successfully");
        }
        catch (Exception e){
            System.out.println("Connection Failed");
            System.out.println(e.toString());
        }


    }


    public void publish(String topic, String stringMessage, int QoS) {
        try {
            MqttMessage messageMqtt = new MqttMessage(stringMessage.getBytes());
            messageMqtt.setQos(QoS);
            client.publish(topic, messageMqtt);
            System.out.println("Message Published");
        } catch (Exception e) {
            System.out.println("Failed to publish message");
            System.out.println(e.toString());
        }
    }
    public void subscribe(String topic, int QoS){
        try {
            client.setCallback(this);
            client.subscribe(topic, QoS);
            System.out.println("Subscribed to " + topic);

        } catch (Exception e) {
            System.out.println("Failed to subscribe");
            System.out.println(e.toString());
        }

    }

    public void unsubscribe(String topic){
        try{
            client.unsubscribe(topic);
        }
        catch (Exception e){
            System.out.println("Failed to unsubscribe");
            System.out.println(e.toString());
        }
    }
    public void disconnect(){
        try {
            client.disconnect();
            System.out.println("Disconnected");
        }
        catch (Exception e){
            System.out.println("Disconnect failed");
            System.out.println(e.toString());
        }


    }






    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub

    }

    @Override
    public void messageArrived(String topic, MqttMessage message)
            throws Exception {
        String[] Recieved = {topic, message.toString()};
        this.MessageQueue.add(Recieved);
        System.out.println("Added to queue");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub

    }
}
