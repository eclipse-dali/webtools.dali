/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ElementChangedEvent;

/**
 * 
 */
public interface IJpaFile extends IJpaNodeModel
{
	/**
	 * Return the type of resource represented by this JPA file
	 * @see IResourceModel#getResourceType()
	 */
	String getResourceType();

	/**
	 * Return the IFile associated with this JPA file
	 */
	IFile getFile();

	/**
	 * Return the resource model represented by this JPA file
	 */
	IResourceModel getResourceModel();

	/**
	 * Return the content node corresponding to the given offset in the source.
	 * This may (and often will) be <code>null</code>.
	 */
	IJpaContentNode getContentNode(int offset);

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
