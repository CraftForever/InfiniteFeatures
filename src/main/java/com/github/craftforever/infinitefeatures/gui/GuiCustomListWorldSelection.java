package com.github.craftforever.infinitefeatures.gui;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.craftforever.infinitefeatures.proxy.ClientProxy;
import com.google.common.collect.Lists;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCustomListWorldSelection extends GuiListExtended
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final GuiCustomWorldSelection worldSelection;
    private final List<GuiCustomListWorldSelectionEntry> entries = Lists.<GuiCustomListWorldSelectionEntry>newArrayList();
    /** Index to the currently selected world */
    public int selectedIdx = -1;

    public GuiCustomListWorldSelection(GuiCustomWorldSelection gcws, Minecraft clientIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn)
    {
        super(clientIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.worldSelection = gcws;
        this.refreshList();
    }

    public void refreshList()
    {
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        List<WorldSummary> list;

        try
        {
            list = isaveformat.getSaveList();
        }
        catch (AnvilConverterException anvilconverterexception)
        {
            LOGGER.error("Couldn't load level list", (Throwable)anvilconverterexception);
            this.mc.displayGuiScreen(new GuiErrorScreen(I18n.format("selectWorld.unable_to_load"), anvilconverterexception.getMessage()));
            return;
        }

        Collections.sort(list);

        for (WorldSummary worldsummary : list)
        {
            this.entries.add(new GuiCustomListWorldSelectionEntry(this, worldsummary, this.mc.getSaveLoader()));
        }
    }

    /**
     * Gets the IGuiListEntry object for the given index
     */
    public GuiCustomListWorldSelectionEntry getListEntry(int index)
    {
        return this.entries.get(index);
    }

    protected int getSize()
    {
        return this.entries.size();
    }

    protected int getScrollBarX()
    {
        return super.getScrollBarX() + 20;
    }

    /**
     * Gets the width of the list
     */
    public int getListWidth()
    {
        return super.getListWidth() + 50;
    }

    public void selectWorld(int idx)
    {
    	if(!ClientProxy.fastLoad) {
    		this.selectedIdx = idx;
            this.worldSelection.selectWorld(this.getSelectedWorld());
    	}else {
    		selectedIdx = ClientProxy.fastIndex;
    	}
        
    }

    /**
     * Returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int slotIndex)
    {
        return slotIndex == this.selectedIdx;
    }

    @Nullable
    public GuiCustomListWorldSelectionEntry getSelectedWorld()
    {
        return this.selectedIdx >= 0 && this.selectedIdx < this.getSize() ? this.getListEntry(this.selectedIdx) : null;
    }

    public GuiCustomWorldSelection getCustomGuiWorldSelection()
    {
        return this.worldSelection;
    }

	public GuiWorldSelection getGuiWorldSelection() {
		return this.worldSelection.guiWorldSelect;
	}
}