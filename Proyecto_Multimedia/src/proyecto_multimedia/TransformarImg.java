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

    @Override
    public BufferedImage transform(BufferedImage image) {
        // Esto sería para el filtro --> image = filterBI(image,filter);
        image = templateBI(image, marco);
        return image;
    }

    private BufferedImage marco = null;

    public void setTemplate(String nombreMarco) {
//        marco = (nombreMarco==null) ? null : getImage(generateRoute("marcos",nombreMarco));
//        marco = (nombreMarco==null) ? null : getImage(Paths.get(Paths.get(".").toAbsolutePath().normalize().toString(), "marcos", nombreMarco).toString());
        try {
            marco = (nombreMarco == null) ? null : ImageIO.read(new FileInputStream(Paths.get(Paths.get(".").toAbsolutePath().normalize().toString(), "marcos", nombreMarco).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
