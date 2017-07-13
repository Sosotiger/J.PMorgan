package jPMorgen;

/**
 * 
 * @author Shady Mohsen
 *
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
	
	//list of all sale messages to be printed each 10 messages 
	private List<SaleItems> itemsList = new ArrayList<SaleItems>();
	//list of adjustment to be printed at the end
	private List<SaleOperations> adjustmentsMessages = new ArrayList<SaleOperations>();
	private BufferedReader inputFile = null;
	
	public void readInputMessages() throws Exception{
		//locate the sample data file
		inputFile = new BufferedReader(new FileReader("SampleData.txt"));
        String lineReader = null;
        int messsagesCount = 1;
        //read file line by line
        while((lineReader = inputFile.readLine()) != null) {
        	//terminate iterator after 50 messages
        	if(messsagesCount > 50){
        		System.out.println("*** Application reached 50 messages and stop accepting new messages ***");
        		System.out.println("");
        		break;
        	}
        	//read line and update data
        	readMesssage(lineReader);
        	//print report every 10 messages
        	if(messsagesCount == 10 || messsagesCount == 20 || messsagesCount == 30 ||
        			messsagesCount == 40 || messsagesCount == 50){
        		System.out.println("************************* Details report for " + messsagesCount + " messages *************************");
        		System.out.println(String.format("%-35s%-20s%-20s","Item name","Quantity","Total value"));
        		System.out.println(String.format("%-35s%-20s%-20s","*********","********","***********"));
        		//total report count
        		Long itemsCount=0L;
        		//total report value
        		Double itemsValue=0D;
        		for(SaleItems item : itemsList){
        			itemsCount = itemsCount + item.getQuantity();
        			itemsValue = itemsValue + item.getValue();
        			System.out.println(String.format("%-35s%-20d%-20.2f",item.getItemName(),item.getQuantity(),item.getValue()));
        		}
        		System.out.println("********");
        		System.out.println(String.format("%-35s%-20d%-20.2f","Total",itemsCount,itemsValue));
        		System.out.println("");
        	}
            messsagesCount++;
        }
	}
	
	private void readMesssage(String message){
		if(message != null && !message.equals("")){
			try{
				//split string to determine which message type is it
				String[] messageItems = message.split("\\s+");
				//message type one I.E apple at 10p
				if(messageItems.length == 3 && messageItems[1].equals("at")){
					SaleItems saleMessage = new SaleItems();
					saleMessage.setItemName(modifyItemName(messageItems[0]));
					saleMessage.setQuantity(1L);
					saleMessage.setValue((Double.parseDouble(messageItems[2].replaceAll("£|p", "")))/100);
					//update itemsList with new message
					updateMessgaesLists(saleMessage);
				//message type 2 I.E 20 sales of apples at 10p each
				}else if(messageItems.length == 7 && messageItems[0].matches("^\\d+")){
					SaleItems saleMessage = new SaleItems();
					saleMessage.setItemName(modifyItemName(messageItems[3]));
					saleMessage.setQuantity(Long.parseLong(messageItems[0]));
					saleMessage.setValue(saleMessage.getQuantity() * (Double.parseDouble(messageItems[5].replaceAll("£|p", "")))/100);
					//update itemsList with new message
					updateMessgaesLists(saleMessage);
				//message type 3 I.E add 20p apples
				}else if(messageItems.length == 3 && (messageItems[0].equals("Add") ||
						messageItems[0].equals("Subtract") || messageItems[0].equals("Multiply"))){
					SaleOperations messageOperation = new SaleOperations();
					messageOperation.setOperationType(messageItems[0]);
					messageOperation.setAmount((Double.parseDouble(messageItems[1].replaceAll("£|p", "")))/100);
					messageOperation.setProductName(modifyItemName(messageItems[2]));
					//update itemsList with new message and add new adjustment to adjustmentsMessages
					adjustmentMessgaesLists(messageOperation);
				}else
					System.out.println(message + " can't parse");
			}catch(Exception ex){
				System.out.println(message + " can't parse");
			}
			
		}
	}
	
	//convert singular to plural names
	private String modifyItemName(String itemName){
        String pluralItemName = "";
        String itemWithoutLastChar = itemName.substring(0, itemName.length() - 1);
        if (itemName.endsWith("o")) {
        	itemName = String.format("%soes", itemWithoutLastChar);
        } else if (itemName.endsWith("y")) {
        	pluralItemName = String.format("%sies", itemWithoutLastChar);
        } else if (itemName.endsWith("h")) {
        	pluralItemName = String.format("%shes", itemWithoutLastChar);
        } else if (!itemName.endsWith("s")) {
        	pluralItemName = String.format("%ss",itemName);
        } else {
        	pluralItemName = String.format("%s",itemName);
        }
        return pluralItemName.toLowerCase();
	}
	
	private void updateMessgaesLists(SaleItems messageItem){
		Boolean isAdded = false;
		for(SaleItems item : itemsList){
			if(item.getItemName().equals(messageItem.getItemName())){
				isAdded = true;
				item.setQuantity(item.getQuantity() + messageItem.getQuantity());
				item.setValue(item.getValue() + messageItem.getValue());
				break;
			}
		}
		if(!isAdded){
			itemsList.add(messageItem);
		}
	}
	
	private void adjustmentMessgaesLists(SaleOperations messageAdjustment){
		for(SaleItems item : itemsList){
			if(item.getItemName().equals(messageAdjustment.getProductName())){
				messageAdjustment.setPreviousQuantity(item.getQuantity());
				adjustmentsMessages.add(messageAdjustment);
				if(messageAdjustment.getOperationType().equals("Add"))
					item.setValue(item.getValue() + (item.getQuantity() * messageAdjustment.getAmount()));
				else if(messageAdjustment.getOperationType().equals("Subtract"))
					item.setValue(item.getValue() - (item.getQuantity() * messageAdjustment.getAmount()));
				else if(messageAdjustment.getOperationType().equals("Multiply"))
					item.setValue(item.getValue() * (item.getQuantity() * messageAdjustment.getAmount()));
				break;
			}
		}
	}
	
	public void printSummaryAdjustments() throws Exception{
		System.out.println("******* Adjustments summary report *******");
		for(SaleOperations operation : adjustmentsMessages){
			System.out.println(operation.getOperationType() + " " + operation.getAmount() + " for each " +
					operation.getProductName() + " affect " + operation.getPreviousQuantity() + " item");
		}
		System.out.println("************************* Thanks *************************");
	}

	public BufferedReader getInputFile() {
		return inputFile;
	}

	public void setInputFile(BufferedReader inputFile) {
		this.inputFile = inputFile;
	}

	public List<SaleItems> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<SaleItems> itemsList) {
		this.itemsList = itemsList;
	}

	public List<SaleOperations> getAdjustmentsMessages() {
		return adjustmentsMessages;
	}

	public void setAdjustmentsMessages(List<SaleOperations> adjustmentsMessages) {
		this.adjustmentsMessages = adjustmentsMessages;
	}
}
