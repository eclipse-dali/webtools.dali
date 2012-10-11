/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.DbGenerator;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.TableGenerator;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmDbGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;

/**
 * <code>orm.xml</code> table generator
 */
public class GenericOrmTableGenerator
	extends AbstractOrmDbGenerator<XmlTableGenerator>
	implements OrmTableGenerator, UniqueConstraint.Owner
{
	protected String specifiedTable;
	protected String defaultTable;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedPkColumnName;
	protected String defaultPkColumnName;

	protected String specifiedValueColumnName;
	protected String defaultValueColumnName;

	protected String specifiedPkColumnValue;
	protected String defaultPkColumnValue;

	protected final ContextListContainer<OrmUniqueConstraint, XmlUniqueConstraint> uniqueConstraintContainer;


	// ********** constructor **********

	public GenericOrmTableGenerator(JpaContextNode parent, XmlTableGenerator xmlTableGenerator) {
		super(parent, xmlTableGenerator);
		this.specifiedTable = xmlTableGenerator.getTable();
		this.specifiedSchema = xmlTableGenerator.getSchema();
		this.specifiedCatalog = xmlTableGenerator.getCatalog();
		this.specifiedPkColumnName = xmlTableGenerator.getPkColumnName();
		this.specifiedValueColumnName = xmlTableGenerator.getValueColumnName();
		this.specifiedPkColumnValue = xmlTableGenerator.getPkColumnValue();
		this.uniqueConstraintContainer = this.buildUniqueConstraintContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedTable_(this.xmlGenerator.getTable());
		this.setSpecifiedSchema_(this.xmlGenerator.getSchema());
		this.setSpecifiedCatalog_(this.xmlGenerator.getCatalog());
		this.setSpecifiedPkColumnName_(this.xmlGenerator.getPkColumnName());
		this.setSpecifiedValueColumnName_(this.xmlGenerator.getValueColumnName());
		this.setSpecifiedPkColumnValue_(this.xmlGenerator.getPkColumnValue());
		this.syncUniqueConstraints();
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultTable(this.buildDefaultTable());
		this.setDefaultSchema(this.buildDefaultSchema());
		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setDefaultPkColumnName(this.buildDefaultPkColumnName());
		this.setDefaultValueColumnName(this.buildDefaultValueColumnName());
		this.setDefaultPkColumnValue(this.buildDefaultPkColumnValue());
		this.updateNodes(this.getUniqueConstraints());
	}


	// ********** initial value **********

	@Override
	protected int buildDefaultInitialValue() {
		return DEFAULT_INITIAL_VALUE;
	}
	

	// ********** table **********

	public String getTable() {
		return (this.specifiedTable != null) ? this.specifiedTable : this.defaultTable;
	}

	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	public void setSpecifiedTable(String table) {
		this.setSpecifiedTable_(table);
		this.xmlGenerator.setTable(table);
	}

	protected void setSpecifiedTable_(String table) {
		String old = this.specifiedTable;
		this.specifiedTable = table;
		this.firePropertyChanged(SPECIFIED_TABLE_PROPERTY, old, table);
	}

	public String getDefaultTable() {
		return this.defaultTable;
	}

	protected void setDefaultTable(String table) {
		String old = this.defaultTable;
		this.defaultTable = table;
		this.firePropertyChanged(DEFAULT_TABLE_PROPERTY, old, table);
	}

	protected String buildDefaultTable() {
		return null; // TODO the default table is determined by the runtime provider...
	}

	public Table getDbTable() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema == null) ? null : dbSchema.getTableForIdentifier(this.getTable());
	}


	// ********** schema **********

	@Override
	public String getSchema() {
		return (this.specifiedSchema != null) ? this.specifiedSchema : this.defaultSchema;
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String schema) {
		this.setSpecifiedSchema_(schema);
		this.xmlGenerator.setSchema(schema);
	}

	protected void setSpecifiedSchema_(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	protected void setDefaultSchema(String schema) {
		String old = this.defaultSchema;
		this.defaultSchema = schema;
		this.firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, old, schema);
	}

	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}


	// ********** catalog **********

	@Override
	public String getCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String catalog) {
		this.setSpecifiedCatalog_(catalog);
		this.xmlGenerator.setCatalog(catalog);
	}

	protected void setSpecifiedCatalog_(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String catalog) {
		String old = this.defaultCatalog;
		this.defaultCatalog = catalog;
		this.firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, catalog);
	}

	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}


	// ********** primary key column name **********

	public String getPkColumnName() {
		return (this.specifiedPkColumnName != null) ? this.specifiedPkColumnName : this.defaultPkColumnName;
	}

	public String getSpecifiedPkColumnName() {
		return this.specifiedPkColumnName;
	}

	public void setSpecifiedPkColumnName(String name) {
		this.setSpecifiedPkColumnName_(name);
		this.xmlGenerator.setPkColumnName(name);
	}

	protected void setSpecifiedPkColumnName_(String name) {
		String old = this.specifiedPkColumnName;
		this.specifiedPkColumnName = name;
		this.firePropertyChanged(SPECIFIED_PK_COLUMN_NAME_PROPERTY, old, name);
	}

	public String getDefaultPkColumnName() {
		return this.defaultPkColumnName;
	}

	protected void setDefaultPkColumnName(String name) {
		String old = this.defaultPkColumnName;
		this.defaultPkColumnName = name;
		this.firePropertyChanged(DEFAULT_PK_COLUMN_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultPkColumnName() {
		return null; // TODO the default pk column name is determined by the runtime provider...
	}


	// ********** value column name **********

	public String getValueColumnName() {
		return (this.specifiedValueColumnName != null) ? this.specifiedValueColumnName : this.defaultValueColumnName;
	}

	public String getSpecifiedValueColumnName() {
		return this.specifiedValueColumnName;
	}

	public void setSpecifiedValueColumnName(String name) {
		this.setSpecifiedValueColumnName_(name);
		this.xmlGenerator.setValueColumnName(name);
	}

	protected void setSpecifiedValueColumnName_(String name) {
		String old = this.specifiedValueColumnName;
		this.specifiedValueColumnName = name;
		this.firePropertyChanged(SPECIFIED_VALUE_COLUMN_NAME_PROPERTY, old, name);
	}

	public String getDefaultValueColumnName() {
		return this.defaultValueColumnName;
	}

	protected void setDefaultValueColumnName(String name) {
		String old = this.defaultValueColumnName;
		this.defaultValueColumnName = name;
		this.firePropertyChanged(DEFAULT_VALUE_COLUMN_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultValueColumnName() {
		return null; // TODO the default value column name is determined by the runtime provider...
	}


	// ********** primary key column value **********

	public String getPkColumnValue() {
		return (this.specifiedPkColumnValue != null) ? this.specifiedPkColumnValue : this.defaultPkColumnValue;
	}

	public String getSpecifiedPkColumnValue() {
		return this.specifiedPkColumnValue;
	}

	public void setSpecifiedPkColumnValue(String value) {
		this.setSpecifiedPkColumnValue_(value);
		this.xmlGenerator.setPkColumnValue(value);
	}

	protected void setSpecifiedPkColumnValue_(String value) {
		String old = this.specifiedPkColumnValue;
		this.specifiedPkColumnValue = value;
		this.firePropertyChanged(SPECIFIED_PK_COLUMN_VALUE_PROPERTY, old, value);
	}

	public String getDefaultPkColumnValue() {
		return this.defaultPkColumnValue;
	}

	protected void setDefaultPkColumnValue(String value) {
		String old = this.defaultPkColumnValue;
		this.defaultPkColumnValue = value;
		this.firePropertyChanged(DEFAULT_PK_COLUMN_VALUE_PROPERTY, old, value);
	}

	protected String buildDefaultPkColumnValue() {
		return null; // TODO the default pk column value is determined by the runtime provider...
	}


	// ********** unique constraints **********

	public ListIterable<OrmUniqueConstraint> getUniqueConstraints() {
		return this.uniqueConstraintContainer.getContextElements();
	}

	public int getUniqueConstraintsSize() {
		return this.uniqueConstraintContainer.getContextElementsSize();
	}

	public OrmUniqueConstraint getUniqueConstraint(int index) {
		return this.uniqueConstraintContainer.getContextElement(index);
	}
	
	public OrmUniqueConstraint addUniqueConstraint() {
		return this.addUniqueConstraint(this.getUniqueConstraintsSize());
	}

	public OrmUniqueConstraint addUniqueConstraint(int index) {
		XmlUniqueConstraint xmlConstraint = this.buildXmlUniqueConstraint();
		OrmUniqueConstraint constraint = this.uniqueConstraintContainer.addContextElement(index, xmlConstraint);
		this.xmlGenerator.getUniqueConstraints().add(index, xmlConstraint);
		return constraint;
	}

	protected XmlUniqueConstraint buildXmlUniqueConstraint() {
		return OrmFactory.eINSTANCE.createXmlUniqueConstraint();
	}

	public void removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraintContainer.indexOfContextElement((OrmUniqueConstraint) uniqueConstraint));
	}

	public void removeUniqueConstraint(int index) {
		this.uniqueConstraintContainer.removeContextElement(index);
		this.xmlGenerator.getUniqueConstraints().remove(index);
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		this.uniqueConstraintContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlGenerator.getUniqueConstraints().move(targetIndex, sourceIndex);
	}

	protected OrmUniqueConstraint buildUniqueConstraint(XmlUniqueConstraint resourceUniqueConstraint) {
		return this.getContextNodeFactory().buildOrmUniqueConstraint(this, this, resourceUniqueConstraint);
	}

	protected void syncUniqueConstraints() {
		this.uniqueConstraintContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlUniqueConstraint> getXmlUniqueConstraints() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlUniqueConstraint>(this.xmlGenerator.getUniqueConstraints());
	}

	protected ContextListContainer<OrmUniqueConstraint, XmlUniqueConstraint> buildUniqueConstraintContainer() {
		UniqueConstraintContainer container = new UniqueConstraintContainer();
		container.initialize();
		return container;
	}

	/**
	 * unique constraint container
	 */
	protected class UniqueConstraintContainer
		extends ContextListContainer<OrmUniqueConstraint, XmlUniqueConstraint>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return UNIQUE_CONSTRAINTS_LIST;
		}
		@Override
		protected OrmUniqueConstraint buildContextElement(XmlUniqueConstraint resourceElement) {
			return GenericOrmTableGenerator.this.buildUniqueConstraint(resourceElement);
		}
		@Override
		protected ListIterable<XmlUniqueConstraint> getResourceElements() {
			return GenericOrmTableGenerator.this.getXmlUniqueConstraints();
		}
		@Override
		protected XmlUniqueConstraint getResourceElement(OrmUniqueConstraint contextElement) {
			return contextElement.getXmlUniqueConstraint();
		}
	}


	// ********** UniqueConstraint.Owner implementation **********

	public Iterable<String> getCandidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.jpa.db.Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.getSortedColumnIdentifiers() : EmptyIterable.<String>instance();
	}

	// ********** misc **********

	public Class<TableGenerator> getType() {
		return TableGenerator.class;
	}

	// ********** validation **********

	@Override
	protected boolean isEquivalentTo(DbGenerator generator) {
		return super.isEquivalentTo(generator)
				&& this.isEquivalentTo((TableGenerator) generator);
	}

	protected boolean isEquivalentTo(TableGenerator generator) {
		return ObjectTools.equals(this.specifiedTable, generator.getSpecifiedTable()) &&
				ObjectTools.equals(this.specifiedSchema, generator.getSpecifiedSchema()) &&
				ObjectTools.equals(this.specifiedCatalog, generator.getSpecifiedCatalog()) &&
				ObjectTools.equals(this.specifiedPkColumnName, generator.getSpecifiedPkColumnName()) &&
				ObjectTools.equals(this.specifiedValueColumnName, generator.getSpecifiedValueColumnName()) &&
				ObjectTools.equals(this.specifiedPkColumnValue, generator.getSpecifiedPkColumnValue()) &&
				this.uniqueConstrainsAreEquivalentTo(generator);
	}

	protected boolean uniqueConstrainsAreEquivalentTo(TableGenerator generator) {
		// get fixed lists of the unique constraints
		ArrayList<OrmUniqueConstraint> uniqueConstraints1 = ListTools.list(this.getUniqueConstraints());
		ArrayList<UniqueConstraint> uniqueConstraints2 = ListTools.list(generator.getUniqueConstraints());
		if (uniqueConstraints1.size() != uniqueConstraints2.size()) {
			return false;
		}
		for (int i = 0; i < uniqueConstraints1.size(); i++) {
			if ( ! uniqueConstraints1.get(i).isEquivalentTo(uniqueConstraints2.get(i))) {
				return false;
			}
		}
		return true;
	}
	

	// ********** metadata conversion **********

	public void convertFrom(JavaTableGenerator javaTableGenerator) {
		super.convertFrom(javaTableGenerator);
		this.setSpecifiedTable(javaTableGenerator.getSpecifiedTable());
		this.setSpecifiedSchema(javaTableGenerator.getSpecifiedSchema());
		this.setSpecifiedCatalog(javaTableGenerator.getSpecifiedCatalog());
		this.setSpecifiedPkColumnName(javaTableGenerator.getSpecifiedPkColumnName());
		this.setSpecifiedValueColumnName(javaTableGenerator.getSpecifiedValueColumnName());
		this.setSpecifiedPkColumnValue(javaTableGenerator.getSpecifiedPkColumnValue());
		for (JavaUniqueConstraint javaUniqueConstraint : javaTableGenerator.getUniqueConstraints()) {
			this.addUniqueConstraint().convertFrom(javaUniqueConstraint);
		}
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (OrmUniqueConstraint constraint : this.getUniqueConstraints()) {
			result = constraint.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * called if the database is connected:
	 * table, schema, catalog, pkColumnName, valueColumnName
	 */
	@Override
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		Iterable<String> result = super.getConnectedCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.tableTouches(pos)) {
			return this.getCandidateTables();
		}
		if (this.pkColumnNameTouches(pos)) {
			return this.getCandidateColumnNames();
		}
		if (this.valueColumnNameTouches(pos)) {
			return this.getCandidateColumnNames();
		}
		return null;
	}

	// ********** content assist: table

	protected boolean tableTouches(int pos) {
		return this.xmlGenerator.tableTouches(pos);
	}

	protected Iterable<String> getCandidateTables() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.getSortedTableIdentifiers() : EmptyIterable.<String> instance();
	}
	
	// ********** content assist: pkColumnName

	protected boolean pkColumnNameTouches(int pos) {
		return this.xmlGenerator.pkColumnNameTouches(pos);
	}

	protected Iterable<String> getCandidateColumnNames() {
		Table table = this.getDbTable();
		return (table != null) ? table.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** content assist: valueColumnName

	protected boolean valueColumnNameTouches(int pos) {
		return this.xmlGenerator.valueColumnNameTouches(pos);
	}
}
