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
public class QueensProblemOptimized {

    public static Random random = new Random();

    public static long currentQueenMap;
    public static long bestQueenMap;

    public static QueenBoardPanel bestPanel;
    public static QueenBoardPanel currentPanel;

    public static int bestQueenMapValue = Integer.MAX_VALUE;
    public static boolean solvedConflict = false;

    public static BufferedImage queenImage;
    public static BufferedImage blackSquareImage;
    public static BufferedImage whiteSquareImage;

    public static long startTime = System.currentTimeMillis();
    public static long currentTimeElapsed = 0;

    //Precomputed bit masks below, used for efficient long bit calculations
    ///////////////////////////////////////////////////////////////////////////////////////
    public static long[] DIAGONAL_MASKS = createDiagonalMasks();

    private static final long[] COLUMN_MASKS = {
            0x0101010101010101L, 0x0202020202020202L, 0x0404040404040404L, 0x0808080808080808L,
            0x1010101010101010L, 0x2020202020202020L, 0x4040404040404040L, 0x8080808080808080L
    };

    // 0 1 2 3 4 5 6 7 8
    private static final int[] CONFLICTS = {
            0, 0, 1, 3, 6, 10, 15, 21, 28
    };

    //security via natural obfuscation
    private static long[] createDiagonalMasks() {
        long[] masks = new long[30];

        for (int diagonal = -7; diagonal <= 7; diagonal++) {
            long mask = 0L;
            for (int row = 0; row < 8; row++) {
                int col = row - diagonal;
                if (col >= 0 && col < 8) {
                    mask |= 1L << (row * 8 + col);
                }
            }

            masks[diagonal + 7] = mask;
        }

        for (int diagonal = 0; diagonal <= 14; diagonal++) {
            long mask = 0L;
            for (int row = 0; row < 8; row++) {
                int col = diagonal - row;
                if (col >= 0 && col < 8) {
                    mask |= 1L << (row * 8 + col);
                }
            }

            masks[15 + diagonal] = mask;
        }

        return masks;
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        try {
            queenImage = ImageIO.read(new File("src/main/resources/healerqueen.png"));
            blackSquareImage = ImageIO.read(new File("src/main/resources/bs.png"));
            whiteSquareImage = ImageIO.read(new File("src/main/resources/ws.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        createNewQueenMap();

        SwingUtilities.invokeLater(() -> {
            createWindow();
            new Thread(QueensProblemOptimized::solveQueens).start();

            Timer timer = new Timer(0, e -> {
                if (!solvedConflict) {
                    currentTimeElapsed = System.currentTimeMillis() - startTime;
                }
                currentPanel.repaint();
            });
            timer.start();

        });
    }

    //No time adjustment for this one, it's too optimized
    public static void solveQueens() {
        while (!solvedConflict) {
            createNewQueenMap();

            int currentQueenMapValue = calculateQueenMapValue(currentQueenMap);

            if (currentQueenMapValue == 0) {
                solvedConflict = true;
            }

            if (currentQueenMapValue <= bestQueenMapValue) {
                bestQueenMapValue = currentQueenMapValue;
                bestQueenMap = currentQueenMap;
                bestPanel.repaint();
            }
        }
    }

    // Instead of wasting time doing a slow division calculation to add to conflicts, I precomputed
    // the conflicts and we can use bit magic to get the specific collision count in O(fast) time complexity
    private static int calculateQueenMapValue(long board) {
        int conflicts = 0;

        for (int row = 0; row < 8; row++) {
            //no bit mask because we can just do hellish bit magic instead
            conflicts += CONFLICTS[Long.bitCount((board >>> (row * 8)) & 0xFFL)];
        }

        for (long mask : COLUMN_MASKS) {
            conflicts += CONFLICTS[Long.bitCount(board & mask)];
        }

        for (long mask : DIAGONAL_MASKS) {
            conflicts += CONFLICTS[Long.bitCount(board & mask)];
        }

        return conflicts;
    }

    //This could technically be optimized to remove the 1/64 to 7/64 bit collision chance,
    //but all my attempts at doing that are slower than this. I tried using an array and removing
    // the indexes that were already taken but it just is slower despite being "faster"
    public static void createNewQueenMap() {
        currentQueenMap = 0L;
        int queensPlaced = 0;

        while (queensPlaced < 8) {
            int position = random.nextInt(64);
            long bit = 1L << position;
            if ((currentQueenMap & bit) == 0) {
                currentQueenMap |= bit;
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

            long queenMap = bestBoard ? bestQueenMap : currentQueenMap;

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

                    BufferedImage tileImage = (row + col) % 2 == 0 ? whiteSquareImage : blackSquareImage;

                    if (tileImage != null) {
                        g2d.drawImage(tileImage, x, y, squareSize, squareSize, null);
                    }

                    int bit = row * 8 + col;

                    if ((queenMap & (1L << bit)) != 0 && queenImage != null) {
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
