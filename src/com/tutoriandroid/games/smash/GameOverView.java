package com.tutoriandroid.games.smash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.gdacarv.engine.androidgame.GameView;
import com.gdacarv.engine.androidgame.Sprite;

public class GameOverView extends GameView {

	private Paint paintText;
	private Context context;
	private int score, highScore;

	public GameOverView(Context context, AttributeSet att) {
		super(context);
		this.context = context;
	}

	@Override
	public void TouchEvents(MotionEvent event) {
		if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN){
			Context ctx = getContext();
			((Activity) ctx).finish();
			Intent intent = new Intent(ctx, MainGameActivity.class);
			ctx.startActivity(intent);
		}
	}

	@Override
	protected void onLoad() {
		Resources res = getResources();
		Sprite gameover; 
		mSprites.add(gameover = new Sprite(BitmapFactory.decodeResource(res, R.drawable.gameover)));
		gameover.x = getWidth()/2 - gameover.width/2;
		gameover.y = (int) (getHeight()*0.2f);
		paintText = new Paint();
		paintText.setColor(Color.WHITE);
		paintText.setTextSize(25);
		score = ((Activity) context).getIntent().getIntExtra("SCORE", 0);
		
		MediaPlayer musica = MediaPlayer.create(context, R.raw.gameover);
		musica.start();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		highScore = prefs.getInt("HIGH_SCORE", 0);
		if(score > highScore)
			prefs.edit().putInt("HIGH_SCORE", score).commit();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawText(context.getString(R.string.score) + " " + score, 50, getHeight()*0.6f, paintText);
		canvas.drawText(context.getString(R.string.iniciar_jogo), 50, getHeight()*0.82f, paintText);
		canvas.drawText(context.getString(R.string.highscore)+" "+highScore, 50, getHeight()*0.68f, paintText);
	}

}
