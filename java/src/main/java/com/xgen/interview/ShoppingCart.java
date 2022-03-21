package com.xgen.interview;

import java.lang.reflect.Array;
import java.util.*;

/**
 * This is the current implementation of ShoppingCart.
 * Please write a replacement
 */
public class ShoppingCart implements IShoppingCart {

  /**
   *
   * Switched from HashMap to LinkedHashMap as linked maps
   * retain insertion Order
   *
   */
  LinkedHashMap<String, Integer> contents = new LinkedHashMap<>();
  Pricer pricer;

  public ShoppingCart(Pricer pricer) {
    this.pricer = pricer;
  }

  //created ENUM's to be able to switch
  //between different price display options
  public enum PriceDisplay {
    PRICE_BEFORE,
    PRICE_AFTER,
  }

  public void addItem(String itemType, int number) {
    if (!contents.containsKey(itemType)) {
      contents.put(itemType, number);
    } else {
      int existing = contents.get(itemType);
      contents.put(itemType, existing + number);
    }
  }

  public void printReceipt(PriceDisplay priceorder) {
    float totalP = 0f;

    // Loop through the LinkedHashMap
    for (Map.Entry<String, Integer> entry : contents.entrySet()) {
      //get the item from the map
      String itemType = entry.getKey();
      int price = pricer.getPrice(itemType);

      //get quantity of item
      int q = entry.getValue();

      //get the price and add to total price
      float priceFloat = (float) (price / 100) * q;
      totalP += priceFloat;
      String priceString = String.format("€%.2f", priceFloat);
      String totalString = String.format("€%.2f", totalP);

      //switch case to allow for the different output types
      switch (priceorder) {
        case PRICE_BEFORE:
          System.out.println(priceString + " - " + itemType + q + " - ");
          break;
        case PRICE_AFTER:
          System.out.println(itemType + " - " + q + " - " + priceString);
          break;
      }
      System.out.println(totalString);
    }
  }

  @Override
  public void printReceipt() {
    printReceipt(PriceDisplay.PRICE_BEFORE);
  }
}
