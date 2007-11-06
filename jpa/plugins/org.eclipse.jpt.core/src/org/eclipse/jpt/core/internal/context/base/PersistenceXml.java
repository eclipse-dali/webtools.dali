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

import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;

public class PersistenceXml extends JpaContextNode
	implements IPersistenceXml
{
	protected IPersistence persistence;
	
	
	public PersistenceXml(IBaseJpaContent baseJpaContent) {
		super(baseJpaContent);
	}
	
	public void initializeFromResource(PersistenceResourceModel persistenceResource) {
		if (persistenceResource.getPersistence() != null) {
			this.persistence = createPersistence(persistenceResource.getPersistence());
		}
	}

	@Override
	public IPersistenceUnit persistenceUnit() {
		throw new UnsupportedOperationException("No PersistenceUnit in this context");
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
			if (this.persistence != null) {
				this.persistence.update(persistenceResource.getPersistence());
			}
			else {
				setPersistence(createPersistence(persistenceResource.getPersistence()));
			}
		}
		else {
			setPersistence(null);
		}
	}
	
	protected IPersistence createPersistence(XmlPersistence xmlPersistence) {
		IPersistence persistence = jpaFactory().createPersistence(this);
		persistence.initializeFromResource(xmlPersistence);
		return persistence;
	}
	
	public ITextRange validationTextRange() {
		return ITextRange.Empty.instance();
	}
}
