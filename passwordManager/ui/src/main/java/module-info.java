module ui {
  requires core;
  requires client;
  requires javafx.controls;
  requires javafx.fxml;
  requires java.net.http;

  opens ui to javafx.graphics, javafx.fxml;
}
