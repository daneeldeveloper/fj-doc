package org.fugerit.java.doc.base.config;

public class DocConstants {

	public static final String EURO_CONST = "€";
	
	private static final DocConstants DEF = new DocConstants();
	
	public static DocConstants getInstance() {
		return DEF;
	}
	
	public String getEuro() {
		return EURO_CONST;
	}

	public static final String STYLE_BOLD = "bold";
	public static final String STYLE_UNDERLINE = "underline";
	public static final String STYLE_ITALIC = "italic";
	public static final String STYLE_BOLDITALIC = "bolditalic";
	public static final String STYLE_NORMAL = "normal";

	public static final String ALIGN_CENTER = "center";
	public static final String ALIGN_RIGHT = "right";
	public static final String ALIGN_LEFT = "left";
	public static final String ALIGN_JUSTIFY = "justify";
	public static final String ALIGN_JUSTIFYALL = "justifyall";
	
	public static final String VALIGN_MIDDLE = "middle";
	public static final String VALIGN_TOP = "top";
	public static final String VALIGN_BOTTOM = "bottom";
	
}
