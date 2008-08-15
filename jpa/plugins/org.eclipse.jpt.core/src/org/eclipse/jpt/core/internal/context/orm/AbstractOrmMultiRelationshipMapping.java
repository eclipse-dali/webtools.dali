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
	public void initializeFromXmlMulitRelationshipMapping(OrmMultiRelationshipMapping oldMapping) {
		super.initializeFromXmlMulitRelationshipMapping(oldMapping);
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
		getAttributeMapping().setMappedBy(newMappedBy);
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
		getAttributeMapping().setOrderBy(newOrderBy);
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
			getAttributeMapping().setOrderBy(null);
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
			getAttributeMapping().setOrderBy("");
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
	
	public OrmJoinTable getJoinTable() {
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
		return getAttributeMapping().getMapKey();
	}
	
	protected void removeResourceMapKey() {
		getAttributeMapping().setMapKey(null);
	}
	
	protected void addResourceMapKey() {
		getAttributeMapping().setMapKey(OrmFactory.eINSTANCE.createMapKeyImpl());
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
		TextRange mappedByTextRange = getAttributeMapping().getMappedByTextRange();
		return mappedByTextRange != null ? mappedByTextRange : getValidationTextRange();
	}
	
	@Override
	public void initialize(T multiRelationshipMapping) {
		super.initialize(multiRelationshipMapping);
		this.mappedBy = multiRelationshipMapping.getMappedBy();
		this.mapKey = this.mapKey(multiRelationshipMapping);
		this.orderBy = this.orderBy(multiRelationshipMapping);
		if (this.orderBy == null) { 
			this.isNoOrdering = true;
		}
		else {
			this.isCustomOrdering = true;
		}
		this.joinTable.initialize(multiRelationshipMapping);
	}
	
	@Override
	public void update(T multiRelationshipMapping) {
		super.update(multiRelationshipMapping);
		this.setMappedBy_(multiRelationshipMapping.getMappedBy());
		this.setMapKey_(this.mapKey(multiRelationshipMapping));
		this.setOrderBy_(this.orderBy(multiRelationshipMapping));
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
		this.joinTable.update(multiRelationshipMapping);
	}
	
	protected String mapKey(T multiRelationshipMapping) {
		return multiRelationshipMapping.getMapKey() == null ? null : multiRelationshipMapping.getMapKey().getName();
	}
	
	protected String orderBy(T multiRelationshipMapping) {
		return multiRelationshipMapping.getOrderBy();
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
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		if (entityOwned() && (this.isJoinTableSpecified() || isRelationshipOwner())) {
			getJoinTable().addToMessages(messages);
		}
		if (getMappedBy() != null) {
			addMappedByMessages(messages);
		}
	}
	
	protected void addMappedByMessages(List<IMessage> messages) {	
		if (isJoinTableSpecified()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.MAPPING_MAPPED_BY_WITH_JOIN_TABLE,
						getJoinTable(),
						getJoinTable().getValidationTextRange())
				);
		}
		
		if (getResolvedTargetEntity() == null) {
			// already have validation messages for that
			return;
		}
		
		PersistentAttribute attribute = getResolvedTargetEntity().getPersistentType().resolveAttribute(getMappedBy());
		
		if (attribute == null) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,
						new String[] {getMappedBy()}, 
						this,
						getMappedByTextRange())
				);
			return;
		}
		
		if (! mappedByIsValid(attribute.getMapping())) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.MAPPING_INVALID_MAPPED_BY,
						new String[] {getMappedBy()}, 
						this,
						getMappedByTextRange())
				);
			return;
		}
		
		NonOwningMapping mappedByMapping;
		try {
			mappedByMapping = (NonOwningMapping) attribute.getMapping();
		} catch (ClassCastException cce) {
			// there is no error then
			return;
		}
		
		if (mappedByMapping.getMappedBy() != null) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES,
						this,
						getMappedByTextRange())
				);
		}
	}
}
