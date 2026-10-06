module com.example.hellofx {
    requires javafx.controls;
    exports com.example.hellofx;
    opens com.example.hellofx to javafx.base;
}