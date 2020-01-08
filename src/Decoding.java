import java.util.BitSet;
import java.util.HashMap;
import java.util.Scanner;

public class Decoding {

	public static void main(String[] args) {

		Scanner pathInput = new Scanner(System.in);
		System.out.println("Enter the path to the file to be decompressed : \n");
		String path = pathInput.nextLine();
		pathInput.close();
		if(path.equals("")|| path==null) {
			System.out.println("INVALID PATH");
			return;
		}

		HashMap<Character,Object> encodedMap = Utils.getMapFromFilePath(path);
		System.out.println(encodedMap);
		if(encodedMap == null) {
			return;
		}
		
		@SuppressWarnings("unchecked")
		HashMap<String, Character> decodingMap = (HashMap<String, Character>) encodedMap.get('M');		
		
		byte[] dataToDecode = (byte[]) encodedMap.get('D');
		BitSet bitData = BitSet.valueOf(dataToDecode);
		
		String decodedString = "";
		String tempString = "";
		for(int i = 0;i<bitData.size();i++) {
			if(tempString==null) {
				return;
			}
			if(decodingMap!=null && decodingMap.containsKey(tempString)) {
				decodedString += decodingMap.get(tempString);
				tempString = "";
			}
			boolean currentBit = bitData.get(i);
			if(currentBit) {
				tempString += "1";
			}
			else {
				tempString += "0";
			}
		}
		
		System.out.println("decoded String - " + decodedString);
		
		System.out.println("\n ***DECODING DONE*** \n\n ");
	}

	
}
