package jPMorgen;

/**
 * 
 * @author Shady Mohsen
 *
 */

public class JPMorganMain {

	public static void main(String[] args) {
		MessageHandler messageHandler = new MessageHandler();
		try{
			//read sample data file and print out its data
			messageHandler.readInputMessages();
			//print adjustments summary
			messageHandler.printSummaryAdjustments();
		}catch(Exception ex){
			System.out.print("An error has been occured. Please try again later");
		}finally{
			try{
				//close input file either exception occurs or no
				messageHandler.getInputFile().close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
