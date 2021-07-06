package auctionsniper.ui;

import auctionsniper.SniperListener;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;

import static auctionsniper.SniperState.JOINING;

public class SnipersTableModel extends AbstractTableModel implements SniperListener {
    private static String[] STATUS_TEXT = {"Joining", "Bidding", "Winning", "Lost", "Won"};
//    private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, JOINING);
//    private SniperSnapshot sniperSnapshot = STARTING_UP;
    private ArrayList<SniperSnapshot> snapshots = new ArrayList<>();

    public int getColumnCount() {
        return Column.values().length;
    }

    public int getRowCount() {
        return snapshots.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return Column.at(columnIndex).valueIn(snapshots.get(rowIndex));
    }

    @Override
    public String getColumnName(int column) {
        return Column.at(column).name;
    }

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }

    public void addSniper(SniperSnapshot snapshot) {
        int row = snapshots.size();
        snapshots.add(snapshot);
        fireTableRowsInserted(row, row);
    }

    @Override
    public void sniperStateChanged(SniperSnapshot newSnapshot) {
        for (int i = 0; i < snapshots.size(); i++) {
            if (newSnapshot.isForSameItemAs(snapshots.get(i))) {
                snapshots.set(i, newSnapshot);
                fireTableRowsUpdated(i, i);
                return;
            }
        }
    }
}