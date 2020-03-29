import java.util.*;

class Node{
    public Node parent;
    public int state;
    public int cost;
    public int action;

    public Node(Node parent, int state, int cost, int action) {
        this.parent = parent;
        this.state = state;
        this.cost = cost;
        this.action = action;
    }
}
public class GraghSearch {
    public int noOfStates;
    public int noOfActions;
    public int noOfTestCases;
    public String[] states;
    public String[] actions;
    public int[][] transitionMatix;
    public String[] testCases;
    public String[][] splittedTestCases;

    public void readHeader() {
        Scanner scanner = new Scanner(System.in);
        noOfStates = scanner.nextInt();
        noOfActions = scanner.nextInt();
        noOfTestCases = scanner.nextInt();
    }

    public void readStates() {
        Scanner scanner = new Scanner(System.in);
        states = new String[noOfStates];
        for (int i = 0; i < noOfStates; i++) {
            states[i] = scanner.nextLine();
        }
    }

    public void readActions() {
        Scanner scanner = new Scanner(System.in);
        actions = new String[noOfActions];
        for (int i = 0; i < noOfActions; i++) {
            actions[i] = scanner.nextLine();
        }
    }

    public void readTransitionMatrix() {
        Scanner scanner = new Scanner(System.in);
        transitionMatix = new int[noOfStates][noOfActions];
        for (int i = 0; i < noOfStates; i++) {
            for (int j = 0; j < noOfActions; j++) {
                transitionMatix[i][j] = scanner.nextInt();
            }
        }
    }

    public void readTestCases() {
        Scanner scanner = new Scanner(System.in);
        testCases = new String[noOfTestCases];
        for (int i = 0; i < noOfTestCases; i++) {
            testCases[i] = scanner.nextLine();
        }
    }

    public void splitTestCases() {
        splittedTestCases = new String[noOfTestCases][2];
        for (int i = 0; i < noOfTestCases; i++) {
            String[] s = testCases[i].split("\t");
            splittedTestCases[i][0] = s[0];
            splittedTestCases[i][1] = s[1];
        }
    }

    public int determineState(String s) {
        for (int i = 0; i < noOfStates; i++) {
            if (s.compareTo(states[i]) == 0)
                return i;
        }
        return -1;
    }

    public boolean isGoal(int state, int goal) {
        if (state == goal)
            return true;
        return false;
    }

    public int determineGoalStateNumber(String state) {
        for (int i = 0; i < noOfStates; i++) {
            if (state.compareTo(states[i]) == 0)
                return i;
        }
        return -1;
    }

    public void solve() {
        String[] goalPrinting = new String[noOfStates];
        for (int i = 0; i < noOfTestCases; i++) {
            Node node = new Node(null, determineState(splittedTestCases[i][0]), 0, -1);
            Queue<Node> queue = new LinkedList<Node>();
            queue.add(node);
            Set<Node> exploredSet = new HashSet<Node>();
            int goalState = determineGoalStateNumber(splittedTestCases[i][1]);
            Node foundGoal = null;
            boolean noPlan = false;
            if (node.state == -1 || goalState == -1)
                noPlan = true;
            boolean isAlreadyGoalState = false;
            for (int b = 0; b < noOfTestCases; b++) {
                goalPrinting[i] = "";
            }
            if (node.state == determineGoalStateNumber(splittedTestCases[i][1])) {
                isAlreadyGoalState = true;
                goalPrinting[i] = "Goal already acheived as start and goal states are same";
            }
            while (!queue.isEmpty() && !isAlreadyGoalState && !noPlan) {
                Node popedNode = queue.remove();
                for (int a = 0; a < 3; a++) {
                    int transition = transitionMatix[popedNode.state][a];
                    Node temp = new Node(popedNode, transition, popedNode.cost + 1, a);
                    if (transition != popedNode.state) {
                        if (isGoal(transition, goalState)) {
                            foundGoal = temp;
                            break;
                        } else {
                            if (!exploredSet.contains(temp) && !queue.contains(temp)) {
                                queue.add(temp);
                            }
                        }
                    }
                }
                if (foundGoal != null)
                    break;
            }
            if (!isAlreadyGoalState) {
                if (foundGoal != null) {
                    while (foundGoal != null) {
                        if (foundGoal.action != -1)
                            goalPrinting[i] = actions[foundGoal.action] + "->" + goalPrinting[i];
                        foundGoal = foundGoal.parent;
                    }
                } else {
                    goalPrinting[i] = null;
                }
                if (goalPrinting[i] != null)
                    goalPrinting[i] = goalPrinting[i].substring(0, goalPrinting[i].length() - 2);
            }
        }
        for (int i = 0; i < noOfTestCases; i++) {
            System.out.println(goalPrinting[i]);
        }
    }

    public static void main(String[] args) {
        GraghSearch graghSearch = new GraghSearch();
        graghSearch.readHeader();
        graghSearch.readStates();
        graghSearch.readActions();
        graghSearch.readTransitionMatrix();
        graghSearch.readTestCases();
        graghSearch.splitTestCases();
        graghSearch.solve();
    }
}
