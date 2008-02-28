/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MappingFileRef extends PersistenceJpaContextNode, JpaStructureNode
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
	
	void update(XmlMappingFileRef mappingFileRef);
	
	
	// *************************************************************************
	
	PersistenceUnitDefaults persistenceUnitDefaults();
	
	/**
	 * Return the OrmPersistentType listed in this mapping file
	 * with the given fullyQualifiedTypeName.  Return null if non exists.
	 */
	OrmPersistentType persistentTypeFor(String fullyQualifiedTypeName);
	
	/**
	 * Return whether the text representation of this persistence unit contains
	 * the given text offset
	 */
	boolean containsOffset(int textOffset);

}
