/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source;

import java.util.Arrays;
import java.util.Vector;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.GeneratedAnnotation;

/**
 * javax.annotation.Generated
 */
public final class SourceGeneratedAnnotation
	extends SourceAnnotation
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


	public SourceGeneratedAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = this.buildAnnotationElementAdapter(VALUE_ADAPTER);
		this.dateAdapter = this.buildAdapter(DATE_ADAPTER);
		this.commentsAdapter = this.buildAdapter(COMMENTS_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	private AnnotationElementAdapter<String[]> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String[]> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String[]>(this.annotatedElement, daea);
	}

	private AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.initializeValues(astAnnotation);
		this.date = this.buildDate(astAnnotation);
		this.comments = this.buildComments(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncValues(astAnnotation);
		this.syncDate(this.buildDate(astAnnotation));
		this.syncComments(this.buildComments(astAnnotation));
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				this.values.isEmpty() &&
				(this.date == null) &&
				(this.comments == null);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.values);
	}


	// ********** GeneratedAnnotation implementation **********

	// ***** values
	public ListIterable<String> getValues() {
		return new LiveCloneListIterable<String>(this.values);
	}

	public int getValuesSize() {
		return this.values.size();
	}

	public String getValue(int index) {
		return this.values.get(index);
	}

	public void addValue(String value) {
		this.addValue(this.values.size(), value);
	}

	public void addValue(int index, String value) {
		this.values.add(index, value);
		this.writeValues();
	}

	public void moveValue(int targetIndex, int sourceIndex) {
		ListTools.move(this.values, targetIndex, sourceIndex);
		this.writeValues();
	}

	public void removeValue(String value) {
		this.values.remove(value);
		this.writeValues();
	}

	public void removeValue(int index) {
		this.values.remove(index);
		this.writeValues();
	}

	private void writeValues() {
		this.valueAdapter.setValue(this.values.toArray(new String[this.values.size()]));
	}

	private void initializeValues(Annotation astAnnotation) {
		String[] astValues = this.valueAdapter.getValue(astAnnotation);
		for (int i = 0; i < astValues.length; i++) {
			this.values.add(astValues[i]);
		}
	}

	private void syncValues(Annotation astAnnotation) {
		String[] astValues = this.valueAdapter.getValue(astAnnotation);
		this.synchronizeList(Arrays.asList(astValues), this.values, VALUES_LIST);
	}

	// ***** date
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		if (this.attributeValueHasChanged(this.date, date)) {
			this.date = date;
			this.dateAdapter.setValue(date);
		}
	}
	
	protected void syncDate(String astDate) {
		String old = this.date;
		this.date = astDate;
		this.firePropertyChanged(DATE_PROPERTY, old, astDate);
	}

	private String buildDate(Annotation astAnnotation) {
		return this.dateAdapter.getValue(astAnnotation);
	}

	// ***** comments
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		if (this.attributeValueHasChanged(this.comments, comments)) {
			this.comments = comments;
			this.commentsAdapter.setValue(comments);
		}
	}

	protected void syncComments(String astComments) {
		String old = this.comments;
		this.comments = astComments;
		this.firePropertyChanged(COMMENTS_PROPERTY, old, astComments);
	}

	private String buildComments(Annotation astAnnotation) {
		return this.commentsAdapter.getValue(astAnnotation);
	}


	// ********** static methods **********

	protected static DeclarationAnnotationElementAdapter<String[]> buildValueAdapter() {
		return buildArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, VALUE_ELEMENT_NAME);
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return buildArrayAnnotationElementAdapter(annotationAdapter, elementName, AnnotationStringArrayExpressionConverter.forStrings());
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String[]> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String[]>(annotationAdapter, elementName, converter);
	}

	protected static DeclarationAnnotationElementAdapter<String> buildAdapter(String elementName) {
		return buildAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	static DeclarationAnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(annotationAdapter, elementName);
	}

}
