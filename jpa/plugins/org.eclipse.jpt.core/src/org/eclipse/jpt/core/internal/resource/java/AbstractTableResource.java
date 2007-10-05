/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public abstract class AbstractTableResource extends AbstractAnnotationResource<Member> implements Table
{
	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	// hold this so we can get the 'schema' text range
	private final DeclarationAnnotationElementAdapter<String> schemaDeclarationAdapter;

	// hold this so we can get the 'catalog' text range
	private final DeclarationAnnotationElementAdapter<String> catalogDeclarationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;

	private final AnnotationElementAdapter<String> schemaAdapter;

	private final AnnotationElementAdapter<String> catalogAdapter;

	private String name;
	
	private String catalog;
	
	private String schema;
	
	private final List<UniqueConstraint> uniqueConstraints;
	
	protected AbstractTableResource(JavaResource parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.nameAdapter(daa);
		this.schemaDeclarationAdapter = this.schemaAdapter(daa);
		this.catalogDeclarationAdapter = this.catalogAdapter(daa);
		this.nameAdapter = buildAnnotationElementAdapter(this.nameDeclarationAdapter);
		this.schemaAdapter = buildAnnotationElementAdapter(this.schemaDeclarationAdapter);
		this.catalogAdapter = buildAnnotationElementAdapter(this.catalogDeclarationAdapter);
		this.uniqueConstraints = new ArrayList<UniqueConstraint>();
	}
	
	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(this.getMember(), daea);
	}

	/**
	 * Build and return a declaration element adapter for the table's 'name' element
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> nameAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter);

	/**
	 * Build and return a declaration element adapter for the table's 'schema' element
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> schemaAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter);

	/**
	 * Build and return a declaration element adapter for the table's 'catalog' element
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> catalogAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter);

	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		this.nameAdapter.setValue(name);
	}

	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
		this.catalogAdapter.setValue(catalog);
	}

	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
		this.schemaAdapter.setValue(schema);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		this.setName(this.nameAdapter.getValue(astRoot));
		this.setSchema(this.schemaAdapter.getValue(astRoot));
		this.setCatalog(this.catalogAdapter.getValue(astRoot));
		this.updateUniqueConstraintsFromJava(astRoot);
	}
	
	/**
	 * here we just worry about getting the unique constraints lists the same size;
	 * then we delegate to the unique constraints to synch themselves up
	 */
	private void updateUniqueConstraintsFromJava(CompilationUnit astRoot) {
		// synchronize the model join columns with the Java source
		List<UniqueConstraint> constraints = this.uniqueConstraints;
		int persSize = constraints.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			UniqueConstraintImpl uniqueConstraint = (UniqueConstraintImpl) constraints.get(i);
			if (uniqueConstraint.jdtAnnotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			uniqueConstraint.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model join columns beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				removeUniqueConstraint(persSize);
				constraints.remove(persSize);
			}
		}
		else {
			// add new model join columns until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				UniqueConstraint uniqueConstraint = this.createUniqueConstraint(javaSize);
				if (uniqueConstraint.jdtAnnotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					addUniqueConstraint(uniqueConstraint);
					uniqueConstraint.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}
	
	public ListIterator<UniqueConstraint> uniqueConstraints() {
		return new CloneListIterator<UniqueConstraint>(this.uniqueConstraints);
	}

	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}
	
	public UniqueConstraint uniqueConstraintAt(int index) {
		return this.uniqueConstraints.get(index);
	}
	
	public UniqueConstraint addUniqueConstraint(int index) {
		UniqueConstraint uniqueConstraint = createUniqueConstraint(index);
		addUniqueConstraint(uniqueConstraint);
		uniqueConstraint.newAnnotation();
		synchUniqueConstraintAnnotationsAfterAdd(index);
		return uniqueConstraint;
	}
	
	private void addUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.uniqueConstraints.add(uniqueConstraint);
	}
	
	public void removeUniqueConstraint(int index) {
		UniqueConstraint uniqueConstraint = this.uniqueConstraints.remove(index);
		uniqueConstraint.removeAnnotation();
		synchUniqueConstraintAnnotationsAfterRemove(index);
	}

	public void moveUniqueConstraint(int oldIndex, int newIndex) {
		this.uniqueConstraints.add(newIndex, this.uniqueConstraints.remove(oldIndex));
		
		uniqueConstraintMoved(newIndex, oldIndex);
	}

	private void uniqueConstraintMoved(int sourceIndex, int targetIndex) {		
		UniqueConstraint uniqueConstraint = uniqueConstraintAt(targetIndex);
		synch(uniqueConstraint, uniqueConstraintsSize());
		
		List<UniqueConstraint> nestableAnnotations = CollectionTools.list(uniqueConstraints());
		if (sourceIndex < targetIndex) {
			for (int i = sourceIndex; i < targetIndex; i++) {
				synch(nestableAnnotations.get(i), i);
			}
		}
		else {
			for (int i = sourceIndex; i > targetIndex; i-- ) {
				synch(nestableAnnotations.get(i), i);			
			}
		}
		synch(uniqueConstraint, targetIndex);
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchUniqueConstraintAnnotationsAfterAdd(int index) {
		List<UniqueConstraint> constraints = this.uniqueConstraints;
		for (int i = constraints.size(); i-- > index;) {
			this.synch(constraints.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchUniqueConstraintAnnotationsAfterRemove(int index) {
		List<UniqueConstraint> constraints = this.uniqueConstraints;
		for (int i = index; i < constraints.size(); i++) {
			this.synch(constraints.get(i), i);
		}
	}

	private void synch(UniqueConstraint uniqueConstraint, int index) {
		((UniqueConstraintImpl) uniqueConstraint).moveAnnotation(index);
	}
	
	protected abstract UniqueConstraint createUniqueConstraint(int index);

}
