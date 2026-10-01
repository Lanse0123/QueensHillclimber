package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.List;

// Out of the 4,426,165,368 possible arrangements of eight queens on the board,
// only 92 arrangements satisfy these constraints
public class QueensProblem {

    public static Random random = new Random();

    public static boolean[][] currentQueenMap;
    public static boolean[][] bestQueenMap;

    public static QueenBoardPanel bestPanel;
    public static QueenBoardPanel currentPanel;

    public static int bestQueenMapValue = Integer.MAX_VALUE;
    public static boolean solvedConflict = false;

    public static BufferedImage queenImage;
    public static BufferedImage blackSquareImage;
    public static BufferedImage whiteSquareImage;

    public static long startTime = System.currentTimeMillis();
    public static long currentTimeElapsed = 0;

    public static int SIMULATION_SPEED_DELAY = 0;

    public static void main(String[] args){
        try {
            //Textures taken from World War Chess
            queenImage = ImageIO.read(new File("src/main/resources/healerqueen.png"));
            blackSquareImage = ImageIO.read(new File("src/main/resources/bs.png"));
            whiteSquareImage = ImageIO.read(new File("src/main/resources/ws.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        createNewQueenMap();

        SwingUtilities.invokeLater(() -> {
            createWindow();

            //I was having weird rendering bugs so this is on a new thread now
            new Thread(QueensProblem::solveQueens).start();
        });
    }

    public static void solveQueens(){
        int currentQueenMapValue;

        while (!solvedConflict){
            try {
                Thread.sleep(SIMULATION_SPEED_DELAY);
            } catch (Exception ignored){}
            currentTimeElapsed = System.currentTimeMillis() - startTime;

            createNewQueenMap();
            currentPanel.repaint();

            currentQueenMapValue = calculateQueenMapValue();

            if (currentQueenMapValue == 0){
                solvedConflict = true;
            }

            if (bestQueenMapValue >= currentQueenMapValue){
                //why does cloning 2D arrays suck...
                bestQueenMapValue = currentQueenMapValue;
                bestQueenMap = new boolean[8][8];
                for (int row = 0; row < 8; row++) {
                    bestQueenMap[row] = currentQueenMap[row].clone();
                }
                bestPanel.repaint();
            }
        }
    }

    //I tried so hard to make this better than the connect 4 version but oh well its ugly
    private static int calculateQueenMapValue() {
        int conflicts = 0;

        for (int row1 = 0; row1 < 8; row1++) {
            for (int col1 = 0; col1 < 8; col1++) {

                if (!currentQueenMap[row1][col1]) {
                    continue;
                }

                for (int row2 = row1; row2 < 8; row2++) {
                    for (int col2 = 0; col2 < 8; col2++) {
                        if (row2 == row1 && col2 <= col1) {
                            continue;
                        }

                        if (!currentQueenMap[row2][col2]) {
                            continue;
                        }

                        //row
                        if (row1 == row2) {
                            conflicts++;
                        }
                        //column
                        else if (col1 == col2) {
                            conflicts++;
                        }
                        //diagonal
                        else if (Math.abs(row1 - row2) == Math.abs(col1 - col2)) {
                            conflicts++;
                        }
                    }
                }
            }
        }

        return conflicts;
    }

    public static void createNewQueenMap(){
        currentQueenMap = new boolean[8][8];

        int queensPlaced = 0;

        while (queensPlaced < 8) {
            int row = random.nextInt(8);
            int col = random.nextInt(8);

            if (!currentQueenMap[row][col]) {
                currentQueenMap[row][col] = true;
                queensPlaced++;
            }
        }
    }

    //logic above
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //drawing below

    private static void createWindow() {
        JFrame frame = new JFrame("THE QUEEEEEN (levy) ((hi))");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2, 20, 0));

        bestPanel = new QueenBoardPanel(true);
        currentPanel = new QueenBoardPanel(false);

        frame.add(bestPanel);
        frame.add(currentPanel);

        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static class QueenBoardPanel extends JPanel {

        private final boolean bestBoard;

        public QueenBoardPanel(boolean bestBoard) {
            this.bestBoard = bestBoard;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            boolean[][] queenMap = bestBoard ? bestQueenMap : currentQueenMap;

            if (queenMap == null) {
                return;
            }

            Graphics2D g2d = (Graphics2D) g.create();

            drawBackground(getWidth(), getHeight(), getWidth() / 2, getHeight() / 2, 10, g2d);

            int boardSize = Math.min(getWidth(), getHeight() - 50);
            int squareSize = boardSize / 8;
            boardSize = squareSize * 8;
            int startX = (getWidth() - boardSize) / 2;
            int startY = 40;

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));

            String title = bestBoard ? "Best (" + bestQueenMapValue + " conflicts)" : "Current";
            int titleWidth = g2d.getFontMetrics().stringWidth(title);
            g2d.drawString(title, (getWidth() - titleWidth) / 2, 30);

            //T I M E (ty)
            if (this == currentPanel) {
                long seconds = currentTimeElapsed / 1000;
                long milliseconds = currentTimeElapsed % 1000;
                String timeText = String.format("Time: %d.%03d s", seconds, milliseconds);
                int timeWidth = g2d.getFontMetrics().stringWidth(timeText);
                g2d.drawString(timeText, getWidth() - timeWidth - 10, 30);
            }


            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {

                    int x = startX + col * squareSize;
                    int y = startY + row * squareSize;
                    BufferedImage tileImage;

                    if ((row + col) % 2 == 0) {
                        tileImage = whiteSquareImage;
                    } else {
                        tileImage = blackSquareImage;
                    }

                    if (tileImage != null) {
                        g2d.drawImage(tileImage, x, y, squareSize, squareSize, null);
                    }

                    if (queenMap[row][col] && queenImage != null) {
                        int padding = squareSize / 10;
                        g2d.drawImage(queenImage, x + padding, y + padding, squareSize - padding * 2, squareSize - padding * 2, null);
                    }
                }
            }
            g2d.dispose();
        }
    }

    //main background copied from World War Chess
    public static void drawBackground(int width, int height, int centerX, int centerY, int step, Graphics2D g2d){
        int checkerSize = 160;
        List<Color> colors = List.of(new Color(40, 40, 40), new Color(80, 80, 80));
        Color color1 = colors.get(0);
        Color color2 = colors.get(1);

        for (int y = 0; y < height; y += step) {
            for (int x = 0; x < width; x += step) {
                double dx = x - centerX;
                double dy = y - centerY;
                double distance = Math.sqrt(dx * dx + dy * dy);
                double angle = Math.atan2(dy, dx);
                double swirlFactor = Math.log1p(distance) * 1.5;
                double distortedAngle = angle + swirlFactor;
                double wave = (Math.sin(distortedAngle * 6) * 0.5 + 0.5);
                int checkerX = (int) ((dx / checkerSize) + wave * 5) % 2;
                int checkerY = (int) ((dy / checkerSize) + wave * 5) % 2;
                boolean isBlack = (checkerX + checkerY) % 2 == 0;
                g2d.setColor(isBlack ? color1 : color2);
                g2d.fillRect(x, y, step, step);
            }
        }
    }
}
