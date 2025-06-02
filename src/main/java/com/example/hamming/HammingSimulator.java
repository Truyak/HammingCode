package com.example.hamming;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HammingSimulator extends Application {
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #E6F3FA;"); // Açık mavi arka plan

        // Data size selection
        ComboBox<String> dataSizeCombo = new ComboBox<>();
        dataSizeCombo.getItems().addAll("8-bit", "16-bit", "32-bit");
        dataSizeCombo.setValue("8-bit");
        dataSizeCombo.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2E4057;"); // Beyaz arka plan, lacivert metin

        // Data input
        TextField dataInput = new TextField();
        dataInput.setPromptText("Enter binary data (e.g., 11001111)");
        dataInput.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2E4057;"); // Beyaz arka plan, lacivert metin

        // Error bit input
        TextField errorBitInput = new TextField();
        errorBitInput.setPromptText("Enter bit position to flip (optional)");
        errorBitInput.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2E4057;"); // Beyaz arka plan, lacivert metin

        // Calculate button
        Button calculateButton = new Button("Calculate Hamming Code");
        calculateButton.setStyle("-fx-background-color: #ffdbdb; -fx-text-fill: #0b0909; -fx-background-radius: 5;"); // Soluk turuncu buton, beyaz metin

        // Labels
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-text-fill: #34495E;"); // Koyu gri metin
        Label faultyLabel = new Label(); // Hatalı veri için
        faultyLabel.setStyle("-fx-text-fill: #34495E;");
        Label syndromeLabel = new Label(); // Syndrome için
        syndromeLabel.setStyle("-fx-text-fill: #34495E;");
        Label correctedLabel = new Label();
        correctedLabel.setStyle("-fx-text-fill: #34495E;");

        calculateButton.setOnAction(e -> {
            try {
                int dataLength = dataSizeCombo.getValue().equals("8-bit") ? 8 : dataSizeCombo.getValue().equals("16-bit") ? 16 : 32;
                int[] data = parseBinaryInput(dataInput.getText(), dataLength);
                int[] hammingCode = HammingCode.calculateHammingCode(data, dataLength);

                resultLabel.setText("Hamming Code: " + arrayToString(hammingCode));

                int[] faultyData = hammingCode.clone(); // Hatalı veriyi saklamak için kopya oluştur
                int errorBit = errorBitInput.getText().isEmpty() ? -1 : Integer.parseInt(errorBitInput.getText()) - 1;
                if (errorBit >= 0 && errorBit < faultyData.length) {
                    faultyData[errorBit] ^= 1; // Hatalı veride belirtilen biti değiştir
                }

                faultyLabel.setText("Faulty Data: " + arrayToString(faultyData));

                // Detect and correct error
                int syndrome = HammingCode.detectError(faultyData, dataLength);
                syndromeLabel.setText("Syndrome: " + syndrome + (syndrome > 0 ? " (Error at bit " + syndrome + ")" : syndrome == 0 ? " (No error)" : " (Double error detected)"));
                if (syndrome > 0) {
                    int[] corrected = HammingCode.correctError(faultyData, syndrome);
                    correctedLabel.setText("Corrected Code: " + arrayToString(corrected));
                } else {
                    correctedLabel.setText("");
                }
            } catch (Exception ex) {
                resultLabel.setText("Error: Invalid input");
            }
        });

        root.getChildren().addAll(
                new Label("Select Data Size:"), dataSizeCombo,
                new Label("Enter Data (binary):"), dataInput,
                new Label("Introduce Error at Bit (optional):"), errorBitInput,
                calculateButton, resultLabel, syndromeLabel, faultyLabel, correctedLabel
        );

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Hamming SEC-DED Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int[] parseBinaryInput(String input, int length) {
        if (input.length() != length || !input.matches("[01]+")) {
            throw new IllegalArgumentException("Invalid binary input");
        }
        int[] data = new int[length];
        for (int i = 0; i < length; i++) {
            data[i] = Character.getNumericValue(input.charAt(i));
        }
        return data;
    }

    private String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int bit : arr) {
            sb.append(bit);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}