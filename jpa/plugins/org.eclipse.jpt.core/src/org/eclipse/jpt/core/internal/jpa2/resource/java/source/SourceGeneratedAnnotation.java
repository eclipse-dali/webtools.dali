/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitArrayAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.jpa2.resource.java.GeneratedAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.annotation.Generated
 */
public final class SourceGeneratedAnnotation
	extends SourceAnnotation<Type>
	implements GeneratedAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String[]> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String[]> valueAdapter;
	private final Vector<String> values = new Vector<String>();

	private static final DeclarationAnnotationElementAdapter<String> DATE_ADAPTER = buildAdapter(DATE_ELEMENT_NAME);
	private final AnnotationElementAdapter<String> dateAdapter;
	private String date;

	private static final DeclarationAnnotationElementAdapter<String> COMMENTS_ADAPTER = buildAdapter(COMMENTS_ELEMENT_NAME);
	private final AnnotationElementAdapter<String> commentsAdapter;
	private String comments;


	public SourceGeneratedAnnotation(JavaResourcePersistentType parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = this.buildAnnotationElementAdapter(VALUE_ADAPTER);
		this.dateAdapter = this.buildAdapter(DATE_ADAPTER);
		this.commentsAdapter = this.buildAdapter(COMMENTS_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	private AnnotationElementAdapter<String[]> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String[]> daea) {
		return new ShortCircuitArrayAnnotationElementAdapter<String>(this.member, daea);
	}

	private AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(this.member, daea);
	}

	public void initialize(CompilationUnit astRoot) {
		this.initializeValues(astRoot);
		this.date = this.buildDate(astRoot);
		this.comments = this.buildComments(astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.updateValues(astRoot);
		this.setDate_(this.buildDate(astRoot));
		this.setComments_(this.buildComments(astRoot));
	}

	public IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.values);
	}


	// ********** GeneratedAnnotation implementation **********

	// ***** values
	public ListIterator<String> values() {
		return new CloneListIterator<String>(this.values);
	}

	public int valuesSize() {
		return this.values.size();
	}

	public String getValue(int index) {
		return this.values.get(index);
	}

	public void addValue(String value) {
		this.addValue(this.values.size(), value);
	}

	public void addValue(int index, String value) {
		this.addValue_(index, value);
		this.valueAdapter.setValue(this.values.toArray(new String[this.values.size()]));
	}

	private void addValue_(int index, String value) {
		this.addItemToList(index, value, this.values, VALUES_LIST);
	}

	public void moveValue(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.values, VALUES_LIST);
		this.valueAdapter.setValue(this.values.toArray(new String[this.values.size()]));
	}

	public void removeValue(String value) {
		this.removeItemFromList(value, this.values, VALUES_LIST);
		this.valueAdapter.setValue(this.values.toArray(new String[this.values.size()]));
	}

	public void removeValue(int index) {
		this.removeItemFromList(index, this.values, VALUES_LIST);
		this.valueAdapter.setValue(this.values.toArray(new String[this.values.size()]));
	}

	private void initializeValues(CompilationUnit astRoot) {
		String[] javaValues = this.valueAdapter.getValue(astRoot);
		for (int i = 0; i < javaValues.length; i++) {
			this.values.add(javaValues[i]);
		}
	}

	private void updateValues(CompilationUnit astRoot) {
		String[] javaValues = this.valueAdapter.getValue(astRoot);
		this.synchronizeList(Arrays.asList(javaValues), this.values, VALUES_LIST);
	}

	// ***** date
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		if (this.attributeValueHasNotChanged(this.date, date)) {
			return;
		}
		String old = this.date;
		this.date = date;
		this.dateAdapter.setValue(date);
		this.firePropertyChanged(DATE_PROPERTY, old, date);
	}
	
	protected void setDate_(String date) {
		String old = this.date;
		this.date = date;
		this.firePropertyChanged(DATE_PROPERTY, old, date);
	}

	private String buildDate(CompilationUnit astRoot) {
		return this.dateAdapter.getValue(astRoot);
	}

	// ***** comments
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		if (this.attributeValueHasNotChanged(this.comments, comments)) {
			return;
		}
		String old = this.date;
		this.comments = comments;
		this.commentsAdapter.setValue(comments);
		this.firePropertyChanged(COMMENTS_PROPERTY, old, comments);
	}

	protected void setComments_(String comments) {
		String old = this.date;
		this.comments = comments;
		this.firePropertyChanged(COMMENTS_PROPERTY, old, comments);
	}

	private String buildComments(CompilationUnit astRoot) {
		return this.commentsAdapter.getValue(astRoot);
	}


	// ********** static methods **********

	protected static DeclarationAnnotationElementAdapter<String[]> buildValueAdapter() {
		return buildArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, VALUE_ELEMENT_NAME);
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return buildArrayAnnotationElementAdapter(annotationAdapter, elementName, AnnotationStringArrayExpressionConverter.forStrings());
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String[]> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String[]>(annotationAdapter, elementName, false, converter);
	}

	protected static DeclarationAnnotationElementAdapter<String> buildAdapter(String elementName) {
		return buildAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	static DeclarationAnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(annotationAdapter, elementName);
	}

}
