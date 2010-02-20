/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.persistence.Persistence;
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

/**
 * context model persistence.xml
 */
public class GenericPersistenceXml
	extends AbstractPersistenceXmlContextNode
	implements PersistenceXml2_0
{
	/**
	 * If the XML resource's content type changes a new instance of this object will be built 
	 */
	protected JpaXmlResource xmlResource;  // never null
	
	/**
	 * The resouce type will only change if the XML file's version changes
	 * (since, if the content type changes, we get garbage-collected).
	 */
	protected JpaResourceType resourceType;
	
	protected Persistence persistence;
	
	
	public GenericPersistenceXml(JpaRootContextNode parent, JpaXmlResource resource) {
		super(parent);
		if ( ! resource.getContentType().isKindOf(JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE)) {
			throw new IllegalArgumentException("Resource " + resource + " must have persistence xml content type"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		this.xmlResource = resource;
		if (resource.getRootObject() != null) {
			this.persistence = this.buildPersistence((XmlPersistence) resource.getRootObject());
			this.resourceType = resource.getResourceType();
		}
	}
	
	
	// ********** XmlFile implementation **********
	
	public JpaXmlResource getXmlResource() {
		return this.xmlResource;
	}
	
	
	// ********** AbstractJpaNode overrides **********
	
	@Override
	public IResource getResource() {
		return this.xmlResource.getFile();
	}
	
	
	// ********** AbstractJpaContextNode overrides **********
	
	@Override
	public JpaResourceType getResourceType() {
		return this.xmlResource.getResourceType();
	}
	
	
	// ********** persistence **********
	
	public Persistence getPersistence() {
		return this.persistence;
	}
	
	protected void setPersistence(Persistence persistence) {
		Persistence old = this.persistence;
		this.persistence = persistence;
		this.firePropertyChanged(PERSISTENCE_PROPERTY, old, persistence);
	}
	
	protected Persistence buildPersistence(XmlPersistence xmlPersistence) {
		return this.getContextNodeFactory().buildPersistence(this, xmlPersistence);
	}
	
	
	// ********** metamodel **********
	
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
	
	
	// ********** updating **********
	
	public void update() {
		XmlPersistence oldXmlPersistence = (this.persistence == null) ? null : this.persistence.getXmlPersistence();
		XmlPersistence newXmlPersistence = (XmlPersistence) this.xmlResource.getRootObject();
		JpaResourceType newResourceType = this.xmlResource.getResourceType();
		
		// if the old and new xml persistences are different instances,
		// we scrap the old and rebuild.  this can happen when the resource
		// model drastically changes, such as a cvs checkout or an edit reversion
		if ((oldXmlPersistence != newXmlPersistence) 
				|| (newXmlPersistence == null) 
				|| this.valuesAreDifferent(this.resourceType, newResourceType)) {
			
			if (this.persistence != null) {
				getJpaFile().removeRootStructureNode(this.xmlResource);
				this.persistence.dispose();
				setPersistence(null);
			}
		}
		
		this.resourceType = newResourceType;
		
		if (newXmlPersistence != null) {
			if (this.persistence != null) {
				this.persistence.update(newXmlPersistence);
			}
			else {
				setPersistence(buildPersistence(newXmlPersistence));
			}
			
			JpaFile jpaFile = getJpaFile();
			if (jpaFile == null) {
				System.out.print("Jpa file is null");
			}
			jpaFile.addRootStructureNode(this.xmlResource, this.persistence);
		}
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		if (this.persistence != null) {
			this.persistence.postUpdate();
		}
	}
	
	
	// ********** JpaStructureNode implementation **********
	
	public String getId() {
		// isn't actually displayed, so needs no details page
		return null;
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
	
	public void dispose() {
		if (this.persistence != null) {
			this.persistence.dispose();
		}
		JpaFile jpaFile = getJpaFile();
		if (jpaFile != null) {
			jpaFile.removeRootStructureNode(this.xmlResource);
		}
	}
	
	protected JpaFile getJpaFile() {
		return this.getJpaFile(this.xmlResource.getFile());
	}
	
	
	// ********** validation **********
	
	// never actually selected
	public TextRange getValidationTextRange() {
		return TextRange.Empty.instance();
	}
	
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
}
