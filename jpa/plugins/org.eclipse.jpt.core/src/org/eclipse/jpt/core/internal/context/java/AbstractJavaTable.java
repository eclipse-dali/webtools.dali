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
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.NameTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

public abstract class AbstractJavaTable extends AbstractJavaJpaContextNode implements UniqueConstraint.Owner
{

	protected String specifiedName;
	protected String defaultName;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedSchema;
	protected String defaultSchema;
	
	protected final List<JavaUniqueConstraint> uniqueConstraints;


	protected AbstractJavaTable(JavaJpaContextNode parent) {
		super(parent);
		this.uniqueConstraints = new ArrayList<JavaUniqueConstraint>();
	}

	@Override
	public JavaJpaContextNode getParent() {
		return (JavaJpaContextNode) super.getParent();
	}

	/**
	 * Return the java table resource, do not return null if the java annotation does not exist.
	 * Use a null resource object instead of null.
	 */
	protected abstract TableAnnotation getResourceTable();
	
	/**
	 * Return the fully qualified annotation name of the java table resource
	 */
	protected abstract String getAnnotationName();
	
	public String getName() {
		return (this.getSpecifiedName() == null) ? getDefaultName() : this.getSpecifiedName();
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		getResourceTable().setName(newSpecifiedName);
		firePropertyChanged(Table.SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedName_(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		firePropertyChanged(Table.SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}

	public String getDefaultName() {
		return this.defaultName;
	}

	public String getCatalog() {
		return (this.getSpecifiedCatalog() == null) ? getDefaultCatalog() : this.getSpecifiedCatalog();
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newSpecifiedCatalog;
		getResourceTable().setCatalog(newSpecifiedCatalog);
		firePropertyChanged(Table.SPECIFIED_CATALOG_PROPERTY, oldSpecifiedCatalog, newSpecifiedCatalog);
	}

	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedCatalog_(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newSpecifiedCatalog;
		firePropertyChanged(Table.SPECIFIED_CATALOG_PROPERTY, oldSpecifiedCatalog, newSpecifiedCatalog);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	public String getSchema() {
		return (this.getSpecifiedSchema() == null) ? getDefaultSchema() : this.getSpecifiedSchema();
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}
	
	public void setSpecifiedSchema(String newSpecifiedSchema) {
		String oldSpecifiedSchema = this.specifiedSchema;
		this.specifiedSchema = newSpecifiedSchema;
		getResourceTable().setSchema(newSpecifiedSchema);
		firePropertyChanged(Table.SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
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
		firePropertyChanged(Table.SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}
	
	
	// ********** unique constraints **********
	
	public ListIterator<JavaUniqueConstraint> uniqueConstraints() {
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
		fireItemAdded(Table.UNIQUE_CONSTRAINTS_LIST, index, uniqueConstraint);
		return uniqueConstraint;
	}
		
	public void removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraints.indexOf(uniqueConstraint));
	}

	public void removeUniqueConstraint(int index) {
		JavaUniqueConstraint removedUniqueConstraint = this.uniqueConstraints.remove(index);
		this.getResourceTable().removeUniqueConstraint(index);
		fireItemRemoved(Table.UNIQUE_CONSTRAINTS_LIST, index, removedUniqueConstraint);
	}
	
	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.uniqueConstraints, targetIndex, sourceIndex);
		this.getResourceTable().moveUniqueConstraint(targetIndex, sourceIndex);
		fireItemMoved(Table.UNIQUE_CONSTRAINTS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addUniqueConstraint(int index, JavaUniqueConstraint uniqueConstraint) {
		addItemToList(index, uniqueConstraint, this.uniqueConstraints, Table.UNIQUE_CONSTRAINTS_LIST);
	}
	
	protected void removeUniqueConstraint_(JavaUniqueConstraint uniqueConstraint) {
		removeItemFromList(uniqueConstraint, this.uniqueConstraints, Table.UNIQUE_CONSTRAINTS_LIST);
	}


	// ********** Table implementation **********

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		TextRange textRange = getResourceTable().getNameTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return getResourceTable().nameTouches(pos, astRoot);
	}

	public TextRange getSchemaTextRange(CompilationUnit astRoot) {
		TextRange textRange = getResourceTable().getSchemaTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

	public boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return getResourceTable().schemaTouches(pos, astRoot);
	}

	public TextRange getCatalogTextRange(CompilationUnit astRoot) {
		return getResourceTable().getCatalogTextRange(astRoot);
	}

	public boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return getResourceTable().catalogTouches(pos, astRoot);
	}

	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		firePropertyChanged(Table.DEFAULT_NAME_PROPERTY, oldDefaultName, newDefaultName);
	}

	protected void setDefaultCatalog(String newDefaultCatalog) {
		String oldDefaultCatalog = this.defaultCatalog;
		this.defaultCatalog = newDefaultCatalog;
		firePropertyChanged(Table.DEFAULT_CATALOG_PROPERTY, oldDefaultCatalog, newDefaultCatalog);
	}

	protected void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = this.defaultSchema;
		this.defaultSchema = newDefaultSchema;
		firePropertyChanged(Table.DEFAULT_SCHEMA_PROPERTY, oldDefaultSchema, newDefaultSchema);
	}
	

	
	protected void initialize(TableAnnotation table) {
		this.defaultName = this.defaultName();
		this.defaultSchema = this.defaultSchema();
		this.defaultCatalog = this.defaultCatalog();
		this.specifiedName = table.getName();
		this.specifiedSchema = table.getSchema();
		this.specifiedCatalog = table.getCatalog();
		this.initializeUniqueConstraints(table);
	}

