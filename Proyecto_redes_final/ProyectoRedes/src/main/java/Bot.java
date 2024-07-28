// Bot.java
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    
    private int distancia;
    private float peso;

    public String getBotUsername() {
        return "PetGourmetBot";
    }

    public String getBotToken() {
        return "7233434390:AAG_iLZa8Qpvb0vjY55AZXN9LlqnOarHzDA";
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    private SendMessage generateSendMessage(Long chatId, String mensaje) {
        return new SendMessage(chatId.toString(), mensaje);
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Calendar fechaActual = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat horaMinuto = new SimpleDateFormat("MM/yyyy HH:mm");
        Calendar ultimaFechaDeComida = null;

        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        Stopwatch1 stopwatch1 = new Stopwatch1();
        long minutos = 0;

        if (message.equalsIgnoreCase("/start")) {
            sendMessage(generateSendMessage(chatId, "Bienvenido a Pet Gourmet"));
            sendMessage(generateSendMessage(chatId, "La hora actual es: " + sdf.format(fechaActual.getTime())));
        }

        if (message.equalsIgnoreCase("/estado")) {
            if (this.peso == 0.0) {
                sendMessage(generateSendMessage(chatId, "El plato está vacío "));
            } else {
                sendMessage(generateSendMessage(chatId, "El plato tiene este peso de comida: " + peso));
            }
        } else if (message.equalsIgnoreCase("/comida")) {
            if (ultimaFechaDeComida != null) {
                sendMessage(generateSendMessage(chatId, "La última fecha de comida fue: " + sdf.format(ultimaFechaDeComida.getTime())));
            } else {
                sendMessage(generateSendMessage(chatId, "No hay información de la última comida."));
            }

            if (distancia >= 3 && distancia <= 10) {
                stopwatch1.start();
                if (distancia > 10) {
                    stopwatch1.stop();
                    ultimaFechaDeComida = Calendar.getInstance();
                    minutos = stopwatch1.getElapsedMinutes();
                }
            }

            if (ultimaFechaDeComida != null) {
                sendMessage(generateSendMessage(chatId, "Su perro se demoró " + minutos + " minutos comiendo " + horaMinuto.format(ultimaFechaDeComida.getTime())));
            }
        }

        if (message.equalsIgnoreCase("hola")) {
            System.out.println("Hola, ¿En qué puedo ayudarte?");
        }

        System.out.println("Mensaje recibido: " + message);
    }

    public void processMqttMessage(String distancia, String peso) {
        try {
            setDistancia(Integer.parseInt(distancia));
            setPeso(Float.parseFloat(peso));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
