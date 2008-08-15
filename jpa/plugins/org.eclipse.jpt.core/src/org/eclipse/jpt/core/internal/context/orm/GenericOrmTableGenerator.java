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
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
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

public class GenericOrmTableGenerator extends AbstractOrmGenerator<XmlTableGenerator> implements OrmTableGenerator, UniqueConstraint.Owner
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

	
	public GenericOrmTableGenerator(OrmJpaContextNode parent, XmlTableGenerator resourceTableGenerator) {
		super(parent);
		this.uniqueConstraints = new ArrayList<OrmUniqueConstraint>();
		this.initialize(resourceTableGenerator);
	}

	public String getTable() {
		return (this.getSpecifiedTable() == null) ? getDefaultTable() : this.getSpecifiedTable();
	}

	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	public void setSpecifiedTable(String newSpecifiedTable) {
		String oldSpecifiedTable = this.specifiedTable;
		this.specifiedTable = newSpecifiedTable;
		getResourceGenerator().setTable(newSpecifiedTable);
		firePropertyChanged(SPECIFIED_TABLE_PROPERTY, oldSpecifiedTable, newSpecifiedTable);
	}
	
	protected void setSpecifiedTable_(String newSpecifiedTable) {
		String oldSpecifiedTable = this.specifiedTable;
		this.specifiedTable = newSpecifiedTable;
		firePropertyChanged(SPECIFIED_TABLE_PROPERTY, oldSpecifiedTable, newSpecifiedTable);
	}

	public String getDefaultTable() {
		return this.defaultTable;
	}
	
	protected void setDefaultTable(String newDefaultTable) {
		String oldDefaultTable = this.defaultTable;
		this.defaultTable = newDefaultTable;
		firePropertyChanged(DEFAULT_TABLE_PROPERTY, oldDefaultTable, newDefaultTable);
	}

	public String getCatalog() {
		return (this.getSpecifiedCatalog() == null) ? getDefaultCatalog() : this.getSpecifiedCatalog();
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newSpecifiedCatalog;
		getResourceGenerator().setCatalog(newSpecifiedCatalog);
		firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, oldSpecifiedCatalog, newSpecifiedCatalog);
	}
	
	protected void setSpecifiedCatalog_(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newSpecifiedCatalog;
		firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, oldSpecifiedCatalog, newSpecifiedCatalog);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}
	
	protected void setDefaultCatalog(String newDefaultCatalog) {
		String oldDefaultCatalog = this.defaultCatalog;
		this.defaultCatalog = newDefaultCatalog;
		firePropertyChanged(TableGenerator.DEFAULT_CATALOG_PROPERTY, oldDefaultCatalog, newDefaultCatalog);
	}

	public String getSchema() {
		return (this.getSpecifiedSchema() == null) ? getDefaultSchema() : this.getSpecifiedSchema();
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String newSpecifiedSchema) {
		String oldSpecifiedSchema = this.specifiedSchema;
		this.specifiedSchema = newSpecifiedSchema;
		getResourceGenerator().setSchema(newSpecifiedSchema);
		firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
	}

	protected void setSpecifiedSchema_(String newSpecifiedSchema) {
		String oldSpecifiedSchema = this.specifiedSchema;
		this.specifiedSchema = newSpecifiedSchema;
		firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}
	
	protected void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = this.defaultSchema;
		this.defaultSchema = newDefaultSchema;
		firePropertyChanged(TableGenerator.DEFAULT_SCHEMA_PROPERTY, oldDefaultSchema, newDefaultSchema);
	}

	public String getPkColumnName() {
		return (this.getSpecifiedPkColumnName() == null) ? getDefaultPkColumnName() : this.getSpecifiedPkColumnName();
	}

	public String getSpecifiedPkColumnName() {
		return this.specifiedPkColumnName;
	}
	
	public void setSpecifiedPkColumnName(String newSpecifiedPkColumnName) {
		String oldSpecifiedPkColumnName = this.specifiedPkColumnName;
		this.specifiedPkColumnName = newSpecifiedPkColumnName;
		getResourceGenerator().setPkColumnName(newSpecifiedPkColumnName);
		firePropertyChanged(SPECIFIED_PK_COLUMN_NAME_PROPERTY, oldSpecifiedPkColumnName, newSpecifiedPkColumnName);
	}
	
	protected void setSpecifiedPkColumnName_(String newSpecifiedPkColumnName) {
		String oldSpecifiedPkColumnName = this.specifiedPkColumnName;
		this.specifiedPkColumnName = newSpecifiedPkColumnName;
		firePropertyChanged(SPECIFIED_PK_COLUMN_NAME_PROPERTY, oldSpecifiedPkColumnName, newSpecifiedPkColumnName);
	}

	public String getDefaultPkColumnName() {
		return this.defaultPkColumnName;
	}
	
	protected void setDefaultPkColumnName(String newDefaultPkColumnName) {
		String oldDefaultPkColumnName = this.defaultPkColumnName;
		this.defaultPkColumnName = newDefaultPkColumnName;
		firePropertyChanged(DEFAULT_PK_COLUMN_NAME_PROPERTY, oldDefaultPkColumnName, newDefaultPkColumnName);
	}

	public String getValueColumnName() {
		return (this.getSpecifiedValueColumnName() == null) ? getDefaultValueColumnName() : this.getSpecifiedValueColumnName();
	}

	public String getSpecifiedValueColumnName() {
		return this.specifiedValueColumnName;
	}

	public void setSpecifiedValueColumnName(String newSpecifiedValueColumnName) {
		String oldSpecifiedValueColumnName = this.specifiedValueColumnName;
		this.specifiedValueColumnName = newSpecifiedValueColumnName;
		getResourceGenerator().setValueColumnName(newSpecifiedValueColumnName);
		firePropertyChanged(SPECIFIED_VALUE_COLUMN_NAME_PROPERTY, oldSpecifiedValueColumnName, newSpecifiedValueColumnName);
	}

	protected void setSpecifiedValueColumnName_(String newSpecifiedValueColumnName) {
		String oldSpecifiedValueColumnName = this.specifiedValueColumnName;
		this.specifiedValueColumnName = newSpecifiedValueColumnName;
		firePropertyChanged(SPECIFIED_VALUE_COLUMN_NAME_PROPERTY, oldSpecifiedValueColumnName, newSpecifiedValueColumnName);
	}

	public String getDefaultValueColumnName() {
		return this.defaultValueColumnName;
	}
	
	protected void setDefaultValueColumnName(String newDefaultValueColumnName) {
		String oldDefaultValueColumnName = this.defaultValueColumnName;
		this.defaultValueColumnName = newDefaultValueColumnName;
		firePropertyChanged(DEFAULT_VALUE_COLUMN_NAME_PROPERTY, oldDefaultValueColumnName, newDefaultValueColumnName);
	}

	public String getPkColumnValue() {
		return (this.getSpecifiedPkColumnValue() == null) ? getDefaultPkColumnValue() : this.getSpecifiedPkColumnValue();
	}

	public String getSpecifiedPkColumnValue() {
		return this.specifiedPkColumnValue;
	}

	public void setSpecifiedPkColumnValue(String newSpecifiedPkColumnValue) {
		String oldSpecifiedPkColumnValue = this.specifiedPkColumnValue;
		this.specifiedPkColumnValue = newSpecifiedPkColumnValue;
		getResourceGenerator().setPkColumnValue(newSpecifiedPkColumnValue);
		firePropertyChanged(SPECIFIED_PK_COLUMN_VALUE_PROPERTY, oldSpecifiedPkColumnValue, newSpecifiedPkColumnValue);
	}

	protected void setSpecifiedPkColumnValue_(String newSpecifiedPkColumnValue) {
		String oldSpecifiedPkColumnValue = this.specifiedPkColumnValue;
		this.specifiedPkColumnValue = newSpecifiedPkColumnValue;
		firePropertyChanged(SPECIFIED_PK_COLUMN_VALUE_PROPERTY, oldSpecifiedPkColumnValue, newSpecifiedPkColumnValue);
	}

	public String getDefaultPkColumnValue() {
		return this.defaultPkColumnValue;
	}
	
	public void setDefaultPkColumnValue(String newDefaultPkColumnValue) {
		String oldDefaultPkColumnValue = this.defaultPkColumnValue;
		this.defaultPkColumnValue = newDefaultPkColumnValue;
		firePropertyChanged(DEFAULT_PK_COLUMN_VALUE_PROPERTY, oldDefaultPkColumnValue, newDefaultPkColumnValue);
	}
	
	
	// ********** unique constraints **********
	
	public ListIterator<OrmUniqueConstraint> uniqueConstraints() {
		return new CloneListIterator<OrmUniqueConstraint>(this.uniqueConstraints);
	}
	
	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}
	
	public OrmUniqueConstraint addUniqueConstraint(int index) {
		XmlUniqueConstraint resourceUniqueConstraint = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		OrmUniqueConstraint contextUniqueConstraint =  buildUniqueConstraint(resourceUniqueConstraint);
		this.uniqueConstraints.add(index, contextUniqueConstraint);
		getResourceGenerator().getUniqueConstraints().add(index, resourceUniqueConstraint);
		fireItemAdded(TableGenerator.UNIQUE_CONSTRAINTS_LIST, index, contextUniqueConstraint);
		return contextUniqueConstraint;
	}
	
	protected void addUniqueConstraint(int index, OrmUniqueConstraint uniqueConstraint) {
		addItemToList(index, uniqueConstraint, this.uniqueConstraints, TableGenerator.UNIQUE_CONSTRAINTS_LIST);
	}
	
	
	public void removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraints.indexOf(uniqueConstraint));
	}
	
	public void removeUniqueConstraint(int index) {
		OrmUniqueConstraint removedUniqueConstraint = this.uniqueConstraints.remove(index);
		getResourceGenerator().getUniqueConstraints().remove(index);
		fireItemRemoved(TableGenerator.UNIQUE_CONSTRAINTS_LIST, index, removedUniqueConstraint);
	}
	
	protected void removeUniqueConstraint_(OrmUniqueConstraint uniqueConstraint) {
		removeItemFromList(uniqueConstraint, this.uniqueConstraints, TableGenerator.UNIQUE_CONSTRAINTS_LIST);
	}
	
	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.uniqueConstraints, targetIndex, sourceIndex);
		this.getResourceGenerator().getUniqueConstraints().move(targetIndex, sourceIndex);
		fireItemMoved(TableGenerator.UNIQUE_CONSTRAINTS_LIST, targetIndex, sourceIndex);		
	}
	
	//******************* UniqueConstraint.Owner implementation ******************

	public Iterator<String> candidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.db.Table dbTable = getDbTable();
		if (dbTable != null) {
			return dbTable.columnNames();
		}
		return EmptyIterator.instance();
	}

	public Table getDbTable() {
		Schema schema = this.getDbSchema();
		return (schema == null) ? null : schema.getTableNamed(this.getTable());
	}

	public Schema getDbSchema() {
		return this.getDataSource().getSchemaNamed(this.getSchema());
	}
	
	// ********** orm resource model -> context model **********

	@Override
	protected void initialize(XmlTableGenerator tableGenerator) {
		super.initialize(tableGenerator);
		this.specifiedTable = this.specifiedTable(tableGenerator);
		this.specifiedCatalog = this.specifiedCatalog(tableGenerator);
		this.defaultCatalog = this.defaultCatalog();
		this.specifiedSchema = this.specifiedSchema(tableGenerator);
		this.defaultSchema = this.defaultSchema();
		this.specifiedPkColumnName = this.specifiedPkColumnName(tableGenerator);
		this.specifiedValueColumnName = this.specifiedValueColumnName(tableGenerator);
		this.specifiedPkColumnValue = this.specifiedPkColumnValue(tableGenerator);
		this.initializeUniqueContraints(tableGenerator);
	}
	
	protected void initializeUniqueContraints(XmlTableGenerator tableGenerator) {
		if (tableGenerator == null) {
			return;
		}
		for (XmlUniqueConstraint uniqueConstraint : tableGenerator.getUniqueConstraints()) {
			this.uniqueConstraints.add(buildUniqueConstraint(uniqueConstraint));
		}
	}
	
	@Override
	public void update(XmlTableGenerator tableGenerator) {
		super.update(tableGenerator);
		this.setSpecifiedTable_(this.specifiedTable(tableGenerator));
		this.setSpecifiedCatalog_(this.specifiedCatalog(tableGenerator));
		this.setDefaultCatalog(this.defaultCatalog());
		this.setSpecifiedSchema_(this.specifiedSchema(tableGenerator));
		this.setDefaultSchema(this.defaultSchema());
		this.setSpecifiedPkColumnName_(this.specifiedPkColumnName(tableGenerator));
		this.setSpecifiedValueColumnName_(this.specifiedValueColumnName(tableGenerator));
		this.setSpecifiedPkColumnValue_(this.specifiedPkColumnValue(tableGenerator));
		//TODO defaults
		this.updateUniqueConstraints(tableGenerator);
	}
	
	protected String specifiedTable(XmlTableGenerator tableGenerator) {
		return tableGenerator.getTable();
	}
	
	protected String specifiedCatalog(XmlTableGenerator tableGenerator) {
		return tableGenerator.getCatalog();
	}
	
	protected String specifiedSchema(XmlTableGenerator tableGenerator) {
		return tableGenerator.getSchema();
	}
	
	protected String specifiedPkColumnName(XmlTableGenerator tableGenerator) {
		return tableGenerator.getPkColumnName();
	}
	
	protected String specifiedValueColumnName(XmlTableGenerator tableGenerator) {
		return tableGenerator.getValueColumnName();
	}
	
	protected String specifiedPkColumnValue(XmlTableGenerator tableGenerator) {
		return tableGenerator.getPkColumnValue();
	}
	
	protected String defaultSchema() {
		return getEntityMappings().getSchema();
	}
	
	protected String defaultCatalog() {
		return getEntityMappings().getCatalog();
	}

	protected void updateUniqueConstraints(XmlTableGenerator tableGenerator) {
		ListIterator<OrmUniqueConstraint> contextUniqueConstraints = uniqueConstraints();
		ListIterator<XmlUniqueConstraint> resourceUniqueConstraints;
		if (tableGenerator == null) {
			resourceUniqueConstraints = EmptyListIterator.instance();
		}
		else {
			resourceUniqueConstraints = new CloneListIterator<XmlUniqueConstraint>(tableGenerator.getUniqueConstraints());//prevent ConcurrentModificiationException
		}
		
		while (contextUniqueConstraints.hasNext()) {
			OrmUniqueConstraint contextUniqueConstraint = contextUniqueConstraints.next();
			if (resourceUniqueConstraints.hasNext()) {
				contextUniqueConstraint.update(resourceUniqueConstraints.next());
			}
			else {
				removeUniqueConstraint_(contextUniqueConstraint);
			}
		}
		
		while (resourceUniqueConstraints.hasNext()) {
			addUniqueConstraint(uniqueConstraintsSize(), buildUniqueConstraint(resourceUniqueConstraints.next()));
		}
	}

	protected OrmUniqueConstraint buildUniqueConstraint(XmlUniqueConstraint resourceUniqueConstraint) {
		return getJpaFactory().buildOrmUniqueConstraint(this, this, resourceUniqueConstraint);
	}

}
