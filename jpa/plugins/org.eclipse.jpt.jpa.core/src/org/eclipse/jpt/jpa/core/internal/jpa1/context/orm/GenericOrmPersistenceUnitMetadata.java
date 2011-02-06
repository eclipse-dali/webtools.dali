/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmPersistenceUnitMetadata2_0;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.resource.xml.EmfTools;

/**
 * <code>orm.xml</code> file
 * <br>
 * <code>persistence-unit-metadata</code> element
 */
public class GenericOrmPersistenceUnitMetadata
	extends AbstractOrmXmlContextNode
	implements OrmPersistenceUnitMetadata2_0
{
	protected boolean xmlMappingMetadataComplete;

	protected String description;

	// never null
	protected final OrmPersistenceUnitDefaults persistenceUnitDefaults;


	public GenericOrmPersistenceUnitMetadata(EntityMappings parent) {
		super(parent);
		this.xmlMappingMetadataComplete = this.buildXmlMappingMetadataComplete();
		this.description = this.buildDescription();
		this.persistenceUnitDefaults = this.buildPersistenceUnitDefaults();
	}

	protected OrmPersistenceUnitDefaults buildPersistenceUnitDefaults() {
		return this.getContextNodeFactory().buildOrmPersistenceUnitDefaults(this);
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setXmlMappingMetadataComplete_(this.buildXmlMappingMetadataComplete());
		this.setDescription_(this.buildDescription());
		this.persistenceUnitDefaults.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.persistenceUnitDefaults.update();
	}


	// ********** XML metadata **********

	/**
	 * Return <code>null</code> if the XML metadata does not exist.
	 */
	public XmlPersistenceUnitMetadata getXmlPersistenceUnitMetadata() {
		return this.getXmlEntityMappings().getPersistenceUnitMetadata();
	}

	/**
	 * Build the XML metadata if it does not exist.
	 */
	public XmlPersistenceUnitMetadata getXmlPersistenceUnitMetadataForUpdate() {
		XmlPersistenceUnitMetadata xmlMetadata = this.getXmlPersistenceUnitMetadata();
		return (xmlMetadata != null) ? xmlMetadata : this.buildXmlPersistenceUnitMetadata();
	}

	protected XmlPersistenceUnitMetadata buildXmlPersistenceUnitMetadata() {
		XmlPersistenceUnitMetadata xmlMetadata = this.buildXmlPersistenceUnitMetadata_();
		this.getXmlEntityMappings().setPersistenceUnitMetadata(xmlMetadata);
		return xmlMetadata;
	}

	protected XmlPersistenceUnitMetadata buildXmlPersistenceUnitMetadata_() {
		return EmfTools.create(
				this.getResourceNodeFactory(),
				OrmPackage.eINSTANCE.getXmlPersistenceUnitMetadata(),
				XmlPersistenceUnitMetadata.class
			);
	}

	public void removeXmlPersistenceUnitMetadataIfUnset() {
		if (this.getXmlPersistenceUnitMetadata().isUnset()) {
			this.getXmlEntityMappings().setPersistenceUnitMetadata(null);
		}
	}


	// ********** XML mapping metadata complete **********

	public boolean isXmlMappingMetadataComplete() {
		return this.xmlMappingMetadataComplete;
	}

	public void setXmlMappingMetadataComplete(boolean xmlMappingMetadataComplete) {
		if (this.xmlMappingMetadataComplete != xmlMappingMetadataComplete) {
			XmlPersistenceUnitMetadata xmlPersistenceUnitMetadata = this.getXmlPersistenceUnitMetadataForUpdate();
			this.setXmlMappingMetadataComplete_(xmlMappingMetadataComplete);
			xmlPersistenceUnitMetadata.setXmlMappingMetadataComplete(xmlMappingMetadataComplete);
			this.removeXmlPersistenceUnitMetadataIfUnset();
		}
	}

	protected void setXmlMappingMetadataComplete_(boolean xmlMappingMetadataComplete) {
		boolean old = this.xmlMappingMetadataComplete;
		this.xmlMappingMetadataComplete = xmlMappingMetadataComplete;
		this.firePropertyChanged(XML_MAPPING_METADATA_COMPLETE_PROPERTY, old, xmlMappingMetadataComplete);
	}

	protected boolean buildXmlMappingMetadataComplete() {
		XmlPersistenceUnitMetadata xmlMetadata = this.getXmlPersistenceUnitMetadata();
		return (xmlMetadata != null) ? xmlMetadata.isXmlMappingMetadataComplete() : false;
	}


	// ********** description **********

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		if (this.valuesAreDifferent(this.description, description)) {
			XmlPersistenceUnitMetadata xmlPersistenceUnitMetadata = this.getXmlPersistenceUnitMetadataForUpdate();
			this.setDescription_(description);
			xmlPersistenceUnitMetadata.setDescription(description);
			this.removeXmlPersistenceUnitMetadataIfUnset();
		}
	}

	protected void setDescription_(String description) {
		String old = this.description;
		this.description = description;
		this.firePropertyChanged(DESCRIPTION_PROPERTY, old, description);
	}

	protected String buildDescription() {
		XmlPersistenceUnitMetadata xmlMetadata = this.getXmlPersistenceUnitMetadata();
		return (xmlMetadata == null) ? null : xmlMetadata.getDescription();
	}


	// ********** persistence unit defaults **********

	public OrmPersistenceUnitDefaults getPersistenceUnitDefaults() {
		return this.persistenceUnitDefaults;
	}


	// ********** misc **********

	@Override
	public EntityMappings getParent() {
		return (EntityMappings) super.getParent();
	}

	public XmlEntityMappings getXmlEntityMappings() {
		return this.getParent().getXmlEntityMappings();
	}

	public boolean resourceExists() {
		return this.getXmlPersistenceUnitMetadata() != null;
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		XmlPersistenceUnitMetadata xmlMetadata = this.getXmlPersistenceUnitMetadata();
		return (xmlMetadata != null) ?
				xmlMetadata.getValidationTextRange() :
				this.getXmlEntityMappings().getValidationTextRange();
	}
}
