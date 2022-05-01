package com.example.budgetmanager;

public class model {
   String item,note,amount,date;
   int weeks,months;

   model()
   {

   }

   public model(String item, String note, String amount, String date, int weeks, int months) {
      this.item = item;
      this.note = note;
      this.amount = amount;
      this.date = date;
      this.weeks = weeks;
      this.months = months;
   }

   public String getItem() {
      return item;
   }

   public void setItem(String item) {
      this.item = item;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public String getAmount() {
      return amount;
   }

   public void setAmount(String amount) {
      this.amount = amount;
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public int getWeeks() {
      return weeks;
   }

   public void setWeeks(int weeks) {
      this.weeks = weeks;
   }

   public int getMonths() {
      return months;
   }

   public void setMonths(int months) {
      this.months = months;
   }
}
