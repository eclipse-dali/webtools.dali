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

import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;

public class PersistenceXml extends JpaContextNode
	implements IPersistenceXml
{
	protected IPersistence persistence;
	
	
	public PersistenceXml(IBaseJpaContent baseJpaContent) {
		super(baseJpaContent);
	}
	
	
	// **************** persistence *******************************************
	
	public IPersistence getPersistence() {
		return persistence;
	}
	
	public void setPersistence(IPersistence newPersistence) {
		IPersistence oldPersistence = persistence;
		persistence = newPersistence;
		firePropertyChanged(PERSISTENCE_PROPERTY, oldPersistence, newPersistence);
	}
	
	
	// **************** updating **********************************************
	
	public void update(PersistenceResourceModel persistenceResource) {
		if (persistenceResource.getPersistence() != null) {
			if (persistence == null) {
				setPersistence(jpaFactory().createPersistence(this));
			}
			persistence.update(persistenceResource.getPersistence());
		}
		else {
			setPersistence(null);
		}
	}
}
