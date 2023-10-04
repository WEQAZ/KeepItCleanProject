package clean.it.keep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
public class GameScreen implements Screen {

    final KeepItClean game;
    private OrthographicCamera camera;
    private Texture dropImage;
    private Sound dropSound;
    private Music rainMusic;
    private Player player1;
    private Array<Rectangle> trashDrops;
    private Array<String> raindropPaths;
    private long lastDropTime;
    private int player1Score = 0;
    private int player1Speed = 600;
    private int spawnDiff = 500000000;
    private int dropSpeed = 200;
    private int dropVib = 10;
    private int dropleaks = 0;
    final Texture Background;
    private String[] trashPaths = {"banana.png","battery.png","glass-bottle.png","plastic-bag.png"};




    public GameScreen(final KeepItClean game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        String[] paths = {"blueBin.png","redBin.png","greenBin.png","yellowBin.png"};
        player1 = new Player(704, 20, 64, 64, paths);

        dropImage = new Texture(Gdx.files.internal("banana.png"));
        trashDrops = new Array<Rectangle>();
        spawnRaindrop();

        dropSound = Gdx.audio.newSound(Gdx.files.internal("garbageSoundEffect.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("bgMusic.mp3"));

        Background = new Texture(Gdx.files.internal("background.jpg"));

        rainMusic.setLooping(true);
    }


    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        trashDrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }



    @Override
    public void render (float delta) {
        ScreenUtils.clear(0,0,0.4f,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            player1.setTexture(1); // Change to the redBin.png texture (index 1)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player1.setTexture(0); // Change to the redBin.png texture (index 0)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            player1.setTexture(2); // Change to the redBin.png texture (index 2)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            player1.setTexture(3); // Change to the redBin.png texture (index 3)
        }


        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) )
            player1.getRectangle().x -= player1Speed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) )
            player1.getRectangle().x += player1Speed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP) )
            player1.getRectangle().y += player1Speed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) )
            player1.getRectangle().y -= player1Speed * Gdx.graphics.getDeltaTime();

        if(player1.getRectangle().x < 0)
            player1.getRectangle().x = 0;
        if(player1.getRectangle().x > 800 - 64)
            player1.getRectangle().x = 800 - 64;
        if(player1.getRectangle().y < 0)
            player1.getRectangle().y = 0;
        if(player1.getRectangle().y > 480 - 64)
            player1.getRectangle().y = 480 -64;

        if(TimeUtils.nanoTime() - lastDropTime > spawnDiff)
            spawnRaindrop();

        for (Iterator<Rectangle> iter = trashDrops.iterator(); iter.hasNext(); ) {
            Rectangle raindrop = iter.next();
            raindrop.y -= dropSpeed * Gdx.graphics.getDeltaTime();
            raindrop.x += MathUtils.random(-dropVib, dropVib) * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0) {
                dropleaks++;
                iter.remove();
            }
            if(raindrop.overlaps(player1.getRectangle())) {
                dropSound.play();
                player1Score++;
                if (player1Score % 5 == 0) {
                    player1Speed += 50;
                }
                iter.remove();
            }
        }



        ScreenUtils.clear(0, 0, 0.3f, 1);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.batch.draw(Background,0,0);
        for(Rectangle raindrop: trashDrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.draw(player1.getTexture(), player1.getRectangle().x, player1.getRectangle().y);
        game.font.draw(game.batch, "P1's score: "+ player1Score + ", speed: "+ player1Speed + "\n"
                + "Drop leaks: "+ dropleaks, 50, 460);
        game.batch.end();

    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        player1.getTexture().dispose();
        dropSound.dispose();
        rainMusic.dispose();
        Background.dispose();
    }

}