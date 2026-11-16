package cl.duoc.tienda.controller;

import cl.duoc.tienda.model.Bebestible;
import cl.duoc.tienda.model.Congelado;
import cl.duoc.tienda.model.Producto;
import cl.duoc.tienda.repository.ProductoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class ProductoController {
    public Label labelTipo;
    public Label labelCodigo;
    public Label labelPrecio;
    public Label labelStock;
    public Label labelTemperatura;
    public Label labelTieneGas;
    public ComboBox<String> comboBoxTipo;
    public TextField textFieldCodigo;
    public TextField textFieldPrecio;
    public TextField textFieldStock;
    public TextField textFieldTemperatura;
    public CheckBox checkBoxTieneGas;
    public Button buttonAgregar;
    public Button buttonEliminar;
    public TableView<Producto> tableViewProductos;
    public TableColumn<Producto,String> tableColumnCodigo;
    public TableColumn<Producto,Double> tableColumnPrecio;
    public TableColumn<Producto,Integer> tableColumnStock;
    public Label labelMensaje;
    public TableColumn<Producto, String> tableColumnTipo;
    public TableColumn<Producto, String> tableColumnCaracteristica;
    public Label labelRegistroTienda;


    //7 métodos que realiza la labor de nuestro controlador:

    // Al iniciar el proyecto
    @FXML
    public void initialize(){
        //acción para cargar valores a un combobox:
        comboBoxTipo.getItems().addAll("Bebestible","Congelado");

        //listener: siempre estará atento a la acción indicada:
        comboBoxTipo.setOnAction(e -> {
            String seleccion = comboBoxTipo.getValue();
            if(seleccion == null){
                return;
            }
            if(seleccion.equals("Bebestible")){
                checkBoxTieneGas.setDisable(false);
                textFieldTemperatura.setDisable(true);
            }else{
                textFieldTemperatura.setDisable(false);
                checkBoxTieneGas.setDisable(true);
            }
        });

        cargarTabla();
    }

    // Agregar Producto:
    @FXML
    protected void agregarProducto(){
        try{
            if(comboBoxTipo.getValue()==null){
                labelMensaje.setText("Debe elegir un tipo de producto.");
                return;
            }

            Producto p;
            String codigo = textFieldCodigo.getText();
            double precio = Double.parseDouble(textFieldPrecio.getText());
            int stock = Integer.parseInt(textFieldStock.getText());
            String tipo = comboBoxTipo.getValue();

            if(tipo.equals("Bebestible")){
                boolean tieneGas = checkBoxTieneGas.isSelected();
                p = new Bebestible(codigo,precio,stock,tieneGas);
            }else{
                double temperatura = Double.parseDouble(textFieldTemperatura.getText());
                p = new Congelado(codigo,precio,stock,temperatura);
            }
            boolean res = new ProductoRepository().agregarProducto(p);
            if(res){
                mostrarAlerta("producto Registrado");
                cargarTabla();
                limpiar();
            }else{
                mostrarAlerta("Producto no Registrado ");
            }
        }catch (NumberFormatException ex){
            labelMensaje.setText("Debe ingresar números en los campos numéricos.");
        }catch(Exception ex){
            labelMensaje.setText( ex.getMessage() );
        }
    }


    // Eliminar porducto
    @FXML
    protected void eliminarProducto(){
        try {
            //1. Borrar usando la table:
            Producto p = tableViewProductos.getSelectionModel().getSelectedItem();
            if(p==null){
                labelMensaje.setText("Debe elegir un producto de la tabla.");
                return;
            }else{
                boolean res = new ProductoRepository().eliminarProducto(p.getCodigo());
                if (res){
                    mostrarAlerta("producto eliminado");
                    cargarTabla();
                    limpiar();
                }else{
                    mostrarAlerta("Producto no eliminado");
                }
            }
        } catch (Exception ex) {
            mostrarAlerta(ex.getMessage());
        }
    }

    public void limpiar(){
        comboBoxTipo.setValue(null);
        textFieldCodigo.setText("");
        textFieldPrecio.setText("");
        textFieldStock.setText("");
        textFieldTemperatura.setText("");
        checkBoxTieneGas.setSelected(false);
        labelMensaje.setText("-");

        textFieldTemperatura.setDisable(true);
        checkBoxTieneGas.setDisable(true);
    }

    // Mostrar mensaje:
    public void mostrarAlerta(String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setContentText(mensaje);
        alerta.show();
    }

    // Cargar Tabla:
    public void cargarTabla(){
        try {
            tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            tableColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            tableColumnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            tableColumnCaracteristica.setCellValueFactory(new PropertyValueFactory<>("caracteristica"));

            ArrayList<Producto> productos = new ProductoRepository().verProductos();
            ObservableList<Producto> data = FXCollections.observableArrayList(productos);
            tableViewProductos.setItems(data);
        }catch (Exception ex){
            mostrarAlerta(ex.getMessage());
        }
    }

    // Seleccionar desde la tabla:
    @FXML
    public void seleccionarProducto() {
        Producto p = tableViewProductos.getSelectionModel().getSelectedItem();
        if( p != null){
            textFieldCodigo.setText(p.getCodigo());
            textFieldPrecio.setText(String.valueOf(p.getPrecio()));
            textFieldStock.setText(String.valueOf(p.getStock()));

            if(p instanceof Bebestible){
                Bebestible b = (Bebestible) p;
                comboBoxTipo.setValue("Bebestible");
                checkBoxTieneGas.setSelected(b.isTieneGas());
                textFieldTemperatura.setText("");
            }else{
                Congelado c = (Congelado) p;
                comboBoxTipo.setValue("Congelado");
                textFieldTemperatura.setText(String.valueOf(c.getTemperatura()));
                checkBoxTieneGas.setSelected(false);
            }
        }
    }


}
