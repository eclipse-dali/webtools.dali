/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.VirtualTable;
import org.eclipse.jpt.jpa.core.context.VirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmVirtualTable<T extends ReadOnlyTable>
	extends AbstractOrmXmlContextNode
	implements VirtualTable
{
	protected final Owner owner;

	protected final T overriddenTable;

	protected String specifiedName;
	protected String defaultName;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected final ContextListContainer<VirtualUniqueConstraint, ReadOnlyUniqueConstraint> uniqueConstraintContainer;


	protected AbstractOrmVirtualTable(JpaContextNode parent, Owner owner, T overridenTable) {
		super(parent);
		this.owner = owner;
		this.overriddenTable = overridenTable;
		this.uniqueConstraintContainer = this.buildUniqueConstraintContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();

		this.setSpecifiedName(this.buildSpecifiedName());
		this.setDefaultName(this.buildDefaultName());

		this.setSpecifiedSchema(this.buildSpecifiedSchema());
		this.setDefaultSchema(this.buildDefaultSchema());

		this.setSpecifiedCatalog(this.buildSpecifiedCatalog());
		this.setDefaultCatalog(this.buildDefaultCatalog());

		this.updateUniqueConstraints();
	}


	// ********** table **********

	/**
	 * This should never return <code>null</code>.
	 */
	public T getOverriddenTable() {
		return this.overriddenTable;
	}


	// ********** name **********

	public String getName() {
		return (this.specifiedName != null) ? this.specifiedName : this.defaultName;
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	protected void setSpecifiedName(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedName() {
		return this.getOverriddenTable().getSpecifiedName();
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

	protected void setSpecifiedSchema(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
	}

	protected String buildSpecifiedSchema() {
		return this.getOverriddenTable().getSchema();
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

	protected void setSpecifiedCatalog(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
	}

	protected String buildSpecifiedCatalog() {
		return this.getOverriddenTable().getCatalog();
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

	public ListIterable<VirtualUniqueConstraint> getUniqueConstraints() {
		return this.uniqueConstraintContainer.getContextElements();
	}

	public int getUniqueConstraintsSize() {
		return this.uniqueConstraintContainer.getContextElementsSize();
	}

	public VirtualUniqueConstraint getUniqueConstraint(int index) {
		return this.uniqueConstraintContainer.getContextElement(index);
	}

	protected void updateUniqueConstraints() {
		this.uniqueConstraintContainer.update();
	}

	protected ListIterable<ReadOnlyUniqueConstraint> getOverriddenUniqueConstraints() {
		return new SuperListIterableWrapper<ReadOnlyUniqueConstraint>(this.getOverriddenTable().getUniqueConstraints());
	}

	protected void moveUniqueConstraint(int index, VirtualUniqueConstraint constraint) {
		this.uniqueConstraintContainer.moveContextElement(index, constraint);
	}

	protected VirtualUniqueConstraint addUniqueConstraint(int index, ReadOnlyUniqueConstraint uniqueConstraint) {
		return this.uniqueConstraintContainer.addContextElement(index, uniqueConstraint);
	}

	protected VirtualUniqueConstraint buildUniqueConstraint(ReadOnlyUniqueConstraint uniqueConstraint) {
		return this.getContextNodeFactory().buildOrmVirtualUniqueConstraint(this, uniqueConstraint);
	}

	protected void removeUniqueConstraint(VirtualUniqueConstraint constraint) {
		this.uniqueConstraintContainer.removeContextElement(constraint);
	}

	protected ContextListContainer<VirtualUniqueConstraint, ReadOnlyUniqueConstraint> buildUniqueConstraintContainer() {
		return new UniqueConstraintContainer();
	}

	/**
	 * unique constraint container
	 */
	protected class UniqueConstraintContainer
		extends ContextListContainer<VirtualUniqueConstraint, ReadOnlyUniqueConstraint>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return UNIQUE_CONSTRAINTS_LIST;
		}
		@Override
		protected VirtualUniqueConstraint buildContextElement(ReadOnlyUniqueConstraint resourceElement) {
			return AbstractOrmVirtualTable.this.buildUniqueConstraint(resourceElement);
		}
		@Override
		protected ListIterable<ReadOnlyUniqueConstraint> getResourceElements() {
			return AbstractOrmVirtualTable.this.getOverriddenUniqueConstraints();
		}
		@Override
		protected ReadOnlyUniqueConstraint getResourceElement(VirtualUniqueConstraint contextElement) {
			return contextElement.getOverriddenUniqueConstraint();
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

	public boolean schemaIsResolved() {
		return this.getDbSchema() != null;
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

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public boolean catalogIsResolved() {
		String catalog = this.getCatalog();
		return (catalog == null) || (this.resolveDbCatalog(catalog) != null);
	}

	public boolean isResolved() {
		return this.getDbTable() != null;
	}

	protected boolean hasResolvedSchema() {
		return this.getDbSchema() != null;
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	protected boolean hasResolvedCatalog() {
		String catalog = this.getCatalog();
		return (catalog == null) || (this.resolveDbCatalog(catalog) != null);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.buildTableValidator().validate(messages, reporter);
	}

	protected JptValidator buildTableValidator() {
		return this.owner.buildTableValidator(this);
	}

	public TextRange getValidationTextRange() {
		return this.getParent().getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange();
	}

	public TextRange getSchemaTextRange() {
		return this.getValidationTextRange();
	}

	public TextRange getCatalogTextRange() {
		return this.getValidationTextRange();
	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.buildQualifiedName());
	}

	protected String buildQualifiedName() {
		return NameTools.buildQualifiedName(this.getCatalog(), this.getSchema(), this.getName());
	}
}
