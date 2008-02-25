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

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * Tweak the node interface with JPA-specific protocol.
 */
public interface JpaNode extends Node, IAdaptable
{

	/**
	 * Return the JPA project the node belongs to.
	 */
	JpaProject jpaProject();

	/**
	 * Return the resource that most directly contains the node.
	 * This is used by JpaHelper.
	 */
	IResource resource();
	
	
	// ********** validation **********
	
	/**
	 * Adds to the list of current messages
	 */
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot);

	// ********** covariant overrides **********

	JpaNode parent();

	JpaProject root();

}
