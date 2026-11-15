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

    ArrayList<Producto> productos = new ArrayList<>();

    //7 métodos que realiza la labor de nuestro controlador:
    @FXML
    public void initialize(){
        //acción para cargar valores a un combobox:
        comboBoxTipo.getItems().addAll("Bebestible","Congelado");
        //listener: siempre estará atento a la acción indicada:
        comboBoxTipo.setOnAction(e -> {
            String seleccion = comboBoxTipo.getValue();
            if(seleccion.equals("Bebestible")){
                checkBoxTieneGas.setDisable(false);
                textFieldTemperatura.setDisable(true);
            }else{
                textFieldTemperatura.setDisable(false);
                checkBoxTieneGas.setDisable(true);
            }
        });
    }

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
                mostrarAlerta("Mascota no Registrado ");
            }
        }catch (NumberFormatException ex){
            labelMensaje.setText("Debe ingresar números en los campos numéricos.");
        }catch(Exception ex){
            labelMensaje.setText( ex.getMessage() );
        }
    }

    @FXML
    protected void eliminarProducto(){
        try {
            //1. Borrar usando la table:
            Producto p = tableViewProductos.getSelectionModel().getSelectedItem();
            if(p==null){
                labelMensaje.setText("Debe elegir un producto de la tabla.");
                return;
            }

            productos.remove(p);
            mostrarAlerta("PRODUCTO ELIMINADO.");
            cargarTabla();
            limpiar();

            /*2. Borrando al escribir el código:
            String codigo = textFieldCodigo.getText();
            for(Producto pro : productos){
                if( codigo.equalsIgnoreCase(pro.getCodigo()) ){
                    productos.remove(pro);
                    mostrarAlerta("PRODUCTO ELIMINADO.");
                    limpiar();
                    cargarTabla();
                    return;
                }
            }
            labelMensaje.setText("PRODUCTO NO EXISTE.");*/
        } catch (Exception ex) {
            labelMensaje.setText( ex.getMessage() );
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
        tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tableColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tableColumnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        ObservableList<Producto> data = FXCollections.observableArrayList(productos);
        tableViewProductos.setItems(data);
    }

    // Seleccionar desde la tabla:
    @FXML
    public void seleccionarProducto() {
        Producto p = tableViewProductos.getSelectionModel().getSelectedItem();
        if( p != null){
            textFieldCodigo.setText(String.valueOf(p.getCodigo()));
            textFieldPrecio.setText(String.valueOf(p.getPrecio()));
            textFieldStock.setText(String.valueOf(p.getPrecio()));
        }
    }


}
