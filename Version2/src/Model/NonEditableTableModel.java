package Version2.src.Model;

public class NonEditableTableModel extends javax.swing.table.DefaultTableModel {
    public NonEditableTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Không cho phép chỉnh sửa bất kỳ ô nào
    }
}
