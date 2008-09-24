/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaRootContextNode extends JpaContextNode
{
	/**
	 * String constant associated with changes to the persistenceXml property
	 */
	public final static String PERSISTENCE_XML_PROPERTY = "persistenceXml"; //$NON-NLS-1$
	
	/** 
	 * Return the content represented by the persistence.xml file associated with 
	 * this project.
	 * This may be null. 
	 */
	PersistenceXml getPersistenceXml();
	
	/**
	 * Add a persistence.xml file to this content and return the content associated
	 * with it.
	 * Throws {@link IllegalStateException} if a persistence.xml already exists.
	 */
	PersistenceXml addPersistenceXml();
	
	/**
	 * Remove the persistence.xml file from this content.
	 * Throws {@link IllegalStateException} if a persistence.xml does not exist.
	 */
	void removePersistenceXml();
	
	
	// **************** updating ***********************************************
	
	/**
	 * Update the context model with the content of the JPA project
	 */
	void update(IProgressMonitor monitor);
	
	
	// **************** validation *********************************************
	
	/**
	 * Add validation messages to the specified list.
	 */
	public void validate(List<IMessage> messages);

}
