
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
/**
 *
 * @author ADMIN
 */
public class Sales extends javax.swing.JPanel {
    

    /**
     * Creates new form Sales
     */
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

        // Method to close the connection (if needed)
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

        // Method to manually reconnect if needed
        public static void reconnect() {
            closeConnection();
            getConnection();
        }
    }

    private void loadCartTable() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM carttable";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) CartTable.getModel();

            while (resultSet.next()) {
                String name = resultSet.getString("productname");
                String qty = resultSet.getString("qty");
                String price = resultSet.getString("price");

                model.addRow(new Object[]{name, qty, price});
            }
        } catch (SQLException e) {
            System.out.println("Failed to load cart items from database.");
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
    }

    public Sales() {
        initComponents();
        loadProductTable();
        loadCartTable();

        // Add a TableModelListener to the CartTable model
        CartTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                updateReceiptTextArea();
            }
        });
    }

    public void updateProductTableQuantity(String productName, int newQty) {
        //FOR MODIFY BUTTON
        //THIS IS STILL BROKEN AT THE MOMENT PLEASE FIX 
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement("UPDATE productTable SET quantity =? WHERE productName =?")) {
            pstmt.setInt(1, newQty);
            pstmt.setString(2, productName);
            pstmt.executeUpdate();
            System.out.println("Quantity updated successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to update the quantity.");
            e.printStackTrace();
        }
    }
    
    private void updateReceiptTextArea() {
        // FOR RECEIPT TEXT AREA ON THE SALES TABLE
        //CREATE A TABLE FOR RECEIPT 
        //FOR BEING ABLE TO RECORD
        DefaultTableModel cartModel = (DefaultTableModel) CartTable.getModel();

        // Calculate the total price
        double totalPrice = 0.0;
        StringBuilder receiptText = new StringBuilder("Receipt:\n");

        for (int i = 0; i < cartModel.getRowCount(); i++) {
            String productName = cartModel.getValueAt(i, 0).toString();
            int qty = 0;
            double price = 0.0;

            try {
                price = Double.parseDouble(cartModel.getValueAt(i, 2).toString());
                qty = Integer.parseInt(cartModel.getValueAt(i, 1).toString());
            } catch (NumberFormatException e) {
                // Handle error, but for now, just skip this item
                continue;
            }

            double subtotal = price * qty;
            totalPrice += subtotal;

            receiptText.append(String.format("%s x %d = ₱%.2f\n", productName, qty, subtotal));
        }

        receiptText.append(String.format("Total Price: ₱%.2f", totalPrice));

        // Update the ReceiptTextArea
        ReceiptTextArea.setText(receiptText.toString());
    }

    private void loadProductTable() {
        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
        // Clear existing data in the table
        model.setRowCount(0);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = Inventory.DatabaseConnection.getConnection();
            String query = "SELECT * FROM inventory";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

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
        } finally {
            // Close database resources
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ProductTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        ReceiptTextArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        CheckOutButton1 = new javax.swing.JButton();
        AddDiscountButton = new javax.swing.JButton();
        RemoveFromCartButton = new javax.swing.JButton();
        ClearCartButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        SelectCustomerComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        ModifyCartItemPriceButton = new javax.swing.JButton();
        AddToCartButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        CartTable = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1094, 720));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        ProductTable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ProductTable.setModel(new javax.swing.table.DefaultTableModel(
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
        ProductTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(ProductTable);
        if (ProductTable.getColumnModel().getColumnCount() > 0) {
            ProductTable.getColumnModel().getColumn(0).setResizable(false);
            ProductTable.getColumnModel().getColumn(1).setResizable(false);
            ProductTable.getColumnModel().getColumn(2).setResizable(false);
            ProductTable.getColumnModel().getColumn(3).setResizable(false);
        }

        ReceiptTextArea.setEditable(false);
        ReceiptTextArea.setBackground(new java.awt.Color(204, 204, 204));
        ReceiptTextArea.setColumns(20);
        ReceiptTextArea.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ReceiptTextArea.setForeground(new java.awt.Color(0, 0, 0));
        ReceiptTextArea.setRows(5);
        jScrollPane2.setViewportView(ReceiptTextArea);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("AVAILABLE PRODUCT");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jLabel1);

        CheckOutButton1.setText("Check Out");
        CheckOutButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        CheckOutButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckOutButton1ActionPerformed(evt);
            }
        });

        AddDiscountButton.setText("Discount");
        AddDiscountButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        AddDiscountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddDiscountButtonActionPerformed(evt);
            }
        });

        RemoveFromCartButton.setText("Remove");
        RemoveFromCartButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        RemoveFromCartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveFromCartButtonActionPerformed(evt);
            }
        });

        ClearCartButton.setText("Clear Cart");
        ClearCartButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ClearCartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearCartButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(ClearCartButton, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(RemoveFromCartButton, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AddDiscountButton, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CheckOutButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addGap(88, 88, 88))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AddDiscountButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ClearCartButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                    .addComponent(RemoveFromCartButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CheckOutButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));

        SelectCustomerComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        SelectCustomerComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectCustomerComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Select Customer");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(51, 51, 51))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(SelectCustomerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(SelectCustomerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(153, 153, 153));

        ModifyCartItemPriceButton.setText("Modify Cart Item");
        ModifyCartItemPriceButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ModifyCartItemPriceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifyCartItemPriceButtonActionPerformed(evt);
            }
        });

        AddToCartButton.setText("Add To Cart");
        AddToCartButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        AddToCartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddToCartButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AddToCartButton, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addComponent(ModifyCartItemPriceButton, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(AddToCartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(ModifyCartItemPriceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        CartTable.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        CartTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Quantity", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        CartTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(CartTable);
        if (CartTable.getColumnModel().getColumnCount() > 0) {
            CartTable.getColumnModel().getColumn(0).setResizable(false);
            CartTable.getColumnModel().getColumn(1).setResizable(false);
            CartTable.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void ClearCartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearCartButtonActionPerformed
        int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the cart?", "Clear Cart", JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            DefaultTableModel cartModel = (DefaultTableModel) CartTable.getModel();
            cartModel.setRowCount(0); // Clear the cart table

            // Update the carttable database
            Connection connection = null;
            PreparedStatement deleteStatement = null;

            try {
                connection = DatabaseConnection.getConnection();
                if (connection != null) {
                    String deleteQuery = "DELETE FROM carttable";
                    deleteStatement = connection.prepareStatement(deleteQuery);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Cart cleared successfully.");
                    } else {
                        System.out.println("Failed to clear cart.");
                    }
                } else {
                    System.out.println("Failed to establish database connection.");
                }
            } catch (SQLException e) {
                System.out.println("Failed to clear cart: " + e.getMessage());
                e.printStackTrace();
            } finally {
                // Close database resources
                if (deleteStatement != null) {
                    try {
                        deleteStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }//GEN-LAST:event_ClearCartButtonActionPerformed

    private void CheckOutButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckOutButton1ActionPerformed
        // Get the cart table model
        DefaultTableModel cartModel = (DefaultTableModel) CartTable.getModel();

        // Check if the cart is empty
        if (cartModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Cart is empty. Please add items to checkout.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate the total price
        double totalPrice = 0.0;
        StringBuilder checkoutMessage = new StringBuilder("Checkout Summary:\n");

        for (int i = 0; i < cartModel.getRowCount(); i++) {
            String productName = cartModel.getValueAt(i, 0).toString();
            int qty = 0;
            double price = 0.0;

            try {
                price = Double.parseDouble(cartModel.getValueAt(i, 2).toString());
                qty = Integer.parseInt(cartModel.getValueAt(i, 1).toString());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid price or quantity in cart. Please check and try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double subtotal = price * qty;
            totalPrice += subtotal;

            checkoutMessage.append(String.format("%s x %d = ₱%.2f\n", productName, qty, subtotal));
        }

        checkoutMessage.append(String.format("Total Price: ₱%.2f", totalPrice));

        // Display the checkout summary
        JOptionPane.showMessageDialog(this, checkoutMessage.toString(), "Checkout", JOptionPane.INFORMATION_MESSAGE);

        // Clear the cart table
        cartModel.setRowCount(0);

        // Update the database to remove all items from the cart
        Connection connection = null;
        PreparedStatement deleteStatement = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            String deleteQuery = "DELETE FROM carttable";
            deleteStatement = connection.prepareStatement(deleteQuery);

            deleteStatement.executeUpdate();

            connection.commit(); // Commit transaction

            JOptionPane.showMessageDialog(this, "Checkout successful. Cart is empty.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Failed to rollback: " + ex.getMessage());
                ex.printStackTrace();
            }
            System.out.println("Failed to checkout: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close database resources
            if (deleteStatement != null) {
                try {
                    deleteStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_CheckOutButton1ActionPerformed

    private void AddDiscountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddDiscountButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddDiscountButtonActionPerformed

    private void RemoveFromCartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveFromCartButtonActionPerformed
        // Get the selected rows from the CartTable
        int[] selectedRows = CartTable.getSelectedRows();

        if (selectedRows.length > 0) {
            // Confirm with the user if they want to remove the selected items
            int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the selected item(s) from the cart?", "Remove From Cart", JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                DefaultTableModel cartModel = (DefaultTableModel) CartTable.getModel();
                Connection connection = null;
                PreparedStatement deleteStatement = null;
                PreparedStatement updateInventoryStatement = null;

                try {
                    // Open the database connection
                    connection = DatabaseConnection.getConnection();
                    connection.setAutoCommit(false); // Start transaction

                    if (connection != null) {
                        String deleteQuery = "DELETE FROM carttable WHERE productname =? AND qty =? AND price =?";
                        deleteStatement = connection.prepareStatement(deleteQuery);

                        String updateInventoryQuery = "UPDATE inventory SET qty = qty +? WHERE productname =?";
                        updateInventoryStatement = connection.prepareStatement(updateInventoryQuery);

                        // Check if the connection is established
                        if (!connection.isClosed()) {
                            for (int row : selectedRows) {
                                String name = (String) cartModel.getValueAt(row, 0);
                                int qty = Integer.parseInt(cartModel.getValueAt(row, 1).toString()); // Convert to Integer
                                double price = Double.parseDouble(cartModel.getValueAt(row, 2).toString()); // Convert to Double

                                // Set parameters for the prepared statement
                                deleteStatement.setString(1, name);
                                deleteStatement.setInt(2, qty);
                                deleteStatement.setDouble(3, price);

                                // Execute the deletion query
                                deleteStatement.executeUpdate();

                                // Update inventory
                                updateInventoryStatement.setInt(1, qty);
                                updateInventoryStatement.setString(2, name);
                                updateInventoryStatement.executeUpdate();
                            }

                            connection.commit(); // Commit transaction

                            // Remove the selected rows from the table (in reverse order to avoid index issues)
                            for (int i = selectedRows.length - 1; i >= 0; i--) {
                                cartModel.removeRow(selectedRows[i]);
                            }

                            // Refresh the ProductTable
                            loadProductTable();

                            JOptionPane.showMessageDialog(this, "Selected items removed from cart successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            System.out.println("Failed to establish database connection.");
                        }
                    } else {
                        System.out.println("Failed to establish database connection.");
                    }
                } catch (SQLException e) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        System.out.println("Failed to rollback: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                    System.out.println("Failed to remove item(s) from cart: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    // Close database resources
                    if (deleteStatement != null) {
                        try {
                            deleteStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (updateInventoryStatement != null) {
                        try {
                            updateInventoryStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No item selected to remove from the cart.", "Error", JOptionPane.ERROR_MESSAGE);
        }
     }//GEN-LAST:event_RemoveFromCartButtonActionPerformed

    private void SelectCustomerComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectCustomerComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SelectCustomerComboBoxActionPerformed

    private void AddToCartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddToCartButtonActionPerformed
        // Get the selected row from the ProductTable
        int selectedRow = ProductTable.getSelectedRow();

        if (selectedRow != -1) {
            // Retrieve product details from the selected row
            DefaultTableModel productModel = (DefaultTableModel) ProductTable.getModel();
            DefaultTableModel cartModel = (DefaultTableModel) CartTable.getModel();

            String name = (String) productModel.getValueAt(selectedRow, 1); // Assuming column 1 is product name
            double price = Double.parseDouble(productModel.getValueAt(selectedRow, 3).toString()); // Assuming column 4 is product price
            int availableStock = Integer.parseInt(productModel.getValueAt(selectedRow, 2).toString()); // Assuming column 3 is available stock

            // Check if the product is already in the cart
            boolean productInCart = false;
            int cartRowIndex = -1;
            for (int i = 0; i < cartModel.getRowCount(); i++) {
                if (cartModel.getValueAt(i, 0).equals(name)) {
                    productInCart = true;
                    cartRowIndex = i;
                    break;
                }
            }

            // Prompt the user to input the quantity of stock to add
            String stockInput = JOptionPane.showInputDialog(this, "Enter the quantity of stock to add for " + name + ":", "Add Stock", JOptionPane.QUESTION_MESSAGE);
            if (stockInput != null && !stockInput.isEmpty()) {
                try {
                    int qtyToAdd = Integer.parseInt(stockInput.trim());

                    // Check if the quantity to add is valid (positive) and available
                    if (qtyToAdd > 0 && qtyToAdd <= availableStock) {
                        Connection connection = null;
                        PreparedStatement updateCartStatement = null;
                        PreparedStatement insertCartStatement = null;
                        PreparedStatement updateInventoryStatement = null;

                        try {
                            connection = DatabaseConnection.getConnection();
                            connection.setAutoCommit(false); // Start transaction

                            if (productInCart) {
                                // Update the quantity in the cart table
                                int currentQty = Integer.parseInt(cartModel.getValueAt(cartRowIndex, 1).toString());
                                cartModel.setValueAt(currentQty + qtyToAdd, cartRowIndex, 1);

                                // Update the quantity in the carttable database table
                                String updateCartQuery = "UPDATE carttable SET qty = qty + ? WHERE productname = ?";
                                updateCartStatement = connection.prepareStatement(updateCartQuery);
                                updateCartStatement.setInt(1, qtyToAdd);
                                updateCartStatement.setString(2, name);
                                updateCartStatement.executeUpdate();
                            } else {
                                // Add the product to the CartTable
                                cartModel.addRow(new Object[]{name, qtyToAdd, price});

                                // Insert the product into the carttable database table
                                String insertCartQuery = "INSERT INTO carttable (productname, qty, price) VALUES (?, ?, ?)";
                                insertCartStatement = connection.prepareStatement(insertCartQuery);
                                insertCartStatement.setString(1, name);
                                insertCartStatement.setInt(2, qtyToAdd);
                                insertCartStatement.setDouble(3, price);
                                insertCartStatement.executeUpdate();
                            }

                            // Update inventory
                            String updateInventoryQuery = "UPDATE inventory SET qty = qty - ? WHERE productname = ?";
                            updateInventoryStatement = connection.prepareStatement(updateInventoryQuery);
                            updateInventoryStatement.setInt(1, qtyToAdd);
                            updateInventoryStatement.setString(2, name);
                            updateInventoryStatement.executeUpdate();

                            connection.commit(); // Commit transaction

                            // Inform the user about the stock update
                            JOptionPane.showMessageDialog(this, qtyToAdd + " units of " + name + " added to the cart.", "Stock Added", JOptionPane.INFORMATION_MESSAGE);
                            // Refresh the product table to reflect the updated stock
                            loadProductTable();
                        } catch (SQLException e) {
                            if (connection != null) {
                                try {
                                    connection.rollback();
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            JOptionPane.showMessageDialog(this, "Failed to execute SQL query: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            e.printStackTrace();
                        } finally {
                            if (updateCartStatement != null) {
                                try {
                                    updateCartStatement.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (insertCartStatement != null) {
                                try {
                                    insertCartStatement.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (updateInventoryStatement != null) {
                                try {
                                    updateInventoryStatement.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else if (qtyToAdd <= 0) {
                        JOptionPane.showMessageDialog(this, "Please enter a valid positive quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Not enough stock available.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No product selected to add.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_AddToCartButtonActionPerformed

 
    private void ModifyCartItemPriceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifyCartItemPriceButtonActionPerformed
        //MODIFY CART ITEM IS BROKEN AT THE MOMENT
        //PROBLEM: productTable "inventory"(database)
        //NOT UPDATING PROPERLY
        int selectedRow = CartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a cart item to modify.");
            return;
        }

        String productName = CartTable.getValueAt(selectedRow, 0).toString();
        int qty = 0;
        double price = 0.0;

        try {
            qty = Integer.parseInt(CartTable.getValueAt(selectedRow, 1).toString());
            price = Double.parseDouble(CartTable.getValueAt(selectedRow, 2).toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity or price format.");
            return;
        }

        String[] options = {"Edit Quantity", "Edit Price", "Edit Both"};
        int choice = JOptionPane.showOptionDialog(this, "What would you like to edit?", "Modify Cart Item",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == -1) {
            return; // User cancelled
        }

        switch (choice) {
            case 0: // Edit Quantity
                String newQtyInput = JOptionPane.showInputDialog(this, "Enter new quantity:");
                if (newQtyInput == null) {
                    return; // User cancelled
                }

                try {
                    int newQty = Integer.parseInt(newQtyInput.trim());

                    // Update the carttable database
                    Connection conn = DatabaseConnection.getConnection();
                    try (PreparedStatement pstmt = conn.prepareStatement("UPDATE carttable SET qty =? WHERE productName =?")) {
                        pstmt.setInt(1, newQty);
                        pstmt.setString(2, productName);
                        pstmt.executeUpdate();
                        System.out.println("Cart quantity updated successfully.");
                    } catch (SQLException e) {
                        System.out.println("Failed to update cart quantity.");
                        e.printStackTrace();
                    }

                    // Update the inventory database quantity
                    try (PreparedStatement pstmt2 = conn.prepareStatement("UPDATE inventory SET qty = qty - ? + ? WHERE productName =?")) {
                        pstmt2.setInt(1, qty); // Subtract the old quantity
                        pstmt2.setInt(2, newQty); // Add the new quantity
                        pstmt2.setString(3, productName);
                        pstmt2.executeUpdate();
                        System.out.println("Inventory quantity updated successfully.");
                    } catch (SQLException e) {
                        System.out.println("Failed to update inventory quantity.");
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity format.");
                    return;
                }
                break;

            case 1: // Edit Price
                String newPriceInput = JOptionPane.showInputDialog(this, "Enter new price:");
                if (newPriceInput == null) {
                    return; // User cancelled
                }

                try {
                    double newPrice = Double.parseDouble(newPriceInput.trim());

                    // Update the carttable database
                    Connection conn2 = DatabaseConnection.getConnection();
                    try (PreparedStatement pstmt = conn2.prepareStatement("UPDATE carttable SET price =? WHERE productName =?")) {
                        pstmt.setDouble(1, newPrice);
                        pstmt.setString(2, productName);
                        pstmt.executeUpdate();
                        System.out.println("Cart price updated successfully.");
                    } catch (SQLException e) {
                        System.out.println("Failed to update cart price.");
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid price format.");
                    return;
                }
                break;

            case 2: // Edit Both
                String input = JOptionPane.showInputDialog(this, "Enter new quantity and price (separated by comma):");
                if (input == null) {
                    return; // User cancelled
                }

                String[] parts = input.split(",");
                if (parts.length != 2) {
                    JOptionPane.showMessageDialog(this, "Invalid input format. Please enter quantity and price separated by comma.");
                    return;
                }

                try {
                    int newQty2 = Integer.parseInt(parts[0].trim());
                    double newPrice2 = Double.parseDouble(parts[1].trim());

                    // Update the carttable database
                    Connection conn3 = DatabaseConnection.getConnection();
                    try (PreparedStatement pstmt = conn3.prepareStatement("UPDATE carttable SET qty =?, price =? WHERE productName =?")) {
                        pstmt.setInt(1, newQty2);
                        pstmt.setDouble(2, newPrice2);
                        pstmt.setString(3, productName);
                        pstmt.executeUpdate();
                        System.out.println("Cart quantity and price updated successfully.");
                    } catch (SQLException e) {
                        System.out.println("Failed to update cart quantity and price.");
                        e.printStackTrace();
                    }

                    // Update the inventory database quantity
                    try (PreparedStatement pstmt2 = conn3.prepareStatement("UPDATE inventory SET qty = qty - ? + ? WHERE productName =?")) {
                        pstmt2.setInt(1, qty); // Subtract the old quantity
                        pstmt2.setInt(2, newQty2); //Add the new quantity
                        pstmt2.setString(3, productName);
                        pstmt2.executeUpdate();
                        System.out.println("Inventory quantity updated successfully.");
                    } catch (SQLException e) {
                        System.out.println("Failed to update inventory quantity.");
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity or price format.");
                    return;
                }
                break;
        }

        updateReceiptTextArea(); // Update the receipt text area
    }//GEN-LAST:event_ModifyCartItemPriceButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddDiscountButton;
    private javax.swing.JButton AddToCartButton;
    private javax.swing.JTable CartTable;
    private javax.swing.JButton CheckOutButton1;
    private javax.swing.JButton ClearCartButton;
    private javax.swing.JButton ModifyCartItemPriceButton;
    private javax.swing.JTable ProductTable;
    private javax.swing.JTextArea ReceiptTextArea;
    private javax.swing.JButton RemoveFromCartButton;
    private javax.swing.JComboBox<String> SelectCustomerComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
