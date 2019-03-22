

public class main {

    public static void main(String[] args) {
        ClientClass thread = new ClientClass("tcp://localhost:1883");
        thread.connect();
        thread.subscribe("asdf",1);
        thread.publish("asdf", "test", 1);
        thread.disconnect();
        System.out.println(thread.MessageQueue.getFirst()[1]);
    }
}

