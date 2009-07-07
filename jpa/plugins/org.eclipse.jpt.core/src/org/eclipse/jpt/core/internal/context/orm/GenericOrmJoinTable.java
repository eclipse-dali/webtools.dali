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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * orm.xml join table
 */
public class GenericOrmJoinTable
	extends AbstractOrmTable
	implements OrmJoinTable
{
	protected OrmJoinColumn defaultJoinColumn;

	protected final Vector<OrmJoinColumn> specifiedJoinColumns = new Vector<OrmJoinColumn>();
	protected final OrmJoinColumn.Owner joinColumnOwner;

	protected OrmJoinColumn defaultInverseJoinColumn;

	protected final Vector<OrmJoinColumn> specifiedInverseJoinColumns = new Vector<OrmJoinColumn>();
	protected final OrmJoinColumn.Owner inverseJoinColumnOwner;


	public GenericOrmJoinTable(OrmJoinTableJoiningStrategy parent, XmlJoinTable resourceJoinTable) {
		super(parent);
		this.joinColumnOwner = this.buildJoinColumnOwner();
		this.inverseJoinColumnOwner = this.buildInverseJoinColumnOwner();
		this.initialize(resourceJoinTable);
	}

	protected OrmJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	protected OrmJoinColumn.Owner buildInverseJoinColumnOwner() {
		return new InverseJoinColumnOwner();
	}

	public OrmRelationshipMapping getRelationshipMapping() {
		return getParent().getRelationshipReference().getRelationshipMapping();
	}

	public void initializeFrom(JoinTable oldJoinTable) {
		super.initializeFrom(oldJoinTable);
		for (Iterator<OrmJoinColumn> stream = oldJoinTable.specifiedJoinColumns(); stream.hasNext(); ) {
			this.addSpecifiedJoinColumnFrom(stream.next());
		}
		for (Iterator<OrmJoinColumn> stream = oldJoinTable.specifiedInverseJoinColumns(); stream.hasNext(); ) {
			this.addSpecifiedInverseJoinColumnFrom(stream.next());
		}
	}

	protected void initialize(XmlJoinTable joinTable) {
		super.initialize(joinTable);
		this.initializeSpecifiedJoinColumns(joinTable);
		this.initializeDefaultJoinColumn();
		this.initializeSpecifiedInverseJoinColumns(joinTable);
		this.initializeDefaultInverseJoinColumn();
	}

	public void update() {
		this.update(this.getResourceTable());
	}

	protected void update(XmlJoinTable joinTable) {
		super.update(joinTable);
		this.updateSpecifiedJoinColumns(joinTable);
		this.updateDefaultJoinColumn();
		this.updateSpecifiedInverseJoinColumns(joinTable);
		this.updateDefaultInverseJoinColumn();
	}


	// ********** AbstractOrmTable implementation **********

	@Override
	public OrmJoinTableJoiningStrategy getParent() {
		return (OrmJoinTableJoiningStrategy) super.getParent();
	}

	@Override
	protected String buildDefaultName() {
		return this.getRelationshipMapping().getJoinTableDefaultName();
	}

	/**
	 * if the join table is on the "mappedBy" side, it's bogus;
	 * so don't give it a default schema
	 */
	@Override
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	@Override
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}

	@Override
	protected XmlJoinTable getResourceTable() {
		return this.getParent().getResourceJoinTable();
	}

	@Override
	protected XmlJoinTable addResourceTable() {
		return getParent().addResourceJoinTable();
	}

	@Override
	protected void removeResourceTable() {
		getParent().removeResourceJoinTable();
	}


	// ********** join columns **********

	public ListIterator<OrmJoinColumn> joinColumns() {
		return this.containsSpecifiedJoinColumns() ? this.specifiedJoinColumns() : this.defaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.containsSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.defaultJoinColumnsSize();
	}

	public void convertDefaultToSpecifiedJoinColumn() {
		MappingTools.convertJoinTableDefaultToSpecifiedJoinColumn(this);
	}


	// ********** default join column **********

	public OrmJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(OrmJoinColumn defaultJoinColumn) {
		OrmJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = defaultJoinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN, old, defaultJoinColumn);
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

	protected void initializeDefaultJoinColumn() {
		if (this.shouldBuildDefaultJoinColumn()) {
			this.defaultJoinColumn = this.buildJoinColumn(null);
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

	protected boolean shouldBuildDefaultJoinColumn() {
		return ! this.containsSpecifiedJoinColumns();
	}


	// ********** specified join columns **********

	public ListIterator<OrmJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<OrmJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean containsSpecifiedJoinColumns() {
		return this.specifiedJoinColumns.size() != 0;
	}

	protected void addSpecifiedJoinColumnFrom(OrmJoinColumn oldJoinColumn) {
		OrmJoinColumn newJoinColumn = this.addSpecifiedJoinColumn(this.specifiedJoinColumns.size());
		newJoinColumn.initializeFrom(oldJoinColumn);
	}

	public OrmJoinColumn addSpecifiedJoinColumn(int index) {
		if (this.getResourceTable() == null) {
			this.addResourceTable();
		}
		XmlJoinColumn xmlJoinColumn = OrmFactory.eINSTANCE.createXmlJoinColumn();
		OrmJoinColumn joinColumn = this.buildJoinColumn(xmlJoinColumn);
		this.specifiedJoinColumns.add(index, joinColumn);
		this.getResourceTable().getJoinColumns().add(index, xmlJoinColumn);
		this.fireItemAdded(SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
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
		if ( ! this.containsSpecifiedJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultJoinColumn = this.buildJoinColumn(null);
		}
		this.getResourceTable().getJoinColumns().remove(index);
		this.fireItemRemoved(SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(DEFAULT_JOIN_COLUMN, null, this.defaultJoinColumn);
		}
	}

	protected void removeSpecifiedJoinColumn_(OrmJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.getResourceTable().getJoinColumns().move(targetIndex, sourceIndex);
		this.fireItemMoved(SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}

	public void clearSpecifiedJoinColumns() {
		this.specifiedJoinColumns.clear();
		this.defaultJoinColumn = this.buildJoinColumn(null);
		this.getResourceTable().getJoinColumns().clear();
		this.fireListCleared(SPECIFIED_JOIN_COLUMNS_LIST);
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN, null, this.defaultJoinColumn);
	}

	protected OrmJoinColumn buildJoinColumn(XmlJoinColumn resourceJoinColumn) {
		return this.buildJoinColumn(resourceJoinColumn, this.joinColumnOwner);
	}

	protected void initializeSpecifiedJoinColumns(XmlJoinTable xmlJoinTable) {
		if (xmlJoinTable != null) {
			for (XmlJoinColumn xmlJoinColumn : xmlJoinTable.getJoinColumns()) {
				this.specifiedJoinColumns.add(this.buildJoinColumn(xmlJoinColumn));
			}
		}
	}

	protected void updateSpecifiedJoinColumns(XmlJoinTable xmlJoinTable) {
		Iterator<XmlJoinColumn> xmlJoinColumns = this.xmlJoinColumns(xmlJoinTable);

		for (Iterator<OrmJoinColumn> contextJoinColumns = this.specifiedJoinColumns(); contextJoinColumns.hasNext(); ) {
			OrmJoinColumn contextJoinColumn = contextJoinColumns.next();
			if (xmlJoinColumns.hasNext()) {
				contextJoinColumn.update(xmlJoinColumns.next());
			} else {
				this.removeSpecifiedJoinColumn_(contextJoinColumn);
			}
		}

		while (xmlJoinColumns.hasNext()) {
			this.addSpecifiedJoinColumn(this.buildJoinColumn(xmlJoinColumns.next()));
		}
	}

	protected Iterator<XmlJoinColumn> xmlJoinColumns(XmlJoinTable xmlJoinTable) {
		// make a copy of the XML join columns (to prevent ConcurrentModificationException)
		return (xmlJoinTable == null) ? EmptyIterator.<XmlJoinColumn>instance()
			: new CloneIterator<XmlJoinColumn>(xmlJoinTable.getJoinColumns());
	}


	// ********** inverse join columns **********

	public ListIterator<OrmJoinColumn> inverseJoinColumns() {
		return this.containsSpecifiedInverseJoinColumns() ? this.specifiedInverseJoinColumns() : this.defaultInverseJoinColumns();
	}

	public int inverseJoinColumnsSize() {
		return this.containsSpecifiedInverseJoinColumns() ? this.specifiedInverseJoinColumnsSize() : this.defaultInverseJoinColumnsSize();
	}

	public void convertDefaultToSpecifiedInverseJoinColumn() {
		MappingTools.convertJoinTableDefaultToSpecifiedInverseJoinColumn(this);
	}


	// ********** default inverse join column **********

	public OrmJoinColumn getDefaultInverseJoinColumn() {
		return this.defaultInverseJoinColumn;
	}

	protected void setDefaultInverseJoinColumn(OrmJoinColumn defaultInverseJoinColumn) {
		OrmJoinColumn old = this.defaultInverseJoinColumn;
		this.defaultInverseJoinColumn = defaultInverseJoinColumn;
		this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, old, defaultInverseJoinColumn);
	}

	protected ListIterator<OrmJoinColumn> defaultInverseJoinColumns() {
		if (this.defaultInverseJoinColumn != null) {
			return new SingleElementListIterator<OrmJoinColumn>(this.defaultInverseJoinColumn);
		}
		return EmptyListIterator.instance();
	}

	protected int defaultInverseJoinColumnsSize() {
		return (this.defaultInverseJoinColumn == null) ? 0 : 1;
	}

	protected void initializeDefaultInverseJoinColumn() {
		if (this.shouldBuildDefaultInverseJoinColumn()) {
			this.defaultInverseJoinColumn = this.buildInverseJoinColumn(null);
		}
	}

	protected void updateDefaultInverseJoinColumn() {
		if (this.shouldBuildDefaultInverseJoinColumn()) {
			if (this.defaultInverseJoinColumn == null) {
				this.setDefaultInverseJoinColumn(this.buildInverseJoinColumn(null));
			} else {
				this.defaultInverseJoinColumn.update(null);
			}
		} else {
			this.setDefaultInverseJoinColumn(null);
		}
	}

	protected boolean shouldBuildDefaultInverseJoinColumn() {
		return ! this.containsSpecifiedInverseJoinColumns();
	}


	// ********** specified inverse join columns **********

	public ListIterator<OrmJoinColumn> specifiedInverseJoinColumns() {
		return new CloneListIterator<OrmJoinColumn>(this.specifiedInverseJoinColumns);
	}

	public int specifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumns.size();
	}

	public boolean containsSpecifiedInverseJoinColumns() {
		return !this.specifiedInverseJoinColumns.isEmpty();
	}

	protected void addSpecifiedInverseJoinColumnFrom(OrmJoinColumn oldJoinColumn) {
		OrmJoinColumn newJoinColumn = this.addSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumns.size());
		newJoinColumn.initializeFrom(oldJoinColumn);
	}

	public OrmJoinColumn addSpecifiedInverseJoinColumn(int index) {
		if (this.getResourceTable() == null) {
			this.addResourceTable();
		}
		XmlJoinColumn xmlJoinColumn = OrmFactory.eINSTANCE.createXmlJoinColumn();
		OrmJoinColumn joinColumn = this.buildInverseJoinColumn(xmlJoinColumn);
		this.specifiedInverseJoinColumns.add(index, joinColumn);
		this.getResourceTable().getInverseJoinColumns().add(index, xmlJoinColumn);
		this.fireItemAdded(SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}

	protected void addSpecifiedInverseJoinColumn(int index, OrmJoinColumn joinColumn) {
		this.addItemToList(index, joinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	protected void addSpecifiedInverseJoinColumn(OrmJoinColumn joinColumn) {
		this.addSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumns.size(), joinColumn);
	}

	public void removeSpecifiedInverseJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumns.indexOf(joinColumn));
	}

	public void removeSpecifiedInverseJoinColumn(int index) {
		OrmJoinColumn removedJoinColumn = this.specifiedInverseJoinColumns.remove(index);
		if ( ! this.containsSpecifiedInverseJoinColumns()) {
			//create the defaultInverseJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultInverseJoinColumn = this.buildInverseJoinColumn(null);
		}
		this.getResourceTable().getInverseJoinColumns().remove(index);
		this.fireItemRemoved(SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultInverseJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, null, this.defaultInverseJoinColumn);
		}
	}

	protected void removeSpecifiedInverseJoinColumn_(OrmJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedInverseJoinColumns, targetIndex, sourceIndex);
		this.getResourceTable().getInverseJoinColumns().move(targetIndex, sourceIndex);
		this.fireItemMoved(SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}

	public void clearSpecifiedInverseJoinColumns() {
		this.specifiedInverseJoinColumns.clear();
		this.defaultInverseJoinColumn = this.buildInverseJoinColumn(null);
		this.getResourceTable().getInverseJoinColumns().clear();
		this.fireListCleared(SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
		this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, null, this.defaultInverseJoinColumn);
	}

	protected OrmJoinColumn buildInverseJoinColumn(XmlJoinColumn resourceJoinColumn) {
		return this.buildJoinColumn(resourceJoinColumn, this.inverseJoinColumnOwner);
	}

	protected void initializeSpecifiedInverseJoinColumns(XmlJoinTable xmlJoinTable) {
		if (xmlJoinTable != null) {
			for (XmlJoinColumn xmlJoinColumn : xmlJoinTable.getInverseJoinColumns()) {
				this.specifiedInverseJoinColumns.add(this.buildInverseJoinColumn(xmlJoinColumn));
			}
		}
	}

	protected void updateSpecifiedInverseJoinColumns(XmlJoinTable xmlJoinTable) {
		Iterator<XmlJoinColumn> xmlJoinColumns = this.xmlInverseJoinColumns(xmlJoinTable);

		for (ListIterator<OrmJoinColumn> contextJoinColumns = this.specifiedInverseJoinColumns(); contextJoinColumns.hasNext(); ) {
			OrmJoinColumn contextColumn = contextJoinColumns.next();
			if (xmlJoinColumns.hasNext()) {
				contextColumn.update(xmlJoinColumns.next());
			} else {
				this.removeSpecifiedInverseJoinColumn_(contextColumn);
			}
		}

		while (xmlJoinColumns.hasNext()) {
			this.addSpecifiedInverseJoinColumn(this.buildInverseJoinColumn(xmlJoinColumns.next()));
		}
	}

	protected Iterator<XmlJoinColumn> xmlInverseJoinColumns(XmlJoinTable xmlJoinTable) {
		// make a copy of the XML join columns (to prevent ConcurrentModificationException)
		return (xmlJoinTable == null) ? EmptyIterator.<XmlJoinColumn>instance()
			: new CloneIterator<XmlJoinColumn>(xmlJoinTable.getInverseJoinColumns());
	}


	// ********** misc **********

	protected OrmJoinColumn buildJoinColumn(XmlJoinColumn resourceJoinColumn, OrmJoinColumn.Owner owner) {
		return this.getJpaFactory().buildOrmJoinColumn(this, owner, resourceJoinColumn);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.connectionProfileIsActive()) {
			this.validateAgainstDatabase(messages, reporter);
		}
	}

	protected void validateAgainstDatabase(List<IMessage> messages, IReporter reporter) {
		OrmRelationshipMapping mapping = this.getRelationshipMapping();

		if ( ! this.hasResolvedCatalog()) {
			if (mapping.getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_CATALOG,
						new String[] {mapping.getName(), this.getCatalog(), this.getName()}, 
						this,
						this.getCatalogTextRange()
					)
				);

			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_TABLE_UNRESOLVED_CATALOG,
						new String[] {this.getCatalog(), this.getName()}, 
						this,
						this.getCatalogTextRange()
					)
				);
			}
			return;
		}

		if ( ! this.hasResolvedSchema()) {
			if (mapping.getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_SCHEMA,
						new String[] {mapping.getName(), this.getSchema(), this.getName()}, 
						this,
						this.getSchemaTextRange()
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_TABLE_UNRESOLVED_SCHEMA,
						new String[] {this.getSchema(), this.getName()}, 
						this,
						this.getSchemaTextRange()
					)
				);
			}
			return;
		}
		if ( ! this.isResolved()) {
			if (getName() != null) { //if name is null, the validation will be handled elsewhere, such as the target entity is not defined
				if (mapping.getPersistentAttribute().isVirtual()) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_NAME,
							new String[] {mapping.getName(), this.getName()}, 
							this,
							this.getNameTextRange()
						)
					);
				} 
				else {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.JOIN_TABLE_UNRESOLVED_NAME,
								new String[] {this.getName()}, 
								this, 
								this.getNameTextRange())
						);
				}
			}
			return;
		}

		this.validateJoinColumns(this.joinColumns(), messages, reporter);
		this.validateJoinColumns(this.inverseJoinColumns(), messages, reporter);
	}

	protected void validateJoinColumns(Iterator<OrmJoinColumn> joinColumns, List<IMessage> messages, IReporter reporter) {
		while (joinColumns.hasNext()) {
			joinColumns.next().validate(messages, reporter);
		}
	}


	// ********** join column owner adapters **********

	/**
	 * just a little common behavior
	 */
	protected abstract class AbstractJoinColumnOwner
		implements OrmJoinColumn.Owner
	{
		protected AbstractJoinColumnOwner() {
			super();
		}

		public TypeMapping getTypeMapping() {
			return this.getRelationshipMapping().getTypeMapping();
		}

		public RelationshipMapping getRelationshipMapping() {
			return GenericOrmJoinTable.this.getRelationshipMapping();
		}

		/**
		 * the default table name is always valid and a specified table name
		 * is prohibited (which will be handled elsewhere)
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return false;
		}

		/**
		 * the join column can only be on the join table itself
		 */
		public boolean tableIsAllowed() {
			return false;
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			String joinTableName = GenericOrmJoinTable.this.getName();
			return (joinTableName == null) ? null : (joinTableName.equals(tableName)) ? GenericOrmJoinTable.this.getDbTable() : null;
		}

		/**
		 * by default, the join column is, obviously, in the join table;
		 * not sure whether it can be anywhere else...
		 */
		public String getDefaultTableName() {
			return GenericOrmJoinTable.this.getName();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmJoinTable.this.getValidationTextRange();
		}
	}


	/**
	 * owner for "back-pointer" JoinColumns;
	 * these point at the source/owning entity
	 */
	protected class JoinColumnOwner
		extends AbstractJoinColumnOwner
	{
		protected JoinColumnOwner() {
			super();
		}

		public Entity getTargetEntity() {
			return GenericOrmJoinTable.this.getRelationshipMapping().getEntity();
		}

		public String getAttributeName() {
			Entity targetEntity = GenericOrmJoinTable.this.getRelationshipMapping().getResolvedTargetEntity();
			if (targetEntity == null) {
				return null;
			}
			for (PersistentAttribute each : CollectionTools.iterable(targetEntity.getPersistentType().allAttributes())) {
				if (each.getMapping().isOwnedBy(this.getRelationshipMapping())) {
					return each.getName();
				}
			}
			return null;
		}

		@Override
		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			org.eclipse.jpt.db.Table dbTable = super.getDbTable(tableName);
			return (dbTable != null) ? dbTable : this.getTypeMapping().getDbTable(tableName);
		}

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			return getTypeMapping().getPrimaryDbTable();
		}

		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return GenericOrmJoinTable.this.defaultJoinColumn == joinColumn;
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public int joinColumnsSize() {
			return GenericOrmJoinTable.this.joinColumnsSize();
		}
	}


	/**
	 * owner for "forward-pointer" JoinColumns;
	 * these point at the target/inverse entity
	 */
	protected class InverseJoinColumnOwner
		extends AbstractJoinColumnOwner
	{
		protected InverseJoinColumnOwner() {
			super();
		}

		public Entity getTargetEntity() {
			return GenericOrmJoinTable.this.getRelationshipMapping().getResolvedTargetEntity();
		}

		public String getAttributeName() {
			return GenericOrmJoinTable.this.getRelationshipMapping().getName();
		}

		@Override
		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			org.eclipse.jpt.db.Table dbTable = super.getDbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			Entity targetEntity = this.getTargetEntity();
			return (targetEntity == null) ? null : targetEntity.getDbTable(tableName);
		}

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			Entity targetEntity = this.getTargetEntity();
			return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
		}

		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return GenericOrmJoinTable.this.defaultInverseJoinColumn == joinColumn;
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public int joinColumnsSize() {
			return GenericOrmJoinTable.this.inverseJoinColumnsSize();
		}
	}

}
