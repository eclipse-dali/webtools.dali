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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlBaseTable;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.NameTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * 
 */
public abstract class AbstractOrmTable
	extends AbstractXmlContextNode
	implements Table, UniqueConstraint.Owner
{
	protected String specifiedName;
	protected String defaultName;

	protected String specifiedSchema;
	protected String defaultSchema;
	
	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected final List<OrmUniqueConstraint> uniqueConstraints;


	// ********** constructor **********

	protected AbstractOrmTable(XmlContextNode parent) {
		super(parent);
		this.uniqueConstraints = new ArrayList<OrmUniqueConstraint>();
	}


	// ********** abstract methods **********

	/**
	 * Return null if no resource table exists.
	 */
	protected abstract XmlBaseTable getResourceTable();

	/**
	 * Return the added resource table.
	 */
	protected abstract XmlBaseTable addResourceTable();

	protected abstract void removeResourceTable();

	protected abstract String buildDefaultName();
	
	protected abstract String buildDefaultSchema();
	
	protected abstract String buildDefaultCatalog();
	

	public boolean isResourceSpecified() {
		return this.getResourceTable() != null;
	}

	// ********** name **********

	public String getName() {
		return (this.specifiedName != null) ? this.specifiedName : this.defaultName;
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}
	
	public void setSpecifiedName(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		if (this.attributeValueHasChanged(old, name)) {
			XmlBaseTable resourceTable = this.getResourceTable();
			if (resourceTable == null) {
				resourceTable = this.addResourceTable();
			}
			resourceTable.setName(name);
			if (resourceTable.isUnset()) {
				this.removeResourceTable();
			}
			this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
		}
	}
	
	protected void setSpecifiedName_(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}

	public String getDefaultName() {
		return this.defaultName;
	}

	protected void setDefaultName(String name) {
		String old = this.defaultName;
		this.defaultName = name;
		this.firePropertyChanged(DEFAULT_NAME_PROPERTY, old, name);
	}


	// ********** schema **********

	public String getSchema() {
		return (this.specifiedSchema != null) ? this.specifiedSchema : this.defaultSchema;
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		if (this.attributeValueHasChanged(old, schema)) {
			XmlBaseTable resourceTable = this.getResourceTable();
			if (resourceTable == null) {
				resourceTable = this.addResourceTable();
			}
			resourceTable.setSchema(schema);
			if (resourceTable.isUnset()) {
				this.removeResourceTable();
			}
			this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
		}
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

	public String getCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		if (this.attributeValueHasChanged(old, catalog)) {
			XmlBaseTable resourceTable = this.getResourceTable();
			if (resourceTable == null) {
				resourceTable = this.addResourceTable();
			}
			resourceTable.setCatalog(catalog);
			if (resourceTable.isUnset()) {
				this.removeResourceTable();
			}
			this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
		}
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
		firePropertyChanged(DEFAULT_CATALOG_PROPERTY, oldDefaultCatalog, newDefaultCatalog);
	}


	// ********** unique constraints **********
	
	public @SuppressWarnings("unchecked") ListIterator<OrmUniqueConstraint> uniqueConstraints() {
		return new CloneListIterator<OrmUniqueConstraint>(this.uniqueConstraints);
	}

	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}
	
	public OrmUniqueConstraint addUniqueConstraint(int index) {
		XmlUniqueConstraint resourceConstraint = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		OrmUniqueConstraint contextConstraint =  this.buildUniqueConstraint(resourceConstraint);
		this.uniqueConstraints.add(index, contextConstraint);
		
		XmlBaseTable resourceTable = this.getResourceTable();
		if (resourceTable == null) {
			resourceTable = this.addResourceTable();
		}
		resourceTable.getUniqueConstraints().add(index, resourceConstraint);

		this.fireItemAdded(UNIQUE_CONSTRAINTS_LIST, index, contextConstraint);
		return contextConstraint;
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
		OrmUniqueConstraint removedUniqueConstraint = this.uniqueConstraints.remove(index);
		this.getResourceTable().getUniqueConstraints().remove(index);
		this.fireItemRemoved(UNIQUE_CONSTRAINTS_LIST, index, removedUniqueConstraint);
	}
	
	protected void removeUniqueConstraint_(OrmUniqueConstraint uniqueConstraint) {
		this.removeItemFromList(uniqueConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.uniqueConstraints, targetIndex, sourceIndex);
		this.getResourceTable().getUniqueConstraints().move(targetIndex, sourceIndex);
		this.fireItemMoved(UNIQUE_CONSTRAINTS_LIST, targetIndex, sourceIndex);		
	}
	

	// ********** convenience methods **********

	protected TextRange getTextRange(TextRange textRange) {
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}

	protected TextRange getNameTextRange() {
		return this.getTextRange(this.getResourceTableNameTextRange());
	}

	protected TextRange getResourceTableNameTextRange() {
		XmlBaseTable resourceTable = this.getResourceTable();
		return (resourceTable == null) ? null : resourceTable.getNameTextRange();
	}

	protected TextRange getSchemaTextRange() {
		return this.getTextRange(this.getResourceTableSchemaTextRange());
	}

	protected TextRange getResourceTableSchemaTextRange() {
		XmlBaseTable resourceTable = this.getResourceTable();
		return (resourceTable == null) ? null : resourceTable.getSchemaTextRange();
	}

	protected TextRange getCatalogTextRange() {
		return this.getTextRange(this.getResourceTableCatalogTextRange());
	}

	protected TextRange getResourceTableCatalogTextRange() {
		XmlBaseTable resourceTable = this.getResourceTable();
		return (resourceTable == null) ? null : resourceTable.getCatalogTextRange();
	}

	protected OrmUniqueConstraint buildUniqueConstraint(XmlUniqueConstraint resourceUniqueConstraint) {
		return this.getJpaFactory().buildOrmUniqueConstraint(this, this, resourceUniqueConstraint);
	}


	// ********** resource => context **********

	protected void initialize(XmlBaseTable xmlTable) {
		this.defaultName = this.buildDefaultName();
		this.specifiedName = this.getResourceTableName(xmlTable);

		this.defaultSchema = this.buildDefaultSchema();
		this.specifiedSchema = this.getResourceTableSchema(xmlTable);

		this.defaultCatalog = this.buildDefaultCatalog();
		this.specifiedCatalog = this.getResourceTableCatalog(xmlTable);

		this.initializeUniqueContraints(xmlTable);
	}
	
	protected void initializeUniqueContraints(XmlBaseTable xmlTable) {
		if (xmlTable == null) {
			return;
		}
		for (XmlUniqueConstraint uniqueConstraint : xmlTable.getUniqueConstraints()) {
			this.uniqueConstraints.add(this.buildUniqueConstraint(uniqueConstraint));
		}
	}

	protected void update(XmlBaseTable xmlTable) {
		this.setDefaultName(this.buildDefaultName());
		this.setSpecifiedName_(this.getResourceTableName(xmlTable));

		this.setDefaultSchema(this.buildDefaultSchema());
		this.setSpecifiedSchema_(this.getResourceTableSchema(xmlTable));

		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setSpecifiedCatalog_(this.getResourceTableCatalog(xmlTable));

		this.updateUniqueConstraints(xmlTable);
	}

	protected String getResourceTableName(XmlBaseTable xmlTable) {
		return (xmlTable == null) ? null : xmlTable.getName();
	}

	protected String getResourceTableSchema(XmlBaseTable xmlTable) {
		return (xmlTable == null) ? null : xmlTable.getSchema();
	}

	protected String getResourceTableCatalog(XmlBaseTable xmlTable) {
		return (xmlTable == null) ? null : xmlTable.getCatalog();
	}

	protected void updateUniqueConstraints(XmlBaseTable xmlTable) {
		Iterator<XmlUniqueConstraint> xmlConstraints = this.xmlUniqueConstraints(xmlTable);

		for (Iterator<OrmUniqueConstraint> contextConstraints = this.uniqueConstraints(); contextConstraints.hasNext(); ) {
			OrmUniqueConstraint contextConstraint = contextConstraints.next();
			if (xmlConstraints.hasNext()) {
				contextConstraint.update(xmlConstraints.next());
			} else {
				this.removeUniqueConstraint_(contextConstraint);
			}
		}
		
		while (xmlConstraints.hasNext()) {
			this.addUniqueConstraint(this.buildUniqueConstraint(xmlConstraints.next()));
		}
	}

	protected Iterator<XmlUniqueConstraint> xmlUniqueConstraints(XmlBaseTable xmlTable) {
		// make a copy of the XML constraints (to prevent ConcurrentModificationException)
		return (xmlTable == null) ? EmptyIterator.<XmlUniqueConstraint>instance()
				: new CloneIterator<XmlUniqueConstraint>(xmlTable.getUniqueConstraints());
	}

	public void initializeFrom(Table oldTable) {
		this.setSpecifiedName(oldTable.getSpecifiedName());
		this.setSpecifiedCatalog(oldTable.getSpecifiedCatalog());
		this.setSpecifiedSchema(oldTable.getSpecifiedSchema());
	}


	// ********** database stuff **********

	public org.eclipse.jpt.db.Table getDbTable() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema == null) ? null : dbSchema.getTableForIdentifier(this.getName());
	}

	public Schema getDbSchema() {
		SchemaContainer dbSchemaContainer = this.getDbSchemaContainer();
		return (dbSchemaContainer == null) ? null : dbSchemaContainer.getSchemaForIdentifier(this.getSchema());
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a *default* catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getDbSchemaContainer() {
		String catalog = this.getCatalog();
		return (catalog != null) ? this.getDbCatalog(catalog) : this.getDatabase();
	}

	public Catalog getDbCatalog() {
		String catalog = this.getCatalog();
		if (catalog == null) {
			return null;  // not even a default catalog (i.e. database probably does not support catalogs)
		}
		return this.getDbCatalog(catalog);
	}

	public boolean isResolved() {
		return this.getDbTable() != null;
	}

	public boolean hasResolvedSchema() {
		return this.getDbSchema() != null;
	}

	public boolean hasResolvedCatalog() {
		String catalog = this.getCatalog();
		if (catalog == null) {
			return true;  // not even a default catalog (i.e. database probably does not support catalogs)
		}
		return this.getDbCatalog(catalog) != null;
	}


	// ********** UniqueConstraint.Owner implementation **********

	public Iterator<String> candidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.db.Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.sortedColumnIdentifiers() : EmptyIterator.<String>instance();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getTextRange(this.getResourceTableValidationTextRange());
	}

	protected TextRange getResourceTableValidationTextRange() {
		XmlBaseTable resourceTable = this.getResourceTable();
		return (resourceTable == null) ? null : resourceTable.getValidationTextRange();
	}


	// ********** misc **********

	/**
	 * covariant override
	 */
	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.getQualifiedName());
	}

	protected String getQualifiedName() {
		return NameTools.buildQualifiedDatabaseObjectName(this.getCatalog(), this.getSchema(), this.getName());
	}

}
