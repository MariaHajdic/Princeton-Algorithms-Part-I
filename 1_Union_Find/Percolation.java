import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * 
 * 
 * 
 */

public class Percolation {
    private static final byte OPEN = 1 << 2; // [1,0,0]
    private static final byte CONNECTED_TO_TOP = 1 << 1; // [0,1,0]
    private static final byte CONNECTED_TO_BOTTOM = 1; // [0,0,1]
    private static final byte CONDUCTOR = CONNECTED_TO_TOP | CONNECTED_TO_BOTTOM | OPEN; //[1,1,1]
    private WeightedQuickUnionUF UF;
    private byte[] sitesState;
    private int[] setRoot;
    private int[] setSize;
    private int n;
    private int openSites;
    private boolean systemPercolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();
        this.n = n;
        this.UF = new WeightedQuickUnionUF(n*n);
        this.sitesState = new byte[n*n];
        this.setRoot = new int[n*n];
        this.setSize = new int[n*n];
        for (int i = 0; i < n * n; i++) {
            setRoot[i] = i;
            setSize[i] = 1;
        }
    }

    private int position(int row, int col) {
        return (row - 1) * n + col - 1;
    }

    private int root(int i) {
        while (i != setRoot[i]) {
            setRoot[i] = setRoot[setRoot[i]];
            i = setRoot[i];
        }
        return i;
    }

    private boolean validCoordinate(int row, int col) {
        return !(row < 1 || row > n || col < 1 || col > n);
    }

    public void open(int row, int col) {
        if (!validCoordinate(row, col)) throw new IllegalArgumentException();
        if (isOpen(row, col)) return;

        int idx = position(row, col);

        sitesState[idx] |= OPEN; // 100
        if (row == 1) sitesState[idx] |= CONNECTED_TO_TOP; // 110
        if (row == n) sitesState[idx] |= CONNECTED_TO_BOTTOM; // 101

        if (row > 1) union(idx, idx-n); // above
        if (col < n) union(idx, idx+1); // right
        if (col > 1) union(idx, idx-1); // left
        if (row < n) union(idx, idx+n); // below

        openSites++;
        UF.find(idx);
        systemPercolates |= sitesState[root(idx)] == CONDUCTOR;
    }

    private void union(int p, int q) {
        if ((sitesState[q] & OPEN) == 0) return;
        UF.union(p, q);
        int firstRoot = root(p);
        int secondRoot = root(q);
        if (firstRoot == secondRoot) return;

        if (setSize[firstRoot] < setSize[secondRoot]) {
            setRoot[firstRoot] = secondRoot;
            setSize[secondRoot] += setSize[firstRoot];
        } else {
            setRoot[secondRoot] = firstRoot;
            setSize[firstRoot] += setSize[secondRoot];
        }

        sitesState[firstRoot] |= sitesState[secondRoot];
        sitesState[secondRoot] |= sitesState[firstRoot];
    }

    public boolean isOpen(int row, int col) {
        if (!validCoordinate(row, col)) throw new IllegalArgumentException();
        return (sitesState[position(row, col)] & OPEN) != 0;
    }

    public boolean isFull(int row, int col) {
        if (!validCoordinate(row, col)) throw new IllegalArgumentException();
        UF.find(position(row, col));
        return (sitesState[root(position(row, col))] & CONNECTED_TO_TOP) != 0;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return systemPercolates;
    }
}