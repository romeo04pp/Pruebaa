//Declaracion del paquete vista compras.cxp
package vista.compras_cxp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */

//Importaciones de archivos a utilizar
import Modelo.Conexion;
import Modelo.compras_cxp.ProveedorDAO;
import Modelo.compras_cxp.Compras_cppDAO;
import Controlador.compras_cxp.Proveedor;
import Controlador.compras_cxp.Compra_cpp;
import Controlador.inventarios.productos;
import Controlador.seguridad.Bitacora;
import Controlador.seguridad.Perfil;
import Controlador.seguridad.UsuarioConectado;
import Modelo.inventarios.ProductosDAO;
import Modelo.seguridad.PerfilDAO;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

//Bitacora Implemenrada por Oscar Morales
import Controlador.seguridad.Bitacora;
import Controlador.seguridad.UsuarioConectado;

/**
 *
 * @author oscar
 */

//Declaracion de la clase compras
public class Compras extends javax.swing.JInternalFrame {
    
        //Codigo de la aplicacion mantenimiento compras
        final int APLICACION=204;
        // Despliegue de id proveedor en combobox ----- HECHO POR KATHIA CONTRERAS 8246
        public void llenadoDeCombos() {
        // instancia de ProveedorDAO
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        //manda con select a una lista todos los proveedores.
        List<Proveedor> proveedores = proveedorDAO.select();
       //ciclo que recorre todos los proveedores existentes
        for (int i = 0; i < proveedores.size(); i++) {
            //agrega todos los proveedores a item del combobox
            idproveedor.addItem(String.valueOf(proveedores.get(i).getId_proveedor()));
        }
        
                    //Kathia Contreras llenado de productos disponibles para la lista #1
                    
        ProductosDAO productosDAO = new ProductosDAO();//crea objeto 
        List<productos> producto = productosDAO.select(); //crea una lista
         DefaultListModel<String> modelo = new DefaultListModel<>();//crea un modelo para la lista
        for (productos app : producto) { //recorre la lista aplicaciones
            modelo.addElement(app.getProNombre()); //agrega los nombres de aplicaciones 
        }
        listaproductos.setModel(modelo);//manda el modelo a la lista (visualmente)

     
            //Modificaciones Realizadas por Alisson López al código base de Kathia Contreras
    
    
    listaproductos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) { // evitar eventos duplicados y mejora el rendimiento con break.
            String nomproducsele = listaproductos.getSelectedValue();// variable que obtiene el nombre de aplicacion seleccionada            
             //verifica que no esté vacía
            if (nomproducsele != null) {
                // Ciclo que busca el ID de la aplicación seleccionada
                for ( productos produc : producto) {
                    //verifica si el nombre del objeto es igual al nombre seleccionado
                    if (produc.getProNombre().equals(nomproducsele)) {
                       // String idproducSeleccionada = String.valueOf(produc.getProCodigo()); //trae el codigo del producto desde la base de datos
                        String codigoseleccionado=String.valueOf(produc.getProCodigo());
                        String precioSeleccionado=String.valueOf(produc.getProPrecio());// trae el precio del producto
                        if (precioSeleccionado.endsWith(".0")) {
                            precioSeleccionado = precioSeleccionado.substring(0, precioSeleccionado.length() - 2);
                        }
                        String cantidadSeleccionada=String.valueOf(produc.getProExistencias());
                        //productotxt.setText(idproducSeleccionada);// envia el id a la pantalla para que el usuario lo vea
                        precio.setText(precioSeleccionado);//envia el precio del producto
                        exis.setText(cantidadSeleccionada);
                        codiprotxt.setText(codigoseleccionado);
                        break;
                    }
                }
            }
        }
    }
});
    
    
    
         
    }
    //Metodo para llenar la tabla de compras     
    public void llenadoDeTablas() {
        //Nuevo modelo para la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        //añadiendo columnas
        
        //Columna no.de compra
        modelo.addColumn("No. Compra");
        //Columna del nombre del comprador
        modelo.addColumn("Nombre");
        //Columnna del apellido del comprador
        modelo.addColumn("Apellido");
        //Columnna del id del proveedor
        modelo.addColumn("Id Proveedor");
        //Columnna del producto comprado
        modelo.addColumn("Producto");
        //Columnna de la cantidad de productos comprados
        modelo.addColumn("Cantidad");
        //Columnna del precio del producto a comprar
        modelo.addColumn("Precio");
        //Columnna del salario anterior
        modelo.addColumn("Salario Anterior");
        //Colummna del plazo de dias
        modelo.addColumn("Plazo");
        //Columnna del total
        modelo.addColumn("Total");
       
        //Crea una instancia del DAO para acceder a los datos de las compras
        Compras_cppDAO compras_cppDAO = new Compras_cppDAO();
        //Obtieniendo la lista de compras desde la base de datos
        List<Compra_cpp> compras_cpp = compras_cppDAO.select();
        //Asigna el modelo creado a la tabla visual
        jTable2.setModel(modelo);
        //Arreglo temporal para almacenar los datos de cada compra
        String[] dato = new String[10];
        
        //Ciclo que Recorre la lista de compras y llena el modelo con los datos
        for (int i = 0; i < compras_cpp.size(); i++) {
            
            //Asignacion de los valores de cada colummna 
            dato[0] = Integer.toString(compras_cpp.get(i).getNo_compra());
            dato[1] = compras_cpp.get(i).getNombre_usuario();
            dato[2] = compras_cpp.get(i).getApellido_usuario();
            dato[3] = Integer.toString(compras_cpp.get(i).getId_proveedor());
            dato[4] = compras_cpp.get(i).getProducto();
            dato[5] = Integer.toString(compras_cpp.get(i).getCantidad());
            dato[6] = Integer.toString(compras_cpp.get(i).getPrecio());
            dato[7] = Integer.toString(compras_cpp.get(i).getSaldo_anterior());
            dato[8] = Integer.toString(compras_cpp.get(i).getPlazo());
            dato[9] = Integer.toString(compras_cpp.get(i).getTotal());
            
            //Agregando fila al modelo
            modelo.addRow(dato);
          }
    }

                           //Hecho por Alisson López
    
   // Calculo de la suma de la columna total implementado 
    public void calcularTotal() {
        //Almacenador de la suma total
        double sumaTotal = 0.0;
        //Decision para saber si la tabla esta vacia y que debe de tener almenos una fila
        if (jTable2 != null && jTable2.getRowCount() > 0) {
            // Obtiene el índice de la última columna (columna Total)
            int columnaTotal = jTable2.getColumnCount() - 1;
           //Ciclo que recirre cada fila de la tabla
           for (int i = 0; i < jTable2.getRowCount(); i++) {
               try {
                    // Obtiene el valor de la celda en la columna Total
                   Object valor = jTable2.getValueAt(i, columnaTotal);
                   //Decision si el valor no es nulo se convierte en double y se suma
                   if (valor != null) {
                       double valorNumerico = Double.parseDouble(valor.toString());
                      sumaTotal += valorNumerico;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir valor en fila " + i);
                }
            }
        }
        //Imprime el resultado
        resul.setText(String.format("%.2f", sumaTotal));
   }

   


    
    //Constructor de la clase compras
    public Compras() {
        //Inicializacion de los objetos visuales
        initComponents();
        //Llamado del metodo de llenado de combos
        llenadoDeCombos();    
        //Llamado del metodo de llenado de tablas
        llenadoDeTablas();
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        idproveedor = new javax.swing.JComboBox<>();
        proveedorselec = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaproductos = new javax.swing.JList<>();
        NomProductotxt = new javax.swing.JTextField();
        precio = new javax.swing.JTextField();
        cantidadtxt = new javax.swing.JTextField();
        agrega = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        txtapellido = new javax.swing.JTextField();
        saldopen = new javax.swing.JTextField();
        txtplazo = new javax.swing.JTextField();
        exis = new javax.swing.JTextField();
        codiprotxt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        BAyudas = new javax.swing.JButton();
        Reporte = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        resul = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Compras Grupo #2");

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel1.setText("Compras - 204");

        jLabel2.setText("Id del proveedor");

        jLabel3.setText("Proveedor seleccionado");

        jLabel4.setText("Lista de productos disponibles");

        jLabel5.setText("Producto");

        jLabel6.setText("Precio");

        jLabel7.setText("Cantidad");

        idproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idproveedorActionPerformed(evt);
            }
        });

        listaproductos.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                listaproductosAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        listaproductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaproductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listaproductos);

        NomProductotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NomProductotxtActionPerformed(evt);
            }
        });

        precio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioActionPerformed(evt);
            }
        });

        cantidadtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cantidadtxtActionPerformed(evt);
            }
        });

        agrega.setText("Agregar");
        agrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregaActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Compra", "Nombre", "Apellido", "Id Proveedor", "Producto", "Cantidad", "Precio", "Saldo Anterior", "Plazo", "Total"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        jLabel8.setText("Nombre del comprador");

        jLabel9.setText("Apellido de comprador");

        jLabel10.setText("Stock");

        BAyudas.setText("Ayuda");
        BAyudas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAyudasActionPerformed(evt);
            }
        });

        Reporte.setText("Reporte");
        Reporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReporteActionPerformed(evt);
            }
        });

        jLabel11.setText("Total Reportes");

        resul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resulActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(agrega)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BAyudas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Reporte))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(codiprotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(273, 273, 273)
                                    .addComponent(saldopen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(110, 110, 110))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(24, 24, 24)
                                .addComponent(idproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(txtplazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtapellido, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                            .addComponent(proveedorselec))))
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9)
                                        .addGap(175, 175, 175))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(68, 68, 68)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel10))
                                                    .addGap(34, 34, 34)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(NomProductotxt)
                                                        .addComponent(precio)
                                                        .addComponent(exis, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addComponent(jLabel7)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(cantidadtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addGap(18, 18, 18)
                                                .addComponent(resul, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(77, 77, 77))))
            .addGroup(layout.createSequentialGroup()
                .addGap(307, 307, 307)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saldopen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtplazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codiprotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(idproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(proveedorselec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(69, 69, 69))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtapellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(NomProductotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(exis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cantidadtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(resul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(agrega)
                            .addComponent(BAyudas)
                            .addComponent(Reporte))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        NomProductotxt.setEditable(false);
        precio.setEditable(false);
        saldopen.setVisible(false);
        txtplazo.setVisible(false);
        exis.setEditable(false);
        codiprotxt.setVisible(false);
        resul.setEditable(false);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    //Cuando se ejecute se selecciona un proveedor en el comboBox
    private void idproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idproveedorActionPerformed
        // Obtiene el ítem seleccionado del comboBox y lo convierte a String
        String seleccionado = (String) idproveedor.getSelectedItem(); //conversion a string
        Proveedor proveedorAConsultar = new Proveedor(); //crea objeto
        ProveedorDAO proveedorDAO = new ProveedorDAO(); //crea objeto
        //Estableciendo los atributos
        proveedorAConsultar.setId_proveedor(Integer.valueOf(seleccionado)); //toma el id seleccionado
        proveedorAConsultar = proveedorDAO.query(proveedorAConsultar); //realiza una busqueda
        proveedorselec.setText(proveedorAConsultar.getNombre_proveedor());
        saldopen.setText(String.valueOf(proveedorAConsultar.getSaldo_proveedor()));
        txtplazo.setText(String.valueOf(proveedorAConsultar.getPlazo_limite()));
    }//GEN-LAST:event_idproveedorActionPerformed
    //Al hacer clic sobre un producto en la lista se obtiene
    private void listaproductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaproductosMouseClicked
                // Alisson López traslado selección a textfield y precio del producto
                
        // Obtiene el nombre del producto seleccionado en la lista
        String productoSeleccionado =listaproductos.getSelectedValue();
        //Decision si el producto seleccionado no esta vacio se establece 
        if (productoSeleccionado != null) 
        NomProductotxt.setText(productoSeleccionado);
    }//GEN-LAST:event_listaproductosMouseClicked
    
    //Cuando se ejecute el boton agregar
    private void agregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregaActionPerformed
        //LLama al metodo de calcular
        calcularTotal();
        
        //Creando instancias a utilizar
        Compras_cppDAO compras_cppDAO = new Compras_cppDAO();
        Compra_cpp comprasAInsertar = new Compra_cpp();
        
        //se obtiene el id del proveedor seleccionado
        String prseleccionado = (String) idproveedor.getSelectedItem();
        
        //Estableciendo los valores de los campos del formulario 
        comprasAInsertar.setNombre_usuario(txtnombre.getText());
        comprasAInsertar.setApellido_usuario(txtapellido.getText());
        comprasAInsertar.setId_proveedor(Integer.valueOf(prseleccionado));
        comprasAInsertar.setProducto(NomProductotxt.getText());
        comprasAInsertar.setCantidad(Integer.parseInt(cantidadtxt.getText()));
        comprasAInsertar.setPrecio(Integer.parseInt(precio.getText()));
        comprasAInsertar.setSaldo_anterior(Integer.parseInt(saldopen.getText()));
        comprasAInsertar.setPlazo(Integer.parseInt(txtplazo.getText()));
        
        //Obteniendo el valor de las variables
        String cantidadTexto = cantidadtxt.getText();
        String precioTexto = precio.getText();
        String saldoTexto = saldopen.getText();
        
        //Conviertiendo los valores a enteros
        int cantidad = Integer.parseInt(cantidadTexto);
        int precio = Integer.parseInt(precioTexto);
        int saldo = Integer.parseInt(saldoTexto);
        
        //Operacion para calcular el total del objeto comprado
        int total = (cantidad * precio) + saldo;
        
        //Estableciendo el total calculado
        comprasAInsertar.setTotal(total);
        compras_cppDAO.insert(comprasAInsertar);
        
        //LLama al metodo de llenado de tablas
        llenadoDeTablas();
        
        try {
            //Se obtiene la conexion
            Connection conn = Conexion.getConnection();
            //Obteniendo los valores de las variables
            int cantidadComprada = Integer.parseInt(cantidadtxt.getText());
            int proCodigo = Integer.parseInt(codiprotxt.getText());
            
            //Consulta sql para obtener las existencias actuales
            String sqlSelect = "SELECT proExistencias FROM tbl_productos WHERE proCodigo = ?";
            PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setInt(1, proCodigo);
            ResultSet rs = psSelect.executeQuery();
            
            //Decision si se encuentra el producto se verifica la cantidad de existencias
            if (rs.next()) {
                int stockActual = rs.getInt("proExistencias");
                
                // Si la cantidad comprada excede el stock disponible, muestra advertencia
                if (cantidadComprada > stockActual) {
                    JOptionPane.showMessageDialog(null, "No hay suficiente stock. Disponible: " + stockActual);
                    return;
                }
                
                //Actualizar el stock de los productos existentes 
                String sqlUpdate = "UPDATE tbl_productos SET proExistencias = proExistencias - ? WHERE proCodigo = ?";
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                //Asignacion de los parametros para la sentencia 
                psUpdate.setInt(1, cantidadComprada);
                psUpdate.setInt(2, proCodigo);
                
                // Ejecuta la actualización y obtiene la cantidad de filas afectadas
                int filas = psUpdate.executeUpdate();

                if (filas > 0) {
                  
                    JOptionPane.showMessageDialog(null, "Compra realizada correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el producto para actualizar.");
                }
                // Cierra los recursos de la base de datos para evitar fugas
                psUpdate.close();
            }
                    rs.close();
                    psSelect.close();

        } catch (NumberFormatException ex) {
              // Captura errores por ingresar datos no numéricos y muestra alerta
            JOptionPane.showMessageDialog(null, "Por favor, ingresá solo números válidos.");
        } catch (SQLException ex) {
            // Muestra error detallado si falla la actualización en base de datos
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el stock: " + ex.getMessage());
        }
        
        //agregado de suma total
        
        UsuarioConectado usuarioEnSesion = new UsuarioConectado();
        int resultadoBitacora=0;
        Bitacora bitacoraRegistro = new Bitacora();
        resultadoBitacora = bitacoraRegistro.setIngresarBitacora(usuarioEnSesion.getIdUsuario(), APLICACION,  "Ingreso Compra");
        
        
    }//GEN-LAST:event_agregaActionPerformed

    private void NomProductotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NomProductotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NomProductotxtActionPerformed

    private void precioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precioActionPerformed

    private void listaproductosAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_listaproductosAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_listaproductosAncestorAdded

    private void BAyudasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAyudasActionPerformed
                                //ayuda implementada por Andy
        try {
            // Ejecuta el archivo de ayuda usando el visor de archivos CHM de Windows
            if ((new File("src\\main\\java\\ayudas\\ayudaComprasTransaccional.chm")).exists()) {
                Process p = Runtime
                        .getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler src\\main\\java\\ayudas\\ayudaComprasTransaccional.chm");
                p.waitFor();
            } else {
                // Muestra mensaje en consola si no se encuentra el archivo de ayuda
                System.out.println("La ayuda no Fue encontrada");
            }
            
            System.out.println("Correcto");
        } catch (Exception ex) {
             // En caso de error, imprime la traza para diagnóstico
            ex.printStackTrace();
        }  
        UsuarioConectado usuarioEnSesion = new UsuarioConectado();
        int resultadoBitacora=0;
        Bitacora bitacoraRegistro = new Bitacora();
        resultadoBitacora = bitacoraRegistro.setIngresarBitacora(usuarioEnSesion.getIdUsuario(), APLICACION,  "Gestion Ayuda Compras");
    }//GEN-LAST:event_BAyudasActionPerformed
        
    private void ReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReporteActionPerformed
    //Implementacion de reportes por Andre Gonzales
        
    // Crea instancia del DAO de compras para acceder a la lógica de datos
    Compras_cppDAO compras_cppDAO = new Compras_cppDAO();
    // Llama al método que genera e imprime el reporte de compras
    compras_cppDAO.imprimirReporte();
    
            UsuarioConectado usuarioEnSesion = new UsuarioConectado();
        int resultadoBitacora=0;
        Bitacora bitacoraRegistro = new Bitacora();
        resultadoBitacora = bitacoraRegistro.setIngresarBitacora(usuarioEnSesion.getIdUsuario(), APLICACION,  "Visualizar reporte Compras");
    }//GEN-LAST:event_ReporteActionPerformed

    private void cantidadtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cantidadtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadtxtActionPerformed

    private void resulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resulActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resulActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BAyudas;
    private javax.swing.JTextField NomProductotxt;
    private javax.swing.JButton Reporte;
    private javax.swing.JButton agrega;
    private javax.swing.JTextField cantidadtxt;
    private javax.swing.JTextField codiprotxt;
    private javax.swing.JTextField exis;
    private javax.swing.JComboBox<String> idproveedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JList<String> listaproductos;
    private javax.swing.JTextField precio;
    private javax.swing.JTextField proveedorselec;
    private javax.swing.JTextField resul;
    private javax.swing.JTextField saldopen;
    private javax.swing.JTextField txtapellido;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtplazo;
    // End of variables declaration//GEN-END:variables
}
