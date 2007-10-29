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

import org.eclipse.jpt.core.internal.IJpaNodeModel;
import org.eclipse.jpt.core.internal.JpaNodeModel;
import org.eclipse.jpt.core.internal.platform.base.IJpaBaseContextFactory;

public abstract class JpaContextNode extends JpaNodeModel
	implements IJpaContextNode
{
	// **************** constructor *******************************************
	
	protected JpaContextNode(IJpaNodeModel parent) {
		super(parent);
	}
	
	
	// **************** IJpaContextNodeModel impl *****************************
	
	@Override
	protected IJpaBaseContextFactory jpaFactory() {
		return (IJpaBaseContextFactory) super.jpaFactory();
	}
	
	//TODO casting to IJpaContextNode here(possible CCE).
	//Overriding in BaseJpaContext, Persistence, PersitsenceXml throws UnsupportedOperationException
	//Overriding in PersistenceUnit to return it.
	public IPersistenceUnit persistenceUnit() {
		return ((IJpaContextNode) parent()).persistenceUnit();
	}
}
