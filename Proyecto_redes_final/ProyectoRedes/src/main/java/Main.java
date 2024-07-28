// Main.java
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

public class Main {

    public static void main(String[] args) {
        try {
            TelegramBotsApi chatBot = new TelegramBotsApi(DefaultBotSession.class);
            Bot bot = new Bot();
            chatBot.registerBot(bot);

            String broker = "localhost"; // Dirección del broker MQTT
            int port = 1883; // Puerto del broker MQTT
            String topic = "data"; // Tema único

            Mqtt3AsyncClient client = MqttClient.builder()
                    .useMqttVersion3()
                    .serverHost(broker)
                    .serverPort(port)
                    .buildAsync();

            client.connectWith()
                    .cleanSession(true)
                    .send()
                    .whenComplete((connAck, throwable) -> {
                        if (throwable != null) {
                            // Handle connection failure
                            System.out.println("Conexión fallida: " + throwable.getMessage());
                        } else {
                            System.out.println("Conectado al broker MQTT");

                            client.subscribeWith()
                                    .topicFilter(topic)
                                    .callback(publish -> {
                                        String payload = new String(publish.getPayloadAsBytes());
                                        System.out.println("Mensaje recibido del tema " + topic + ": " + payload);

                                        // Procesar el mensaje recibido
                                        String[] parts = payload.split(", ");
                                        if (parts.length == 2) {
                                            String distancia = parts[0].replace("Distancia: ", "").replace("cm", "").trim();
                                            String peso = parts[1].replace("Peso: ", "").replace("g", "").trim();
                                            System.out.println("Distancia: " + distancia + " cm");
                                            System.out.println("Peso: " + peso + " g");

                                            // Update bot with new data
                                            bot.processMqttMessage(distancia, peso);
                                        } else {
                                            System.out.println("Formato de mensaje no válido.");
                                        }
                                    })
                                    .send();
                        }
                    });

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
