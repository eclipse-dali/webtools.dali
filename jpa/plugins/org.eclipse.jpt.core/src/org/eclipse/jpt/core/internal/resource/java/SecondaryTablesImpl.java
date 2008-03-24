/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NestableSecondaryTable;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTables;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class SecondaryTablesImpl extends AbstractResourceAnnotation<Member> implements SecondaryTables
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final List<NestableSecondaryTable> secondaryTables;
	
	protected SecondaryTablesImpl(JavaResourcePersistentType parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.secondaryTables = new ArrayList<NestableSecondaryTable>();
	}

	public void initialize(CompilationUnit astRoot) {
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return SecondaryTableAnnotation.ANNOTATION_NAME;
	}

	public String getElementName() {
		return "value";
	}
	
	public ListIterator<NestableSecondaryTable> nestedAnnotations() {
		return new CloneListIterator<NestableSecondaryTable>(this.secondaryTables);
	}
	
	public int nestedAnnotationsSize() {
		return this.secondaryTables.size();
	}
	
	public NestableSecondaryTable addInternal(int index) {
		NestableSecondaryTable secondaryTable = createSecondaryTable(index);
		this.secondaryTables.add(index, secondaryTable);
		return secondaryTable;
	}

	public NestableSecondaryTable add(int index) {
		NestableSecondaryTable secondaryTable = createSecondaryTable(index);
		this.add(index, secondaryTable);
		return secondaryTable;
	}
	
	protected void add(int index, NestableSecondaryTable secondaryTable) {
		addItemToList(index, secondaryTable, this.secondaryTables, SecondaryTables.SECONDARY_TABLES_LIST);
	}
	
	public void remove(NestableSecondaryTable secondaryTable) {
		removeItemFromList(secondaryTable, this.secondaryTables, SecondaryTables.SECONDARY_TABLES_LIST);
	}
	
	public void remove(int index) {
		removeItemFromList(index, this.secondaryTables, SecondaryTables.SECONDARY_TABLES_LIST);
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

	public void move(int targetIndex, int sourceIndex) {
		moveItemInList(targetIndex, sourceIndex,  this.secondaryTables, SecondaryTables.SECONDARY_TABLES_LIST);
	}
	//TODO this move is different than how we handle SecondarTable.pkJoinColumns
//	public void movePkJoinColumn(int oldIndex, int newIndex) {
//		movePkJoinColumnInternal(oldIndex, newIndex);
//		ContainerAnnotationTools.synchAnnotationsAfterMove(newIndex, oldIndex, this.pkJoinColumnsContainerAnnotation);
//		fireItemMoved(SecondaryTable.PK_JOIN_COLUMNS_LIST, newIndex, oldIndex);
//	}
	
	public void moveInternal(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.secondaryTables, targetIndex, sourceIndex);
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

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new SecondaryTablesImpl((JavaResourcePersistentType) parent, member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}


}
