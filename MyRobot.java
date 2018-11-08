// Feel free to use this java file as a template and extend it to write your solver.
// ---------------------------------------------------------------------------------

import world.Robot;
import world.World;

import java.awt.*;
import java.util.*;

public class MyRobot extends Robot {
    private World world;
    boolean isUncertain;
    Point start;
    Point end;
    
    /* Function to determine the heuristic cost of map */
    public int[][] heuristify(int r, int c){
            int h[][] = new int[r][c];
            //System.out.println("Heuristic: ");
            for(int i = 0; i < r; i++) {
                for(int j = 0; j < c; j++) {
                    Point temp = new Point(i, j);
                    h[i][j] = Math.abs((int) end.getX() - i) + Math.abs((int) end.getY() - j);
                    String assoc = super.pingMap(temp);
                    if(assoc.equals("X"))
                        h[i][j] = -1;
                    if(isUncertain) {
                        //pingMap like hundreds of times and get higher prob
                        int xcount = 0, ocount = 0;
                        if(assoc.equals("X"))
                            xcount++;
                        else
                            ocount++;
                        for(int k = 0; k < r * c; k++) { //times to ping will depend on map size WHOOOO IT WORKS
                            assoc = super.pingMap(temp);
                            if(assoc.equals("X"))
                                xcount++;
                            else
                                ocount++;
                        }
                        if(xcount > ocount) {
                            h[i][j] = -1;
                        }
                        else {
                            h[i][j] = Math.abs((int) end.getX() - i) + Math.abs((int) end.getY() - j);
                        }
                    }
                    //System.out.print(h[i][j] + "\t");
                }
                //System.out.println();
            }
            return h;
    }
    
    /* Finding cost of shortest path, K-Nearest Neighbor Alg */
    public int[][] calcCost(int[][] h, int r, int c){
            int cost[][] = new int[r][c];
            cost[(int) start.getX()][(int) start.getY()] = 0;
            Queue<Point> open = new LinkedList<Point>();
            boolean[][] visited = new boolean[r][c];
            open.add(start);
            while(!open.isEmpty()) {
                Point curr = open.poll();
                int x = (int) curr.getX();
                int y = (int) curr.getY();
                int val = cost[x][y] + 1;
                visited[x][y] = true;
                Point neighbor;
                if(x - 1 >= 0){
                    neighbor = new Point(x - 1, y);
                    if(val < cost[x - 1][y] || cost[x - 1][y] == 0) {
                        cost[x - 1][y] = val;
                        if(h[x - 1][y] == -1) {
                            cost[x - 1][y] = -1;
                            visited[x - 1][y] = true;
                    }
                    }
                    if(!visited[x - 1][y] && !open.contains(neighbor))
                        open.add(neighbor);
                    if(y - 1 >= 0) {
                        neighbor = new Point(x - 1, y - 1);
                        if(val < cost[x - 1][y - 1] || cost[x - 1][y - 1] == 0) {
                            cost[x - 1][y - 1] = val;
                            if(h[x - 1][y - 1] == -1) {
                                cost[x - 1][y - 1] = -1;
                                visited[x - 1][y - 1] = true;
                            }
                        }
                        if(!visited[x - 1][y - 1] && !open.contains(neighbor))
                            open.add(neighbor);
                    }
                    if(y + 1 < c) {
                        neighbor = new Point(x - 1, y + 1);
                        if(val < cost[x - 1][y + 1] || cost[x - 1][y + 1] == 0) {
                            cost[x - 1][y + 1] = val;
                            if(h[x - 1][y + 1] == -1) {
                                cost[x - 1][y + 1] = -1;
                                visited[x - 1][y + 1] = true;
                            }
                        }
                        if(!visited[x - 1][y + 1] && !open.contains(neighbor))
                            open.add(neighbor);
                    }
                }
                if(y - 1 >= 0) {
                    neighbor = new Point(x, y - 1);
                    if(val < cost[x][y - 1] || cost[x][y - 1] == 0) {
                        cost[x][y - 1] = val;
                        if(h[x][y - 1] == -1) {
                            cost[x][y - 1] = -1;
                            visited[x][y - 1] = true;
                        }
                    }
                    if(!visited[x][y - 1] && !open.contains(neighbor))
                        open.add(neighbor);
                }
                if(y + 1 < c) {
                    neighbor = new Point(x, y + 1);
                    if(val < cost[x][y + 1] || cost[x][y + 1] == 0) {
                        cost[x][y + 1] = val;
                        if(h[x][y + 1] == -1) {
                            cost[x][y + 1] = -1;
                            visited[x][y + 1] = true;
                    }
                    }
                    if(!visited[x][y + 1] && !open.contains(neighbor))
                        open.add(neighbor);
                }
                if(x + 1 < r) {
                    neighbor = new Point(x + 1, y);
                    if(val < cost[x + 1][y] || cost[x + 1][y] == 0) {
                        cost[x + 1][y] = val;
                        if(h[x + 1][y] == -1) {
                            cost[x + 1][y] = -1;
                            visited[x + 1][y] = true;
                    }
                    }
                    if(!visited[x + 1][y] && !open.contains(neighbor))
                        open.add(neighbor);
                    if(y - 1 >= 0) {
                        neighbor = new Point(x + 1, y - 1);
                        if(val < cost[x + 1][y - 1] || cost[x + 1][y - 1] == 0) {
                            cost[x + 1][y - 1] = val;
                            if(h[x + 1][y - 1] == -1) {
                                cost[x + 1][y - 1] = -1;
                                visited[x + 1][y - 1] = true;
                            }
                        }
                        if(!visited[x + 1][y - 1] && !open.contains(neighbor))
                            open.add(neighbor);
                    }
                    if(y + 1 < c) {
                        neighbor = new Point(x + 1, y + 1);
                        if(val < cost[x + 1][y + 1] || cost[x + 1][y + 1] == 0) {
                            cost[x + 1][y + 1] = val;
                            if(h[x + 1][y + 1] == -1) {
                                cost[x + 1][y + 1] = -1;
                                visited[x + 1][y + 1] = true;
                            }
                        }
                        if(!visited[x + 1][y + 1] && !open.contains(neighbor))
                            open.add(neighbor);
                    }
                }
            }
            cost[(int) start.getX()][(int) start.getY()] = 0;
            /*System.out.println("Cost of Path: ");
            for(int i = 0; i < r; i++) {
                for(int j = 0; j < c; j++) {
                    System.out.print(cost[i][j] + "\t");
                }
                System.out.println();
            }*/
            return cost;
    }
    
