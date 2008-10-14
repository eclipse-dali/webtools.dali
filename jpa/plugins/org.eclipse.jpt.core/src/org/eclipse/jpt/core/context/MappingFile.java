/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MappingFile extends XmlContextNode, JpaStructureNode
{
	/**
	 * Return the underlying xml resource
	 */
	JpaXmlResource getXmlResource();
	
	/**
	 * Return the root object of this mapping file
	 */
	MappingFileRoot getRoot();
	
	/**
	 * Return the PersistentType listed in this mapping file
	 * with the given fullyQualifiedTypeName.  Return null if none exists.
	 */
	PersistentType getPersistentType(String fullyQualifiedTypeName);
	
	
	// **************** updating **********************************************
	
	/**
	 * Update the MappingFile context model object to match the JpaXmlResource 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(JpaXmlResource mappingFileResource);
}
