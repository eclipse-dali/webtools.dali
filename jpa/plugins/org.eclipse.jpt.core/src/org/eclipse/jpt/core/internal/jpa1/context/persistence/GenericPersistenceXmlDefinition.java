/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.persistence;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.PersistenceXmlContextNodeFactory;
import org.eclipse.jpt.core.context.persistence.PersistenceXmlDefinition;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceXmlDefinition;
import org.eclipse.jpt.core.internal.context.persistence.GenericPersistenceXmlContextNodeFactory;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;

public class GenericPersistenceXmlDefinition
	extends AbstractPersistenceXmlDefinition
{
	// singleton
	private static final PersistenceXmlDefinition INSTANCE = 
			new GenericPersistenceXmlDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static PersistenceXmlDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private GenericPersistenceXmlDefinition() {
		super();
	}
	
	
	public EFactory getResourceNodeFactory() {
		return PersistenceFactory.eINSTANCE;
	}
	
	@Override
	protected PersistenceXmlContextNodeFactory buildContextNodeFactory() {
		return new GenericPersistenceXmlContextNodeFactory();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE;
	}
	
}
