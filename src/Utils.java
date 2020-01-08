import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.HashMap;
import java.util.stream.Stream;

public class Utils {
	
	//FUNTION TO GET STRING FROM THE FILE AT THE GIVEN PATH
	public static String getStringFromPath(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
		{
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return contentBuilder.toString();
	}
	
	//FUNCTION TO GET BITSET FROM GIVEN TEXT USING THE CHARBIT HASHMAP
	public static BitSet getBitSetfromString(String text, HashMap<Character, String> charBitMap) {
		StringBuffer temp = new StringBuffer();
		for(int i=0;i<text.length();i++) {
			char currentChar = text.charAt(i);
			temp.append(charBitMap.get(currentChar));
		}
		System.out.println("\nString to bitString");
		
		
		BitSet bitset = new BitSet(temp.length());
		for(int i=0;i<temp.length();i++) {
			if(temp.charAt(i)=='1') {
				bitset.set(i);
			}
		}
		System.out.println("\nBitString to Bitset");
		
		return bitset;
	}
	
	//FUNCTION TO GIVE DECODING HASHMAP FROM ENCODING HASHMAP
	public static HashMap<String, Character> reverseMap(HashMap<Character, String> charBitMap) {
		
		HashMap<String, Character> mapToReturn = new HashMap<>();
		for(char currentChar: charBitMap.keySet()) {
			mapToReturn.put(charBitMap.get(currentChar), currentChar);
		}
		
		return mapToReturn;
	}


	//FUNCTION TO CREATE AN OUTPUT FILE FOR THE GIVEN PATH
	public static void createOutputFile(HashMap<Character, Object> output, String path) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path+".djn");
			ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
			outputStream.writeObject(output);
			outputStream.close();
			fileOut.close();
		} catch (IOException e) {
			System.out.println("Utils line 68");
			e.printStackTrace();
		}
	}
	
	//FUNCTION TO GET HASHMAP FROM THE GIVEN PATH
	@SuppressWarnings("unchecked")
	public static HashMap<Character, Object> getMapFromFilePath(String path) {
		HashMap<Character, Object> toReturn = null;
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream inputStream = new ObjectInputStream(fileIn);
			toReturn = (HashMap<Character, Object>) inputStream.readObject();
			inputStream.close();
			fileIn.close();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("INVALID PATH OR FILE");
			return null;
		}
		//System.out.println("toReturn "+ toReturn);
		return toReturn;
	}
}
