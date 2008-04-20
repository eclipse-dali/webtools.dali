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
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlBaseTable;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.NameTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

public abstract class AbstractOrmTable extends AbstractOrmJpaContextNode implements UniqueConstraint.Owner
{
	protected String specifiedName;

	protected String defaultName;

	protected String specifiedCatalog;

	protected String defaultCatalog;

	protected String specifiedSchema;

	protected String defaultSchema;
	
	protected final List<OrmUniqueConstraint> uniqueConstraints;


	protected AbstractOrmTable(OrmJpaContextNode parent) {
		super(parent);
		this.uniqueConstraints = new ArrayList<OrmUniqueConstraint>();
	}

	@Override
	public OrmJpaContextNode getParent() {
		return (OrmJpaContextNode) super.getParent();
	}
	
	public void initializeFrom(Table oldTable) {
		setSpecifiedName(oldTable.getSpecifiedName());
		setSpecifiedCatalog(oldTable.getSpecifiedCatalog());
		setSpecifiedSchema(oldTable.getSpecifiedSchema());
	}
	
	
	public String getName() {
		return (this.getSpecifiedName() == null) ? this.getDefaultName() : this.getSpecifiedName();
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}
	
	/**
	 * Return null if no table resource element exists
	 */
	protected abstract XmlBaseTable getTableResource();

	protected abstract void removeTableResource();
	
	protected abstract void addTableResource();
	
	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		if (oldSpecifiedName != newSpecifiedName) {
			if (this.getTableResource() != null) {
				this.getTableResource().setName(newSpecifiedName);						
				if (this.getTableResource().isAllFeaturesUnset()) {
					removeTableResource();
				}
			}
			else if (newSpecifiedName != null) {
				addTableResource();
				getTableResource().setName(newSpecifiedName);
			}
		}
		firePropertyChanged(Table.SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}
	
	protected void setSpecifiedName_(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		firePropertyChanged(Table.SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}

	public String getDefaultName() {
		return this.defaultName;
	}

	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		firePropertyChanged(Table.DEFAULT_NAME_PROPERTY, oldDefaultName, newDefaultName);
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
		if (oldSpecifiedCatalog != newSpecifiedCatalog) {
			if (this.getTableResource() != null) {
				this.getTableResource().setCatalog(newSpecifiedCatalog);						
				if (this.getTableResource().isAllFeaturesUnset()) {
					removeTableResource();
				}
			}
			else if (newSpecifiedCatalog != null) {
				addTableResource();
				getTableResource().setCatalog(newSpecifiedCatalog);
			}
		}
		firePropertyChanged(Table.SPECIFIED_CATALOG_PROPERTY, oldSpecifiedCatalog, newSpecifiedCatalog);
	}
	
	protected void setSpecifiedCatalog_(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newSpecifiedCatalog;
		firePropertyChanged(Table.SPECIFIED_CATALOG_PROPERTY, oldSpecifiedCatalog, newSpecifiedCatalog);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String newDefaultCatalog) {
		String oldDefaultCatalog = this.defaultCatalog;
		this.defaultCatalog = newDefaultCatalog;
		firePropertyChanged(Table.DEFAULT_CATALOG_PROPERTY, oldDefaultCatalog, newDefaultCatalog);
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
		if (oldSpecifiedSchema != newSpecifiedSchema) {
			if (this.getTableResource() != null) {
				this.getTableResource().setSchema(newSpecifiedSchema);						
				if (this.getTableResource().isAllFeaturesUnset()) {
					removeTableResource();
				}
			}
			else if (newSpecifiedSchema != null) {
				addTableResource();
				getTableResource().setSchema(newSpecifiedSchema);
			}
		}

		firePropertyChanged(Table.SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
	}
	
	protected void setSpecifiedSchema_(String newSpecifiedSchema) {
		String oldSpecifiedSchema = this.specifiedSchema;
		this.specifiedSchema = newSpecifiedSchema;
		firePropertyChanged(Table.SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}
	
	protected void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = this.defaultSchema;
		this.defaultSchema = newDefaultSchema;
		firePropertyChanged(Table.DEFAULT_SCHEMA_PROPERTY, oldDefaultSchema, newDefaultSchema);
	}

	
	// ********** unique constraints **********
	
	public ListIterator<OrmUniqueConstraint> uniqueConstraints() {
		return new CloneListIterator<OrmUniqueConstraint>(this.uniqueConstraints);
	}
	
	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}
	
	public OrmUniqueConstraint addUniqueConstraint(int index) {
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		OrmUniqueConstraint uniqueConstraint =  buildUniqueConstraint(uniqueConstraintResource);
		this.uniqueConstraints.add(index, uniqueConstraint);
		
		if (this.getTableResource() == null) {
			addTableResource();
		}
		
		getTableResource().getUniqueConstraints().add(index, uniqueConstraintResource);
		fireItemAdded(Table.UNIQUE_CONSTRAINTS_LIST, index, uniqueConstraint);
		return uniqueConstraint;
	}
	
