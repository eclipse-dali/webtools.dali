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

import java.util.ListIterator;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.IJpaStructureNode;
import org.eclipse.jpt.utility.internal.model.Model;

public interface IResourceModel extends Model
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
	
	
	// **************** root context nodes *************************************
	
	/**
	 * String constant associated with changes to the list of root context nodes
	 */
	final static String ROOT_CONTEXT_NODE_LIST = "rootContextNodes";
	
	/**
	 * Return a list iterator of all root context nodes
	 */
	ListIterator<IJpaContextNode> rootContextNodes();
	
	/**
	 * Return a structural node for the given text offset
	 */
	IJpaStructureNode structureNode(int textOffset);
	
	
	void handleJavaElementChangedEvent(ElementChangedEvent event);
	
	
	void addResourceModelChangeListener(IResourceModelListener listener);
	
	void removeResourceModelChangeListener(IResourceModelListener listener);
	
	
	void dispose();
	
	/**
	 * Use to resolve type information that could be dependent on other files being added/removed
	 */
	void resolveTypes();
}

