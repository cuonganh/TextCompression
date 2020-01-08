
public class HuffmanNode implements Comparable<HuffmanNode>{
	
	private char data;
	private int freq;
	private HuffmanNode left;
	private HuffmanNode right;
	
	public HuffmanNode(char data, int freq) {
		super();
		this.data = data;
		this.freq = freq;
		this.left = null;
		this.right = null;
	}

	public HuffmanNode(char data, int freq, HuffmanNode left, HuffmanNode right) {
		super();
		this.data = data;
		this.freq = freq;
		this.left = left;
		this.right = right;
	}

	public int getFreq() {
		return freq;
	}

	public char getData() {
		return data;
	}
	
	public HuffmanNode getLeft() {
		return left;
	}

	public void setLeft(HuffmanNode left) {
		this.left = left;
	}

	public HuffmanNode getRight() {
		return right;
	}

	public void setRight(HuffmanNode right) {
		this.right = right;
	}

	@Override
	public int compareTo(HuffmanNode o) {
		if(this.getFreq() > o.getFreq())	return 1;
		if(this.getFreq() < o.getFreq()) return -1;
		return 0;
	}
}
