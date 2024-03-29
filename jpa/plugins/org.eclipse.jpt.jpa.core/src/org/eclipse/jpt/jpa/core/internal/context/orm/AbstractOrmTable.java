/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.NameTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.VirtualTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlTable;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> table, secondary table, join table, or collection table
 * <p>
 * <strong>NB:</strong> any subclass that directly holds its XML table must:<ul>
 * <li>call the "super" constructor that takes an XML table
 *     {@link #AbstractOrmTable(org.eclipse.jpt.jpa.core.context.Table.ParentAdapter, AbstractXmlTable)}
 * <li>override {@link #setXmlTable(AbstractXmlTable)} to set the XML table
 *     so it is in place before the table's state (e.g. {@link #specifiedName})
 *     is initialized
 * </ul>
 */
public abstract class AbstractOrmTable<P extends JpaContextModel, PA extends Table.ParentAdapter<P>, X extends AbstractXmlTable>
	extends AbstractOrmXmlContextModel<P>
	implements OrmSpecifiedTable, SpecifiedUniqueConstraint.Parent
{
	protected final PA parentAdapter;

	protected String specifiedName;
	protected String defaultName;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected final ContextListContainer<OrmSpecifiedUniqueConstraint, XmlUniqueConstraint> uniqueConstraintContainer;


	// ********** constructor/initialization **********

	protected AbstractOrmTable(PA parentAdapter) {
		this(parentAdapter, null);
	}

	protected AbstractOrmTable(PA parentAdapter, X xmlTable) {
		super(parentAdapter.getTableParent());
		this.parentAdapter = parentAdapter;
		this.setXmlTable(xmlTable);
		this.specifiedName = this.buildSpecifiedName();
		this.specifiedSchema = this.buildSpecifiedSchema();
		this.specifiedCatalog = this.buildSpecifiedCatalog();
		this.uniqueConstraintContainer = this.buildUniqueConstraintContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedName_(this.buildSpecifiedName());
		this.setSpecifiedSchema_(this.buildSpecifiedSchema());
		this.setSpecifiedCatalog_(this.buildSpecifiedCatalog());
		this.syncUniqueConstraints(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultName(this.buildDefaultName());
		this.setDefaultSchema(this.buildDefaultSchema());
		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.updateModels(this.getUniqueConstraints(), monitor);
	}


	// ********** XML table **********

	/**
	 * Return null if XML table does not exists.
	 */
	protected abstract X getXmlTable();

	/**
	 * see class comment...
	 */
	protected void setXmlTable(X xmlTable) {
		if (xmlTable != null) {
			throw new IllegalArgumentException("this method must be overridden if the XML table is not null: " + xmlTable); //$NON-NLS-1$
		}
	}

	/**
	 * Build the XML table if it does not exist.
	 */
	protected X getXmlTableForUpdate() {
		X xmlTable = this.getXmlTable();
		return (xmlTable != null) ? xmlTable : this.buildXmlTable();
	}

	protected abstract X buildXmlTable();

	protected void removeXmlTableIfUnset() {
		if (this.getXmlTable().isUnset()) {
			this.removeXmlTable();
		}
	}

	protected abstract void removeXmlTable();

	public boolean isSpecifiedInResource() {
		return this.getXmlTable() != null;
	}


	// ********** name **********

	public String getName() {
		return (this.specifiedName != null) ? this.specifiedName : this.defaultName;
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String name) {
		if (ObjectTools.notEquals(this.specifiedName, name)) {
			X xmlTable = this.getXmlTableForUpdate();
			this.setSpecifiedName_(name);
			xmlTable.setName(name);
			this.removeXmlTableIfUnset();
		}
	}

	protected void setSpecifiedName_(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedName() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ? null : xmlTable.getName();
	}

	public String getDefaultName() {
		return this.defaultName;
	}

	protected void setDefaultName(String name) {
		String old = this.defaultName;
		this.defaultName = name;
		this.firePropertyChanged(DEFAULT_NAME_PROPERTY, old, name);
	}

	protected abstract String buildDefaultName();


	// ********** schema **********

	public String getSchema() {
		return (this.specifiedSchema != null) ? this.specifiedSchema : this.defaultSchema;
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String schema) {
		if (ObjectTools.notEquals(this.specifiedSchema, schema)) {
			X xmlTable = this.getXmlTableForUpdate();
			this.setSpecifiedSchema_(schema);
			xmlTable.setSchema(schema);
			this.removeXmlTableIfUnset();
		}
	}

	protected void setSpecifiedSchema_(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
	}

	protected String buildSpecifiedSchema() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ? null : xmlTable.getSchema();
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	protected void setDefaultSchema(String schema) {
		String old = this.defaultSchema;
		this.defaultSchema = schema;
		this.firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, old, schema);
	}

	protected abstract String buildDefaultSchema();


	// ********** catalog **********

	public String getCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String catalog) {
		if (ObjectTools.notEquals(this.specifiedCatalog, catalog)) {
			X xmlTable = this.getXmlTableForUpdate();
			this.setSpecifiedCatalog_(catalog);
			xmlTable.setCatalog(catalog);
			this.removeXmlTableIfUnset();
		}
	}

	protected void setSpecifiedCatalog_(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
	}

	protected String buildSpecifiedCatalog() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ? null : xmlTable.getCatalog();
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String catalog) {
		String old = this.defaultCatalog;
		this.defaultCatalog = catalog;
		this.firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, catalog);
	}

	protected abstract String buildDefaultCatalog();


	// ********** unique constraints **********

	public ListIterable<OrmSpecifiedUniqueConstraint> getUniqueConstraints() {
		return this.uniqueConstraintContainer;
	}

	public int getUniqueConstraintsSize() {
		return this.uniqueConstraintContainer.size();
	}

	public OrmSpecifiedUniqueConstraint getUniqueConstraint(int index) {
		return this.uniqueConstraintContainer.get(index);
	}

	public OrmSpecifiedUniqueConstraint addUniqueConstraint() {
		return this.addUniqueConstraint(this.getUniqueConstraintsSize());
	}

	public OrmSpecifiedUniqueConstraint addUniqueConstraint(int index) {
		X xmlTable = this.getXmlTableForUpdate();
		XmlUniqueConstraint xmlConstraint = this.buildXmlUniqueConstraint();
		OrmSpecifiedUniqueConstraint constraint = this.uniqueConstraintContainer.addContextElement(index, xmlConstraint);
		xmlTable.getUniqueConstraints().add(index, xmlConstraint);
		return constraint;
	}

	protected XmlUniqueConstraint buildXmlUniqueConstraint() {
		return OrmFactory.eINSTANCE.createXmlUniqueConstraint();
	}

	public void removeUniqueConstraint(SpecifiedUniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraintContainer.indexOf((OrmSpecifiedUniqueConstraint) uniqueConstraint));
	}

	public void removeUniqueConstraint(int index) {
		this.uniqueConstraintContainer.remove(index);
		this.getXmlTable().getUniqueConstraints().remove(index);
		this.removeXmlTableIfUnset();
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		this.uniqueConstraintContainer.move(targetIndex, sourceIndex);
		this.getXmlTable().getUniqueConstraints().move(targetIndex, sourceIndex);
	}

	protected OrmSpecifiedUniqueConstraint buildUniqueConstraint(XmlUniqueConstraint xmlConstraint) {
		return this.getContextModelFactory().buildOrmUniqueConstraint(this, xmlConstraint);
	}

	protected void syncUniqueConstraints(IProgressMonitor monitor) {
		this.uniqueConstraintContainer.synchronizeWithResourceModel(monitor);
	}
	protected ListIterable<XmlUniqueConstraint> getXmlUniqueConstraints() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ?
				EmptyListIterable.<XmlUniqueConstraint>instance() :
				// clone to reduce chance of concurrency problems
				IterableTools.cloneLive(xmlTable.getUniqueConstraints());
	}

	protected ContextListContainer<OrmSpecifiedUniqueConstraint, XmlUniqueConstraint> buildUniqueConstraintContainer() {
		return this.buildSpecifiedContextListContainer(UNIQUE_CONSTRAINTS_LIST, new UniqueConstraintContainerAdapter());
	}

	/**
	 * unique constraint container adapter
	 */
	public class UniqueConstraintContainerAdapter
		extends AbstractContainerAdapter<OrmSpecifiedUniqueConstraint, XmlUniqueConstraint>
	{
		public OrmSpecifiedUniqueConstraint buildContextElement(XmlUniqueConstraint resourceElement) {
			return AbstractOrmTable.this.buildUniqueConstraint(resourceElement);
		}
		public ListIterable<XmlUniqueConstraint> getResourceElements() {
			return AbstractOrmTable.this.getXmlUniqueConstraints();
		}
		public XmlUniqueConstraint extractResourceElement(OrmSpecifiedUniqueConstraint contextElement) {
			return contextElement.getXmlUniqueConstraint();
		}
	}


	// ********** database stuff **********

	public org.eclipse.jpt.jpa.db.Table getDbTable() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema == null) ? null : dbSchema.getTableForIdentifier(this.getName());
	}

	public Schema getDbSchema() {
		SchemaContainer dbSchemaContainer = this.getDbSchemaContainer();
		return (dbSchemaContainer == null) ? null : dbSchemaContainer.getSchemaForIdentifier(this.getSchema());
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em> catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getDbSchemaContainer() {
		String catalog = this.getCatalog();
		return (catalog != null) ? this.resolveDbCatalog(catalog) : this.getDatabase();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getDbCatalog() {
		String catalog = this.getCatalog();
		return (catalog == null) ? null : this.resolveDbCatalog(catalog);
	}

	public boolean isResolved() {
		return this.getDbTable() != null;
	}

	public boolean schemaIsResolved() {
		return this.getDbSchema() != null;
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public boolean catalogIsResolved() {
		String catalog = this.getCatalog();
		return (catalog == null) || (this.resolveDbCatalog(catalog) != null);
	}


	// ********** SpecifiedUniqueConstraint.Parent implementation **********

	public Iterable<String> getCandidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.jpa.db.Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.getSortedColumnIdentifiers() : EmptyIterable.<String>instance();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.buildTableValidator().validate(messages, reporter);
	}

	protected JpaValidator buildTableValidator() {
		return this.parentAdapter.buildTableValidator(this);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlTableValidationTextRange();
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}

	protected TextRange getXmlTableValidationTextRange() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ? null : xmlTable.getValidationTextRange();
	}

	public TextRange getNameValidationTextRange() {
		return this.getValidationTextRange(this.getXmlTableNameTextRange());
	}

	protected TextRange getXmlTableNameTextRange() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ? null : xmlTable.getNameTextRange();
	}

	public TextRange getSchemaValidationTextRange() {
		return this.getValidationTextRange(this.getXmlTableSchemaTextRange());
	}

	protected TextRange getXmlTableSchemaTextRange() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ? null : xmlTable.getSchemaTextRange();
	}

	public TextRange getCatalogValidationTextRange() {
		return this.getValidationTextRange(this.getXmlTableCatalogTextRange());
	}

	protected TextRange getXmlTableCatalogTextRange() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ? null : xmlTable.getCatalogTextRange();
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (OrmSpecifiedUniqueConstraint constraint : this.getUniqueConstraints()) {
			result = constraint.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * called if the database is connected:
	 * name, schema, catalog
	 */
	@Override
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		Iterable<String> result = super.getConnectedCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.tableNameTouches(pos)) {
			return this.getCandidateTableNames();
		}
		if (this.schemaTouches(pos)) {
			return this.getCandidateSchemata();
		}
		if (this.catalogTouches(pos)) {
			return this.getCandidateCatalogs();
		}
		return null;
	}

	// ********* content assist : table
	
	protected boolean tableNameTouches(int pos) {
		X table = this.getXmlTable();
		return (table != null) && (table.nameTouches(pos));
	}

	protected Iterable<String> getCandidateTableNames() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.getSortedTableIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********* content assist : schema
	
	protected boolean schemaTouches(int pos) {
		X table = this.getXmlTable();
		return (table != null) && (table.schemaTouches(pos));
	}

	protected Iterable<String> getCandidateSchemata() {
		if (this.getDbSchemaContainer() == null) {
			return EmptyIterable.<String>instance();
		}
		return this.getDbSchemaContainer().getSortedSchemaIdentifiers();
	}

	// ********* content assist : catalog
	
	protected boolean catalogTouches(int pos) {
		X table = this.getXmlTable();
		return (table != null) && (table.catalogTouches(pos));
	}

	protected Iterable<String> getCandidateCatalogs() {
		Database db = this.getDatabase();
		return (db != null) ? db.getSortedCatalogIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** misc **********

	protected void initializeFrom(Table oldTable) {
		this.initializeFrom_(oldTable);
	}

	protected void initializeFrom(VirtualTable virtualTable) {
		this.initializeFrom_(virtualTable);
	}

	protected void initializeFrom_(Table oldTable) {
		this.setSpecifiedName(oldTable.getSpecifiedName());
		this.setSpecifiedCatalog(oldTable.getSpecifiedCatalog());
		this.setSpecifiedSchema(oldTable.getSpecifiedSchema());
		for (UniqueConstraint constraint : oldTable.getUniqueConstraints()) {
			this.addUniqueConstraint().initializeFrom(constraint);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.buildQualifiedName());
	}

	protected String buildQualifiedName() {
		return NameTools.buildQualifiedName(this.getCatalog(), this.getSchema(), this.getName());
	}

}
