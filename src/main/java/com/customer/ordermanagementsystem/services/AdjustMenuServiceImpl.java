package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.item.Type;
import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuEditDTO;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AdjustMenuServiceImpl implements AdjustMenuService {

    @Override
    public void transformToItemAndSave(MenuEditDTO menuEditDTO) {
        HashMap<String,String> soupAndValue = new HashMap<>();
        HashMap<String,String> mealAndValue = new HashMap<>();

        feedSoupMap(menuEditDTO, soupAndValue);
        feedMealMap(menuEditDTO, mealAndValue);


        if (!menuEditDTO.getSoup1().isEmpty());



    }

    private List<Item> transformToItem(HashMap<String,String> map, Type type){
        ArrayList<Item> items = new ArrayList<>();

        for (Map.Entry<String, String> input : map.entrySet()){
            String name = input.getKey();

            if ( name == null )
                continue;

            String price = input.getValue();

            //externalizovat

            if (price == null && type.equals(Type.MENU_POLIEVKA))
                price="1";

            if (price == null && type.equals(Type.MENU_JEDLO)) {
                log.info("Menu Type.MENU_JEDLO price is missing; skipping");
                continue;
            }
            price = replaceCommaWithDots(price);

            BigDecimal bigDecimalPrice = new BigDecimal( price );

            //prerobit na item a pridat do pola
        }

        return items;
    }

    private void feedMealMap(MenuEditDTO menuEditDTO, HashMap<String, String> mealAndValue) {
        mealAndValue.put(menuEditDTO.getMeal1(), menuEditDTO.getMeal1price());
        mealAndValue.put(menuEditDTO.getMeal2(), menuEditDTO.getMeal2price());
        mealAndValue.put(menuEditDTO.getMeal3(), menuEditDTO.getMeal3price());
        mealAndValue.put(menuEditDTO.getMeal4(), menuEditDTO.getMeal4price());
        mealAndValue.put(menuEditDTO.getMeal5(), menuEditDTO.getMeal5price());
        mealAndValue.put(menuEditDTO.getMeal6(), menuEditDTO.getMeal6price());
        mealAndValue.put(menuEditDTO.getMeal7(), menuEditDTO.getMeal7price());
        mealAndValue.put(menuEditDTO.getMeal8(), menuEditDTO.getMeal8price());
    }

    private void feedSoupMap(MenuEditDTO menuEditDTO, HashMap<String, String> soupAndValue) {
        soupAndValue.put(menuEditDTO.getSoup1(),menuEditDTO.getSoup1price());
        soupAndValue.put(menuEditDTO.getSoup2(),menuEditDTO.getSoup2price());
    }

    @Override
    public MenuEditDTO getMenuEditDTOAccordingDate(String date) {
        return null;
    }

    private String replaceCommaWithDots(String s){

        return s.replaceAll(",",".");
    }


}
