/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.persistence;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.PersistenceXmlContextNodeFactory;
import org.eclipse.jpt.core.context.persistence.PersistenceXmlDefinition;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceXmlDefinition;
import org.eclipse.jpt.core.jpa2.resource.persistence.Persistence2_0Factory;

public class Generic2_0PersistenceXmlDefinition
	extends AbstractPersistenceXmlDefinition
{
	// singleton
	private static final PersistenceXmlDefinition INSTANCE = 
			new Generic2_0PersistenceXmlDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static PersistenceXmlDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private Generic2_0PersistenceXmlDefinition() {
		super();
	}
	
	
	public EFactory getResourceNodeFactory() {
		return Persistence2_0Factory.eINSTANCE;
	}
	
	@Override
	protected PersistenceXmlContextNodeFactory buildContextNodeFactory() {
		return new Generic2_0PersistenceXmlContextNodeFactory();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.PERSISTENCE2_0_XML_CONTENT_TYPE;
	}
	
}
