// -----------------------------------------------------
// Assignment 2 - COMP 249
// Written by: Maëlys Arnaud 40278798
// Due date : March 27, 2024
// -----------------------------------------------------
// -----------------------------------------------------
// this program is able to read movie files with records in it
// you can later navigate through the different genres of movies
// -----------------------------------------------------

import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import SementicError.*;
import SyntaxError.*;

public class Driver {

	/**
	 * in the main method, many static methods are called, where the first will create a file with the name of movie genre comma separated value files
	 * in that method, a bad movie record file is also made to reject the invalid records
	 * the second method, serializes all the genre files (so it creates Movie objects to then put in the binary files
	 * the third method deserialized the files, and puts the objects in a 2D array that is used in the interactive part of this program
	 * then the  interactive part keeps track of the genre the user wants to navigate, and the last record of each genre they were on
	 */
	public static void main(String[] args) {
		
		// initialization of variables relevant for later 
		int numberOfFieldsExpected = 10;
		String[] availableGenres =  {"musical", "comedy", "animation", "adventure", "drama", "crime", "biography", "horror", 
                "action", "documentary", "fantasy", "mystery", "sci-fi", "family", "western", "romance", "thriller"};
		String[] typesOfRatings = {"pg", "unrated", "g", "r", "pg-13", "nc-17"};
		
		// required code:
		String part1_manifest = "part1_manifest.txt"; 
        String part2_manifest = do_part1(part1_manifest, numberOfFieldsExpected, availableGenres, typesOfRatings); // partition
        String part3_manifest = do_part2(part2_manifest, numberOfFieldsExpected);  // serialize
        Movie[][] all_movies = do_part3(part3_manifest, availableGenres, part2_manifest);
        
        System.out.println("Assignment 2, program written by Maëlys\nWelcome to the movie record keeper!"); // welcome message 
        
        navigate_movie_array(all_movies, availableGenres); // interactive part of program 

        System.out.println("Bye bye"); // closing message 
	}
	
