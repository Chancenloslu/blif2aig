package Parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class ParseFile {
	
	private char[] inputName;	// a b c
	private char[] outputName;
	public HashSet<String> inputBooleanSet = new HashSet<>();	//110 001 101
	
	//uppercase ---> normal, lowercase ---> inverse
	public String[] inputMinterm;
	
	/**
	 * @param input:Strs[2] from seperate(toSep)
	 * @return Names of the Inputs, such as "a,b,c,d,e"
	 * */
	private char[] getInputNames(String input) {
		
		//int numberOfInputs = getNbOfInputs(input);
		String strTemp =input.replaceAll(".inputs", "");
		strTemp = strTemp.replaceAll(" ", "");
		
		char[] inputChar = strTemp.toCharArray();
		
		return inputChar;
	}
	private char[] getOutputNames(String output){
		String tmp = output.replaceAll(".outputs","");
		tmp=tmp.replaceAll(" ", "");

		char [] outputChar = tmp.toCharArray();
		return outputChar;
	}
	/**
	 * 
	 * @param input: boolean description
	 * @return	String Array of Input
	 */
	private void getInput(String input) {
		String[] str;
		str = input.split(" ");
		this.inputBooleanSet.add(str[0]);
	}
	
	public void paserFile(String filename) {
		BufferedReader blifFile = null;
		try {
			blifFile = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String line = "";
		try {
			if(blifFile != null){
				while((line = blifFile.readLine()) != null){
					if(line.isEmpty() || line.startsWith("#")) continue;
//					line = line.replaceAll("\\s+"," ");
					if(line.startsWith(".")){
						
						String str = line.split(" ")[0];
						
						switch(str){
						case ".model":
							break;
						case ".inputs":
							this.inputName = getInputNames(line);
							break;
						case ".outputs":
							this.outputName=getOutputNames(line);
							break;
						case ".names":
							break;
						case ".end":
							break;
						default:
							break;
						}
					}
					else{
						this.getInput(line);
					}
				}

				inputTermGenerate(inputName, inputBooleanSet);
			}
			blifFile.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * getter
	 */
	public char[] getInputName() {
		return inputName;
	}

	public char[] getOutputName() {
		return outputName;
	}

	/**
	 * generate the boolean function
	 * @param names: inputnames[]
	 * @param values: inputValues[]
	 * @return the String name of Boolean function
	 * */
	private void inputTermGenerate(char[] names, HashSet<String> values) {
		int lengthofNames=names.length;
		String toReturn="";
		for(String s: values) {
			String ValueTmp = s;//010
			String toReturnTmp = "";
			for(int j=0;j<lengthofNames;j++) {
				if(ValueTmp.charAt(j)=='0') {
					toReturnTmp += (char)(names[j] - 32);//uppercase letters represents inverse
				}else {
					toReturnTmp += names[j];//lowcase letters represents normal
				}
				
			}

//			String tmp1=String.valueOf(toReturnTmp);
			toReturn += toReturnTmp + "+";
		}
		
		this.inputMinterm = toReturn.split("\\+");
	}
	
}
