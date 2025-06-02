module com.example.hamming {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hamming to javafx.fxml;
    exports com.example.hamming;
}