	protected void addUniqueConstraint(int index, OrmUniqueConstraint uniqueConstraint) {
		addItemToList(index, uniqueConstraint, this.uniqueConstraints, Table.UNIQUE_CONSTRAINTS_LIST);
	}
	
	
	public void removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraints.indexOf(uniqueConstraint));
	}
	
	public void removeUniqueConstraint(int index) {
		OrmUniqueConstraint removedUniqueConstraint = this.uniqueConstraints.remove(index);
		getTableResource().getUniqueConstraints().remove(index);
		fireItemRemoved(Table.UNIQUE_CONSTRAINTS_LIST, index, removedUniqueConstraint);
	}
	
	protected void removeUniqueConstraint_(OrmUniqueConstraint uniqueConstraint) {
		removeItemFromList(uniqueConstraint, this.uniqueConstraints, Table.UNIQUE_CONSTRAINTS_LIST);
	}
	
	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.uniqueConstraints, targetIndex, sourceIndex);
		this.getTableResource().getUniqueConstraints().move(targetIndex, sourceIndex);
		fireItemMoved(Table.UNIQUE_CONSTRAINTS_LIST, targetIndex, sourceIndex);		
	}
	
	//******************* UniqueConstraint.Owner implementation ******************

	public Iterator<String> candidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.db.Table dbTable = getDbTable();
		if (dbTable != null) {
			return dbTable.columnNames();
		}
		return EmptyIterator.instance();
	}
	
	public org.eclipse.jpt.db.Table getDbTable() {
		Schema schema = this.getDbSchema();
		return (schema == null) ? null : schema.tableNamed(getName());
	}

	public Schema getDbSchema() {
		return getConnectionProfile().getDatabase().schemaNamed(getSchema());
	}

	public boolean hasResolvedSchema() {
		return getDbSchema() != null;
	}

	public boolean isResolved() {
		return getDbTable() != null;
	}
	
	protected void initialize(XmlBaseTable table) {
		this.specifiedName = this.specifiedName(table);
		this.specifiedSchema = this.specifiedSchema(table);
		this.specifiedCatalog = this.specifiedCatalog(table);
		this.defaultName = this.defaultName();
		this.defaultSchema = this.defaultSchema();
		this.defaultCatalog = this.defaultCatalog();
		this.initializeUniqueContraints(table);
	}
	
	protected void initializeUniqueContraints(XmlBaseTable table) {
		if (table == null) {
			return;
		}
		for (XmlUniqueConstraint uniqueConstraint : table.getUniqueConstraints()) {
			this.uniqueConstraints.add(buildUniqueConstraint(uniqueConstraint));
		}
	}
	
	protected void update(XmlBaseTable table) {
		this.setSpecifiedName_(this.specifiedName(table));
		this.setSpecifiedSchema_(this.specifiedSchema(table));
		this.setSpecifiedCatalog_(this.specifiedCatalog(table));
		this.setDefaultName(this.defaultName());
		this.setDefaultSchema(this.defaultSchema());
		this.setDefaultCatalog(this.defaultCatalog());
		this.updateUniqueConstraints(table);
	}

	protected String specifiedName(XmlBaseTable table) {
		return table == null ? null : table.getName();
	}
	
	protected String specifiedSchema(XmlBaseTable table) {
		return table == null ? null : table.getSchema();
	}
	
	protected String specifiedCatalog(XmlBaseTable table) {
		return table == null ? null : table.getCatalog();
	}
	
	protected abstract String defaultName();
	
	protected abstract String defaultSchema();
	
	protected abstract String defaultCatalog();
	
	protected void updateUniqueConstraints(XmlBaseTable table) {
		ListIterator<OrmUniqueConstraint> uniqueConstraints = uniqueConstraints();
		ListIterator<XmlUniqueConstraint> resourceUniqueConstraints;
		if (table == null) {
			resourceUniqueConstraints = EmptyListIterator.instance();
		}
		else {
			resourceUniqueConstraints = new CloneListIterator<XmlUniqueConstraint>(table.getUniqueConstraints());//prevent ConcurrentModificiationException
		}
		while (uniqueConstraints.hasNext()) {
			OrmUniqueConstraint uniqueConstraint = uniqueConstraints.next();
			if (resourceUniqueConstraints.hasNext()) {
				uniqueConstraint.update(resourceUniqueConstraints.next());
			}
			else {
				removeUniqueConstraint_(uniqueConstraint);
			}
		}
		
		while (resourceUniqueConstraints.hasNext()) {
			addUniqueConstraint(uniqueConstraintsSize(), buildUniqueConstraint(resourceUniqueConstraints.next()));
		}
	}

	protected OrmUniqueConstraint buildUniqueConstraint(XmlUniqueConstraint xmlUniqueConstraint) {
		return getJpaFactory().buildOrmUniqueConstraint(this, this, xmlUniqueConstraint);
	}
	

	public String qualifiedName() {
		return NameTools.buildQualifiedDatabaseObjectName(this.getCatalog(), this.getSchema(), this.getName());
	}

	protected TextRange getNameTextRange() {
		if (getTableResource() != null) {
			TextRange textRange = getTableResource().getNameTextRange();
			if (textRange != null) {
				return textRange;
			}
		}
		return this.getParent().getValidationTextRange(); 
	}
	
	protected TextRange getCatalogTextRange() {
		if (getTableResource() != null) {
			TextRange textRange = getTableResource().getCatalogTextRange();
			if (textRange != null) {
				return textRange;
			}
		}
		return this.getParent().getValidationTextRange(); 
	}
	
	protected TextRange getSchemaTextRange() {
		if (getTableResource() != null) {
			TextRange textRange = getTableResource().getSchemaTextRange();
			if (textRange != null) {
				return textRange;
			}
		}
		return this.getParent().getValidationTextRange(); 
	}
	
	public TextRange getValidationTextRange() {
		if (getTableResource() != null) {
			TextRange textRange = this.getTableResource().getValidationTextRange();
			if (textRange != null) {
				return textRange;
			}
		}
		return getParent().getValidationTextRange();
	}

	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(qualifiedName());
	}

	@Override
	public String displayString() {
		return qualifiedName();
	}
}