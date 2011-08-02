/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.NameTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaTable;
import org.eclipse.jpt.jpa.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.resource.java.BaseTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java table, secondary table, join table, or collection table
 * <p>
 * <strong>NB:</strong> any subclass that directly holds its table
 * annotation must:<ul>
 * <li>call the "super" constructor that takes a table annotation
 *     {@link #AbstractJavaTable(JavaJpaContextNode, org.eclipse.jpt.jpa.core.context.Table.Owner, BaseTableAnnotation)}
 * <li>override {@link #setTableAnnotation(BaseTableAnnotation)} to set the table
 *     annotation so it is in place before the table's state
 *     (e.g. {@link #specifiedName}) is initialized
 * </ul>
 */
public abstract class AbstractJavaTable<A extends BaseTableAnnotation>
	extends AbstractJavaJpaContextNode
	implements JavaTable, UniqueConstraint.Owner
{
	protected final Owner owner;

	protected String specifiedName;
	protected String defaultName;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected final ContextListContainer<JavaUniqueConstraint, UniqueConstraintAnnotation> uniqueConstraintContainer;


	protected AbstractJavaTable(JavaJpaContextNode parent, Owner owner) {
		this(parent, owner, null);
	}

	protected AbstractJavaTable(JavaJpaContextNode parent, Owner owner, A tableAnnotation) {
		super(parent);
		this.owner = owner;
		this.setTableAnnotation(tableAnnotation);
		this.specifiedName = this.buildSpecifiedName();
		this.specifiedSchema = this.buildSpecifiedSchema();
		this.specifiedCatalog = this.buildSpecifiedCatalog();
		this.uniqueConstraintContainer = this.buildUniqueConstraintContainer();
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


	// ********** table annotation **********

	/**
	 * Return the Java table annotation. Do not return <code>null</code> if the
	 * Java annotation does not exist; return a <em>null</em> table annotation
	 * instead.
	 */
	public abstract A getTableAnnotation();

	/**
	 * see class comment... ({@link AbstractJavaTable})
	 */
	protected void setTableAnnotation(A tableAnnotation) {
		if (tableAnnotation != null) {
			throw new IllegalArgumentException("this method must be overridden if the table annotation is not null: " + tableAnnotation); //$NON-NLS-1$
		}
	}

	protected void removeTableAnnotationIfUnset() {
		if (this.getTableAnnotation().isUnset()) {
			this.removeTableAnnotation();
		}
	}

	protected abstract void removeTableAnnotation();

	public boolean isSpecifiedInResource() {
		return this.getTableAnnotation().isSpecified();
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
			this.getTableAnnotation().setName(name);
			this.removeTableAnnotationIfUnset();
			this.setSpecifiedName_(name);
		}
	}

	protected void setSpecifiedName_(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedName() {
		return this.getTableAnnotation().getName();
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
			this.getTableAnnotation().setSchema(schema);
			this.removeTableAnnotationIfUnset();
			this.setSpecifiedSchema_(schema);
		}
	}

	protected void setSpecifiedSchema_(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
	}

	protected String buildSpecifiedSchema() {
		return this.getTableAnnotation().getSchema();
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
			this.getTableAnnotation().setCatalog(catalog);
			this.removeTableAnnotationIfUnset();
			this.setSpecifiedCatalog_(catalog);
		}
	}

	protected void setSpecifiedCatalog_(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
	}

	protected String buildSpecifiedCatalog() {
		return this.getTableAnnotation().getCatalog();
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
	public ListIterable<JavaUniqueConstraint> getUniqueConstraints() {
		return this.uniqueConstraintContainer.getContextElements();
	}

	public int getUniqueConstraintsSize() {
		return this.uniqueConstraintContainer.getContextElementsSize();
	}

	public JavaUniqueConstraint getUniqueConstraint(int index) {
		return this.uniqueConstraintContainer.getContextElement(index);
	}

	public JavaUniqueConstraint addUniqueConstraint() {
		return this.addUniqueConstraint(this.getUniqueConstraintsSize());
	}

	public JavaUniqueConstraint addUniqueConstraint(int index) {
		UniqueConstraintAnnotation annotation = this.getTableAnnotation().addUniqueConstraint(index);
		return this.uniqueConstraintContainer.addContextElement(index, annotation);
	}

	public void removeUniqueConstraint(int index) {
		this.getTableAnnotation().removeUniqueConstraint(index);
		this.uniqueConstraintContainer.removeContextElement(index);
	}

	public void removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraintContainer.indexOfContextElement((JavaUniqueConstraint) uniqueConstraint));
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		this.getTableAnnotation().moveUniqueConstraint(targetIndex, sourceIndex);
		this.uniqueConstraintContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected void syncUniqueConstraints() {
		this.uniqueConstraintContainer.synchronizeWithResourceModel();
	}

	protected void updateUniqueConstraints() {
		this.uniqueConstraintContainer.update();
	}

	protected JavaUniqueConstraint buildUniqueConstraint(UniqueConstraintAnnotation constraintAnnotation) {
		return this.getJpaFactory().buildJavaUniqueConstraint(this, this, constraintAnnotation);
	}

	protected ListIterable<UniqueConstraintAnnotation> getUniqueConstraintAnnotations() {
		return this.getTableAnnotation().getUniqueConstraints();
	}

	protected ContextListContainer<JavaUniqueConstraint, UniqueConstraintAnnotation> buildUniqueConstraintContainer() {
		return new UniqueConstraintContainer();
	}

	/**
	 * unique constraint container
	 */
	protected class UniqueConstraintContainer
		extends ContextListContainer<JavaUniqueConstraint, UniqueConstraintAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return UNIQUE_CONSTRAINTS_LIST;
		}
		@Override
		protected JavaUniqueConstraint buildContextElement(UniqueConstraintAnnotation resourceElement) {
			return AbstractJavaTable.this.buildUniqueConstraint(resourceElement);
		}
		@Override
		protected ListIterable<UniqueConstraintAnnotation> getResourceElements() {
			return AbstractJavaTable.this.getUniqueConstraintAnnotations();
		}
		@Override
		protected UniqueConstraintAnnotation getResourceElement(JavaUniqueConstraint contextElement) {
			return contextElement.getUniqueConstraintAnnotation();
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


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaUniqueConstraint constraint : this.getUniqueConstraints()) {
			result = constraint.getJavaCompletionProposals(pos, filter, astRoot);
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
	protected Iterable<String> getConnectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getConnectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos, astRoot)) {
			return this.getJavaCandidateNames(filter);
		}
		if (this.schemaTouches(pos, astRoot)) {
			return this.getJavaCandidateSchemata(filter);
		}
		if (this.catalogTouches(pos, astRoot)) {
			return this.getJavaCandidateCatalogs(filter);
		}
		return null;
	}

	protected boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.getTableAnnotation().nameTouches(pos, astRoot);
	}

	protected Iterable<String> getJavaCandidateNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateNames(filter));
	}

	protected Iterable<String> getCandidateNames(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateNames(), filter);
	}

	protected Iterable<String> getCandidateNames() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.getSortedTableIdentifiers() : EmptyIterable.<String> instance();
	}

	protected boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return this.getTableAnnotation().schemaTouches(pos, astRoot);
	}

	protected Iterable<String> getJavaCandidateSchemata(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateSchemata(filter));
	}

	protected Iterable<String> getCandidateSchemata(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateSchemata(), filter);
	}

	protected Iterable<String> getCandidateSchemata() {
		return this.getDbSchemaContainer().getSortedSchemaIdentifiers();
	}

	protected boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return this.getTableAnnotation().catalogTouches(pos, astRoot);
	}

	protected Iterable<String> getJavaCandidateCatalogs(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateCatalogs(filter));
	}

	protected Iterable<String> getCandidateCatalogs(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateCatalogs(), filter);
	}

	protected Iterable<String> getCandidateCatalogs() {
		Database db = this.getDatabase();
		return (db != null) ? db.getSortedCatalogIdentifiers() : EmptyIterable.<String> instance();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.buildTableValidator(astRoot).validate(messages, reporter);
	}

	protected JptValidator buildTableValidator(CompilationUnit astRoot) {
		return this.owner.buildTableValidator(this, buildTextRangeResolver(astRoot));
	}

	protected TableTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaTableTextRangeResolver(this, astRoot);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getTableAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.getTableAnnotation().getNameTextRange(astRoot), astRoot);
	}

	public TextRange getSchemaTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.getTableAnnotation().getSchemaTextRange(astRoot), astRoot);
	}

	public TextRange getCatalogTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.getTableAnnotation().getCatalogTextRange(astRoot), astRoot);
	}


	// ********** misc **********

	@Override
	public JavaJpaContextNode getParent() {
		return (JavaJpaContextNode) super.getParent();
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
