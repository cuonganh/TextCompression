import java.util.BitSet;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Encoding {
	
	public static void main(String[] args) {
			
		long startTime = System.currentTimeMillis();
		//PATH TO THE FILE TO BE COMPRESSED
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the path to the file to be compressed : \n");
		String path = sc.nextLine();
		sc.close();
		if(path.equals("")|| path==null) {
			System.out.println("INVALID PATH");
			return;
		}
		
		//TEXT FROM THE FILE IS CONVERTED TO STRING
		String dataToEncode = Utils.getStringFromPath(path);
		if(dataToEncode.equals("")||dataToEncode==null) {
			System.out.println("INVALID DATA");
			return;
		}
		System.out.println("\nInput file read");
		
		
		//ENCODING MAPPING FOR THE STRING IS CREATED TO COMPRESS IT
		HashMap<Character, String> encodingHashMap = encodeString(dataToEncode);
		
		//COMPRESSED STRING FROM THE FUNCTION AND CONVERT IT TO BYTE ARRAY
		BitSet encodedBitString = Utils.getBitSetfromString(dataToEncode,encodingHashMap);
		
		byte[] encodedByteArray = encodedBitString.toByteArray();
		System.out.println("\nBitset to Byte Array");
		
		//HASHMAP USED FOR DECODING TO BE WRITTEN IN OUTPUT FROM CHARBITMAP
		HashMap<String, Character> decodingHashMap = Utils.reverseMap(encodingHashMap);
		System.out.println("\nDecoding Hashmap created");
		
		
		//CREATING OUTPUT FILE
		HashMap<Character, Object> output = new HashMap<>();
		output.put('M', decodingHashMap);
		output.put('D', encodedByteArray);
		Utils.createOutputFile(output,path);
		
		System.out.println("\nOutput file created ");
		
		long duration = System.currentTimeMillis() - startTime;

		System.out.println("\nEncoding finished in "+duration/1000f+" seconds");
	}

	//FUNCTION TO RETURN HASHMAP TO ENCODE STRING
	private static HashMap<Character, String> encodeString(String text) {
		
		//CREATING A HASHMAP FOR FREQUENCY OF DIFFERENT CHARACTERS IN STRING
		int[] freqArray = new int[256];
		for(int i=0;i<text.length();i++) {
			char currentChar = text.charAt(i);
			freqArray[currentChar]++;
		}
		
		System.out.println("\nFrequency Array created");
		
		//CREATED MIN PRIORITY QUEUE
		PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
		
		//ADD ALL HUFFMAN TREE NODES TO PRIORITY QUEUE
		for(int i=0;i<freqArray.length;i++) {
			char currentChar = (char) i;
			if(freqArray[i]==0) {
				continue;
			}
			priorityQueue.add(new HuffmanNode(currentChar, freqArray[i]));
		}
		
		System.out.println("\nPriority Queue created");
		
		//CREATING A HUFFMAN TREE
		HuffmanNode root = null;
		while(priorityQueue.size()>1) {
			HuffmanNode left = priorityQueue.poll();
			HuffmanNode right = priorityQueue.poll();
			int newFreq;
			newFreq = left.getFreq()+right.getFreq();
			root = new HuffmanNode('$',newFreq,left,right);
			priorityQueue.add(root);
		}
		

		System.out.println("\nHuffman Tree created");
			
		//CHARACTER AND STRING MAP FROM THE HUFFMAN TREE
		HashMap<Character,String> charStringMap = getMapfromTree(root,"1"); 
		System.out.println("\nEncoding HashMap created");
		
		return charStringMap;
	}
	
	//FUNCTION TO GIVE CHARACTER TO STRING HASHMAP FOR GIVEN HUFFMAN TREE
	private static HashMap<Character, String> getMapfromTree(HuffmanNode root,String currentString) {
		if(root == null) {
			return null;
		}
		
		if(root.getLeft()== null && root.getRight() == null) {
			HashMap<Character,String> newMap = new HashMap<>();
			newMap.put(root.getData(), currentString);
			return newMap;
		}
		
		HashMap<Character,String> leftMap = getMapfromTree(root.getLeft(), currentString+"0");
		HashMap<Character,String> rightMap = getMapfromTree(root.getRight(), currentString+"1");
		
		HashMap<Character,String> ans = new HashMap<>();		
		ans.putAll(leftMap);
		ans.putAll(rightMap);
		return ans;
	}	
}