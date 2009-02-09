/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFilePersistenceUnitDefaults;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;

/**
 * This is the context model corresponding to the 
 * persistence resource model XmlMappingFileRef,
 * which corresponds to the 'mapping-file' tag in the persistence.xml.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MappingFileRef
	extends XmlContextNode, JpaStructureNode
{

	// ********** file name **********

	/**
	 * String constant associated with changes to the file name.
	 */
	String FILE_NAME_PROPERTY = "fileName"; //$NON-NLS-1$

	/**
	 * Return the file name of the mapping file ref.
	 */
	String getFileName();

	/**
	 * Set the file name of the mapping file ref.
	 */
	void setFileName(String fileName);


	// ********** mapping file (orm.xml) **********

	/**
	 * String constant associated with changes to the mapping file.
	 */
	String MAPPING_FILE_PROPERTY = "mappingFile"; //$NON-NLS-1$

	/**
	 * Return the mapping file ref's mapping file corresponding to the file name.
	 */
	MappingFile getMappingFile();


	// ********** updating **********

	/**
	 * Update the MappingFileRef context model object to match the XmlMappingFileRef 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(XmlMappingFileRef mappingFileRef);


	// ********** queries **********

	/**
	 * Return whether the mapping file ref is "implied" by, or explicitly
	 * specified in, the persistence.xml.
	 */
	boolean isImplied();

	/**
	 * Return the mapping file's persistence unit defaults.
	 */
	MappingFilePersistenceUnitDefaults getPersistenceUnitDefaults();

	/**
	 * Return whether the mapping file's persistence unit defaults exist.
	 */
	boolean persistenceUnitDefaultsExists();

	/**
	 * Return the persistent type, as listed in the mapping file,
	 * with the specified name. Return null if it does not exists.
	 */
	PersistentType getPersistentType(String typeName);

	/**
	 * Return whether the text representation of the mapping file contains
	 * the specified text offset.
	 */
	boolean containsOffset(int textOffset);

}
