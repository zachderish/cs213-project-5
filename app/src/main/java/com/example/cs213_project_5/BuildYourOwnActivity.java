package com.example.cs213_project_5;

import static com.example.cs213_project_5.PizzaMaker.createPizza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Define Build Your Own Activity class.
 * @author Kenrick Eagar, Zachary Derish
 */

public class BuildYourOwnActivity extends AppCompatActivity {

    TextView listToppings;
    TextView priceBox;
    boolean[] selectedToppings;
    ArrayList<Integer> topArray = new ArrayList<>();
    CheckBox exCheese, exSauce;
    Spinner sauceSpinner, sizeSpinner;
    Button addOrder;


    private Pizza pizza = createPizza("Build Your Own");

    String[] toppings = {"SAUSAGE", "PEPPERONI", "GREEN_PEPPERS", "ONION", "MUSHROOM", "BLACK_OLIVE",
                        "BEEF", "HAM", "SHRIMP","SQUID","CRAB_MEATS","PINEAPPLE","PICKLES","CHICKEN",
                        "FRIED_EGG", "BACON", "SPINACH", "HAMBURGER"};

    /**
     * Method to get topping enum based off string input
     * @param input, string representation of topping
     * @return topping enum
     */
    private Topping getTopping(String input){
        Topping[] tempToppings = Topping.BEEF.getList();
        for(int i =0; i<tempToppings.length; i++){
            String temp = tempToppings[i].toString();
            if(temp.equals(input.toUpperCase())){
                return tempToppings[i];
            }
        }


        return null;
    }

    /**
     * Method to get String to updated price
     * @return String representing price
     */
    private String updatePrice(){
        double price = this.pizza.price();
        return Double.toString(price);
    }


