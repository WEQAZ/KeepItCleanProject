package clean.it.keep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

//import java.lang.reflect.Array;

public class Player {
    private final Rectangle rectangle = new Rectangle();
    private Texture texture;
//    private Array<Texture> textures;

//    public Player(int x, int y, int width, int height, String[] paths) {
public Player(int x, int y, int width, int height, String paths) {
        this.rectangle.x = x;
        this.rectangle.y = y;
        this.rectangle.width = width;
        this.rectangle.height = height;
        this.texture = new Texture(Gdx.files.internal(paths));
//        System.out.println(paths.length);
//        for(Iterator<Texture> iter = textures.iterator(); iter.hasNext();){
//           System.out.println(paths[i] +" " +i);
//            Texture texture = iter.next();
//            this.textures[iter] = new Texture(Gdx.files.internal(paths[iter]));
//        }

//        for (int i=0; i < paths.length; i++) {
//          System.out.println(paths[i] +" " +i);
//          this.textures[iter.next()] = new Texture(Gdx.files.internal(paths[iter.next()]));
//        }
//        this.texture = new Texture(Gdx.files.internal(paths[1])); //this.textures[0]; //bin start color
//        this.texture = new Texture(Gdx.files.internal("redBin")); //this.textures[0]; //bin start color
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public Texture getTexture() {
        return this.texture;
    }

//    public void setColor(int x) {
////        this.texture = textures[x];
//        this.texture = texture;
//    }
}
