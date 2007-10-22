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
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitArrayAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class UniqueConstraintImpl extends AbstractAnnotationResource<Member> implements NestableUniqueConstraint
{
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.UNIQUE_CONSTRAINT);

	private final DeclarationAnnotationElementAdapter<String[]> columnNamesDeclarationAdapter;

	private final AnnotationElementAdapter<String[]> columnNamesAdapter;

	private final List<String> columnNames;


	public UniqueConstraintImpl(JavaResource parent, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, member, idaa, new MemberIndexedAnnotationAdapter(member, idaa));
		this.columnNamesDeclarationAdapter = buildArrayAnnotationElementAdapter(idaa, JPA.UNIQUE_CONSTRAINT__COLUMN_NAMES);
		this.columnNamesAdapter = this.buildAnnotationElementAdapter(this.columnNamesDeclarationAdapter);
		this.columnNames = new ArrayList<String>();
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
		return JPA.UNIQUE_CONSTRAINT;
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

	public void addColumnName(String columnName) {
		this.columnNames.add(columnName);
		this.columnNamesAdapter.setValue(this.columnNames.toArray(new String[this.columnNames.size()]));
	}
	
	public void removeColumnName(String columnName) {
		this.columnNames.remove(columnName);
		this.columnNamesAdapter.setValue(this.columnNames.toArray(new String[this.columnNames.size()]));
	}

	public boolean columnNamesTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.columnNamesDeclarationAdapter, pos, astRoot);
	}

	/**
	 * allow owners to verify the annotation
	 */
	public org.eclipse.jdt.core.dom.Annotation jdtAnnotation(CompilationUnit astRoot) {
		return getAnnotationAdapter().getAnnotation(astRoot);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		updateColumnNamesFromJava(astRoot);
	}

	private void updateColumnNamesFromJava(CompilationUnit astRoot) {
		String[] javaColumnNames = this.columnNamesAdapter.getValue(astRoot);
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

	public void newAnnotation() {
		getAnnotationAdapter().newMarkerAnnotation();
	}

	public void removeAnnotation() {
		getAnnotationAdapter().removeAnnotation();
	}

	// ********** static methods **********
	static NestableUniqueConstraint createSecondaryTableUniqueConstraint(JavaResource parent, DeclarationAnnotationAdapter declarationAnnotationAdapter, Member member, int index) {
		return new UniqueConstraintImpl(parent, member, buildSecondaryTableUniqueConstraintAnnotationAdapter(declarationAnnotationAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildSecondaryTableUniqueConstraintAnnotationAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static NestableUniqueConstraint createJoinTableUniqueConstraint(JavaResource parent, Member member, int index) {
		return new UniqueConstraintImpl(parent, member, buildJoinTableUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JoinTableImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static NestableUniqueConstraint createTableUniqueConstraint(JavaResource parent, Member member, int index) {
		return new UniqueConstraintImpl(parent, member, buildTableUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildTableUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(TableImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static NestableUniqueConstraint createTableGeneratorUniqueConstraint(JavaResource parent, Member member, int index) {
		return new UniqueConstraintImpl(parent, member, buildTableGeneratorUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildTableGeneratorUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(TableGeneratorImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE_GENERATOR__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}
}
