/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;

/**
 * javax.persistence.Basic
 */
public final class SourceBasicAnnotation
	extends SourceAnnotation<Attribute>
	implements BasicAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<Boolean> OPTIONAL_ADAPTER = buildOptionalAdapter();
	private final AnnotationElementAdapter<Boolean> optionalAdapter;
	private Boolean optional;

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();
	private final AnnotationElementAdapter<String> fetchAdapter;
	private FetchType fetch;


	public SourceBasicAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.optionalAdapter = new MemberAnnotationElementAdapter<Boolean>(attribute, OPTIONAL_ADAPTER);
		this.fetchAdapter = new MemberAnnotationElementAdapter<String>(attribute, FETCH_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.optional = this.buildOptional(astRoot);
		this.fetch = this.buildFetch(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncOptional(this.buildOptional(astRoot));
		this.syncFetch(this.buildFetch(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.fetch);
	}


	//*************** Basic implementation ****************

	// ***** optional
	public Boolean getOptional() {
		return this.optional;
	}

	public void setOptional(Boolean optional) {
		if (this.attributeValueHasChanged(this.optional, optional)) {
			this.optional = optional;
			this.optionalAdapter.setValue(optional);
		}
	}

	private void syncOptional(Boolean astOptional) {
		Boolean old = this.optional;
		this.optional = astOptional;
		this.firePropertyChanged(OPTIONAL_PROPERTY, old, astOptional);
	}

	private Boolean buildOptional(CompilationUnit astRoot) {
		return this.optionalAdapter.getValue(astRoot);
	}

	public TextRange getOptionalTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(OPTIONAL_ADAPTER, astRoot);
	}

	// ***** fetch
	public FetchType getFetch() {
		return this.fetch;
	}

	public void setFetch(FetchType fetch) {
		if (this.attributeValueHasChanged(this.fetch, fetch)) {
			this.fetch = fetch;
			this.fetchAdapter.setValue(FetchType.toJavaAnnotationValue(fetch));
		}
	}

	private void syncFetch(FetchType astFetch) {
		FetchType old = this.fetch;
		this.fetch = astFetch;
		this.firePropertyChanged(FETCH_PROPERTY, old, astFetch);
	}

	private FetchType buildFetch(CompilationUnit astRoot) {
		return FetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astRoot));
	}

	public TextRange getFetchTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(FETCH_ADAPTER, astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<Boolean> buildOptionalAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, JPA.BASIC__OPTIONAL, false, BooleanExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.BASIC__FETCH, false);
	}

}
