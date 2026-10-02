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

public class FinalProjSubmission {
}
