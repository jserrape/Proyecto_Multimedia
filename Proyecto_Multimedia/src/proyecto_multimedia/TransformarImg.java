/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_multimedia;

import com.github.sarxos.webcam.WebcamImageTransformer;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import com.jhlabs.image.*;
import java.awt.Graphics2D;

/**
 *
 * @author Markax
 */
public class TransformarImg implements WebcamImageTransformer {

    public TransformarImg() {
        this.marco = null;
        this.filtro = 0;
    }

    /**
     *
     * @param image: frame de imagen a la que se le aplica una transformación
     * @return imagen ya transformada
     */
    @Override
    public BufferedImage transform(BufferedImage image) {
        image = createFilter(image, filtro);
        image = createTemplate(image, marco);
        return image;
    }

    private BufferedImage marco;
    private int filtro;

    /**
     * Método para insertar marcos
     *
     * @param nombreMarco titulo del marco a insertar en la imagen
     */
    public void setTemplate(String nombreMarco) {
        try {
            marco = (nombreMarco == null) ? null : ImageIO.read(new FileInputStream(Paths.get(Paths.get(".").toAbsolutePath().normalize().toString(), "marcos", nombreMarco).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFilter(int _filtro){
       filtro = _filtro;
    }
    
    
    private static final BufferedImageOp[] listaFiltros = new BufferedImageOp[]{
        new DitherFilter(),
        new ExposureFilter(),
        new GammaFilter(),
        new GaussianFilter(10),
        new GlowFilter(),
        new GrayscaleFilter(),
        new InvertFilter(),
        new KaleidoscopeFilter(),
        new LightFilter(),
        new NoiseFilter(),
        new SharpenFilter(),
        new SolarizeFilter(),
        new SphereFilter(),
        new ThresholdFilter(),
        new WaterFilter(),
        new PinchFilter(),
        new EdgeFilter(),
        new WeaveFilter(),
        new EmbossFilter(),
        new PolarFilter(),
        new LookupFilter()
    };

    public static BufferedImage createFilter(BufferedImage image, int filtro) {
        if (filtro != 0) {
            BufferedImage modified = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            listaFiltros[filtro - 1].filter(image, modified);
            modified.flush();
            return modified;
        } else {
            return image;
        }
    }

    public static BufferedImage createTemplate(BufferedImage image, BufferedImage template) {
        if (template != null) {
            BufferedImage modified = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            template = scaleTemplate(template, image.getWidth(), image.getHeight());
            Graphics2D g2 = modified.createGraphics();
            g2.drawImage(image, null, 0, 0);
            g2.drawImage(template, null, 0, 0);
            g2.dispose();
            modified.flush();
            return modified;
        } else {
            return image;
        }
    }
    
    /**
     * 
     * Reescala un marco para que se adapte a la cam
     *
     * @param template marco a comprobar.
     * @param x Ancho del marco.
     * @param y Alto del marco.
     * @return marco escalado.
     */
    public static BufferedImage scaleTemplate(BufferedImage template, int x, int y) {
        float ancho = ((float) x) / template.getWidth();
        float alto = ((float) y) / template.getHeight();
        if (ancho != 1 || alto != 1) {
            BufferedImage templateEscalado = new BufferedImage(template.getWidth(), template.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D G = templateEscalado.createGraphics();
            G.scale(ancho, alto);
            G.drawImage(template, null, 0, 0);
            return templateEscalado;
        } else {
            return template;
        }
    }
}
