/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmXml
	extends AbstractOrmXmlContextNode
	implements OrmXml
{
	protected JpaXmlResource xmlResource;
	
	protected EntityMappings entityMappings;

	protected JpaResourceType resourceType;	
	
	public GenericOrmXml(MappingFileRef parent, JpaXmlResource resource) {
		super(parent);
		if (!resource.getContentType().isKindOf(JptCorePlugin.MAPPING_FILE_CONTENT_TYPE)) {
			throw new IllegalArgumentException(resource + " does not have mapping file content type"); //$NON-NLS-1$
		}
		this.xmlResource = resource;
		XmlEntityMappings xmlEntityMappings = (XmlEntityMappings) this.xmlResource.getRootObject();
		if (xmlEntityMappings != null) {
			this.entityMappings = this.buildEntityMappings(xmlEntityMappings);
		}
		if (resource.getRootObject() != null) {
			this.entityMappings = buildEntityMappings((XmlEntityMappings) resource.getRootObject());
			this.resourceType = resource.getResourceType();
		}
	}

	
	// ********** overrides **********
	
	@Override
	public MappingFileRef getParent() {
		return (MappingFileRef) super.getParent();
	}
	
	@Override
	public IResource getResource() {
		return this.xmlResource.getFile();
	}
	
	@Override
	public MappingFileRoot getMappingFileRoot() {
		return getEntityMappings();
	}
	
	
	// ********** JpaStructureNode implementation **********
	
	public String getId() {
		// isn't actually displayed, so needs no details page
		return null;
	}

	@Override
	public JpaResourceType getResourceType() {
		return this.xmlResource.getResourceType();
	}
	
	
	// ********** MappingFile implementation **********
	
	public JpaXmlResource getXmlResource() {
		return this.xmlResource;
	}
	
	public MappingFileRoot getRoot() {
		return getEntityMappings();
	}
	
	public OrmPersistentType getPersistentType(String fullyQualifiedTypeName) {
		return (this.entityMappings == null) ? null : this.entityMappings.getPersistentType(fullyQualifiedTypeName);
	}

	
	// ********** PersistentTypeContainer implementation **********
	
	/**
	 * All orm.xml mapping files must be able to generate a static metamodel
	 * because 1.0 orm.xml files can be referenced from 2.0 persistence.xml files.
	 */
	public Iterable<? extends PersistentType> getPersistentTypes() {
		return (this.entityMappings != null) ? this.entityMappings.getPersistentTypes() : EmptyIterable.<JavaPersistentType>instance();
	}

	
	// ********** entity mappings **********
	
	public EntityMappings getEntityMappings() {
		return this.entityMappings;
	}
	
	protected void setEntityMappings(EntityMappings entityMappings) {
		EntityMappings old = this.entityMappings;
		this.entityMappings = entityMappings;
		this.firePropertyChanged(ENTITY_MAPPINGS_PROPERTY, old, entityMappings);
	}
	
	
	// ********** updating **********
	
	public void update(JpaXmlResource resource) {		
		if (!resource.getContentType().isKindOf(JptCorePlugin.MAPPING_FILE_CONTENT_TYPE)) {
			throw new IllegalArgumentException(resource + " does not have mapping file content type"); //$NON-NLS-1$
		}
		this.xmlResource = resource;
		this.update();
	}
	
	protected void update() {		
		XmlEntityMappings oldXmlEntityMappings = 
			(this.entityMappings == null) ? null : this.entityMappings.getXmlEntityMappings();
		XmlEntityMappings newXmlEntityMappings = (XmlEntityMappings) this.xmlResource.getRootObject();
		JpaResourceType newResourceType = xmlResource.getResourceType();

		// if the old and new xml entity mappings are different instances,
		// we scrap the old and rebuild.  this can happen when the resource
		// model drastically changes, such as a cvs checkout or an edit reversion
		if (oldXmlEntityMappings != newXmlEntityMappings || newXmlEntityMappings == null || valuesAreDifferent(this.resourceType, newResourceType)) {
			if (this.entityMappings != null) {
				this.getJpaFile(this.xmlResource.getFile()).removeRootStructureNode(this.xmlResource);
				this.entityMappings.dispose();
				this.setEntityMappings(null);
			}
		}
		
		if (newXmlEntityMappings != null) {
			if (this.entityMappings != null) {
				this.entityMappings.update();
			} else {
				this.setEntityMappings(this.buildEntityMappings(newXmlEntityMappings));
			}
			this.getJpaFile(this.xmlResource.getFile()).addRootStructureNode(this.xmlResource, this.entityMappings);
		}
		this.resourceType = newResourceType;
	}
	
	protected EntityMappings buildEntityMappings(XmlEntityMappings xmlEntityMappings) {
		return getXmlContextNodeFactory().buildEntityMappings(this, xmlEntityMappings);
	}	
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		if (this.entityMappings != null) {
			this.entityMappings.postUpdate();
		}
	}
	
	
	// ********** text **********
	
	public JpaStructureNode getStructureNode(int textOffset) {
		if (this.entityMappings.containsOffset(textOffset)) {
			return this.entityMappings.getStructureNode(textOffset);
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
	
	
	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.entityMappings != null) {
			this.entityMappings.validate(messages, reporter);
		}
	}
	
	
	// ********** dispose **********
	
	public void dispose() {
		if (this.entityMappings != null) {
			this.entityMappings.dispose();
		}
	}

}
