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
package org.eclipse.jpt.core.internal;

import org.eclipse.jdt.core.ElementChangedEvent;

public interface IResourceModel
{
	/**
	 * Constant representing a Java resource type
	 * @see IResourceModel#getResourceType()
	 */
	static final String JAVA_RESOURCE_TYPE = "JAVA_RESOURCE_TYPE";
	
	/**
	 * Constant representing a persistence.xml resource type
	 * @see IResourceModel#getResourceType()
	 */
	static final String PERSISTENCE_RESOURCE_TYPE = "PERSISTENCE_RESOURCE_TYPE";
	
	/**
	 * Constant representing a mapping file (e.g. orm.xml) resource type
	 * @see IResourceModel#getResourceType()
	 */
	static final String ORM_RESOURCE_TYPE = "ORM_RESOURCE_TYPE";
	
	
	/**
	 * Return a unique identifier for all resource models of this type
	 */
	String getResourceType();
	
	/**
	 * Return the content node corresponding to the given offset in the source.
	 * This may (and often will) be <code>null</code>.
	 */
	IJpaContentNode getContentNode(int offset);
	
	void dispose();
	
	void handleJavaElementChangedEvent(ElementChangedEvent event);
}

