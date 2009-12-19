/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.core.resource.xml.EmfTools;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * <code>orm.xml</code> file
 * <br>
 * <code>persistence-unit-metadata</code> element
 */
public class GenericPersistenceUnitMetadata
	extends AbstractOrmXmlContextNode
	implements PersistenceUnitMetadata
{
	protected boolean xmlMappingMetadataComplete;

	protected final OrmPersistenceUnitDefaults persistenceUnitDefaults;


	public GenericPersistenceUnitMetadata(EntityMappings parent) {
		super(parent);
		this.xmlMappingMetadataComplete = this.getResourceXmlMappingMetadataComplete();
		this.persistenceUnitDefaults = this.getXmlContextNodeFactory().buildPersistenceUnitDefaults(this);
	}

	@Override
	public EntityMappings getParent() {
		return (EntityMappings) super.getParent();
	}

	public XmlEntityMappings getXmlEntityMappings() {
		return this.getParent().getXmlEntityMappings();
	}


	// ********** persistence unit metadata **********

	public XmlPersistenceUnitMetadata buildXmlPersistenceUnitMetadata() {
		return EmfTools.create(
				this.getResourceNodeFactory(),
				OrmPackage.eINSTANCE.getXmlPersistenceUnitMetadata(),
				XmlPersistenceUnitMetadata.class
			);
	}

	public XmlPersistenceUnitMetadata getXmlPersistenceUnitMetadata() {
		return this.getXmlEntityMappings().getPersistenceUnitMetadata();
	}


	// ********** XML mapping metadata complete **********

	public boolean isXmlMappingMetadataComplete() {
		return this.xmlMappingMetadataComplete;
	}

	public void setXmlMappingMetadataComplete(boolean xmlMappingMetadataComplete) {
		boolean old = this.xmlMappingMetadataComplete;
		this.xmlMappingMetadataComplete = xmlMappingMetadataComplete;
		if (old != xmlMappingMetadataComplete) {
			XmlPersistenceUnitMetadata xmlMetadata = this.getXmlPersistenceUnitMetadata();
			if (xmlMetadata != null) {
				xmlMetadata.setXmlMappingMetadataComplete(xmlMappingMetadataComplete);
				if (xmlMetadata.isUnset()) {
					this.getXmlEntityMappings().setPersistenceUnitMetadata(null);
				}
			}
			else if (xmlMappingMetadataComplete) {
				xmlMetadata = this.buildXmlPersistenceUnitMetadata();
				this.getXmlEntityMappings().setPersistenceUnitMetadata(xmlMetadata);
				xmlMetadata.setXmlMappingMetadataComplete(xmlMappingMetadataComplete);
			}
			this.firePropertyChanged(XML_MAPPING_METADATA_COMPLETE_PROPERTY, old, xmlMappingMetadataComplete);
		}
	}

	protected void setXmlMappingMetadataComplete_(boolean xmlMappingMetadataComplete) {
		boolean old = this.xmlMappingMetadataComplete;
		this.xmlMappingMetadataComplete = xmlMappingMetadataComplete;
		this.firePropertyChanged(XML_MAPPING_METADATA_COMPLETE_PROPERTY, old, xmlMappingMetadataComplete);
	}

	protected boolean getResourceXmlMappingMetadataComplete() {
		XmlPersistenceUnitMetadata xmlMetadata = this.getXmlPersistenceUnitMetadata();
		return (xmlMetadata != null) ? xmlMetadata.isXmlMappingMetadataComplete() : false;
	}


	// ********** persistence unit defaults **********

	public OrmPersistenceUnitDefaults getPersistenceUnitDefaults() {
		return this.persistenceUnitDefaults;
	}


	// ********** miscellaneous **********

	public void update() {
		this.setXmlMappingMetadataComplete_(this.getResourceXmlMappingMetadataComplete());
		this.persistenceUnitDefaults.update();
	}

	public TextRange getValidationTextRange() {
		if (this.getXmlPersistenceUnitMetadata() != null) {
			return this.getXmlPersistenceUnitMetadata().getValidationTextRange();
		}
		return this.getXmlEntityMappings().getValidationTextRange();
	}

}