	/**
	 * For this method, i want to be able to create all the files, bad records and genre (17)
	 * and write the corresponding files in it 
	 * in the bad record file, i need to specify all the mistakes there are in a record
	 * therefore, i want to create an array that indicates which mistakes are present in the record
	 * I also assumed that is a syntax error is thrown, there is no semantic errors that can be thrown 
	 * 
	 * @return the name of the file contaning the name of the genre files 
	 */
	static String do_part1(String part1_manifest, int numberOfFieldsExpected, String[] availableGenres, String[] typesOfRatings) {
		
		 PrintWriter so = null; // initialization
	     PrintWriter so2 = null;
	     try {
	            // create part1_manifest
	            so = new PrintWriter(new FileOutputStream(part1_manifest)); 
	            // we will be adding the required input files in this text file
	            for (int i = 1990; i < 2000; i++){
	                so.println("Movies" + i + ".csv");
	            }
	 
	            // create part2_manifest
	            so2 = new PrintWriter((new FileOutputStream("part2_manifest.txt")));
	            // put the genres in the part2_manifest file from the String array
	            for (int i = 0; i < availableGenres.length; i++){
	                so2.println(availableGenres[i] + ".csv");
	            }

	       }catch (FileNotFoundException fnf) {
	            System.out.println("FileNotFoundException...");
	            System.exit(0); // this will terminate the program
	       }finally{
	            so.close();
	            so2.close();
	       }
	     
	     Scanner fileSelector = null;
	     Scanner genreFileSelector = null;
	     PrintWriter badMovieWriter = null;
	     PrintWriter goodMovieWriter = null;
	     
	     try {
	            fileSelector = new Scanner(new FileReader("part1_manifest.txt"));
	            genreFileSelector = new Scanner(new FileReader("part2_manifest.txt"));
	            // need to clear the files for every use :
	            // hard coded 17 because 17 genre but can be changed 
	            for (int num = 0; num < 17; num++){
	                goodMovieWriter = new PrintWriter(new FileOutputStream(genreFileSelector.nextLine())); // to reset all files 
	            }

	            badMovieWriter = new PrintWriter(new FileOutputStream("bad_movie_records.txt")); // to restart the files 
	            Scanner inputStream = null;
	            
	            fileSelector = new Scanner(new FileReader("part1_manifest.txt"));
	            genreFileSelector = new Scanner(new FileReader("part2_manifest.txt"));
	            // need to clear the files for every use :
	            // hard coded 17 because 17 genre but can be changed 
	            for (int num = 0; num < 17; num++){
	                goodMovieWriter = new PrintWriter(new FileOutputStream(genreFileSelector.nextLine())); // to reset all files 
	            }
	            
	            while(fileSelector.hasNextLine()){
	                badMovieWriter = new PrintWriter(new FileOutputStream("bad_movie_records.txt", true)); // now to append the file (add without erasing past lines)
	                //goodMovieWriter = new PrintWriter(new FileOutputStream("good_movie_records.txt", true));
	                
	                int count = 0; // to know which line if the error ! 
	                
	                String fileName = fileSelector.nextLine(); // to use in error message that is printed in bad_movie_records.txt
	                try{
	                    inputStream = new Scanner(new FileReader(fileName)); // will take one file at a time 
	                    // it doesn't work if you don't close and reopen the scanner :
	                    while (inputStream.hasNextLine()){
	                    	genreFileSelector.close();
	                    	genreFileSelector = new Scanner(new FileReader("part2_manifest.txt"));
	                        // check that the line respects the requirements 
	                        String line = inputStream.nextLine();
	                        count++;
	                        String block = "\nFile : " + fileName + "\nLine : " + count + "\n\n";
	                        // this part is to know which one is a good record or a bad record 
	                        int[] syntaxErrors = getSyntaxErrors(line, numberOfFieldsExpected);
	                        int[] semanticErrors; 
	                        if (syntaxErrors[0] == 1 || syntaxErrors[1] == 1 || syntaxErrors[2] == 1) {
	                        	for (int i = 0; i < syntaxErrors.length; i++) { // this will throw all kinds of exceptions 
	                        		try {
	                        			if (syntaxErrors[i] == 1) {
	                        				switch (i){
	                        				case 0:
	                        					throw new MissingQuotesException(); // no need of breaks, because unreachable anyways 
	                        				case 1:
	                        					throw new MissingFieldsException();
	                        				case 2:
	                        					throw new ExcessFieldsException();
	                        				}
	                        			}
	                        		}catch (MissingQuotesException e) {
	                        			badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        		}catch (MissingFieldsException e) {
	                        			badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        		}catch (ExcessFieldsException e) {
	                        			badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        		}
	                        	}	
	                        }else{ // meaning there are no semantic errors 
	                        	// check for semantic errors
	                        	semanticErrors = getSemanticErrors(line, numberOfFieldsExpected, availableGenres, typesOfRatings);
	                        	// 1-invalid year 2-missing year 3-missing title 4-invalid duration 5-missing duration 6-invalid genre 7-missing genre
	                        	// 8-invalid rating 9-missing rating 10-invalid score 11-missing score 12-missing director 13-missing actor1 14-2 15-3
	                        	boolean freeOfError = true;
	                        	for (int i = 0; i < semanticErrors.length; i++) {
	                        		if (semanticErrors[i] == 1) {
	                        			freeOfError = false;
	                        			break;
	                        		}
	                        	}
	                        	if(!freeOfError) {
	                        		for (int i = 0; i < semanticErrors.length; i++) { // this will throw all kinds of exceptions 
	                        			try {
	                        				if (semanticErrors[i] == 1) {
	                        					switch (i){
	                        					case 0:
	                        						throw new InvalidYearException(); // no need of breaks, because unreachable anyways 
	                        					case 1:
	                        						throw new MissingYearException();
	                        					case 2:
	                        						throw new MissingTitleException();
	                        					case 3:
	                        						throw new InvalidDurationException();
	                        					case 4:
	                        						throw new MissingDurationException();
	                        					case 5:
	                        						throw new InvalidGenreException();
	                        					case 6:
	                        						throw new MissingGenreException();
	                        					case 7:
	                        						throw new InvalidRatingException();
	                        					case 8:
	                        						throw new MissingRatingException();
	                        					case 9:
	                        						throw new InvalidScoreException();
	                        					case 10:
	                        						throw new MissingScoreException();
	                        					case 11:
	                        						throw new MissingNamesException("SementicError: Missing director name");
	                        					case 12:
	                        						throw new MissingNamesException("SementicError: Missing actor1 name");
	                        					case 13:
	                        						throw new MissingNamesException("SementicError: Missing actor2 name");
	                        					case 14:
	                        						throw new MissingNamesException("SementicError: Missing actor3 name");
	                        					}
	                        				}
	                        			} catch (InvalidYearException e) {
	                        				badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        			} catch (MissingYearException e) {
	                        				badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        			} catch (MissingTitleException e) {
	                        				badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        			} catch (InvalidDurationException e) {
	                        				badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        			} catch (MissingDurationException e) {
	                        				badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        			} catch (InvalidGenreException e) {
	                        				badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        			} catch (MissingGenreException e) {
	                        				badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        			} catch (InvalidRatingException e) {
	                        				badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        			} catch (MissingRatingException e) {
	                        				badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        			} catch (InvalidScoreException e) {
	                        				badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        			} catch (MissingScoreException e) {
	                        				badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        			} catch (MissingNamesException e) {
	                        				badMovieWriter.write(e.getMessage() + "\n" + line + block);
	                        			}
	                        		}	
	                        	}
	                        	else {
	                        		String[] fields = getFields(line, numberOfFieldsExpected);
	                        		int lineNumOfGenreFile = 0;
	                        		// this swicth will allow the reader of the genre file to be on the right line since you cant jump on a specific line 
	                                switch (fields[3].replaceAll("\"", "")){ // index 3 is the place for genre 
	                                    case "Musical":
	                                        lineNumOfGenreFile = 1;
	                                        break;
	                                    case "Comedy":
	                                        lineNumOfGenreFile = 2;
	                                        break;
	                                    case "Animation":
	                                        lineNumOfGenreFile = 3;
	                                        break;
	                                    case "Adventure":
	                                        lineNumOfGenreFile = 4;
	                                        break;
	                                    case "Drama":
	                                        lineNumOfGenreFile = 5;
	                                        break; 
	                                    case "Crime":
	                                        lineNumOfGenreFile = 6;
	                                        break;
	                                    case "Biography":
	                                        lineNumOfGenreFile = 7;
	                                        break;
	                                    case "Horror":
	                                        lineNumOfGenreFile = 8;
	                                        break;
	                                    case "Action":
	                                        lineNumOfGenreFile = 9;
	                                        break;
	                                    case "Documentary":
	                                        lineNumOfGenreFile = 10;
	                                        break;
	                                    case "Fantasy":
	                                        lineNumOfGenreFile = 11;
	                                        break;
	                                    case "Mystery":
	                                        lineNumOfGenreFile = 12;
	                                        break;
	                                    case "Sci-fi":
	                                        lineNumOfGenreFile = 13;
	                                        break;
	                                    case "Family":
	                                        lineNumOfGenreFile = 14;
	                                        break;
	                                    case "Western":
	                                        lineNumOfGenreFile = 15;
	                                        break;
	                                    case "Romance":
	                                        lineNumOfGenreFile = 16;
	                                        break;
	                                    case "Thriller":
	                                        lineNumOfGenreFile = 17;
	                                        break;
	                                }
	                                for (int num = 1; num < lineNumOfGenreFile; num++){
	                                    genreFileSelector.nextLine(); // to read the lines 
	                                }

	                                String genreFileString = genreFileSelector.nextLine();
	                                goodMovieWriter = new PrintWriter(new FileOutputStream(genreFileString, true));
	                                goodMovieWriter.println(line);
	                                goodMovieWriter.close();
	                                genreFileSelector.close();
	                        		
	                        	}
	                        }
	                        	}
	                }catch (FileNotFoundException fnf){
	                    System.out.println(fileName + " file not found ...");
	                }catch (IOException e){
	                    System.out.println(e.getMessage());
	                }finally{
	                    inputStream.close();
	                    badMovieWriter.close();
	                }
	            } 
	        }catch (FileNotFoundException fnf){
	            System.out.println("File not found ...");
	            System.exit(0); // will terminate the program bc without it won't work
	        }catch (IOException e){
	            System.out.println(e.getMessage());
	        }finally{
	            fileSelector.close();
	        }
	    
		return "part2_manifest.txt";
	}
	

