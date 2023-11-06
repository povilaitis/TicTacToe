module lt.viko.eif.vpovilaitis.hellofx {
    requires javafx.controls;
    requires javafx.fxml;


    opens lt.viko.eif.vpovilaitis.hellofx to javafx.fxml;
    exports lt.viko.eif.vpovilaitis.hellofx;
}