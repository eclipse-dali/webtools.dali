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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaPlatform;
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
	
	private final List<NestableUniqueConstraint> uniqueConstraints;
	
	private final UniqueConstraintsContainerAnnotation uniqueConstraintsContainerAnnotation;
	
	protected AbstractTableResource(JavaResource parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.nameAdapter(daa);
		this.schemaDeclarationAdapter = this.schemaAdapter(daa);
		this.catalogDeclarationAdapter = this.catalogAdapter(daa);
		this.nameAdapter = buildAnnotationElementAdapter(this.nameDeclarationAdapter);
		this.schemaAdapter = buildAnnotationElementAdapter(this.schemaDeclarationAdapter);
		this.catalogAdapter = buildAnnotationElementAdapter(this.catalogDeclarationAdapter);
		this.uniqueConstraints = new ArrayList<NestableUniqueConstraint>();
		this.uniqueConstraintsContainerAnnotation = new UniqueConstraintsContainerAnnotation();
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
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this.uniqueConstraintsContainerAnnotation);
	}
	
	public ListIterator<UniqueConstraint> uniqueConstraints() {
		return new CloneListIterator<UniqueConstraint>(this.uniqueConstraints);
	}
	
	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}
	
	public NestableUniqueConstraint uniqueConstraintAt(int index) {
		return this.uniqueConstraints.get(index);
	}
	
	public int indexOfUniqueConstraint(UniqueConstraint uniqueConstraint) {
		return this.uniqueConstraints.indexOf(uniqueConstraint);
	}
	
	public UniqueConstraint addUniqueConstraint(int index) {
		NestableUniqueConstraint uniqueConstraint = createUniqueConstraint(index);
		addUniqueConstraint(uniqueConstraint);
		uniqueConstraint.newAnnotation();
		synchUniqueConstraintAnnotationsAfterAdd(index);
		return uniqueConstraint;
	}
	
	private void addUniqueConstraint(NestableUniqueConstraint uniqueConstraint) {
		this.uniqueConstraints.add(uniqueConstraint);
		//property change notification
	}
	
	public void removeUniqueConstraint(int index) {
		NestableUniqueConstraint uniqueConstraint = this.uniqueConstraints.remove(index);
		uniqueConstraint.removeAnnotation();
		synchUniqueConstraintAnnotationsAfterRemove(index);
	}

	public void moveUniqueConstraint(int oldIndex, int newIndex) {
		this.uniqueConstraints.add(newIndex, this.uniqueConstraints.remove(oldIndex));
		
		uniqueConstraintMoved(newIndex, oldIndex);
	}

	private void uniqueConstraintMoved(int sourceIndex, int targetIndex) {		
		ContainerAnnotationTools.synchAnnotationsAfterMove(sourceIndex, targetIndex, this.uniqueConstraintsContainerAnnotation);
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchUniqueConstraintAnnotationsAfterAdd(int index) {
		ContainerAnnotationTools.synchAnnotationsAfterAdd(index, this.uniqueConstraintsContainerAnnotation);
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchUniqueConstraintAnnotationsAfterRemove(int index) {
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.uniqueConstraintsContainerAnnotation);
	}
	
	protected abstract NestableUniqueConstraint createUniqueConstraint(int index);

	
	private class UniqueConstraintsContainerAnnotation implements ContainerAnnotation<NestableUniqueConstraint> {

		public NestableUniqueConstraint add(int index) {
			NestableUniqueConstraint uniqueConstraint = createNestedAnnotation(index);
			AbstractTableResource.this.addUniqueConstraint(uniqueConstraint);
			return uniqueConstraint;
		}

		public NestableUniqueConstraint createNestedAnnotation(int index) {
			return AbstractTableResource.this.createUniqueConstraint(index);
		}

		public String getAnnotationName() {
			return AbstractTableResource.this.getAnnotationName();
		}

		public String getNestableAnnotationName() {
			return JPA.UNIQUE_CONSTRAINT;
		}

		public int indexOf(NestableUniqueConstraint uniqueConstraint) {
			return AbstractTableResource.this.indexOfUniqueConstraint(uniqueConstraint);
		}

		public void move(int oldIndex, int newIndex) {
			AbstractTableResource.this.uniqueConstraints.add(newIndex, AbstractTableResource.this.uniqueConstraints.remove(oldIndex));
		}

		public NestableUniqueConstraint nestedAnnotationAt(int index) {
			return AbstractTableResource.this.uniqueConstraintAt(index);
		}

		public NestableUniqueConstraint nestedAnnotationFor(Annotation jdtAnnotation) {
			for (NestableUniqueConstraint uniqueConstraint : CollectionTools.iterable(nestedAnnotations())) {
				if (jdtAnnotation == uniqueConstraint.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
					return uniqueConstraint;
				}
			}
			return null;
		}

		public ListIterator<NestableUniqueConstraint> nestedAnnotations() {
			return new CloneListIterator<NestableUniqueConstraint>(AbstractTableResource.this.uniqueConstraints);
		}

		public int nestedAnnotationsSize() {
			return AbstractTableResource.this.uniqueConstraints.size();
		}

		public void remove(NestableUniqueConstraint uniqueConstraint) {
			this.remove(indexOf(uniqueConstraint));
		}

		public void remove(int index) {
			AbstractTableResource.this.removeUniqueConstraint(index);	
		}

		public Annotation jdtAnnotation(CompilationUnit astRoot) {
			return AbstractTableResource.this.jdtAnnotation(astRoot);
		}

		public void newAnnotation() {
			AbstractTableResource.this.newAnnotation();
		}

		public void removeAnnotation() {
			AbstractTableResource.this.removeAnnotation();
		}

		public IJpaPlatform jpaPlatform() {
			return AbstractTableResource.this.jpaPlatform();
		}

		public void updateFromJava(CompilationUnit astRoot) {
			AbstractTableResource.this.updateFromJava(astRoot);
		}
		
	}
}
