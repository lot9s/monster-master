package edu.mit.icelab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import java.lang.ClassNotFoundException;

import java.net.URISyntaxException;

import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public class CreatureClassifier {
	// --- Variables ---
	private RandomForest rf;
	
	// --- Constants ---
	// NOTE: When exporting as a .jar, paths must have /resources placed in front of it.
	private static final String PATH_MODEL = "/resources/random-forest-50I-90P.model";
	private static final String PATH_STUB = "/resources/stub.arff";
	
	/** */
	// TODO: apply Singleton design pattern to this class
	public CreatureClassifier() {
		try {
			// get Random Forest classifier from .model file
			InputStream is = this.getClass().getResourceAsStream(PATH_MODEL);
			ObjectInputStream ois = new ObjectInputStream(is);
      rf = (RandomForest) ois.readObject();
      
      // close I/O objects
      ois.close();
      //fis.close();
      is.close();
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
      System.err.println("IOException: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      System.err.println("ClassNotFoundException: " + e.getMessage());
    } /*catch (URISyntaxException e) {
    	System.err.println("URISyntaxException: " + e.getMessage());
    }*/
	}
	
  /**
   * @param formData the value obtained from the HTML form public/index.html
   */
  public String label(String formData) {
  	try {
  		// open stub.arff
  		InputStream is = this.getClass().getResourceAsStream(PATH_STUB);
  		BufferedReader stubReader = new BufferedReader(new InputStreamReader(is));
  		
  		// read contents of stub.arff
  		String stubContent = "";
  		String line = stubReader.readLine();
  		while (line != null) {
  			stubContent = stubContent + line + "\n";
  			line = stubReader.readLine();
  		}
  		
  		// close stub.arff
  		stubReader.close();
  		is.close();
  		
	    // create a temporary .arff file with unlabeled data
	    String arffData = stubContent.trim() + formData + "\n";
	    PrintWriter arffWriter = new PrintWriter("temp.arff", "UTF-8");
	    arffWriter.print(arffData);
	    arffWriter.flush();
	    arffWriter.close();
	    
	    // create Instances object from temporary .arff file
	    FileReader arffReader = new FileReader("temp.arff");
	    BufferedReader reader = new BufferedReader(arffReader);
	    Instances unlabeledData = new Instances(reader);
	    reader.close();
	    
	    // delete temporary .arff file
	    File tempFile = new File("temp.arff");
	    tempFile.delete();
	    
	    // set class attribute
      unlabeledData.setClassIndex(0);
	    
	    // create copy of unlabeled data
      Instances labeledData = new Instances(unlabeledData);
      
      // label the unlabeled data
      for (int i = 0; i < unlabeledData.numInstances(); i++) {
        double label = rf.classifyInstance(unlabeledData.instance(i));
        labeledData.instance(i).setClassValue(label);
      }
      
      // return label
      return labeledData.get(0).stringValue(labeledData.attribute(0));
    }  catch (IOException e) {
    	System.err.println("IOException: " + e.getMessage());
    } catch (URISyntaxException e) {
    	System.err.println("URISyntaxException: " + e.getMessage());
    } catch (Exception e) {
    	System.err.println("Exception: " + e.getMessage());
    }
  	
  	return "CR ??";
  }
}
