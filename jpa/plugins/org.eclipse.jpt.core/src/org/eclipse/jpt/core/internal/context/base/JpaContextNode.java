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
package org.eclipse.jpt.core.internal.context.base;

import org.eclipse.jpt.core.internal.IJpaNode;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.JpaNode;
import org.eclipse.jpt.core.internal.SimpleTextRange;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.platform.base.IJpaBaseContextFactory;

public abstract class JpaContextNode extends JpaNode
	implements IJpaContextNode
{
	// **************** constructor ********************************************
	
	protected JpaContextNode(IJpaNode parent) {
		super(parent);
	}
	
	
	// **************** IJpaContextNode impl ***********************************
	
	@Override
	protected IJpaBaseContextFactory jpaFactory() {
		return (IJpaBaseContextFactory) super.jpaFactory();
	}
	
	//TODO casting to IJpaContextNode here(possible CCE).
	/**
	 * Overidden in BaseJpaContext, Persistence, PersitsenceXml to throw UnsupportedOperationException
	 * Overidden in PersistenceUnit to return it.
	 */
	public IPersistenceUnit persistenceUnit() {
		return ((IJpaContextNode) parent()).persistenceUnit();
	}
	
	/**
	 * Overidden in BaseJpaContext to return null
	 * Overidden in EntityMappings to return it.
	 */
	public EntityMappings entityMappings() {
		return ((IJpaContextNode) parent()).entityMappings();
	}
	
	/**
	 * Overidden in BaseJpaContext to return null
	 * Overidden in XmlPersistentType to return it.
	 */
	public XmlPersistentType xmlPersistentType() {
		return ((IJpaContextNode) parent()).xmlPersistentType();
	}
	
	public ITextRange selectionTextRange() {
		return new SimpleTextRange(0, 0, 0);
	}
	
	public IJpaContextNode contextNode(int offset) {
		return null;
	}
}
