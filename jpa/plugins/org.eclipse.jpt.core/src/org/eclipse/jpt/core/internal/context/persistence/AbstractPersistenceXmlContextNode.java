/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.persistence.PersistenceXmlContextNodeFactory;
import org.eclipse.jpt.core.context.persistence.PersistenceXmlDefinition;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;

/**
 * Use this abstract class for context nodes that are part of an OrmXml file.
 * This will not work for purely MappingFile implementations
 */
public abstract class AbstractPersistenceXmlContextNode
	extends AbstractXmlContextNode
	implements XmlContextNode
{

	// ********** constructor **********

	protected AbstractPersistenceXmlContextNode(JpaContextNode parent) {
		super(parent);
	}
	
	public PersistenceXmlDefinition getPersistenceXmlDefinition() {
		return (PersistenceXmlDefinition) getJpaPlatform().getResourceDefinition(getContentType());
	}
	
	public EFactory getResourceNodeFactory() {
		return getPersistenceXmlDefinition().getResourceNodeFactory();
	}
	
	public PersistenceXmlContextNodeFactory getContextNodeFactory() {
		return getPersistenceXmlDefinition().getContextNodeFactory();
	}
}
