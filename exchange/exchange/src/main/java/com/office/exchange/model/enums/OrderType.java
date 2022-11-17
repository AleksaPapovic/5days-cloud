package com.office.exchange.model.enums;

public enum OrderType {
   BUY("BUY"), 
   SELL("SELL");
   
   public final String label;

   private OrderType(String label) {
       this.label = label;
   }
}
