/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.NameTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlTable;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> table, secondary table, join table, or collection table
 * <p>
 * <strong>NB:</strong> any subclass that directly holds its XML table must:<ul>
 * <li>call the "super" constructor that takes an XML table
 *     {@link #AbstractOrmTable(XmlContextNode, Owner, AbstractXmlTable)}
 * <li>override {@link #setXmlTable(AbstractXmlTable)} to set the XML table
 *     so it is in place before the table's state (e.g. {@link #specifiedName})
 *     is initialized
 * </ul>
 */
public abstract class AbstractOrmTable<X extends AbstractXmlTable>
	extends AbstractOrmXmlContextNode
	implements OrmTable, UniqueConstraint.Owner
{
	protected final Owner owner;

	protected String specifiedName;
	protected String defaultName;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected final UniqueConstraintContainer uniqueConstraintContainer;


	// ********** constructor/initialization **********

	protected AbstractOrmTable(XmlContextNode parent, Owner owner) {
		this(parent, owner, null);
	}

	protected AbstractOrmTable(XmlContextNode parent, Owner owner, X xmlTable) {
		super(parent);
		this.owner = owner;
		this.setXmlTable(xmlTable);
		this.specifiedName = this.buildSpecifiedName();
		this.specifiedSchema = this.buildSpecifiedSchema();
		this.specifiedCatalog = this.buildSpecifiedCatalog();
		this.uniqueConstraintContainer = new UniqueConstraintContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedName_(this.buildSpecifiedName());
		this.setSpecifiedSchema_(this.buildSpecifiedSchema());
		this.setSpecifiedCatalog_(this.buildSpecifiedCatalog());
		this.syncUniqueConstraints();
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultName(this.buildDefaultName());
		this.setDefaultSchema(this.buildDefaultSchema());
		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.updateNodes(this.getUniqueConstraints());
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
		if (this.valuesAreDifferent(this.specifiedName, name)) {
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
		if (this.valuesAreDifferent(this.specifiedSchema, schema)) {
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
		if (this.valuesAreDifferent(this.specifiedCatalog, catalog)) {
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
		X xmlTable = this.getXmlTableForUpdate();
		XmlUniqueConstraint xmlConstraint = this.buildXmlUniqueConstraint();
		OrmUniqueConstraint constraint = this.uniqueConstraintContainer.addContextElement(index, xmlConstraint);
		xmlTable.getUniqueConstraints().add(index, xmlConstraint);
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
		this.getXmlTable().getUniqueConstraints().remove(index);
		this.removeXmlTableIfUnset();
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		this.uniqueConstraintContainer.moveContextElement(targetIndex, sourceIndex);
		this.getXmlTable().getUniqueConstraints().move(targetIndex, sourceIndex);
	}

	protected OrmUniqueConstraint buildUniqueConstraint(XmlUniqueConstraint xmlConstraint) {
		return this.getContextNodeFactory().buildOrmUniqueConstraint(this, this, xmlConstraint);
	}

	protected void syncUniqueConstraints() {
		this.uniqueConstraintContainer.synchronizeWithResourceModel();
	}
	protected ListIterable<XmlUniqueConstraint> getXmlUniqueConstraints() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ?
				EmptyListIterable.<XmlUniqueConstraint>instance() :
				// clone to reduce chance of concurrency problems
				new LiveCloneListIterable<XmlUniqueConstraint>(xmlTable.getUniqueConstraints());
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
			return AbstractOrmTable.this.buildUniqueConstraint(resourceElement);
		}
		@Override
		protected ListIterable<XmlUniqueConstraint> getResourceElements() {
			return AbstractOrmTable.this.getXmlUniqueConstraints();
		}
		@Override
		protected XmlUniqueConstraint getResourceElement(OrmUniqueConstraint contextElement) {
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


	// ********** UniqueConstraint.Owner implementation **********

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

	protected JptValidator buildTableValidator() {
		return this.owner.buildTableValidator(this, this.buildTextRangeResolver());
	}

	protected TableTextRangeResolver buildTextRangeResolver() {
		return new OrmTableTextRangeResolver(this);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlTableValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}

	protected TextRange getXmlTableValidationTextRange() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ? null : xmlTable.getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.getXmlTableNameTextRange());
	}

	protected TextRange getXmlTableNameTextRange() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ? null : xmlTable.getNameTextRange();
	}

	public TextRange getSchemaTextRange() {
		return this.getValidationTextRange(this.getXmlTableSchemaTextRange());
	}

	protected TextRange getXmlTableSchemaTextRange() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ? null : xmlTable.getSchemaTextRange();
	}

	public TextRange getCatalogTextRange() {
		return this.getValidationTextRange(this.getXmlTableCatalogTextRange());
	}

	protected TextRange getXmlTableCatalogTextRange() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ? null : xmlTable.getCatalogTextRange();
	}


	// ********** misc **********

	/**
	 * covariant override
	 */
	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}

	protected void initializeFrom(ReadOnlyTable oldTable) {
		this.setSpecifiedName(oldTable.getSpecifiedName());
		this.setSpecifiedCatalog(oldTable.getSpecifiedCatalog());
		this.setSpecifiedSchema(oldTable.getSpecifiedSchema());
		for (ReadOnlyUniqueConstraint constraint : oldTable.getUniqueConstraints()) {
			this.addUniqueConstraint().initializeFrom(constraint);
		}
	}

	protected void initializeFromVirtual(ReadOnlyTable virtualTable) {
		this.setSpecifiedName(virtualTable.getName());
		// ignore other settings?
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.buildQualifiedName());
	}

	protected String buildQualifiedName() {
		return NameTools.buildQualifiedDatabaseObjectName(this.getCatalog(), this.getSchema(), this.getName());
	}

}
