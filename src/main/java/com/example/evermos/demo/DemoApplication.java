package com.example.evermos.demo;

import com.example.evermos.demo.game.TreasureHuntGame;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        XADataSourceAutoConfiguration.class
})
@EnableAsync
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        TreasureHuntGame game = new TreasureHuntGame();
        boolean playing = false;
        int tempX = 1;
        int tempY = 4;
        System.out.println("Press \"p\" for play treasure hunt");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s;
            while ((s = br.readLine()) != null) {
                clearConsole();
                int newX = tempX;
                int newY = tempY;

                switch (s) {
                    case "a":
                        newY -= 1;
                        break;
                    case "b":
                        newX += 1;
                        break;
                    case "c":
                        newY += 1;
                        break;
                    case "p":
                        playing = true;
                        break;
                }

                if (playing) {
                    if (game.validateObstacle(newX,newY)) {
                        game.printGrid(tempX, tempY);
                        System.out.println("You can't move");
                        System.out.println("Press \"r\" for restart");
                    } else {
                        game.printGrid(newX,newY);
                        tempX = newX;
                        tempY = newY;

                        if (game.isTreasureFound(newX,newY)) {
                            System.out.println("Treasure found");
                        } else {
                            System.out.println(game.countStepTreasure(newX,newY) + " More step to treasure");
                        }
                    }
                }

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void clearConsole() throws IOException, InterruptedException {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
    }
}
