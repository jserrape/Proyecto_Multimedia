/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_multimedia;

import static proyecto_multimedia.CapturaWC.templateBI;
import com.github.sarxos.webcam.WebcamImageTransformer;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 *
 * @author Markax
 */
public class TransformarImg implements WebcamImageTransformer {

    public TransformarImg() {
        this.marco = null;
    }

    /**
     *
     * @param image: frame de imagen a la que se le aplica una transformación
     * @return imagen ya transformada
     */
    @Override
    public BufferedImage transform(BufferedImage image) {
        // Esto sería para el filtro --> image = filterBI(image,filter);
        image = templateBI(image, marco);
        return image;
    }

    private BufferedImage marco;

    /**
     * Método para insertar marcos
     * @param nombreMarco titulo del marco a insertar en la imagen
     */
    public void setTemplate(String nombreMarco) {
        try {
            marco = (nombreMarco == null) ? null : ImageIO.read(new FileInputStream(Paths.get(Paths.get(".").toAbsolutePath().normalize().toString(), "marcos", nombreMarco).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
