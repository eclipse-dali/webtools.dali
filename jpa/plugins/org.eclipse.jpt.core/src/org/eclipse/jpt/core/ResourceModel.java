/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.ListIterator;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.utility.model.Model;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ResourceModel extends Model
{
	/**
	 * Constant representing a Java resource type
	 * @see ResourceModel#getResourceType()
	 */
	static final String JAVA_RESOURCE_TYPE = "JAVA_RESOURCE_TYPE";
	
	/**
	 * Constant representing a persistence.xml resource type
	 * @see ResourceModel#getResourceType()
	 */
	static final String PERSISTENCE_RESOURCE_TYPE = "PERSISTENCE_RESOURCE_TYPE";
	
	/**
	 * Constant representing a mapping file (e.g. orm.xml) resource type
	 * @see ResourceModel#getResourceType()
	 */
	static final String ORM_RESOURCE_TYPE = "ORM_RESOURCE_TYPE";
	
	
	/**
	 * Return a unique identifier for all resource models of this type
	 */
	String getResourceType();
	
	
	// **************** root structure nodes *************************************
	
	/**
	 * String constant associated with changes to the list of root structure nodes
	 */
	final static String ROOT_STRUCTURE_NODES_LIST = "rootStructureNodes";
	
	/**
	 * Return a list iterator of all root structure nodes
	 */
	ListIterator<JpaStructureNode> rootStructureNodes();
	
	/**
	 * Return the size of all root structure nodes
	 */
	int rootStructureNodesSize();
	
	/**
	 * Return a structure node for the given text offset
	 */
	JpaStructureNode getStructureNode(int textOffset);
	
	void addRootStructureNode(JpaStructureNode structureNode);
	
	void addRootStructureNode(int index, JpaStructureNode structureNode);
	

	void javaElementChanged(ElementChangedEvent event);
	
	
	void addResourceModelChangeListener(ResourceModelListener listener);
	
	void removeResourceModelChangeListener(ResourceModelListener listener);
	
	
	void dispose();
	
	/**
	 * Use to resolve type information that could be dependent on other files being added/removed
	 */
	void resolveTypes();
}

