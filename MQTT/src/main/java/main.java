

public class main {

    public static void main(String[] args) {
        ClientClass thread = new ClientClass("tcp://localhost:1883");
        thread.connect();
        thread.subscribe("test topic",1);
        thread.publish("test topic", "test message", 1);
        thread.disconnect();
    }
}

