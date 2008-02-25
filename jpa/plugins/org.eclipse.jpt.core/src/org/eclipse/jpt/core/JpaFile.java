/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ElementChangedEvent;

/**
 * 
 */
public interface JpaFile extends JpaNode
{
	/**
	 * Return the type of resource represented by this JPA file
	 * @see ResourceModel#getResourceType()
	 */
	String getResourceType();

	/**
	 * Return the IFile associated with this JPA file
	 */
	IFile getFile();

	/**
	 * Return the resource model represented by this JPA file
	 */
	ResourceModel getResourceModel();
	
	/**
	 * Return the structure node best represented by the location in the file
	 */
	JpaStructureNode structureNode(int textOffset);

	/**
	 * Forward the Java element changed event to the JPA file's content.
	 */
	void javaElementChanged(ElementChangedEvent event);

	/**
	 * The JPA file has been removed from the JPA project. Clean up any
	 * hooks to external resources etc.
	 */
	void dispose();
	
	/**
	 * jpaFile was added to the JpaProject
	 * @param jpaFile
	 */
	void fileAdded(JpaFile jpaFile);
	
	/**
	 * jpaFile was removed from the JpaProject
	 * @param jpaFile
	 */
	void fileRemoved(JpaFile jpaFile);
}
