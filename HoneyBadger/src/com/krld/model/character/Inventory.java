package com.krld.model.character;

import com.krld.model.items.Collective;

import java.io.Serializable;
import java.util.ArrayList;

public class Inventory implements Serializable {
    private ArrayList<Collective> items;
      public Inventory(){
          items = new ArrayList<Collective>();
      }
    public ArrayList<Collective> getItems() {
        return items;
    }

    public void setItems(ArrayList<Collective> items) {
        this.items = items;
    }
    public String toString () {
        return printItems();
    }

    public String printItems() {
        String toString = "Items in inventory:" ;
        for (Collective item : items) {
            toString += item + ", ";
        }
        return toString;
    }


}
