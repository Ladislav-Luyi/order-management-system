package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.item.Item;
import com.customer.ordermanagementsystem.pojos.item.Type;
import com.customer.ordermanagementsystem.pojos.item.menu_item.MenuEditDTO;
import com.customer.ordermanagementsystem.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
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
        log.info("saveMenuItems is saving " + items);

        String targetDate = items.get(0).getDate();

        Predicate<Item> isMenuMeal = i -> i.getType().equals(Type.MENU_JEDLO);
        Predicate<Item> isMenuSoup = i -> i.getType().equals(Type.MENU_POLIEVKA);

        ArrayList<Item> itemsToRemove = itemRepository.findAll().stream()
                .filter(isMenuMeal.or(isMenuSoup))
                .filter(i -> i.getDate().equals(targetDate))
                .collect(Collectors.toCollection(ArrayList::new));

        itemsToRemove.forEach(itemRepository::delete);

        items.forEach(itemRepository::save);
    }

    private List<Item> transformToItem(HashMap<String,String> map, Type type, String date){
        ArrayList<Item> items = new ArrayList<>();

        long tmpId = Instant.now().toEpochMilli();

        for (Map.Entry<String, String> input : map.entrySet()){

            String name = input.getKey();


            if ( name == null || name == "" ) {
                log.info("Menu name is missing; skipping");
                continue;
            }

            String price = input.getValue();

            if (!price.matches("^([0-9]|\\.|,)+$"))
                continue;

            if ( ( price == null || price == "" ) && type.equals(Type.MENU_POLIEVKA))
                price=soupDefaultPrice;

            if ( ( price == null || price == "" ) && type.equals(Type.MENU_JEDLO)) {
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


        return mealAndValue;
    }

    private HashMap<String, String> feedSoupMap(MenuEditDTO menuEditDTO ) {
        HashMap<String, String> soupAndValue = new HashMap<>();

        soupAndValue.put(menuEditDTO.getSoup1(),menuEditDTO.getSoup1price());

        return soupAndValue;
    }

    @Override
    public List<Item>  getMenuEditDTOAccordingDate(String date) {
        List<Item> items = itemRepository.findAll();
        log.info("All items from getMenuEditDTOAccordingDate " + items);

        Predicate<Item> isMenuMeal = i -> i.getType().equals(Type.MENU_JEDLO);
        Predicate<Item> isMenuSoup = i -> i.getType().equals(Type.MENU_POLIEVKA);


        List<Item> collect = items.stream()
                .filter(isMenuMeal.or(isMenuSoup))
                .filter(i -> i.getDate() != null)
                .filter(i -> i.getDate().equals(date))
                .collect(Collectors.toCollection(ArrayList::new));

        log.info("Loaded items from date " + date + ": "+ collect);

        return collect;
    }

    @Override
    public void loadMenuEditDTO(MenuEditDTO menuEditDTO, List<Item> items) {
        log.info("Calling loadMenuEditDTO " + items);
        int countMeal = 0;
        for(Item item : items){

            if(item.getType().equals(Type.MENU_JEDLO)) {
                if (countMeal == 0) {
                    menuEditDTO.setMeal1(item.getName());
                    menuEditDTO.setMeal1price(item.getPrice().toString());
                }

                if (countMeal == 1) {
                    menuEditDTO.setMeal2(item.getName());
                    menuEditDTO.setMeal2price(item.getPrice().toString());
                }

                if (countMeal == 2) {
                    menuEditDTO.setMeal3(item.getName());
                    menuEditDTO.setMeal3price(item.getPrice().toString());
                }

                if (countMeal == 3) {
                    menuEditDTO.setMeal4(item.getName());
                    menuEditDTO.setMeal4price(item.getPrice().toString());
                }

                countMeal++;
            }

        }

        int countSoup = 0;
        for(Item item : items){

            if(item.getType().equals(Type.MENU_POLIEVKA)) {
                if (countSoup == 0) {
                    menuEditDTO.setSoup1(item.getName());
                    menuEditDTO.setSoup1price(item.getPrice().toString());
                }

                countSoup++;
            }
        }
    }


    private String replaceCommaWithDots(String s){

        return s.replaceAll(",",".");
    }


}
