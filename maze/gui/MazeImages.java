package gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import logic.Logic;

public class MazeImages {
	private ArrayList<BufferedImage> images;
	private static String resourcesFolder = System.getProperty("user.dir")+"\\maze\\gui\\resources\\";
	
	public MazeImages() {
		images = new ArrayList<BufferedImage>();
		
		try {
			images.add(ImageIO.read(new File(resourcesFolder+"empty.png")));
			images.add(ImageIO.read(new File(resourcesFolder+"wall.png")));
			images.add(ImageIO.read(new File(resourcesFolder+"sword.png")));
			
			images.add(ImageIO.read(new File(resourcesFolder+"arrow.png"))); //3
			images.add(ImageIO.read(new File(resourcesFolder+"dragon.png")));
			images.add(ImageIO.read(new File(resourcesFolder+"hero.png")));
			
			images.add(ImageIO.read(new File(resourcesFolder+"shield.png"))); //6
			images.add(ImageIO.read(new File(resourcesFolder+"fire.png")));
		} catch (IOException e) {
			System.out.println("Error Loading Images");
			System.exit(0);
		}
	}

	public ArrayList<BufferedImage> get(char c) {
		ArrayList<BufferedImage> imagesToDraw = new ArrayList<BufferedImage>();
		
		// Wall
		if(c == Logic.wallSymbol) {
			imagesToDraw.add(images.get(1));
		
		//Dragon
		} else if(c == Logic.dragonSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(4).getSubimage(0, 0, 50, 50));
		} else if(c == Logic.dragonSleepSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(4).getSubimage(50, 0, 50, 50));
		
		// Dragon on Objects
		} else if(c == Logic.dragonDartSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(3));
			imagesToDraw.add(images.get(4).getSubimage(0, 0, 50, 50));
		} else if(c == Logic.dragonSleepDartSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(3));
			imagesToDraw.add(images.get(4).getSubimage(50, 0, 50, 50));
		} else if(c == Logic.dragonSwordSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(2));
			imagesToDraw.add(images.get(4).getSubimage(0, 0, 50, 50));
		} else if(c == Logic.dragonSleepSwordSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(2));
			imagesToDraw.add(images.get(4).getSubimage(50, 0, 50, 50));
		} else if(c == Logic.dragonShieldSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(6));
			imagesToDraw.add(images.get(4).getSubimage(0, 0, 50, 50));
		} else if(c == Logic.dragonSleepShieldSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(6));
			imagesToDraw.add(images.get(4).getSubimage(50, 0, 50, 50));
		
		// Objects
		} else if(c == Logic.swordSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(2));
		} else if(c == Logic.dartSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(3));
		} else if(c == Logic.shieldSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(6));
		
		// Hero
		} else if(c == Logic.heroSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(5).getSubimage(0, 0, 50, 50));
		} else if(c == Logic.heroSwordSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(5).getSubimage(100, 0, 50, 50));
		} else if(c == Logic.heroSwordShieldSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(5).getSubimage(150, 0, 50, 50));
		} else if(c == Logic.heroShieldSymbol) {
			imagesToDraw.add(images.get(0));
			imagesToDraw.add(images.get(5).getSubimage(50, 0, 50, 50));
			
		
		}else
			imagesToDraw.add(images.get(0));
		
		return imagesToDraw;
	}

	public Image fire() {
		return images.get(7);
	}
}
