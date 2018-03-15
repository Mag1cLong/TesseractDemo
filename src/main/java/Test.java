import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import java.io.File;

/**
 * Created by jcl on 2018/3/14
 */
public class Test {
    public static void main(String[] args) {
        try {
            File imageFile = new File("D:/name.png");
            Tesseract tessreact = new Tesseract();
            File tessDataFolder = LoadLibs.extractTessResources("tessdata");
//            tessreact.setLanguage("eng");//英文库识别数字比较准确
            tessreact.setLanguage("chi_sim");
            System.out.println(tessDataFolder.getAbsolutePath());
            tessreact.setDatapath(tessDataFolder.getAbsolutePath());
            String result = tessreact.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}
