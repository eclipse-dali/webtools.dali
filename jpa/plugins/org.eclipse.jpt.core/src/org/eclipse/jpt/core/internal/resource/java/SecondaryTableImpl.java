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
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class SecondaryTableImpl extends AbstractTableResource implements NestableSecondaryTable
{	
	private static final String ANNOTATION_NAME = JPA.SECONDARY_TABLE;

	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	private final List<NestablePrimaryKeyJoinColumn> pkJoinColumns;
	
	private final PkJoinColumnsContainerAnnotation pkJoinColumnsContainerAnnotation;
	

	protected SecondaryTableImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.pkJoinColumns = new ArrayList<NestablePrimaryKeyJoinColumn>();
		this.pkJoinColumnsContainerAnnotation = new PkJoinColumnsContainerAnnotation();
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> catalogAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__CATALOG);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> nameAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__NAME);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> schemaAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__SCHEMA);
	}

	public IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	public void moveAnnotation(int newIndex) {
		getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}
	
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		SecondaryTable oldSecondaryTable = (SecondaryTable) oldAnnotation;
		setName(oldSecondaryTable.getName());
		setCatalog(oldSecondaryTable.getCatalog());
		setSchema(oldSecondaryTable.getSchema());
		for (UniqueConstraint uniqueConstraint : CollectionTools.iterable(oldSecondaryTable.uniqueConstraints())) {
			NestableUniqueConstraint newUniqueConstraint = addUniqueConstraint(oldSecondaryTable.indexOfUniqueConstraint(uniqueConstraint));
			newUniqueConstraint.initializeFrom((NestableAnnotation) uniqueConstraint);
		}
	}
	
	@Override
	protected NestableUniqueConstraint createUniqueConstraint(int index) {
		return UniqueConstraintImpl.createSecondaryTableUniqueConstraint(new UniqueConstraintOwner(this), this.getDeclarationAnnotationAdapter(), this.getMember(), index);
	}
	
	// ************* SecondaryTable implementation *******************
	
	
	public ListIterator<PrimaryKeyJoinColumn> pkJoinColumns() {
		return new CloneListIterator<PrimaryKeyJoinColumn>(this.pkJoinColumns);
	}
	
	public int pkJoinColumnsSize() {
		return this.pkJoinColumns.size();
	}
	
	public NestablePrimaryKeyJoinColumn pkJoinColumnAt(int index) {
		return this.pkJoinColumns.get(index);
	}
	
	public int indexOfPkJoinColumn(PrimaryKeyJoinColumn joinColumn) {
		return this.pkJoinColumns.indexOf(joinColumn);
	}
	
	public PrimaryKeyJoinColumn addPkJoinColumn(int index) {
		NestablePrimaryKeyJoinColumn pkJoinColumn = createPrimaryKeyJoinColumn(index);
		addPkJoinColumn(pkJoinColumn);
		pkJoinColumn.newAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterAdd(index, this.pkJoinColumnsContainerAnnotation);
		return pkJoinColumn;
	}
	
	private void addPkJoinColumn(NestablePrimaryKeyJoinColumn pkJoinColumn) {
		this.pkJoinColumns.add(pkJoinColumn);
		//property change notification
	}
	
	public void removePkJoinColumn(int index) {
		NestablePrimaryKeyJoinColumn joinColumn = this.pkJoinColumns.remove(index);
		joinColumn.removeAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.pkJoinColumnsContainerAnnotation);
	}

	public void movePkJoinColumn(int oldIndex, int newIndex) {
		this.pkJoinColumns.add(newIndex, this.pkJoinColumns.remove(oldIndex));
		ContainerAnnotationTools.synchAnnotationsAfterMove(newIndex, oldIndex, this.pkJoinColumnsContainerAnnotation);
	}


	protected NestablePrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index) {
		return PrimaryKeyJoinColumnImpl.createSecondaryTablePrimaryKeyJoinColumn(getDeclarationAnnotationAdapter(), this, getMember(), index);
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.updatePkJoinColumnsFromJava(astRoot);
	}
	
	private void updatePkJoinColumnsFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this.pkJoinColumnsContainerAnnotation);
	}

	// ********** static methods **********
	static SecondaryTableImpl createSecondaryTable(JavaResource parent, Member member) {
		return new SecondaryTableImpl(parent, member, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	static SecondaryTableImpl createNestedSecondaryTable(JavaResource parent, Member member, int index, DeclarationAnnotationAdapter secondaryTablesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, secondaryTablesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new SecondaryTableImpl(parent, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter secondaryTablesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(secondaryTablesAdapter, index, JPA.SECONDARY_TABLE);
	}
	
	
	private class PkJoinColumnsContainerAnnotation implements ContainerAnnotation<NestablePrimaryKeyJoinColumn> {

		public NestablePrimaryKeyJoinColumn add(int index) {
			NestablePrimaryKeyJoinColumn pKeyJoinColumn = createNestedAnnotation(index);
			SecondaryTableImpl.this.addPkJoinColumn(pKeyJoinColumn);
			return pKeyJoinColumn;
		}

		public NestablePrimaryKeyJoinColumn createNestedAnnotation(int index) {
			return SecondaryTableImpl.this.createPrimaryKeyJoinColumn(index);
		}

		public int indexOf(NestablePrimaryKeyJoinColumn pkJoinColumn) {
			return SecondaryTableImpl.this.indexOfPkJoinColumn(pkJoinColumn);
		}

		public void move(int oldIndex, int newIndex) {
			SecondaryTableImpl.this.pkJoinColumns.add(newIndex, SecondaryTableImpl.this.pkJoinColumns.remove(oldIndex));
		}

		public NestablePrimaryKeyJoinColumn nestedAnnotationAt(int index) {
			return SecondaryTableImpl.this.pkJoinColumnAt(index);
		}

		public ListIterator<NestablePrimaryKeyJoinColumn> nestedAnnotations() {
			return new CloneListIterator<NestablePrimaryKeyJoinColumn>(SecondaryTableImpl.this.pkJoinColumns);
		}

		public int nestedAnnotationsSize() {
			return pkJoinColumnsSize();
		}

		public void remove(int index) {
			SecondaryTableImpl.this.removePkJoinColumn(index);	
		}		

		public String getAnnotationName() {
			return SecondaryTableImpl.this.getAnnotationName();
		}

		public String getNestableAnnotationName() {
			return JPA.PRIMARY_KEY_JOIN_COLUMN;
		}

		public NestablePrimaryKeyJoinColumn nestedAnnotationFor(org.eclipse.jdt.core.dom.Annotation jdtAnnotation) {
			for (NestablePrimaryKeyJoinColumn pkJoinColumn : CollectionTools.iterable(nestedAnnotations())) {
				if (jdtAnnotation == pkJoinColumn.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
					return pkJoinColumn;
				}
			}
			return null;
		}

		public void remove(NestablePrimaryKeyJoinColumn joinColumn) {
			this.remove(indexOf(joinColumn));
		}

		public org.eclipse.jdt.core.dom.Annotation jdtAnnotation(CompilationUnit astRoot) {
			return SecondaryTableImpl.this.jdtAnnotation(astRoot);
		}

		public void newAnnotation() {
			SecondaryTableImpl.this.newAnnotation();
		}

		public void removeAnnotation() {
			SecondaryTableImpl.this.removeAnnotation();
		}

		public IJpaPlatform jpaPlatform() {
			return SecondaryTableImpl.this.jpaPlatform();
		}

		public void updateFromJava(CompilationUnit astRoot) {
			SecondaryTableImpl.this.updateFromJava(astRoot);
		}
		
	}

	public static class SecondaryTableAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final SecondaryTableAnnotationDefinition INSTANCE = new SecondaryTableAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private SecondaryTableAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return SecondaryTableImpl.createSecondaryTable(parent, member);
		}
		
		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
