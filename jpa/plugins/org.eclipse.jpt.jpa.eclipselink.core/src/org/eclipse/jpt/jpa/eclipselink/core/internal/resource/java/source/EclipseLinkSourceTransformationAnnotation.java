/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.resource.java.FetchType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TransformationAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.Transformation</code>
 */
public final class EclipseLinkSourceTransformationAnnotation
	extends SourceAnnotation
	implements TransformationAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();
	private final AnnotationElementAdapter<String> fetchAdapter;
	private FetchType fetch;
	private TextRange fetchTextRange;

	private static final DeclarationAnnotationElementAdapter<Boolean> OPTIONAL_ADAPTER = buildOptionalAdapter();
	private final AnnotationElementAdapter<Boolean> optionalAdapter;
	private Boolean optional;
	private TextRange optionalTextRange;


	public EclipseLinkSourceTransformationAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.fetchAdapter = new AnnotatedElementAnnotationElementAdapter<String>(element, FETCH_ADAPTER);
		this.optionalAdapter = new AnnotatedElementAnnotationElementAdapter<Boolean>(element, OPTIONAL_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.fetch = this.buildFetch(astAnnotation);
		this.fetchTextRange = this.buildFetchTextRange(astAnnotation);

		this.optional = this.buildOptional(astAnnotation);
		this.optionalTextRange = this.buildOptionalTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncFetch(this.buildFetch(astAnnotation));
		this.fetchTextRange = this.buildFetchTextRange(astAnnotation);

		this.syncOptional(this.buildOptional(astAnnotation));
		this.optionalTextRange = this.buildOptionalTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.fetch == null) &&
				(this.optional == null);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.fetch);
	}


	// ********** TransformationAnnotation implementation **********

	// ***** fetch
	public FetchType getFetch() {
		return this.fetch;
	}

	public void setFetch(FetchType fetch) {
		if (ObjectTools.notEquals(this.fetch, fetch)) {
			this.fetch = fetch;
			this.fetchAdapter.setValue(FetchType.toJavaAnnotationValue(fetch));
		}
	}

	private void syncFetch(FetchType astFetch) {
		FetchType old = this.fetch;
		this.fetch = astFetch;
		this.firePropertyChanged(FETCH_PROPERTY, old, astFetch);
	}

	private FetchType buildFetch(Annotation astAnnotation) {
		return FetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astAnnotation));
	}

	public TextRange getFetchTextRange() {
		return this.fetchTextRange;
	}

	private TextRange buildFetchTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(FETCH_ADAPTER, astAnnotation);
	}

	// ***** optional
	public Boolean getOptional() {
		return this.optional;
	}

	public void setOptional(Boolean optional) {
		if (ObjectTools.notEquals(this.optional, optional)) {
			this.optional = optional;
			this.optionalAdapter.setValue(optional);
		}
	}

	private void syncOptional(Boolean astOptional) {
		Boolean old = this.optional;
		this.optional = astOptional;
		this.firePropertyChanged(OPTIONAL_PROPERTY, old, astOptional);
	}

	private Boolean buildOptional(Annotation astAnnotation) {
		return this.optionalAdapter.getValue(astAnnotation);
	}

	public TextRange getOptionalTextRange() {
		return this.optionalTextRange;
	}

	private TextRange buildOptionalTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(OPTIONAL_ADAPTER, astAnnotation);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<Boolean> buildOptionalAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.TRANSFORMATION__OPTIONAL, BooleanExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.TRANSFORMATION__FETCH);
	}
}
