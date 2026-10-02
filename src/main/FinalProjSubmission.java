/*
 * PROJECT TODO LIST: 8-Queens Hill Climbing
 *
 * 1. SETUP & BOARD REPRESENTATION
 * [ ] Parse x # of puzzles to solve.
 * [ ] Generate x random initial board states.
 * [ ] Board format: 8-element list (index = column, value = queen's row).
 *
 * 2. HEURISTIC & ALGORITHMS
 * [ ] Heuristic function h(s): Calculate total direct + indirect conflicts
 *     (queens sharing the same row or diagonal). Minimization goal: h(s) = 0.
 * [ ] Algorithm 1: Random-Restart Hill-Climbing
 *     - Generate all 56 successors (move 1 queen at a time to every row in its col).
 *     - Pick successor with lowest h(s) (break ties randomly).
 *     - Move to successor if its h(s) < current h(s).
 *     - If stuck at local min (no strictly lower successor), restart randomly.
 *     - Stop when h(s) == 0 or max restarts hit.
 * [ ] Algorithm 2: Implement ONE of (First-Choice HC, Genetic Alg, Simulated Annealing).
 *
 * 3. METRICS TRACKING
 * [ ] Track per algorithm search:
 *     - Search cost (# of successors/boards generated).
 *     - Number of restarts required.
 *     - Success/Failure status (solved vs max restarts hit).
 *
 * 4. OUTPUT & VISUALIZATION
 * [ ] Summary Output (When x > 1 boards):
 *     - Display welcome message with both selected algorithm names.
 *     - Display total puzzles tried (x) & max restarts allowed per search.
 *     - Display stats for BOTH algorithms: avg search cost (successors),
 *       avg restarts required, and total search failures.
 * [ ] Console Visualization Mode (Triggered when x = 1 board):
 *     - Display selected algorithm name.
 *     - Display initial board 8x8 grid ('Q' and '-'), list state, and initial h(s).
 *     - Display steps taken, restarts used, final board grid ('Q' and '-'),
 *       and final h(s) (if not solved).
 */

package main;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FinalProjSubmission {

    public static Random random = new Random();

    public static boolean[][] currentQueenMap;
    public static boolean[][] bestQueenMap;

    public static int bestQueenMapValue = Integer.MAX_VALUE;
    public static boolean solvedConflict = false;

    public static void main(String[] args){
        createNewQueenMap();

        solveQueens();
    }

    public static void solveQueens(){
        int currentQueenMapValue;

        while (!solvedConflict){

            createNewQueenMap();

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

    //TODO - this should be replaced with something other than bogo sort
    public static void createNewQueenMap(){
        currentQueenMap = new boolean[8][8];

        for (int i = 0; i < 8; i++){
            currentQueenMap[i][random.nextInt(8)] = true;
        }
    }
}