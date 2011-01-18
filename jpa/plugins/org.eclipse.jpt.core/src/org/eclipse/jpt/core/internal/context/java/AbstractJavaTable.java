/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.ReadOnlyTable;
import org.eclipse.jpt.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.resource.java.BaseTableAnnotation;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.NameTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java table, secondary table, join table, or collection table
 * <p>
 * <strong>NB:</strong> any subclass that directly holds its table
 * annotation must:<ul>
 * <li>call the "super" constructor that takes a table annotation
 *     {@link #AbstractJavaTable(JavaJpaContextNode, org.eclipse.jpt.core.context.Table.Owner, BaseTableAnnotation)}
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

	protected final Vector<JavaUniqueConstraint> uniqueConstraints = new Vector<JavaUniqueConstraint>();
	protected final UniqueConstraintContainerAdapter uniqueConstraintContainerAdapter = new UniqueConstraintContainerAdapter();


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
		this.initializeUniqueConstraints();
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

	public ListIterator<JavaUniqueConstraint> uniqueConstraints() {
		return this.getUniqueConstraints().iterator();
	}

	protected ListIterable<JavaUniqueConstraint> getUniqueConstraints() {
		return new LiveCloneListIterable<JavaUniqueConstraint>(this.uniqueConstraints);
	}

	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}

	public JavaUniqueConstraint getUniqueConstraint(int index) {
		return this.uniqueConstraints.get(index);
	}

	public JavaUniqueConstraint addUniqueConstraint() {
		return this.addUniqueConstraint(this.uniqueConstraints.size());
	}

	public JavaUniqueConstraint addUniqueConstraint(int index) {
		UniqueConstraintAnnotation annotation = this.getTableAnnotation().addUniqueConstraint(index);
		return this.addUniqueConstraint_(index, annotation);
	}

	public void removeUniqueConstraint(UniqueConstraint constraint) {
		this.removeUniqueConstraint(this.uniqueConstraints.indexOf(constraint));
	}

	public void removeUniqueConstraint(int index) {
		this.getTableAnnotation().removeUniqueConstraint(index);
		this.removeTableAnnotationIfUnset();
		this.removeUniqueConstraint_(index);
	}

	protected void removeUniqueConstraint_(int index) {
		this.removeItemFromList(index, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		this.getTableAnnotation().moveUniqueConstraint(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}

	protected void initializeUniqueConstraints() {
		for (Iterator<UniqueConstraintAnnotation> stream = this.getTableAnnotation().uniqueConstraints(); stream.hasNext(); ) {
			this.uniqueConstraints.add(this.buildUniqueConstraint(stream.next()));
		}
	}

	protected JavaUniqueConstraint buildUniqueConstraint(UniqueConstraintAnnotation constraintAnnotation) {
		return this.getJpaFactory().buildJavaUniqueConstraint(this, this, constraintAnnotation);
	}

	protected void syncUniqueConstraints() {
		ContextContainerTools.synchronizeWithResourceModel(this.uniqueConstraintContainerAdapter);
	}

	protected Iterable<UniqueConstraintAnnotation> getUniqueConstraintAnnotations() {
		return CollectionTools.iterable(this.getTableAnnotation().uniqueConstraints());
	}

	protected void moveUniqueConstraint_(int index, JavaUniqueConstraint constraint) {
		this.moveItemInList(index, constraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}

	protected JavaUniqueConstraint addUniqueConstraint_(int index, UniqueConstraintAnnotation constraintAnnotation) {
		JavaUniqueConstraint constraint = this.buildUniqueConstraint(constraintAnnotation);
		this.addItemToList(index, constraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
		return constraint;
	}

	protected void removeUniqueConstraint_(JavaUniqueConstraint constraint) {
		this.removeUniqueConstraint_(this.uniqueConstraints.indexOf(constraint));
	}

	/**
	 * unique constraint container adapter
	 */
	protected class UniqueConstraintContainerAdapter
		implements ContextContainerTools.Adapter<JavaUniqueConstraint, UniqueConstraintAnnotation>
	{
		public Iterable<JavaUniqueConstraint> getContextElements() {
			return AbstractJavaTable.this.getUniqueConstraints();
		}
		public Iterable<UniqueConstraintAnnotation> getResourceElements() {
			return AbstractJavaTable.this.getUniqueConstraintAnnotations();
		}
		public UniqueConstraintAnnotation getResourceElement(JavaUniqueConstraint contextElement) {
			return contextElement.getUniqueConstraintAnnotation();
		}
		public void moveContextElement(int index, JavaUniqueConstraint element) {
			AbstractJavaTable.this.moveUniqueConstraint_(index, element);
		}
		public void addContextElement(int index, UniqueConstraintAnnotation resourceElement) {
			AbstractJavaTable.this.addUniqueConstraint_(index, resourceElement);
		}
		public void removeContextElement(JavaUniqueConstraint element) {
			AbstractJavaTable.this.removeUniqueConstraint_(element);
		}
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

	public Iterator<String> candidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.db.Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.getSortedColumnIdentifiers().iterator() : EmptyIterator.<String>instance();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaUniqueConstraint constraint : CollectionTools.iterable(this.uniqueConstraints())) {
			result = constraint.javaCompletionProposals(pos, filter, astRoot);
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
	public Iterator<String> connectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos, astRoot)) {
			return this.getJavaCandidateNames(filter).iterator();
		}
		if (this.schemaTouches(pos, astRoot)) {
			return this.getJavaCandidateSchemata(filter).iterator();
		}
		if (this.catalogTouches(pos, astRoot)) {
			return this.getJavaCandidateCatalogs(filter).iterator();
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
		return this.getTextRange(this.getTableAnnotation().getTextRange(astRoot), astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.getTableAnnotation().getNameTextRange(astRoot), astRoot);
	}

	public TextRange getSchemaTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.getTableAnnotation().getSchemaTextRange(astRoot), astRoot);
	}

	public TextRange getCatalogTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.getTableAnnotation().getCatalogTextRange(astRoot), astRoot);
	}

	protected TextRange getTextRange(TextRange textRange, CompilationUnit astRoot) {
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
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
		for (ReadOnlyUniqueConstraint constraint : CollectionTools.iterable(oldTable.uniqueConstraints())) {
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
