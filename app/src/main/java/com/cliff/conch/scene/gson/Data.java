package com.cliff.conch.scene.gson;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public String name;
    public List<Item> items;

    public Data() {
        this.name = "cliff";
        this.items = new ArrayList<>();
        items.add(new Item("item1"));
        items.add(new Item("item2"));
        items.add(new Item("item3"));
        items.add(new Item("item4"));
    }

    public static class Item {
        public String itemName = "item";
        public Item(String itemName) {
            this.itemName = itemName;
        }
    }
}