	/**
	 * this method first creates the .ser files used as binary files for serializing
	 * then if writes all the object in the corresponding binary genre file
	 * 
	 * @return the name of the file containing the name of the binary genre files 
	 */
	static String do_part2(String part2_manifest, int numberOfFieldsExpected) {
		
		// needs to take the part2_manifest file and check all the .csv files in it 
        Scanner check = null;
        PrintWriter so = null; // initialization
        
        try {
            check = new Scanner(new FileReader(part2_manifest)); // this looks at the lines naming the genre files 
            so = new PrintWriter(new FileOutputStream("part3_manifest.txt")); 

            while (check.hasNextLine()){ // will go line by line 
                // this loop will check all the lines
                String genreFile = check.nextLine();
                //now create the .ser file (binary file)
                String genreSer = "";
                for (int i = 0; i < genreFile.length(); i++){
                	if (genreFile.charAt(i) == '.') // we don't want this in the name 
                		break;
                	genreSer += genreFile.charAt(i);
                }
                genreSer += ".ser"; // add the end for the serialized binary file 
                
                Scanner genreScanner = new Scanner(new FileReader(genreFile));
                
                // this will add the name of the new file in part3_manifest
                so.println(genreSer);
                //then create the movie object to write in the binary file 
                try {
                	FileOutputStream fileOut = new FileOutputStream(genreSer);
                	ObjectOutputStream out = new ObjectOutputStream(fileOut);
                	// create object first 
                	while (genreScanner.hasNextLine()){
                		String[] fields = getFields(genreScanner.nextLine(), numberOfFieldsExpected);
                		Movie movie = new Movie(Integer.parseInt(fields[0].replaceAll("\"", "")), fields[1], Integer.parseInt(fields[2].replaceAll("\"", "")), 
                				fields[3].replaceAll("\"", ""), fields[4].replaceAll("\"", ""), Double.parseDouble(fields[5].replaceAll("\"", "")), 
                				fields[6].replaceAll("\"", ""), fields[7].replaceAll("\"", ""), fields[8].replaceAll("\"", ""), fields[9].replaceAll("\"", ""));
                		out.writeObject(movie);
                	}
                	
                	out.close();
                	fileOut.close();
                	
                }catch (IOException e) {
                	e.printStackTrace();
                }
                genreScanner.close(); // close the scanner again :)
            }
        } catch (FileNotFoundException e) {
        	System.out.println("FileNotFoundException...");
	            System.exit(0); // this will terminate the program
        }finally{
            check.close();
            so.close();
        }
        
		return "part3_manifest.txt";
	}
	

