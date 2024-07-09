package moneyexchange.Trading_Swap;

import moneyexchange.CurrencyName;
import java.io.*;
import java.net.Socket;

public class SwapClient {
    private String serverAddress;
    private int serverPort;

    public SwapClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public SwapRequest sendSwapRequest(SwapRequest request) throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(request);
            return (SwapRequest) in.readObject();
        }
    }
}
