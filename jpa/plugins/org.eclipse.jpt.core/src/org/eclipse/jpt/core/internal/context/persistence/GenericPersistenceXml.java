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
package org.eclipse.jpt.core.internal.context.persistence;

import java.util.List;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public class GenericPersistenceXml
	extends AbstractXmlContextNode
	implements PersistenceXml
{
	protected PersistenceResource persistenceResource;
	
	protected Persistence persistence;
	
	
	public GenericPersistenceXml(JpaRootContextNode parent, PersistenceResource persistenceResource) {
		super(parent);
		this.initialize(persistenceResource);
	}
	
	
	// **************** JpaNode impl *******************************************
	
	@Override
	public JpaRootContextNode getParent() {
		return (JpaRootContextNode) super.getParent();
	}
	
	@Override
	public IResource getResource() {
		return this.persistenceResource.getFile();
	}
	
	
	// **************** XmlContextNode impl ************************************
	
	@Override
	public JpaXmlResource getEResource() {
		return this.persistenceResource;
	}
	
	
	// **************** JpaStructureNode impl **********************************
	
	public String getId() {
		// isn't actually displayed, so needs no details page
		return null;
	}
	
	
	// **************** persistence ********************************************
	
	public Persistence getPersistence() {
		return this.persistence;
	}
	
	public Persistence addPersistence() {
		if (this.persistence != null) {
			throw new IllegalStateException();
		}
		
		XmlPersistence xmlPersistence = PersistenceFactory.eINSTANCE.createXmlPersistence();
		this.persistence = buildPersistence(xmlPersistence);
		this.persistenceResource.getContents().add(xmlPersistence);
		firePropertyChanged(PERSISTENCE_PROPERTY, null, this.persistence);
		return this.persistence;
	}
	
	public void removePersistence() {
		if (this.persistence == null) {
			throw new IllegalStateException();
		}
		getJpaFile(this.persistenceResource.getFile()).removeRootStructureNode(this.persistenceResource);
		this.persistence.dispose();
		Persistence oldPersistence = this.persistence;
		this.persistence = null;
		XmlPersistence xmlPersistence = this.persistenceResource.getPersistence();
		this.persistenceResource.getContents().remove(xmlPersistence);
		firePropertyChanged(PERSISTENCE_PROPERTY, oldPersistence, null);
	}
	
	protected void setPersistence_(Persistence newPersistence) {
		Persistence oldPersistence = this.persistence;
		this.persistence = newPersistence;
		firePropertyChanged(PERSISTENCE_PROPERTY, oldPersistence, newPersistence);
	}
	
	
	// **************** updating ***********************************************
	
	protected void initialize(PersistenceResource pr) {
		this.persistenceResource = pr;
		if (pr.getPersistence() != null) {
			this.persistence = buildPersistence(pr.getPersistence());
		}
	}

	public void update(PersistenceResource pr) {
		this.persistenceResource = pr;
		if (pr.getPersistence() != null) {
			if (this.persistence != null) {
				this.getJpaFile(this.persistenceResource.getFile()).addRootStructureNode(this.persistenceResource, this.persistence);
				this.persistence.update(pr.getPersistence());
			}
			else {
				setPersistence_(buildPersistence(pr.getPersistence()));
			}
		}
		else {
			if (this.persistence != null) {
				this.getJpaFile(this.persistenceResource.getFile()).removeRootStructureNode(this.persistenceResource);
				this.persistence.dispose();
			}
			setPersistence_(null);
		}
	}
	
	protected Persistence buildPersistence(XmlPersistence xmlPersistence) {
		return getJpaFactory().buildPersistence(this, xmlPersistence);
	}
	
	
	// *************************************************************************
	
	@Override
	public PersistenceUnit getPersistenceUnit() {
		throw new UnsupportedOperationException("No Persistence Unit in this context"); //$NON-NLS-1$
	}
	
	
	public JpaStructureNode getStructureNode(int textOffset) {
		if (this.persistence.containsOffset(textOffset)) {
			return this.persistence.getStructureNode(textOffset);
		}
		return this;
	}
	
	// never actually selected
	public TextRange getSelectionTextRange() {
		return TextRange.Empty.instance();
	}
	
	public TextRange getValidationTextRange() {
		return TextRange.Empty.instance();
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);

		if (this.persistence == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_XML_INVALID_CONTENT,
					this
				)
			);
			return;
		}

		this.persistence.validate(messages);
	}

	public void dispose() {
		this.persistence.dispose();
	}
}
