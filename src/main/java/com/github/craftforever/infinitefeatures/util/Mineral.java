package com.github.craftforever.infinitefeatures.util;

import java.awt.Color;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class Mineral {
	public String name;
	public Material material;
	public float lightlevel;
	public String toolType;
	public int harvestLevel;
	public float hardness;
	public float resistance;
	public SoundType sound;
	public Color color;
	
	
	public Mineral(String iname, Material imaterial, float ilightlevel, String itoolType,int iharvestLevel,float ihardness,float iresistance,SoundType isound,Color icolor) {
		name = iname;
		material = imaterial;
		lightlevel = ilightlevel;
		toolType = itoolType;
		harvestLevel = iharvestLevel;
		hardness = ihardness;
		resistance = iresistance;
		sound = isound;
		color = icolor;
	}

}
