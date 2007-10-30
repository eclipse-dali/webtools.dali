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
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class PrimaryKeyJoinColumnsImpl extends AbstractAnnotationResource<Member> implements PrimaryKeyJoinColumns
{
	private static final String ANNOTATION_NAME = JPA.PRIMARY_KEY_JOIN_COLUMNS;
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private List<NestablePrimaryKeyJoinColumn> pkJoinColumns;
	
	protected PrimaryKeyJoinColumnsImpl(JavaResource parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.pkJoinColumns = new ArrayList<NestablePrimaryKeyJoinColumn>();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMN;
	}
		
	public ListIterator<NestablePrimaryKeyJoinColumn> nestedAnnotations() {
		return new CloneListIterator<NestablePrimaryKeyJoinColumn>(this.pkJoinColumns);
	}
	
	public int nestedAnnotationsSize() {
		return this.pkJoinColumns.size();
	}	

	public NestablePrimaryKeyJoinColumn add(int index) {
		PrimaryKeyJoinColumnImpl pkJoinColumn = createPrimaryKeyJoinColumn(index);
		add(index, pkJoinColumn);
		return pkJoinColumn;
	}
	
	private void add(int index, NestablePrimaryKeyJoinColumn pkJoinColumn) {
		this.pkJoinColumns.add(index, pkJoinColumn);
		//TODO event notification
	}

	public void remove(NestablePrimaryKeyJoinColumn pkJoinColumn) {
		this.pkJoinColumns.remove(pkJoinColumn);		
	}
	
	public void remove(int index) {
		this.pkJoinColumns.remove(index);
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
	
	public void move(int oldIndex, int newIndex) {
		this.pkJoinColumns.add(newIndex, this.pkJoinColumns.remove(oldIndex));
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

		public PrimaryKeyJoinColumns buildAnnotation(JavaResource parent, Member member) {
			return new PrimaryKeyJoinColumnsImpl(parent, member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
