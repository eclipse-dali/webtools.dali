/*******************************************************************************
 * Copyright (c) 2010, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.NameTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.VirtualTable;
import org.eclipse.jpt.jpa.core.context.VirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmVirtualTable<P extends JpaContextModel, PA extends Table.ParentAdapter<P>, T extends Table>
	extends AbstractOrmXmlContextModel<P>
	implements VirtualTable
{
	protected final PA parentAdapter;

	protected final T overriddenTable;

	protected String specifiedName;
	protected String defaultName;
	protected String name;

	protected String specifiedSchema;
	protected String defaultSchema;
	protected String schema;

	protected String specifiedCatalog;
	protected String defaultCatalog;
	protected String catalog;

	protected final ContextListContainer<VirtualUniqueConstraint, UniqueConstraint> uniqueConstraintContainer;


	protected AbstractOrmVirtualTable(PA parentAdapter, T overriddenTable) {
		super(parentAdapter.getTableParent());
		this.parentAdapter = parentAdapter;
		this.overriddenTable = overriddenTable;
		this.uniqueConstraintContainer = this.buildUniqueConstraintContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);

		this.setSpecifiedName(this.buildSpecifiedName());
		this.setDefaultName(this.buildDefaultName());
		this.setName(this.buildName());

		this.setSpecifiedSchema(this.buildSpecifiedSchema());
		this.setDefaultSchema(this.buildDefaultSchema());
		this.setSchema(this.buildSchema());

		this.setSpecifiedCatalog(this.buildSpecifiedCatalog());
		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setCatalog(this.buildCatalog());

		this.updateUniqueConstraints(monitor);
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
		return this.name;
	}

	protected void setName(String name) {
		String old = this.name;
		this.firePropertyChanged(NAME_PROPERTY, old, this.name = name);
	}

	protected String buildName() {
		return (this.specifiedName != null) ? this.specifiedName : this.defaultName;
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	protected void setSpecifiedName(String name) {
		String old = this.specifiedName;
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, this.specifiedName = name);
	}

	protected String buildSpecifiedName() {
		return this.getOverriddenTable().getSpecifiedName();
	}

	public String getDefaultName() {
		return this.defaultName;
	}

	protected void setDefaultName(String name) {
		String old = this.defaultName;
		this.firePropertyChanged(DEFAULT_NAME_PROPERTY, old, this.defaultName = name);
	}

	protected abstract String buildDefaultName();


	// ********** schema **********

	public String getSchema() {
		return this.schema;
	}

	protected void setSchema(String schema) {
		String old = this.schema;
		this.firePropertyChanged(SCHEMA_PROPERTY, old, this.schema = schema);
	}

	protected String buildSchema() {
		return (this.specifiedSchema != null) ? this.specifiedSchema : this.defaultSchema;
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	protected void setSpecifiedSchema(String schema) {
		String old = this.specifiedSchema;
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, this.specifiedSchema = schema);
	}

	protected String buildSpecifiedSchema() {
		return this.getOverriddenTable().getSchema();
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	protected void setDefaultSchema(String schema) {
		String old = this.defaultSchema;
		this.firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, old, this.defaultSchema = schema);
	}

	protected abstract String buildDefaultSchema();


	// ********** catalog **********

	public String getCatalog() {
		return this.catalog;
	}

	protected void setCatalog(String catalog) {
		String old = this.catalog;
		this.firePropertyChanged(CATALOG_PROPERTY, old, this.catalog = catalog);
	}

	protected String buildCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	protected void setSpecifiedCatalog(String catalog) {
		String old = this.specifiedCatalog;
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, this.specifiedCatalog = catalog);
	}

	protected String buildSpecifiedCatalog() {
		return this.getOverriddenTable().getCatalog();
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String catalog) {
		String old = this.defaultCatalog;
		this.firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, this.defaultCatalog = catalog);
	}

	protected abstract String buildDefaultCatalog();


	// ********** unique constraints **********

	public ListIterable<VirtualUniqueConstraint> getUniqueConstraints() {
		return this.uniqueConstraintContainer;
	}

	public int getUniqueConstraintsSize() {
		return this.uniqueConstraintContainer.size();
	}

	public VirtualUniqueConstraint getUniqueConstraint(int index) {
		return this.uniqueConstraintContainer.get(index);
	}

	protected void updateUniqueConstraints(IProgressMonitor monitor) {
		this.uniqueConstraintContainer.update(monitor);
	}

	protected ListIterable<UniqueConstraint> getOverriddenUniqueConstraints() {
		return new SuperListIterableWrapper<>(this.getOverriddenTable().getUniqueConstraints());
	}

	protected void moveUniqueConstraint(int index, VirtualUniqueConstraint constraint) {
		this.uniqueConstraintContainer.move(index, constraint);
	}

	protected VirtualUniqueConstraint addUniqueConstraint(int index, UniqueConstraint uniqueConstraint) {
		return this.uniqueConstraintContainer.addContextElement(index, uniqueConstraint);
	}

	protected VirtualUniqueConstraint buildUniqueConstraint(UniqueConstraint uniqueConstraint) {
		return this.getContextModelFactory().buildOrmVirtualUniqueConstraint(this, uniqueConstraint);
	}

	protected void removeUniqueConstraint(VirtualUniqueConstraint constraint) {
		this.uniqueConstraintContainer.remove(constraint);
	}

	protected ContextListContainer<VirtualUniqueConstraint, UniqueConstraint> buildUniqueConstraintContainer() {
		return this.buildVirtualContextListContainer(UNIQUE_CONSTRAINTS_LIST, new UniqueConstraintContainerAdapter());
	}

	/**
	 * unique constraint container adapter
	 */
	public class UniqueConstraintContainerAdapter
		extends AbstractContainerAdapter<VirtualUniqueConstraint, UniqueConstraint>
	{
		public VirtualUniqueConstraint buildContextElement(UniqueConstraint resourceElement) {
			return AbstractOrmVirtualTable.this.buildUniqueConstraint(resourceElement);
		}
		public ListIterable<UniqueConstraint> getResourceElements() {
			return AbstractOrmVirtualTable.this.getOverriddenUniqueConstraints();
		}
		public UniqueConstraint extractResourceElement(VirtualUniqueConstraint contextElement) {
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
		String string = this.getCatalog();
		return (string != null) ? this.resolveDbCatalog(string) : this.getDatabase();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getDbCatalog() {
		String string = this.getCatalog();
		return (string == null) ? null : this.resolveDbCatalog(string);
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public boolean catalogIsResolved() {
		String string = this.getCatalog();
		return (string == null) || (this.resolveDbCatalog(string) != null);
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
		String string = this.getCatalog();
		return (string == null) || (this.resolveDbCatalog(string) != null);
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
		return this.parent.getValidationTextRange();
	}

	public TextRange getNameValidationTextRange() {
		return this.getValidationTextRange();
	}

	public TextRange getSchemaValidationTextRange() {
		return this.getValidationTextRange();
	}

	public TextRange getCatalogValidationTextRange() {
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
