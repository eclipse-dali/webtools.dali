/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.Collection;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.orm.MappingFileDefinition;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;

/**
 * JPA mapping file (typically <code>orm.xml</code>).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.1
 */
public interface MappingFile
	extends JpaContextModel, PersistentTypeContainer, DeleteTypeRefactoringParticipant, TypeRefactoringParticipant
{
	MappingFileRef getParent();

	/**
	 * Return the mapping file's root.
	 * This can be <code>null</code>.
	 */
	Root getRoot();

	/**
	 * String constant associated with changes to the
	 * {@link #getRoot() root property}.
	 */
	String ROOT_PROPERTY = "root"; //$NON-NLS-1$

	/**
	 * Return the mapping file's definition.
	 */
	MappingFileDefinition getDefinition();

	/**
	 * Return whether the mapping file exists in the specified folder.
	 */
	boolean isIn(IFolder folder);

	/**
	 * Return the corresponding resource mapping file. The type of the returned
	 * resource mapping file is determined by the implementation and its
	 * clients.
	 */
	Object getResourceMappingFile();

	/**
	 * Add the appropriate root structure nodes to the collection that
	 * correspond to the given JPA file.
	 * @see JpaFile#getRootStructureNodes()
	 */
	void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes);


	// ********** queries/generators **********

	/**
	 * Return the queries defined directly in the mapping file (as opposed to
	 * including the queries defined in Java types referenced by the mapping
	 * file).
	 */
	Iterable<Query> getMappingFileQueries();

	/**
	 * Return the generators defined directly in the mapping file (as opposed to
	 * including the generators defined in Java types referenced by the mapping
	 * file).
	 */
	Iterable<Generator> getMappingFileGenerators();


	// ********** mapping file root **********

	/**
	 * Common interface for the root of a mapping file.
	 */
	interface Root
		extends JpaStructureNode, PersistentTypeContainer, AccessReference
	{
		/**
		 * covariant override
		 */
		MappingFile getParent();

		/**
		 * Return the specified catalog if present, otherwise return the default
		 * catalog.
		 */
		String getCatalog();

		/**
		 * Return the specified schema if present, otherwise return the default
		 * schema.
		 */
		String getSchema();

		/**
		 * Return the metadata defined within the mapping file
		 * <em>for the persistence unit</em>.
		 * Return <code>null</code> if none exists.
		 * 
		 * @see MappingFilePersistenceUnitMetadata#resourceExists()
		 */
		MappingFilePersistenceUnitMetadata getPersistenceUnitMetadata();
	}
}
