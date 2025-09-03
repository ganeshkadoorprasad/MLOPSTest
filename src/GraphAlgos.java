
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GraphAlgos {
    private ArrayList<Edge> graph[] = new ArrayList[7];
    private ArrayList<Edge> cyclegraph[] = new ArrayList[6];

    public GraphAlgos() {
        for (int i = 0; i < 7; i++) {
            graph[i] = new ArrayList<Edge>();
        }

        graph[0].add(new Edge(0, 1, 1));
        graph[0].add(new Edge(0, 2, 1));

        graph[1].add(new Edge(1, 0, 1));
        graph[1].add(new Edge(1, 3, 1));

        graph[2].add(new Edge(2, 0, 1));
        graph[2].add(new Edge(2, 4, 1));

        graph[3].add(new Edge(3, 1, 1));
        graph[3].add(new Edge(3, 4, 1));
        graph[3].add(new Edge(3, 5, 1));

        graph[4].add(new Edge(4, 2, 1));
        graph[4].add(new Edge(4, 3, 1));
        graph[4].add(new Edge(4, 5, 1));

        graph[5].add(new Edge(5, 3, 1));
        graph[5].add(new Edge(5, 4, 1));
        graph[5].add(new Edge(5, 6, 1));

        graph[6].add(new Edge(6, 5, 1));

        for (int i = 0; i < 6; i++) {
            cyclegraph[i] = new ArrayList<Edge>();
        }
        cyclegraph[0].add(new Edge(0, 1, 2));
        cyclegraph[0].add(new Edge(0, 2, 4));

        cyclegraph[1].add(new Edge(1, 3, 7));
        cyclegraph[1].add(new Edge(1, 2, -1));

        cyclegraph[2].add(new Edge(2, 4, 3));

        cyclegraph[3].add(new Edge(3, 5, 1));

        cyclegraph[4].add(new Edge(4, 3, 2));
        cyclegraph[4].add(new Edge(4, 5, 5));

    }

    public static void main(String args[]) {
        System.out.println("Start1");
        GraphAlgos gs = new GraphAlgos();
        boolean v[] = new boolean[7];
        // gs.dfs(gs.graph, v, 6);
        // gs.bfs(gs.graph, 7);
        gs.findAllPath(gs.graph, v, 0, "0", 5);

        boolean v1[] = new boolean[6];
        boolean recStack[] = new boolean[5];
        // boolean flag = gs.isCyclicalGraph(gs.cyclegraph, v1, 0, recStack);
        // System.out.println("graph is cyclicla " + flag);

        // gs.dijkstra(gs.cyclegraph, v1);
        // gs.bellmanFord(gs.cyclegraph, v1);
        gs.primsAlgo(gs.cyclegraph, v1);
    }

    class Pair implements Comparable<Pair> {
        int node;
        int distance;

        public Pair(int n, int d) {
            this.node = n;
            this.distance = d;
        }

        @Override
        public int compareTo(Pair p2) {
            return this.distance - p2.distance;
        }
    }

    private void primsAlgo(ArrayList<Edge>[] graph2, boolean[] visited) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(0, 0));
        int dist[] = new int[visited.length];
        for (int i = 0; i < visited.length; i++) {
            dist[i] = 0;
        }
        dist[0] = 0;
        int finalcost = 0;
        while (!pq.isEmpty()) {
            Pair curr = pq.remove();

            if (visited[curr.node] == false) {
                visited[curr.node] = true;

                finalcost += curr.distance;
                // dist[curr.node] += curr.distance;
                dist[curr.node] += finalcost;

                for (int i = 0; i < graph2[curr.node].size(); i++) {
                    Edge edge = graph2[curr.node].get(i);
                    // if (visited[edge.dest] == false) {
                    pq.add(new Pair(edge.dest, edge.weight));
                    // }
                }
            }
        }
        System.out.println("finalcost" + finalcost);
        for (int i = 0; i < dist.length; i++) {
            System.out.println("node " + i + " distance" + dist[i]);
        }
    }

    private void dijkstra(ArrayList<Edge>[] graph2, boolean[] visited) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(0, 0));
        int dist[] = new int[visited.length];
        for (int i = 0; i < visited.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[0] = 0;

        while (!pq.isEmpty()) {
            Pair curr = pq.remove();
            if (visited[curr.node] == false) {
                visited[curr.node] = true;
                for (int i = 0; i < graph2[curr.node].size(); i++) {
                    Edge edge = graph2[curr.node].get(i);
                    if (dist[edge.dest] > dist[edge.src] + edge.weight) { // relaxation
                        dist[edge.dest] = dist[edge.src] + edge.weight;
                        pq.add(new Pair(edge.dest, dist[edge.dest]));
                    }

                }
            }
        }
        for (int i = 0; i < dist.length; i++) {
            System.out.println("node " + i + " distance" + dist[i]);
        }
    }

    private void bellmanFord(ArrayList<Edge>[] graph2, boolean[] visited) {

        int dist[] = new int[visited.length];
        for (int i = 0; i < visited.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[0] = 0;
        for (int k = 0; k < visited.length - 1; k++) {
            System.out.println("inside k loop");
            for (int i = 0; i < visited.length; i++) {
                // if (visited[i] == false) {
                // visited[i] = true;
                System.out.println("inside i loop");
                for (int j = 0; j < graph2[i].size(); j++) {
                    Edge edge = graph2[i].get(j);
                    System.out.println("edge.dest " + edge.dest + "dist[edge.dest] " + dist[edge.dest]);
                    if (dist[edge.src] != Integer.MAX_VALUE && dist[edge.dest] > dist[edge.src] + edge.weight) { // relaxation
                        dist[edge.dest] = dist[edge.src] + edge.weight;
                        System.out.println("edge.dest " + edge.dest + "dist[edge.dest] " + dist[edge.dest]);
                    }

                }
                // }
            }
        }
        for (int i = 0; i < dist.length; i++) {
            System.out.println("node " + i + " distance" + dist[i]);
        }
    }

    private boolean isCyclicalGraph(ArrayList<Edge>[] graph2, boolean[] v, int curr, boolean[] recStack) {
        v[curr] = true;
        recStack[curr] = true;

        for (int i = 0; i < graph2[curr].size(); i++) {
            Edge edge = graph2[curr].get(i);
            if (recStack[edge.dest] == true) {
                return true;
            } else if (v[edge.dest] == false) {
                if (isCyclicalGraph(graph2, v, edge.dest, recStack)) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return recStack[curr] = false;
    }

    public void dfs(ArrayList<Edge> graph[], boolean v[], int curr) {
        System.out.print(curr + " ");
        v[curr] = true;
        for (int i = 0; i < graph[curr].size(); i++) {
            Edge edge = graph[curr].get(i);
            if (v[edge.dest] == false) {

                dfs(graph, v, edge.dest);
            }
        }

    }

    public void bfs(ArrayList<Edge> graph[], int v) {
        Queue<Integer> q = new LinkedList<>();
        boolean vis[] = new boolean[v];
        q.add(0);
        while (!q.isEmpty()) {
            int curr = q.remove();
            if (vis[curr] == false) {
                System.out.print(curr + " ");
                vis[curr] = true;
                for (int i = 0; i < graph[curr].size(); i++) {
                    Edge edge = graph[curr].get(i);
                    q.add(edge.dest);
                }
            }
        }

    }

    public void findAllPath(ArrayList<Edge> graph[], boolean v[], int curr, String path, int tar) {
        if (curr == tar) {
            System.out.println(path + " ");
            return;
        }

        for (int i = 0; i < graph[curr].size(); i++) {
            Edge edge = graph[curr].get(i);
            if (v[edge.dest] == false) {
                v[curr] = true;
                findAllPath(graph, v, edge.dest, path + edge.dest, tar);
                v[curr] = false;
            }
        }

    }
}
