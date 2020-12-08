package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.item.Type;
import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuEditDTO;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AdjustMenuServiceImpl implements AdjustMenuService {

    private final ItemRepository itemRepository;

    @Value("${soupDefaultPrice}")
    private String soupDefaultPrice;

    @Autowired
    public AdjustMenuServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void transformToItemAndSave(MenuEditDTO menuEditDTO) {
        HashMap<String,String> soupAndValue = feedSoupMap(menuEditDTO);

        HashMap<String,String> mealAndValue = feedMealMap(menuEditDTO);

        String date = menuEditDTO.getSpecificDate();

        List<Item> items = transformToItem( soupAndValue,Type.MENU_POLIEVKA,date );

        items.addAll( transformToItem(mealAndValue,Type.MENU_JEDLO,date) );

        saveMenuItems(items);
    }

    private void saveMenuItems(List<Item> items) {
        items.forEach(itemRepository::save);
    }

    private List<Item> transformToItem(HashMap<String,String> map, Type type, String date){
        ArrayList<Item> items = new ArrayList<>();

        long tmpId = 1000l;
        for (Map.Entry<String, String> input : map.entrySet()){
            String name = input.getKey();

            if ( name == null )
                continue;

            String price = input.getValue();

            if (price == null && type.equals(Type.MENU_POLIEVKA))
                price=soupDefaultPrice;

            if (price == null && type.equals(Type.MENU_JEDLO)) {
                log.info("Menu Type.MENU_JEDLO price is missing; skipping");
                continue;
            }
            price = replaceCommaWithDots(price);

            BigDecimal bigDecimalPrice = new BigDecimal( price );

            Item item = new Item(tmpId++,name,type,bigDecimalPrice,date);

            items.add(item);
        }

        return items;
    }

    private HashMap<String, String> feedMealMap(MenuEditDTO menuEditDTO) {
        HashMap<String, String> mealAndValue = new HashMap<>();

        mealAndValue.put(menuEditDTO.getMeal1(), menuEditDTO.getMeal1price());
        mealAndValue.put(menuEditDTO.getMeal2(), menuEditDTO.getMeal2price());
        mealAndValue.put(menuEditDTO.getMeal3(), menuEditDTO.getMeal3price());
        mealAndValue.put(menuEditDTO.getMeal4(), menuEditDTO.getMeal4price());
        mealAndValue.put(menuEditDTO.getMeal5(), menuEditDTO.getMeal5price());
        mealAndValue.put(menuEditDTO.getMeal6(), menuEditDTO.getMeal6price());
        mealAndValue.put(menuEditDTO.getMeal7(), menuEditDTO.getMeal7price());
        mealAndValue.put(menuEditDTO.getMeal8(), menuEditDTO.getMeal8price());

        return mealAndValue;
    }

    private HashMap<String, String> feedSoupMap(MenuEditDTO menuEditDTO ) {
        HashMap<String, String> soupAndValue = new HashMap<>();

        soupAndValue.put(menuEditDTO.getSoup1(),menuEditDTO.getSoup1price());
        soupAndValue.put(menuEditDTO.getSoup2(),menuEditDTO.getSoup2price());

        return soupAndValue;
    }

    @Override
    public MenuEditDTO getMenuEditDTOAccordingDate(String date) {
        return null;
    }

    private String replaceCommaWithDots(String s){

        return s.replaceAll(",",".");
    }


}