	protected void initializeUniqueConstraints(TableAnnotation table) {
		for (UniqueConstraintAnnotation uniqueConstraintAnnotation : CollectionTools.iterable(table.uniqueConstraints())) {
			this.uniqueConstraints.add(buildUniqueConstraint(uniqueConstraintAnnotation));
		}
	}
	
	protected void update(TableAnnotation table) {
		this.setSpecifiedName_(table.getName());
		this.setSpecifiedSchema_(table.getSchema());
		this.setSpecifiedCatalog_(table.getCatalog());
		this.setDefaultName(this.defaultName());
		this.setDefaultSchema(this.defaultSchema());
		this.setDefaultCatalog(this.defaultCatalog());
		this.updateUniqueConstraints(table);
	}
	
	protected abstract String defaultName();

	protected String defaultSchema() {
		if (getEntityMappings() != null) {
			return getEntityMappings().getSchema();
		}
		return getPersistenceUnit().getDefaultSchema();
	}
	
	protected String defaultCatalog() {
		if (getEntityMappings() != null) {
			return getEntityMappings().getCatalog();
		}
		return getPersistenceUnit().getDefaultCatalog();
	}
	
	protected void updateUniqueConstraints(TableAnnotation table) {
		ListIterator<JavaUniqueConstraint> uniqueConstraints = uniqueConstraints();
		ListIterator<UniqueConstraintAnnotation> resourceUniqueConstraints = table.uniqueConstraints();
		
		while (uniqueConstraints.hasNext()) {
			JavaUniqueConstraint uniqueConstraint = uniqueConstraints.next();
			if (resourceUniqueConstraints.hasNext()) {
				uniqueConstraint.update(resourceUniqueConstraints.next());
			}
			else {
				removeUniqueConstraint_(uniqueConstraint);
			}
		}
		
		while (resourceUniqueConstraints.hasNext()) {
			addUniqueConstraint(uniqueConstraintsSize(), buildUniqueConstraint(resourceUniqueConstraints.next()));
		}
	}

	protected JavaUniqueConstraint buildUniqueConstraint(UniqueConstraintAnnotation uniqueConstraintAnnotation) {
		JavaUniqueConstraint uniqueConstraint = getJpaFactory().buildJavaUniqueConstraint(this, this);
		uniqueConstraint.initialize(uniqueConstraintAnnotation);
		return uniqueConstraint;
	}
	
	
	//******************* UniqueConstraint.Owner implementation ******************
	
	public Iterator<String> candidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.db.Table dbTable = getDbTable();
		if (dbTable != null) {
			return dbTable.columnNames();
		}
		return EmptyIterator.instance();
	}
	
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = getResourceTable().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

	public org.eclipse.jpt.db.Table getDbTable() {
		Schema schema = this.getDbSchema();
		return (schema == null) ? null : schema.getTableNamed(this.getName());
	}

	public Schema getDbSchema() {
		return this.getDataSource().getSchemaNamed(this.getSchema());
	}

	public boolean hasResolvedSchema() {
		return this.getDbSchema() != null;
	}

	public boolean isResolved() {
		return this.getDbTable() != null;
	}

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
	 * called if the database is connected
	 * name, schema, catalog
	 */
	@Override
	public Iterator<String> connectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos, astRoot)) {
			return this.quotedCandidateNames(filter);
		}
		if (this.schemaTouches(pos, astRoot)) {
			return this.quotedCandidateSchemas(filter);
		}
		if (this.catalogTouches(pos, astRoot)) {
			return this.quotedCandidateCatalogs(filter);
		}
		return null;
	}

	private Iterator<String> candidateNames() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.tableNames() : EmptyIterator.<String> instance();
	}

	private Iterator<String> candidateNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateNames(), filter);
	}

	private Iterator<String> quotedCandidateNames(Filter<String> filter) {
		return StringTools.quote(this.candidateNames(filter));
	}

	private Iterator<String> candidateSchemas() {
		return this.getDataSource().schemaNames();
	}

	private Iterator<String> candidateSchemas(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateSchemas(), filter);
	}

	private Iterator<String> quotedCandidateSchemas(Filter<String> filter) {
		return StringTools.quote(this.candidateSchemas(filter));
	}

	private Iterator<String> candidateCatalogs() {
		return this.getDataSource().catalogNames();
	}

	private Iterator<String> candidateCatalogs(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateCatalogs(), filter);
	}

	private Iterator<String> quotedCandidateCatalogs(Filter<String> filter) {
		return StringTools.quote(this.candidateCatalogs(filter));
	}

	public String getQualifiedName() {
		return NameTools.buildQualifiedDatabaseObjectName(this.getCatalog(), this.getSchema(), this.getName());
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getQualifiedName());
	}

	@Override
	public String displayString() {
		return getQualifiedName();
	}
}
