import java.util.*;
//-----------------------------------------------------
//Title: Main Class
//Author: Ayda Nil Özyürek
//Description: This class uses the kruskal algorithm to output 
//             a single integer expressing the minimum 
//             cost of good trip in a grid structure.
//------------------------------------------------

public class Main {

	static int[] parent;
	static int[] rank;
	static int[][] grid;

	// ------------------------------------------------
	// Used Comparable because this makes it simpler for us to locate the edges
	// with the lowest weight and put them in the minimum spanning tree.
	// (Collection.sort())
	// ------------------------------------------------
	static class Edge implements Comparable<Edge> {
		int u, v, w, time;

		public Edge(int u, int v, int w) {

			this.time = 1; // In order to keep track of how frequently an edge is being used, we use the
							// times variable.
			this.u = u;
			this.v = v;
			this.w = w;

		}

		@Override
		public int compareTo(Edge other) {
			return Integer.compare(w, other.w);

		}

	}

	// ------------------------------------------------
	// The implementation of the Kruskal's algorithm merges two disjoint collections
	// of vertices using the union method. It accepts two vertex indices, u and v,
	// and uses the findParent function to locate each of their respective parent
	// nodes first.
	// ------------------------------------------------
	static int union(int u, int v) {

		int rootU = find(u);
		int rootV = find(v);

		if (rootU == rootV) {
			return -1;
		}

		if (rank[rootU] < rank[rootV]) {
			parent[rootU] = rootV;
		} else if (rank[rootU] > rank[rootV]) {
			parent[rootV] = rootU;
		} else {
			parent[rootU] = rootV;
			rank[rootV]++;
		}
		return 1;
	}

	// ------------------------------------------------
	// The find operation of the find-union algorithm, which returns
	// the root element in the set to which u belongs, is implemented by this
	// function.
	// ------------------------------------------------
	static int find(int u) {
		while (parent[u] != u) {
			u = parent[u];
		}
		return u;
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int numTest = scanner.nextInt(); // get the number of tests from user

		for (int t = 0; t < numTest; t++) {

			int N = scanner.nextInt(); // number of rows
			int M = scanner.nextInt(); // number of columns

			int r1 = scanner.nextInt() - 1; // row-starting cell
			int c1 = scanner.nextInt() - 1; // column-starting cell

			int r2 = scanner.nextInt() - 1; // row-ending cell
			int c2 = scanner.nextInt() - 1; // column- ending cell

			parent = new int[N * M]; // Constructs an array with a capacity of N * M to hold the parents of all grid
										// nodes. Each node has its own connected component at first since each node
										// is its own parent.

			rank = new int[N * M]; // constructs an array of N * M dimensions to hold the rank of each grid node.
									// Each node starts out with a rank of zero.

			grid = new int[N][M]; // N by M size two-dimensional array to hold the values for the grid.

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					grid[i][j] = scanner.nextInt();

					int id = i * M + j; // Determines the current node in the grid's unique identification. The
										// node's location in the grid is represented by the identifier, which is an
										// unique integer.

					rank[id] = 0; // initializes the rank of the current node to zero
					parent[id] = id; // initializes the parent of the current node to itself

				}
			}

			// Adding all the edges to the list
			ArrayList<Edge> edges = new ArrayList<>();
			// ------------------------------------------------
			// By considering each grid cell as a node and connecting nearby cells with
			// weighted edges, this algorithm creates a graph. Each edge's weight is
			// determined by performing an XOR operation on the values of the two cells it
			// connects.
			// ------------------------------------------------
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {

					if (j < M - 1) {
						int id1 = i * M + j;
						int id2 = i * M + j + 1;
						int w = grid[i][j] ^ grid[i][j + 1];
						edges.add(new Edge(id1, id2, w));
					}

					if (i < N - 1) {
						int id1 = i * M + j;
						int id2 = (i + 1) * M + j;
						int w = grid[i][j] ^ grid[i + 1][j];
						edges.add(new Edge(id1, id2, w));
					}
				}
			}
			Collections.sort(edges);

			// Kruskal's algorithm
			int weight = 0;

			for (Edge edge : edges) {

				int u = edge.u;
				int v = edge.v;
				int w = edge.w;

				weight += (w * ((edge.time + 1) / 2));
				int d = union(u, v);
				if (d == -1)
					weight -= (w * ((edge.time + 1) / 2));
			}

			System.out.println(weight);
		}
	}
}
