/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmJoinColumnJoiningStrategy 
	extends AbstractXmlContextNode
	implements OrmJoinColumnJoiningStrategy
{
	protected OrmJoinColumn defaultJoinColumn;
	
	protected final Vector<OrmJoinColumn> specifiedJoinColumns = new Vector<OrmJoinColumn>();

	protected final OrmJoinColumn.Owner joinColumnOwner;
	
	protected XmlJoinColumnsMapping resource;
	
	
	protected AbstractOrmJoinColumnJoiningStrategy(
			JoinColumnEnabledRelationshipReference parent,
			XmlJoinColumnsMapping resource) {
		super(parent);
		this.resource = resource;
		this.joinColumnOwner = this.buildJoinColumnOwner();
		this.initializeSpecifiedJoinColumns();
		this.initializeDefaultJoinColumn();
	}
	
	public void initializeFrom(JoinColumnJoiningStrategy oldStrategy) {
		for (JoinColumn joinColumn : CollectionTools.iterable(oldStrategy.joinColumns())) {
			JoinColumn newJoinColumn = this.addSpecifiedJoinColumn(this.specifiedJoinColumnsSize());
			newJoinColumn.setSpecifiedName(joinColumn.getName());
			newJoinColumn.setSpecifiedReferencedColumnName(joinColumn.getReferencedColumnName());			
		}
	}
	
	protected abstract OrmJoinColumn.Owner buildJoinColumnOwner();
	
	protected void initializeSpecifiedJoinColumns() {
		for (XmlJoinColumn resourceJoinColumn : this.resource.getJoinColumns()) {
			this.specifiedJoinColumns.add(buildJoinColumn(resourceJoinColumn));
		}
	}
	
	protected void initializeDefaultJoinColumn() {
		if (mayHaveDefaultJoinColumn()) {
			this.defaultJoinColumn = this.buildJoinColumn(null);
		}
	}	
	
	@Override
	public JoinColumnEnabledRelationshipReference getParent() {
		return (JoinColumnEnabledRelationshipReference) super.getParent();
	}
	
	public JoinColumnEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	public RelationshipMapping getRelationshipMapping() {
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
		XmlJoinColumn resourceJoinColumn = OrmFactory.eINSTANCE.createXmlJoinColumn();
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
	
	protected OrmJoinColumn buildJoinColumn(XmlJoinColumn resourceJoinColumn) {
		return this.getJpaFactory().buildOrmJoinColumn(this, this.joinColumnOwner, resourceJoinColumn);
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
		validateJoinColumnName(joinColumn, messages);
		validationJoinColumnReferencedColumnName(joinColumn, messages);
	}
	
	protected void validateJoinColumnName(OrmJoinColumn joinColumn, List<IMessage> messages) {
		if ( ! joinColumn.isResolved() && joinColumn.getDbTable() != null) {
			if (joinColumn.getName() != null) {
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
			else if (joinColumn.getOwner().joinColumnsSize() > 1) {
				if (getRelationshipMapping().getPersistentAttribute().isVirtual()) {
					messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_NAME_MULTIPLE_JOIN_COLUMNS,
								new String[] {getRelationshipMapping().getName()}, 
								joinColumn,
								joinColumn.getNameTextRange()
							)
						);
				}
				else {
					messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME_MULTIPLE_JOIN_COLUMNS,
								joinColumn,
								joinColumn.getNameTextRange()
							)
						);
				}
			}
		}
	}
	
	protected void validationJoinColumnReferencedColumnName(OrmJoinColumn joinColumn, List<IMessage> messages) {
		if ( ! joinColumn.isReferencedColumnResolved() && joinColumn.getReferencedColumnDbTable() != null) {
			if (joinColumn.getReferencedColumnName() != null) {
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
			else if (joinColumn.getOwner().joinColumnsSize() > 1) {
				if (getRelationshipMapping().getPersistentAttribute().isVirtual()) {
					messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME_MULTIPLE_JOIN_COLUMNS,
								new String[] {getRelationshipMapping().getName(), joinColumn.getName()}, 
								joinColumn,
								joinColumn.getReferencedColumnNameTextRange()
							)
						);					
				}
				else {
					messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME_MULTIPLE_JOIN_COLUMNS,
								joinColumn,
								joinColumn.getReferencedColumnNameTextRange()
							)
						);
				}
			}			
		}
	}

}
