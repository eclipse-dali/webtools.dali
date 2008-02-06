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

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class PersistenceXml extends JpaContextNode
	implements IPersistenceXml
{
	protected PersistenceResource persistenceResource;
	
	protected IPersistence persistence;
	
	
	public PersistenceXml(IBaseJpaContent baseJpaContent) {
		super(baseJpaContent);
	}
	
	@Override
	public IResource resource() {
		return persistenceResource.getFile();
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
	private boolean okToContinueValidation = true;

	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		addInvalidPersistenceXmlContentMessage(messages);
		
		if (okToContinueValidation){
			getPersistence().addToMessages(messages, astRoot);
		}
	}
	
	protected void addInvalidPersistenceXmlContentMessage(List<IMessage> messages) {

		if (this.persistence == null) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_XML_INVALID_CONTENT,
						this)
				);
			okToContinueValidation = false;
		}
	}
	
}