	/**
	 * this method is used to deserialize the binary genre files, and put the object of the binary files in a 2D array of movie objects
	 *  this method keeps track of which genre and which file is currently used/open, so the Movie[][] is the equivalent of the files we created in 
	 *  part 1, but under the form of objects, that can later be used in the interactive part 
	 *  
	 * @return a 2d array of movie objects 
	 */
	static Movie[][] do_part3(String part3_manifest,String[] availableGenres, String normalFile){
		// One problem encountered while doing this method: 
		// it couldn't count the number of lines properly in the binary file (bc it is not meant to be read like a book or normal file)
		// so i used the older files (normal ones, with .csv and .txt to count how many records there is in each file
		
		// create the 2D array 
		Movie[][] all_movies = new Movie[availableGenres.length][];
		// now deserialize every genre files and put it in the 2D array of movies 
		Scanner genreScan = null;
		Scanner normal = null;
		try {
			genreScan = new Scanner(new FileReader(part3_manifest)); // this will read the name of the binary genre files 
			normal = new Scanner(new FileReader(normalFile));
			try {
				// while (genreScan.hasNextLine()) {  --> this could've been used, but since available genres and the number of lines in part3_manifest matches 
				for (int i = 0; i < availableGenres.length; i++) {				
					String binaryGenreFile = genreScan.nextLine();
					String normalGenreFile = normal.nextLine();
					// count how many movie records there is :
					int numOfMovies = 0;
					Scanner count = new Scanner(new FileReader(normalGenreFile));
					while (count.hasNextLine()) {
						numOfMovies++;
						count.nextLine();
					}
					
					FileInputStream fileIn = new FileInputStream(binaryGenreFile);
					ObjectInputStream in = new ObjectInputStream(fileIn);
					
					all_movies[i] = new Movie[numOfMovies];					
					for (int j = 0; j < numOfMovies; j++) {
						all_movies[i][j] = (Movie) in.readObject();
					}
					
					fileIn.close();
					in.close();
				}
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
		}finally {
			genreScan.close();
			normal.close();
		}

		return all_movies;
	}
	

	/**
	 *  this method is the interactive method part of the program
	 *  it goes throw a do while loop (so that it can at least once play
	 *  and it will stop running when the user enters x
	 *  the method keeps track of the genre the user wants to naviagte and the last record viewed 
	 *  i used an integer variable called where to keep track of the chosen genre
	 *  and an int array for the position of the last viewed movie record 
	 */
	static void navigate_movie_array(Movie[][] all_movies, String[] availableGenres) {
		
		String answer = "something";
		int where = 0;
		int[] positionInSubArray = new int[availableGenres.length];
		for (int i = 0; i < availableGenres.length; i++) {
			positionInSubArray[i] = 0; // this is to initilize the position array (more flexible if we change the genres 
		}
		Scanner key = new Scanner(System.in);
		
		do {
			System.out.print("-----------------------------\n" + "\tMain Menu\n" + "-----------------------------\n" 
					+ "s  Select a movie array to navigate\n" + "n  Navigate "  + availableGenres[where] + " movies (" 
					+ all_movies[where].length + " record(s))\n" + "x  Exit\n" + "-----------------------------\n\nEnter Your Choice: ");
			answer = key.nextLine();
			
			switch (answer.toLowerCase()) {
			case "s":
				System.out.println("-----------------------------\n" + "\tGenre Sub-Menu\n" + "-----------------------------");
				for (int i = 1; i <= availableGenres.length; i++) {
					System.out.println(i + "  " + availableGenres[i-1] + "\t\t\t(" + all_movies[i-1].length + " movie(s))");
				}
				System.out.print((availableGenres.length + 1) + "  Exit\n" + "-----------------------------\n" + "Enter Your Choice: ");
				
				boolean isInt = false;
				int num = where;
				while(!isInt) {
					String choice = key.next();
					key.nextLine(); // junk line
					try {
						num = Integer.parseInt(choice);
						if (num >= 1 && num < 18)
							isInt = true;	
						else if (num == 18) {
							num = where;
							isInt = true;
						}
						else 
							throw new NumberFormatException(); // bc not a valid choice 
					}catch (NumberFormatException nf) {
						System.out.print("Invalid entry, please enter a valid choice: ");
					}
				}
				where = num - 1; // choice (to remember)
				break;
			case "n":
				System.out.print("\nNavigating " + availableGenres[where] + " movies (" + all_movies[where].length + ")\nEnter Your Choice: ");
				boolean isInt2 = false;
				int num2 = 0; // default value :) 
				while(!isInt2) {
					String choice = key.next();
					key.nextLine(); // junk line
					try {
						num2 = Integer.parseInt(choice);
						isInt2 = true; // only true if no error thrown 
					}catch (NumberFormatException nf) {
						System.out.print("Invalid entry, please enter a valid choice: ");
					}
				}
				if (num2 < 0 /*NEGATIVE N*/) {
					int moves = -num2 - 1;
					if (positionInSubArray[where] - moves < 0)
						System.out.println("BOF had been reached");
					for (int i = positionInSubArray[where]; i >= 0; i--) {
						System.out.println(all_movies[where][i].toString());
						if (moves == 0)
							break;
						else if (i == 0) {
							positionInSubArray[where] = 0; // bc at the top
							break;
						}
						else
							moves--;
					}
					if (positionInSubArray[where] != 0)
						positionInSubArray[where] -= (num2 -1);
				}else if (num2 > 0) {
					int moves = num2 - 1;
					if (positionInSubArray[where] + moves >= all_movies[where].length)
						System.out.println("EOF had been reached");
					for (int i = positionInSubArray[where]; i < all_movies[where].length; i++) {
						System.out.println(all_movies[where][i].toString());
						if (moves == 0)
							break;
						else if (i == all_movies[where].length - 1) {
							positionInSubArray[where] = all_movies[where].length - 1; // bc the bottom
							break;
						}
						else
							moves--;
					}
					System.out.println();
					if (positionInSubArray[where] != all_movies[where].length - 1)
						positionInSubArray[where] += (num2 -1);
				}
				// no need to handle num2 = 0 bc it goes back to menue
				break;
			case "x":
				break;
			default:
				System.out.println("Invalid Entry");
					
			}
			
		}while (!answer.equalsIgnoreCase("x"));
		
		key.close();
}
	
	/**
	 * this method check for the syntax errors with a for loop going character by char 
	 * for this method it is also logic to note that is there is missing quote error, 
	 * it is very likely that there will be a missing fields error thrown too 
	 * since this method does not count the commas as field seperators if there was an opening quote 
	 * (but it does after encountering another quote --> which is considered a closing quote 
	 * 
	 * @return an int array that translates to what syntax error there is in the records 
	 */
	static int[] getSyntaxErrors(String line, int numberOfFieldsExpected){
		
        int[] syntaxErrors = {0,0,0};
        int numberOfFields = 1;
        //to keep count of the syntax error, i change the value of the corresponding error 
        boolean inQuotes = false;
        // this is to count the numbers of fields on the line
        for(int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == '\"') {
                inQuotes = !inQuotes; // this is to detect if it is in quote or not
                continue; 
            }
    //        if(line.charAt(i) == this.fieldDelimiter && !inQuotes) {
            if(line.charAt(i) == ',' && !inQuotes) {
                numberOfFields++; // to count how many there is 
            }
            if (line.charAt(i) == ',' && !inQuotes && i == line.length() - 1){
                numberOfFields--; // this is to counter the extra comma at the end when is a file with a record with excess fields:)
            }
        }
        if(inQuotes) {  // if the quotes were never closed 
            syntaxErrors[0] = 1; // throw this exception
        }if(numberOfFields < numberOfFieldsExpected) { // not enough 
        	syntaxErrors[1] = 1;
        }if(numberOfFields > numberOfFieldsExpected) { // too many
        	syntaxErrors[2] = 1;
        }
    
        return syntaxErrors; // this returns all the contents of the line 
    }  	
	
