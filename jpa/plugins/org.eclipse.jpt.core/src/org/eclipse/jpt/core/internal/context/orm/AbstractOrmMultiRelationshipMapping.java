/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.MultiRelationshipMapping;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmMultiRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public abstract class AbstractOrmMultiRelationshipMapping<T extends XmlMultiRelationshipMapping>
	extends AbstractOrmRelationshipMapping<T>
	implements OrmMultiRelationshipMapping
{

	protected String mappedBy;

	protected String orderBy;//TODO change this to defaultOrderBy and specifiedOrderBy?

	protected boolean isNoOrdering;

	protected boolean isPkOrdering;

	protected boolean isCustomOrdering;

	protected final OrmJoinTable joinTable;

	protected String mapKey;

	protected AbstractOrmMultiRelationshipMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.joinTable = getJpaFactory().buildOrmJoinTable(this);
	}

	@Override
	public void initializeFromOrmMulitRelationshipMapping(OrmMultiRelationshipMapping oldMapping) {
		super.initializeFromOrmMulitRelationshipMapping(oldMapping);
		getJoinTable().initializeFrom(oldMapping.getJoinTable());
	}
	
	public boolean isRelationshipOwner() {
		return getMappedBy() == null;
	}

	public FetchType getDefaultFetch() {
		return MultiRelationshipMapping.DEFAULT_FETCH_TYPE;
	}
	
	public String getMappedBy() {
		return this.mappedBy;
	}

	public void setMappedBy(String newMappedBy) {
		String oldMappedBy = this.mappedBy;
		this.mappedBy = newMappedBy;
		this.resourceAttributeMapping.setMappedBy(newMappedBy);
		firePropertyChanged(MAPPED_BY_PROPERTY, oldMappedBy, newMappedBy);
	}
	
	protected void setMappedBy_(String newMappedBy) {
		String oldMappedBy = this.mappedBy;
		this.mappedBy = newMappedBy;
		firePropertyChanged(MAPPED_BY_PROPERTY, oldMappedBy, newMappedBy);
	}

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
	
	public OrmJoinTable getJoinTable() {
		return this.joinTable;
	}

	public boolean joinTableIsSpecified() {
		return getJoinTable().isSpecified();
	}

	public String getMapKey() {
		return this.mapKey;
	}

	public void setMapKey(String newMapKey) {
		String oldMapKey = this.mapKey;
		this.mapKey = newMapKey;
		if (oldMapKey != newMapKey) {
			if (this.getResourceMapKey() != null) {
				this.getResourceMapKey().setName(newMapKey);						
				if (this.getResourceMapKey().isAllFeaturesUnset()) {
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

//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		super.refreshDefaults(defaultsContext);
//		// TODO
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
//
//	//TODO copied from JavaMultiRelationshipMapping
//	/**
//	 * extract the element type from the specified container signature and
//	 * convert it into a reference entity type name;
//	 * return null if the type is not a valid reference entity type (e.g. it's
//	 * another container or an array or a primitive or other Basic type)
//	 */
//	@Override
//	protected String javaDefaultTargetEntity(ITypeBinding typeBinding) {
//		String typeName = super.javaDefaultTargetEntity(typeBinding);
//		return JavaRelationshipMapping.typeNamedIsContainer(typeName) ? this.javaDefaultTargetEntityFromContainer(typeBinding) : null;
//	}
//
//	protected String javaDefaultTargetEntityFromContainer(ITypeBinding typeBinding) {
//		return JavaMultiRelationshipMapping.javaDefaultTargetEntityFromContainer(typeBinding);
//	}
	
	public TextRange getMappedByTextRange() {
		TextRange mappedByTextRange = this.resourceAttributeMapping.getMappedByTextRange();
		return mappedByTextRange != null ? mappedByTextRange : getValidationTextRange();
	}
	
	@Override
	public void initialize(XmlAttributeMapping attributeMapping) {
		super.initialize(attributeMapping);
		this.mappedBy = this.resourceAttributeMapping.getMappedBy();
		this.mapKey = this.mapKey();
		this.orderBy = this.orderBy();
		if (this.orderBy == null) { 
			this.isNoOrdering = true;
		}
		else {
			this.isCustomOrdering = true;
		}
		this.joinTable.initialize(this.resourceAttributeMapping);
	}
	
	@Override
	public void update() {
		super.update();
		this.setMappedBy_(this.resourceAttributeMapping.getMappedBy());
		this.setMapKey_(this.mapKey());
		this.setOrderBy_(this.orderBy());
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
		this.joinTable.update();
	}
	
	protected String mapKey() {
		return this.resourceAttributeMapping.getMapKey() == null ? null : this.resourceAttributeMapping.getMapKey().getName();
	}
	
	protected String orderBy() {
		return this.resourceAttributeMapping.getOrderBy();
	}
	
	@Override
	protected String defaultTargetEntity(JavaResourcePersistentAttribute jrpa) {
		if (!jrpa.typeIsContainer()) {
			return null;
		}
		return jrpa.getQualifiedReferenceEntityElementTypeName();
	}

	//****************** validation ******************8
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		if (this.ownerIsEntity() && (this.joinTableIsSpecified() || this.isRelationshipOwner())) {
			this.joinTable.validate(messages);
		}
		if (this.mappedBy != null) {
			this.validateMappedBy(messages);
		}
	}
	
	protected void validateMappedBy(List<IMessage> messages) {	
		if (this.joinTableIsSpecified()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.MAPPING_MAPPED_BY_WITH_JOIN_TABLE,
					this.joinTable,
					this.joinTable.getValidationTextRange()
				)
			);
		}
		Entity targetEntity = this.getResolvedTargetEntity();
		if (targetEntity == null) {
			return;  // validated elsewhere
		}
		
		PersistentAttribute attribute = targetEntity.getPersistentType().resolveAttribute(this.mappedBy);
		
		if (attribute == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,
					new String[] {this.mappedBy}, 
					this,
					this.getMappedByTextRange()
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
					this.getMappedByTextRange()
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
					this.getMappedByTextRange()
				)
			);
		}
	}
}
