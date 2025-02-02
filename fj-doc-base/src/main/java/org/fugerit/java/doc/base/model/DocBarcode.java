package org.fugerit.java.doc.base.model;

public class DocBarcode extends DocElement {
	
	public static final String TAG_NAME = "barcode";

	/**
	 * 
	 */
	private static final long serialVersionUID = 887515352642661380L;

	private int size;
	
	private String text;
	
	private String type;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
