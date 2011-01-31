/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmJoinTableRelationshipStrategy;
import org.eclipse.jpt.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> join table
 */
public class GenericOrmJoinTable
	extends GenericOrmReferenceTable<XmlJoinTable>
	implements OrmJoinTable
{
	protected final Vector<OrmJoinColumn> specifiedInverseJoinColumns = new Vector<OrmJoinColumn>();
	protected final SpecifiedInverseJoinColumnContainerAdapter specifiedInverseJoinColumnContainerAdapter = new SpecifiedInverseJoinColumnContainerAdapter();
	protected final OrmJoinColumn.Owner inverseJoinColumnOwner;

	protected OrmJoinColumn defaultInverseJoinColumn;


	public GenericOrmJoinTable(OrmJoinTableRelationshipStrategy parent, Owner owner) {
		super(parent, owner);
		this.inverseJoinColumnOwner = this.buildInverseJoinColumnOwner();
		this.initializeSpecifiedInverseJoinColumns();
	}

	@Override
	protected OrmJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncSpecifiedInverseJoinColumns();
		if (this.defaultInverseJoinColumn != null) {
			this.defaultInverseJoinColumn.synchronizeWithResourceModel();
		}
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getSpecifiedInverseJoinColumns());
		this.updateDefaultInverseJoinColumn();
	}


	// ********** XML table **********

	@Override
	protected XmlJoinTable getXmlTable() {
		return this.getJoinStrategy().getXmlJoinTable();
	}

	@Override
	protected XmlJoinTable buildXmlTable() {
		return this.getJoinStrategy().buildXmlJoinTable();
	}

	@Override
	protected void removeXmlTable() {
		this.getJoinStrategy().removeXmlJoinTable();
	}


	// ********** inverse join columns **********

	public ListIterator<OrmJoinColumn> inverseJoinColumns() {
		return this.getInverseJoinColumns().iterator();
	}

	protected ListIterable<OrmJoinColumn> getInverseJoinColumns() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumns() : this.getDefaultInverseJoinColumns();
	}

	public int inverseJoinColumnsSize() {
		return this.hasSpecifiedInverseJoinColumns() ? this.specifiedInverseJoinColumnsSize() : this.getDefaultInverseJoinColumnsSize();
	}

	public void convertDefaultToSpecifiedInverseJoinColumn() {
		MappingTools.convertJoinTableDefaultToSpecifiedInverseJoinColumn(this);
	}


	// ********** specified inverse join columns **********

	public ListIterator<OrmJoinColumn> specifiedInverseJoinColumns() {
		return this.getSpecifiedInverseJoinColumns().iterator();
	}

	public ListIterable<OrmJoinColumn> getSpecifiedInverseJoinColumns() {
		return new LiveCloneListIterable<OrmJoinColumn>(this.specifiedInverseJoinColumns);
	}

	public int specifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumns.size();
	}

	public boolean hasSpecifiedInverseJoinColumns() {
		return this.specifiedInverseJoinColumns.size() != 0;
	}

	public OrmJoinColumn getSpecifiedInverseJoinColumn(int index) {
		return this.specifiedInverseJoinColumns.get(index);
	}

	public OrmJoinColumn addSpecifiedInverseJoinColumn() {
		return this.addSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumns.size());
	}

	public OrmJoinColumn addSpecifiedInverseJoinColumn(int index) {
		XmlJoinTable xmlTable = this.getXmlTableForUpdate();
		XmlJoinColumn xmlJoinColumn = this.buildXmlJoinColumn();
		OrmJoinColumn joinColumn = this.addSpecifiedInverseJoinColumn_(index, xmlJoinColumn);
		xmlTable.getInverseJoinColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	public void removeSpecifiedInverseJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumns.indexOf(joinColumn));
	}

	public void removeSpecifiedInverseJoinColumn(int index) {
		this.removeSpecifiedInverseJoinColumn_(index);
		this.getXmlTable().getInverseJoinColumns().remove(index);
		this.removeXmlTableIfUnset();
	}

	protected void removeSpecifiedInverseJoinColumn_(int index) {
		this.removeItemFromList(index, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
		this.getXmlTable().getInverseJoinColumns().move(targetIndex, sourceIndex);
	}

	public void clearSpecifiedInverseJoinColumns() {
		this.clearCollection(this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
		this.getXmlTable().getInverseJoinColumns().clear();
	}

	protected void initializeSpecifiedInverseJoinColumns() {
		for (XmlJoinColumn xmlJoinColumn : this.getXmlInverseJoinColumns()) {
			this.specifiedInverseJoinColumns.add(this.buildInverseJoinColumn(xmlJoinColumn));
		}
	}

	protected void syncSpecifiedInverseJoinColumns() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedInverseJoinColumnContainerAdapter);
	}

	protected Iterable<XmlJoinColumn> getXmlInverseJoinColumns() {
		XmlJoinTable xmlTable = this.getXmlTable();
		return (xmlTable == null) ?
				EmptyIterable.<XmlJoinColumn>instance() :
				// clone to reduce chance of concurrency problems
				new LiveCloneIterable<XmlJoinColumn>(xmlTable.getInverseJoinColumns());
	}

	protected void moveSpecifiedInverseJoinColumn_(int index, OrmJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	protected OrmJoinColumn addSpecifiedInverseJoinColumn_(int index, XmlJoinColumn xmlJoinColumn) {
		OrmJoinColumn joinColumn = this.buildInverseJoinColumn(xmlJoinColumn);
		this.addItemToList(index, joinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
		return joinColumn;
	}

	protected void removeSpecifiedInverseJoinColumn_(OrmJoinColumn joinColumn) {
		this.removeSpecifiedInverseJoinColumn_(this.specifiedInverseJoinColumns.indexOf(joinColumn));
	}

	/**
	 * specified inverse join column container adapter
	 */
	protected class SpecifiedInverseJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<OrmJoinColumn, XmlJoinColumn>
	{
		public Iterable<OrmJoinColumn> getContextElements() {
			return GenericOrmJoinTable.this.getSpecifiedInverseJoinColumns();
		}
		public Iterable<XmlJoinColumn> getResourceElements() {
			return GenericOrmJoinTable.this.getXmlInverseJoinColumns();
		}
		public XmlJoinColumn getResourceElement(OrmJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
		public void moveContextElement(int index, OrmJoinColumn element) {
			GenericOrmJoinTable.this.moveSpecifiedInverseJoinColumn_(index, element);
		}
		public void addContextElement(int index, XmlJoinColumn resourceElement) {
			GenericOrmJoinTable.this.addSpecifiedInverseJoinColumn_(index, resourceElement);
		}
		public void removeContextElement(OrmJoinColumn element) {
			GenericOrmJoinTable.this.removeSpecifiedInverseJoinColumn_(element);
		}
	}

	protected OrmJoinColumn.Owner buildInverseJoinColumnOwner() {
		return new InverseJoinColumnOwner();
	}


	// ********** default inverse join column **********

	public OrmJoinColumn getDefaultInverseJoinColumn() {
		return this.defaultInverseJoinColumn;
	}

	protected void setDefaultInverseJoinColumn(OrmJoinColumn joinColumn) {
		OrmJoinColumn old = this.defaultInverseJoinColumn;
		this.defaultInverseJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, old, joinColumn);
	}

	protected ListIterable<OrmJoinColumn> getDefaultInverseJoinColumns() {
		return (this.defaultInverseJoinColumn != null) ?
				new SingleElementListIterable<OrmJoinColumn>(this.defaultInverseJoinColumn) :
				EmptyListIterable.<OrmJoinColumn>instance();
	}

	protected int getDefaultInverseJoinColumnsSize() {
		return (this.defaultInverseJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultInverseJoinColumn() {
		if (this.buildsDefaultInverseJoinColumn()) {
			if (this.defaultInverseJoinColumn == null) {
				this.setDefaultInverseJoinColumn(this.buildInverseJoinColumn(null));
			} else {
				this.defaultInverseJoinColumn.update();
			}
		} else {
			this.setDefaultInverseJoinColumn(null);
		}
	}

	protected boolean buildsDefaultInverseJoinColumn() {
		return ! this.hasSpecifiedInverseJoinColumns();
	}


	// ********** misc **********

	@Override
	public OrmJoinTableRelationshipStrategy getParent() {
		return (OrmJoinTableRelationshipStrategy) super.getParent();
	}

	protected OrmJoinTableRelationshipStrategy getJoinStrategy() {
		return this.getParent();
	}

	@Override
	protected String buildDefaultName() {
		return this.getJoinStrategy().getJoinTableDefaultName();
	}

	public void initializeFrom(ReadOnlyJoinTable oldTable) {
		super.initializeFrom(oldTable);
		for (ReadOnlyJoinColumn joinColumn : CollectionTools.iterable(oldTable.specifiedInverseJoinColumns())) {
			this.addSpecifiedInverseJoinColumn().initializeFrom(joinColumn);
		}
	}

	public void initializeFromVirtual(ReadOnlyJoinTable virtualTable) {
		super.initializeFromVirtual(virtualTable);
		for (ReadOnlyJoinColumn joinColumn : CollectionTools.iterable(virtualTable.inverseJoinColumns())) {
			this.addSpecifiedInverseJoinColumn().initializeFromVirtual(joinColumn);
		}
	}

	protected OrmJoinColumn buildInverseJoinColumn(XmlJoinColumn xmlJoinColumn) {
		return this.getContextNodeFactory().buildOrmJoinColumn(this, this.inverseJoinColumnOwner, xmlJoinColumn);
	}

	public RelationshipMapping getRelationshipMapping() {
		return this.getJoinStrategy().getRelationship().getMapping();
	}

	public PersistentAttribute getPersistentAttribute() {
		return this.getRelationshipMapping().getPersistentAttribute();
	}


	// ********** validation **********

	@Override
	protected void validateJoinColumns(List<IMessage> messages, IReporter reporter) {
		super.validateJoinColumns(messages, reporter);
		this.validateJoinColumns(this.getInverseJoinColumns(), messages, reporter);
	}

	public boolean validatesAgainstDatabase() {
		return this.getJoinStrategy().validatesAgainstDatabase();
	}


	// ********** join column owners **********

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
			return GenericOrmJoinTable.this.getJoinStrategy().getRelationship().getTypeMapping();
		}

		protected PersistentAttribute getPersistentAttribute() {
			return GenericOrmJoinTable.this.getPersistentAttribute();
		}

		/**
		 * @see MappingTools#buildJoinColumnDefaultName(org.eclipse.jpt.core.context.ReadOnlyJoinColumn, org.eclipse.jpt.core.context.ReadOnlyJoinColumn.Owner)
		 */
		public String getDefaultColumnName() {
			throw new UnsupportedOperationException();
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name.  the table is always the join table
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return Tools.valuesAreDifferent(this.getDefaultTableName(), tableName);
		}

		/**
		 * the join column can only be on the join table itself
		 */
		public Iterator<String> candidateTableNames() {
			return EmptyIterator.instance();
		}

		public org.eclipse.jpt.db.Table resolveDbTable(String tableName) {
			return Tools.valuesAreEqual(GenericOrmJoinTable.this.getName(), tableName) ?
					GenericOrmJoinTable.this.getDbTable() :
					null;
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

		protected boolean isPersistentAttributeVirtual() {
			return this.getPersistentAttribute().isVirtual();
		}

		protected String getPersistentAttributeName() {
			return this.getPersistentAttribute().getName();
		}

		protected OrmJoinTableRelationshipStrategy getJoinStrategy() {
			return GenericOrmJoinTable.this.getJoinStrategy();
		}
	}


	/**
	 * owner for "back-pointer" join columns;
	 * these point at the source/owning entity
	 */
	protected class JoinColumnOwner
		extends AbstractJoinColumnOwner
	{
		protected JoinColumnOwner() {
			super();
		}

		public Entity getRelationshipTarget() {
			return this.getJoinStrategy().getRelationship().getEntity();
		}

		public String getAttributeName() {
			return MappingTools.getTargetAttributeName(GenericOrmJoinTable.this.getRelationshipMapping());
		}

		@Override
		public org.eclipse.jpt.db.Table resolveDbTable(String tableName) {
			org.eclipse.jpt.db.Table dbTable = super.resolveDbTable(tableName);
			return (dbTable != null) ? dbTable : this.getTypeMapping().resolveDbTable(tableName);
		}

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			return this.getTypeMapping().getPrimaryDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericOrmJoinTable.this.defaultJoinColumn == joinColumn;
		}

		public int joinColumnsSize() {
			return GenericOrmJoinTable.this.joinColumnsSize();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return this.getJoinStrategy().buildJoinTableJoinColumnValidator((JoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver);
		}
	}


	/**
	 * owner for "forward-pointer" join columns;
	 * these point at the target/inverse entity
	 */
	protected class InverseJoinColumnOwner
		extends AbstractJoinColumnOwner
	{
		protected InverseJoinColumnOwner() {
			super();
		}

		public Entity getRelationshipTarget() {
			RelationshipMapping relationshipMapping = GenericOrmJoinTable.this.getRelationshipMapping();
			return (relationshipMapping == null) ? null : relationshipMapping.getResolvedTargetEntity();
		}

		public String getAttributeName() {
			RelationshipMapping relationshipMapping = GenericOrmJoinTable.this.getRelationshipMapping();
			return (relationshipMapping == null) ? null : relationshipMapping.getName();
		}

		@Override
		public org.eclipse.jpt.db.Table resolveDbTable(String tableName) {
			org.eclipse.jpt.db.Table dbTable = super.resolveDbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			Entity relationshipTarget = this.getRelationshipTarget();
			return (relationshipTarget == null) ? null : relationshipTarget.resolveDbTable(tableName);
		}

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			Entity relationshipTarget = this.getRelationshipTarget();
			return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericOrmJoinTable.this.defaultInverseJoinColumn == joinColumn;
		}

		public int joinColumnsSize() {
			return GenericOrmJoinTable.this.inverseJoinColumnsSize();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return this.getJoinStrategy().buildJoinTableInverseJoinColumnValidator((JoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver);
		}
	}
}