	/**
	 *  similar to getSyntaxErrors
	 *  this method is not reached if a syntax error was already thrown 
	 *  therefore there is no need to wonder if there are enough fields or not
	 *  this mehtod simply checks for commas without being in quotes to use as delimiters of fields
	 * 
	 * @return an int array that translates to what semantic error there is in a record
	 */
	static int[] getSemanticErrors(String line, int numberOfFieldsExpected, String[] availableGenres, String[] typesOfRatings) {
		
		int[] semanticErrors = new int[15]; //bc 15 errors possible
		
		// create the fields 
		// no need to check anything bc this method cannot be accessed by invalid (in term of syntax) records -- that's how this program is designed 
		String[] fields = new String[numberOfFieldsExpected]; // this could also work with numberOfFields bc after the throws 
        boolean inQuotes = false;
        int currentFieldNumber = 0; 
        fields[currentFieldNumber] = ""; // to create a string right away
        for (int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == '\"') {
                inQuotes = !inQuotes;
                // continue; this would remove the "" on the fields, and you don't want to delete them if title with comma !!
            }
            else if(line.charAt(i) == ',' && !inQuotes) {
                currentFieldNumber++; // change fields 
                if (currentFieldNumber == numberOfFieldsExpected) {
                    break; // the array is done and ready to be returned 
                }
                fields[currentFieldNumber] = ""; // to create a new string in the new field
                continue;
            }
            fields[currentFieldNumber]+=line.charAt(i); //concatenation of the characters of the info
        }
        
