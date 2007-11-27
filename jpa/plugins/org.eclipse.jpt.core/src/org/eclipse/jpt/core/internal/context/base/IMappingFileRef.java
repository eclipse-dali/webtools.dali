/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;

public interface IMappingFileRef extends IJpaContextNode
{
	// **************** file name **********************************************
	
	/**
	 * String constant associated with changes to the file name
	 */
	final static String FILE_NAME_PROPERTY = "fileName";
	
	/**
	 * Return the file name of the mapping file ref.
	 */
	String getFileName();
	
	/**
	 * Set the file name of the mapping file ref.
	 */
	void setFileName(String fileName);
	
	
	// **************** udpating ***********************************************
	
	void initialize(XmlMappingFileRef mappingFileRef);
	
	void update(XmlMappingFileRef mappingFileRef);
}
