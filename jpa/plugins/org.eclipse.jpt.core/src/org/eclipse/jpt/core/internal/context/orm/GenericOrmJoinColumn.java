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

import java.util.List;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.RelationshipMappingTools;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericOrmJoinColumn extends AbstractOrmBaseColumn<XmlJoinColumn> implements OrmJoinColumn
{

	protected String specifiedReferencedColumnName;

	protected String defaultReferencedColumnName;

	protected XmlJoinColumn joinColumn;

	public GenericOrmJoinColumn(OrmJpaContextNode parent, OrmJoinColumn.Owner owner) {
		super(parent, owner);
	}

	public void initializeFrom(JoinColumn oldColumn) {
		super.initializeFrom(oldColumn);
		setSpecifiedReferencedColumnName(oldColumn.getSpecifiedReferencedColumnName());
	}
	
	public String getReferencedColumnName() {
		return (this.getSpecifiedReferencedColumnName() == null) ? getDefaultReferencedColumnName() : this.getSpecifiedReferencedColumnName();
	}

	public String getSpecifiedReferencedColumnName() {
		return this.specifiedReferencedColumnName;
	}

	public void setSpecifiedReferencedColumnName(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		columnResource().setReferencedColumnName(newSpecifiedReferencedColumnName);
		firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}
	
	public void setSpecifiedReferencedColumnName_(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}

	public String getDefaultReferencedColumnName() {
		return this.defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String newDefaultReferencedColumnName) {
		String oldDefaultReferencedColumnName = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = newDefaultReferencedColumnName;
		firePropertyChanged(DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY, oldDefaultReferencedColumnName, newDefaultReferencedColumnName);
	}

	public boolean isVirtual() {
		return owner().isVirtual(this);
	}
	
	@Override
	public OrmJoinColumn.Owner owner() {
		return (OrmJoinColumn.Owner) this.owner;
	}

	public Table dbReferencedColumnTable() {
		return owner().dbReferencedColumnTable();
	}

	public Column dbReferencedColumn() {
		Table table = dbReferencedColumnTable();
		return (table == null) ? null : table.columnNamed(getReferencedColumnName());
	}

	public boolean isReferencedColumnResolved() {
		return dbReferencedColumn() != null;
	}

	public TextRange referencedColumnNameTextRange() {
		if (columnResource() != null) {
			TextRange textRange = columnResource().referencedColumnNameTextRange();
			if (textRange != null) {
				return textRange;
			}
		}
		return owner().validationTextRange();
	}


	@Override
	protected XmlJoinColumn columnResource() {
		return this.joinColumn;
	}

	@Override
	protected void addColumnResource() {
		//joinColumns are part of a collection, the join-column element will be removed/added
		//when the XmlJoinColumn is removed/added to the XmlEntity collection
	}
	
	@Override
	protected void removeColumnResource() {
		//joinColumns are part of a collection, the pk-join-column element will be removed/added
		//when the XmlJoinColumn is removed/added to the XmlEntity collection
	}
	
	
	@Override
	public void initialize(XmlJoinColumn column) {
		this.joinColumn = column;
		super.initialize(column);
		this.specifiedReferencedColumnName = specifiedReferencedColumnName(column);
		this.defaultReferencedColumnName = defaultReferencedColumnName();
	}
	
	@Override
	public void update(XmlJoinColumn column) {
		this.joinColumn = column;
		super.update(column);
		this.setSpecifiedReferencedColumnName_(specifiedReferencedColumnName(column));
		this.setDefaultReferencedColumnName(defaultReferencedColumnName());
	}

	protected String specifiedReferencedColumnName(XmlJoinColumn column) {
		return column == null ? null : column.getReferencedColumnName();
	}

	@Override
	protected String defaultName() {
		RelationshipMapping relationshipMapping = owner().relationshipMapping();
		if (relationshipMapping == null) {
			return null;
		}
		if (!owner().relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		return RelationshipMappingTools.buildJoinColumnDefaultName(this);
	}
	
	protected String defaultReferencedColumnName() {
		RelationshipMapping relationshipMapping = owner().relationshipMapping();
		if (relationshipMapping == null) {
			return null;
		}
		if (!relationshipMapping.isRelationshipOwner()) {
			return null;
		}
		return RelationshipMappingTools.buildJoinColumnDefaultReferencedColumnName(this);
	}
	
	@Override
	protected String defaultTable() {
		RelationshipMapping relationshipMapping = owner().relationshipMapping();
		if (relationshipMapping == null) {
			return null;
		}
		if (!relationshipMapping.isRelationshipOwner()) {
			return null;
		}
		return super.defaultTable();
	}
	
	
	//******************* validation ***********************
	
	/** used internally as a mechanism to short circuit continued message adding */
	private boolean doContinue;
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		this.doContinue = this.connectionProfileIsActive();
	
		OrmRelationshipMapping mapping = (OrmRelationshipMapping) owner().relationshipMapping();
		//TODO why is this commented out?  i copied it like this from the maintenance stream
//		if (doContinue && typeMapping.tableNameIsInvalid(table)) {
//			if (mapping.isVirtual()) {
//				messages.add(
//					JpaValidationMessages.buildMessage(
//						IMessage.HIGH_SEVERITY,
//						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_TABLE,
//						new String[] {mapping.getPersistentAttribute().getName(), table, column.getName()},
//						column, column.getTableTextRange())
//				);
//			}
//			else {
//				messages.add(
//					JpaValidationMessages.buildMessage(
//						IMessage.HIGH_SEVERITY,
//						IJpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
//						new String[] {table, column.getName()}, 
//						column, column.getTableTextRange())
//				);
//			}
//			doContinue = false;
//		}
//		
		if (this.doContinue && ! isResolved()) {
			if (mapping.persistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME,
						new String[] {mapping.getName(), getName()}, 
						this, 
						nameTextRange())
				);
			}
			else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {getName()}, 
						this, 
						nameTextRange())
				);
			}
		}
	}
}
