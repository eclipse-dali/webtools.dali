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

import java.util.Iterator;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.MultiRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmMultiRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping;


public abstract class AbstractOrmMultiRelationshipMapping<T extends XmlMultiRelationshipMapping>
	extends AbstractOrmRelationshipMapping<T>
	implements OrmMultiRelationshipMapping
{
	protected String mapKey;
	
	protected String orderBy;//TODO change this to defaultOrderBy and specifiedOrderBy?
	
	protected boolean isNoOrdering;
	
	protected boolean isPkOrdering;
	
	protected boolean isCustomOrdering;
	
	
	protected AbstractOrmMultiRelationshipMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.mapKey = this.getResourceMapKeyName();
		this.orderBy = this.getResourceOrderBy();
		if (this.orderBy == null) { 
			this.isNoOrdering = true;
		}
		else {
			this.isCustomOrdering = true;
		}
	}
	
	
	public FetchType getDefaultFetch() {
		return MultiRelationshipMapping.DEFAULT_FETCH_TYPE;
	}
	
	
	// **************** map key ************************************************
	
	public String getMapKey() {
		return this.mapKey;
	}

	public void setMapKey(String newMapKey) {
		String oldMapKey = this.mapKey;
		this.mapKey = newMapKey;
		if (oldMapKey != newMapKey) {
			if (this.getResourceMapKey() != null) {
				this.getResourceMapKey().setName(newMapKey);						
				if (this.getResourceMapKey().isUnset()) {
					removeResourceMapKey();
				}
			}
			else if (newMapKey != null) {
				addResourceMapKey();
				getResourceMapKey().setName(newMapKey);
			}
		}
		firePropertyChanged(MAP_KEY_PROPERTY, oldMapKey, newMapKey);
	}
	
	protected void setMapKey_(String newMapKey) {
		String oldMapKey = this.mapKey;
		this.mapKey = newMapKey;
		firePropertyChanged(MAP_KEY_PROPERTY, oldMapKey, newMapKey);
	}
	
	protected MapKey getResourceMapKey() {
		return this.resourceAttributeMapping.getMapKey();
	}
	
	protected void removeResourceMapKey() {
		this.resourceAttributeMapping.setMapKey(null);
	}
	
	protected void addResourceMapKey() {
		this.resourceAttributeMapping.setMapKey(OrmFactory.eINSTANCE.createMapKeyImpl());
	}

	public Iterator<String> candidateMapKeyNames() {
		return this.allTargetEntityAttributeNames();
	}
	
	
	// **************** ordering ***********************************************
		
	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String newOrderBy) {
		String oldOrderBy = this.orderBy;
		this.orderBy = newOrderBy;
		this.resourceAttributeMapping.setOrderBy(newOrderBy);
		firePropertyChanged(ORDER_BY_PROPERTY, oldOrderBy, newOrderBy);
	}
	
	protected void setOrderBy_(String newOrderBy) {
		String oldOrderBy = this.orderBy;
		this.orderBy = newOrderBy;
		firePropertyChanged(ORDER_BY_PROPERTY, oldOrderBy, newOrderBy);
	}

	public boolean isNoOrdering() {
		return this.isNoOrdering;
	}

	public void setNoOrdering(boolean newNoOrdering) {
		boolean oldNoOrdering = this.isNoOrdering;
		this.isNoOrdering = newNoOrdering;
		if (newNoOrdering) {
			this.resourceAttributeMapping.setOrderBy(null);
		}
		firePropertyChanged(NO_ORDERING_PROPERTY, oldNoOrdering, newNoOrdering);			
	}
	
	protected void setNoOrdering_(boolean newNoOrdering) {
		boolean oldNoOrdering = this.isNoOrdering;
		this.isNoOrdering = newNoOrdering;
		firePropertyChanged(NO_ORDERING_PROPERTY, oldNoOrdering, newNoOrdering);			
	}
	
	public boolean isPkOrdering() {
		return this.isPkOrdering;
	}
	
	public void setPkOrdering(boolean newPkOrdering) {
		boolean oldPkOrdering = this.isPkOrdering;
		this.isPkOrdering = newPkOrdering;
		if (newPkOrdering) {
			this.resourceAttributeMapping.setOrderBy(""); //$NON-NLS-1$
		}
		firePropertyChanged(PK_ORDERING_PROPERTY, oldPkOrdering, newPkOrdering);	
	}
	
	protected void setPkOrdering_(boolean newPkOrdering) {
		boolean oldPkOrdering = this.isPkOrdering;
		this.isPkOrdering = newPkOrdering;
		firePropertyChanged(PK_ORDERING_PROPERTY, oldPkOrdering, newPkOrdering);	
	}
	
	public boolean isCustomOrdering() {
		return this.isCustomOrdering;
	}

	public void setCustomOrdering(boolean newCustomOrdering) {
		boolean oldCustomOrdering = this.isCustomOrdering;
		this.isCustomOrdering = newCustomOrdering;
		if (newCustomOrdering) {
			setOrderBy(""); //$NON-NLS-1$
		}
		firePropertyChanged(CUSTOM_ORDERING_PROPERTY, oldCustomOrdering, newCustomOrdering);
	}
	
	protected void setCustomOrdering_(boolean newCustomOrdering) {
		boolean oldCustomOrdering = this.isCustomOrdering;
		this.isCustomOrdering = newCustomOrdering;
		firePropertyChanged(CUSTOM_ORDERING_PROPERTY, oldCustomOrdering, newCustomOrdering);
	}
	
	
	// **************** resource -> context ************************************
	
	@Override
	public void update() {
		super.update();
		this.setMapKey_(this.getResourceMapKeyName());
		this.setOrderBy_(this.getResourceOrderBy());
		if (getOrderBy() == null) { 
			setNoOrdering_(true);
			setPkOrdering_(false);
			setCustomOrdering_(false);
		}
		else {
			setNoOrdering_(false);
			setPkOrdering_(false);
			setCustomOrdering_(true);
		}
	}
	
	protected String getResourceMapKeyName() {
		return this.resourceAttributeMapping.getMapKey() == null ? null : this.resourceAttributeMapping.getMapKey().getName();
	}
	
	protected String getResourceOrderBy() {
		return this.resourceAttributeMapping.getOrderBy();
	}
	
	@Override
	protected String getResourceDefaultTargetEntity() {
		return this.getJavaPersistentAttribute().getMultiReferenceEntityTypeName();
	}
}
