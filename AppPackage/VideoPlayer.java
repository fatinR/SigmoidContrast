package AppPackage;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class VideoPlayer {

	private String videoFile;

	public void setMediaFile (final String vf){
		this.videoFile = vf;
	}

	private String getMediaFile (){
		return this.videoFile;
	}

	private Scene createScene () {
		final Group  root  =  new  Group();
		//exception
		final Scene scene = new Scene (root, 1100, 600, Color.BLACK);
		final Media media = new Media (getMediaFile());
		final MediaPlayer mPlayer = new MediaPlayer(media);		
		mPlayer.setAutoPlay(true);
		final MediaControl mediaControl = new MediaControl(mPlayer);
		scene.setRoot(mediaControl);
		return (scene);	    
	}

	public Scene getScene() {

		final Scene scene = createScene();
		return scene;
	}
}
