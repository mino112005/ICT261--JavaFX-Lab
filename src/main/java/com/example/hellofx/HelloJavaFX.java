package com.example.hellofx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class HelloJavaFX extends Application {

    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        Label title = new Label("CUSTOMER REGISTRATION FORM");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // 1. Form: name field + province list
        Label nameLabel = new Label("Customer name");
        TextField nameField = new TextField();
        nameField.setPromptText("e.g., Mary Banda");
        nameLabel.setLabelFor(nameField);

        Label provinceLabel = new Label("Province");
        ComboBox<String> provinceBox = new ComboBox<>();
        provinceBox.getItems().addAll("Central", "Copperbelt", "Eastern",
                "Luapula", "Lusaka", "Muchinga", "Northern",
                "North-Western", "Southern", "Western");
        provinceBox.setPromptText("Choose a province");
        provinceBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Choose a province" : item);
            }
        });
        provinceLabel.setLabelFor(provinceBox);

        Button saveButton = new Button("Save customer");
        saveButton.setDefaultButton(true);
        Button deleteButton = new Button("Delete selected");
        Button clearButton = new Button("Clear");
        Label status = new Label();

        // 2 & 3. ObservableList + TableView with name and province columns
        TableView<Customer> table = new TableView<>();
        table.setItems(customers);
        table.setPlaceholder(new Label("No customers yet."));

        TableColumn<Customer, String> nameCol = new TableColumn<>("Customer name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Customer, String> provinceCol = new TableColumn<>("Province");
        provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));
        table.getColumns().add(nameCol);
        table.getColumns().add(provinceCol);
        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.49));
        provinceCol.prefWidthProperty().bind(table.widthProperty().multiply(0.49));


        // 4. Validate input, then add the customer
        saveButton.setOnAction(event -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                status.setText("Enter the customer name.");
                nameField.requestFocus();
                return;
            }
            String province = provinceBox.getValue();
            if (province == null) {
                status.setText("Choose a province.");
                provinceBox.requestFocus();
                return;
            }
            customers.add(new Customer(name, province));
            status.setText("Customer saved.");
            nameField.clear();
            provinceBox.setValue(null);
            nameField.requestFocus();
        });

        // 5. Confirm deletion of a selected customer
        deleteButton.setOnAction(event -> {
            Customer selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                status.setText("Select a customer first.");
                return;
            }
            ButtonType delete = new ButtonType("Delete");
            Alert ask = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete the selected customer?", delete, ButtonType.CANCEL);
            ask.setHeaderText("Confirm deletion");
            if (ask.showAndWait().orElse(ButtonType.CANCEL) == delete) {
                customers.remove(selected);
                status.setText("Customer deleted.");
            }
        });

        clearButton.setOnAction(event -> {
            nameField.clear();
            provinceBox.setValue(null);
            status.setText("");
            nameField.requestFocus();
        });

        HBox buttons = new HBox(10, saveButton, deleteButton, clearButton);
        VBox layout = new VBox(8, title, nameLabel, nameField, provinceLabel,
                provinceBox, buttons, status, table);
        layout.setPadding(new Insets(15));

        Menu fileMenu = new Menu("File");
        MenuItem closeItem = new MenuItem("Close");
        closeItem.setOnAction(e -> stage.close());
        fileMenu.getItems().add(closeItem);
        MenuBar menuBar = new MenuBar(fileMenu);
        VBox root = new VBox(menuBar, layout);

        Scene scene = new Scene(root, 500, 560);
        stage.setTitle("My First JavaFX Application - StudentNumber : 202500391");
        stage.setScene(scene);
        stage.show();
        nameField.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
