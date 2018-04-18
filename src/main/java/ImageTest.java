import net.sourceforge.tess4j.util.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by jcl on 2018/3/15
 */
public class ImageTest {
    public static void main(String[] args) throws Exception {
        File f = new File("d:address.png");
        BufferedImage img = ImageIO.read(f);
//        img = ImageHelper.convertImageToGrayscale(img);//转黑白
//        img = ImageHelper.rotateImage(img,90);//旋转
//        img = ImageHelper.getScaledInstance(img, img.getWidth() * 3, img.getHeight() * 3);//放大
        img = ImageHelper.invertImageColor(img);//反转颜色
        ImageIO.write(img, "png", new File("d:\\black.png"));
    }
}
