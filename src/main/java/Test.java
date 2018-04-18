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
//        String fileName = "d:id.png";
//        File f = new File(fileName);
//        BufferedImage img = ImageIO.read(f);
//        img = ImageHelper.rotateImage(img, 90);//旋转
//        ImageIO.write(img, "png", new File(fileName));
//        File f = new File("d:id.png");
//        BufferedImage img = ImageIO.read(f);
//        img = ImageHelper.getSubImage(img, 230, 360, 320, 50);
//        ImageIO.write(img, "png", new File("d:test.png"));

//getFaceCoordinate("d:id.png");
        getIdnoImg(getFaceCoordinate("d:id.png"), "d:id.png");
        getNameImg(getFaceCoordinate("d:id.png"), "d:id.png");
        getSexImg(getFaceCoordinate("d:id.png"), "d:id.png");
        getBirthdayImg(getFaceCoordinate("d:id.png"), "d:id.png");
        getBirthMonthImg(getFaceCoordinate("d:id.png"), "d:id.png");
        getBirthYearImg(getFaceCoordinate("d:id.png"), "d:id.png");
        getAddressImg(getFaceCoordinate("d:id.png"), "d:id.png");
        System.out.println("姓名：" + readCh("d:name.png"));
        System.out.println("性别：" + readCh("d:sex.png"));
        System.out.println("生日：" + readNum("d:birthYear.png") + "年" + readNum("d:birthMonth.png") + "月" + readNum("d:birthday.png") + "日");
        System.out.println("地址：" + readCh("d:address.png"));
        System.out.println("身份证号：" + readNum("d:num.png"));
    }

    /**
     * 根据面部坐标获取身份证号区域图片
     *
     * @param faceCoordinate
     * @param fileName
     * @return
     */
    static void getIdnoImg(int[] faceCoordinate, String fileName) throws Exception {
        File f = new File(fileName);
        int x = faceCoordinate[0] - 190;
        int y = faceCoordinate[1] + 190;
        BufferedImage img = ImageIO.read(f);
        img = ImageHelper.getSubImage(img, x, y, 320, 50);
        ImageIO.write(img, "png", new File("d:num.png"));
    }

    /**
     * 根据面部坐标获取姓名区域图片
     *
     * @param faceCoordinate
     * @param fileName
     * @return
     */
    static void getNameImg(int[] faceCoordinate, String fileName) throws Exception {
        File f = new File(fileName);
        int x = faceCoordinate[0] - 270;
        int y = faceCoordinate[1] - 55;
        BufferedImage img = ImageIO.read(f);
        img = ImageHelper.getSubImage(img, x, y, 120, 50);
        ImageIO.write(img, "png", new File("d:name.png"));
    }

    /**
     * 根据面部坐标获取性别区域图片
     *
     * @param faceCoordinate
     * @param fileName
     * @return
     */
    static void getSexImg(int[] faceCoordinate, String fileName) throws Exception {
        File f = new File(fileName);
        int x = faceCoordinate[0] - 265;
        int y = faceCoordinate[1] - 10;
        BufferedImage img = ImageIO.read(f);
        img = ImageHelper.getSubImage(img, x, y, 50, 40);
        ImageIO.write(img, "png", new File("d:sex.png"));
    }


    /**
     * 根据面部坐标获取生日-日区域图片
     *
     * @param faceCoordinate
     * @param fileName
     * @return
     */
    static void getBirthdayImg(int[] faceCoordinate, String fileName) throws Exception {
        File f = new File(fileName);
        int x = faceCoordinate[0] - 130;
        int y = faceCoordinate[1] + 30;
        BufferedImage img = ImageIO.read(f);
        img = ImageHelper.getSubImage(img, x, y, 35, 40);
        ImageIO.write(img, "png", new File("d:birthday.png"));
    }

    /**
     * 根据面部坐标获取生日-月区域图片
     *
     * @param faceCoordinate
     * @param fileName
     * @return
     */
    static void getBirthMonthImg(int[] faceCoordinate, String fileName) throws Exception {
        File f = new File(fileName);
        int x = faceCoordinate[0] - 180;
        int y = faceCoordinate[1] + 30;
        BufferedImage img = ImageIO.read(f);
        img = ImageHelper.getSubImage(img, x, y, 35, 40);
        ImageIO.write(img, "png", new File("d:birthMonth.png"));
    }

    /**
     * 根据面部坐标获取生日-年区域图片
     *
     * @param faceCoordinate
     * @param fileName
     * @return
     */
    static void getBirthYearImg(int[] faceCoordinate, String fileName) throws Exception {
        File f = new File(fileName);
        int x = faceCoordinate[0] - 265;
        int y = faceCoordinate[1] + 30;
        BufferedImage img = ImageIO.read(f);
        img = ImageHelper.getSubImage(img, x, y, 65, 40);
        ImageIO.write(img, "png", new File("d:birthYear.png"));
    }

    /**
     * 根据面部坐标获取地址区域图片
     *
     * @param faceCoordinate
     * @param fileName
     * @return
     */
    static void getAddressImg(int[] faceCoordinate, String fileName) throws Exception {
        File f = new File(fileName);
        int x = faceCoordinate[0] - 280;
        int y = faceCoordinate[1] + 70;
        BufferedImage img = ImageIO.read(f);
        img = ImageHelper.getSubImage(img, x, y, 260, 80);
        ImageIO.write(img, "png", new File("d:address.png"));
    }

    /**
     * 检测脸部，检测不到则旋转90度继续检测
     *
     * @param fileName
     * @throws Exception
     */
    static int[] getFaceCoordinate(String fileName) throws Exception {
        System.loadLibrary("opencv_java330");
        int[] faceCoordinate = {};
        int i = 4;
        while (i > 0) {
            faceCoordinate = detectFace(fileName);
            if (faceCoordinate[4] == 1) break;
            File f = new File(fileName);
            BufferedImage img = ImageIO.read(f);
            img = ImageHelper.rotateImage(img, 90);//旋转
            ImageIO.write(img, "png", new File(fileName));
            i--;
        }
//        System.out.println(faceCoordinate[0]);
//        System.out.println(faceCoordinate[1]);
//        System.out.println(faceCoordinate[2]);
//        System.out.println(faceCoordinate[3]);
        return faceCoordinate;
    }


    /**
     * 识别中文
     *
     * @param fileName
     */
    static String readCh(String fileName) {
        String result = "";
        try {
            File imageFile = new File(fileName);
            Tesseract tessreact = new Tesseract();
            File tessDataFolder = LoadLibs.extractTessResources("tessdata");
            tessreact.setLanguage("chi_sim");
            tessreact.setDatapath(tessDataFolder.getAbsolutePath());
            result = tessreact.doOCR(imageFile);
//            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /**
     * 识别英文
     *
     * @param fileName
     */
    static String readEng(String fileName) {
        String result = "";
        try {
            File imageFile = new File(fileName);
            Tesseract tessreact = new Tesseract();
            File tessDataFolder = LoadLibs.extractTessResources("tessdata");
            tessreact.setLanguage("eng");//英文库识别数字比较准确
            tessreact.setDatapath(tessDataFolder.getAbsolutePath());
            result = tessreact.doOCR(imageFile);
//            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /**
     * 识别数字
     *
     * @param fileName
     */
    static String readNum(String fileName) {
        return readEng(fileName);
    }

    /**
     * 识别面部
     *
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
//            System.out.println(rect.toString());
            rectPosition[0] = rect.x;
            rectPosition[1] = rect.y;
            rectPosition[2] = rect.width;
            rectPosition[3] = rect.height;
            rectPosition[4] = 1;
        }
        return rectPosition;
    }

}
