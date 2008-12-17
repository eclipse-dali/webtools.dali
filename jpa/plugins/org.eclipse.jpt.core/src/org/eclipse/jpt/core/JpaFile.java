/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

/**
 * A JPA Project contains JPA files for all files in the project that
 * are relevant to the JPA platform.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaFile
	extends JpaNode
{

	/**
	 * Return the JPA file's Eclipse file.
	 */
	IFile getFile();

	/**
	 * Return all the types that are "persistable", as defined by the JPA spec.
	 */
	Iterator<JavaResourcePersistentType> persistableTypes();

	// ********** event handlers **********

	/**
	 * A "significant" JDT Java element has changed.
	 * Synchronize the JPA file's resource model.
	 */
	void updateFromJava();

	/**
	 * The specified compilation unit has changed.
	 * If the change is relevant to the JPA file, synchronize its resource model
	 * and return true.
	 */
	boolean updateFromJava(ICompilationUnit compilationUnit);

	/**
	 * Calculate any information that is dependent on other files
	 * being added or removed. For now, only the Java resource model needs
	 * this; in particular, it needs to re-calculate resolved types.
	 */
	void jpaFilesChanged();


	// ********** resource type **********

	/**
	 * Return the type of resource held by the JPA file.
	 */
	String getResourceType();


	// ********** resource model listeners **********

	/**
	 * Changes to the resource model result in events. In particular, the JPA
	 * project performs an "update" whenever a resource changes.
	 */
	void addResourceModelListener(JpaResourceModelListener listener);

	/**
	 * @see #addResourceModelChangeListener(ResourceModelListener)
	 */
	void removeResourceModelListener(JpaResourceModelListener listener);


	// ********** root structure nodes **********

	/**
	 * Return the JPA file's root structure nodes.
	 */
	Iterator<JpaStructureNode> rootStructureNodes();
		String ROOT_STRUCTURE_NODES_COLLECTION = "rootStructureNodes"; //$NON-NLS-1$

	/**
	 * Return the count of the JPA file's root context model objects.
	 */
	int rootStructureNodesSize();

	/**
	 * Add a root structure node.
	 * There is the potential for multiple root structure nodes 
	 * for a particular key. For example, a Java file that is listed
	 * both as a <class> in the persistence.xml and as an <entity> in
	 * an orm.xml file. In this case the orm.xml file needs to set
	 * the root structure node after the Java class reference.
	 * Last one in during project "update" wins.
	 */
	void addRootStructureNode(Object key, JpaStructureNode rootStructureNode);

	/**
	 * @see #addRootStructureNode(Object, JpaStructureNode)
	 */
	void removeRootStructureNode(Object key);

	/**
	 * Return the structure node best corresponding to the location in the file.
	 */
	JpaStructureNode getStructureNode(int textOffset);

}
