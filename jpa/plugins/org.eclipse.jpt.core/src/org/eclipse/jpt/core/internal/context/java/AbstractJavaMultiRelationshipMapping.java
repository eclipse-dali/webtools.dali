/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.java.JavaMultiRelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.core.resource.java.OrderByAnnotation;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * Java multi-relationship (m:m, 1:m) mapping
 */
public abstract class AbstractJavaMultiRelationshipMapping<T extends RelationshipMappingAnnotation>
	extends AbstractJavaRelationshipMapping<T> 
	implements JavaMultiRelationshipMapping
{
	protected String orderBy;

	protected boolean noOrdering = false;

	protected boolean pkOrdering = false;

	protected boolean customOrdering = false;

	protected String mapKey;


	protected AbstractJavaMultiRelationshipMapping(JavaPersistentAttribute parent) {
		super(parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.initializeOrderBy();
		this.initializeMapKey();
	}

	@Override
	protected void update() {
		super.update();
		this.updateOrderBy();
		this.updateMapKey();
	}


	// ********** AbstractJavaRelationshipMapping implementation **********  

	@Override
	protected String buildDefaultTargetEntity() {
		return this.getPersistentAttribute().getMultiReferenceEntityTypeName();
	}


	// ********** order by **********  

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		String old = this.orderBy;
		this.orderBy = orderBy;
		OrderByAnnotation orderByAnnotation = this.getOrderByAnnotation();
		if (orderBy == null) {
			if (orderByAnnotation != null) { 
				this.removeOrderByAnnotation();
			}
		} else {
			if (orderByAnnotation == null) {
				orderByAnnotation = this.addOrderByAnnotation();
			}
			orderByAnnotation.setValue(orderBy);
		}
		this.firePropertyChanged(ORDER_BY_PROPERTY, old, orderBy);
	}

	protected void setOrderBy_(String orderBy) {
		String old = this.orderBy;
		this.orderBy = orderBy;
		this.firePropertyChanged(ORDER_BY_PROPERTY, old, orderBy);
	}

	protected void initializeOrderBy() {
		OrderByAnnotation orderByAnnotation = this.getOrderByAnnotation();
		if (orderByAnnotation == null) {
			this.noOrdering = true;
		} else {
			this.orderBy = orderByAnnotation.getValue();
			if (orderByAnnotation.getValue() == null) {
				this.pkOrdering = true;
			} else {
				this.customOrdering = true;
			}
		}
	}

	protected void updateOrderBy() {
		OrderByAnnotation orderByAnnotation = this.getOrderByAnnotation();
		if (orderByAnnotation == null) {
			this.setOrderBy_(null);
			this.setNoOrdering_(true);
			this.setPkOrdering_(false);
			this.setCustomOrdering_(false);
		} else {
			String ob = orderByAnnotation.getValue();
			this.setOrderBy_(ob);
			if (ob == null) {
				this.setPkOrdering_(true);
				this.setCustomOrdering_(false);
				this.setNoOrdering_(false);
			}
			else {
				this.setCustomOrdering_(true);
				this.setPkOrdering_(false);
				this.setNoOrdering_(false);
			}
		}
	}

	protected OrderByAnnotation getOrderByAnnotation() {
		return (OrderByAnnotation) this.resourcePersistentAttribute.getAnnotation(OrderByAnnotation.ANNOTATION_NAME);
	}

	protected OrderByAnnotation addOrderByAnnotation() {
		return (OrderByAnnotation) this.resourcePersistentAttribute.addAnnotation(OrderByAnnotation.ANNOTATION_NAME);
	}

	protected void removeOrderByAnnotation() {
		this.resourcePersistentAttribute.removeAnnotation(OrderByAnnotation.ANNOTATION_NAME);
	}


	// ********** no ordering **********  

	public boolean isNoOrdering() {
		return this.noOrdering;
	}

	public void setNoOrdering(boolean noOrdering) {
		boolean old = this.noOrdering;
		this.noOrdering = noOrdering;
		if (noOrdering) {
			if (this.getOrderByAnnotation() != null) {
				this.removeOrderByAnnotation();
			}
		} else {
			// the 'noOrdering' flag is cleared as a
			// side-effect of setting the other flags,
			// via a call to #setNoOrdering_(boolean)
		}
		this.firePropertyChanged(NO_ORDERING_PROPERTY, old, noOrdering);
	}

	protected void setNoOrdering_(boolean noOrdering) {
		boolean old = this.noOrdering;
		this.noOrdering = noOrdering;
		this.firePropertyChanged(NO_ORDERING_PROPERTY, old, noOrdering);	
	}


	// ********** pk ordering **********  

	public boolean isPkOrdering() {
		return this.pkOrdering;
	}

	public void setPkOrdering(boolean pkOrdering) {
		boolean old = this.pkOrdering;
		this.pkOrdering = pkOrdering;
		OrderByAnnotation orderByAnnotation = this.getOrderByAnnotation();
		if (pkOrdering) {
			if (orderByAnnotation == null) {
				this.addOrderByAnnotation();
			} else {
				orderByAnnotation.setValue(null);
			}
		} else {
			// the 'pkOrdering' flag is cleared as a
			// side-effect of setting the other flags,
			// via a call to #setPkOrdering_(boolean)
		}
		this.firePropertyChanged(PK_ORDERING_PROPERTY, old, pkOrdering);
	}

	protected void setPkOrdering_(boolean pkOrdering) {
		boolean old = this.pkOrdering;
		this.pkOrdering = pkOrdering;
		this.firePropertyChanged(PK_ORDERING_PROPERTY, old, pkOrdering);
	}


	// ********** custom ordering **********  

	public boolean isCustomOrdering() {
		return this.customOrdering;
	}

	public void setCustomOrdering(boolean customOrdering) {
		boolean old = this.customOrdering;
		this.customOrdering = customOrdering;
		if (customOrdering) {
			this.setOrderBy(""); //$NON-NLS-1$
		} else {
			// the 'customOrdering' flag is cleared as a
			// side-effect of setting the other flags,
			// via a call to #setCustomOrdering_(boolean)
		}
		this.firePropertyChanged(CUSTOM_ORDERING_PROPERTY, old, customOrdering);
	}

	protected void setCustomOrdering_(boolean customOrdering) {
		boolean old = this.customOrdering;
		this.customOrdering = customOrdering;
		this.firePropertyChanged(CUSTOM_ORDERING_PROPERTY, old, customOrdering);
	}


	// ********** Fetchable implementation **********  

	public FetchType getDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}


	// ********** map key **********  

	public String getMapKey() {
		return this.mapKey;
	}

	public void setMapKey(String mapKey) {
		String old = this.mapKey;
		this.mapKey = mapKey;
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		if (mapKey == null) {
			if (mapKeyAnnotation != null) {
				this.removeMapKeyAnnotation();
			}
		} else {
			if (mapKeyAnnotation == null) {
				mapKeyAnnotation = this.addMapKeyAnnotation();
			}
			mapKeyAnnotation.setName(mapKey);
		}
		this.firePropertyChanged(MAP_KEY_PROPERTY, old, mapKey);
	}

	protected void setMapKey_(String mapKey) {
		String old = this.mapKey;
		this.mapKey = mapKey;
		this.firePropertyChanged(MAP_KEY_PROPERTY, old, mapKey);
	}

	protected void initializeMapKey() {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		if (mapKeyAnnotation != null) {
			this.mapKey = mapKeyAnnotation.getName();
		}
	}

	protected void updateMapKey() {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		this.setMapKey_((mapKeyAnnotation == null) ? null : mapKeyAnnotation.getName());
	}

	protected MapKeyAnnotation getMapKeyAnnotation() {
		return (MapKeyAnnotation) this.resourcePersistentAttribute.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected MapKeyAnnotation addMapKeyAnnotation() {
		return (MapKeyAnnotation) this.resourcePersistentAttribute.addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyAnnotation() {
		this.resourcePersistentAttribute.removeAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected boolean mapKeyNameTouches(int pos, CompilationUnit astRoot) {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		return (mapKeyAnnotation != null) && mapKeyAnnotation.nameTouches(pos, astRoot);
	}


	// ********** Java completion proposals **********  

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.mapKeyNameTouches(pos, astRoot)) {
			return this.javaCandidateMapKeyNames(filter);
		}
		return null;
	}

	protected Iterator<String> javaCandidateMapKeyNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateMapKeyNames(filter));
	}

	protected Iterator<String> candidateMapKeyNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateMapKeyNames(), filter);
	}

	public Iterator<String> candidateMapKeyNames() {
		return this.allTargetEntityAttributeNames();
	}

}
