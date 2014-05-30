/*

Copyright [2014] [ASIER ARRANZ - asierarranz@gmail.com - www.asierarranz.com - @asierarranz]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


*/


// Mover vídeos: adb push "video.mp4" /mnt/sdcard/Movies/
package com.asierarranz.bbvapp;

import android.app.Activity; 
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.media.Sounds;
import com.asierarranz.bbvapp.R;


import java.io.File;

import java.util.Random;


//import java.util.ArrayList;

public class MainActivity extends Activity {

	private AudioManager maManager;
	private String mMovieDirectory;
	private GestureDetector mGestureDetector;
	private TextToSpeech mSpeech;
	private BroadcastReceiver mIntentBlocker;
	public boolean menuprincipal=true;
	public boolean menureus1=false;
	public boolean menureus2=false;
	public boolean menureus3=false;
	public boolean menureus4=false;
	public boolean menupadel1=false;
	public boolean menupadel2=false;
	public boolean getplayer=false;
	public boolean menurestauracion=false;
	public boolean menuguardepor=false;
	public boolean menureusCalendar=false;
	public boolean menureusGmail=false;
	public String valorSala="Galileo";
	public String valorDia="Hoy";
	public String valorDiaPadel="Hoy";
	public int valorHora=9;
	ArrayAdapter<String> menuList=null;
	Touchpad touchpad=null;
	ListView listView=null;
	Handler handler=null;
	Runnable runnable=null;
	public int opcionmenuprincipal=1;
	public int opcionguardepor=1;
	public int frameanimation=0;
	public int animationiterations=0;
	public boolean animationrunning=false;
	public boolean uploaded=false;
	public boolean menunointernet=false;
	public int icafe=0;
	public int irest1=0;
	public int irest2=0;
	
	
	protected void onCreate(Bundle savedInstanceState) 
	{
						getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
						super.onCreate(savedInstanceState);
						maManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
						mMovieDirectory = Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_MOVIES; 
						enableTTS();
						bloquearGuinyo();
						playVideo("1.mp4");
						createMenuPrincipal();
						enableTouchPad();
						

					   
	}
	
	
	public void enableTTS()
	{
		mSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() 
		{
			public void onInit(int status) {} // Para que funcione el TTS
		});
	}
	
	public void createMenuPrincipal()
	{

		
		reset();
		setContentView(R.layout.layout);
	}
	
	public void enableTouchPad()
	{
		touchpad = new Touchpad(this);
		mGestureDetector = new GestureDetector(this);
		mGestureDetector.setBaseListener(touchpad);
		mGestureDetector.setFingerListener(touchpad);
		mGestureDetector.setScrollListener(touchpad);
	}
	
	public void comando(String cmd)
	{
		switch (cmd)
		{
		case "swipe_left":
			maManager.playSoundEffect(Sounds.SELECTED);
			
			if (menuprincipal)
			{
					if (opcionmenuprincipal==1)
					{
						opcionmenuprincipal=2;
						((ImageView)findViewById (R.id.imageViewMenuPrincipal)).setImageDrawable(getResources().getDrawable(R.drawable.menuprincipal2));
					}
					else if (opcionmenuprincipal==2)
					{
						opcionmenuprincipal=3;
						((ImageView)findViewById (R.id.imageViewMenuPrincipal)).setImageDrawable(getResources().getDrawable(R.drawable.menuprincipal3));
					
					}
					
			}
			else if (menureus1)
			{
					if (valorSala.equals("Galileo"))
					{
					((TextView)findViewById (R.id.TextViewSala)).setText ("Aristóteles");
					valorSala="Aristóteles";
					}
					else if (valorSala.equals("Aristóteles"))
					{
					((TextView)findViewById (R.id.TextViewSala)).setText ("Einstein");
					valorSala="Einstein";
					}
			}
			else if (menureus2)
			{
					if (valorDia.equals("Hoy"))
					{
					((TextView)findViewById (R.id.TextViewDia)).setText ("Mañana");
					valorDia="mañana";
					}
					else if (valorDia.equals("mañana"))
					{
					((TextView)findViewById (R.id.TextViewDia)).setText ("Pasado mañana");
					valorDia="Pasado mañana";
					}
			}
			else if (menureus3)
			{
				valorHora++;
				if (valorHora>20)
					valorHora=20;
				((TextView)findViewById (R.id.TextViewHora)).setText (valorHora+ ":00");
			}
			else if (menureus4)
			{
				maManager.playSoundEffect(Sounds.DISMISSED);
				menureus4=false;
				valorSala="Galileo";
				valorDia="Hoy";
				valorHora=9;
				setContentView(R.layout.reus1);
				createMenuPrincipal();
			}
			
			else if (menurestauracion)
			{
				maManager.playSoundEffect(Sounds.DISALLOWED);
			}
			
			else if (menupadel1)
			{
				menurestauracion=false;
				if (valorDiaPadel.equals("Hoy"))
				{
				((TextView)findViewById (R.id.TextViewDiaPadel)).setText ("Mañana");
				valorDiaPadel="Mañana";
				}
			}
			else if (menupadel2)
			{
				getplayer();
			}
			
			else if (menuguardepor)
			{
					if (opcionguardepor==1)
					{
						opcionguardepor=2;
						((ImageView)findViewById (R.id.imageViewGuardepor)).setImageDrawable(getResources().getDrawable(R.drawable.guardepor2));
					}
					
			}
			
			break;
		case "swipe_right":
			maManager.playSoundEffect(Sounds.SELECTED);
			if (menuprincipal)
				{
				if (opcionmenuprincipal==3)
				{
					opcionmenuprincipal=2;
					((ImageView)findViewById (R.id.imageViewMenuPrincipal)).setImageDrawable(getResources().getDrawable(R.drawable.menuprincipal2));
				}
				else if (opcionmenuprincipal==2)
				{
					opcionmenuprincipal=1;
					((ImageView)findViewById (R.id.imageViewMenuPrincipal)).setImageDrawable(getResources().getDrawable(R.drawable.menuprincipal1));
				
				}
				
				}
			else if (menureus1)
			{
					if (valorSala.equals("Einstein"))
					{
					((TextView)findViewById (R.id.TextViewSala)).setText ("Aristóteles");
					valorSala="Aristóteles";
					}
					else if (valorSala.equals("Aristóteles"))
					{
					((TextView)findViewById (R.id.TextViewSala)).setText ("Galileo");
					valorSala="Galileo";
					}
			}
			else if (menureus2)
			{
					if (valorDia.equals("Pasado mañana"))
					{
					((TextView)findViewById (R.id.TextViewDia)).setText ("Mañana");
					valorDia="mañana";
					}
					else if (valorDia.equals("mañana"))
					{
					((TextView)findViewById (R.id.TextViewDia)).setText ("Hoy");
					valorDia="Hoy";
					}
			}
			else if (menureus3)
			{
				valorHora--;
				((TextView)findViewById (R.id.TextViewHora)).setText (valorHora+ ":00");
				if (valorHora<8)
					valorHora=8;
			}
			else if (menureus4)
			{
				maManager.playSoundEffect(Sounds.DISMISSED);
				menureus4=false;
				valorSala="Galileo";
				valorDia="Hoy";
				valorHora=9;
				createMenuPrincipal();
			}
			
			else if (menurestauracion)
			{
				maManager.playSoundEffect(Sounds.DISALLOWED);
			}
			else if (menupadel1)
			{
				menurestauracion=false;
				if (valorDiaPadel.equals("Mañana"))
				{
				((TextView)findViewById (R.id.TextViewDiaPadel)).setText ("Hoy");
				valorDiaPadel="Hoy";
				}
			}
			else if (menuguardepor)
			{
					if (opcionguardepor==2)
					{
						opcionguardepor=1;
						((ImageView)findViewById (R.id.imageViewGuardepor)).setImageDrawable(getResources().getDrawable(R.drawable.guardepor1));
					}
					
			}
			else if (menupadel2)
			{
				getplayer();
			}
			
			break;
			
		case "tap":
			maManager.playSoundEffect(Sounds.TAP);
			if (menuprincipal)
			{
			if (opcionmenuprincipal==1)
				menu1();
			if (opcionmenuprincipal==2)
				menu2();
			if (opcionmenuprincipal==3)
				menu3();
}
			else if (menureus1)
			{
				menureus1=false;
				menureus2=true;
			    setContentView(R.layout.reus2);
			}
			else if (menureus2)
			{
				menureus2=false;
				menureus3=true;
			    setContentView(R.layout.reus3);
			}
			else if (menureus3)
			{
				menureus3=false;
				menureus4=true;
			    confirmareus();
			}
			else if (menureus4)
			{
				menureus4=false;
				menureusCalendar=true;
				animationrunning=true;
				uploading();
			}
			
			else if (menurestauracion)
			{
				handler.removeCallbacks(runnable);
				menurestauracion=false;
				createMenuPrincipal();
			}
			
			else if (uploaded)
			{
				uploaded=false;
				createMenuPrincipal();
			}
			else if (menupadel1)
			{
				menupadel1=false;
				getplayer();
			}
			else if(menuguardepor)
			{
				if (opcionguardepor==1)
				{
					menuguardepor=false;
					asignarjugadorpadel();
				}
				else if (opcionguardepor==2)
				{
					menuguardepor=false;
					if (isNetworkAvailable())
					leertrabajoshijos();
					else{
						setContentView(R.layout.nointernet);	
						menunointernet=true;
						maManager.playSoundEffect(Sounds.ERROR);
						
					}
					
					
				}
			}
			else if (menunointernet)
			{
				menunointernet=false;
				createMenuPrincipal();
			}
			else if (menupadel2)
			{
				menupadel2=false;
				menureusGmail=true;
				animationrunning=true;
				uploading();
			}
			
				
			
			break;
			case "swipe_down":
				if (menuprincipal)
				{
					maManager.playSoundEffect(Sounds.DISALLOWED);
					finish();
				}
				else if (menurestauracion)
				{
					handler.removeCallbacks(runnable);
					createMenuPrincipal();
				}
				else 
				{
					maManager.playSoundEffect(Sounds.DISALLOWED);
					createMenuPrincipal();
				}
			break;
			}
		}
	
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	public void asignarjugadorpadel()
	{
		menuguardepor=false;
		menupadel1=true;
		setContentView(R.layout.padel1);
	}
	
	public void leertrabajoshijos()
	{
		menuguardepor=false;
		String url = "http://myoffice.glass/nursery/?child=AsierArranz";
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		startActivity(intent);
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		createMenuPrincipal();

	}
	
	public void uploading()
	{
		
		
		if (menureusCalendar)
			setContentView(R.layout.reuscalendar);
		else if (menureusGmail)
			setContentView(R.layout.gmail);
		
		runnable = new Runnable() {
			   @Override
			   public void run() {
				  if (animationrunning)
				  {
					  if (menureusCalendar)
				      nextuploadingcalendar();
					  else if (menureusGmail)
					  nextuploadinggmail();
				      handler.postDelayed(this, 100);
				  }
			   }
			};
			
		handler = new Handler();
		handler.postDelayed(runnable, 100);
		
		
		
	}
	
	
	
		
	public void nextuploadingcalendar()
	{
		
		if (frameanimation==0)
			((ImageView)findViewById (R.id.imageViewUploading)).setImageDrawable(getResources().getDrawable(R.drawable.uploading1));

		if (frameanimation==1)
			((ImageView)findViewById (R.id.imageViewUploading)).setImageDrawable(getResources().getDrawable(R.drawable.uploading2));

		if (frameanimation==2)
			((ImageView)findViewById (R.id.imageViewUploading)).setImageDrawable(getResources().getDrawable(R.drawable.uploading3));

		if (frameanimation==3)
			((ImageView)findViewById (R.id.imageViewUploading)).setImageDrawable(getResources().getDrawable(R.drawable.uploading4));

		if (frameanimation==4)
		{
			((ImageView)findViewById (R.id.imageViewUploading)).setImageDrawable(getResources().getDrawable(R.drawable.uploading5));
			animationiterations++;
			frameanimation=-1;
		}
		
		frameanimation++;
		if (animationiterations>5)
		{
			maManager.playSoundEffect(Sounds.SUCCESS);
			animationiterations=0;
			handler.removeCallbacks(runnable);
			animationrunning=false;
			((ImageView)findViewById (R.id.imageViewUploading)).setImageDrawable(getResources().getDrawable(R.drawable.uploaded));
			uploaded=true;
			runnable = new Runnable() {
				   @Override
				   public void run() {
					      
							String url = "http://myoffice.glass/meeting/?dia=hoy&hora=18&sala=Galilea";
							Intent intent = new Intent(Intent.ACTION_VIEW);
							intent.setData(Uri.parse(url));
							startActivity(intent);
							try{Thread.sleep(2000);} catch (InterruptedException e) {}
							createMenuPrincipal();
					      
				   }
				};
				
			handler = new Handler();
			insertcalendar();
			handler.postDelayed(runnable, 1000);
		}
		
		
	}
	
	public void insertcalendar()
	{
		
	}
	
	public void nextuploadinggmail()
	{
		
		if (frameanimation==0)
			((ImageView)findViewById (R.id.imageViewUploading)).setImageDrawable(getResources().getDrawable(R.drawable.uploadinggmail1));

		if (frameanimation==1)
			((ImageView)findViewById (R.id.imageViewUploading)).setImageDrawable(getResources().getDrawable(R.drawable.uploadinggmail2));

		if (frameanimation==2)
			((ImageView)findViewById (R.id.imageViewUploading)).setImageDrawable(getResources().getDrawable(R.drawable.uploadinggmail3));

		if (frameanimation==3)
			((ImageView)findViewById (R.id.imageViewUploading)).setImageDrawable(getResources().getDrawable(R.drawable.uploadinggmail4));

		if (frameanimation==4)
		{
			((ImageView)findViewById (R.id.imageViewUploading)).setImageDrawable(getResources().getDrawable(R.drawable.uploadingmail5));
			animationiterations++;
			frameanimation=-1;
		}
		
		frameanimation++;
		if (animationiterations>5)
		{
			maManager.playSoundEffect(Sounds.SUCCESS);
			animationiterations=0;
			handler.removeCallbacks(runnable);
			animationrunning=false;
			((ImageView)findViewById (R.id.imageViewUploading)).setImageDrawable(getResources().getDrawable(R.drawable.uploadedgmail));
			uploaded=true;
			runnable = new Runnable() {
				   @Override
				   public void run() {
					      createMenuPrincipal();
				   }
				};
				
			handler = new Handler();
			handler.postDelayed(runnable, 2000);
		}
		
		
	}
	
	
	public void confirmareus()
	{
		
		setContentView(R.layout.reus4);
		((TextView)findViewById (R.id.TextViewConfirma1)).setText ("Has reservado la sala "+ valorSala);
		((TextView)findViewById (R.id.TextViewConfirma2)).setText ("Para "+ valorDia.toLowerCase() + " a las " + valorHora+":00");
		
	}
	
	public void bloquearGuinyo()
	{
		mIntentBlocker = new BroadcastReceiver() 
		{
			public void onReceive(Context context, Intent intent) 
			{
				abortBroadcast();	
			}
		};
	IntentFilter filter = new IntentFilter();
	filter.addAction("com.google.glass.action.EYE_GESTURE");
	registerReceiver(mIntentBlocker, filter);
	}
	
	
	
	
	
	
	
	
	public void playVideo(String filename)
	{
		try
		{
			Log.d("asierlog", "dentro playvideo");
			String path = mMovieDirectory+"/"+filename;
			Log.d("asierlog", path);
			File file = new File(path);
			if (!file.exists()) {
				return;
			}
			Intent i0 = new Intent();
			i0.setAction("com.google.glass.action.VIDEOPLAYER");
			i0.putExtra("video_url", path);
			Log.d("asierlog", path);
			startActivity(i0);
		}
		catch(Exception e){}
	}
	
	
	
	
	
	
	
	
	public boolean onGenericMotionEvent(MotionEvent event) 
	{
		if (mGestureDetector != null) 
		{
			return mGestureDetector.onMotionEvent(event);
		}
		return false;
	}

	
	public void menu1()
	{
		playVideo("2.mp4");
		try{Thread.sleep(900);} catch (InterruptedException e) {}
		menuprincipal=false;
		menureus1=true;
		setContentView(R.layout.reus1);
	}
	
	public void menu2()
	{
		playVideo("3.mp4");
		try{Thread.sleep(900);} catch (InterruptedException e) {}
		menuprincipal=false;
		menurestauracion=true;
		setContentView(R.layout.restauracion);
		loadSensorValues();
	}
	
	public void loadSensorValues()
	{
		Random rnd=new Random();

		icafe = 70+rnd.nextInt(21);
		irest1 = 50+rnd.nextInt(18);
		irest2 = 20+rnd.nextInt(15);

	    
	
	    runnable = new Runnable() {
			   @Override
			   public void run() {
				Random rnd=new Random();
				icafe = icafe-1+rnd.nextInt(3);
				irest1 = irest1-1+rnd.nextInt(3);
				irest2 = irest2-1+rnd.nextInt(3);
				((TextView)findViewById (R.id.textValorCafe)).setText (icafe+"%"); 
			    ((TextView)findViewById (R.id.textValorRest1)).setText (irest1+"%"); 
			    ((TextView)findViewById (R.id.textValorRest2)).setText (irest2+"%"); 
				handler.postDelayed(this, 2000);
			   }
			};
			
		handler = new Handler();
		handler.postDelayed(runnable, 2000);

	}
	
	public void menu3()
	{
		playVideo("4.mp4");
		try 
			{
				Thread.sleep(900); // Para que le de tiempo a cargar el vídeo sin mostrar la siguiente pantalla antes
			} 
				catch (InterruptedException e) {
			}
		menuprincipal=false;
		setContentView(R.layout.guardepor);
		menuguardepor=true;
	}
	
	public void getplayer()
	{

		getplayer=true;
		setContentView(R.layout.padel1);
		((TextView)findViewById (R.id.TextViewQueDiaPadel)).setText ("Buscando");
		((TextView)findViewById (R.id.TextViewDiaPadel)).setText ("jugadores...");
		
		runnable = new Runnable() {
			   @Override
			   public void run() {
				      getplayer2();
			   }
			};
			
		handler = new Handler();
		handler.postDelayed(runnable, 2000);
	}
	
	public void getplayer2()
	{
		menupadel1=false;
		menupadel2=true;	
		setContentView(R.layout.jugadorelegido);
		Random r=new Random();
		String nombres[]=  {"Jorge López", "Amanda Benito", "Luis Nuñez", "Jon Beltrán", "Jose Durango", "Alejandro Benito", "María López", "Layla Verde", "Gisela Montero", "Ivan Castro","Alberto Moreno", "Virginia Jiménez", "Ignacio García", "Marcos Caballero"};
		String cargos[]=  {"Marketing", "Corporativo", "Comunicación", "Estrategia", "Finanzas", "Informática", "Redes Sociales", "Business Development", "Innovación", "Innovación","Blue BBVA", "BlueBBVA", "Dirección general", "Pymes"};
		int i1 = r.nextInt(14);
		((TextView)findViewById (R.id.TextViewJugador1)).setText (nombres[i1]);
		((TextView)findViewById (R.id.TextViewugador2)).setText ("Área de "+ cargos[i1]);
		
		
	}
	
	
	
	public void say(String filename) 
	{
		int dot = filename.lastIndexOf(".");
		if (dot != -1) 
		{
			filename = filename.substring(0, dot);
		}
		mSpeech.speak(filename, TextToSpeech.QUEUE_FLUSH, null);
		Log.d("asierlog", "Diciendo: "+filename);
	}


	public void reset()
	{
		menuprincipal=true;
		menureus1=false;
		menureus2=false;
		menureus3=false;
		menureus4=false;
		menurestauracion=false;
		menuguardepor=false;
		menureusCalendar=false;
		valorSala="Galileo";
		valorDia="Hoy";
		valorDiaPadel="Hoy";
		valorHora=9;
		menuList=null;
		touchpad=null;
		listView=null;
		handler=null;
		runnable=null;
		opcionmenuprincipal=1;
		opcionguardepor=1;
		frameanimation=0;
		animationiterations=0;
		animationrunning=false;
		uploaded=false;
		menunointernet=false;
		menupadel1=false;
		menupadel2=false;
		getplayer=false;
	}
	protected void onDestroy() 
	{
		super.onDestroy();
		mSpeech.shutdown();
		unregisterReceiver(mIntentBlocker);
	}

}
