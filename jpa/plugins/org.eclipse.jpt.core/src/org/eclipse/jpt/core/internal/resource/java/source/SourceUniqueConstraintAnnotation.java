/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitArrayAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableUniqueConstraintAnnotation;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.UniqueConstraint
 */
public final class SourceUniqueConstraintAnnotation
	extends SourceAnnotation<Member>
	implements NestableUniqueConstraintAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final DeclarationAnnotationElementAdapter<String[]> columnNamesDeclarationAdapter;
	private final AnnotationElementAdapter<String[]> columnNamesAdapter;
	private final Vector<String> columnNames = new Vector<String>();


	public SourceUniqueConstraintAnnotation(JavaResourceNode parent, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, member, idaa, new MemberIndexedAnnotationAdapter(member, idaa));
		this.columnNamesDeclarationAdapter = buildArrayAnnotationElementAdapter(idaa, JPA.UNIQUE_CONSTRAINT__COLUMN_NAMES);
		this.columnNamesAdapter = this.buildAnnotationElementAdapter(this.columnNamesDeclarationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	private AnnotationElementAdapter<String[]> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String[]> daea) {
		return new ShortCircuitArrayAnnotationElementAdapter<String>(this.member, daea);
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return buildArrayAnnotationElementAdapter(annotationAdapter, elementName, AnnotationStringArrayExpressionConverter.forStrings());
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String[]> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String[]>(annotationAdapter, elementName, false, converter);
	}

	public void initialize(CompilationUnit astRoot) {
		String[] javaColumnNames = this.columnNamesAdapter.getValue(astRoot);
		for (int i = 0; i < javaColumnNames.length; i++) {
			this.columnNames.add(javaColumnNames[i]);
		}
	}

	public void update(CompilationUnit astRoot) {
		this.updateColumnNames(astRoot);
	}

	private void updateColumnNames(CompilationUnit astRoot) {
		String[] javaColumnNames = this.columnNamesAdapter.getValue(astRoot);
		this.synchronizeList(Arrays.asList(javaColumnNames), this.columnNames, COLUMN_NAMES_LIST);
	}

	public IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.columnNames);
	}


	// ********** UniqueConstraintAnnotation implementation **********

	// ***** column names
	public ListIterator<String> columnNames() {
		return new CloneListIterator<String>(this.columnNames);
	}

	public int columnNamesSize() {
		return this.columnNames.size();
	}

	public void addColumnName(String columnName) {
		this.addColumnName(this.columnNames.size(), columnName);
	}

	public void addColumnName(int index, String columnName) {
		this.addColumnName_(index, columnName);
		this.columnNamesAdapter.setValue(this.columnNames.toArray(new String[this.columnNames.size()]));
	}

	private void addColumnName_(int index, String columnName) {
		this.addItemToList(index, columnName, this.columnNames, COLUMN_NAMES_LIST);
	}

	public void moveColumnName(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.columnNames, COLUMN_NAMES_LIST);
		this.columnNamesAdapter.setValue(this.columnNames.toArray(new String[this.columnNames.size()]));
	}

	public boolean columnNamesTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.columnNamesDeclarationAdapter, pos, astRoot);
	}

	public void removeColumnName(String columnName) {
		this.removeItemFromList(columnName, this.columnNames, COLUMN_NAMES_LIST);
		this.columnNamesAdapter.setValue(this.columnNames.toArray(new String[this.columnNames.size()]));
	}

	public void removeColumnName(int index) {
		this.removeItemFromList(index, this.columnNames, COLUMN_NAMES_LIST);
		this.columnNamesAdapter.setValue(this.columnNames.toArray(new String[this.columnNames.size()]));
	}


	// ********** NestableAnnotation implementation **********

	public void initializeFrom(NestableAnnotation oldAnnotation) {
		UniqueConstraintAnnotation oldConstraint = (UniqueConstraintAnnotation) oldAnnotation;
		for (String columnName : CollectionTools.iterable(oldConstraint.columnNames())) {
			this.addColumnName(columnName);
		}
	}

	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}

}
