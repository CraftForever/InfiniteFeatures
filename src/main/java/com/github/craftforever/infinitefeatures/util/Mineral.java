package com.github.craftforever.infinitefeatures.util;

import static com.github.craftforever.infinitefeatures.helpers.RandomHelper.getRandomIntInRange;
import static com.github.craftforever.infinitefeatures.helpers.RandomHelper.getRandomBoolean;

import java.awt.Color;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class Mineral
{
	public String name;
	public Color color;
	public Item dropitem;
	public int quality;
	public int genmaxy;
	public int gensize;
	public int genrarity;
	public boolean edible;
	public boolean isGem;
	public String underlay;
	public Material material;
	private int randNum;
	private String[] underlays = new String[] {"dirt","sand","stone","netherrack","endstone"};
	private Material[] materials = new Material[] {Material.GROUND,Material.SAND,Material.ROCK,Material.ROCK,Material.ROCK};
	//smaller is rarer

	public Mineral(String iname, Color icolor,int iquality)
	{
		name = iname;
		color = icolor;
		quality = iquality;
		genmaxy = getRandomIntInRange(50/iquality,80/iquality);
		gensize = getRandomIntInRange(5,10)/iquality;
		genrarity = getRandomIntInRange(25,50)/iquality;
		edible = getRandomBoolean(0.5F);
		randNum = getRandomIntInRange(0,4);
		underlay = underlays[randNum];
		material = materials[randNum];
		isGem = getRandomBoolean(0.5F);
		/*
		System.out.print("\n\n"+name+": "+quality+"\n");
		System.out.print("genmaxy : "+genmaxy+"\n");
		System.out.print("gensize : "+gensize+"\n");
		System.out.print("genrarity : "+genrarity+"\n");
		*/
	}
	
}