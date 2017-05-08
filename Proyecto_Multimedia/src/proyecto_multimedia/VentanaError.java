/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_multimedia;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class VentanaError {

    private final String mensaje;

    public VentanaError(String mensajee) {
        this.mensaje = mensajee;
    }

    public void mostrar() {
        JOptionPane.showMessageDialog(new JFrame(), mensaje, "Dialog", JOptionPane.ERROR_MESSAGE);
    }

}
