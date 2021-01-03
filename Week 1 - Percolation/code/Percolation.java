import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF quickUnionUF;
    private final int virtualBottom;
    private final boolean[] open;
    private final int rowSize;
    private int openSitesCount = 0;

    public Percolation(int n) {
        rowSize = n;
        open = new boolean[n * n];
        virtualBottom = n * n;
        quickUnionUF = new WeightedQuickUnionUF(rowSize * rowSize + 1);
        // union top row
        for (int i = 0; i < n; i++) {
            quickUnionUF.union(0, i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int site = coordsToSite(row, col);
        unionNeighbours(row, col);
        open[site] = true;
        openSitesCount += 1;
    }

    private void unionNeighbours(int row, int col) {
        int site = coordsToSite(row, col);
        if (isLastRow(row)) {
            quickUnionUF.union(site, virtualBottom);
        }

        int topNeighbour = site - rowSize;
        if (isSiteExists(topNeighbour) && isOpen(topNeighbour)) {
            quickUnionUF.union(site, topNeighbour);
        }

        int rightNeighbour = site + 1;
        if (isSiteExists(rightNeighbour) && checkIfSitesOnSameLine(site, rightNeighbour) && isOpen(rightNeighbour)) {
            quickUnionUF.union(site, rightNeighbour);
        }

        int bottomNeighbour = site + rowSize;
        if (isSiteExists(bottomNeighbour) && isOpen(bottomNeighbour)) {
            quickUnionUF.union(site, bottomNeighbour);
        }

        int leftNeighbour = site - 1;
        if (isSiteExists(leftNeighbour) && checkIfSitesOnSameLine(site, leftNeighbour) && isOpen(leftNeighbour)) {
            quickUnionUF.union(site, leftNeighbour);
        }
    }

    private boolean checkIfSitesOnSameLine(int p, int q) {
        return q / rowSize == p / rowSize;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return isOpen(coordsToSite(row, col));
    }

    private boolean isOpen(int site) {
        return open[site];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isFull(coordsToSite(row, col));
    }

    private boolean isFull(int site) {
        return isOpen(site) && quickUnionUF.find(0) == quickUnionUF.find(site);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.find(0) == quickUnionUF.find(virtualBottom);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > rowSize || col < 1 || col > rowSize) {
            throw new IllegalArgumentException(
                    String.format("Invalid site's coords: [%d, %d]. Matrix size: %dx%d", row, col, rowSize, rowSize)
            );
        }
    }

    private int coordsToSite(int row, int col) {
        return rowSize * (row - 1) + col - 1;
    }

    private boolean isLastRow(int row) {
        return row == rowSize;
    }

    private boolean isSiteExists(int site) {
        return site >= 0 && site < rowSize * rowSize;
    }

    public static void main(String[] args) {

    }
}
