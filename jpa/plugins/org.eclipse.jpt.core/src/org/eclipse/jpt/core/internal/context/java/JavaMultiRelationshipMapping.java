/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.INonOwningMapping;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.MapKey;
import org.eclipse.jpt.core.internal.resource.java.OrderBy;
import org.eclipse.jpt.core.internal.resource.java.RelationshipMapping;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;


public abstract class JavaMultiRelationshipMapping<T extends RelationshipMapping>
	extends JavaRelationshipMapping<T> implements IMultiRelationshipMapping
{

	protected String mappedBy;

	protected String orderBy;

	protected final IJavaJoinTable joinTable;

	protected String mapKey;

	protected JavaMultiRelationshipMapping(IJavaPersistentAttribute parent) {
		super(parent);
		this.joinTable = jpaFactory().createJavaJoinTable(this); 

	}

	
//	@Override
//	protected void notifyChanged(Notification notification) {
//		super.notifyChanged(notification);
//		switch (notification.getFeatureID(INonOwningMapping.class)) {
//			case JpaCoreMappingsPackage.INON_OWNING_MAPPING__MAPPED_BY :
//				this.mappedByAdapter.setValue((String) notification.getNewValue());
//				break;
//			default :
//				break;
//		}
//		switch (notification.getFeatureID(IMultiRelationshipMapping.class)) {
//			case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__ORDER_BY :
//				String orderBy = (String) notification.getNewValue();
//				if (orderBy == null) {
//					this.orderByAnnotationAdapter.removeAnnotation();
//				}
//				else if ("".equals(orderBy)) {
//					Annotation orderByAnnotation = this.orderByAnnotationAdapter.getAnnotation();
//					if (orderByAnnotation != null) {
//						// if the value is already "", then leave it alone (short circuit java change cycle)
//						if (!"".equals(orderByValueAdapter.getValue())) {
//							this.orderByValueAdapter.setValue(null);
//						}
//					}
//					else {
//						this.orderByAnnotationAdapter.newMarkerAnnotation();
//					}
//				}
//				else {
//					this.orderByValueAdapter.setValue(orderBy);
//				}
//				break;
//			case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__MAP_KEY :
//				String mk = (String) notification.getNewValue();
//				if (mk == null) {
//					this.mapKeyAnnotationAdapter.removeAnnotation();
//				}
//				else {
//					this.mapKeyNameAdapter.setValue(mk);
//				}
//				break;
//			case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__FETCH :
//				this.getFetchAdapter().setValue(((DefaultLazyFetchType) notification.getNewValue()).convertToJavaAnnotationValue());
//				break;
//			default :
//				break;
//		}
//	}

	public String getMappedBy() {
		return this.mappedBy;
	}

	public void setMappedBy(String newMappedBy) {
		String oldMappedBy = this.mappedBy;
		this.mappedBy = newMappedBy;
		this.setMappedByOnResourceModel(newMappedBy);
		firePropertyChanged(INonOwningMapping.MAPPED_BY_PROPERTY, oldMappedBy, newMappedBy);
	}
	
	protected abstract void setMappedByOnResourceModel(String mappedBy);

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String newOrderBy) {
		String oldOrderBy = this.orderBy;
		this.orderBy = newOrderBy;
		if (oldOrderBy != newOrderBy) {
			if (this.orderByResource(this.persistentAttributeResource) != null) {
				if (newOrderBy != null) {
					this.orderByResource(this.persistentAttributeResource).setValue(newOrderBy);
				}
				else {
					this.persistentAttributeResource.removeAnnotation(OrderBy.ANNOTATION_NAME);				
				}
			}
			else if (newOrderBy != null) {
				this.persistentAttributeResource.addAnnotation(OrderBy.ANNOTATION_NAME);
				orderByResource(this.persistentAttributeResource).setValue(newOrderBy);
			}
		}
		firePropertyChanged(IMultiRelationshipMapping.ORDER_BY_PROPERTY, oldOrderBy, newOrderBy);
	}
	
	protected void setOrderBy_(String newOrderBy) {
		String oldOrderBy = this.orderBy;
		this.orderBy = newOrderBy;
		firePropertyChanged(IMultiRelationshipMapping.ORDER_BY_PROPERTY, oldOrderBy, newOrderBy);
	}
	
	protected OrderBy orderByResource(JavaPersistentAttributeResource persistentAttributeResource) {
		return (OrderBy) persistentAttributeResource.annotation(OrderBy.ANNOTATION_NAME);
	}
	
	public boolean isNoOrdering() {
		return getOrderBy() == null;
	}

	public void setNoOrdering() {
		setOrderBy(null);
	}

	public boolean isOrderByPk() {
		return "".equals(getOrderBy());
	}

	public void setOrderByPk() {
		setOrderBy("");
	}

	public boolean isCustomOrdering() {
		return !StringTools.stringIsEmpty(getOrderBy());
	}

	public FetchType getDefaultFetch() {
		return IMultiRelationshipMapping.DEFAULT_FETCH_TYPE;
	}

	public IJavaJoinTable getJoinTable() {
		return this.joinTable;
	}

	public boolean isJoinTableSpecified() {
		return getJoinTable().isSpecified();
	}

	public String getMapKey() {
		return this.mapKey;
	}

	public void setMapKey(String newMapKey) {
		String oldMapKey = this.mapKey;
		this.mapKey = newMapKey;
		if (oldMapKey != newMapKey) {
			if (this.mapKeyResource(this.persistentAttributeResource) != null) {
				if (newMapKey != null) {
					this.mapKeyResource(this.persistentAttributeResource).setName(newMapKey);
				}
				else {
					this.persistentAttributeResource.removeAnnotation(MapKey.ANNOTATION_NAME);				
				}
			}
			else if (newMapKey != null) {
				this.persistentAttributeResource.addAnnotation(MapKey.ANNOTATION_NAME);
				mapKeyResource(this.persistentAttributeResource).setName(newMapKey);
			}
		}
		firePropertyChanged(IMultiRelationshipMapping.MAP_KEY_PROPERTY, oldMapKey, newMapKey);
	}

	protected void setMapKey_(String newMapKey) {
		String oldMapKey = this.mapKey;
		this.mapKey = newMapKey;
		firePropertyChanged(IMultiRelationshipMapping.MAP_KEY_PROPERTY, oldMapKey, newMapKey);
	}

