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

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ElementChangedEvent;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
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
	JpaStructureNode getStructureNode(int textOffset);

	/**
	 * Forward the Java element changed event to the JPA file's content.
	 */
	void javaElementChanged(ElementChangedEvent event);

	/**
	 * The JPA file has been removed from the JPA project. Clean up any
	 * hooks to external resources etc.
	 */
	void dispose();
	
}
