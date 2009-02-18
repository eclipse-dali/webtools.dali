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
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.MultiRelationshipMapping;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.core.resource.java.OrderByAnnotation;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaMultiRelationshipMapping<T extends RelationshipMappingAnnotation>
	extends AbstractJavaRelationshipMapping<T> implements MultiRelationshipMapping
{

	protected String mappedBy;

	protected String orderBy;

	protected boolean isNoOrdering;

	protected boolean isPkOrdering;

	protected boolean isCustomOrdering;
	
	//TODO should this be null if this is the non-owning side of the relationship??
	protected final JavaJoinTable joinTable; 

	protected String mapKey;

	protected AbstractJavaMultiRelationshipMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.joinTable = getJpaFactory().buildJavaJoinTable(this); 
	}

	public String getMappedBy() {
		return this.mappedBy;
	}

	public void setMappedBy(String newMappedBy) {
		String oldMappedBy = this.mappedBy;
		this.mappedBy = newMappedBy;
		this.setMappedByOnResourceModel(newMappedBy);
		firePropertyChanged(NonOwningMapping.MAPPED_BY_PROPERTY, oldMappedBy, newMappedBy);
	}

	protected void setMappedBy_(String newMappedBy) {
		String oldMappedBy = this.mappedBy;
		this.mappedBy = newMappedBy;
		firePropertyChanged(NonOwningMapping.MAPPED_BY_PROPERTY, oldMappedBy, newMappedBy);
	}

	protected abstract void setMappedByOnResourceModel(String mappedBy);

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String newOrderBy) {
		String oldOrderBy = this.orderBy;
		this.orderBy = newOrderBy;
		if (newOrderBy == null) {
			if (getResourceOrderBy() != null) { 
				removeResourceOrderBy();
			}
		}
		else {
			if (getResourceOrderBy() == null) {
				addResourceOrderBy();
			}
			getResourceOrderBy().setValue(newOrderBy);
		}
		firePropertyChanged(MultiRelationshipMapping.ORDER_BY_PROPERTY, oldOrderBy, newOrderBy);
	}
	
	protected void setOrderBy_(String newOrderBy) {
		String oldOrderBy = this.orderBy;
		this.orderBy = newOrderBy;
		firePropertyChanged(MultiRelationshipMapping.ORDER_BY_PROPERTY, oldOrderBy, newOrderBy);
	}
	
	protected OrderByAnnotation getResourceOrderBy() {
		return (OrderByAnnotation) getResourcePersistentAttribute().getSupportingAnnotation(OrderByAnnotation.ANNOTATION_NAME);
	}
	
	protected OrderByAnnotation addResourceOrderBy() {
		return (OrderByAnnotation) getResourcePersistentAttribute().addSupportingAnnotation(OrderByAnnotation.ANNOTATION_NAME);
	}
	
	protected void removeResourceOrderBy() {
		getResourcePersistentAttribute().removeSupportingAnnotation(OrderByAnnotation.ANNOTATION_NAME);
	}
	
	public boolean isNoOrdering() {
		return this.isNoOrdering;
	}

	public void setNoOrdering(boolean newNoOrdering) {
		boolean oldNoOrdering = this.isNoOrdering;
		this.isNoOrdering = newNoOrdering;
		if (newNoOrdering) {
			if (getResourceOrderBy() != null) {
				removeResourceOrderBy();
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
			if (getResourceOrderBy() == null) {
				addResourceOrderBy();
			}
			else {
				getResourceOrderBy().setValue(null);
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
			setOrderBy(""); //$NON-NLS-1$
		}
		firePropertyChanged(CUSTOM_ORDERING_PROPERTY, oldCustomOrdering, newCustomOrdering);
	}
	
	protected void setCustomOrdering_(boolean newCustomOrdering) {
		boolean oldCustomOrdering = this.isCustomOrdering;
		this.isCustomOrdering = newCustomOrdering;
		firePropertyChanged(CUSTOM_ORDERING_PROPERTY, oldCustomOrdering, newCustomOrdering);
	}
	
	public FetchType getDefaultFetch() {
		return MultiRelationshipMapping.DEFAULT_FETCH_TYPE;
	}

	public JavaJoinTable getJoinTable() {
		return this.joinTable;
	}

	public boolean joinTableIsSpecified() {
		return this.joinTable.isSpecified();
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
			if (this.getMapKeyResource() != null) {
				if (newMapKey != null) {
					this.getMapKeyResource().setName(newMapKey);
				}
				else {
					getResourcePersistentAttribute().removeSupportingAnnotation(MapKeyAnnotation.ANNOTATION_NAME);				
				}
			}
			else if (newMapKey != null) {
				getResourcePersistentAttribute().addSupportingAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
				getMapKeyResource().setName(newMapKey);
			}
		}
		firePropertyChanged(MultiRelationshipMapping.MAP_KEY_PROPERTY, oldMapKey, newMapKey);
	}

	protected void setMapKey_(String newMapKey) {
		String oldMapKey = this.mapKey;
		this.mapKey = newMapKey;
		firePropertyChanged(MultiRelationshipMapping.MAP_KEY_PROPERTY, oldMapKey, newMapKey);
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
	protected String buildDefaultTargetEntity() {
		if (!this.resourcePersistentAttribute.typeIsContainer()) {
			return null;
		}
		return this.resourcePersistentAttribute.getQualifiedReferenceEntityElementTypeName();
	}
	
	protected abstract boolean mappedByTouches(int pos, CompilationUnit astRoot);

	protected boolean mapKeyNameTouches(int pos, CompilationUnit astRoot) {
		if (getMapKeyResource() != null) {
			return getMapKeyResource().nameTouches(pos, astRoot);
		}
		return false;
	}
	
	protected MapKeyAnnotation getMapKeyResource() {
		return (MapKeyAnnotation) this.resourcePersistentAttribute.getSupportingAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	public Iterator<String> candidateMapKeyNames() {
		return this.allTargetEntityAttributeNames();
	}

	protected Iterator<String> candidateMapKeyNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateMapKeyNames(), filter);
	}

	protected Iterator<String> javaCandidateMapKeyNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateMapKeyNames(filter));
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.joinTable.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.mappedByTouches(pos, astRoot)) {
			return this.javaCandidateMappedByAttributeNames(filter);
		}
		if (this.mapKeyNameTouches(pos, astRoot)) {
			return this.javaCandidateMapKeyNames(filter);
		}
		return null;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyResource();
		if (mapKeyAnnotation != null) {
			this.mapKey = mapKeyAnnotation.getName();
		}
		this.initializeOrderBy();
		this.joinTable.initialize(this.resourcePersistentAttribute);
		this.mappedBy = this.getResourceMappedBy();
	}
	
	protected void initializeOrderBy() {
		OrderByAnnotation orderByAnnotation = this.getResourceOrderBy();
		if (orderByAnnotation != null) {
			this.orderBy = orderByAnnotation.getValue();
			if (orderByAnnotation.getValue() == null) {
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
	protected void update() {
		super.update();
		this.updateMapKey();
		this.updateOrderBy();
		this.joinTable.update(this.resourcePersistentAttribute);
		this.setMappedBy_(this.getResourceMappedBy());
	}	
	
	protected void updateMapKey() {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyResource();
		if (mapKeyAnnotation != null) {
			setMapKey_(mapKeyAnnotation.getName());
		}
		else {
			setMapKey_(null);
		}
	}
	
	protected void updateOrderBy() {
		OrderByAnnotation orderByAnnotation = this.getResourceOrderBy();
		if (orderByAnnotation != null) {
			setOrderBy_(orderByAnnotation.getValue());
			if (orderByAnnotation.getValue() == null) {
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

	protected abstract String getResourceMappedBy();


	// ********** validation **********
	
	public abstract TextRange getMappedByTextRange(CompilationUnit astRoot);
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		if (this.ownerIsEntity() && (this.joinTableIsSpecified() || this.isRelationshipOwner())) {
			this.joinTable.validate(messages, reporter, astRoot);
		}
		if (this.getMappedBy() != null) {
			this.validateMappedBy(messages, astRoot);
		}
	}
	
	protected void validateMappedBy(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.joinTableIsSpecified()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.MAPPING_MAPPED_BY_WITH_JOIN_TABLE,
					this.joinTable, 
					this.joinTable.getValidationTextRange(astRoot)
				)
			);
		}
		
		Entity targetEntity = this.getResolvedTargetEntity();
		
		if (targetEntity == null) {
			// already have validation messages for that
			return;
		}
		
		PersistentAttribute attribute = targetEntity.getPersistentType().resolveAttribute(this.mappedBy);
		
		if (attribute == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,
					new String[] {this.mappedBy}, 
					this, 
					this.getMappedByTextRange(astRoot)
				)
			);
			return;
		}

		AttributeMapping mappedByMapping = attribute.getMapping();
		if ( ! this.mappedByIsValid(mappedByMapping)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.MAPPING_INVALID_MAPPED_BY,
					new String[] {this.mappedBy}, 
					this, 
					this.getMappedByTextRange(astRoot)
				)
			);
			return;
		}
		
		if ((mappedByMapping instanceof NonOwningMapping)
				&& ((NonOwningMapping) mappedByMapping).getMappedBy() != null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES,
					this, 
					this.getMappedByTextRange(astRoot)
				)
			);
		}
	}

}
