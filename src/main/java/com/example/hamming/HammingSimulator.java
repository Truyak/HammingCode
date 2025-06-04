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
        root.setStyle("-fx-background-color: #E6F3FA;");

        ComboBox<String> dataSizeCombo = new ComboBox<>();
        dataSizeCombo.getItems().addAll("8-bit", "16-bit", "32-bit");
        dataSizeCombo.setValue("8-bit");
        dataSizeCombo.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2E4057;");

        TextField dataInput = new TextField();
        dataInput.setPromptText("Enter binary data (e.g., 11001111)");
        dataInput.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2E4057;");

        TextField errorBitInput = new TextField();
        errorBitInput.setPromptText("Enter bit position to flip (optional, rightmost is 1)");
        errorBitInput.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2E4057;");

        Button calculateButton = new Button("Calculate Hamming Code");
        calculateButton.setStyle("-fx-background-color: #ffdbdb; -fx-text-fill: #0b0909; -fx-background-radius: 5;");

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-text-fill: #34495E;");
        Label faultyLabel = new Label();
        faultyLabel.setStyle("-fx-text-fill: #34495E;");
        Label syndromeLabel = new Label();
        syndromeLabel.setStyle("-fx-text-fill: #34495E;");
        Label correctedLabel = new Label();
        correctedLabel.setStyle("-fx-text-fill: #34495E;");

        calculateButton.setOnAction(e -> {
            try {
                int dataLength = dataSizeCombo.getValue().equals("8-bit") ? 8 : dataSizeCombo.getValue().equals("16-bit") ? 16 : 32;
                int[] data = parseBinaryInput(dataInput.getText(), dataLength);
                int[] hammingCode = HammingCode.calculateHammingCode(data, dataLength);

                resultLabel.setText("Hamming Code: " + arrayToString(hammingCode));

                int[] faultyData = hammingCode.clone();
                int errorBit = errorBitInput.getText().isEmpty() ? -1 : Integer.parseInt(errorBitInput.getText());
                if (errorBit > 0 && errorBit <= faultyData.length) {
                    faultyData[faultyData.length - errorBit] ^= 1; // Sağdan hata pozisyonu
                }

                int syndrome = Math.max(errorBit, 0);
                syndromeLabel.setText("Syndrome: " + (syndrome > 0 ? syndrome + " (Error at bit " + syndrome + " from right)" : "0 (No error)"));
                if (syndrome > 0) {
                    faultyLabel.setText("Faulty Data: " + arrayToString(faultyData));
                    int[] corrected = HammingCode.correctError(faultyData, syndrome);
                    correctedLabel.setText("Corrected Code: " + arrayToString(corrected));
                } else {
                    faultyLabel.setText("Faulty Data: No error introduced");
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
        // Diziyi ters sırayla yazdır (sağdan sola)
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}