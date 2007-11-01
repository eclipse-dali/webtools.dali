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
import org.eclipse.jpt.core.internal.ITextRange;
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
	
	public void initialize(CompilationUnit astRoot) {
		this.name = this.name(astRoot);
		this.catalog = this.catalog(astRoot);
		this.schema = this.schema(astRoot);
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this.uniqueConstraintsContainerAnnotation);
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

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.nameAdapter.setValue(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String newCatalog) {
		String oldCatalog = this.catalog;
		this.catalog = newCatalog;
		this.catalogAdapter.setValue(newCatalog);
		firePropertyChanged(CATALOG_PROPERTY, oldCatalog, newCatalog);
	}

	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String newSchema) {
		String oldSchema = this.schema;
		this.schema = newSchema;
		this.schemaAdapter.setValue(newSchema);
		firePropertyChanged(SCHEMA_PROPERTY, oldSchema, newSchema);
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
	
	public NestableUniqueConstraint addUniqueConstraint(int index) {
		NestableUniqueConstraint uniqueConstraint = createUniqueConstraint(index);
		addUniqueConstraint(index, uniqueConstraint);
		ContainerAnnotationTools.synchAnnotationsAfterAdd(index + 1, this.uniqueConstraintsContainerAnnotation);
		uniqueConstraint.newAnnotation();
		return uniqueConstraint;
	}
	
	private void addUniqueConstraint(int index, NestableUniqueConstraint uniqueConstraint) {
		addItemToList(index, uniqueConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}
	
	public void removeUniqueConstraint(int index) {
		NestableUniqueConstraint uniqueConstraint = this.uniqueConstraintAt(index);
		removeUniqueConstraint(uniqueConstraint);
		uniqueConstraint.removeAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.uniqueConstraintsContainerAnnotation);
	}

	private void removeUniqueConstraint(NestableUniqueConstraint uniqueConstraint) {
		removeItemFromList(uniqueConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}
	
	public void moveUniqueConstraint(int oldIndex, int newIndex) {
		moveItemInList(newIndex, oldIndex, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
		ContainerAnnotationTools.synchAnnotationsAfterMove(newIndex, oldIndex, this.uniqueConstraintsContainerAnnotation);
	}
	
	protected abstract NestableUniqueConstraint createUniqueConstraint(int index);

	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return elementTextRange(this.nameDeclarationAdapter, astRoot);
	}
	
	public ITextRange schemaTextRange(CompilationUnit astRoot) {
		return elementTextRange(this.schemaDeclarationAdapter, astRoot);
	}
	
	public ITextRange catalogTextRange(CompilationUnit astRoot) {
		return elementTextRange(this.catalogDeclarationAdapter, astRoot);
	}
	
	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.nameDeclarationAdapter, pos, astRoot);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
		this.setSchema(this.schema(astRoot));
		this.setCatalog(this.catalog(astRoot));
		this.updateUniqueConstraintsFromJava(astRoot);
	}
	
	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}
	
	protected String schema(CompilationUnit astRoot) {
		return this.schemaAdapter.getValue(astRoot);
	}
	
	protected String catalog(CompilationUnit astRoot) {
		return this.catalogAdapter.getValue(astRoot);
	}
	
	/**
	 * here we just worry about getting the unique constraints lists the same size;
	 * then we delegate to the unique constraints to synch themselves up
	 */
	private void updateUniqueConstraintsFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this.uniqueConstraintsContainerAnnotation);
	}

	
	private class UniqueConstraintsContainerAnnotation extends AbstractResource 
		implements ContainerAnnotation<NestableUniqueConstraint> 
	{
		public UniqueConstraintsContainerAnnotation() {
			super(AbstractTableResource.this);
		}
		
		public void initialize(CompilationUnit astRoot) {
			//nothing to initialize
		}

		public NestableUniqueConstraint addInternal(int index) {
			NestableUniqueConstraint uniqueConstraint = AbstractTableResource.this.createUniqueConstraint(index);
			AbstractTableResource.this.uniqueConstraints.add(index, uniqueConstraint);			
			return uniqueConstraint;
		}
		
		public NestableUniqueConstraint add(int index) {
			NestableUniqueConstraint uniqueConstraint = AbstractTableResource.this.createUniqueConstraint(index);
			AbstractTableResource.this.addUniqueConstraint(index, uniqueConstraint);
			return uniqueConstraint;
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
			AbstractTableResource.this.moveUniqueConstraint(oldIndex, newIndex);
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
			return AbstractTableResource.this.uniqueConstraintsSize();
		}

		public void remove(NestableUniqueConstraint uniqueConstraint) {
			AbstractTableResource.this.removeUniqueConstraint(uniqueConstraint);	
		}

		public void remove(int index) {
			this.remove(nestedAnnotationAt(index));
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

		public void updateFromJava(CompilationUnit astRoot) {
			AbstractTableResource.this.updateFromJava(astRoot);
		}
		
		public ITextRange textRange(CompilationUnit astRoot) {
			return AbstractTableResource.this.textRange(astRoot);
		}
	}
}
