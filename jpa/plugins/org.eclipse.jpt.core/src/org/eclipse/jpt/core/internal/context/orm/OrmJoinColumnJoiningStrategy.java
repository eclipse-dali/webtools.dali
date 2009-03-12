/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmJoinColumnJoiningStrategy extends AbstractXmlContextNode
	implements JoinColumnJoiningStrategy
{
	protected XmlJoinColumnsMapping resource;
	
	protected OrmJoinColumn defaultJoinColumn;
	
	protected final List<OrmJoinColumn> specifiedJoinColumns;
	
	
	public OrmJoinColumnJoiningStrategy(
			OrmJoinColumnEnabledRelationshipReference parent,
			XmlJoinColumnsMapping resource) {
		super(parent);
		this.resource = resource;
		this.specifiedJoinColumns = new ArrayList<OrmJoinColumn>();
		this.initializeSpecifiedJoinColumns();
		this.initializeDefaultJoinColumn();
	}
	
	
	protected void initializeSpecifiedJoinColumns() {
		if (this.resource != null) {
			for (XmlJoinColumn resourceJoinColumn : this.resource.getJoinColumns()) {
				this.specifiedJoinColumns.add(buildJoinColumn(resourceJoinColumn));
			}
		}
	}
	
	protected void initializeDefaultJoinColumn() {
		if (mayHaveDefaultJoinColumn()) {
			this.defaultJoinColumn = this.buildJoinColumn(null);
		}
	}
	
	protected OrmJoinColumn buildJoinColumn(XmlJoinColumn resourceJoinColumn) {
		return this.getJpaFactory().buildOrmJoinColumn(this, new JoinColumnOwner(), resourceJoinColumn);
	}
	
	@Override
	public OrmJoinColumnEnabledRelationshipReference getParent() {
		return (OrmJoinColumnEnabledRelationshipReference) super.getParent();
	}
	
	public OrmJoinColumnEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	public OrmRelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}
	
	public void addStrategy() {
		if (specifiedJoinColumnsSize() == 0) {
			addSpecifiedJoinColumn(0);
		}
	}
	
	public void removeStrategy() {
		for (JoinColumn each : CollectionTools.iterable(specifiedJoinColumns())) {
			removeSpecifiedJoinColumn(each);
		}
	}
	
	
	// **************** join columns *******************************************
	
	public ListIterator<OrmJoinColumn> joinColumns() {
		return this.hasSpecifiedJoinColumns() ? 
			this.specifiedJoinColumns() : this.defaultJoinColumns();
	}
	
	public int joinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? 
			this.specifiedJoinColumnsSize() : this.defaultJoinColumnsSize();
	}
	
	
	// **************** default join column ************************************
	
	public OrmJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}
	
	protected void setDefaultJoinColumn(OrmJoinColumn joinColumn) {
		OrmJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
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
	
	
	// **************** specified join columns *********************************
	
	public ListIterator<OrmJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<OrmJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return ! this.specifiedJoinColumns.isEmpty();
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
		this.resource.getJoinColumns().add(index, resourceJoinColumn);
		this.fireItemAdded(SPECIFIED_JOIN_COLUMNS_LIST, index, contextJoinColumn);
		if (oldDefaultJoinColumn != null) {
			this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, oldDefaultJoinColumn, null);
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
		this.resource.getJoinColumns().remove(index);
		this.fireItemRemoved(SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, null, this.defaultJoinColumn);		
		}
	}

	protected void removeSpecifiedJoinColumn_(OrmJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.resource.getJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	
	// **************** resource => context ************************************
	
	public void update() {
		updateSpecifiedJoinColumns();
		updateDefaultJoinColumn();
	}
	
	protected void updateSpecifiedJoinColumns() {
		// make a copy of the XML join columns (to prevent ConcurrentModificationException)
		Iterator<XmlJoinColumn> xmlJoinColumns = 
			new CloneIterator<XmlJoinColumn>(this.resource.getJoinColumns());
		
		for (Iterator<OrmJoinColumn> contextJoinColumns = this.specifiedJoinColumns(); 
				contextJoinColumns.hasNext(); ) {
			OrmJoinColumn contextJoinColumn = contextJoinColumns.next();
			if (xmlJoinColumns.hasNext()) {
				contextJoinColumn.update(xmlJoinColumns.next());
			}
			else {
				removeSpecifiedJoinColumn_(contextJoinColumn);
			}
		}
		
		while (xmlJoinColumns.hasNext()) {
			addSpecifiedJoinColumn(buildJoinColumn(xmlJoinColumns.next()));
		}
	}
	
	protected void updateDefaultJoinColumn() {
		if (mayHaveDefaultJoinColumn()) {
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(null));
			} else {
				this.defaultJoinColumn.update(null);
			}
		} else {
			this.setDefaultJoinColumn(null);
		}
	}
	
	protected boolean mayHaveDefaultJoinColumn() {
		return getRelationshipReference().mayHaveDefaultJoinColumn()
			&& ! hasSpecifiedJoinColumns();
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (getRelationshipMapping().shouldValidateAgainstDatabase()) {
			for (Iterator<OrmJoinColumn> stream = this.joinColumns(); stream.hasNext(); ) {
				this.validateJoinColumn(stream.next(), messages);
			}
		}
	}
	
	protected void validateJoinColumn(OrmJoinColumn joinColumn, List<IMessage> messages) {
		if (getRelationshipMapping().getTypeMapping().tableNameIsInvalid(joinColumn.getTable())) {
			if (this.getRelationshipMapping().getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_TABLE,
						new String[] {getRelationshipMapping().getName(), joinColumn.getTable(), joinColumn.getName()},
						joinColumn,
						joinColumn.getTableTextRange()
					)
				);
			} 
			else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_TABLE,
						new String[] {joinColumn.getTable(), joinColumn.getName()}, 
						joinColumn, 
						joinColumn.getTableTextRange()
					)
				);
			}
			return;
		}
		
		if ( ! joinColumn.isResolved()) {
			if (getRelationshipMapping().getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_NAME,
						new String[] {getRelationshipMapping().getName(), joinColumn.getName()}, 
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
			if (getRelationshipMapping().getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {getRelationshipMapping().getName(), joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
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
	
	public TextRange getValidationTextRange() {
		return this.getRelationshipReference().getValidationTextRange();
	}
	
	
	public class JoinColumnOwner implements OrmJoinColumn.Owner 
	{
		public JoinColumnOwner() {
			super();
		}
		
		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return getTypeMapping().getPrimaryTableName();
		}
		
		public Entity getTargetEntity() {
			return getRelationshipMapping().getResolvedTargetEntity();
		}
		
		public String getAttributeName() {
			return getRelationshipMapping().getName();
		}
		
		public RelationshipMapping getRelationshipMapping() {
			return OrmJoinColumnJoiningStrategy.this.getRelationshipMapping();
		}
		
		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}
		
		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public TypeMapping getTypeMapping() {
			return getRelationshipMapping().getTypeMapping();
		}
		
		public Table getDbTable(String tableName) {
			return getTypeMapping().getDbTable(tableName);
		}
		
		public Table getReferencedColumnDbTable() {
			Entity targetEntity = getTargetEntity();
			return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return OrmJoinColumnJoiningStrategy.this.defaultJoinColumn == joinColumn;
		}
		
		public String getDefaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}

		public int joinColumnsSize() {
			return OrmJoinColumnJoiningStrategy.this.joinColumnsSize();
		}
		
		public TextRange getValidationTextRange() {
			return OrmJoinColumnJoiningStrategy.this.getValidationTextRange();
		}
	}
}
