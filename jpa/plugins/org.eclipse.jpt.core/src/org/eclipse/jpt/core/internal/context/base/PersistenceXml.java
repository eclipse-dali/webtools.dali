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
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;

public class PersistenceXml extends JpaContextNode
	implements IPersistenceXml
{
	protected PersistenceResource persistenceResource;
	
	protected IPersistence persistence;
	
	
	public PersistenceXml(IBaseJpaContent baseJpaContent) {
		super(baseJpaContent);
	}
	
	
	// **************** persistence ********************************************
	
	public IPersistence getPersistence() {
		return persistence;
	}
	
	public IPersistence addPersistence() {
		if (persistence != null) {
			throw new IllegalStateException();
		}
		
		XmlPersistence xmlPersistence = PersistenceFactory.eINSTANCE.createXmlPersistence();
		persistence = createPersistence(xmlPersistence);
		persistenceResource.getContents().add(xmlPersistence);
		firePropertyChanged(PERSISTENCE_PROPERTY, null, persistence);
		return persistence;
	}
	
	public void removePersistence() {
		if (persistence == null) {
			throw new IllegalStateException();
		}
		
		IPersistence oldPersistence = persistence;
		persistence = null;
		XmlPersistence xmlPersistence = persistenceResource.getPersistence();
		persistenceResource.getContents().remove(xmlPersistence);
		firePropertyChanged(PERSISTENCE_PROPERTY, oldPersistence, null);
	}
	
	protected void setPersistence_(IPersistence newPersistence) {
		IPersistence oldPersistence = persistence;
		persistence = newPersistence;
		firePropertyChanged(PERSISTENCE_PROPERTY, oldPersistence, newPersistence);
	}
	
	
	// **************** updating ***********************************************
	
	public void initialize(PersistenceResource persistenceResource) {
		this.persistenceResource = persistenceResource;
		if (persistenceResource.getPersistence() != null) {
			this.persistence = createPersistence(persistenceResource.getPersistence());
		}
	}

	public void update(PersistenceResource persistenceResource) {
		if (! persistenceResource.equals(this.persistenceResource)) {
			this.persistenceResource = persistenceResource;
			this.persistenceResource.resourceModel().removeRootContextNode(this);
		}
		if (persistenceResource.getPersistence() != null) {
			if (this.persistence != null) {
				this.persistence.update(persistenceResource.getPersistence());
			}
			else {
				setPersistence_(createPersistence(persistenceResource.getPersistence()));
			}
			persistenceResource.resourceModel().addRootContextNode(getPersistence());
		}
		else {
			setPersistence_(null);
		}
	}
	
	protected IPersistence createPersistence(XmlPersistence xmlPersistence) {
		IPersistence persistence = jpaFactory().createPersistence(this);
		persistence.initialize(xmlPersistence);
		return persistence;
	}
	
	
	// *************************************************************************
	
	@Override
	public IPersistenceUnit persistenceUnit() {
		throw new UnsupportedOperationException("No PersistenceUnit in this context");
	}
	
	public ITextRange validationTextRange() {
		return ITextRange.Empty.instance();
	}
}
