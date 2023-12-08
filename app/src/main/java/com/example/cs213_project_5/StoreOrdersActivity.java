package com.example.cs213_project_5;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class StoreOrdersActivity extends AppCompatActivity {

    private Spinner orderNumSpinner;
    private ListView pizzaOrderList;
    private Button cancelButton;

    private int orderSelected = -1; //Default



    private void fillOrderNumSpinner(){
        StoreOrders storeOrder = StoreOrders.getStoreOrders();
        ArrayList<Integer> orderNums = storeOrder.getOrderNumbersPlaced() ;
        ArrayAdapter<Integer> orderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,orderNums);
        orderAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        orderNumSpinner.setAdapter(orderAdapter);
    }

    private void populateListView(){
        StoreOrders storeOrder = StoreOrders.getStoreOrders();
        Order selectedOrder = storeOrder.find(orderSelected);
        ArrayList<String> pizzas = selectedOrder.getPizzas();

        ArrayAdapter<String> orderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,pizzas);
        orderAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        pizzaOrderList.setAdapter(orderAdapter);

    }

    public void onSoCancelButton(View view){
        StoreOrders storeOrder = StoreOrders.getStoreOrders();
        if(!storeOrder.hasOrders()){
            Toast.makeText(this, "No Orders Placed", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean removeMe = storeOrder.removeOrder(orderSelected);
        fillOrderNumSpinner();
        Toast.makeText(this, "Order: " + orderSelected + " Cancelled!", Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);
        orderNumSpinner = findViewById(R.id.soOrder_spinner);
        cancelButton = findViewById(R.id.soCancel_Button);
        pizzaOrderList = findViewById(R.id.soListView);
        fillOrderNumSpinner();

        orderNumSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                orderSelected = i;
                populateListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


}