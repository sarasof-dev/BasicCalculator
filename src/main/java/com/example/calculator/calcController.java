
package com.example.calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.ArrayList;

public class calcController {

    @FXML
    private Label pantalla;

    // Botones numericos
    @FXML
    private Button cero, uno, dos, tres, cuatro,
            cinco, seis, siete, ocho, nueve;

    // Operaciones
    @FXML
    private Button suma, resta, div, multi;

    // Operaciones de calculo
    @FXML
    private Button clear, igual;

    // Diseño
    @FXML
    private VBox base;

    @FXML
    private ToggleButton mode;

    private boolean modoOscuro = false;

    // NUMEROS

    @FXML
    private void agregarCero() {
        agregarDigito("0");
    }

    @FXML
    private void agregarUno() {
        agregarDigito("1");
    }

    @FXML
    private void agregarDos() {
        agregarDigito("2");
    }

    @FXML
    private void agregarTres() {
        agregarDigito("3");
    }

    @FXML
    private void agregarCuatro() {
        agregarDigito("4");
    }

    @FXML
    private void agregarCinco() {
        agregarDigito("5");
    }

    @FXML
    private void agregarSeis() {
        agregarDigito("6");
    }

    @FXML
    private void agregarSiete() {
        agregarDigito("7");
    }

    @FXML
    private void agregarOcho() {
        agregarDigito("8");
    }

    @FXML
    private void agregarNueve() {
        agregarDigito("9");
    }

    // OPERACIONES

    @FXML
    private void accionSuma() {
        seleccionarOperacion("+");
    }

    @FXML
    private void accionResta() {
        seleccionarOperacion("-");
    }

    @FXML
    private void accionMultiplicacion() {
        seleccionarOperacion("*");
    }

    @FXML
    private void accionDivision() {
        seleccionarOperacion("/");
    }

    @FXML
    private void accionDecimal() {
        seleccionarOperacion(".");
    }

    // IGUAL Y CLEAR

    @FXML
    private void accionIgual() {
        calcular();
    }

    @FXML
    private void accionClear() {
        limpiar();
    }

    // MODO CLARO Y OSCURO

    @FXML
    private void cambiarModo() {

        modoOscuro = !modoOscuro;

        if (modoOscuro) {

            // MODO OSCURO
            base.setStyle("-fx-background-color: #512B61;");

            aplicarEstiloBotones(
                    "rgba(230, 193, 193, 0.18)",
                    "#E8EFFF"
            );

            pantalla.setStyle(
                    "-fx-background-color: #392345;" +
                            "-fx-text-fill: #E8EFFF;" +
                            "-fx-border-color: #A9BCEB;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 8;" +
                            "-fx-background-radius: 8;"
            );

            mode.setText("☀");

        } else {

            // MODO CLARO
            base.setStyle("-fx-background-color: #E8EBFF;");

            aplicarEstiloBotones(
                    "rgba(255, 255, 255, 0.65)",
                    "#BF2B2B"
            );

            pantalla.setStyle(
                    "-fx-background-color: #D9DDF5;" +
                            "-fx-text-fill: #BF2B2B;" +
                            "-fx-border-color: #BF2B2B;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 8;" +
                            "-fx-background-radius: 8;"
            );

            mode.setText("☾");
        }
    }

    // ESTILO DE LOS BOTONES

    private void aplicarEstiloBotones(String fondo, String texto) {

        for (Node nodo : base.lookupAll(".calc-button")) {

            nodo.setStyle(
                    "-fx-background-color: " + fondo + ";" +
                            "-fx-text-fill: " + texto + ";" +
                            "-fx-background-radius: 10;" +
                            "-fx-border-radius: 10;"
            );
        }
    }

    // LOGICA DE LA CALCULADORA

    private String expresion = "";
    private boolean nuevoNumero = true;

    private void agregarDigito(String digito) {

        if (nuevoNumero) {
            expresion += digito;
            nuevoNumero = false;

        } else {
            expresion += digito;
        }

        pantalla.setText(expresion);
    }

    private void seleccionarOperacion(String op) {

        if (expresion.isEmpty()) {
            return;
        }

        if (expresion.endsWith("+") ||
                expresion.endsWith("-") ||
                expresion.endsWith("*") ||
                expresion.endsWith("/")) {

            expresion = expresion.substring(0, expresion.length() - 1);
        }

        expresion += op;

        pantalla.setText(expresion);

        nuevoNumero = true;
    }

    private double evaluarExpresion(String texto) {

        List<Double> numeros = new ArrayList<>();
        List<String> operadores = new ArrayList<>();

        String numero = "";

        for (int i = 0; i < texto.length(); i++) {

            char caracter = texto.charAt(i);

            if (Character.isDigit(caracter) || caracter == '.') {

                numero += caracter;

            } else {

                numeros.add(Double.parseDouble(numero));
                numero = "";

                operadores.add(String.valueOf(caracter));
            }
        }

        numeros.add(Double.parseDouble(numero));

        // Primero multiplicacion y division
        for (int i = 0; i < operadores.size(); i++) {

            String op = operadores.get(i);

            if (op.equals("*") || op.equals("/")) {

                double a = numeros.get(i);
                double b = numeros.get(i + 1);

                if (op.equals("/") && b == 0) {
                    throw new ArithmeticException("ERROR");
                }

                double resultado = op.equals("*") ? a * b : a / b;

                numeros.set(i, resultado);
                numeros.remove(i + 1);
                operadores.remove(i);

                i--;
            }
        }

        // Despues suma y resta
        double resultado = numeros.get(0);

        for (int i = 0; i < operadores.size(); i++) {

            double siguiente = numeros.get(i + 1);

            if (operadores.get(i).equals("+")) {
                resultado += siguiente;
            } else {
                resultado -= siguiente;
            }
        }

        return resultado;
    }


    private void calcular() {

        if (expresion.isEmpty()) {
            return;
        }

        if (expresion.endsWith("+") ||
                expresion.endsWith("-") ||
                expresion.endsWith("*") ||
                expresion.endsWith("/")) {
            return;
        }

        try {

            double resultado = evaluarExpresion(expresion);

            pantalla.setText(
                    resultado == (long) resultado
                            ? String.valueOf((long) resultado)
                            : String.valueOf(resultado)
            );

            expresion = pantalla.getText();
            nuevoNumero = true;

        } catch (ArithmeticException | NumberFormatException | IndexOutOfBoundsException e) {

            pantalla.setText("Error");
            expresion = "";
            nuevoNumero = true;

        }
    }


    private void limpiar() {

        pantalla.setText("0");

        expresion = "";

        nuevoNumero = true;
    }
}
