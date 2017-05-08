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

    /**
     * Constructor de la ventana de error
     * @param mensajee: Mensaje que se mostrará en la ventana de error
     */
    public VentanaError(String mensajee) {
        this.mensaje = mensajee;
    }

    /**
     *Visualización de la ventana de error
     */
    public void mostrar() {
        JOptionPane.showMessageDialog(new JFrame(), mensaje, "Dialog", JOptionPane.ERROR_MESSAGE);
    }

}
