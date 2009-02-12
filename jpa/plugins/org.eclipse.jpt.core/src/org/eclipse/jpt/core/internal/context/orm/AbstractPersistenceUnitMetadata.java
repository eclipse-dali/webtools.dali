/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.core.utility.TextRange;

public abstract class AbstractPersistenceUnitMetadata extends AbstractXmlContextNode
	implements PersistenceUnitMetadata
{
	protected boolean xmlMappingMetadataComplete;

	protected final OrmPersistenceUnitDefaults persistenceUnitDefaults;

	protected final XmlEntityMappings xmlEntityMappings;

	protected AbstractPersistenceUnitMetadata(EntityMappings parent, XmlEntityMappings resource) {
		super(parent);
		this.xmlEntityMappings = resource;
		this.xmlMappingMetadataComplete = this.getResourceXmlMappingMetadataComplete();
		this.persistenceUnitDefaults = getJpaFactory().buildPersistenceUnitDefaults(this, this.xmlEntityMappings);
	}

	public boolean isXmlMappingMetadataComplete() {
		return this.xmlMappingMetadataComplete;
	}

	public void setXmlMappingMetadataComplete(boolean newXmlMappingMetadataComplete) {
		boolean oldXmlMappingMetadataComplete = this.xmlMappingMetadataComplete;
		this.xmlMappingMetadataComplete = newXmlMappingMetadataComplete;
		if (oldXmlMappingMetadataComplete != newXmlMappingMetadataComplete) {
			if (this.getResourcePersistenceUnitMetadata() != null) {
				this.getResourcePersistenceUnitMetadata().setXmlMappingMetadataComplete(newXmlMappingMetadataComplete);						
				if (this.getResourcePersistenceUnitMetadata().isUnset()) {
					this.xmlEntityMappings.setPersistenceUnitMetadata(null);
				}
			}
			else if (newXmlMappingMetadataComplete) {
				this.xmlEntityMappings.setPersistenceUnitMetadata(createResourcePersistenceUnitMetadata());
				this.getResourcePersistenceUnitMetadata().setXmlMappingMetadataComplete(newXmlMappingMetadataComplete);		
			}
		}
		firePropertyChanged(PersistenceUnitMetadata.XML_MAPPING_METADATA_COMPLETE_PROPERTY, oldXmlMappingMetadataComplete, newXmlMappingMetadataComplete);
	}
	
	protected void setXmlMappingMetadataComplete_(boolean newXmlMappingMetadataComplete) {
		boolean oldXmlMappingMetadataComplete = this.xmlMappingMetadataComplete;
		this.xmlMappingMetadataComplete = newXmlMappingMetadataComplete;
		firePropertyChanged(PersistenceUnitMetadata.XML_MAPPING_METADATA_COMPLETE_PROPERTY, oldXmlMappingMetadataComplete, newXmlMappingMetadataComplete);
	}

	public OrmPersistenceUnitDefaults getPersistenceUnitDefaults() {
		return this.persistenceUnitDefaults;
	}
	
	public void update() {
		this.setXmlMappingMetadataComplete_(this.getResourceXmlMappingMetadataComplete());
		this.persistenceUnitDefaults.update();
	}
	
	
	protected boolean getResourceXmlMappingMetadataComplete() {
		XmlPersistenceUnitMetadata resourcePersistenceUnitMetadata = getResourcePersistenceUnitMetadata();
		return resourcePersistenceUnitMetadata != null ? resourcePersistenceUnitMetadata.isXmlMappingMetadataComplete() : false;
	}
	
	public XmlPersistenceUnitMetadata getResourcePersistenceUnitMetadata() {
		return this.xmlEntityMappings.getPersistenceUnitMetadata();
	}
		
	public TextRange getValidationTextRange() {
		if (getResourcePersistenceUnitMetadata() != null) {
			return getResourcePersistenceUnitMetadata().getValidationTextRange();
		}
		return this.xmlEntityMappings.getValidationTextRange();
	}
}
