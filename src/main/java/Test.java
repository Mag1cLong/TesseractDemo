import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;
import net.sourceforge.tess4j.util.LoadLibs;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by jcl on 2018/3/14
 */
public class Test {
    public static void main(String[] args) throws Exception {
//        detectFace("d:id.png");
        detectFaceOrRotateImg("d:id.png");
//        String fileName = "d:id.png";
//        File f = new File(fileName);
//        BufferedImage img = ImageIO.read(f);
//        img = ImageHelper.rotateImage(img, 90);//旋转
//        ImageIO.write(img, "png", new File(fileName));
    }

    /**
     * 根据面部坐标获取身份证号区域图片
     * @param faceCoordinate
     * @param fileName
     * @return
     */
    static BufferedImage getIdnoImg(int[] faceCoordinate,String fileName){
        return null;
    }

    /**
     * 检测脸部，检测不到则旋转90度继续检测
     * @param fileName
     * @throws Exception
     */
    static void detectFaceOrRotateImg(String fileName) throws Exception {
        System.loadLibrary("opencv_java330");
        int i = 4;
        while (i > 0) {
            int[] array = detectFace(fileName);
            if (array[4] == 1) break;
            System.out.println("i:" + i);
            File f = new File(fileName);
            BufferedImage img = ImageIO.read(f);
            img = ImageHelper.rotateImage(img, 90);//旋转
            ImageIO.write(img, "png", new File(fileName));
            i--;
        }
    }

    /**
     * 识别中文
     * @param fileName
     */
    static void readCh(String fileName) {
        try {
            File imageFile = new File(fileName);
            Tesseract tessreact = new Tesseract();
            File tessDataFolder = LoadLibs.extractTessResources("tessdata");
            tessreact.setLanguage("chi_sim");
            tessreact.setDatapath(tessDataFolder.getAbsolutePath());
            String result = tessreact.doOCR(imageFile);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 识别英文
     * @param fileName
     */
    static void readEng(String fileName) {
        try {
            File imageFile = new File(fileName);
            Tesseract tessreact = new Tesseract();
            File tessDataFolder = LoadLibs.extractTessResources("tessdata");
            tessreact.setLanguage("eng");//英文库识别数字比较准确
            tessreact.setDatapath(tessDataFolder.getAbsolutePath());
            String result = tessreact.doOCR(imageFile);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 识别数字
     * @param fileName
     */
    static void readNum(String fileName) {
        readEng(fileName);
    }

    /**
     * 识别面部
     * @param fileName
     * @return
     */
    static int[] detectFace(String fileName) {
        int[] rectPosition = new int[5];
//        CascadeClassifier faceDetector = new CascadeClassifier("E:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_default.xml");
        CascadeClassifier faceDetector = new CascadeClassifier("E:\\opencv\\sources\\data\\lbpcascades\\lbpcascade_frontalface.xml");
        Mat image = Imgcodecs.imread(fileName);
        MatOfRect faceDetections = new MatOfRect();
        Size minSize = new Size(120, 120);
        Size maxSize = new Size(250, 250);
        faceDetector.detectMultiScale(image, faceDetections, 1.1f, 4, 0, minSize, maxSize);
        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        rectPosition[4] = 0;
        for (Rect rect : faceDetections.toArray()) {
            System.out.println(rect.toString());
            rectPosition[0] = rect.x;
            rectPosition[1] = rect.y;
            rectPosition[2] = rect.width;
            rectPosition[3] = rect.height;
            rectPosition[4] = 1;
        }
        return rectPosition;
    }

}
