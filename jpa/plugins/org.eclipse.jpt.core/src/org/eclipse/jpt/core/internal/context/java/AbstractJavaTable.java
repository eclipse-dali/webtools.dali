/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.resource.java.TableAnnotation;
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
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * 
 */
public abstract class AbstractJavaTable
	extends AbstractJavaJpaContextNode
	implements Table, UniqueConstraint.Owner
{
	protected String specifiedName;
	protected String defaultName;

	protected String specifiedSchema;
	protected String defaultSchema;
	
	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected final List<JavaUniqueConstraint> uniqueConstraints;


	// ********** constructor **********

	protected AbstractJavaTable(JavaJpaContextNode parent) {
		super(parent);
		this.uniqueConstraints = new ArrayList<JavaUniqueConstraint>();
	}


	// ********** abstract methods **********

	/**
	 * Return the Java table annotation. Do not return null if the Java
	 * annotation does not exist; return a null table annotation instead.
	 */
	protected abstract TableAnnotation getResourceTable();

	/**
	 * Return the fully qualified name of the Java annotation.
	 */
	protected abstract String getAnnotationName();

	protected abstract String buildDefaultName();

	protected abstract String buildDefaultSchema();
	
	protected abstract String buildDefaultCatalog();
	

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
		this.getResourceTable().setName(name);
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
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
		this.getResourceTable().setSchema(schema);
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedSchema_(String newSpecifiedSchema) {
		String oldSpecifiedSchema = this.specifiedSchema;
		this.specifiedSchema = newSpecifiedSchema;
		firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
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
		this.getResourceTable().setCatalog(catalog);
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
	}

	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedCatalog_(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String catalog) {
		String old = this.defaultCatalog;
		this.defaultCatalog = catalog;
		this.firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, catalog);
	}


	// ********** unique constraints **********
	
	public @SuppressWarnings("unchecked") ListIterator<JavaUniqueConstraint> uniqueConstraints() {
		return new CloneListIterator<JavaUniqueConstraint>(this.uniqueConstraints);
	}
	
	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}
	
	public JavaUniqueConstraint addUniqueConstraint(int index) {
		JavaUniqueConstraint uniqueConstraint = getJpaFactory().buildJavaUniqueConstraint(this, this);
		this.uniqueConstraints.add(index, uniqueConstraint);
		UniqueConstraintAnnotation uniqueConstraintAnnotation = this.getResourceTable().addUniqueConstraint(index);
		uniqueConstraint.initialize(uniqueConstraintAnnotation);
		fireItemAdded(UNIQUE_CONSTRAINTS_LIST, index, uniqueConstraint);
		return uniqueConstraint;
	}
		
	public void removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraints.indexOf(uniqueConstraint));
	}

	public void removeUniqueConstraint(int index) {
		JavaUniqueConstraint removedUniqueConstraint = this.uniqueConstraints.remove(index);
		this.getResourceTable().removeUniqueConstraint(index);
		fireItemRemoved(UNIQUE_CONSTRAINTS_LIST, index, removedUniqueConstraint);
	}
	
	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.uniqueConstraints, targetIndex, sourceIndex);
		this.getResourceTable().moveUniqueConstraint(targetIndex, sourceIndex);
		fireItemMoved(UNIQUE_CONSTRAINTS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addUniqueConstraint(int index, JavaUniqueConstraint uniqueConstraint) {
		addItemToList(index, uniqueConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}
	
	protected void addUniqueConstraint(JavaUniqueConstraint uniqueConstraint) {
		this.addUniqueConstraint(this.uniqueConstraints.size(), uniqueConstraint);
	}
	
	protected void removeUniqueConstraint_(JavaUniqueConstraint uniqueConstraint) {
		removeItemFromList(uniqueConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}


	// ********** convenience methods **********

	protected TextRange getTextRange(TextRange textRange, CompilationUnit astRoot) {
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

	protected TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.getResourceTable().getNameTextRange(astRoot), astRoot);
	}

	protected boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.getResourceTable().nameTouches(pos, astRoot);
	}

	protected TextRange getSchemaTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.getResourceTable().getSchemaTextRange(astRoot), astRoot);
	}

	protected boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return this.getResourceTable().schemaTouches(pos, astRoot);
	}

	protected TextRange getCatalogTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.getResourceTable().getCatalogTextRange(astRoot), astRoot);
	}

	protected boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return this.getResourceTable().catalogTouches(pos, astRoot);
	}


	// ********** resource => context **********

	protected void initialize(TableAnnotation tableAnnotation) {
		this.defaultName = this.buildDefaultName();
		this.specifiedName = tableAnnotation.getName();

		this.defaultSchema = this.buildDefaultSchema();
		this.specifiedSchema = tableAnnotation.getSchema();

		this.defaultCatalog = this.buildDefaultCatalog();
		this.specifiedCatalog = tableAnnotation.getCatalog();

		this.initializeUniqueConstraints(tableAnnotation);
	}

	protected void initializeUniqueConstraints(TableAnnotation tableAnnotation) {
		for (UniqueConstraintAnnotation uniqueConstraintAnnotation : CollectionTools.iterable(tableAnnotation.uniqueConstraints())) {
			this.uniqueConstraints.add(buildUniqueConstraint(uniqueConstraintAnnotation));
		}
	}
	
	protected void update(TableAnnotation tableAnnotation) {
		this.setDefaultName(this.buildDefaultName());
		this.setSpecifiedName_(tableAnnotation.getName());

		this.setDefaultSchema(this.buildDefaultSchema());
		this.setSpecifiedSchema_(tableAnnotation.getSchema());

		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setSpecifiedCatalog_(tableAnnotation.getCatalog());

		this.updateUniqueConstraints(tableAnnotation);
	}
	
	protected void updateUniqueConstraints(TableAnnotation tableAnnotation) {
		ListIterator<UniqueConstraintAnnotation> resourceConstraints = tableAnnotation.uniqueConstraints();
		ListIterator<JavaUniqueConstraint> contextConstraints = this.uniqueConstraints();
		while (contextConstraints.hasNext()) {
			JavaUniqueConstraint uniqueConstraint = contextConstraints.next();
			if (resourceConstraints.hasNext()) {
				uniqueConstraint.update(resourceConstraints.next());
			}
			else {
				removeUniqueConstraint_(uniqueConstraint);
			}
		}
		
		while (resourceConstraints.hasNext()) {
			addUniqueConstraint(buildUniqueConstraint(resourceConstraints.next()));
		}
	}

	protected JavaUniqueConstraint buildUniqueConstraint(UniqueConstraintAnnotation uniqueConstraintAnnotation) {
		JavaUniqueConstraint uniqueConstraint = getJpaFactory().buildJavaUniqueConstraint(this, this);
		uniqueConstraint.initialize(uniqueConstraintAnnotation);
		return uniqueConstraint;
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

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.getResourceTable().getTextRange(astRoot), astRoot);
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
			return this.javaCandidateNames(filter);
		}
		if (this.schemaTouches(pos, astRoot)) {
			return this.javaCandidateSchemata(filter);
		}
		if (this.catalogTouches(pos, astRoot)) {
			return this.javaCandidateCatalogs(filter);
		}
		return null;
	}

	protected Iterator<String> javaCandidateNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateNames(filter));
	}

	protected Iterator<String> candidateNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateNames(), filter);
	}

	protected Iterator<String> candidateNames() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.sortedTableIdentifiers() : EmptyIterator.<String> instance();
	}

	protected Iterator<String> javaCandidateSchemata(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateSchemata(filter));
	}

	protected Iterator<String> candidateSchemata(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateSchemata(), filter);
	}

	protected Iterator<String> candidateSchemata() {
		return this.getDbSchemaContainer().sortedSchemaIdentifiers();
	}

	protected Iterator<String> javaCandidateCatalogs(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateCatalogs(filter));
	}

	protected Iterator<String> candidateCatalogs(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateCatalogs(), filter);
	}

	protected Iterator<String> candidateCatalogs() {
		Database db = this.getDatabase();
		return (db != null) ? db.sortedCatalogIdentifiers() : EmptyIterator.<String> instance();
	}


	// ********** misc **********

	/**
	 * covariant override
	 */
	@Override
	public JavaJpaContextNode getParent() {
		return (JavaJpaContextNode) super.getParent();
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
