package org.fugerit.java.doc.freemarker.process;

import java.io.Serializable;
import java.util.Properties;

import lombok.Data;

@Data
public class ChainStepModel implements Serializable {

	private static final long serialVersionUID = 622077549080786391L;
	
	private String stepType;
	
	private Properties attributes;
	
}
