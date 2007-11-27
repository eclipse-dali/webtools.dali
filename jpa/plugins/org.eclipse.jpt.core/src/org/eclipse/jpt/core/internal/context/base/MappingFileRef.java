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

import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;

public class MappingFileRef extends JpaContextNode 
	implements IMappingFileRef
{
	protected XmlMappingFileRef xmlMappingFileRef;
	
	protected String fileName;
	
	
	public MappingFileRef(IPersistenceUnit parent) {
		super(parent);
	}
	
	
	// **************** file name **********************************************
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String newFileName) {
		String oldFileName = fileName;
		fileName = newFileName;
		xmlMappingFileRef.setFileName(newFileName);
		firePropertyChanged(FILE_NAME_PROPERTY, oldFileName, newFileName);
	}
	
	
	// **************** updating ***********************************************
	
	public void initialize(XmlMappingFileRef mappingFileRef) {
		xmlMappingFileRef = mappingFileRef;
		fileName = mappingFileRef.getFileName();
	}
	
	public void update(XmlMappingFileRef mappingFileRef) {
		this.xmlMappingFileRef = mappingFileRef;
		setFileName(mappingFileRef.getFileName());
	}
	
	
	// *************************************************************************
	
	public ITextRange validationTextRange() {
		return this.xmlMappingFileRef.validationTextRange();
	}
}
