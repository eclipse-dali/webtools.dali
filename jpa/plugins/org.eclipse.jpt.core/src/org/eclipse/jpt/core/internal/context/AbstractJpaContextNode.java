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
package org.eclipse.jpt.core.internal.context;

import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.AbstractJpaNode;

public abstract class AbstractJpaContextNode extends AbstractJpaNode
	implements JpaContextNode
{
	// **************** constructor ********************************************
	
	protected AbstractJpaContextNode(JpaNode parent) {
		super(parent);
	}
	
	
	// **************** JpaNode impl *******************************************
	
	@Override
	public JpaContextNode parent() {
		return (JpaContextNode) super.parent();
	}
	
	
	// **************** JpaContextNode impl ************************************
	
	/**
	 * Overidden in BaseJpaContext, Persistence, PersitsenceXml to throw UnsupportedOperationException
	 * Overidden in PersistenceUnit to return it.
	 */
	public PersistenceUnit persistenceUnit() {
		return parent().persistenceUnit();
	}
	
	/**
	 * Overidden in BaseJpaContext to return null
	 * Overidden in EntityMappings to return it.
	 */
	public EntityMappings entityMappings() {
		return parent().entityMappings();
	}
	
	/**
	 * Overidden in BaseJpaContext to return null
	 * Overidden in OrmPersistentType to return it.
	 */
	public OrmPersistentType ormPersistentType() {
		return parent().ormPersistentType();
	}
}
