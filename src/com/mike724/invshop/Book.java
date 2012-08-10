/* Credits to Ne0nx3r0 for this class! Thank you! */
package com.mike724.invshop;

import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

public class Book
{
  private String author;
  private String title;
  private String[] pages;

  public Book(org.bukkit.inventory.ItemStack bookItem)
  {
    NBTTagCompound bookData = ((CraftItemStack)bookItem).getHandle().tag;

    this.author = bookData.getString("author");
    this.title = bookData.getString("title");

    NBTTagList nPages = bookData.getList("pages");

    String[] sPages = new String[nPages.size()];
    for (int i = 0; i < nPages.size(); i++)
    {
      sPages[i] = nPages.get(i).toString();
    }

    this.pages = sPages;
  }

  Book(String title, String author, String[] pages) {
    this.title = title;
    this.author = author;
    this.pages = pages;
  }

  public String getAuthor()
  {
    return this.author;
  }

  public void setAuthor(String sAuthor)
  {
    this.author = sAuthor;
  }

  public String getTitle()
  {
    return this.title;
  }

  public String[] getPages()
  {
    return this.pages;
  }

  public org.bukkit.inventory.ItemStack generateItemStack() {
    CraftItemStack newbook = new CraftItemStack(Material.WRITTEN_BOOK);

    NBTTagCompound newBookData = new NBTTagCompound();

    newBookData.setString("author", this.author);
    newBookData.setString("title", this.title);

    NBTTagList nPages = new NBTTagList();
    for (int i = 0; i < this.pages.length; i++)
    {
      nPages.add(new NBTTagString(this.pages[i], this.pages[i]));
    }

    newBookData.set("pages", nPages);

    newbook.getHandle().tag = newBookData;

    return newbook;
  }
}