package com.raven.all;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
/**
 *
 * @author ADMIN
 */
public class Inventory extends javax.swing.JPanel {

    public class DatabaseConnection {
        // Database URL, username, and password

        private static final String URL = "jdbc:mysql://localhost:3306/waterstation";
        private static final String USER = "root";
        private static final String PASSWORD = "";

        // Single instance of the connection
        private static Connection connection = null;

        // Method to establish a connection to the database
        public static Connection getConnection() {
            if (connection == null) {
                try {
                    // Register the JDBC driver
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    // Open a connection
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("Database connected successfully.");
                } catch (ClassNotFoundException e) {
                    System.out.println("MySQL JDBC Driver not found.");
                    e.printStackTrace();
                } catch (SQLException e) {
                    System.out.println("Failed to connect to the database.");
                    e.printStackTrace();
                }
            }
            return connection;
        }

        public static void closeConnection() {
            if (connection != null) {
                try {
                    connection.close();
                    connection = null;
                    System.out.println("Database connection closed.");
                } catch (SQLException e) {
                    System.out.println("Failed to close the database connection.");
                    e.printStackTrace();
                }
            }
        }
    }

    public static class NumberOnlyFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) {
                return;
            }
            if (isNumeric(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) {
                return;
            }
            if (isNumeric(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isNumeric(String text) {
            return text.matches("\\d*"); // This regex ensures only digits are allowed
        }
    }

    //Load inventory
    public Inventory() {
        initComponents();
        loadInventoryTable();
        ((AbstractDocument) Qty_field.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) Price_field.getDocument()).setDocumentFilter(new NumberOnlyFilter());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        InventoryTable = new javax.swing.JTable();
        Product_Info_Panel = new javax.swing.JPanel();
        Inventory_Label = new javax.swing.JLabel();
        InventoryCRUD_panel = new javax.swing.JPanel();
        Name_Label = new javax.swing.JLabel();
        Qty_Label = new javax.swing.JLabel();
        Price_Label = new javax.swing.JLabel();
        Add_Bttn = new javax.swing.JButton();
        ProductInfo_Label = new javax.swing.JLabel();
        Name_field = new javax.swing.JTextField();
        Qty_field = new javax.swing.JTextField();
        Price_field = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        ModifyIventoryItem = new javax.swing.JButton();
        Delete_Bttn = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        InventoryTable.setBackground(new java.awt.Color(153, 204, 255));
        InventoryTable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        InventoryTable.setForeground(new java.awt.Color(0, 74, 173));
        InventoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Name", "Stocks", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        InventoryTable.setGridColor(new java.awt.Color(0, 74, 173));
        InventoryTable.setOpaque(false);
        InventoryTable.setRowHeight(50);
        InventoryTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(InventoryTable);
        if (InventoryTable.getColumnModel().getColumnCount() > 0) {
            InventoryTable.getColumnModel().getColumn(0).setResizable(false);
            InventoryTable.getColumnModel().getColumn(1).setResizable(false);
            InventoryTable.getColumnModel().getColumn(2).setResizable(false);
            InventoryTable.getColumnModel().getColumn(3).setResizable(false);
        }

        Product_Info_Panel.setBackground(new java.awt.Color(255, 255, 255));

        Inventory_Label.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        Inventory_Label.setForeground(new java.awt.Color(0, 74, 173));
        Inventory_Label.setText("INVENTORY");

        javax.swing.GroupLayout Product_Info_PanelLayout = new javax.swing.GroupLayout(Product_Info_Panel);
        Product_Info_Panel.setLayout(Product_Info_PanelLayout);
        Product_Info_PanelLayout.setHorizontalGroup(
            Product_Info_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Product_Info_PanelLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(Inventory_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(91, 91, 91))
        );
        Product_Info_PanelLayout.setVerticalGroup(
            Product_Info_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Product_Info_PanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(Inventory_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );

        InventoryCRUD_panel.setBackground(new java.awt.Color(255, 255, 255));
        InventoryCRUD_panel.setForeground(new java.awt.Color(0, 74, 173));

        Name_Label.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Name_Label.setForeground(new java.awt.Color(0, 74, 173));
        Name_Label.setText("Name:");

        Qty_Label.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Qty_Label.setForeground(new java.awt.Color(0, 74, 173));
        Qty_Label.setText("Qty:");

        Price_Label.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Price_Label.setForeground(new java.awt.Color(0, 74, 173));
        Price_Label.setText("Price:");

        Add_Bttn.setBackground(new java.awt.Color(0, 74, 173));
        Add_Bttn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Add_Bttn.setForeground(new java.awt.Color(153, 204, 255));
        Add_Bttn.setText("Add Product");
        Add_Bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add_BttnActionPerformed(evt);
            }
        });

        ProductInfo_Label.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        ProductInfo_Label.setForeground(new java.awt.Color(0, 74, 173));
        ProductInfo_Label.setText("PRODUCT INFO");

        Name_field.setBackground(new java.awt.Color(153, 204, 255));
        Name_field.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Name_field.setForeground(new java.awt.Color(0, 74, 173));

        Qty_field.setBackground(new java.awt.Color(153, 204, 255));
        Qty_field.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Qty_field.setForeground(new java.awt.Color(0, 74, 173));

        Price_field.setBackground(new java.awt.Color(153, 204, 255));
        Price_field.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Price_field.setForeground(new java.awt.Color(0, 74, 173));

        javax.swing.GroupLayout InventoryCRUD_panelLayout = new javax.swing.GroupLayout(InventoryCRUD_panel);
        InventoryCRUD_panel.setLayout(InventoryCRUD_panelLayout);
        InventoryCRUD_panelLayout.setHorizontalGroup(
            InventoryCRUD_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryCRUD_panelLayout.createSequentialGroup()
                .addGroup(InventoryCRUD_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InventoryCRUD_panelLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(InventoryCRUD_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Qty_Label)
                            .addComponent(Name_Label))
                        .addGap(22, 22, 22)
                        .addComponent(Name_field, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(InventoryCRUD_panelLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(Price_Label)
                        .addGap(26, 26, 26)
                        .addGroup(InventoryCRUD_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Price_field, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Qty_field, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Add_Bttn)))
                    .addGroup(InventoryCRUD_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ProductInfo_Label)))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        InventoryCRUD_panelLayout.setVerticalGroup(
            InventoryCRUD_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryCRUD_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ProductInfo_Label)
                .addGap(49, 49, 49)
                .addGroup(InventoryCRUD_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Name_Label)
                    .addComponent(Name_field, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(InventoryCRUD_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Qty_Label)
                    .addComponent(Qty_field, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(InventoryCRUD_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Price_field, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Price_Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Add_Bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        ModifyIventoryItem.setBackground(new java.awt.Color(0, 74, 173));
        ModifyIventoryItem.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        ModifyIventoryItem.setForeground(new java.awt.Color(153, 204, 255));
        ModifyIventoryItem.setText("Modify");
        ModifyIventoryItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifyIventoryItemActionPerformed(evt);
            }
        });

        Delete_Bttn.setBackground(new java.awt.Color(0, 74, 173));
        Delete_Bttn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Delete_Bttn.setForeground(new java.awt.Color(153, 204, 255));
        Delete_Bttn.setText("Delete");
        Delete_Bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delete_BttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(ModifyIventoryItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addComponent(Delete_Bttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Delete_Bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ModifyIventoryItem, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Product_Info_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(InventoryCRUD_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Product_Info_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(InventoryCRUD_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 124, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // PLEASE TRY TO AVOID CLOSE CONNECTION ON EVERY METHODS ONLY PUT IF IT WONT BREAK ANYTHING
    // THIS PART SHOULD BE DONE PLEASE DO SOME TEST
    // PLEASE TRY TO AVOID CLOSE CONNECTION ON EVERY METHODS ONLY PUT IF IT WONT BREAK ANYTHING

    private void Add_BttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add_BttnActionPerformed
        String name = Name_field.getText().trim();
        String qty = Qty_field.getText().trim();
        String price = Price_field.getText().trim();

        if (name.isEmpty() || qty.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to add the product?", "Confirm Add", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                // Add to database
                int id = addProductToDatabase(name, qty, price);

                // Add to InventoryTable
                DefaultTableModel model = (DefaultTableModel) InventoryTable.getModel();
                model.addRow(new Object[]{id, name, qty, price});

                // Clear input fields
                Name_field.setText("");
                Price_field.setText("");
                Qty_field.setText("");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity or price format.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_Add_BttnActionPerformed

    private int addProductToDatabase(String name, String qty, String price) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int productId = -1;

        try {
            connection = DatabaseConnection.getConnection();

            // Get the current maximum product ID
            String getMaxIdQuery = "SELECT MAX(productid) FROM inventory";
            preparedStatement = connection.prepareStatement(getMaxIdQuery);
            resultSet = preparedStatement.executeQuery();
            int maxId = 0;
            if (resultSet.next()) {
                maxId = resultSet.getInt(1);
            }

            // Insert the new product with the next sequential ID
            String query = "INSERT INTO inventory (productid, productname, qty, price) VALUES (?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            productId = maxId + 1;
            preparedStatement.setInt(1, productId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, qty);
            preparedStatement.setString(4, price);

            preparedStatement.executeUpdate();
            System.out.println("Product added to database with ID: " + productId);
        } catch (SQLException e) {
            System.out.println("Failed to add product to database.");
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return productId;
    }

    private void Delete_BttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Delete_BttnActionPerformed
        int selectedRow = InventoryTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No product selected to remove from inventory.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel inventoryModel = (DefaultTableModel) InventoryTable.getModel();

        int id = (int) inventoryModel.getValueAt(selectedRow, 0);

        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected product?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement deleteStatement = null;
            PreparedStatement updateStatement = null;

            try {
                connection.setAutoCommit(false);

                deleteStatement = connection.prepareStatement("DELETE FROM inventory WHERE productid =?");
                deleteStatement.setInt(1, id);
                deleteStatement.executeUpdate();

                // Update the remaining product IDs to be sequential
                updateStatement = connection.prepareStatement("UPDATE inventory SET productid = productid - 1 WHERE productid >?");
                updateStatement.setInt(1, id);
                updateStatement.executeUpdate();

                connection.commit();
                loadInventoryTable(); // Reload the inventory table to reflect changes
                JOptionPane.showMessageDialog(this, "Product deleted and inventory updated.", "Inventory Updated", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                try {
                    if (connection != null) {
                        connection.rollback();
                    }
                } catch (SQLException ex) {
                    System.out.println("Failed to rollback: " + ex.getMessage());
                    ex.printStackTrace();
                }
                System.out.println("Failed to delete product: " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (deleteStatement != null) {
                    try {
                        deleteStatement.close();
                    } catch (SQLException e) {
                        System.out.println("Failed to close prepared statement: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                if (updateStatement != null) {
                    try {
                        updateStatement.close();
                    } catch (SQLException e) {
                        System.out.println("Failed to close update statement: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

        }
    }//GEN-LAST:event_Delete_BttnActionPerformed

    private void ModifyIventoryItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifyIventoryItemActionPerformed
        // Get the selected row index from the InventoryTable
        int selectedIndex = InventoryTable.getSelectedRow();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to modify.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the current values from the selected row
        int currentProductID = Integer.parseInt(InventoryTable.getValueAt(selectedIndex, 0).toString());
        String currentProductName = InventoryTable.getValueAt(selectedIndex, 1).toString();
        int currentQty = Integer.parseInt(InventoryTable.getValueAt(selectedIndex, 2).toString());
        int currentPrice = Integer.parseInt(InventoryTable.getValueAt(selectedIndex, 3).toString());

        String newProductName = currentProductName;
        int newQty = currentQty;
        int newPrice = currentPrice;

        String[] options = {"Edit Product Name", "Edit Stocks", "Edit Price"};
        int choice = JOptionPane.showOptionDialog(this, "What would you like to edit?", "Modify Inventory Item",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == -1) {
            return; // User cancelled
        }

        switch (choice) {
            case 0: // Edit Product Name
                newProductName = JOptionPane.showInputDialog(this, "Enter new product name:", currentProductName);
                if (newProductName == null || newProductName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Product name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // User cancelled or input is empty
                }
                break;

            case 1: // Edit Quantity
                String newQtyInput = JOptionPane.showInputDialog(this, "Enter new quantity:", String.valueOf(currentQty));
                try {
                    newQty = Integer.parseInt(newQtyInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                break;

            case 2: // Edit Price
                String newPriceInput = JOptionPane.showInputDialog(this, "Enter new price:", String.valueOf(currentPrice));
                try {
                    newPrice = Integer.parseInt(newPriceInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid price. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                break;
        }

        // Confirm with the user before updating the database
        int confirmUpdate = JOptionPane.showConfirmDialog(this, "Are you sure you want to update the inventory item?", "Confirm Update", JOptionPane.YES_NO_OPTION);
        if (confirmUpdate == JOptionPane.YES_OPTION) {
            try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement("UPDATE inventory SET productname =?, qty =?, price =? WHERE productid =?")) {
                pstmt.setString(1, newProductName);
                pstmt.setInt(2, newQty);
                pstmt.setInt(3, newPrice);
                pstmt.setInt(4, currentProductID);
                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated > 0) {
                    // Update the table model
                    InventoryTable.setValueAt(newProductName, selectedIndex, 1);
                    InventoryTable.setValueAt(newQty, selectedIndex, 2);
                    InventoryTable.setValueAt(newPrice, selectedIndex, 3);
                    JOptionPane.showMessageDialog(this, "Inventory item modified successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to modify inventory item.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error modifying inventory item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_ModifyIventoryItemActionPerformed

    private void loadInventoryTable() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM inventory ORDER BY productid ASC";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) InventoryTable.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                int id = resultSet.getInt("productid");
                String name = resultSet.getString("productname");
                String qty = resultSet.getString("qty");
                String price = resultSet.getString("price");

                model.addRow(new Object[]{id, name, qty, price});
            }
        } catch (SQLException e) {
            System.out.println("Failed to load inventory from database.");
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add_Bttn;
    private javax.swing.JButton Delete_Bttn;
    private javax.swing.JPanel InventoryCRUD_panel;
    private javax.swing.JTable InventoryTable;
    private javax.swing.JLabel Inventory_Label;
    private javax.swing.JButton ModifyIventoryItem;
    private javax.swing.JLabel Name_Label;
    private javax.swing.JTextField Name_field;
    private javax.swing.JLabel Price_Label;
    private javax.swing.JTextField Price_field;
    private javax.swing.JLabel ProductInfo_Label;
    private javax.swing.JPanel Product_Info_Panel;
    private javax.swing.JLabel Qty_Label;
    private javax.swing.JTextField Qty_field;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
