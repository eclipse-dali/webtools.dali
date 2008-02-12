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
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.INonOwningMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.MapKey;
import org.eclipse.jpt.core.internal.resource.java.OrderBy;
import org.eclipse.jpt.core.internal.resource.java.RelationshipMapping;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public abstract class JavaMultiRelationshipMapping<T extends RelationshipMapping>
	extends JavaRelationshipMapping<T> implements IMultiRelationshipMapping
{

	protected String mappedBy;

	protected String orderBy;

	protected boolean isNoOrdering;

	protected boolean isPkOrdering;

	protected boolean isCustomOrdering;
	
	//TODO should this be null if this is the non-owning side of the relationship??
	protected final IJavaJoinTable joinTable; 

	protected String mapKey;

	protected JavaMultiRelationshipMapping(IJavaPersistentAttribute parent) {
		super(parent);
		this.joinTable = jpaFactory().createJavaJoinTable(this); 
	}

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
		if (newOrderBy == null) {
			if (orderByResource() != null) { 
				removeOrderByResource();
			}
		}
		else {
			if (orderByResource() == null) {
				addOrderByResource();
			}
			orderByResource().setValue(newOrderBy);
		}
		firePropertyChanged(IMultiRelationshipMapping.ORDER_BY_PROPERTY, oldOrderBy, newOrderBy);
	}
	
	protected void setOrderBy_(String newOrderBy) {
		String oldOrderBy = this.orderBy;
		this.orderBy = newOrderBy;
		firePropertyChanged(IMultiRelationshipMapping.ORDER_BY_PROPERTY, oldOrderBy, newOrderBy);
	}
	
	protected OrderBy orderByResource() {
		return (OrderBy) this.persistentAttributeResource.annotation(OrderBy.ANNOTATION_NAME);
	}
	
	protected OrderBy addOrderByResource() {
		return (OrderBy) this.persistentAttributeResource.addAnnotation(OrderBy.ANNOTATION_NAME);
	}
	
	protected void removeOrderByResource() {
		this.persistentAttributeResource.removeAnnotation(OrderBy.ANNOTATION_NAME);
	}
	
	public boolean isNoOrdering() {
		return this.isNoOrdering;
	}

	public void setNoOrdering(boolean newNoOrdering) {
		boolean oldNoOrdering = this.isNoOrdering;
		this.isNoOrdering = newNoOrdering;
		if (newNoOrdering) {
			if (orderByResource() != null) {
				removeOrderByResource();
			}
		}
		else {
			//??
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
			if (orderByResource() == null) {
				addOrderByResource();
			}
			else {
				orderByResource().setValue(null);
			}
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
			setOrderBy("");
		}
		firePropertyChanged(CUSTOM_ORDERING_PROPERTY, oldCustomOrdering, newCustomOrdering);
	}
	
	protected void setCustomOrdering_(boolean newCustomOrdering) {
		boolean oldCustomOrdering = this.isCustomOrdering;
		this.isCustomOrdering = newCustomOrdering;
		firePropertyChanged(CUSTOM_ORDERING_PROPERTY, oldCustomOrdering, newCustomOrdering);
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

	public boolean isRelationshipOwner() {
		return getMappedBy() == null;
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
		this.initializeOrderBy(this.orderByResource());
		this.joinTable.initializeFromResource(persistentAttributeResource);
	}
	
	@Override
	protected void initialize(T relationshipMapping) {
		super.initialize(relationshipMapping);
		this.mappedBy = this.mappedBy(relationshipMapping);
	}
	
	protected void initializeOrderBy(OrderBy orderBy) {
		if (orderBy != null) {
			this.orderBy = orderBy.getValue();
			if (orderBy.getValue() == null) {
				this.isPkOrdering = true;
			}
			else {
				this.isCustomOrdering = true;
			}
		}
		else {
			this.isNoOrdering = true;
		}
	}
	
	@Override
	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.updateMapKey(persistentAttributeResource);
		this.updateOrderBy(this.orderByResource());
		this.joinTable.update(persistentAttributeResource);
	}	
	
	@Override
	protected void update(T relationshipMapping) {
		super.update(relationshipMapping);
		this.setMappedBy(this.mappedBy(relationshipMapping));
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
	
	protected void updateOrderBy(OrderBy orderBy) {
		if (orderBy != null) {
			setOrderBy_(orderBy.getValue());
			if (orderBy.getValue() == null) {
				setPkOrdering_(true);
				setCustomOrdering_(false);
				setNoOrdering_(false);
			}
			else {
				setPkOrdering_(false);
				setCustomOrdering_(true);
				setNoOrdering_(false);
			}
		}
		else {
			setOrderBy_(null);
			setPkOrdering_(false);
			setCustomOrdering_(false);
			setNoOrdering_(true);
		}
	}
	protected abstract String mappedBy(T relationshipMapping);

	//******** Validation ***********************************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		if (this.isJoinTableSpecified() || isRelationshipOwner()) {
			addJoinTableMessages(messages, astRoot);
		}
		if (this.getMappedBy() != null) {
			addMappedByMessages(messages, astRoot);
		}
	}
	
	protected void addJoinTableMessages(List<IMessage> messages, CompilationUnit astRoot) {
		IJavaJoinTable joinTable = this.getJoinTable();
		
		boolean doContinue = joinTable.isConnected();
		String schema = joinTable.getSchema();
		
		if (doContinue && ! joinTable.hasResolvedSchema()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_TABLE_UNRESOLVED_SCHEMA,
						new String[] {schema, joinTable.getName()}, 
						joinTable, joinTable.schemaTextRange(astRoot))
				);
			doContinue = false;
		}
		
		if (doContinue && ! joinTable.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_TABLE_UNRESOLVED_NAME,
						new String[] {joinTable.getName()}, 
						joinTable, joinTable.nameTextRange(astRoot))
				);
			doContinue = false;
		}
		
		for (Iterator<IJavaJoinColumn> stream = joinTable.joinColumns(); stream.hasNext(); ) {
			IJavaJoinColumn joinColumn = stream.next();
			
			if (doContinue && ! joinColumn.isResolved()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getName()}, 
						joinColumn, joinColumn.nameTextRange(astRoot))
				);
			}
			
			if (doContinue && ! joinColumn.isReferencedColumnResolved()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
						joinColumn, joinColumn.referencedColumnNameTextRange(astRoot))
				);
			}
		}
		
		for (Iterator<IJavaJoinColumn> stream = joinTable.inverseJoinColumns(); stream.hasNext(); ) {
			IJavaJoinColumn joinColumn = stream.next();
			
			if (doContinue && ! joinColumn.isResolved()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getName()}, 
						joinColumn, joinColumn.nameTextRange(astRoot))
				);
			}
			
			if (doContinue && ! joinColumn.isReferencedColumnResolved()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
						joinColumn, joinColumn.referencedColumnNameTextRange(astRoot))
				);
			}
		}
	}
	
	protected void addMappedByMessages(List<IMessage> messages, CompilationUnit astRoot) {
		String mappedBy = this.getMappedBy();
		
		if (this.isJoinTableSpecified()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.MAPPING_MAPPED_BY_WITH_JOIN_TABLE,
						this.getJoinTable(), this.getJoinTable().validationTextRange(astRoot))
				);
						
		}
		
		IEntity targetEntity = this.getResolvedTargetEntity();
		
		if (targetEntity == null) {
			// already have validation messages for that
			return;
		}
		
		IPersistentAttribute attribute = targetEntity.persistentType().resolveAttribute(mappedBy);
		
		if (attribute == null) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,
						new String[] {mappedBy}, 
						this, this.mappedByTextRange(astRoot))
				);
			return;
		}
		
		if (! this.mappedByIsValid(attribute.getMapping())) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.MAPPING_INVALID_MAPPED_BY,
						new String[] {mappedBy}, 
						this, this.mappedByTextRange(astRoot))
				);
			return;
		}
		
		INonOwningMapping mappedByMapping;
		try {
			mappedByMapping = (INonOwningMapping) attribute.getMapping();
		} catch (ClassCastException cce) {
			// there is no error then
			return;
		}
		
		if (mappedByMapping.getMappedBy() != null) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES,
						this, this.mappedByTextRange(astRoot))
				);
		}
	}
}
