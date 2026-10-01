package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

// Out of the 4,426,165,368 possible arrangements of eight queens on the board,
// only 92 arrangements satisfy these constraints
public class MagicSquares {

    public static Random random = new Random();

    public static byte[] currentMap;
    public static byte[] bestMap;

    public static MagicSquareBoardPanel bestPanel;
    public static MagicSquareBoardPanel currentPanel;

    public static double bestFitness = 1;
    public static boolean solvedConflict = false;

    public static BufferedImage blackSquareImage;
    public static BufferedImage whiteSquareImage;

    public static long startTime = System.currentTimeMillis();
    public static long currentTimeElapsed = 0;

    public static int SIMULATION_SPEED_DELAY = 0;

    public static void main(String[] args){
        try {
            //Textures taken from World War Chess
            blackSquareImage = ImageIO.read(new File("src/main/resources/bs.png"));
            whiteSquareImage = ImageIO.read(new File("src/main/resources/ws.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        createNewSquareMap();

        SwingUtilities.invokeLater(() -> {
            createWindow();

            //I was having weird rendering bugs so this is on a new thread now
            new Thread(MagicSquares::solveQueens).start();
        });
    }

    public static void solveQueens(){
        double currentFitness;

        while (!solvedConflict){
            try {
                Thread.sleep(SIMULATION_SPEED_DELAY);
            } catch (Exception ignored){}
            currentTimeElapsed = System.currentTimeMillis() - startTime;

            createNewSquareMap();
            currentPanel.repaint();

            currentFitness = calculateSquareMapValue();

            if (currentFitness == 0){
                solvedConflict = true;
            }

            if (bestFitness >= currentFitness){
                //why does cloning 2D arrays suck...
                bestFitness = currentFitness;
                bestMap = currentMap.clone();
                bestPanel.repaint();
            }
        }
    }

    private static double calculateSquareMapValue() {
        int sum1 = currentMap[0] + currentMap[3] + currentMap[6];
        int sum2 = currentMap[1] + currentMap[4] + currentMap[7];
        int sum3 = currentMap[2] + currentMap[5] + currentMap[8];

        int sum4 = currentMap[0] + currentMap[1] + currentMap[2];
        int sum5 = currentMap[3] + currentMap[4] + currentMap[5];
        int sum6 = currentMap[6] + currentMap[7] + currentMap[8];

        int sum7 = currentMap[0] + currentMap[4] + currentMap[8];
        int sum8 = currentMap[2] + currentMap[4] + currentMap[6];

        double average = (double) (sum1 + sum2 + sum3 + sum4 + sum5 + sum6 + sum7 + sum8) / 8;

        //standard deviation algorithm from internet
        double variance = (Math.pow(sum1 - average, 2) +
                        Math.pow(sum2 - average, 2) +
                        Math.pow(sum3 - average, 2) +
                        Math.pow(sum4 - average, 2) +
                        Math.pow(sum5 - average, 2) +
                        Math.pow(sum6 - average, 2) +
                        Math.pow(sum7 - average, 2) +
                        Math.pow(sum8 - average, 2)) / 8;

        return Math.sqrt(variance);
    }

    public static void createNewSquareMap(){
        currentMap = new byte[9];

        boolean hasThatNumber = false;
        int valuesPlaced = 0;

        while (valuesPlaced < 9) {
            byte val = (byte) (random.nextInt(10) - 10);

            for (byte value : currentMap){
                if (val == value){
                    hasThatNumber = true;
                    break;
                }
            }

            if (!hasThatNumber) {
                currentMap[valuesPlaced] = val;
                valuesPlaced++;
            }
            hasThatNumber = false;
        }
    }

    //logic above
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //drawing below

    private static void createWindow() {
        JFrame frame = new JFrame("S Q U A R E S");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2, 20, 0));

        bestPanel = new MagicSquareBoardPanel(true);
        currentPanel = new MagicSquareBoardPanel(false);

        frame.add(bestPanel);
        frame.add(currentPanel);

        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static class MagicSquareBoardPanel extends JPanel {

        private final boolean bestBoard;

        public MagicSquareBoardPanel(boolean bestBoard) {
            this.bestBoard = bestBoard;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            byte[] boardMap = bestBoard ? bestMap : currentMap;

            if (boardMap == null) {
                return;
            }

            Graphics2D g2d = (Graphics2D) g.create();

            drawBackground(getWidth(), getHeight(), getWidth() / 2, getHeight() / 2, 10, g2d);

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));

            String title = bestBoard ? "Best (" + bestFitness + " fitness)" : "Current";
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

            int boardSize = Math.min(getWidth(), getHeight() - 50);
            int squareSize = boardSize / 3;
            boardSize = squareSize * 3;
            int startX = (getWidth() - boardSize) / 2;
            int startY = 40;

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));

            for (int i = 0; i < 9; i++) {
                int row = i / 3;
                int col = i % 3;

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

                String value = Byte.toString(boardMap[i]);

                int textWidth = g2d.getFontMetrics().stringWidth(value);
                int textHeight = g2d.getFontMetrics().getAscent();

                int textX = x + (squareSize - textWidth) / 2;
                int textY = y + (squareSize + textHeight) / 2;

                g2d.setColor(Color.WHITE);
                g2d.drawString(value, textX, textY);
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
