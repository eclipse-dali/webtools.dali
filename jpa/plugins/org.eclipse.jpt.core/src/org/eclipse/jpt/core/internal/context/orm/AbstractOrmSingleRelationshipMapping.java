/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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

import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmSingleRelationshipMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSingleRelationshipMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public abstract class AbstractOrmSingleRelationshipMapping<T extends XmlSingleRelationshipMapping>
	extends AbstractOrmRelationshipMapping<T>
	implements OrmSingleRelationshipMapping
{
	
	protected final List<OrmJoinColumn> specifiedJoinColumns;
	protected OrmJoinColumn defaultJoinColumn;

	protected Boolean specifiedOptional;


	protected AbstractOrmSingleRelationshipMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.specifiedJoinColumns = new ArrayList<OrmJoinColumn>();
	}
	

	// ********** join columns **********

	public ListIterator<OrmJoinColumn> joinColumns() {
		return this.containsSpecifiedJoinColumns() ? this.specifiedJoinColumns() : this.defaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.containsSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.defaultJoinColumnsSize();
	}
	
	public ListIterator<OrmJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<OrmJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.specifiedJoinColumns.isEmpty();
	}

	public OrmJoinColumn addSpecifiedJoinColumn(int index) {
		OrmJoinColumn oldDefaultJoinColumn = this.defaultJoinColumn;
		if (oldDefaultJoinColumn != null) {
			//null the default join column now if one already exists.
			//if one does not exist, there is already a specified join column.
			//Remove it now so that it doesn't get removed during an update and
			//cause change notifications to be sent to the UI in the wrong order
			this.defaultJoinColumn = null;
		}
		XmlJoinColumn resourceJoinColumn = OrmFactory.eINSTANCE.createXmlJoinColumnImpl();
		OrmJoinColumn contextJoinColumn = this.buildJoinColumn(resourceJoinColumn);
		this.specifiedJoinColumns.add(index, contextJoinColumn);
		this.getAttributeMapping().getJoinColumns().add(index, resourceJoinColumn);
		this.fireItemAdded(SPECIFIED_JOIN_COLUMNS_LIST, index, contextJoinColumn);
		if (oldDefaultJoinColumn != null) {
			this.firePropertyChanged(DEFAULT_JOIN_COLUMN, oldDefaultJoinColumn, null);
		}
		return contextJoinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, OrmJoinColumn joinColumn) {
		this.addItemToList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected void addSpecifiedJoinColumn(OrmJoinColumn joinColumn) {
		this.addSpecifiedJoinColumn(this.specifiedJoinColumns.size(), joinColumn);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		OrmJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		if (this.specifiedJoinColumns.isEmpty()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultJoinColumn = this.buildJoinColumn(null);
		}
		this.getAttributeMapping().getJoinColumns().remove(index);
		this.fireItemRemoved(SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(DEFAULT_JOIN_COLUMN, null, this.defaultJoinColumn);		
		}
	}

	protected void removeSpecifiedJoinColumn_(OrmJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.getAttributeMapping().getJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}

	public OrmJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}
	
	protected void setDefaultJoinColumn(OrmJoinColumn joinColumn) {
		OrmJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN, old, joinColumn);
	}

	protected ListIterator<OrmJoinColumn> defaultJoinColumns() {
		if (this.defaultJoinColumn != null) {
			return new SingleElementListIterator<OrmJoinColumn>(this.defaultJoinColumn);
		}
		return EmptyListIterator.instance();
	}
	
	protected int defaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}
	

	// ********** optional **********

	public Boolean getOptional() {
		return (this.specifiedOptional != null) ? this.specifiedOptional : this.getDefaultOptional();
	}
	
	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.getAttributeMapping().setOptional(optional);
		this.firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}
	
	protected void setSpecifiedOptional_(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}
	
	public Boolean getDefaultOptional() {
		return Nullable.DEFAULT_OPTIONAL;
	}


	// ********** resource => context **********

	@Override
	public void initialize(T singleRelationshipMapping) {
		super.initialize(singleRelationshipMapping);
		this.specifiedOptional = singleRelationshipMapping.getOptional();
		//TODO defaultOptional
		this.initializeSpecifiedJoinColumns(singleRelationshipMapping);
		this.initializeDefaultJoinColumn();
	}
	
	protected void initializeSpecifiedJoinColumns(T singleRelationshipMapping) {
		if (singleRelationshipMapping != null) {
			for (XmlJoinColumn resourceJoinColumn : singleRelationshipMapping.getJoinColumns()) {
				this.specifiedJoinColumns.add(buildJoinColumn(resourceJoinColumn));
			}
		}
	}
	
	protected void initializeDefaultJoinColumn() {
		if (this.shouldBuildDefaultJoinColumn()) {
			this.defaultJoinColumn = this.buildJoinColumn(null);
		}
	}	
	
	protected boolean shouldBuildDefaultJoinColumn() {
		return ! this.containsSpecifiedJoinColumns() && this.isRelationshipOwner();
	}
	
	protected OrmJoinColumn buildJoinColumn(XmlJoinColumn resourceJoinColumn) {
		return this.getJpaFactory().buildOrmJoinColumn(this, new JoinColumnOwner(), resourceJoinColumn);
	}	

	@Override
	public void update(T singleRelationshipMapping) {
		super.update(singleRelationshipMapping);
		this.setSpecifiedOptional_(singleRelationshipMapping.getOptional());
		this.updateSpecifiedJoinColumns(singleRelationshipMapping);
		this.updateDefaultJoinColumn();
	}
	
	protected void updateSpecifiedJoinColumns(T singleRelationshipMapping) {
		ListIterator<OrmJoinColumn> contextJoinColumns = specifiedJoinColumns();
		ListIterator<XmlJoinColumn> resourceJoinColumns = EmptyListIterator.instance();
		if (singleRelationshipMapping != null) {
			resourceJoinColumns = new CloneListIterator<XmlJoinColumn>(singleRelationshipMapping.getJoinColumns());//prevent ConcurrentModificiationException
		}
		
		while (contextJoinColumns.hasNext()) {
			OrmJoinColumn contextJoinColumn = contextJoinColumns.next();
			if (resourceJoinColumns.hasNext()) {
				contextJoinColumn.update(resourceJoinColumns.next());
			}
			else {
				removeSpecifiedJoinColumn_(contextJoinColumn);
			}
		}
		
		while (resourceJoinColumns.hasNext()) {
			addSpecifiedJoinColumn(buildJoinColumn(resourceJoinColumns.next()));
		}
	}
	
	protected void updateDefaultJoinColumn() {
		if (this.shouldBuildDefaultJoinColumn()) {
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(null));
			} else {
				this.defaultJoinColumn.update(null);
			}
		} else {
			this.setDefaultJoinColumn(null);
		}
	}	


	// ********** AbstractOrmRelationshipMapping implementation **********

	/**
	 * eliminate any "container" types
	 */
	@Override
	protected String defaultTargetEntity(JavaResourcePersistentAttribute persistentAttributeResource) {
		if (persistentAttributeResource.typeIsContainer()) {
			return null;
		}
		return persistentAttributeResource.getQualifiedReferenceEntityTypeName();
	}


	// ********** AbstractOrmAttributeMapping implementation **********

	@Override
	public void initializeFromOrmSingleRelationshipMapping(OrmSingleRelationshipMapping oldMapping) {
		super.initializeFromOrmSingleRelationshipMapping(oldMapping);
		int index = 0;
		for (JoinColumn joinColumn : CollectionTools.iterable(oldMapping.specifiedJoinColumns())) {
			OrmJoinColumn newJoinColumn = addSpecifiedJoinColumn(index++);
			newJoinColumn.initializeFrom(joinColumn);
		}
	}


	// ********** Fetchable implementation **********

	public FetchType getDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		if (this.connectionProfileIsActive()) {
			this.validateJoinColumns(messages);
		}
	}
	
	//bug 192287 - do not want joinColumn validation errors on the non-owning side
	//of a bidirectional relationship.  This is a low risk fix for RC3, but a better
	//solution would be to not have the default joinColumns on the non-owning side.
	//This would fix another bug that we show default joinColumns in this situation.
	protected void validateJoinColumns(List<IMessage> messages) {
		if (this.ownerIsEntity() && this.isRelationshipOwner()) {
			for (Iterator<OrmJoinColumn> stream = this.joinColumns(); stream.hasNext(); ) {
				this.validateJoinColumn(stream.next(), messages);
			}
		}
	}

	protected void validateJoinColumn(OrmJoinColumn joinColumn, List<IMessage> messages) {
		String tableName = joinColumn.getTable();
		if (this.getTypeMapping().tableNameIsInvalid(tableName)) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_TABLE,
						new String[] {this.getName(), tableName, joinColumn.getName()},
						joinColumn,
						joinColumn.getTableTextRange()
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_TABLE,
						new String[] {tableName, joinColumn.getName()}, 
						joinColumn, 
						joinColumn.getTableTextRange()
					)
				);
			}
			return;
		}
		
		if ( ! joinColumn.isResolved()) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_NAME,
						new String[] {this.getName(), joinColumn.getName()}, 
						joinColumn, 
						joinColumn.getNameTextRange()
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getName()}, 
						joinColumn, 
						joinColumn.getNameTextRange()
					)
				);
			}
		}
		
		if ( ! joinColumn.isReferencedColumnResolved()) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {this.getName(), joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
						joinColumn, 
						joinColumn.getReferencedColumnNameTextRange()
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
						joinColumn, 
						joinColumn.getReferencedColumnNameTextRange()
					)
				);
			}
		}
	}


	// ********** join column owner adapter **********

	public class JoinColumnOwner implements OrmJoinColumn.Owner {

		public JoinColumnOwner() {
			super();
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return AbstractOrmSingleRelationshipMapping.this.getTypeMapping().getPrimaryTableName();
		}

		public Entity getTargetEntity() {
			return AbstractOrmSingleRelationshipMapping.this.getResolvedTargetEntity();
		}

		public String getAttributeName() {
			return AbstractOrmSingleRelationshipMapping.this.getName();
		}

		public RelationshipMapping getRelationshipMapping() {
			return AbstractOrmSingleRelationshipMapping.this;
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractOrmSingleRelationshipMapping.this.getTypeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public TypeMapping getTypeMapping() {
			return AbstractOrmSingleRelationshipMapping.this.getTypeMapping();
		}

		public Table getDbTable(String tableName) {
			return getTypeMapping().getDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			Entity targetEntity = getTargetEntity();
			return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return AbstractOrmSingleRelationshipMapping.this.defaultJoinColumn == joinColumn;
		}
		
		public String getDefaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}

		public int joinColumnsSize() {
			return AbstractOrmSingleRelationshipMapping.this.joinColumnsSize();
		}
		
		public TextRange getValidationTextRange() {
			return AbstractOrmSingleRelationshipMapping.this.getValidationTextRange();
		}

	}

}
