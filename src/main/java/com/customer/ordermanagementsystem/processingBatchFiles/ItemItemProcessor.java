package com.customer.ordermanagementsystem.processingBatchFiles;


import com.customer.ordermanagementsystem.pojos.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

class ItemItemProcessor implements ItemProcessor<Item, Item> {

    private static final Logger log = LoggerFactory.getLogger(ItemProcessor.class);

    @Override
    public Item process(final Item item) throws Exception {

        log.info("Converting (" + item + ")");


        return item;
    }

}