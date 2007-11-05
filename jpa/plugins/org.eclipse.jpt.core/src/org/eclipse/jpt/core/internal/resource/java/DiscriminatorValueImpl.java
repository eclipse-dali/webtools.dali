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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class DiscriminatorValueImpl extends AbstractAnnotationResource<Type> implements DiscriminatorValue
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();

	
	private final AnnotationElementAdapter<String> valueAdapter;

	private String value;
	
	protected DiscriminatorValueImpl(JavaResource parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(type, VALUE_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.value = this.value(astRoot);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		this.valueAdapter.setValue(newValue);
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	public ITextRange valueTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(VALUE_ADAPTER, astRoot);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		this.setValue(this.value(astRoot));
	}
	
	protected String value(CompilationUnit astRoot) {
		return this.valueAdapter.getValue(astRoot);
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.DISCRIMINATOR_VALUE__VALUE);
	}


	public static class DiscriminatorValueAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final DiscriminatorValueAnnotationDefinition INSTANCE = new DiscriminatorValueAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private DiscriminatorValueAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new DiscriminatorValueImpl(parent, (Type) member);
		}
		
		public Annotation buildNullAnnotation(JavaResource parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
