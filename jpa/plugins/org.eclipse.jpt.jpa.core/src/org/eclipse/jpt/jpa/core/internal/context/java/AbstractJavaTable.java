/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.NameTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.VirtualTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
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
 *     {@link #AbstractJavaTable(org.eclipse.jpt.jpa.core.context.Table.ParentAdapter, BaseTableAnnotation)}
 * <li>override {@link #setTableAnnotation(BaseTableAnnotation)} to set the table
 *     annotation so it is in place before the table's state
 *     (e.g. {@link #specifiedName}) is initialized
 * </ul>
 */
public abstract class AbstractJavaTable<P extends JpaContextModel, PA extends Table.ParentAdapter<P>, A extends BaseTableAnnotation>
	extends AbstractJavaContextModel<P>
	implements JavaSpecifiedTable, SpecifiedUniqueConstraint.Parent
{
	protected final PA parentAdapter;

	protected String specifiedName;
	protected String defaultName;
	protected String name;

	protected String specifiedSchema;
	protected String defaultSchema;
	protected String schema;

	protected String specifiedCatalog;
	protected String defaultCatalog;
	protected String catalog;

	protected final ContextListContainer<JavaSpecifiedUniqueConstraint, UniqueConstraintAnnotation> uniqueConstraintContainer;


	protected AbstractJavaTable(PA parentAdapter) {
		this(parentAdapter, null);
	}

	protected AbstractJavaTable(PA parentAdapter, A tableAnnotation) {
		super(parentAdapter.getTableParent());
		this.parentAdapter = parentAdapter;
		this.setTableAnnotation(tableAnnotation);
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
		this.setName(this.buildName());

		this.setDefaultSchema(this.buildDefaultSchema());
		this.setSchema(this.buildSchema());

		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setCatalog(this.buildCatalog());

		this.updateModels(this.getUniqueConstraints(), monitor);
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

	public void setSpecifiedName(String name) {
		if (ObjectTools.notEquals(this.specifiedName, name)) {
			this.getTableAnnotation().setName(name);
			this.removeTableAnnotationIfUnset();
			this.setSpecifiedName_(name);
		}
	}

	protected void setSpecifiedName_(String name) {
		String old = this.specifiedName;
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, this.specifiedName = name);
	}

	protected String buildSpecifiedName() {
		return this.getTableAnnotation().getName();
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

	public void setSpecifiedSchema(String schema) {
		if (ObjectTools.notEquals(this.specifiedSchema, schema)) {
			this.getTableAnnotation().setSchema(schema);
			this.removeTableAnnotationIfUnset();
			this.setSpecifiedSchema_(schema);
		}
	}

	protected void setSpecifiedSchema_(String schema) {
		String old = this.specifiedSchema;
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, this.specifiedSchema = schema);
	}

	protected String buildSpecifiedSchema() {
		return this.getTableAnnotation().getSchema();
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

	public void setSpecifiedCatalog(String catalog) {
		if (ObjectTools.notEquals(this.specifiedCatalog, catalog)) {
			this.getTableAnnotation().setCatalog(catalog);
			this.removeTableAnnotationIfUnset();
			this.setSpecifiedCatalog_(catalog);
		}
	}

	protected void setSpecifiedCatalog_(String catalog) {
		String old = this.specifiedCatalog;
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, this.specifiedCatalog = catalog);
	}

	protected String buildSpecifiedCatalog() {
		return this.getTableAnnotation().getCatalog();
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
	public ListIterable<JavaSpecifiedUniqueConstraint> getUniqueConstraints() {
		return this.uniqueConstraintContainer;
	}

	public int getUniqueConstraintsSize() {
		return this.uniqueConstraintContainer.size();
	}

	public JavaSpecifiedUniqueConstraint getUniqueConstraint(int index) {
		return this.uniqueConstraintContainer.get(index);
	}

	public JavaSpecifiedUniqueConstraint addUniqueConstraint() {
		return this.addUniqueConstraint(this.getUniqueConstraintsSize());
	}

	public JavaSpecifiedUniqueConstraint addUniqueConstraint(int index) {
		UniqueConstraintAnnotation annotation = this.getTableAnnotation().addUniqueConstraint(index);
		return this.uniqueConstraintContainer.addContextElement(index, annotation);
	}

	public void removeUniqueConstraint(int index) {
		this.getTableAnnotation().removeUniqueConstraint(index);
		this.uniqueConstraintContainer.remove(index);
	}

	public void removeUniqueConstraint(SpecifiedUniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraintContainer.indexOf((JavaSpecifiedUniqueConstraint) uniqueConstraint));
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		this.getTableAnnotation().moveUniqueConstraint(targetIndex, sourceIndex);
		this.uniqueConstraintContainer.move(targetIndex, sourceIndex);
	}

	protected void syncUniqueConstraints(IProgressMonitor monitor) {
		this.uniqueConstraintContainer.synchronizeWithResourceModel(monitor);
	}

	protected JavaSpecifiedUniqueConstraint buildUniqueConstraint(UniqueConstraintAnnotation constraintAnnotation) {
		return this.getJpaFactory().buildJavaUniqueConstraint(this, constraintAnnotation);
	}

	protected ListIterable<UniqueConstraintAnnotation> getUniqueConstraintAnnotations() {
		return this.getTableAnnotation().getUniqueConstraints();
	}

	protected ContextListContainer<JavaSpecifiedUniqueConstraint, UniqueConstraintAnnotation> buildUniqueConstraintContainer() {
		return this.buildSpecifiedContextListContainer(UNIQUE_CONSTRAINTS_LIST, new UniqueConstraintContainerAdapter());
	}

	/**
	 * unique constraint container adapter
	 */
	public class UniqueConstraintContainerAdapter
		extends AbstractContainerAdapter<JavaSpecifiedUniqueConstraint, UniqueConstraintAnnotation>
	{
		public JavaSpecifiedUniqueConstraint buildContextElement(UniqueConstraintAnnotation resourceElement) {
			return AbstractJavaTable.this.buildUniqueConstraint(resourceElement);
		}
		public ListIterable<UniqueConstraintAnnotation> getResourceElements() {
			return AbstractJavaTable.this.getUniqueConstraintAnnotations();
		}
		public UniqueConstraintAnnotation extractResourceElement(JavaSpecifiedUniqueConstraint contextElement) {
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
		String catalogString = this.getCatalog();
		return (catalogString != null) ? this.resolveDbCatalog(catalogString) : this.getDatabase();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getDbCatalog() {
		String catalogString = this.getCatalog();
		return (catalogString == null) ? null : this.resolveDbCatalog(catalogString);
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
		String catalogString = this.getCatalog();
		return (catalogString == null) || (this.resolveDbCatalog(catalogString) != null);
	}


	// ********** SpecifiedUniqueConstraint.Parent implementation **********

	public Iterable<String> getCandidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.jpa.db.Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.getSortedColumnIdentifiers() : EmptyIterable.<String>instance();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (JavaSpecifiedUniqueConstraint constraint : this.getUniqueConstraints()) {
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
		if (this.nameTouches(pos)) {
			return this.getJavaCandidateNames();
		}
		if (this.schemaTouches(pos)) {
			return this.getJavaCandidateSchemata();
		}
		if (this.catalogTouches(pos)) {
			return this.getJavaCandidateCatalogs();
		}
		return null;
	}

	protected boolean nameTouches(int pos) {
		return this.getTableAnnotation().nameTouches(pos);
	}

	protected Iterable<String> getJavaCandidateNames() {
		return new TransformationIterable<>(this.getCandidateNames(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected Iterable<String> getCandidateNames() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.getSortedTableIdentifiers() : EmptyIterable.<String> instance();
	}

	protected boolean schemaTouches(int pos) {
		return this.getTableAnnotation().schemaTouches(pos);
	}

	protected Iterable<String> getJavaCandidateSchemata() {
		return new TransformationIterable<>(this.getCandidateSchemata(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected Iterable<String> getCandidateSchemata() {
		return this.getDbSchemaContainer().getSortedSchemaIdentifiers();
	}

	protected boolean catalogTouches(int pos) {
		return this.getTableAnnotation().catalogTouches(pos);
	}

	protected Iterable<String> getJavaCandidateCatalogs() {
		return new TransformationIterable<>(this.getCandidateCatalogs(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected Iterable<String> getCandidateCatalogs() {
		Database db = this.getDatabase();
		return (db != null) ? db.getSortedCatalogIdentifiers() : EmptyIterable.<String> instance();
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
		TextRange textRange = this.getTableAnnotation().getTextRange();
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}

	public TextRange getNameValidationTextRange() {
		return this.getValidationTextRange(this.getTableAnnotation().getNameValidationTextRange());
	}

	public TextRange getSchemaValidationTextRange() {
		return this.getValidationTextRange(this.getTableAnnotation().getSchemaValidationTextRange());
	}

	public TextRange getCatalogValidationTextRange() {
		return this.getValidationTextRange(this.getTableAnnotation().getCatalogValidationTextRange());
	}


	// ********** misc **********

	protected void initializeFrom(VirtualTable virtualTable) {
		this.setSpecifiedName(virtualTable.getName());
		// ignore other settings?
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.buildQualifiedName());
	}

	protected String buildQualifiedName() {
		return NameTools.buildQualifiedName(this.getCatalog(), this.getSchema(), this.getName());
	}
}