    /**
     * Method to search for a specific number within arrayList and remove it
     * @param list, Generic Integer ArrayList that contains no duplicate numbers
     * @param target, number we want to remove
     */
    private void removeElement(ArrayList<Integer> list, int target){
        for(int i = 0; i<list.size(); i++){
            if(list.get(i) == target){
                list.remove(i);
                return;
            }
        }
    }
    /**
     * Method to initialize and show toppingsDialogue
     */
    private void showToppingsDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(BuildYourOwnActivity.this);
        builder.setTitle("Select Toppings");
        builder.setCancelable(false);
        builder.setMultiChoiceItems(toppings, selectedToppings, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    topArray.add(i);
                    Collections.sort(topArray);
                } else {
                    removeElement(topArray, i);
                }
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override //action once OK is selected
            public void onClick(DialogInterface dialogInterface, int i) {
                StringBuilder stringBuilder = new StringBuilder();
                pizza.toppings.clear();
                for (int j = 0; j < topArray.size(); j++) {
                    stringBuilder.append(toppings[topArray.get(j)]);
                    pizza.toppings.add(getTopping(toppings[topArray.get(j)]));
                    if (j != topArray.size() - 1) {
                        stringBuilder.append(", ");
                    }
                }
                listToppings.setText(stringBuilder.toString());
                priceBox.setText(updatePrice());
            }
        });
        builder.show();
    }

    /**
     * Method to get Sauce based off string input
     * @param sauce, string representation of sauce type
     * @return sauce enum
     */
   private Sauce getSauce(String sauce){
        if(sauce.equals("Tomato")){
            return Sauce.TOMATO;
        }
        return Sauce.ALFREDO;
   }
    /**
     * Method to get Size based off string input
     * @param size, string representation of size type
     * @return size enum
     */
   private Size getSize(String size){
        if(size.equals("Small")){
            return Size.SMALL;
        }
       if(size.equals("Medium")){
           return Size.MEDIUM;
       }
       return Size.LARGE;
   }

    /**
     * Method to add extra cheese, when checkbox is selected
     * @param view, the current view
     */
   public void onExCheeseClick(View view){
       if(exCheese.isChecked()){
           pizza.extraCheese = true;
           priceBox.setText(updatePrice());
       } else{
           pizza.extraCheese = false;
           priceBox.setText(updatePrice());
       }
   }
    /**
     * Method to add extra sauce, when checkbox is selected
     * @param view, the current view
     */
   public void onExSauceClick(View view){
       if(exSauce.isChecked()){
           pizza.extraSauce = true;
           priceBox.setText(updatePrice());
       } else{
           pizza.extraSauce = false;
           priceBox.setText(updatePrice());
       }
   }

    /**
     * Method to initialize and fill sauce spinner
     */
   private void initializeSauceSpinner(){
       ArrayList<String> sauceList = new ArrayList<>();
       sauceList.add("Tomato");
       sauceList.add("Alfredo");
       ArrayAdapter<String> sauceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,sauceList);
       sauceAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
       sauceSpinner.setAdapter(sauceAdapter);
   }
    /**
     * Method to initialize and fill size spinner
     */
   private void initializeSizeSpinner(){
       ArrayList<String> sizeList = new ArrayList<>();
       sizeList.add("Small");
       sizeList.add("Medium");
       sizeList.add("Large");
       ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,sizeList);
       sizeAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
       sizeSpinner.setAdapter(sizeAdapter);
   }

    /**
     * Method to check if minimum/maximum toppings have been reached
     * @return String stating if we reached maximum or not reached minimum toppings
     */
   private String checkNumToppings(){

        if(pizza.toppings.size() < 3){
            return "Error: Minimum 3 Toppings";
        }
       if(pizza.toppings.size() >7){
           return "Error: Maximum 7 Toppings";
       }
       return "";
   }

    /**
     * Method to add order when Add Order Button is clicked
     * @param view, the current view
     */
   public void soAddOrderClick(View view){
        String msg = checkNumToppings();
        if(!msg.isBlank()){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }
       StoreOrders storeOrders = StoreOrders.getStoreOrders();
       ArrayList<Order> orders = storeOrders.getStoreOrdersList();
       int orderNumber = storeOrders.getAvailable_OrderNumber();
       Order currentOrder = orders.get(orderNumber);
       currentOrder.addPizza(pizza);

        msg = "Pizza Added to Order!";
       Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
   }

    /**
     * Action when sauce is selected
     */
   public void onSauceSelected(){
       sauceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String sauceType = adapterView.getItemAtPosition(i).toString();
               pizza.sauce = getSauce(sauceType);
               Toast.makeText(BuildYourOwnActivity.this,sauceType+" Sauce Selected",Toast.LENGTH_SHORT).show();
           }
           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {
               pizza.sauce = getSauce("Tomato");
               sauceSpinner.setSelection(0);
           }
       });
   }
    /**
     * Action when size is selected
     */
   public void onSizeSelected(){
       sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String sizeSelected = adapterView.getItemAtPosition(i).toString();
               pizza.size = getSize(sizeSelected);
               priceBox.setText(updatePrice());
               Toast.makeText(BuildYourOwnActivity.this,"Size Selected "+ sizeSelected,Toast.LENGTH_SHORT).show();
           }
           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {
               sizeSpinner.setSelection(0);
           }
       });
   }
    /**
     * Method to create and initialize features
     * @param savedInstanceState, the current instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_your_own);
        listToppings = findViewById(R.id.list_topps);
        priceBox = findViewById(R.id.byoPriceText);
        sauceSpinner = findViewById(R.id.sauce_spinner);
        sizeSpinner = findViewById(R.id.size_spinner);
        exSauce = findViewById((R.id.exSauce_Box));
        exCheese = findViewById((R.id.exCheese_box));
        addOrder = findViewById(R.id.byoAddOrder);
        selectedToppings = new boolean[toppings.length];
        listToppings.setOnClickListener(view ->  {
            showToppingsDialogue();
        });
        initializeSauceSpinner();
        onSauceSelected();
        initializeSizeSpinner();
        onSizeSelected();

    } //end of create bracket


}