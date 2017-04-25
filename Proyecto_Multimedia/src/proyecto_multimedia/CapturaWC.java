/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_multimedia;

/**
 *
 * @author Markax
 */

import com.github.sarxos.webcam.Webcam;
// import com.jhlabs.image.*;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComboBox;
import javax.swing.JList;

/**
 * <b>Implementa las operaciones lógicas de la aplicación y de interacción con la interfaz.</b>
 */
public class CapturaWC {
    
    /**
     * Instancias de filtros disponibles.
     */
    private static final BufferedImageOp[] filters = new BufferedImageOp[] {
//        new DitherFilter(),
//        new ExposureFilter(),
//        new GammaFilter(),
//        new GaussianFilter(10),
//        new GlowFilter(),
//        new GrayscaleFilter(),
//        new InvertFilter(),
//        new KaleidoscopeFilter(),
//        new LightFilter(),
//        new NoiseFilter(),
//        new SharpenFilter(),
//        new SolarizeFilter(),
//        new SphereFilter(),
//        new ThresholdFilter(),
//        new WaterFilter(),
//        new PinchFilter(),
//        new EdgeFilter(),
//        new WeaveFilter(),
//        new EmbossFilter(),
//        new PolarFilter(),
//        new LookupFilter()
    };

    /**
     * <b>Genera la lista de webcams disponibles en el sistema y lo almacena en una lista desplegable de Java.</b>
     * @param jcb Lista desplegable de Java.
     */
    public static Webcam getListWebcam(JComboBox jcb, Webcam active) {
        jcb.removeAllItems();
        if (active!=null) jcb.addItem(active);
        List<Webcam> l = Webcam.getWebcams();
        for (Webcam w : l) {
            if (active!=w && !w.getLock().isLocked()) jcb.addItem(w);
        }
        for (Webcam w : l) {
            if (active!=w && w.getLock().isLocked()) jcb.addItem(w);
        }
        return (l.isEmpty()) ? null : (active!=null) ? active : (Webcam)jcb.getItemAt(0);
    }
    
    public static Dimension[] updateResolution(Webcam activeWebcam, JComboBox jcb) {
        jcb.removeAllItems();
        Dimension[] ds = activeWebcam.getViewSizes();
        for (int i=ds.length-1; i>=0; --i) {
            jcb.addItem(ds[i].toString().substring(18));
        }
        activeWebcam.setViewSize(ds[ds.length-1]);
        return ds;
    }
   
    /**
     * <b>Dada una imagen y un factor de escala, aumenta o disminuye la imagen (ocupando las mismas dimensiones).</b>
     * @param img Imagen de entrada.
     * @param sx Factor de escala en X.
     * @param sy Factor de escala en Y.
     * @return Imagen de salida con la escala aplicada.
     */
    public static BufferedImage scaleBI(BufferedImage img, float sx, float sy) {
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        g2.scale(sx,sy);  
        g2.drawImage(img,null,0,0);
        return newImage;    
    }
    
    /**
     * <b>Adapta una imagen a la resolución X,Y.</b>
     * @param input Entrada de imagen.
     * @param x Tamaño para x.
     * @param y Tamaño para y.
     * @return Imagen de salida adaptada.
     */
    public static BufferedImage prepareFI(BufferedImage input, int x, int y) {
        float sx = ((float)x)/input.getWidth(),
              sy = ((float)y)/input.getHeight();
        return (sx!=1 || sy!=1) ? scaleBI(input,sx,sy) : input;
    }
    
    /**
     * <b>Dada una imagen y el índice+1 de un filtro, lo aplica a la imagen.</b>
     * Si el índice es 0, no aplica filtro.
     * @param image Imagen de entrada.
     * @param filter Índice+1 del filtro a emplear del vector de filtros disponibles {@link #filters}
     * @return Imagen de salida filtrada.
     */
    public static BufferedImage filterBI(BufferedImage image, int filter) {
        if (filter>0) {
            BufferedImage modified = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            filters[filter-1].filter(image, modified);
            modified.flush();
            return modified;
        } else {
            return image;
        }
    }
    
    /**
     * <b>Dada una imagen y una plantilla, la superpone en la imagen.</b>
     * @param image Imagen de entrada.
     * @param template Imagen plantilla de entrada.
     * @return Imagen de salida correspondiente a la imagen de entrada con la plantilla superpuesta.
     */
    public static BufferedImage templateBI(BufferedImage image, BufferedImage template) {
        if (template!=null) {
            BufferedImage modified = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            template = prepareFI(template, image.getWidth(), image.getHeight());
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
}

