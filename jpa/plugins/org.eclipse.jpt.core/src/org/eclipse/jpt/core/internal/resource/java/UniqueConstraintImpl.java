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

	private final UniqueConstraint.Owner owner;
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.UNIQUE_CONSTRAINT);

	private final DeclarationAnnotationElementAdapter<String[]> columnNamesDeclarationAdapter;

	private final AnnotationElementAdapter<String[]> columnNamesAdapter;

	private final List<String> columnNames;


	public UniqueConstraintImpl(UniqueConstraint.Owner owner, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super(owner.javaResource(), member, idaa, new MemberIndexedAnnotationAdapter(member, idaa));
		this.owner = owner;
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
		//don't need to implement this, uniqueConstraints can't move from nested to unnested or vice versa
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
	
	public UniqueConstraint.Owner getOwner() {
		return this.owner;
	}
//
//	public ITextRange validationTextRange() {
//		return this.member.annotationTextRange(this.idaa);
//	}
//
//	protected boolean elementTouches(DeclarationAnnotationElementAdapter<?> elementAdapter, int pos, CompilationUnit astRoot) {
//		return this.elementTouches(this.member.annotationElementTextRange(elementAdapter, astRoot), pos);
//	}
//
//	private boolean columnNamesTouches(int pos, CompilationUnit astRoot) {
//		return this.elementTouches(this.columnNamesDeclarationAdapter, pos, astRoot);
//	}
//
//	private Iterator<String> candidateColumnNames() {
//		return this.getOwner().candidateUniqueConstraintColumnNames();
//	}
//
//	private Iterator<String> candidateColumnNames(Filter<String> filter) {
//		return new FilteringIterator<String>(this.candidateColumnNames(), filter);
//	}
//
//	private Iterator<String> quotedCandidateColumnNames(Filter<String> filter) {
//		return StringTools.quote(this.candidateColumnNames(filter));
//	}
//
//	@Override
//	public Iterator<String> connectedCandidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
//		Iterator<String> result = super.connectedCandidateValuesFor(pos, filter, astRoot);
//		if (result != null) {
//			return result;
//		}
//		if (this.columnNamesTouches(pos, astRoot)) {
//			return this.quotedCandidateColumnNames(filter);
//		}
//		return null;
//	}

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
	static NestableUniqueConstraint createSecondaryTableUniqueConstraint(UniqueConstraint.Owner owner, DeclarationAnnotationAdapter declarationAnnotationAdapter, Member member, int index) {
		return new UniqueConstraintImpl(owner, member, buildSecondaryTableUniqueConstraintAnnotationAdapter(declarationAnnotationAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildSecondaryTableUniqueConstraintAnnotationAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static NestableUniqueConstraint createJoinTableUniqueConstraint(UniqueConstraint.Owner owner, Member member, int index) {
		return new UniqueConstraintImpl(owner, member, buildJoinTableUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JoinTableImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static NestableUniqueConstraint createTableUniqueConstraint(UniqueConstraint.Owner owner, Member member, int index) {
		return new UniqueConstraintImpl(owner, member, buildTableUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildTableUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(TableImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static NestableUniqueConstraint createTableGeneratorUniqueConstraint(UniqueConstraint.Owner owner, Member member, int index) {
		return new UniqueConstraintImpl(owner, member, buildTableGeneratorUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildTableGeneratorUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(TableGeneratorImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE_GENERATOR__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}	


}
