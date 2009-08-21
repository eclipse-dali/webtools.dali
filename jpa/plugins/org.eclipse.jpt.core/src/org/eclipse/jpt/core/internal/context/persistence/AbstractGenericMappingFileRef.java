/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.utility.TextRange;

public abstract class AbstractGenericMappingFileRef
	extends AbstractMappingFileRef
{
	protected XmlMappingFileRef xmlMappingFileRef;


	// **************** construction/initialization ****************************

	protected AbstractGenericMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef) {
		super(parent, xmlMappingFileRef.getFileName());
		this.xmlMappingFileRef = xmlMappingFileRef;
	}
	
	
	// **************** file name **********************************************
	
	public void setFileName(String newFileName) {
		String old = this.fileName;
		this.fileName = newFileName;
		this.xmlMappingFileRef.setFileName(newFileName);
		this.firePropertyChanged(FILE_NAME_PROPERTY, old, newFileName);
	}

	protected void setFileName_(String newFileName) {
		String old = this.fileName;
		this.fileName = newFileName;
		this.firePropertyChanged(FILE_NAME_PROPERTY, old, newFileName);
	}
	
	
	// **************** MappingFileRef impl ************************************
	
	public void update(XmlMappingFileRef mappingFileRef) {
		this.xmlMappingFileRef = mappingFileRef;
		this.setFileName_(this.getResourceFileName());
		super.update();
	}
	
	protected String getResourceFileName() {
		return this.xmlMappingFileRef.getFileName();
	}
	
	public boolean isImplied() {
		return false;
	}
	
	public boolean containsOffset(int textOffset) {
		return this.xmlMappingFileRef.containsOffset(textOffset);
	}
	
	
	// **************** JpaStructureNode impl **********************************
	
	public TextRange getSelectionTextRange() {
		return this.xmlMappingFileRef.getSelectionTextRange();
	}
	
	public TextRange getValidationTextRange() {
		return this.xmlMappingFileRef.getValidationTextRange();
	}
}
