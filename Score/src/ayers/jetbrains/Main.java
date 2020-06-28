package ayers.jetbrains;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        File file = new File(args[0]);
        double ARIscore = 0.0;
		double FKscore = 0.0;
		double SMOGscore = 0.0;
		double CLscore = 0.0;
        int sentenceCount = 0;
        int wordCount = 1;
        int charCount = 0;
		int syllableCount = 0;
		int polySyllableCount = 0;
		double avgCharPerWords = 0;
		double avgSentencesPerWords = 0;
		int ARIindex = 0;
		int FKindex = 0;
		int SMOGindex = 0;
		int CLindex = 0;
        String msg = "";
		
        try (Scanner scanner = new Scanner(file)) {
            msg = msg + scanner.nextLine();
            while (scanner.hasNext()) {
                msg = msg + " " + scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Exception occurred");
        } finally {
            System.out.println("The text is:");
            System.out.println(msg);
            System.out.println();
        }
		
        wordCount = getWordCount(msg);
        System.out.println("Words: " + wordCount);
        
		sentenceCount = getSentenceCount(msg);	
        System.out.println("Sentences: " + sentenceCount);
        
        charCount = getcharCount(msg);
        System.out.println("Characters: " + charCount);
        
		syllableCount = getSyllableCount(msg);
		System.out.println("Syllables: " + syllableCount);
		
		polySyllableCount = getPolySyllableCount(msg);
		System.out.println("Polysyllables: " + polySyllableCount);
		
		Scanner scanner2 = new Scanner(System.in);
		System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
		String method = scanner2.nextLine();
		System.out.println();
		scanner2.close();
		
		switch (method) {
			case "ARI": {
				System.out.print("Automated Readability Index: ");
				ARIscore = calculateARI(sentenceCount, wordCount, charCount);
				System.out.printf("%.2f", ARIscore);
				ARIindex = getReadabilityIndex(ARIscore);
				break;
			}
			case "FK": {
				System.out.print("Flesch–Kincaid readability tests: ");
				FKscore = calculateFK(sentenceCount, wordCount, syllableCount);
				System.out.printf("%.2f", FKscore);
				FKindex = getReadabilityIndex(FKscore);
				break;
			}
			case "SMOG": {
				System.out.print("Simple Measure of Gobbledygook: ");
				SMOGscore = calculateSMOG(sentenceCount, polySyllableCount);
				System.out.printf("%.2f", SMOGscore);
				SMOGindex = getReadabilityIndex(SMOGscore);
				break;
			}
			case "CL": {
				System.out.print("Coleman–Liau index: ");
				avgCharPerWords = (double) charCount / ((double) wordCount / 100);
				avgSentencesPerWords = (double) sentenceCount / ((double) wordCount / 100);
				CLscore = calculateCL(avgSentencesPerWords, avgCharPerWords);
				System.out.printf("%.2f", CLscore);
				CLindex = getReadabilityIndex(CLscore);
				break;
			}
			case "all": {
				System.out.print("Automated Readability Index: ");
				ARIscore = calculateARI(sentenceCount, wordCount, charCount);
				System.out.printf("%.2f", ARIscore);
				ARIindex = getReadabilityIndex(ARIscore);
				
				System.out.print("Flesch–Kincaid readability tests: ");
				FKscore = calculateFK(sentenceCount, wordCount, syllableCount);
				System.out.printf("%.2f", FKscore);
				FKindex = getReadabilityIndex(FKscore);
				
				System.out.print("Simple Measure of Gobbledygook: ");
				SMOGscore = calculateSMOG(sentenceCount, polySyllableCount);
				System.out.printf("%.2f", SMOGscore);
				SMOGindex = getReadabilityIndex(SMOGscore);
				
				System.out.print("Coleman–Liau index: ");
				avgCharPerWords = (double) charCount / ((double) wordCount / 100);
				avgSentencesPerWords = (double) sentenceCount / ((double) wordCount / 100);
				CLscore = calculateCL(avgSentencesPerWords, avgCharPerWords);
				System.out.printf("%.2f", CLscore);
				CLindex = getReadabilityIndex(CLscore);
				break;
			}
		}
		
		if (method.equals("all")) {
			System.out.println();
			double avgRI = (double)(ARIindex + FKindex + SMOGindex + CLindex) / 4;
			System.out.print("This text should be understood in average by ");
			System.out.printf("%.2f", avgRI);
			System.out.println(" year olds.");
		}
		
    }
    
    static int getReadabilityIndex(double score) {
        int value = (int) Math.ceil(score);
        
        switch (value) {
            case 1: {
                System.out.println(" (about 5 year olds).");
				return 5;
            }
            case 2: {
                System.out.println(" (about 6 year olds).");
				return 6;
            }
            case 3: {
                System.out.println(" (about 7 year olds).");
				return 7;
            }
            case 4: {
                System.out.println(" (about 9 year olds).");
				return 9;
            }
            case 5: {
                System.out.println(" (about 10 year olds).");
				return 10;
            }
            case 6: {
                System.out.println(" (about 11 year olds).");
				return 11;
            }
            case 7: {
                System.out.println(" (about 12 year olds).");
				return 12;
            }
            case 8: {
                System.out.println(" (about 13 year olds).");
				return 13;
            }
            case 9: {
                System.out.println(" (about 14 year olds).");
				return 14;
            }
            case 10: {
                System.out.println(" (about 15 year olds).");
				return 15;
            }
            case 11: {
                System.out.println(" (about 16 year olds).");
				return 16;
            }
            case 12: {
                System.out.println(" (about 17 year olds).");
				return 17;
            }
            case 13: {
                System.out.println(" (about 18 year olds).");
				return 18;
            }
            case 14: {
                System.out.println(" (about 24+ year olds).");
				return 24;
            }
            case 15: {
                System.out.println(" (about 24+ year olds).");
				return 24;
            }
        }
		return 0;
    }
    
    static int getPolySyllableCount(String msg) {	
		msg = msg.toLowerCase();
		// remove punctuation
		msg = msg.replace(".", "");
		msg = msg.replace("!", "");
		msg = msg.replace("?", "");
		msg = msg.replace(",", "");
		
		String[] words = msg.split(" ");
		int count = 0;
		
		for (int i = 0; i < words.length; i++) {
			if (getSyllableCount(words[i]) > 2) {
				count++;
			}
		}
		
		return count;
	}
	
	static boolean isVowel(char character) {
		if (character == 'a' || character == 'e' || character == 'i' || character == 'o' || character == 'u' || character == 'y') {
			return true;
		} else {
			return false;
		}
	}
	
	static int getSyllableCount(String msg) {
		msg = msg.toLowerCase();
		// remove punctuation
		msg = msg.replace(".", "");
		msg = msg.replace("!", "");
		msg = msg.replace("?", "");
		msg = msg.replace(",", "");
		
		String[] words = msg.split(" ");
		int syllables = 0;
		int vowels = 0;
		
		for (int i = 0; i < words.length; i++) {
			for (int j = 0; j < words[i].length(); j++) {
				// if char is vowel AND the previous char was not a vowel AND the last character is not an 'e'
				if (isVowel(words[i].charAt(j)) && !(j > 0 && isVowel(words[i].charAt(j-1))) && !(j == words[i].length() - 1 && words[i].charAt(j) == 'e')) {
					vowels++;
				}
			}
			if (vowels == 0) {
				vowels++;
			}
			syllables += vowels;
			vowels = 0;
		}
		
		return syllables;
	}
    
    static int getcharCount(String msg) {
        int count = msg.length();
        		
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) == ' ' || msg.charAt(i) == '\n' || msg.charAt(i) == '\t') {
				count--;
            }
        }
        
        return count;
    }
    
    static int getSentenceCount(String msg) {
        int num = 0;
        char lastChar = msg.charAt(msg.length()-1);
		if (lastChar != '.' && lastChar != '?' && lastChar != '!') {
			num++;
		}
		for (char character : msg.toCharArray()) {
			if (character == '.' || character == '!' || character == '?')
			{
				num++;
			}
		}
        return num;
    }
    
    static int getWordCount(String msg) {
        int count = 0;
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) == ' ') {
                count++;
            }
        }
        if (msg.charAt(msg.length()-1) != ' ') {
            count++;
        }
        return count;
    }
    
    static double calculateARI(int sentences, int words, int characters) {
        double value = 4.71 * ((double) characters / (double) words) + 0.5 * ((double) words / (double) sentences) - 21.43;
        return value;
    }
	
	static double calculateFK(int sentences, int words, int syllables) {
		double value = 0.39 * ((double) words / (double) sentences) + 11.8 * ((double) syllables / (double) words) - 15.59;
		return value;
	}
	
	static double calculateSMOG(int sentences, int polysyllables) {
		double value = 1.043 * Math.sqrt(polysyllables * (30 / (double) sentences)) + 3.1291;
		return value;
	}
	
	static double calculateCL(double avgSentences, double avgChars) {
		double value = 0.0588 * (double) avgChars - 0.296 * (double) avgSentences - 15.8;
		return value;
	}
}