//TODO default orderBy - this wasn't supported in 1.0 either
//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		super.refreshDefaults(defaultsContext);
//		//		if (isOrderByPk()) {
//		//			refreshDefaultOrderBy(defaultsContext);
//		//		}
//	}
//
//	//primary key ordering when just the @OrderBy annotation is present
//	protected void refreshDefaultOrderBy(DefaultsContext defaultsContext) {
//		IEntity targetEntity = getResolvedTargetEntity();
//		if (targetEntity != null) {
//			setOrderBy(targetEntity.primaryKeyAttributeName() + " ASC");
//		}
//	}

//	private void updateOrderByFromJava(CompilationUnit astRoot) {
//		String orderBy = this.orderByValueAdapter.getValue(astRoot);
//		if (orderBy == null) {
//			if (orderByAnnotation(astRoot) == null) {
//				this.setNoOrdering();
//			}
//			else {
//				this.setOrderByPk();
//			}
//		}
//		else if ("".equals(orderBy)) {
//			this.setOrderByPk();
//		}
//		else {
//			this.setOrderBy(orderBy);
//		}
//	}
//
//	private Annotation orderByAnnotation(CompilationUnit astRoot) {
//		return this.orderByAnnotationAdapter.getAnnotation(astRoot);
//	}
//
//	private void updateMapKeyFromJava(CompilationUnit astRoot) {
//		this.setMapKey(this.mapKeyNameAdapter.getValue(astRoot));
//	}

	@Override
	protected String defaultTargetEntity(JavaPersistentAttributeResource persistentAttributeResource) {
		if (!persistentAttributeResource.typeIsContainer()) {
			return null;
		}
		return persistentAttributeResource.getQualifiedReferenceEntityElementTypeName();
	}
	
	protected abstract boolean mappedByTouches(int pos, CompilationUnit astRoot);

	protected boolean mapKeyNameTouches(int pos, CompilationUnit astRoot) {
		if (mapKeyResource(this.persistentAttributeResource) != null) {
			return mapKeyResource(this.persistentAttributeResource).nameTouches(pos, astRoot);
		}
		return false;
	}
	
	protected MapKey mapKeyResource(JavaPersistentAttributeResource persistentAttributeResource) {
		return (MapKey) persistentAttributeResource.annotation(MapKey.ANNOTATION_NAME);
	}

	public Iterator<String> candidateMapKeyNames() {
		return this.allTargetEntityAttributeNames();
	}

	protected Iterator<String> candidateMapKeyNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateMapKeyNames(), filter);
	}

	protected Iterator<String> quotedCandidateMapKeyNames(Filter<String> filter) {
		return StringTools.quote(this.candidateMapKeyNames(filter));
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getJoinTable().candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.mappedByTouches(pos, astRoot)) {
			return this.quotedCandidateMappedByAttributeNames(filter);
		}
		if (this.mapKeyNameTouches(pos, astRoot)) {
			return this.quotedCandidateMapKeyNames(filter);
		}
		return null;
	}
	
	@Override
	public void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		MapKey mapKey = this.mapKeyResource(persistentAttributeResource);
		if (mapKey != null) {
			this.mapKey = mapKey.getName();
		}
		OrderBy orderBy = this.orderByResource(persistentAttributeResource);
		if (orderBy != null) {
			this.orderBy = orderBy.getValue();
		}
		this.joinTable.initializeFromResource(persistentAttributeResource);
	}
	
	@Override
	protected void initialize(T relationshipMapping) {
		super.initialize(relationshipMapping);
		this.mappedBy = this.mappedBy(relationshipMapping);
	}
	
	@Override
	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.updateMapKey(persistentAttributeResource);
		this.updateOrderBy(persistentAttributeResource);
		this.joinTable.update(persistentAttributeResource);
	}
	
	protected void updateMapKey(JavaPersistentAttributeResource persistentAttributeResource) {
		MapKey mapKey = this.mapKeyResource(persistentAttributeResource);
		if (mapKey != null) {
			setMapKey_(mapKey.getName());
		}
		else {
			setMapKey_(null);
		}
	}
	
	protected void updateOrderBy(JavaPersistentAttributeResource persistentAttributeResource) {
		OrderBy orderBy = this.orderByResource(persistentAttributeResource);
		if (orderBy != null) {
			setOrderBy_(orderBy.getValue());
		}
		else {
			setOrderBy_(null);
		}
	}
	
	@Override
	protected void update(T relationshipMapping) {
		super.update(relationshipMapping);
		this.setMappedBy(this.mappedBy(relationshipMapping));
	}
	
	protected abstract String mappedBy(T relationshipMapping);

}
