/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.context.CollectionMapping;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Orderable;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.orm.OrmMultiRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmOrderable;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.jpa2.context.orm.OrmPersistentAttribute2_0;
import org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlMapKeyClass;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * ORM multi-relationship (m:m, 1:m) mapping
 */
public abstract class AbstractOrmMultiRelationshipMapping<T extends AbstractXmlMultiRelationshipMapping>
	extends AbstractOrmRelationshipMapping<T>
	implements OrmMultiRelationshipMapping
{
	protected final OrmOrderable orderable;
		
	protected String specifiedMapKey;
	protected boolean noMapKey = false;
	protected boolean pkMapKey = false;
	protected boolean customMapKey = false;
	
	protected String specifiedMapKeyClass;
	protected String defaultMapKeyClass;

	protected AbstractOrmMultiRelationshipMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.orderable = getXmlContextNodeFactory().buildOrmOrderable(this);
		this.initializeMapKey();
		this.defaultMapKeyClass = this.buildDefaultMapKeyClass();
		this.specifiedMapKeyClass = this.getResourceMapKeyClass();
	}
	
	@Override
	public void update() {
		super.update();
		this.orderable.update();
		this.updateMapKey();
		this.setDefaultMapKeyClass(this.buildDefaultMapKeyClass());
		this.setSpecifiedMapKeyClass_(this.getResourceMapKeyClass());
	}
	
	@Override
	protected String getResourceDefaultTargetEntity() {
		return this.getJavaPersistentAttribute().getMultiReferenceTargetTypeName();
	}
	
	public FetchType getDefaultFetch() {
		return CollectionMapping.DEFAULT_FETCH_TYPE;
	}
	
	public PersistentType getResolvedTargetType() {
		return getResolvedTargetEntity() == null ? null : getResolvedTargetEntity().getPersistentType();
	}
	
	// **************** order by ***********************************************

	public Orderable getOrderable() {
		return this.orderable;
	}
	
	
	// **************** map key ************************************************
	
	public String getMapKey() {
		if (this.noMapKey) {
			return null;
		}
		if (this.pkMapKey) {
			return this.getTargetEntityIdAttributeName();
		}
		if (this.customMapKey) {
			return this.specifiedMapKey;
		}
		throw new IllegalStateException("unknown map key"); //$NON-NLS-1$
	}
	
	public String getSpecifiedMapKey() {
		return this.specifiedMapKey;
	}

	public void setSpecifiedMapKey(String mapKey) {
		String old = this.specifiedMapKey;
		this.specifiedMapKey = mapKey;
		if (this.attributeValueHasChanged(old, mapKey)) {
			MapKey xmlMapKey = this.getXmlMapKey();
			if (mapKey == null) {
				if (xmlMapKey != null) {
					this.removeXmlMapKey();
				}
			} else {
				if (xmlMapKey == null) {
					xmlMapKey = this.addXmlMapKey();
				}
				xmlMapKey.setName(mapKey);
			}
		}
		this.firePropertyChanged(SPECIFIED_MAP_KEY_PROPERTY, old, mapKey);
	}
	
	protected void setSpecifiedMapKey_(String mapKey) {
		String old = this.specifiedMapKey;
		this.specifiedMapKey = mapKey;
		this.firePropertyChanged(SPECIFIED_MAP_KEY_PROPERTY, old, mapKey);
	}
	
	protected void initializeMapKey() {
		MapKey xmlMapKey = this.getXmlMapKey();
		if (xmlMapKey == null) { 
			this.noMapKey = true;
		} else {
			this.specifiedMapKey = xmlMapKey.getName();
			if (this.specifiedMapKey == null) {
				this.pkMapKey = true;
			} else {
				this.customMapKey = true;
			}
		}
	}
	
	protected void updateMapKey() {
		MapKey xmlMapKey = this.getXmlMapKey();
		if (xmlMapKey == null) {
			this.setSpecifiedMapKey_(null);
			this.setNoMapKey_(true);
			this.setPkMapKey_(false);
			this.setCustomMapKey_(false);
		} else {
			String mk = xmlMapKey.getName();
			this.setSpecifiedMapKey_(mk);
			this.setNoMapKey_(false);
			this.setPkMapKey_(mk == null);
			this.setCustomMapKey_(mk != null);
		}
	}
	
	protected MapKey getXmlMapKey() {
		return this.resourceAttributeMapping.getMapKey();
	}
	
	protected MapKey addXmlMapKey() {
		MapKey mapKey = OrmFactory.eINSTANCE.createMapKey();
		this.resourceAttributeMapping.setMapKey(mapKey);
		return mapKey;
	}

	protected void removeXmlMapKey() {
		this.resourceAttributeMapping.setMapKey(null);
	}
	
	public Iterator<String> candidateMapKeyNames() {
		return this.allTargetEntityAttributeNames();
	}
	
	
	// **************** no map key ***********************************************
		
	public boolean isNoMapKey() {
		return this.noMapKey;
	}

	public void setNoMapKey(boolean noMapKey) {
		boolean old = this.noMapKey;
		this.noMapKey = noMapKey;
		if (noMapKey) {
			if (this.getXmlMapKey() != null) {
				this.removeXmlMapKey();
			}
		}
		this.firePropertyChanged(NO_MAP_KEY_PROPERTY, old, noMapKey);			
	}
	
	protected void setNoMapKey_(boolean noMapKey) {
		boolean old = this.noMapKey;
		this.noMapKey = noMapKey;
		this.firePropertyChanged(NO_MAP_KEY_PROPERTY, old, noMapKey);			
	}
	
	
	// **************** pk map key ***********************************************
		
	public boolean isPkMapKey() {
		return this.pkMapKey;
	}
	
	public void setPkMapKey(boolean pkMapKey) {
		boolean old = this.pkMapKey;
		this.pkMapKey = pkMapKey;
		MapKey xmlMapKey = this.getXmlMapKey();
		if (pkMapKey) {
			if (xmlMapKey == null) {
				this.addXmlMapKey();
			} else {
				xmlMapKey.setName(null);
			}
		}
		this.firePropertyChanged(PK_MAP_KEY_PROPERTY, old, pkMapKey);	
	}
	
	protected void setPkMapKey_(boolean pkMapKey) {
		boolean old = this.pkMapKey;
		this.pkMapKey = pkMapKey;
		this.firePropertyChanged(PK_MAP_KEY_PROPERTY, old, pkMapKey);	
	}
	
	
	// **************** custom map key ***********************************************
		
	public boolean isCustomMapKey() {
		return this.customMapKey;
	}

	public void setCustomMapKey(boolean customMapKey) {
		boolean old = this.customMapKey;
		this.customMapKey = customMapKey;
		if (customMapKey) {
			this.setSpecifiedMapKey(""); //$NON-NLS-1$
		}
		this.firePropertyChanged(CUSTOM_MAP_KEY_PROPERTY, old, customMapKey);
	}
	
	protected void setCustomMapKey_(boolean customMapKey) {
		boolean old = this.customMapKey;
		this.customMapKey = customMapKey;
		this.firePropertyChanged(CUSTOM_MAP_KEY_PROPERTY, old, customMapKey);
	}
	

	// **************** map key class ******************************************

	public char getMapKeyClassEnclosingTypeSeparator() {
		return '$';
	}
	
	public String getMapKeyClass() {
		return (this.specifiedMapKeyClass != null) ? this.specifiedMapKeyClass : this.defaultMapKeyClass;
	}

	public String getSpecifiedMapKeyClass() {
		return this.specifiedMapKeyClass;
	}

	public void setSpecifiedMapKeyClass(String mapKeyClass) {
		String old = this.specifiedMapKeyClass;
		this.specifiedMapKeyClass = mapKeyClass;
		if (this.attributeValueHasChanged(old, mapKeyClass)) {
			XmlMapKeyClass xmlMapKeyClass = this.getXmlMapKeyClass();
			if (mapKeyClass == null) {
				if (xmlMapKeyClass != null) {
					this.removeXmlMapKeyClass();
				}
			} else {
				if (xmlMapKeyClass == null) {
					xmlMapKeyClass = this.addXmlMapKeyClass();
				}
				xmlMapKeyClass.setClassName(mapKeyClass);
			}
		}
		this.firePropertyChanged(SPECIFIED_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected void setSpecifiedMapKeyClass_(String mapKeyClass) {
		String old = this.specifiedMapKeyClass;
		this.specifiedMapKeyClass = mapKeyClass;
		this.firePropertyChanged(SPECIFIED_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}
	
	protected XmlMapKeyClass getXmlMapKeyClass() {
		return this.resourceAttributeMapping.getMapKeyClass();
	}
	
	protected XmlMapKeyClass addXmlMapKeyClass() {
		XmlMapKeyClass mapKeyClass = OrmFactory.eINSTANCE.createXmlMapKeyClass();
		this.resourceAttributeMapping.setMapKeyClass(mapKeyClass);
		return mapKeyClass;
	}

	protected void removeXmlMapKeyClass() {
		this.resourceAttributeMapping.setMapKeyClass(null);
	}

	public String getDefaultMapKeyClass() {
		return this.defaultMapKeyClass;
	}

	protected void setDefaultMapKeyClass(String mapKeyClass) {
		String old = this.defaultMapKeyClass;
		this.defaultMapKeyClass = mapKeyClass;
		this.firePropertyChanged(DEFAULT_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected String getResourceMapKeyClass() {
		XmlMapKeyClass mapKeyClass = this.resourceAttributeMapping.getMapKeyClass();
		return mapKeyClass == null ? null : mapKeyClass.getClassName();
	}
	
	protected String buildDefaultMapKeyClass() {
		if (this.getJavaPersistentAttribute() != null) {
			return this.getJavaPersistentAttribute().getMultiReferenceMapKeyTypeName();
		}
		return null;
	}

	
	// ********** metamodel **********  

	@Override
	protected String getMetamodelFieldTypeName() {
		return ((OrmPersistentAttribute2_0) getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		this.addMetamodelFieldMapKeyTypeArgumentNameTo(typeArgumentNames);
		super.addMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
	}

	protected void addMetamodelFieldMapKeyTypeArgumentNameTo(ArrayList<String> typeArgumentNames) {
		String keyTypeName = ((OrmPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldMapKeyTypeName();
		if (keyTypeName != null) {
			typeArgumentNames.add(keyTypeName);
		}
	}

	public String getMetamodelFieldMapKeyTypeName() {
		return MappingTools.getMetamodelFieldMapKeyTypeName(this);
	}
	
	
	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.orderable.validate(messages, reporter);
	}

}
