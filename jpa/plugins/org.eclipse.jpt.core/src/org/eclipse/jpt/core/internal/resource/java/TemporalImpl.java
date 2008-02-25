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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.Temporal;
import org.eclipse.jpt.core.resource.java.TemporalType;

public class TemporalImpl extends AbstractAnnotationResource<Attribute> implements Temporal
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();

	private final AnnotationElementAdapter<String> valueAdapter;

	private TemporalType value;
	
	protected TemporalImpl(JavaResourceNode parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, VALUE_ADAPTER);
	}

	public void initialize(CompilationUnit astRoot) {
		this.value = this.value(astRoot);		
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	public TemporalType getValue() {
		return this.value;
	}
	
	public void setValue(TemporalType newValue) {
		TemporalType oldValue = this.value;
		this.value = newValue;
		this.valueAdapter.setValue(TemporalType.toJavaAnnotationValue(newValue));
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	public TextRange valueTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(VALUE_ADAPTER, astRoot);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		this.setValue(this.value(astRoot));
	}
	
	protected TemporalType value(CompilationUnit astRoot) {
		return TemporalType.fromJavaAnnotationValue(this.valueAdapter.getValue(astRoot));
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.TEMPORAL__VALUE);
	}
	
	public static class TemporalAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final TemporalAnnotationDefinition INSTANCE = new TemporalAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private TemporalAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new TemporalImpl(parent, (Attribute) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new NullTemporal(parent);
		}
		
		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