    /* Function to add admissible heuristic and cost of best path to current place */
    public int[] matrixAdd(int[][] a, int[] c, int row, int col) {
        int[][] res = new int[row][col];
        for(int i = 0; i<row; i++) {
            for (int j=0; j<col, j++){
                if(a[i][j]!=-1){
                    res[i][j]=a[i][j]+b[i][j];
                }
                else {
                    res[i][j]=-1;
                }
            }
        }
        return res;
    }
    public int[][] addHC(int[][] h, int[][] c, int rows, int cols){
            int[][] fin = new int[rows][cols];
            //System.out.println("Final Cost:");
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < cols; j++) {
                    if(h[i][j] != -1)   
                        fin[i][j] = h[i][j] + c[i][j];
                    else
                        fin[i][j] = -1;
                    //System.out.print("(" + i + ", " + j + ") = " + fin[i][j] + "\t");
                }
                //System.out.println();
            }
            return fin;
    }
    
    /* Function to get all adjacent neighbors that aren't blocks or already in the ArrayList passed in */
    public ArrayList<Point> getNeighbors(ArrayList<Point> n, Point p, int[][] f, int r, int c) {
            int x = (int) p.getX();
            int y = (int) p.getY();
            Point temp;
            if(y - 1 >= 0){
                if(x + 1 < r) {
                    temp = new Point(x + 1, y - 1);
                    if(!n.contains(temp) && f[x + 1][y - 1] != -1)
                        n.add(temp);
                }
                temp = new Point(x, y - 1);
                if(!n.contains(temp) && f[x][y - 1] != -1)
                    n.add(temp);
                if(x - 1 >= 0) {
                    temp = new Point(x - 1, y - 1);
                    if(!n.contains(temp) && f[x - 1][y - 1] != -1)
                        n.add(temp);
                }
            }
            if(x - 1 >= 0) {
                temp = new Point(x - 1, y);
                if(!n.contains(temp) && f[x - 1][y] != -1)
                    n.add(temp);
            }
            if(x + 1 < r) {
                temp = new Point(x + 1, y);
                if(!n.contains(temp) && f[x + 1][y] != -1)
                    n.add(temp);
            }
            if(y + 1 < c) {
                if(x - 1 >= 0) {
                    temp = new Point(x - 1, y + 1);
                    if(!n.contains(temp) && f[x - 1][y + 1] != -1)
                        n.add(temp);
                }
                if(x + 1 < r) {
                    temp = new Point(x + 1, y + 1);
                    if(!n.contains(temp) && f[x + 1][y + 1] != -1)
                        n.add(temp);
                }
                temp = new Point(x, y + 1);
                if(!n.contains(temp) && f[x][y + 1] != -1)
                    n.add(temp);
            }
            return n;
    }
    
    /* Function to calculate the shortest path from start to destination */
    public Stack<Point> shortestPath(int[][] f, int r, int c) {
            Stack<Point> path = new Stack<Point>();
            ArrayList<Point> neighbors = new ArrayList<Point>();
            Point prev = new Point(-1, -1);
            path.push(start);
            while(true) {
                Point curr = path.peek();
                //System.out.print(curr.getX() + ", " + curr.getY());
                if(curr.equals(end))
                    return path;
                ArrayList<Point> adjNeigh = new ArrayList<Point>();
                adjNeigh = getNeighbors(adjNeigh, curr, f, r, c);
                int min = Integer.MAX_VALUE;
                ArrayList<Point> minN = new ArrayList<Point>();
                if(!prev.equals(curr) || curr.equals(start)) {
                    if(curr.equals(start)) {
                        neighbors.clear();
                    }
                    for(Point p : adjNeigh) {
                        if(f[(int) p.getX()][(int) p.getY()] <= min && !neighbors.contains(p) && !path.contains(p)) {
                            min = f[(int) p.getX()][(int) p.getY()];
                            minN.add(p);
                        }
                    }
                    for(Iterator<Point> it = minN.iterator(); it.hasNext();) {
                        Point m = it.next();
                        if(f[(int) m.getX()][(int) m.getY()] > min) {
                            it.remove();
                        }
                    }
                    //System.out.print( " with mins " + minN.toString());
                    if(minN.isEmpty()) {
                        f[(int) curr.getX()][(int) curr.getY()] = -1;
                        path.pop();
                        prev = path.peek();
                        neighbors.remove(curr);
                        //System.out.println(" just popped!");
                    }
                    else {
                        neighbors = getNeighbors(neighbors, curr, f, r, c);
                        neighbors.remove(prev);
                        prev = curr;
                        neighbors.remove(minN.get(0));
                        path.push(minN.get(0));
                        //System.out.println( " with neighbors " + neighbors.toString());
                    }
                }
                /* Previous Point popped */
                else {
                    Point temp = path.pop();
                    Point actPrev = path.peek();
                    path.push(temp);
                    ArrayList<Point> prevAdj = new ArrayList<Point>();
                    prevAdj = getNeighbors(prevAdj, actPrev, f, r, c);
                    adjNeigh.remove(actPrev);
                    for(Point p : prevAdj) {
                        if(adjNeigh.contains(p))
                            adjNeigh.remove(p);
                    }
                    int ind = neighbors.size() - 1;
                    prevAdj.clear();
                    for(Point p : adjNeigh) {
                        Point check = neighbors.get(ind);
                        if(adjNeigh.contains(check)) {
                            prevAdj.add(check);

                        }
                    }
                    for(Point p : prevAdj) {
                        if(f[(int) p.getX()][(int) p.getY()] <= min) {
                            min = f[(int) p.getX()][(int) p.getY()];
                            minN.add(p);
                        }
                    }
                    for(Iterator<Point> it = minN.iterator(); it.hasNext();) {
                        Point m = it.next();
                        if(f[(int) m.getX()][(int) m.getY()] > min) {
                            it.remove();
                        }
                    }
                    if(minN.isEmpty()) {
                        f[(int) curr.getX()][(int) curr.getY()] = -1;
                        path.pop();
                        prev = path.peek();
                        neighbors.remove(curr);
                        //System.out.println(" just popped 2!");
                    }
                    else {
                        neighbors.remove(prev);
                        prev = curr;
                        neighbors.remove(minN.get(0));
                        path.push(minN.get(0));
                        //System.out.println( " with neighbors " + neighbors.toString());
                    }
                }
            }
            //return path;
    }
    
    /* Simple function to reverse the path Stack into proper order */
    public Stack<Point> inverse(Stack<Point> in){
            Stack<Point> p = new Stack<Point>();
            while(!in.isEmpty()) {
                Point temp = in.pop();
                if(!temp.equals(start))
                    p.push(temp);
            }
            return p;
    }
    
    @Override
    public void travelToDestination() {
            long startTime = System.currentTimeMillis();
            int cols = world.numCols();
            int rows = world.numRows();
            int heuristic[][] = heuristify(rows, cols);
            int cost[][] = calcCost(heuristic, rows, cols);
            int finalCost[][] = matrixAdd(heuristic, cost, rows, cols);
            Stack<Point> path = shortestPath(finalCost, rows, cols);
            path = inverse(path);
            while(!path.isEmpty()) {
                Point temp = path.pop();
                if(temp.equals(end)) {
                    long endTime = System.currentTimeMillis();
                    System.out.println("Total elapsed time: " + (endTime - startTime) + " ms");
                }
                this.move(temp);
                //System.out.println(temp.toString());
            }
    }

    @Override
    public void addToWorld(World w) {
        isUncertain = w.getUncertain();
        super.addToWorld(w);
        world = w;
        start = w.getStartPos();
        end = w.getEndPos();
    }

    public static void main(String[] args) {
        try {
            World myWorld = new World("TestCases/myInputFile2.txt", true);
            
            MyRobot robot = new MyRobot();
            robot.addToWorld(myWorld);
            myWorld.createGUI(400, 400, 200); // uncomment this and create a GUI; the last parameter is delay in msecs
            
            robot.travelToDestination();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}