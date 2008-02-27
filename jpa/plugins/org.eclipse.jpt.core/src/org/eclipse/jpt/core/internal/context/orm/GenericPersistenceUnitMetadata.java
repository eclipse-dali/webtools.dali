/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata;

public class GenericPersistenceUnitMetadata extends AbstractOrmJpaContextNode
	implements PersistenceUnitMetadata
{
	protected boolean xmlMappingMetadataComplete;

	protected final PersistenceUnitDefaults persistenceUnitDefaults;

	protected XmlEntityMappings entityMappings;

	public GenericPersistenceUnitMetadata(EntityMappings parent) {
		super(parent);
		this.persistenceUnitDefaults = jpaFactory().buildPersistenceUnitDefaults(this);
	}

	public boolean isXmlMappingMetadataComplete() {
		return this.xmlMappingMetadataComplete;
	}

	public void setXmlMappingMetadataComplete(boolean newXmlMappingMetadataComplete) {
		boolean oldXmlMappingMetadataComplete = this.xmlMappingMetadataComplete;
		this.xmlMappingMetadataComplete = newXmlMappingMetadataComplete;
		if (oldXmlMappingMetadataComplete != newXmlMappingMetadataComplete) {
			if (this.persistenceUnitMetadata() != null) {
				this.persistenceUnitMetadata().setXmlMappingMetadataComplete(newXmlMappingMetadataComplete);						
				if (this.persistenceUnitMetadata().isAllFeaturesUnset()) {
					this.entityMappings.setPersistenceUnitMetadata(null);
				}
			}
			else if (newXmlMappingMetadataComplete) {
				this.entityMappings.setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
				this.persistenceUnitMetadata().setXmlMappingMetadataComplete(newXmlMappingMetadataComplete);		
			}
		}
		firePropertyChanged(PersistenceUnitMetadata.XML_MAPPING_METADATA_COMPLETE_PROPERTY, oldXmlMappingMetadataComplete, newXmlMappingMetadataComplete);
	}
	
	protected void setXmlMappingMetadataComplete_(boolean newXmlMappingMetadataComplete) {
		boolean oldXmlMappingMetadataComplete = this.xmlMappingMetadataComplete;
		this.xmlMappingMetadataComplete = newXmlMappingMetadataComplete;
		firePropertyChanged(PersistenceUnitMetadata.XML_MAPPING_METADATA_COMPLETE_PROPERTY, oldXmlMappingMetadataComplete, newXmlMappingMetadataComplete);
	}

	public PersistenceUnitDefaults getPersistenceUnitDefaults() {
		return this.persistenceUnitDefaults;
	}
	
	public void initialize(XmlEntityMappings entityMappings) {
		this.entityMappings = entityMappings;
		if (this.persistenceUnitMetadata() != null) {
			this.xmlMappingMetadataComplete = this.persistenceUnitMetadata().isXmlMappingMetadataComplete();
		}
		this.persistenceUnitDefaults.initialize(entityMappings);
	}
	
	public void update(XmlEntityMappings entityMappings) {
		this.entityMappings = entityMappings;
		if (this.persistenceUnitMetadata() != null) {
			setXmlMappingMetadataComplete_(this.persistenceUnitMetadata().isXmlMappingMetadataComplete());
		}
		else {
			setXmlMappingMetadataComplete_(false);
		}
		this.persistenceUnitDefaults.update(entityMappings);
	}
	
	protected XmlPersistenceUnitMetadata persistenceUnitMetadata() {
		return this.entityMappings.getPersistenceUnitMetadata();
	}
	
	public TextRange validationTextRange() {
		if (persistenceUnitMetadata() != null) {
			return persistenceUnitMetadata().validationTextRange();
		}
		return this.entityMappings.validationTextRange();
	}
}
