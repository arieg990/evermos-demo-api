package com.example.evermos.demo.game;

import org.springframework.data.util.Pair;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TreasureHuntGame {
    public void printGrid(int x, int y) {
        generateGrid(x, y).subscribe(s -> {
            String map = s.getFirst();
            System.out.println(map);
        });
    }

    /**
     * generate grid layout
     * @param positionX is current horizontal position
     * @param positionY is current vertical position
     * @return Mono<Pair<String, String>>
     */
    private Mono<Pair<String, String>> generateGrid(int positionX, int positionY) {
        Integer[] yValue = {0, 1, 2, 3, 4, 5};
        Integer[] xValue = {0, 1, 2, 3, 4, 5, 6, 7};
        AtomicReference<String> msg = new AtomicReference<>("");
        AtomicInteger pX = new AtomicInteger(positionX);
        AtomicInteger pY = new AtomicInteger(positionY);

        return Flux.fromArray(yValue)
                .flatMap(y -> Flux.fromArray(xValue)
                        .map(x -> {
                            if (validateObstacle(x, y)) {
                                return "#";
                            } else if (!validateObstacle(pX.get(), pY.get()) && x == positionX && y == positionY) {
                                return "X";
                            } else {
                                return ".";
                            }
                        })
                        .collectList()
                        .map(s -> String.join("", s)
                        )).collectList()
                .map(s -> Pair.of(String.join("\n", s), msg.get()));

    }

    private HashMap<Integer, int[]> obstaclePosition() {
        HashMap<Integer, int[]> obstacle = new HashMap<>();
        obstacle.put(0, new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        obstacle.put(1, new int[]{0, 7});
        obstacle.put(2, new int[]{0, 2, 3, 4, 7});
        obstacle.put(3, new int[]{0, 4, 6, 7});
        obstacle.put(4, new int[]{0, 2, 7});
        obstacle.put(5, new int[]{0, 1, 2, 3, 4, 5, 6, 7});

        return obstacle;
    }

    public Pair<Integer, Integer> treasurePosition() {

        return Pair.of(3, 5);
    }

    /**
     * validate treasure position in coordinate or not
     * @param x is horizontal position
     * @param y is vertical position
     * @return Boolean
     */
    public Boolean isTreasureFound(int x, int y) {
        return x == treasurePosition().getSecond() && y == treasurePosition().getFirst();
    }

    /**
     * Validation if player going to obstacle or not
     * @param x is horizontal position
     * @param y is vertical position
     * @return Boolean
     */
    public Boolean validateObstacle(int x, int y) {
        HashMap<Integer, int[]> obstacle = obstaclePosition();
        return obstacle.get(y) != null &&
                Arrays.stream(obstacle.get(y)).filter(i -> i == x).findAny().isPresent();
    }

    /**
     * count step to go to treasure
     * @param x is horizontal position
     * @param y is vertical position
     * @return int
     */
    public int countStepTreasure(int x, int y) {
        int count = 0;
        boolean repeat = true;
        int currentX = x;
        int currentY = y;

        while (repeat) {
            if (currentX < treasurePosition().getSecond() &&
                    !validateObstacle(currentX+1, currentY)) {
                currentX += 1;
                count += 1;
            } else if (currentY > treasurePosition().getFirst() &&
                    !validateObstacle(currentX, currentY-1)) {
                currentY -= 1;
                count += 1;
            } else if (currentY < treasurePosition().getFirst() &&
                    !validateObstacle(currentX, currentY+1)) {
                currentY += 1;
                count += 1;
            } else {
                if (!validateObstacle(currentX, currentY+1)) {
                    currentY += 1;
                    count += 1;
                } else if (!validateObstacle(currentX, currentY-1)) {
                    currentY -= 1;
                    count += 1;
                } else {
                    count = 0;
                }
            }
            repeat = !isTreasureFound(currentX, currentY);
        }

        return count;
    }
}
