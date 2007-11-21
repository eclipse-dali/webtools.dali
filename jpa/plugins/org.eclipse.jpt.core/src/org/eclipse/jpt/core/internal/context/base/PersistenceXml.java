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
	
	protected void setPersistence(IPersistence newPersistence) {
		IPersistence oldPersistence = persistence;
		persistence = newPersistence;
		firePropertyChanged(PERSISTENCE_PROPERTY, oldPersistence, newPersistence);
	}
	
	public IPersistence addPersistence() {
		if (persistence != null) {
			throw new IllegalStateException();
		}
		
		XmlPersistence xmlPersistence = PersistenceFactory.eINSTANCE.createXmlPersistence();
		persistenceResource.getContents().add(xmlPersistence);
		setPersistence(createPersistence(xmlPersistence));
		return getPersistence();
	}
	
	public void removePersistence() {
		if (persistence == null) {
			throw new IllegalStateException();
		}
		
		XmlPersistence xmlPersistence = persistenceResource.getPersistence();
		persistenceResource.getContents().remove(xmlPersistence);
		
		setPersistence(null);
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
		}
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
	
	
	// *************************************************************************
	
	@Override
	public IPersistenceUnit persistenceUnit() {
		throw new UnsupportedOperationException("No PersistenceUnit in this context");
	}
	
	public ITextRange validationTextRange() {
		return ITextRange.Empty.instance();
	}
}
