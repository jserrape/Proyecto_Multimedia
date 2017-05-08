package proyecto_multimedia;

import com.github.sarxos.webcam.Webcam;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

public class hiloVideo implements Runnable {
    //Variables del hilo de video
    private final Webcam cam;
    private boolean stop;

    /**
     * Constructor de hiloVideo
     * @param c webcam activa
     */
    public hiloVideo(Webcam c) {
        this.cam = c;
    }

    /**
     * Detiene la grabación de video
     */
    public void parar() {
        this.stop = true;
    }

    @Override
    public void run() {
        File file;
        IMediaWriter writer;
        Dimension size;
        long start;
        
        file = new File(String.format("video-%d.mp4", System.currentTimeMillis()));       
        writer = ToolFactory.makeWriter(file.getAbsolutePath());
        size = cam.getViewSize();
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);
       
        start = System.currentTimeMillis();

        int i = 0;
        while (!stop) {
            BufferedImage image = ConverterFactory.convertToType(cam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
            IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);
            IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
            frame.setKeyFrame(i == 0);
            frame.setQuality(100);
            writer.encodeVideo(0, frame);
            ++i;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(hiloVideo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        writer.close();
    }
}
