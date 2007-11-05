/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.Table;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

public abstract class AbstractJavaTable extends JavaContextModel implements ITable
{

	protected String specifiedName;
	protected String defaultName;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected JavaPersistentTypeResource persistentTypeResource;
	
//	protected EList<IUniqueConstraint> uniqueConstraints;

//	private final Owner owner;


	protected AbstractJavaTable(IJpaContextNode parent) {
		super(parent);
	}

	public void initialize(JavaPersistentTypeResource persistentTypeResource) {
		this.persistentTypeResource = persistentTypeResource;
		initialize(tableResource());
		
	}
	protected void initialize(Table table) {
		this.defaultName = this.defaultName();
		this.defaultSchema = this.defaultSchema();
		this.defaultCatalog = this.defaultCatalog();
		this.specifiedName = table.getName();
		this.specifiedSchema = table.getSchema();
		this.specifiedCatalog = table.getCatalog();
	}
	
	//query for the table resource every time on setters.
	//call one setter and the tableResource could change. 
	//You could call more than one setter before this object has received any notification
	//from the java resource model
	protected Table tableResource() {
		//TODO get the NullTable from the resource model or build it here in the context model??
		return (Table) this.persistentTypeResource.nonNullAnnotation(annotationName());
	}
	
	protected abstract String annotationName();
	
	public String getName() {
		return (this.getSpecifiedName() == null) ? getDefaultName() : this.getSpecifiedName();
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		tableResource().setName(newSpecifiedName);
		firePropertyChanged(ITable.SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
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
		tableResource().setCatalog(newSpecifiedCatalog);
		firePropertyChanged(ITable.SPECIFIED_CATALOG_PROPERTY, oldSpecifiedCatalog, newSpecifiedCatalog);
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
		tableResource().setSchema(newSpecifiedSchema);
		firePropertyChanged(ITable.SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}
	
//	public EList<IUniqueConstraint> getUniqueConstraints() {
//		if (uniqueConstraints == null) {
//			uniqueConstraints = new EObjectContainmentEList<IUniqueConstraint>(IUniqueConstraint.class, this, JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS);
//		}
//		return uniqueConstraints;
//	}



	// ********** ITable implementation **********

	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return tableResource() .nameTextRange(astRoot);
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return tableResource() .nameTouches(pos, astRoot);
	}

	public ITextRange schemaTextRange(CompilationUnit astRoot) {
		return tableResource() .schemaTextRange(astRoot);
	}

	public boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return tableResource() .schemaTouches(pos, astRoot);
	}

	public ITextRange catalogTextRange(CompilationUnit astRoot) {
		return tableResource() .catalogTextRange(astRoot);
	}

	public boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return tableResource() .catalogTouches(pos, astRoot);
	}

	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		firePropertyChanged(DEFAULT_NAME_PROPERTY, oldDefaultName, newDefaultName);
	}

	protected void setDefaultCatalog(String newDefaultCatalog) {
		String oldDefaultCatalog = this.defaultCatalog;
		this.defaultCatalog = newDefaultCatalog;
		firePropertyChanged(DEFAULT_CATALOG_PROPERTY, oldDefaultCatalog, newDefaultCatalog);
	}

	protected void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = this.defaultSchema;
		this.defaultSchema = newDefaultSchema;
		firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, oldDefaultSchema, newDefaultSchema);
	}
//
//	public Owner getOwner() {
//		return owner;
//	}
//
//	public IUniqueConstraint createUniqueConstraint(int index) {
//		return createJavaUniqueConstraint(index);
//	}
//
//	protected abstract JavaUniqueConstraint createJavaUniqueConstraint(int index);
//
//	//set these defaults here or call setDefaultCatalog from JavaTableContext instead
//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		this.setDefaultCatalog((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TABLE_CATALOG_KEY));
//		this.setDefaultSchema((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TABLE_SCHEMA_KEY));
//	}

	public void update(JavaPersistentTypeResource persistentTypeResource) {
		this.persistentTypeResource = persistentTypeResource;
		this.update(tableResource());
	}
	
	protected void update(Table table) {
		this.setSpecifiedName(table.getName());
		this.setSpecifiedSchema(table.getSchema());
		this.setSpecifiedCatalog(table.getCatalog());
		this.setDefaultName(this.defaultName());
		this.setDefaultSchema(this.defaultSchema());
		this.setDefaultCatalog(this.defaultCatalog());
		//this.updateUniqueConstraints(table);
		
	}
	
	protected abstract String defaultName();

	//if listed in a mapping file, then check the default schema there
	//otherwise check the persistence unit default schema
	//otherwise the project level default schema which for us will usually be the username
	protected String defaultSchema() {
		//should i calculate this here or should someone else set this on me???
		//getOwner().defaultSchema()?
		return null;
	}
	
	protected String defaultCatalog() {
		return null;
	}
	
	
