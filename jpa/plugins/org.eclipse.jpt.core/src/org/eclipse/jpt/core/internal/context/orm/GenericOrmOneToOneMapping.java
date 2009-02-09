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
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public class GenericOrmOneToOneMapping
	extends AbstractOrmSingleRelationshipMapping<XmlOneToOne>
	implements OrmOneToOneMapping
{
	
	protected String mappedBy;
	
	protected final List<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns;


	public GenericOrmOneToOneMapping(OrmPersistentAttribute parent, XmlOneToOne resourceMapping) {
		super(parent, resourceMapping);
		this.mappedBy = this.getResourceMappedBy();
		this.primaryKeyJoinColumns = new ArrayList<OrmPrimaryKeyJoinColumn>();
		this.initializePrimaryKeyJoinColumns();
	}


	// ********** mapped by **********

	public String getMappedBy() {
		return this.mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		String old = this.mappedBy;
		this.mappedBy = mappedBy;
		this.resourceAttributeMapping.setMappedBy(mappedBy);
		this.firePropertyChanged(MAPPED_BY_PROPERTY, old, mappedBy);
	}
	
	protected void setMappedBy_(String mappedBy) {
		String old = this.mappedBy;
		this.mappedBy = mappedBy;
		this.firePropertyChanged(MAPPED_BY_PROPERTY, old, mappedBy);
	}
	
	public boolean mappedByIsValid(AttributeMapping mappedByMapping) {
		return (mappedByMapping.getKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}

	public TextRange getMappedByTextRange() {
		TextRange mappedByTextRange = this.resourceAttributeMapping.getMappedByTextRange();
		return mappedByTextRange != null ? mappedByTextRange : getValidationTextRange();
	}


	// ********** primary key join columns **********

	public ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return new CloneListIterator<OrmPrimaryKeyJoinColumn>(this.primaryKeyJoinColumns);
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.primaryKeyJoinColumns.size();
	}
	
	public OrmPrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index) {
		XmlPrimaryKeyJoinColumn resourcePkJoinColumn = OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumnImpl();
		OrmPrimaryKeyJoinColumn contextPkJoinColumn = this.buildPrimaryKeyJoinColumn(resourcePkJoinColumn);
		this.primaryKeyJoinColumns.add(index, contextPkJoinColumn);
		this.resourceAttributeMapping.getPrimaryKeyJoinColumns().add(index, resourcePkJoinColumn);
		this.fireItemAdded(PRIMARY_KEY_JOIN_COLUMNS_LIST, index, contextPkJoinColumn);
		return contextPkJoinColumn;
	}

	protected void addPrimaryKeyJoinColumn(int index, OrmPrimaryKeyJoinColumn joinColumn) {
		this.addItemToList(index, joinColumn, this.primaryKeyJoinColumns, PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected void addPrimaryKeyJoinColumn(OrmPrimaryKeyJoinColumn joinColumn) {
		this.addPrimaryKeyJoinColumn(this.primaryKeyJoinColumns.size(), joinColumn);
	}

	public void removePrimaryKeyJoinColumn(PrimaryKeyJoinColumn pkJoinColumn) {
		this.removePrimaryKeyJoinColumn(this.primaryKeyJoinColumns.indexOf(pkJoinColumn));
	}
	
	public void removePrimaryKeyJoinColumn(int index) {
		OrmPrimaryKeyJoinColumn removedPkJoinColumn = this.primaryKeyJoinColumns.remove(index);
		this.resourceAttributeMapping.getPrimaryKeyJoinColumns().remove(index);
		this.fireItemRemoved(PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPkJoinColumn);
	}

	protected void removePrimaryKeyJoinColumn_(OrmPrimaryKeyJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.primaryKeyJoinColumns, PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void movePrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.primaryKeyJoinColumns, targetIndex, sourceIndex);
		this.resourceAttributeMapping.getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	public boolean containsPrimaryKeyJoinColumns() {
		return ! this.primaryKeyJoinColumns.isEmpty();
	}


	// ********** AttributeMapping implementation **********

	public String getKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}


	// ********** OrmAttributeMapping implementation **********

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmOneToOneMapping(this);
	}

	public int getXmlSequence() {
		return 60;
	}

	@Override
	public void initializeFromOrmNonOwningMapping(NonOwningMapping oldMapping) {
		super.initializeFromOrmNonOwningMapping(oldMapping);
		setMappedBy(oldMapping.getMappedBy());
	}
	
	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
	
	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getOneToOnes().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getOneToOnes().remove(this.resourceAttributeMapping);
	}	


	// ********** RelationshipMapping implementation **********

	public boolean isRelationshipOwner() {
		return getMappedBy() == null;
	}


	// ********** resource => context **********
	
	protected void initializePrimaryKeyJoinColumns() {
		if (this.resourceAttributeMapping != null) {
			for (XmlPrimaryKeyJoinColumn resourcePkJoinColumn : this.resourceAttributeMapping.getPrimaryKeyJoinColumns()) {
				this.primaryKeyJoinColumns.add(buildPrimaryKeyJoinColumn(resourcePkJoinColumn));
			}
		}
	}
	
	protected OrmPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn resourcePkJoinColumn) {
		return getJpaFactory().buildOrmPrimaryKeyJoinColumn(this, new JoinColumnOwner(), resourcePkJoinColumn);
	}	

	
	@Override
	public void update() {
		super.update();
		this.setMappedBy_(this.getResourceMappedBy());
		this.updatePrimaryKeyJoinColumns();
	}
	
	protected String getResourceMappedBy() {
		return this.resourceAttributeMapping.getMappedBy();
	}
	
	protected void updatePrimaryKeyJoinColumns() {
		// make a copy of the XML PK join columns (to prevent ConcurrentModificationException)
		Iterator<XmlPrimaryKeyJoinColumn> xmlPkJoinColumns = new CloneIterator<XmlPrimaryKeyJoinColumn>(this.resourceAttributeMapping.getPrimaryKeyJoinColumns());
		
		for (Iterator<OrmPrimaryKeyJoinColumn> contextPkJoinColumns = primaryKeyJoinColumns(); contextPkJoinColumns.hasNext(); ) {
			OrmPrimaryKeyJoinColumn contextPkJoinColumn = contextPkJoinColumns.next();
			if (xmlPkJoinColumns.hasNext()) {
				contextPkJoinColumn.update(xmlPkJoinColumns.next());
			}
			else {
				removePrimaryKeyJoinColumn_(contextPkJoinColumn);
			}
		}
		
		while (xmlPkJoinColumns.hasNext()) {
			addPrimaryKeyJoinColumn(buildPrimaryKeyJoinColumn(xmlPkJoinColumns.next()));
		}
	}


	// ********** Validation **********

	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		if (this.mappedBy != null) {
			this.validateMappedBy(messages);
		}
	}
	
	@Override
	protected void validateJoinColumns(List<IMessage> messages) {
		if (this.primaryKeyJoinColumns.isEmpty() || this.containsSpecifiedJoinColumns()) {
			super.validateJoinColumns(messages);
		}
	}

	protected void validateMappedBy(List<IMessage> messages) {
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
