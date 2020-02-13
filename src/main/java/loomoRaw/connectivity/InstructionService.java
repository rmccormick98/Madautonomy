package loomoRaw.connectivity;
import android.app.IntentService;
import android.os.IBinder;
import android.content.Intent;
import android.widget.Toast;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class InstructionService extends IntentService {

    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;

    public InstructionService(){
        super("InstructionService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        System.out.println("Inside Service");

        try {
            serverSocket = new ServerSocket(4447);

            clientSocket = serverSocket.accept(); // blocks and listen until a connection is made.

            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            wr.write("Loomo Message");
            wr.flush(); // flushes the stream

            String inputLine;
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }

            serverSocket.close();
            clientSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
