/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.NameTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.VirtualTable;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;

public abstract class AbstractJavaVirtualTable<T extends Table>
	extends AbstractJavaJpaContextNode
	implements VirtualTable
{
	protected String specifiedName;
	protected String defaultName;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected final Vector<JavaVirtualUniqueConstraint> uniqueConstraints = new Vector<JavaVirtualUniqueConstraint>();
	protected final UniqueConstraintContainerAdapter uniqueConstraintContainerAdapter = new UniqueConstraintContainerAdapter();


	protected AbstractJavaVirtualTable(JavaJpaContextNode parent) {
		super(parent);
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
	public abstract T getOverriddenTable();


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

	public ListIterator<JavaVirtualUniqueConstraint> uniqueConstraints() {
		return this.getUniqueConstraints().iterator();
	}

	protected ListIterable<JavaVirtualUniqueConstraint> getUniqueConstraints() {
		return new LiveCloneListIterable<JavaVirtualUniqueConstraint>(this.uniqueConstraints);
	}

	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}

	public JavaVirtualUniqueConstraint getUniqueConstraint(int index) {
		return this.uniqueConstraints.get(index);
	}

	protected void updateUniqueConstraints() {
		ContextContainerTools.update(this.uniqueConstraintContainerAdapter);
	}

	protected Iterable<UniqueConstraint> getOverriddenUniqueConstraints() {
		return CollectionTools.iterable(this.getOverriddenTable().uniqueConstraints());
	}

	protected void moveUniqueConstraint(int index, JavaVirtualUniqueConstraint constraint) {
		this.moveItemInList(index, constraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}

	protected JavaVirtualUniqueConstraint addUniqueConstraint(int index, UniqueConstraint uniqueConstraint) {
		JavaVirtualUniqueConstraint virtualConstraint = this.buildUniqueConstraint(uniqueConstraint);
		this.addItemToList(index, virtualConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
		return virtualConstraint;
	}

	protected JavaVirtualUniqueConstraint buildUniqueConstraint(UniqueConstraint uniqueConstraint) {
		return this.getJpaFactory().buildJavaVirtualUniqueConstraint(this, uniqueConstraint);
	}

	protected void removeUniqueConstraint(JavaVirtualUniqueConstraint constraint) {
		this.removeItemFromList(constraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}

	/**
	 * unique constraint container adapter
	 */
	protected class UniqueConstraintContainerAdapter
		implements ContextContainerTools.Adapter<JavaVirtualUniqueConstraint, UniqueConstraint>
	{
		public Iterable<JavaVirtualUniqueConstraint> getContextElements() {
			return AbstractJavaVirtualTable.this.getUniqueConstraints();
		}
		public Iterable<UniqueConstraint> getResourceElements() {
			return AbstractJavaVirtualTable.this.getOverriddenUniqueConstraints();
		}
		public UniqueConstraint getResourceElement(JavaVirtualUniqueConstraint contextElement) {
			return contextElement.getOverriddenUniqueConstraint();
		}
		public void moveContextElement(int index, JavaVirtualUniqueConstraint element) {
			AbstractJavaVirtualTable.this.moveUniqueConstraint(index, element);
		}
		public void addContextElement(int index, UniqueConstraint resourceElement) {
			AbstractJavaVirtualTable.this.addUniqueConstraint(index, resourceElement);
		}
		public void removeContextElement(JavaVirtualUniqueConstraint element) {
			AbstractJavaVirtualTable.this.removeUniqueConstraint(element);
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

	protected boolean isResolved() {
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

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.buildQualifiedName());
	}

	protected String buildQualifiedName() {
		return NameTools.buildQualifiedDatabaseObjectName(this.getCatalog(), this.getSchema(), this.getName());
	}
}