//	/**
//	 * here we just worry about getting the unique constraints lists the same size;
//	 * then we delegate to the unique constraints to synch themselves up
//	 */
//	private void updateUniqueConstraintsFromJava(CompilationUnit astRoot) {
//		// synchronize the model join columns with the Java source
//		List<IUniqueConstraint> constraints = this.getUniqueConstraints();
//		int persSize = constraints.size();
//		int javaSize = 0;
//		boolean allJavaAnnotationsFound = false;
//		for (int i = 0; i < persSize; i++) {
//			JavaUniqueConstraint uniqueConstraint = (JavaUniqueConstraint) constraints.get(i);
//			if (uniqueConstraint.annotation(astRoot) == null) {
//				allJavaAnnotationsFound = true;
//				break; // no need to go any further
//			}
//			uniqueConstraint.updateFromJava(astRoot);
//			javaSize++;
//		}
//		if (allJavaAnnotationsFound) {
//			// remove any model join columns beyond those that correspond to the Java annotations
//			while (persSize > javaSize) {
//				persSize--;
//				constraints.remove(persSize);
//			}
//		}
//		else {
//			// add new model join columns until they match the Java annotations
//			while (!allJavaAnnotationsFound) {
//				JavaUniqueConstraint uniqueConstraint = this.createJavaUniqueConstraint(javaSize);
//				if (uniqueConstraint.annotation(astRoot) == null) {
//					allJavaAnnotationsFound = true;
//				}
//				else {
//					this.getUniqueConstraints().add(uniqueConstraint);
//					uniqueConstraint.updateFromJava(astRoot);
//					javaSize++;
//				}
//			}
//		}
//	}

	public ITextRange validationTextRange() {
		//TODO textRange
		return null;
//		ITextRange textRange = this.member.annotationTextRange(this.daa);
//		return (textRange != null) ? textRange : this.getOwner().validationTextRange();
	}

	public org.eclipse.jpt.db.internal.Table dbTable() {
		Schema schema = this.dbSchema();
		return (schema == null) ? null : schema.tableNamed(this.getName());
	}

	public Schema dbSchema() {
		return this.database().schemaNamed(this.getSchema());
	}

	public boolean hasResolvedSchema() {
		return this.dbSchema() != null;
	}

	public boolean isResolved() {
		return this.dbTable() != null;
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
//		for (IUniqueConstraint constraint : this.getUniqueConstraints()) {
//			result = ((JavaUniqueConstraint) constraint).candidateValuesFor(pos, filter, astRoot);
//			if (result != null) {
//				return result;
//			}
//		}
		return null;
	}

	/**
	 * called if the database is connected
	 * name, schema, catalog
	 */
	@Override
	public Iterator<String> connectedCandidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedCandidateValuesFor(pos, filter, astRoot);
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
		Schema dbSchema = this.dbSchema();
		return (dbSchema != null) ? dbSchema.tableNames() : EmptyIterator.<String> instance();
	}

	private Iterator<String> candidateNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateNames(), filter);
	}

	private Iterator<String> quotedCandidateNames(Filter<String> filter) {
		return StringTools.quote(this.candidateNames(filter));
	}

	private Iterator<String> candidateSchemas() {
		return this.database().schemaNames();
	}

	private Iterator<String> candidateSchemas(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateSchemas(), filter);
	}

	private Iterator<String> quotedCandidateSchemas(Filter<String> filter) {
		return StringTools.quote(this.candidateSchemas(filter));
	}

	private Iterator<String> candidateCatalogs() {
		return this.database().catalogNames();
	}

	private Iterator<String> candidateCatalogs(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateCatalogs(), filter);
	}

	private Iterator<String> quotedCandidateCatalogs(Filter<String> filter) {
		return StringTools.quote(this.candidateCatalogs(filter));
	}

}
