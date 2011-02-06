/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NestableUniqueConstraintAnnotation;

/**
 * <code>javax.persistence.UniqueConstraint</code>
 */
public final class SourceUniqueConstraintAnnotation
	extends SourceAnnotation<Member>
	implements NestableUniqueConstraintAnnotation
{
	private DeclarationAnnotationElementAdapter<String[]> columnNamesDeclarationAdapter;
	private AnnotationElementAdapter<String[]> columnNamesAdapter;
	private final Vector<String> columnNames = new Vector<String>();


	public SourceUniqueConstraintAnnotation(JavaResourceNode parent, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, member, idaa, new ElementIndexedAnnotationAdapter(member, idaa));
		this.columnNamesDeclarationAdapter = buildColumnNamesDeclarationAdapter();
		this.columnNamesAdapter = buildColumnNamesAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.initializeColumnNames(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncColumnNames(astRoot);
	}


	// ********** UniqueConstraintAnnotation implementation **********

	// ***** column names
	public ListIterator<String> columnNames() {
		return new CloneListIterator<String>(this.columnNames);
	}

	private Iterable<String> getColumnNames() {
		return new LiveCloneIterable<String>(this.columnNames);
	}

	public int columnNamesSize() {
		return this.columnNames.size();
	}

	public void addColumnName(String columnName) {
		this.addColumnName(this.columnNames.size(), columnName);
	}

	public void addColumnName(int index, String columnName) {
		this.columnNames.add(index, columnName);
		this.writeColumnNames();
	}

	public void moveColumnName(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.columnNames, targetIndex, sourceIndex);
		this.writeColumnNames();
	}

	public void removeColumnName(String columnName) {
		this.columnNames.remove(columnName);
		this.writeColumnNames();
	}

	public void removeColumnName(int index) {
		this.columnNames.remove(index);
		this.writeColumnNames();
	}

	private void writeColumnNames() {
		this.columnNamesAdapter.setValue(this.columnNames.toArray(new String[this.columnNames.size()]));
	}

	private void initializeColumnNames(CompilationUnit astRoot) {
		String[] astColumnNames = this.columnNamesAdapter.getValue(astRoot);
		for (int i = 0; i < astColumnNames.length; i++) {
			this.columnNames.add(astColumnNames[i]);
		}
	}

	private void syncColumnNames(CompilationUnit astRoot) {
		String[] javaColumnNames = this.columnNamesAdapter.getValue(astRoot);
		this.synchronizeList(Arrays.asList(javaColumnNames), this.columnNames, COLUMN_NAMES_LIST);
	}

	public boolean columnNamesTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.columnNamesDeclarationAdapter, pos, astRoot);
	}

	private DeclarationAnnotationElementAdapter<String[]> buildColumnNamesDeclarationAdapter() {
		return buildArrayAnnotationElementAdapter(this.daa, JPA.UNIQUE_CONSTRAINT__COLUMN_NAMES);
	}

	private AnnotationElementAdapter<String[]> buildColumnNamesAdapter() {
		return this.buildAnnotationElementAdapter(this.columnNamesDeclarationAdapter);
	}

	private AnnotationElementAdapter<String[]> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String[]> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String[]>(this.annotatedElement, daea);
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return buildArrayAnnotationElementAdapter(annotationAdapter, elementName, AnnotationStringArrayExpressionConverter.forStrings());
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String[]> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String[]>(annotationAdapter, elementName, converter);
	}


	// ********** NestableAnnotation implementation **********

	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				this.columnNames.isEmpty();
	}

	@Override
	protected void rebuildAdapters() {
		super.rebuildAdapters();
		this.columnNamesDeclarationAdapter = buildColumnNamesDeclarationAdapter();
		this.columnNamesAdapter = buildColumnNamesAdapter();
	}

	@Override
	public void storeOn(Map<String, Object> map) {
		super.storeOn(map);

		List<String> columnNamesState = new ArrayList<String>(this.columnNames.size());
		for (String columnName : this.getColumnNames()) {
			columnNamesState.add(columnName);
		}
		map.put(COLUMN_NAMES_LIST, columnNamesState);
		this.columnNames.clear();
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);

		@SuppressWarnings("unchecked")
		List<String> columnNamesState = (List<String>) map.get(COLUMN_NAMES_LIST);
		for (String columnName : columnNamesState) {
			this.addColumnName(columnName);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.columnNames);
	}
}
