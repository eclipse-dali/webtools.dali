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
package org.eclipse.jpt.core.internal.context.persistence;

import org.eclipse.jpt.core.internal.IJpaStructureNode;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.orm.OrmXml;
import org.eclipse.jpt.core.internal.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;

public interface IMappingFileRef extends IJpaContextNode, IJpaStructureNode
{
	/**
	 * Return whether this mapping file ref is represented by an entry in the
	 * persistence.xml (false) or if it is instead virtual
	 */
	boolean isVirtual();
	
	
	// **************** file name **********************************************
	
	/**
	 * String constant associated with changes to the file name
	 */
	String FILE_NAME_PROPERTY = "fileNameProperty";
	
	/**
	 * Return the file name of the mapping file ref.
	 */
	String getFileName();
	
	/**
	 * Set the file name of the mapping file ref.
	 */
	void setFileName(String fileName);
	
	
	// **************** orm xml ************************************************
	
	String ORM_XML_PROPERTY = "ormXmlProperty";
	
	OrmXml getOrmXml();	
	
	
	// **************** udpating ***********************************************
	
	void initialize(XmlMappingFileRef mappingFileRef);
	
	void update(XmlMappingFileRef mappingFileRef);
	
	
	// *************************************************************************
	
	PersistenceUnitDefaults persistenceUnitDefaults();
	
	/**
	 * Return the XmlPersistentType listed in this mapping file
	 * with the given fullyQualifiedTypeName.  Return null if non exists.
	 */
	XmlPersistentType persistentTypeFor(String fullyQualifiedTypeName);
	
	/**
	 * Return whether the text representation of this persistence unit contains
	 * the given text offset
	 */
	boolean containsOffset(int textOffset);
}
