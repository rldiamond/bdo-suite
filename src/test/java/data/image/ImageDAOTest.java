package data.image;

import javafx.scene.image.Image;
import org.junit.Test;

public class ImageDAOTest {

    @Test
    public void test() {
        Image image = ImageDAO.getInstance().getImage(721003);
        System.out.println("yeet");
    }

}
