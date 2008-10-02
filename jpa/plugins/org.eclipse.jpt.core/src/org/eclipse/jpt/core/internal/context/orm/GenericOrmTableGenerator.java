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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

/**
 * 
 */
public class GenericOrmTableGenerator
	extends AbstractOrmGenerator<XmlTableGenerator>
	implements OrmTableGenerator, UniqueConstraint.Owner
{
	protected String specifiedTable;
	protected String defaultTable;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected String specifiedPkColumnName;
	protected String defaultPkColumnName;

	protected String specifiedValueColumnName;
	protected String defaultValueColumnName;

	protected String specifiedPkColumnValue;
	protected String defaultPkColumnValue;

	protected final List<OrmUniqueConstraint> uniqueConstraints;


	// ********** constructor **********

	public GenericOrmTableGenerator(XmlContextNode parent, XmlTableGenerator resourceTableGenerator) {
		super(parent);
		this.uniqueConstraints = new ArrayList<OrmUniqueConstraint>();
		this.initialize(resourceTableGenerator);
	}


	// ********** table **********

	public String getTable() {
		return (this.specifiedTable != null) ? this.specifiedTable : this.defaultTable;
	}

	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	public void setSpecifiedTable(String table) {
		String old = this.specifiedTable;
		this.specifiedTable = table;
		this.getResourceGenerator().setTable(table);
		this.firePropertyChanged(SPECIFIED_TABLE_PROPERTY, old, table);
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


	// ********** schema **********

	@Override
	public String getSchema() {
		return (this.specifiedSchema != null) ? this.specifiedSchema : this.defaultSchema;
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		this.getResourceGenerator().setSchema(schema);
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
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


	// ********** catalog **********

	@Override
	public String getCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		this.getResourceGenerator().setCatalog(catalog);
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
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
		firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, catalog);
	}


	// ********** primary key column name **********

	public String getPkColumnName() {
		return (this.specifiedPkColumnName != null) ? this.specifiedPkColumnName : this.defaultPkColumnName;
	}

	public String getSpecifiedPkColumnName() {
		return this.specifiedPkColumnName;
	}
	
	public void setSpecifiedPkColumnName(String name) {
		String old = this.specifiedPkColumnName;
		this.specifiedPkColumnName = name;
		this.getResourceGenerator().setPkColumnName(name);
		this.firePropertyChanged(SPECIFIED_PK_COLUMN_NAME_PROPERTY, old, name);
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


	// ********** value column name **********

	public String getValueColumnName() {
		return (this.specifiedValueColumnName != null) ? this.specifiedValueColumnName : this.defaultValueColumnName;
	}

	public String getSpecifiedValueColumnName() {
		return this.specifiedValueColumnName;
	}

	public void setSpecifiedValueColumnName(String name) {
		String old = this.specifiedValueColumnName;
		this.specifiedValueColumnName = name;
		this.getResourceGenerator().setValueColumnName(name);
		this.firePropertyChanged(SPECIFIED_VALUE_COLUMN_NAME_PROPERTY, old, name);
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


	// ********** primary key column value **********

	public String getPkColumnValue() {
		return (this.specifiedPkColumnValue != null) ? this.specifiedPkColumnValue : this.defaultPkColumnValue;
	}

	public String getSpecifiedPkColumnValue() {
		return this.specifiedPkColumnValue;
	}

	public void setSpecifiedPkColumnValue(String value) {
		String old = this.specifiedPkColumnValue;
		this.specifiedPkColumnValue = value;
		this.getResourceGenerator().setPkColumnValue(value);
		this.firePropertyChanged(SPECIFIED_PK_COLUMN_VALUE_PROPERTY, old, value);
	}

	protected void setSpecifiedPkColumnValue_(String value) {
		String old = this.specifiedPkColumnValue;
		this.specifiedPkColumnValue = value;
		this.firePropertyChanged(SPECIFIED_PK_COLUMN_VALUE_PROPERTY, old, value);
	}

	public String getDefaultPkColumnValue() {
		return this.defaultPkColumnValue;
	}
	
	public void setDefaultPkColumnValue(String value) {
		String old = this.defaultPkColumnValue;
		this.defaultPkColumnValue = value;
		this.firePropertyChanged(DEFAULT_PK_COLUMN_VALUE_PROPERTY, old, value);
	}


	// ********** unique constraints **********
	
	public ListIterator<OrmUniqueConstraint> uniqueConstraints() {
		return new CloneListIterator<OrmUniqueConstraint>(this.uniqueConstraints);
	}
	
	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}
	
	public OrmUniqueConstraint addUniqueConstraint(int index) {
		XmlUniqueConstraint resourceUC = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		OrmUniqueConstraint contextUC = this.buildUniqueConstraint(resourceUC);
		this.uniqueConstraints.add(index, contextUC);
		this.getResourceGenerator().getUniqueConstraints().add(index, resourceUC);
		this.fireItemAdded(UNIQUE_CONSTRAINTS_LIST, index, contextUC);
		return contextUC;
	}
	
	protected void addUniqueConstraint(int index, OrmUniqueConstraint uniqueConstraint) {
		this.addItemToList(index, uniqueConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}
	
	protected void addUniqueConstraint(OrmUniqueConstraint uniqueConstraint) {
		this.addUniqueConstraint(this.uniqueConstraints.size(), uniqueConstraint);
	}
	
	public void removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraints.indexOf(uniqueConstraint));
	}
	
	public void removeUniqueConstraint(int index) {
		OrmUniqueConstraint uniqueConstraint = this.uniqueConstraints.remove(index);
		this.getResourceGenerator().getUniqueConstraints().remove(index);
		this.fireItemRemoved(UNIQUE_CONSTRAINTS_LIST, index, uniqueConstraint);
	}
	
	protected void removeUniqueConstraint_(OrmUniqueConstraint uniqueConstraint) {
		this.removeItemFromList(uniqueConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}
	
	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.uniqueConstraints, targetIndex, sourceIndex);
		this.getResourceGenerator().getUniqueConstraints().move(targetIndex, sourceIndex);
		this.fireItemMoved(UNIQUE_CONSTRAINTS_LIST, targetIndex, sourceIndex);		
	}


	//******************* UniqueConstraint.Owner implementation ******************

	public Iterator<String> candidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.db.Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.sortedColumnIdentifiers() : EmptyIterator.<String>instance();
	}


	// ********** resource => context **********

	@Override
	protected void initialize(XmlTableGenerator xmlTableGenerator) {
		super.initialize(xmlTableGenerator);
		this.specifiedTable = xmlTableGenerator.getTable();
		this.defaultSchema = this.buildDefaultSchema();
		this.specifiedSchema = xmlTableGenerator.getSchema();
		this.defaultCatalog = this.buildDefaultCatalog();
		this.specifiedCatalog = xmlTableGenerator.getCatalog();
		this.specifiedPkColumnName = xmlTableGenerator.getPkColumnName();
		this.specifiedValueColumnName = xmlTableGenerator.getValueColumnName();
		this.specifiedPkColumnValue = xmlTableGenerator.getPkColumnValue();
		this.initializeUniqueContraints(xmlTableGenerator);
	}
	
	protected void initializeUniqueContraints(XmlTableGenerator xmlTableGenerator) {
		if (xmlTableGenerator == null) {
			return;
		}
		for (XmlUniqueConstraint uniqueConstraint : xmlTableGenerator.getUniqueConstraints()) {
			this.uniqueConstraints.add(this.buildUniqueConstraint(uniqueConstraint));
		}
	}
	
	@Override
	public void update(XmlTableGenerator xmlTableGenerator) {
		super.update(xmlTableGenerator);
		this.setSpecifiedTable_(xmlTableGenerator.getTable());
		this.setDefaultSchema(this.buildDefaultSchema());
		this.setSpecifiedSchema_(xmlTableGenerator.getSchema());
		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setSpecifiedCatalog_(xmlTableGenerator.getCatalog());
		this.setSpecifiedPkColumnName_(xmlTableGenerator.getPkColumnName());
		this.setSpecifiedValueColumnName_(xmlTableGenerator.getValueColumnName());
		this.setSpecifiedPkColumnValue_(xmlTableGenerator.getPkColumnValue());
		// TODO defaults
		this.updateUniqueConstraints(xmlTableGenerator);
	}
	
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}
	
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}

	protected void updateUniqueConstraints(XmlTableGenerator tableGenerator) {
		ListIterator<OrmUniqueConstraint> contextConstraints = uniqueConstraints();
		ListIterator<XmlUniqueConstraint> resourceConstraints = EmptyListIterator.instance();
		if (tableGenerator != null) {
			resourceConstraints = new CloneListIterator<XmlUniqueConstraint>(tableGenerator.getUniqueConstraints());//prevent ConcurrentModificiationException
		}
		
		while (contextConstraints.hasNext()) {
			OrmUniqueConstraint contextConstraint = contextConstraints.next();
			if (resourceConstraints.hasNext()) {
				contextConstraint.update(resourceConstraints.next());
			}
			else {
				this.removeUniqueConstraint_(contextConstraint);
			}
		}
		
		while (resourceConstraints.hasNext()) {
			this.addUniqueConstraint(this.buildUniqueConstraint(resourceConstraints.next()));
		}
	}

	protected OrmUniqueConstraint buildUniqueConstraint(XmlUniqueConstraint resourceUniqueConstraint) {
		return this.getJpaFactory().buildOrmUniqueConstraint(this, this, resourceUniqueConstraint);
	}


	// ********** database stuff **********

	public Table getDbTable() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema == null) ? null : dbSchema.getTableForIdentifier(this.getTable());
	}

}
