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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericOrmOneToOneMapping extends AbstractOrmSingleRelationshipMapping<XmlOneToOne>
	implements OrmOneToOneMapping
{
	
	protected String mappedBy;
	
	protected final List<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns;


	public GenericOrmOneToOneMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.primaryKeyJoinColumns = new ArrayList<OrmPrimaryKeyJoinColumn>();
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmOneToOneMapping(this);
	}

	@Override
	public void initializeFromOrmNonOwningMapping(NonOwningMapping oldMapping) {
		super.initializeFromOrmNonOwningMapping(oldMapping);
		setMappedBy(oldMapping.getMappedBy());
	}
	
	public boolean isRelationshipOwner() {
		return getMappedBy() == null;
	}

	
	
	public ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return new CloneListIterator<OrmPrimaryKeyJoinColumn>(this.primaryKeyJoinColumns);
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.primaryKeyJoinColumns.size();
	}
	
	public OrmPrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index) {
		XmlPrimaryKeyJoinColumn resourcePkJoinColumn = OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumnImpl();
		OrmPrimaryKeyJoinColumn contextPkJoinColumn = buildPrimaryKeyJoinColumn(resourcePkJoinColumn);
		this.primaryKeyJoinColumns.add(index, contextPkJoinColumn);
		this.getAttributeMapping().getPrimaryKeyJoinColumns().add(index, resourcePkJoinColumn);
		this.fireItemAdded(OneToOneMapping.PRIMAY_KEY_JOIN_COLUMNS_LIST, index, contextPkJoinColumn);
		return contextPkJoinColumn;
	}

	protected void addPrimaryKeyJoinColumn(int index, OrmPrimaryKeyJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.primaryKeyJoinColumns, OneToOneMapping.PRIMAY_KEY_JOIN_COLUMNS_LIST);
	}

	public void removePrimaryKeyJoinColumn(PrimaryKeyJoinColumn pkJoinColumn) {
		this.removePrimaryKeyJoinColumn(this.primaryKeyJoinColumns.indexOf(pkJoinColumn));
	}
	
	public void removePrimaryKeyJoinColumn(int index) {
		OrmPrimaryKeyJoinColumn removedPkJoinColumn = this.primaryKeyJoinColumns.remove(index);
		this.getAttributeMapping().getPrimaryKeyJoinColumns().remove(index);
		fireItemRemoved(OneToOneMapping.PRIMAY_KEY_JOIN_COLUMNS_LIST, index, removedPkJoinColumn);
	}

	protected void removePrimaryKeyJoinColumn_(OrmPrimaryKeyJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.primaryKeyJoinColumns, OneToOneMapping.PRIMAY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void movePrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.primaryKeyJoinColumns, targetIndex, sourceIndex);
		this.getAttributeMapping().getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(OneToOneMapping.PRIMAY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	public boolean containsPrimaryKeyJoinColumns() {
		return !this.primaryKeyJoinColumns.isEmpty();
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
	
	public boolean mappedByIsValid(AttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}

	public TextRange getMappedByTextRange() {
		TextRange mappedByTextRange = getAttributeMapping().getMappedByTextRange();
		return mappedByTextRange != null ? mappedByTextRange : getValidationTextRange();
	}


	public int getXmlSequence() {
		return 6;
	}

	public String getKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
	
	public XmlOneToOne addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlOneToOne oneToOne = OrmFactory.eINSTANCE.createXmlOneToOneImpl();
		getPersistentAttribute().initialize(oneToOne);
		typeMapping.getAttributes().getOneToOnes().add(oneToOne);
		return oneToOne;
	}
	
	public void removeFromResourceModel(AbstractXmlTypeMapping typeMapping) {
		typeMapping.getAttributes().getOneToOnes().remove(this.getAttributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}	

	
	//***************** Validation ***********************************
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		if (this.getMappedBy() != null) {
			addMappedByMessages(messages);
		}
	}
	
	@Override
	protected boolean addJoinColumnMessages() {
		if (containsPrimaryKeyJoinColumns() && !containsSpecifiedJoinColumns()) {
			return false;
		}
		return super.addJoinColumnMessages();
	}
	
	protected void addMappedByMessages(List<IMessage> messages) {
		String mappedBy = this.getMappedBy();
		Entity targetEntity = this.getResolvedTargetEntity();
		
		if (targetEntity == null) {
			// already have validation messages for that
			return;
		}
		
		PersistentAttribute attribute = targetEntity.getPersistentType().resolveAttribute(mappedBy);
		
		if (attribute == null) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,
						new String[] {mappedBy}, 
						this, this.getMappedByTextRange())
				);
			return;
		}
		
		if (! this.mappedByIsValid(attribute.getMapping())) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.MAPPING_INVALID_MAPPED_BY,
						new String[] {mappedBy}, 
						this, this.getMappedByTextRange())
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
						this, this.getMappedByTextRange())
				);
		}
	}

	@Override
	public void initialize(XmlOneToOne oneToOne) {
		super.initialize(oneToOne);
		this.mappedBy = oneToOne.getMappedBy();
		this.initializePrimaryKeyJoinColumns(oneToOne);
	}
	
	protected void initializePrimaryKeyJoinColumns(XmlOneToOne oneToOne) {
		if (oneToOne == null) {
			return;
		}
		for (XmlPrimaryKeyJoinColumn resourcePkJoinColumn : oneToOne.getPrimaryKeyJoinColumns()) {
			this.primaryKeyJoinColumns.add(buildPrimaryKeyJoinColumn(resourcePkJoinColumn));
		}
	}
	
	protected OrmPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn resourcePkJoinColumn) {
		return getJpaFactory().buildOrmPrimaryKeyJoinColumn(this, new JoinColumnOwner(), resourcePkJoinColumn);
	}	

	
	@Override
	public void update(XmlOneToOne oneToOne) {
		super.update(oneToOne);
		this.setMappedBy_(oneToOne.getMappedBy());
		this.updatePrimaryKeyJoinColumns(oneToOne);
	}
	
	
	protected void updatePrimaryKeyJoinColumns(XmlOneToOne oneToOne) {
		ListIterator<OrmPrimaryKeyJoinColumn> pkJoinColumns = primaryKeyJoinColumns();
		ListIterator<XmlPrimaryKeyJoinColumn> resourcePkJoinColumns = EmptyListIterator.instance();
		if (oneToOne != null) {
			resourcePkJoinColumns = new CloneListIterator<XmlPrimaryKeyJoinColumn>(oneToOne.getPrimaryKeyJoinColumns());//prevent ConcurrentModificiationException
		}
		
		while (pkJoinColumns.hasNext()) {
			OrmPrimaryKeyJoinColumn pkJoinColumn = pkJoinColumns.next();
			if (resourcePkJoinColumns.hasNext()) {
				pkJoinColumn.update(resourcePkJoinColumns.next());
			}
			else {
				removePrimaryKeyJoinColumn_(pkJoinColumn);
			}
		}
		
		while (resourcePkJoinColumns.hasNext()) {
			addPrimaryKeyJoinColumn(primaryKeyJoinColumnsSize(), buildPrimaryKeyJoinColumn(resourcePkJoinColumns.next()));
		}
	}

}
