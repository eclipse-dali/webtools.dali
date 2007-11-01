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

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.BooleanStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


public class BasicImpl extends AbstractAnnotationResource<Attribute> implements Basic
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> optionalAdapter;

	private final AnnotationElementAdapter<String> fetchAdapter;

	private static final DeclarationAnnotationElementAdapter<String> OPTIONAL_ADAPTER = buildOptionalAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();
	
	private Boolean optional;
	
	private FetchType fetch;
	
	protected BasicImpl(JavaPersistentAttributeResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.optionalAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, OPTIONAL_ADAPTER);
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
		Boolean oldOptional = this.optional;
		this.optional = newOptional;
		this.optionalAdapter.setValue(BooleanUtility.toJavaAnnotationValue(newOptional));
		firePropertyChanged(OPTIONAL_PROPERTY, oldOptional, newOptional);
	}

	public FetchType getFetch() {
		return this.fetch;
	}
	
	public void setFetch(FetchType newFetch) {
		FetchType oldFetch = this.fetch;
		this.fetch = newFetch;
		this.fetchAdapter.setValue(FetchType.toJavaAnnotationValue(newFetch));
		firePropertyChanged(FETCH_PROPERTY, oldFetch, newFetch);
	}
	
	public ITextRange fetchTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(FETCH_ADAPTER, astRoot);
	}
	
	public ITextRange optionalTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(OPTIONAL_ADAPTER, astRoot);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		this.setOptional(this.optional(astRoot));
		this.setFetch(this.fetch(astRoot));
	}
	
	protected FetchType fetch(CompilationUnit astRoot) {
		return FetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astRoot));
	}
	
	protected Boolean optional(CompilationUnit astRoot) {
		return BooleanUtility.fromJavaAnnotationValue(this.optionalAdapter.getValue(astRoot));
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildOptionalAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, JPA.BASIC__OPTIONAL, false, BooleanStringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.BASIC__FETCH, false);
	}
	
	public static class BasicAnnotationDefinition implements MappingAnnotationDefinition
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

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new BasicImpl((JavaPersistentAttributeResource) parent, (Attribute) member);
		}

		public Iterator<String> correspondingAnnotationNames() {
			return new ArrayIterator<String>(
				JPA.COLUMN,
				JPA.LOB,
				JPA.TEMPORAL,
				JPA.ENUMERATED);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
