/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_multimedia;

import static proyecto_multimedia.CapturaWC.filterBI;
import static proyecto_multimedia.CapturaWC.generateRoute;
import static proyecto_multimedia.CapturaWC.getImage;
import static proyecto_multimedia.CapturaWC.templateBI;
import com.github.sarxos.webcam.WebcamImageTransformer;
import java.awt.image.BufferedImage;

/**
 *
 * @author Markax
 */
public class TransformarImg implements WebcamImageTransformer {
    
    @Override
    public BufferedImage transform(BufferedImage image) {
        // Esto sería para el filtro --> image = filterBI(image,filter);
        image = templateBI(image,marco);
        return image;
    }
    
    private BufferedImage marco = null;
    
    public void setTemplate(String nombreMarco) {
        marco = (nombreMarco==null) ? null : getImage(generateRoute("marcos",nombreMarco));
    }
    
}
