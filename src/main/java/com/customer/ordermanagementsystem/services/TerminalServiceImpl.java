package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.order.Order;
import com.customer.ordermanagementsystem.pojos.order.TerminalReply;
import com.customer.ordermanagementsystem.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Slf4j
@Service
public class TerminalServiceImpl implements TerminalService {

    private final OrderRepository orderRepository;

    @Autowired
    public TerminalServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public File refreshAndGetFile() {
        remove2DaysOldFiles();

        File f = getCurrentFileForWriting();

        log.info("Obtaining file: " + f.getAbsolutePath());
        List<String> listOrdersFromFile = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader( new FileReader(f) )){

            while(reader.ready())
                listOrdersFromFile.add(reader.readLine());

            log.debug("Orders from file " + listOrdersFromFile.toString());

        }catch (IOException e) {
            log.error("There is error during reading  the file: " + e.getMessage());
            e.printStackTrace();
        }

        try(FileWriter fileWriter = new FileWriter(f, true)) {


            outerLoop: for(Order o : orderRepository.findAll()) {

                if (o.getTerminalReplyInfo() != null)
                    continue;

                String s = "#" + o.getId().toString() + "*";

                for(String line : listOrdersFromFile)
                    if (line.contains(s)) {
                    continue outerLoop;
                }

                fileWriter.write( composeMessage(o) );
            }

            fileWriter.flush();

        } catch (IOException e) {
            log.error("There is error during writing to the file: " + e.getMessage());
            e.printStackTrace();
        }

        return f;
    }

    @Override
    public String composeMessage(Order o) {
        StringBuilder s = new StringBuilder();
        s.append("#");
        s.append(o.getId());
        s.append("*");
        s.append(o.getOrderText());
        s.append("*");
        s.append(o.getOrderInfo().getTelephoneNumber());
        s.append("#");
        s.append("\r");

        return s.toString();
    }

    @Override
    public void updateOrder(TerminalReply terminalReply) {
        Optional<Order> order = orderRepository.findById(
                Long.valueOf(terminalReply.getOrderId()).longValue());

        order.ifPresent( o -> { o.setTerminalReplyInfo(terminalReply);
            orderRepository.save(o);});

    }


    private File createFileWithMillisecondName() {
        String fileName = new Date().toInstant().toEpochMilli() + "_orders.txt";

        log.info("Creating new file: " + fileName);

        File f = new File(fileName);

        try {
            f.createNewFile();
        }catch (IOException io){
            io.printStackTrace();
        }

        return f;
    }

    private void remove2DaysOldFiles(){
        File currentDir = new File(".");

        Arrays.stream( Objects.requireNonNull( currentDir.listFiles() ) )
                .filter(this::isRelevantFile)
                .filter(this::isOlderThanTwoDays)
                .forEach(File::delete);
    }

    private File getCurrentFileForWriting() {
        File currentDir = new File(".");

        return  Arrays.stream( Objects.requireNonNull( currentDir.listFiles() ) )
                .filter(this::isRelevantFile)
                .reduce((a, b) -> {
                    log.info("Found multiple relevant files. Removing some files till one file remain.");
            a.delete(); return b;
        })
                .orElseGet(this::createFileWithMillisecondName);

    }

    private boolean isRelevantFile(File file){
        return file.getName()
                .matches("^[0-9]+_.*$");
    }

    private boolean isOlderThanTwoDays(File file){

        Long twoDaysInMillisecond = 2 * 24 * 60 * 60 * 1000l;

        Long currentTimeStampMinusTwoDays = new Date().toInstant().toEpochMilli() - twoDaysInMillisecond;

        String fileName = file.getName();

        Long l = Long.valueOf(fileName.split("_")[0]);

        int value = l.compareTo(currentTimeStampMinusTwoDays);

        if(value < 0)
            return true;

        return false;

    }
}
