/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.persistence;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.persistence.Persistence2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceXml2_0;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericPersistenceXml
	extends AbstractPersistenceXmlContextNode
	implements PersistenceXml2_0
{
	protected JpaXmlResource persistenceXmlResource;
	
	protected Persistence persistence;
	
	protected JpaResourceType resourceType;
	
	public GenericPersistenceXml(JpaRootContextNode parent, JpaXmlResource resource) {
		super(parent);
		if (! resource.getContentType().isKindOf(JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE)) {
			throw new IllegalArgumentException("Resource " + resource + " must have persistence xml content type"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		this.persistenceXmlResource = resource;
		if (resource.getRootObject() != null) {
			this.persistence = buildPersistence((XmlPersistence) resource.getRootObject());
			this.resourceType = resource.getResourceType();
		}
	}
	
	public JpaXmlResource getXmlResource() {
		return this.persistenceXmlResource;
	}
	
	// **************** JpaNode impl *******************************************
	
	@Override
	public IResource getResource() {
		return this.persistenceXmlResource.getFile();
	}
	
	
	// **************** JpaStructureNode impl **********************************
	
	public String getId() {
		// isn't actually displayed, so needs no details page
		return null;
	}
	
	@Override
	public JpaResourceType getResourceType() {
		return this.persistenceXmlResource.getResourceType();
	}
	
	
	// **************** persistence ********************************************
	
	public Persistence getPersistence() {
		return this.persistence;
	}
	
	protected void setPersistence(Persistence newPersistence) {
		Persistence oldPersistence = this.persistence;
		this.persistence = newPersistence;
		firePropertyChanged(PERSISTENCE_PROPERTY, oldPersistence, newPersistence);
	}
	
	
	// **************** metamodel ********************

	public void initializeMetamodel() {
		if (this.persistence != null) {
			((Persistence2_0) this.persistence).initializeMetamodel();
		}
	}

	public void synchronizeMetamodel() {
		if (this.persistence != null) {
			((Persistence2_0) this.persistence).synchronizeMetamodel();
		}
	}

	public void disposeMetamodel() {
		if (this.persistence != null) {
			((Persistence2_0) this.persistence).disposeMetamodel();
		}
	}


	// **************** updating ***********************************************

	//TODO I haven't yet figured out if we do not need the resource object passed in now.
	//I don't think we will even build AbstractPersistenceXml if the resource object is null
	//I'm pretty sure this won't change now, but need to investigate - KFB
	public void update(JpaXmlResource persistenceResource) {
		XmlPersistence oldXmlPersistence = 
			(this.persistence == null) ? null : this.persistence.getXmlPersistence();
		XmlPersistence newXmlPersistence = (XmlPersistence) persistenceResource.getRootObject();
		JpaResourceType newResourceType = persistenceResource.getResourceType();
		
		this.persistenceXmlResource = persistenceResource;
		
		// if the old and new xml persistences are different instances,
		// we scrap the old and rebuild.  this can happen when the resource
		// model drastically changes, such as a cvs checkout or an edit reversion
		if (oldXmlPersistence != newXmlPersistence || newXmlPersistence == null || valuesAreDifferent(this.resourceType, newResourceType)) {
			if (this.persistence != null) {
				this.getJpaFile(this.persistenceXmlResource.getFile()).removeRootStructureNode(this.persistenceXmlResource);
				this.persistence.dispose();
				this.setPersistence(null);
			}
		}
		
		if (newXmlPersistence != null) {
			if (this.persistence != null) {
				this.persistence.update(newXmlPersistence);
			}
			else {
				setPersistence(buildPersistence(newXmlPersistence));
			}
			this.getJpaFile(this.persistenceXmlResource.getFile()).addRootStructureNode(this.persistenceXmlResource, this.persistence);
		}
		this.resourceType = newResourceType;
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		if (this.persistence != null) {
			this.persistence.postUpdate();
		}
	}
	
	protected Persistence buildPersistence(XmlPersistence xmlPersistence) {
		return getContextNodeFactory().buildPersistence(this, xmlPersistence);
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
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

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

		this.persistence.validate(messages, reporter);
	}

	public void dispose() {
		this.persistence.dispose();
	}
}
