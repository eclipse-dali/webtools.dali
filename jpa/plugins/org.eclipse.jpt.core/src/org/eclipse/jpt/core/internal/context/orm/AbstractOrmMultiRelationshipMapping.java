/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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

import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.MultiRelationshipMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmMultiRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.AttributeMapping2_0;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.OrmFactory;

/**
 * ORM multi-relationship (m:m, 1:m) mapping
 */
public abstract class AbstractOrmMultiRelationshipMapping<T extends AbstractXmlMultiRelationshipMapping>
	extends AbstractOrmRelationshipMapping<T>
	implements OrmMultiRelationshipMapping
{
	protected String specifiedOrderBy;
	protected boolean noOrdering = false;
	protected boolean pkOrdering = false;
	protected boolean customOrdering = false;
	
	protected String specifiedMapKey;
	protected boolean noMapKey = false;
	protected boolean pkMapKey = false;
	protected boolean customMapKey = false;
	
	
	protected AbstractOrmMultiRelationshipMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.initializeOrderBy();
		this.initializeMapKey();
	}
	
	@Override
	public void update() {
		super.update();
		this.updateOrderBy();
		this.updateMapKey();
	}
	
	@Override
	protected String getResourceDefaultTargetEntity() {
		return this.getJavaPersistentAttribute().getMultiReferenceEntityTypeName();
	}
	
	public FetchType getDefaultFetch() {
		return MultiRelationshipMapping.DEFAULT_FETCH_TYPE;
	}
	
	
	// **************** order by ***********************************************
	
	public String getOrderBy() {
		if (this.noOrdering) {
			return null;
		}
		if (this.pkOrdering) {
			return this.getTargetEntityIdAttributeName();
		}
		if (this.customOrdering) {
			return this.specifiedOrderBy;
		}
		throw new IllegalStateException("unknown ordering"); //$NON-NLS-1$
	}
	
	public String getSpecifiedOrderBy() {
		return this.specifiedOrderBy;
	}

	public void setSpecifiedOrderBy(String orderBy) {
		String old = this.specifiedOrderBy;
		this.specifiedOrderBy = orderBy;
		this.resourceAttributeMapping.setOrderBy(orderBy);
		this.firePropertyChanged(SPECIFIED_ORDER_BY_PROPERTY, old, orderBy);
	}
	
	protected void setSpecifiedOrderBy_(String orderBy) {
		String old = this.specifiedOrderBy;
		this.specifiedOrderBy = orderBy;
		this.firePropertyChanged(SPECIFIED_ORDER_BY_PROPERTY, old, orderBy);
	}

	protected void initializeOrderBy() {
		this.specifiedOrderBy = this.getXmlOrderBy();
		if (this.specifiedOrderBy == null) { 
			this.noOrdering = true;
		} else if (this.specifiedOrderBy.equals("")) { //$NON-NLS-1$
			this.pkOrdering = true;
		} else {
			this.customOrdering = true;
		}
	}
	
	protected void updateOrderBy() {
		this.setSpecifiedOrderBy_(this.getXmlOrderBy());
		if (this.specifiedOrderBy == null) { 
			this.setNoOrdering_(true);
			this.setPkOrdering_(false);
			this.setCustomOrdering_(false);
		} else if (this.specifiedOrderBy.equals("")) { //$NON-NLS-1$
			this.setNoOrdering_(false);
			this.setPkOrdering_(true);
			this.setCustomOrdering_(false);
		} else {
			this.setNoOrdering_(false);
			this.setPkOrdering_(false);
			this.setCustomOrdering_(true);
		}
	}
	
	protected String getXmlOrderBy() {
		return this.resourceAttributeMapping.getOrderBy();
	}
	
	
	// **************** no ordering ***********************************************
		
	public boolean isNoOrdering() {
		return this.noOrdering;
	}

	public void setNoOrdering(boolean noOrdering) {
		boolean old = this.noOrdering;
		this.noOrdering = noOrdering;
		if (noOrdering) {
			this.resourceAttributeMapping.setOrderBy(null);
		}
		this.firePropertyChanged(NO_ORDERING_PROPERTY, old, noOrdering);			
	}
	
	protected void setNoOrdering_(boolean noOrdering) {
		boolean old = this.noOrdering;
		this.noOrdering = noOrdering;
		this.firePropertyChanged(NO_ORDERING_PROPERTY, old, noOrdering);			
	}
	
	
	// **************** pk ordering ***********************************************
		
	public boolean isPkOrdering() {
		return this.pkOrdering;
	}
	
	public void setPkOrdering(boolean pkOrdering) {
		boolean old = this.pkOrdering;
		this.pkOrdering = pkOrdering;
		if (pkOrdering) {
			this.resourceAttributeMapping.setOrderBy(""); //$NON-NLS-1$
		}
		this.firePropertyChanged(PK_ORDERING_PROPERTY, old, pkOrdering);	
	}
	
	protected void setPkOrdering_(boolean pkOrdering) {
		boolean old = this.pkOrdering;
		this.pkOrdering = pkOrdering;
		this.firePropertyChanged(PK_ORDERING_PROPERTY, old, pkOrdering);	
	}
	
	
	// **************** custom ordering ***********************************************
		
	public boolean isCustomOrdering() {
		return this.customOrdering;
	}

	public void setCustomOrdering(boolean customOrdering) {
		boolean old = this.customOrdering;
		this.customOrdering = customOrdering;
		if (customOrdering) {
			this.setSpecifiedOrderBy(""); //$NON-NLS-1$
		}
		this.firePropertyChanged(CUSTOM_ORDERING_PROPERTY, old, customOrdering);
	}
	
	protected void setCustomOrdering_(boolean customOrdering) {
		boolean old = this.customOrdering;
		this.customOrdering = customOrdering;
		this.firePropertyChanged(CUSTOM_ORDERING_PROPERTY, old, customOrdering);
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
	

	// ********** metamodel **********  

	@Override
	protected String getMetamodelFieldTypeName() {
		return ((JavaPersistentAttribute2_0) this.getJavaPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		this.addMetamodelFieldMapKeyTypeArgumentNameTo(typeArgumentNames);
		super.addMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
	}

	protected void addMetamodelFieldMapKeyTypeArgumentNameTo(ArrayList<String> typeArgumentNames) {
		String mapKey = this.getMapKey();
		if (mapKey != null) {
			typeArgumentNames.add(this.getMetamodelTypeNameForAttributeNamed(mapKey));
		}
	}

	/**
	 * pre-condition: attribute name is non-null
	 */
	protected String getMetamodelTypeNameForAttributeNamed(String attributeName) {
		if (this.resolvedTargetEntity == null) {
			return MetamodelField.DEFAULT_TYPE_NAME;
		}
		PersistentAttribute pa = this.resolvedTargetEntity.getPersistentType().resolveAttribute(attributeName);
		if (pa == null) {
			return MetamodelField.DEFAULT_TYPE_NAME;
		}
		AttributeMapping2_0 am = (AttributeMapping2_0) pa.getMapping();
		if (am == null) {
			return MetamodelField.DEFAULT_TYPE_NAME;
		}
		return am.getMetamodelTypeName();
	}

}
