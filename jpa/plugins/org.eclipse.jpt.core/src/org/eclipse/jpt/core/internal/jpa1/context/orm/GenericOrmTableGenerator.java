/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmGenerator;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;

/**
 * <code>orm.xml</code> table generator
 */
public class GenericOrmTableGenerator
	extends AbstractOrmGenerator<XmlTableGenerator>
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

	protected final Vector<OrmUniqueConstraint> uniqueConstraints = new Vector<OrmUniqueConstraint>();
	protected final UniqueConstraintContainerAdapter uniqueConstraintContainerAdapter = new UniqueConstraintContainerAdapter();


	// ********** constructor **********

	public GenericOrmTableGenerator(XmlContextNode parent, XmlTableGenerator xmlTableGenerator) {
		super(parent, xmlTableGenerator);
		this.specifiedTable = xmlTableGenerator.getTable();
		this.specifiedSchema = xmlTableGenerator.getSchema();
		this.specifiedCatalog = xmlTableGenerator.getCatalog();
		this.specifiedPkColumnName = xmlTableGenerator.getPkColumnName();
		this.specifiedValueColumnName = xmlTableGenerator.getValueColumnName();
		this.specifiedPkColumnValue = xmlTableGenerator.getPkColumnValue();
		this.initializeUniqueContraints();
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

	public Iterable<OrmUniqueConstraint> getUniqueConstraints() {
		return new LiveCloneIterable<OrmUniqueConstraint>(this.uniqueConstraints);
	}

	public int getUniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}

	public OrmUniqueConstraint addUniqueConstraint() {
		return this.addUniqueConstraint(this.uniqueConstraints.size());
	}

	public OrmUniqueConstraint addUniqueConstraint(int index) {
		XmlUniqueConstraint xmlConstraint = this.buildXmlUniqueConstraint();
		OrmUniqueConstraint constraint = this.addUniqueConstraint_(index, xmlConstraint);
		this.xmlGenerator.getUniqueConstraints().add(index, xmlConstraint);
		return constraint;
	}

	protected XmlUniqueConstraint buildXmlUniqueConstraint() {
		return OrmFactory.eINSTANCE.createXmlUniqueConstraint();
	}

	public void removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraints.indexOf(uniqueConstraint));
	}

	public void removeUniqueConstraint(int index) {
		this.removeUniqueConstraint_(index);
		this.xmlGenerator.getUniqueConstraints().remove(index);
	}

	protected void removeUniqueConstraint_(int index) {
		this.removeItemFromList(index, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
		this.xmlGenerator.getUniqueConstraints().move(targetIndex, sourceIndex);
	}

	protected void initializeUniqueContraints() {
		for (XmlUniqueConstraint constraint : this.getXmlUniqueConstraints()) {
			this.uniqueConstraints.add(this.buildUniqueConstraint(constraint));
		}
	}

	protected OrmUniqueConstraint buildUniqueConstraint(XmlUniqueConstraint resourceUniqueConstraint) {
		return this.getContextNodeFactory().buildOrmUniqueConstraint(this, this, resourceUniqueConstraint);
	}

	protected void syncUniqueConstraints() {
		ContextContainerTools.synchronizeWithResourceModel(this.uniqueConstraintContainerAdapter);
	}

	protected Iterable<XmlUniqueConstraint> getXmlUniqueConstraints() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlUniqueConstraint>(this.xmlGenerator.getUniqueConstraints());
	}

	protected void moveUniqueConstraint_(int index, OrmUniqueConstraint uniqueConstraint) {
		this.moveItemInList(index, uniqueConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}

	protected OrmUniqueConstraint addUniqueConstraint_(int index, XmlUniqueConstraint xmlConstraint) {
		OrmUniqueConstraint constraint = this.buildUniqueConstraint(xmlConstraint);
		this.addItemToList(index, constraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
		return constraint;
	}

	protected void removeUniqueConstraint_(OrmUniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint_(this.uniqueConstraints.indexOf(uniqueConstraint));
	}

	/**
	 * unique constraint container adapter
	 */
	protected class UniqueConstraintContainerAdapter
		implements ContextContainerTools.Adapter<OrmUniqueConstraint, XmlUniqueConstraint>
	{
		public Iterable<OrmUniqueConstraint> getContextElements() {
			return GenericOrmTableGenerator.this.getUniqueConstraints();
		}
		public Iterable<XmlUniqueConstraint> getResourceElements() {
			return GenericOrmTableGenerator.this.getXmlUniqueConstraints();
		}
		public XmlUniqueConstraint getResourceElement(OrmUniqueConstraint contextElement) {
			return contextElement.getXmlUniqueConstraint();
		}
		public void moveContextElement(int index, OrmUniqueConstraint element) {
			GenericOrmTableGenerator.this.moveUniqueConstraint_(index, element);
		}
		public void addContextElement(int index, XmlUniqueConstraint resourceElement) {
			GenericOrmTableGenerator.this.addUniqueConstraint_(index, resourceElement);
		}
		public void removeContextElement(OrmUniqueConstraint element) {
			GenericOrmTableGenerator.this.removeUniqueConstraint_(element);
		}
	}


	// ********** UniqueConstraint.Owner implementation **********

	public Iterator<String> candidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.db.Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.getSortedColumnIdentifiers().iterator() : EmptyIterator.<String>instance();
	}

}
