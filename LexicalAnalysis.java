import java.util.*;
import java.io.*;
import java.awt.*;

// First Programmer  ->  Name: Ahmet Arda     Surname: Nalbant   No: 150121004
// Second Programmer ->  Name: Hasan  	      Surname: Özeren    No: 150121036
// Third Programmer  ->  Name: Niyazi Ozan    Surname: Ateþ      No: 150121991

public class LexicalAnalysis {

	public static void main(String[] args) throws Exception{
		
		// Take file pointers
		Scanner tempScan = new Scanner(System.in);
        System.out.print("What is the file labeled as: ");
        String fileName = tempScan.nextLine();
        File file = new File(fileName);
        
        if (file.exists()) {
            
        Scanner input = new Scanner(new File(fileName));
        Scanner errorFinder = new Scanner(new File(fileName));
		Scanner control = new Scanner(new File(fileName));
		
		
		File outputFile = new File("output.txt");
	    FileWriter writer = new FileWriter(outputFile);
		
	    // Create arrays for reading and checking
		ArrayList<String> list = new ArrayList<>();
		ArrayList<String> controlList = new ArrayList<>();
		
		// Tokenize the control array and remove strings, braces, keywords and booleans
		tokenizeInput(control, controlList);
		
		// Declare Variables
		int indexLine = 0;
		int index1 = -1;
		int index2 = -1;
		int count = 0;
		String errorString = "";
		boolean errorFound = true;
		
		// Controlling the tokens after removing strings, braces, keywords and booleans
		while (indexLine < controlList.size()) {
			
			String temp[] = controlList.get(indexLine).split(" ");
			int i = 0;
			while (i < temp.length) {
				int indexPlace = 0;
				count = 0;
				while (indexPlace < temp[i].length()) {

				if (temp[i].charAt(indexPlace) == '.' && (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9')) {
					count++;
					if (temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9') {
						while (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9') {
							if (indexPlace + 1 == temp[i].length()) {
								indexPlace++;
								break;
							}
							indexPlace++;
						}
					}
					if (indexPlace + 1 < temp[i].length() && (temp[i].charAt(indexPlace + 1) == 'E' || temp[i].charAt(indexPlace + 1) == 'e')) {
						indexPlace++;
						if (indexPlace + 1 < temp[i].length() && (temp[i].charAt(indexPlace + 1) == '+' || temp[i].charAt(indexPlace + 1) == '-' || (( temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9')))) {
							indexPlace += 2;
							if (indexPlace >= temp[i].length()) {
								count = 2;
							}
							while (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9') {
								if (indexPlace + 1 == temp[i].length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
						if (indexPlace < temp[i].length() && !(temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9')) {
							count = 2;
						}
					}
				}
				else if (((temp[i].charAt(indexPlace) == '-' || temp[i].charAt(indexPlace) == '+') && (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9')) || (temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9') || ((temp[i].charAt(indexPlace) == '-' || temp[i].charAt(indexPlace) == '+') && indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) == '.')) {
					boolean isHex = false;
					boolean isBinary = false;
					count++;
					if (indexPlace + 1 < temp[i].length() && (temp[i].charAt(indexPlace + 1) == 'e' || temp[i].charAt(indexPlace + 1) == 'E' || temp[i].charAt(indexPlace + 1) == '.')) {
						indexPlace++;
					}
						if ((temp[i].charAt(indexPlace) == '-' || temp[i].charAt(indexPlace) == '+')){
							indexPlace++;
						}
						if (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) == 'x') {
							isHex = true;
							if (indexPlace + 2 >= temp[i].length()) {
								count = 2;
							}
							indexPlace += 2;
							while (indexPlace + 1 < temp[i].length() && ((temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9') || (temp[i].charAt(indexPlace + 1) >= 'A' && temp[i].charAt(indexPlace + 1) <= 'F') || (temp[i].charAt(indexPlace + 1) >= 'a' && temp[i].charAt(indexPlace + 1) <= 'f'))) {
								if (indexPlace + 1 == temp[i].length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
						else if (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) == 'b') {
							isBinary = true;
							indexPlace += 2;
							if (indexPlace >= temp[i].length()) {
								count = 2;
							}
							while (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '1') {
								if (indexPlace + 1 == temp[i].length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
						else if (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9') {

							while (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9') {
								if (indexPlace + 1 == temp[i].length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
							if (indexPlace + 1 < temp[i].length() && ((temp[i].charAt(indexPlace + 1) == 'e' || temp[i].charAt(indexPlace + 1) == 'E'))) {
								indexPlace++;
							}
							if (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) == '.') {
								indexPlace++;
							}
						}
						if (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) == '.') {
							indexPlace++;
							while (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9') {
								if (indexPlace + 1 == temp[i].length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
							if (indexPlace - 1 < temp[i].length() && !(temp[i].charAt(indexPlace - 1) >= '0' && temp[i].charAt(indexPlace - 1) <= '9')) {
								count = 2;
							}
						}
						if (indexPlace < temp[i].length() && (temp[i].charAt(indexPlace) == 'E' || temp[i].charAt(indexPlace) == 'e')) {
							if (indexPlace + 1 >= temp[i].length()) {
								count = 2;
							}
							indexPlace++;
							if (indexPlace < temp[i].length() && (temp[i].charAt(indexPlace) == '+' || temp[i].charAt(indexPlace) == '-' || (( temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9')))) {
								indexPlace++;
								while (temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9') {
									if (indexPlace + 1 == temp[i].length()) {
										indexPlace++;
										break;
									}
									indexPlace++;
								}
								indexPlace--;
							}
						}
						if (isHex && (indexPlace < temp[i].length() && !((temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9') || (temp[i].charAt(indexPlace) >= 'a' && temp[i].charAt(indexPlace) <= 'f') || (temp[i].charAt(indexPlace) >= 'A' && temp[i].charAt(indexPlace) <= 'F')))) {
							count = 2;
						}
						else if (isBinary && (indexPlace < temp[i].length() && !(temp[i].charAt(indexPlace) == '0' || temp[i].charAt(indexPlace) == '1'))) {
							count = 2;
						}
						else if (!isHex && !isBinary && indexPlace < temp[i].length() && !(temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9')) {
							count = 2;
						}
					}
				else if (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) == '\'') {
					count++;
					indexPlace++;
					if (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) == '\\') {
						indexPlace++;
						if (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) == '\'') {
							indexPlace++;
							if (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) == '\'') {
								indexPlace++;
							}
						}
					}
					else if (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) == '\'') {
						indexPlace++;
					}
				}
				else if (temp[i].charAt(indexPlace) == '.' || temp[i].charAt(indexPlace) == '+' || temp[i].charAt(indexPlace) == '-') {
					count++;
					indexPlace++;
					if (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) != ' ') {
						count = 2;
					}
				}
				else if (temp[i].charAt(indexPlace) == '!' || temp[i].charAt(indexPlace) == '*' || temp[i].charAt(indexPlace) == '/' || temp[i].charAt(indexPlace) == ':' || temp[i].charAt(indexPlace) == '<' || temp[i].charAt(indexPlace) == '=' || temp[i].charAt(indexPlace) == '>' || temp[i].charAt(indexPlace) == '?' || (temp[i].charAt(indexPlace) >= 'a' && temp[i].charAt(indexPlace) <= 'z')) {
					String temps = "" + temp[i].charAt(indexPlace);
					int currentIndex = indexPlace;
					
					while (indexPlace + 1 < temp[i].length() && ((temp[i].charAt(indexPlace + 1) >= 'a' && temp[i].charAt(indexPlace + 1) <= 'z') || (temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9') || temp[i].charAt(indexPlace + 1) == '.' || temp[i].charAt(indexPlace + 1) == '+' || temp[i].charAt(indexPlace + 1) == '-')) {
						indexPlace++;
						temps += temp[i].charAt(indexPlace);
					}
						count++;
				}
				else if (temp[i].charAt(0) == '~') {
					break;
				}
				else {
					count = 2;
				}
				
				indexPlace++;
			}
				if (count > 1 && errorFound) {
					index1 = indexLine;
					index2 = i;
					errorString = temp[index2];
					errorFound = false;
				}
				i++;
			}
			indexLine++;
		}
		
		if (!errorFound) { // Error finder and printer
			int i = 0;
			while (i < index1 && errorFinder.hasNext()) {
				String test = errorFinder.nextLine();
				i++;
			}
			String test = errorFinder.nextLine();
			index2 = test.indexOf(errorString);
		}
				indexLine = 1;
				boolean exitControl = true;
				String wrongString = "";
		while (input.hasNext() && exitControl) {
			
			String test = input.nextLine();
			int indexPlace = 0;
			
			while (indexPlace < test.length() && exitControl) {
				
				if (index1 == (indexLine-1) && index2 == indexPlace) {
					wrongString = "LEXICAL ERROR [" + (index1 + 1) + ":" + (test.indexOf(errorString) + 1) + "]: Invalid token \'" + errorString + "\'";
					exitControl = false;
				}
				else {
					if (test.charAt(indexPlace) == '(' ) {
					list.add("LEFTPAR " + indexLine + ":" + (indexPlace + 1));
				}
				else if (test.charAt(indexPlace) == ')') {
					list.add("RIGHTPAR " + indexLine + ":" + (indexPlace + 1));
				}
				else if (test.charAt(indexPlace) == '[') {
					list.add("LEFTSQUAREB " + indexLine + ":" + (indexPlace + 1));
				}
				else if (test.charAt(indexPlace) == ']') {
					list.add("RIGHTSQUAREB " + indexLine + ":" + (indexPlace + 1));
				}
				else if (test.charAt(indexPlace) == '{') {
					list.add("LEFTCURLYB " + indexLine + ":" + (indexPlace + 1));
				}
				else if (test.charAt(indexPlace) == '}') {
					list.add("RIGHTCURLYB " + indexLine + ":" + (indexPlace + 1));
				}

				else if (test.charAt(indexPlace) == '.' && (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9')) {
					list.add("NUMBER " + indexLine + ":" + (indexPlace + 1));
					if (test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9') {
						while (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9') {
							if (indexPlace + 1 == test.length()) {
								indexPlace++;
								break;
							}
							indexPlace++;
						}
					}
					if (indexPlace + 1 < test.length() && (test.charAt(indexPlace + 1) == 'E' || test.charAt(indexPlace + 1) == 'e')) {
						indexPlace++;
						if (test.charAt(indexPlace + 1) == '+' || test.charAt(indexPlace + 1) == '-' || (( test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9'))) {
							indexPlace += 2;
							while (test.charAt(indexPlace) >= '0' && test.charAt(indexPlace) <= '9') {
								if (indexPlace + 1 == test.length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
					}
				}
				else if (((test.charAt(indexPlace) == '-' || test.charAt(indexPlace) == '+') && (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9')) || (test.charAt(indexPlace) >= '0' && test.charAt(indexPlace) <= '9') || ((test.charAt(indexPlace) == '-' || test.charAt(indexPlace) == '+') && indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) == '.')) {
					list.add("NUMBER " + indexLine + ":" + (indexPlace + 1));
					
					if (indexPlace + 1 < test.length() && (test.charAt(indexPlace + 1) == 'e' || test.charAt(indexPlace + 1) == 'E' || test.charAt(indexPlace + 1) == '.')) {
						indexPlace++;
					}
						if ((test.charAt(indexPlace) == '-' || test.charAt(indexPlace) == '+')){
							indexPlace++;
						}
						if (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) == 'x') {
							
							indexPlace += 2;
							while (indexPlace + 1 < test.length() && ((test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9') || (test.charAt(indexPlace + 1) >= 'A' && test.charAt(indexPlace + 1) <= 'F') || (test.charAt(indexPlace + 1) >= 'a' && test.charAt(indexPlace + 1) <= 'f'))) {
								if (indexPlace + 1 == test.length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
						else if (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) == 'b') {
							
							indexPlace += 2;
							while (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '1') {
								if (indexPlace + 1 == test.length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
						else if (indexPlace + 1 < test.length() && test.charAt(indexPlace) >= '0' && test.charAt(indexPlace) <= '9') {

							while (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9') {
								if (indexPlace + 1 == test.length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
							if (indexPlace + 1 < test.length() && ((test.charAt(indexPlace + 1) == 'e' || test.charAt(indexPlace + 1) == 'E'))) {
								indexPlace++;
							}
							if (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) == '.') {
								indexPlace++;
							}
						}
						if (indexPlace < test.length() && test.charAt(indexPlace) == '.') {
							indexPlace++;
							while (indexPlace < test.length() && test.charAt(indexPlace) >= '0' && test.charAt(indexPlace) <= '9') {
								if (indexPlace + 1 == test.length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
						if (indexPlace < test.length() && (test.charAt(indexPlace) == 'E' || test.charAt(indexPlace) == 'e')) {
							indexPlace++;
							if (test.charAt(indexPlace ) == '+' || test.charAt(indexPlace ) == '-' || (( test.charAt(indexPlace ) >= '0' && test.charAt(indexPlace ) <= '9'))) {
								indexPlace++;
								while (test.charAt(indexPlace) >= '0' && test.charAt(indexPlace) <= '9') {
									if (indexPlace + 1 == test.length()) {
										indexPlace++;
										break;
									}
									indexPlace++;
								}
								indexPlace--;
							}
						}
					}
				else if (indexPlace < test.length() && test.charAt(indexPlace) == '\'') {
					list.add("CHAR " + indexLine + ":" + (indexPlace + 1));
					indexPlace++;
					if (indexPlace < test.length() && test.charAt(indexPlace) == '\\') {
						indexPlace++;
						if (indexPlace < test.length() && test.charAt(indexPlace) == '\'') {
							indexPlace++;
							if (indexPlace < test.length() && test.charAt(indexPlace) == '\'') {
								indexPlace++;
							}
						}
					}
					else if (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) == '\'') {
						indexPlace++;
					}
				}
				else if (indexPlace < test.length() && test.charAt(indexPlace) == '"') {
					list.add("STRING " + indexLine + ":" + (indexPlace + 1));
					indexPlace++;
					while (indexPlace < test.length() && test.charAt(indexPlace) != '"') {
						if (indexPlace + 1 < test.length() && test.charAt(indexPlace) == '\\' && test.charAt(indexPlace + 1) == '"') {
							indexPlace += 2;
						}
						else {
							indexPlace++;
						}
					}
				}
				else if (test.charAt(indexPlace) == '.' || test.charAt(indexPlace) == '+' || test.charAt(indexPlace) == '-') {
					list.add("IDENTIFIER " + indexLine + ":" + (indexPlace + 1));
					indexPlace++;
				}
				else if (test.charAt(indexPlace) == '!' || test.charAt(indexPlace) == '*' || test.charAt(indexPlace) == '/' || test.charAt(indexPlace) == ':' || test.charAt(indexPlace) == '<' || test.charAt(indexPlace) == '=' || test.charAt(indexPlace) == '>' || test.charAt(indexPlace) == '?' || (test.charAt(indexPlace) >= 'a' && test.charAt(indexPlace) <= 'z')) {
					String temp = "" + test.charAt(indexPlace);
					int currentIndex = indexPlace;
					
					while (indexPlace + 1 < test.length() && ((test.charAt(indexPlace + 1) >= 'a' && test.charAt(indexPlace + 1) <= 'z') || (test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9') || test.charAt(indexPlace + 1) == '.' || test.charAt(indexPlace + 1) == '+' || test.charAt(indexPlace + 1) == '-')) {
						indexPlace++;
						temp += test.charAt(indexPlace);
					}
					
					if (temp.compareTo("define") == 0) {
						list.add("DEFINE " + indexLine + ":" + (currentIndex + 1));
					}
					else if (temp.compareTo("if") == 0) {
						list.add("IF " + indexLine + ":" + (currentIndex + 1));
					}
					else if (temp.compareTo("begin") == 0) {
						list.add("BEGIN " + indexLine + ":" + (currentIndex + 1));
					}
					else if (temp.compareTo("let") == 0) {
						list.add("LET " + indexLine + ":" + (currentIndex + 1));
					}
					else if (temp.compareTo("cond") == 0) {
						list.add("COND " + indexLine + ":" + (currentIndex + 1));
					}
					else if (temp.compareTo("true") == 0) {
						list.add("BOOLEAN " + indexLine + ":" + (currentIndex + 1));
					}
					else if (temp.compareTo("false") == 0) {
						list.add("BOOLEAN " + indexLine + ":" + (currentIndex + 1));
					}
					else {
						list.add("IDENTIFIER " + indexLine + ":" + (currentIndex + 1));
					}
					
				}
				else if (test.charAt(0) == '~') {
					break;
				}
				
				indexPlace++;
				}
				
			}
			indexLine++;
		}
		
		// Opens output file
		Desktop.getDesktop().open(outputFile);
		
		// Printing on the console and output file
		int i = 0;
		while (i < list.size()) {
			System.out.println(list.get(i));
			writer.write(list.get(i)+ "\n");
			i++;
		}
		if (!exitControl) {
			System.out.println(wrongString);
			writer.write(wrongString);
		}
		
		// Closing files
		input.close();
		errorFinder.close();
		control.close();
		writer.close();
		tempScan.close();
            
        }
        else {
            System.out.println("File not found: " + fileName);
        }
        
	}
	
	// Method to tokenize and remove strings, braces, keywords and booleans
    public static void tokenizeInput(Scanner input, ArrayList<String> copy) {
        input.useLocale(Locale.US);
        int i = 0;
        
        while (input.hasNext()) {
            copy.add(input.nextLine().replaceAll("[{}\\[\\]()]", " ").replaceAll("\\b(let|define|cond|if|begin|true|false)\\b", " ").replaceAll("\\\\\"", "").replaceAll("\"[^\"]*\"", ""));
            copy.set(i, copy.get(i).replaceAll("\\s+", " "));
            i++;
        }
    }
	
}
