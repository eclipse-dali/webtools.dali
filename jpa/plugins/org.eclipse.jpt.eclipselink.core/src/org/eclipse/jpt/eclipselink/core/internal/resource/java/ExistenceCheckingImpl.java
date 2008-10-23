/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.AbstractResourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.ExistenceCheckingAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.ExistenceType;


public class ExistenceCheckingImpl extends AbstractResourceAnnotation<Type> implements ExistenceCheckingAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> valueAdapter;
	
	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	
	
	private ExistenceType value;
	
	protected ExistenceCheckingImpl(JavaResourcePersistentType parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(type, VALUE_ADAPTER);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//*************** CacheAnnotation implementation ****************

	public ExistenceType getValue() {
		return this.value;
	}
	
	public void setValue(ExistenceType newValue) {
		if (attributeValueHasNotChanged(this.value, newValue)) {
			return;
		}
		ExistenceType oldValue = this.value;
		this.value = newValue;
		this.valueAdapter.setValue(ExistenceType.toJavaAnnotationValue(newValue));
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}

	
	public void initialize(CompilationUnit astRoot) {
		this.value = this.value(astRoot);
	}
	
	public void update(CompilationUnit astRoot) {
		this.setValue(this.value(astRoot));
	}
	
	protected ExistenceType value(CompilationUnit astRoot) {
		return ExistenceType.fromJavaAnnotationValue(this.valueAdapter.getValue(astRoot));
	}
	
	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_ADAPTER, astRoot);
	}
	
	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.EXISTENCE_CHECKING__VALUE, false);
	}	
	
	public static class ExistenceCheckingAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final ExistenceCheckingAnnotationDefinition INSTANCE = new ExistenceCheckingAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static ExistenceCheckingAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure single instance.
		 */
		private ExistenceCheckingAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new ExistenceCheckingImpl((JavaResourcePersistentType) parent, (Type) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
