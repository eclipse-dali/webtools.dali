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
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class SecondaryTablesImpl extends AbstractAnnotationResource<Member> implements SecondaryTables
{
	private static final String ANNOTATION_NAME = JPA.SECONDARY_TABLES;
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final List<NestableSecondaryTable> secondaryTables;
	
	protected SecondaryTablesImpl(JavaPersistentTypeResource parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.secondaryTables = new ArrayList<NestableSecondaryTable>();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return JPA.SECONDARY_TABLE;
	}
	
	public ListIterator<NestableSecondaryTable> nestedAnnotations() {
		return new CloneListIterator<NestableSecondaryTable>(this.secondaryTables);
	}
	
	public int nestedAnnotationsSize() {
		return this.secondaryTables.size();
	}
	
	public NestableSecondaryTable add(int index) {
		NestableSecondaryTable javaSecondaryTableResource = createSecondaryTable(index);
		this.add(index, javaSecondaryTableResource);
		return javaSecondaryTableResource;
	}
	
	private void add(int index, NestableSecondaryTable secondaryTable) {
		this.secondaryTables.add(index, secondaryTable);
		//TODO event notification
	}
	
	public void remove(NestableSecondaryTable secondaryTable) {
		this.secondaryTables.remove(secondaryTable);
	}
	
	public void remove(int index) {
		this.secondaryTables.remove(index);
	}
	
	public int indexOf(NestableSecondaryTable secondaryTable) {
		return this.secondaryTables.indexOf(secondaryTable);
	}
	
	public NestableSecondaryTable nestedAnnotationAt(int index) {
		return this.secondaryTables.get(index);
	}
	
	public NestableSecondaryTable nestedAnnotationFor(org.eclipse.jdt.core.dom.Annotation jdtAnnotation) {
		for (NestableSecondaryTable secondaryTable : this.secondaryTables) {
			if (jdtAnnotation == secondaryTable.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
				return secondaryTable;
			}
		}
		return null;
	}

	public void move(int oldIndex, int newIndex) {
		this.secondaryTables.add(newIndex, this.secondaryTables.remove(oldIndex));
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this);
	}
	
	private SecondaryTableImpl createSecondaryTable(int index) {
		return SecondaryTableImpl.createNestedSecondaryTable(this, getMember(), index, getDeclarationAnnotationAdapter());
	}
	
	public static class SecondaryTablesAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final SecondaryTablesAnnotationDefinition INSTANCE = new SecondaryTablesAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private SecondaryTablesAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new SecondaryTablesImpl((JavaPersistentTypeResource) parent, member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}


}
