/*****************************************************************
<copyright>
	Fugerit Java Library org.fugerit.java.doc.ent 

	Copyright (c) 2019 Fugerit

	All rights reserved. This program and the accompanying materials
	are made available under the terms of the Apache License v2.0
	which accompanies this distribution, and is available at
	http://www.apache.org/licenses/
	(txt version : http://www.apache.org/licenses/LICENSE-2.0.txt
	html version : http://www.apache.org/licenses/LICENSE-2.0.html)

   This product includes software developed at
   The Apache Software Foundation (http://www.apache.org/).
</copyright>
*****************************************************************/
/*
 * @(#)SampleFacade.java
 *
 * @project    : org.fugerit.java.doc.ent
 * @package    : org.fugerit.java.doc.sample.facade
 * @creation   : 13/set/06
 * @license	   : META-INF/LICENSE.TXT
 */
package org.fugerit.java.doc.sample.facade;

import org.fugerit.java.doc.base.facade.DocHandlerFacade;
import org.fugerit.java.doc.base.facade.DocHandlerFactory;

/**
 * 
 *
 * @author mfranci
 *
 */
public class SampleFacade {

	private static DocHandlerFacade FACADE_MAIN = new DocHandlerFacade();
	private static DocHandlerFacade FACADE_ALT = new DocHandlerFacade();
	static {
		try {
			FACADE_MAIN = DocHandlerFactory.register( "cl://config/doc-handler-sample.xml" );
			FACADE_ALT = DocHandlerFactory.register( "cl://config/doc-handler-sample.xml" , "alternate-complete" );
		} catch (Exception e) {
			throw new RuntimeException( e );
		}	
	}
	
	public static DocHandlerFacade getInstance() {
		return getInstanceMain();
	}	
	
	public static DocHandlerFacade getInstanceMain() {
		return FACADE_MAIN;
	}	
	
	public static DocHandlerFacade getInstanceAlt() {
		return FACADE_ALT;
	}	
	
}
