package mock;

import com.intuit.karate.FileUtils;
import com.intuit.karate.netty.FeatureServer;

import java.io.File;
import java.util.Scanner;

public class ServerRunner {

    public static void main(String[] args) {
        File file = FileUtils.getFileRelativeTo(ServerRunner.class, "../mock.feature");
        FeatureServer server = FeatureServer.start(file, 0, false, null);
        System.out.println("Server started on port " + server.getPort());
        System.out.println("Type `exit` to stop the server.");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while(!input.equals("exit")) {
            input = scanner.nextLine();
        }
        server.stop();
    }
}
