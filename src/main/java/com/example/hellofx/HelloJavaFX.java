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

public class HelloJavaFX extends Application {

    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        Label message = new Label("Welcome, Maximino Phiri!");

        // 1. Form: name field + province list
        Label nameLabel = new Label("Customer name");
        TextField nameField = new TextField();
        nameField.setPromptText("e.g., Mary Banda");
        nameLabel.setLabelFor(nameField);

        Label provinceLabel = new Label("Province");
        ComboBox<String> provinceBox = new ComboBox<>();
        provinceBox.getItems().addAll("Central", "Lusaka", "Copperbelt");
        provinceBox.setPromptText("Choose a province");
        provinceLabel.setLabelFor(provinceBox);

        Button saveButton = new Button("Save customer");
        saveButton.setDefaultButton(true);
        Button deleteButton = new Button("Delete selected");
        Label status = new Label();

        // 2 & 3. ObservableList + TableView with name and province columns
        TableView<Customer> table = new TableView<>();
        table.setItems(customers);

        TableColumn<Customer, String> nameCol = new TableColumn<>("Customer name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Customer, String> provinceCol = new TableColumn<>("Province");
        provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));
        table.getColumns().add(nameCol);
        table.getColumns().add(provinceCol);

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

        HBox buttons = new HBox(10, saveButton, deleteButton);
        VBox layout = new VBox(8, message, nameLabel, nameField, provinceLabel,
                provinceBox, buttons, status, table);
        layout.setPadding(new Insets(15));

        Scene scene = new Scene(layout, 500, 560);
        stage.setTitle("My First JavaFX Application - StudentNumber : 202500391");
        stage.setScene(scene);
        stage.show();
        nameField.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
