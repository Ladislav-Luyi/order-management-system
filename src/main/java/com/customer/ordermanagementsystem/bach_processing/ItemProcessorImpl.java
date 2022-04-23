package com.customer.ordermanagementsystem.bach_processing;


import com.customer.ordermanagementsystem.pojos.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

class ItemProcessorImpl implements ItemProcessor<Item, Item> {

    private static final Logger log = LoggerFactory.getLogger(ItemProcessor.class);

    @Override
    public Item process(final Item item) throws Exception {

        log.info("Converting (" + item + ")");


        return item;
    }

}