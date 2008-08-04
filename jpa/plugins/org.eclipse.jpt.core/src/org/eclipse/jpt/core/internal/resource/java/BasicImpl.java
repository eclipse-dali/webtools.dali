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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;


public class BasicImpl extends AbstractResourceAnnotation<Attribute> implements BasicAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<Boolean> optionalAdapter;

	private final AnnotationElementAdapter<String> fetchAdapter;

	private static final DeclarationAnnotationElementAdapter<Boolean> OPTIONAL_ADAPTER = buildOptionalAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();
	
	private Boolean optional;
	
	private FetchType fetch;
	
	protected BasicImpl(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.optionalAdapter = new ShortCircuitAnnotationElementAdapter<Boolean>(attribute, OPTIONAL_ADAPTER);
		this.fetchAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, FETCH_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.optional = this.optional(astRoot);
		this.fetch = this.fetch(astRoot);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//*************** Basic implementation ****************
	public Boolean getOptional() {
		return this.optional;
	}
	
	public void setOptional(Boolean newOptional) {
		if (attributeValueHasNotChanged(this.optional, newOptional)) {
			return;
		}
		Boolean oldOptional = this.optional;
		this.optional = newOptional;
		this.optionalAdapter.setValue(newOptional);
		firePropertyChanged(OPTIONAL_PROPERTY, oldOptional, newOptional);
	}

	public FetchType getFetch() {
		return this.fetch;
	}
	
	public void setFetch(FetchType newFetch) {
		if (attributeValueHasNotChanged(this.fetch, newFetch)) {
			return;
		}
		FetchType oldFetch = this.fetch;
		this.fetch = newFetch;
		this.fetchAdapter.setValue(FetchType.toJavaAnnotationValue(newFetch));
		firePropertyChanged(FETCH_PROPERTY, oldFetch, newFetch);
	}
	
	public TextRange getFetchTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(FETCH_ADAPTER, astRoot);
	}
	
	public TextRange getOptionalTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(OPTIONAL_ADAPTER, astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setOptional(this.optional(astRoot));
		this.setFetch(this.fetch(astRoot));
	}
	
	protected FetchType fetch(CompilationUnit astRoot) {
		return FetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astRoot));
	}
	
	protected Boolean optional(CompilationUnit astRoot) {
		return this.optionalAdapter.getValue(astRoot);
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<Boolean> buildOptionalAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, JPA.BASIC__OPTIONAL, false, BooleanExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.BASIC__FETCH, false);
	}
	
	public static class BasicAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final BasicAnnotationDefinition INSTANCE = new BasicAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static BasicAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private BasicAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new BasicImpl((JavaResourcePersistentAttribute) parent, (Attribute) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new NullBasic(parent);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
