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
import org.eclipse.jpt.core.internal.utility.jdt.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitArrayAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableUniqueConstraint;
import org.eclipse.jpt.core.resource.java.UniqueConstraint;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class UniqueConstraintImpl extends AbstractResourceAnnotation<Member> implements NestableUniqueConstraint
{
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.UNIQUE_CONSTRAINT);

	private final DeclarationAnnotationElementAdapter<String[]> columnNamesDeclarationAdapter;

	private final AnnotationElementAdapter<String[]> columnNamesAdapter;

	private final List<String> columnNames;


	public UniqueConstraintImpl(JavaResourceNode parent, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, member, idaa, new MemberIndexedAnnotationAdapter(member, idaa));
		this.columnNamesDeclarationAdapter = buildArrayAnnotationElementAdapter(idaa, JPA.UNIQUE_CONSTRAINT__COLUMN_NAMES);
		this.columnNamesAdapter = this.buildAnnotationElementAdapter(this.columnNamesDeclarationAdapter);
		this.columnNames = new ArrayList<String>();
	}

	public void initialize(CompilationUnit astRoot) {
		String[] javaColumnNames = this.columnNamesAdapter.value(astRoot);
		for (int i = 0; i < javaColumnNames.length; i++) {
			this.columnNames.add(javaColumnNames[i]);
		}
	}
	
	protected AnnotationElementAdapter<String[]> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String[]> daea) {
		return new ShortCircuitArrayAnnotationElementAdapter<String>(getMember(), daea);
	}

	protected static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return buildArrayAnnotationElementAdapter(annotationAdapter, elementName, AnnotationStringArrayExpressionConverter.forStrings());
	}

	protected static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String[]> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String[]>(annotationAdapter, elementName, false, converter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	@Override
	public IndexedAnnotationAdapter getAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}
	
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		UniqueConstraint oldUniqueConstraint = (UniqueConstraint) oldAnnotation;
		for (String columnName : CollectionTools.iterable(oldUniqueConstraint.columnNames())) {
			addColumnName(columnName);
		}
	}
	
	public ListIterator<String> columnNames() {
		return new CloneListIterator<String>(this.columnNames);
	}

	public int columnNamesSize() {
		return this.columnNames.size();
	}
	
	public void addColumnName(String columnName) {
		addItemToList(columnName, this.columnNames, COLUMN_NAMES_LIST);
		this.columnNamesAdapter.setValue(this.columnNames.toArray(new String[this.columnNames.size()]));
	}
	
	public void removeColumnName(String columnName) {
		removeItemFromList(columnName, this.columnNames, COLUMN_NAMES_LIST);
		this.columnNamesAdapter.setValue(this.columnNames.toArray(new String[this.columnNames.size()]));
	}

	public boolean columnNamesTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.columnNamesDeclarationAdapter, pos, astRoot);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		this.updateColumnNamesFromJava(astRoot);
	}

	protected void updateColumnNamesFromJava(CompilationUnit astRoot) {
		String[] javaColumnNames = this.columnNamesAdapter.value(astRoot);
		//TODO hmm, seems we need change notification for this
		CollectionTools.retainAll(this.columnNames, javaColumnNames);
		for (int i = 0; i < javaColumnNames.length; i++) {
			String columnName = javaColumnNames[i];
			if (!this.columnNames.contains(columnName)) {
				addColumnName(columnName);
			}
		}
	}

	// ********** persistence model -> java annotations **********
	public void moveAnnotation(int newIndex) {
		getAnnotationAdapter().moveAnnotation(newIndex);
	}

	// ********** static methods **********
	static NestableUniqueConstraint createSecondaryTableUniqueConstraint(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter declarationAnnotationAdapter, int index) {
		return new UniqueConstraintImpl(parent, member, buildSecondaryTableUniqueConstraintAnnotationAdapter(declarationAnnotationAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildSecondaryTableUniqueConstraintAnnotationAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static NestableUniqueConstraint createJoinTableUniqueConstraint(JavaResourceNode parent, Member member, int index) {
		return new UniqueConstraintImpl(parent, member, buildJoinTableUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JoinTableImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static NestableUniqueConstraint createTableUniqueConstraint(JavaResourceNode parent, Member member, int index) {
		return new UniqueConstraintImpl(parent, member, buildTableUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildTableUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(TableImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static NestableUniqueConstraint createTableGeneratorUniqueConstraint(JavaResourceNode parent, Member member, int index) {
		return new UniqueConstraintImpl(parent, member, buildTableGeneratorUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildTableGeneratorUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(TableGeneratorImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE_GENERATOR__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}
}
