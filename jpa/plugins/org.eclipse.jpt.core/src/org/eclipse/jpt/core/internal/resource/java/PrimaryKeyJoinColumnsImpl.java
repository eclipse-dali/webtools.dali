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
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestablePrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumns;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class PrimaryKeyJoinColumnsImpl extends AbstractAnnotationResource<Member> implements PrimaryKeyJoinColumns
{
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private List<NestablePrimaryKeyJoinColumn> pkJoinColumns;
	
	protected PrimaryKeyJoinColumnsImpl(JavaResourceNode parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.pkJoinColumns = new ArrayList<NestablePrimaryKeyJoinColumn>();
	}

	public void initialize(CompilationUnit astRoot) {
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME;
	}

	public String getElementName() {
		return "value";
	}
		
	public ListIterator<NestablePrimaryKeyJoinColumn> nestedAnnotations() {
		return new CloneListIterator<NestablePrimaryKeyJoinColumn>(this.pkJoinColumns);
	}
	
	public int nestedAnnotationsSize() {
		return this.pkJoinColumns.size();
	}	

	public NestablePrimaryKeyJoinColumn addInternal(int index) {
		NestablePrimaryKeyJoinColumn pkJoinColumn = createPrimaryKeyJoinColumn(index);
		this.pkJoinColumns.add(index, pkJoinColumn);
		return pkJoinColumn;
	}
	
	public NestablePrimaryKeyJoinColumn add(int index) {
		NestablePrimaryKeyJoinColumn pkJoinColumn = createPrimaryKeyJoinColumn(index);
		add(index, pkJoinColumn);
		return pkJoinColumn;
	}
	
	protected void add(int index, NestablePrimaryKeyJoinColumn pkJoinColumn) {
		addItemToList(index, pkJoinColumn, this.pkJoinColumns, PK_JOIN_COLUMNS_LIST);
	}

	public void remove(NestablePrimaryKeyJoinColumn pkJoinColumn) {
		removeItemFromList(pkJoinColumn, this.pkJoinColumns, PK_JOIN_COLUMNS_LIST);
	}
	
	public void remove(int index) {
		removeItemFromList(index, this.pkJoinColumns, PK_JOIN_COLUMNS_LIST);
	}
	
	public int indexOf(NestablePrimaryKeyJoinColumn pkJoinColumn) {
		return this.pkJoinColumns.indexOf(pkJoinColumn);
	}
	
	public NestablePrimaryKeyJoinColumn nestedAnnotationAt(int index) {
		return this.pkJoinColumns.get(index);
	}
	
	public NestablePrimaryKeyJoinColumn nestedAnnotationFor(Annotation jdtAnnotation) {
		for (NestablePrimaryKeyJoinColumn pkJoinColumn : this.pkJoinColumns) {
			if (jdtAnnotation == pkJoinColumn.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
				return pkJoinColumn;
			}
		}
		return null;
	}
	
	public void move(int targetIndex, int sourceIndex) {
		moveItemInList(targetIndex, sourceIndex, this.pkJoinColumns, PK_JOIN_COLUMNS_LIST);
	}
	
	public void moveInternal(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.pkJoinColumns, targetIndex, sourceIndex);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this);
	}
	
	private PrimaryKeyJoinColumnImpl createPrimaryKeyJoinColumn(int index) {
		return PrimaryKeyJoinColumnImpl.createNestedPrimaryKeyJoinColumn(this, getMember(), index, getDeclarationAnnotationAdapter());
	}

	
	public static class PrimaryKeyJoinColumnsAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final PrimaryKeyJoinColumnsAnnotationDefinition INSTANCE = new PrimaryKeyJoinColumnsAnnotationDefinition();


		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private PrimaryKeyJoinColumnsAnnotationDefinition() {
			super();
		}

		public PrimaryKeyJoinColumns buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new PrimaryKeyJoinColumnsImpl(parent, member);
		}
		
		public PrimaryKeyJoinColumns buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