        for (int i = 0; i < fields.length; i++){
            // i used a switch case to make the program more adaptable to other situations (if we change the attributes required or add smt)
            switch (i){
                case 0:
                    try{
                        if (fields[i].equals(""))
                            semanticErrors[1] = 1; // bc missing year
                        else {
                        	int year = Integer.parseInt(fields[i].replaceAll("\"", ""));
                        	if (year < 1990 || year >= 2000)
                        		semanticErrors[0]=1; // invalid year
                        }
                    }catch(NumberFormatException nf){
                        // then it is a string, non convertible, so :
                    	semanticErrors[0]=1; // invalid year
                    }break;
                case 1:
                    String title = fields[i]; // only the title can have "" to differ from the commas of csv and the title
                    if (title.equals("")) // if null
                        semanticErrors[2]=1;
                    break;
                case 2:
                    try{
                        if (fields[i].equals(""))
                        	semanticErrors[4]=1; // missing duration
                        else {
                        	int duration = Integer.parseInt(fields[i].replaceAll("\"", ""));
                        	if (duration < 30 || duration > 300)
                        		semanticErrors[3]=1; // invalid duration                        	
                        }
                    }catch(NumberFormatException nf){
                    	semanticErrors[3]=1; // invalid duration      
                    }break;
                case 3:
                    String genre = fields[i].replaceAll("\"", "");
                    if (genre.equals(""))
                    	semanticErrors[6]=1;
                    else {
                    	for (int j = 0; j < availableGenres.length; j++){
                    		if (genre.equalsIgnoreCase(availableGenres[j])){
                    			break;
                    		}if (j == availableGenres.length - 1 && !genre.equalsIgnoreCase(availableGenres[j]))
                    			semanticErrors[5]=1;   // no genre matching 
                    	}	
                    }break;
                case 4:
                    String rating = fields[i].replaceAll("\"", "");
                    if (rating.equals(""))
                    	semanticErrors[8]=1;
                    else {
                    	for (int j = 0; j < typesOfRatings.length; j++){
                    		if (rating.equalsIgnoreCase(typesOfRatings[j])){
                    			break;
                    		}if (j == typesOfRatings.length - 1 && !rating.equalsIgnoreCase(typesOfRatings[j]))
                    			semanticErrors[7]=1; 
                    	}
                    }break;
                case 5:
                    try{
                        if (fields[i].equals(""))
                        	semanticErrors[10]=1;
                        else {
                        	double score = Double.parseDouble(fields[i].replaceAll("\"", ""));
                        	if (score < 0 || score > 10)
                        		semanticErrors[9]=1;
                        }
                    }catch(NumberFormatException nf){
                    	semanticErrors[9]=1;
                    }
                    break;
                case 6:
                    String director = fields[i].replaceAll("\"", "");
                    if (director.equals(""))
                    	semanticErrors[11]=1;
                    break;
                case 7:
                    String actor1 = fields[i].replaceAll("\"", "");
                    if (actor1.equals(""))
                    	semanticErrors[12]=1;
                    break;
                case 8:
                    String actor2 = fields[i].replaceAll("\"", "");
                    if (actor2.equals(""))
                    	semanticErrors[13]=1;
                    break;
                case 9:
                    String actor3 = fields[i].replaceAll("\"", "");
                    if (actor3.equals(""))
                    	semanticErrors[14]=1;
                    break;
            }
		
        }
		return semanticErrors;
	}
	
	/**
	 *  this method helps the creation of the Movie object
	 * 	if there are more fields, this method would not change, but when using the constructor, it is hard coded and might need so modifications
	 * 	this method works similarly to the getSemanticErrors, but would work no matter what, since this method is only called if there are no
	 * 	semantic or syntax error
	 * 	as long as it hasn't encountered a comma, it continues to concatenate the characters into the string of the field
	 * 	if there was a quote, and it wasn't closed, it continues to read and doesn't identifies the comma as a delimiter 
	 * 
	 * @return string array where each string is the
	 */
	static String[] getFields(String line, int numberOfFieldsExpected) {
		String[] fields;
        int numberOfFields = 1;
        boolean inQuotes = false;
        fields = new String[numberOfFieldsExpected]; // this could also work with numberOfFields bc after the throws 
        int currentFieldNumber = 0; 
        
        fields[currentFieldNumber] = ""; // to create a string right away
        
        for (int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == '\"') {
                inQuotes = !inQuotes;
                // continue; this would remove the "" on the fields, and you don't want to delete them if title with comma !!
            }
            else if(line.charAt(i) == ',' && !inQuotes) {
                currentFieldNumber++; // change fields 
                if (currentFieldNumber == numberOfFieldsExpected) {
                    break; // the array is done and ready to be returned 
                }
                fields[currentFieldNumber] = ""; // to create a new string in the new field
                continue;
            }
            fields[currentFieldNumber]+=line.charAt(i); //concatenation of the characters of the info
        }
        return fields; // this returns all the contents of the line 
	}
}
