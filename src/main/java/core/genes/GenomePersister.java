package core.genes;

import java.io.*;
import java.util.Calendar;

public class GenomePersister {

	public void perist(Genome pGenomeOrigin, String pPath) {
		/*
		DateAndTimeObject dateAndTimeObject = null;
	      FileInputStream fileInputStream = null;
	      ObjectInputStream objectInputStream = null;
	 
	      String serializedFileName = "dateAndTime.ser";
	      if(args.length > 0)
	      {
	         serializedFileName = args[0];
	      }
	 
	      try
	      {
	         fileInputStream = new FileInputStream(serializedFileName);
	         objectInputStream = new ObjectInputStream(fileInputStream);
	         dateAndTimeObject = (DateAndTimeObject) objectInputStream.readObject();
	         objectInputStream.close();
	 
	         //Date and Time that was serialized
	         System.out.println("Serialize Date and Time: " + dateAndTimeObject.getDateAndTime());
	         //Current Date and Time
	         System.out.println("Current Date and Time  : " + Calendar.getInstance().getTime());
	 
	      }
	      catch(FileNotFoundException fnfe)
	      {
	         System.out.println("File not found: "+fnfe.getMessage());
	         //Close all I/O streams
	         //Handle the exception here
	      }
	      catch(IOException ioe)
	      {
	         ioe.printStackTrace();
	         //Close all I/O streams
	         //Handle the exception here
	      }
	      catch(ClassNotFoundException cnfe)
	      {
	         cnfe.printStackTrace();
	         //Close all I/O streams
	         //Handle the exception here
	      }
	      */
	}

	public Genome load(String pPath) {
		// TODO Auto-generated method stub
		return null;
	}

}
