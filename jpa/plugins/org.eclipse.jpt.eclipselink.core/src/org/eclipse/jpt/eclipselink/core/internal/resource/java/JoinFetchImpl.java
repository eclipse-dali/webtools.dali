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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType;


public class JoinFetchImpl extends AbstractResourceAnnotation<Attribute> implements JoinFetchAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> valueAdapter;
	
	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	
	
	private JoinFetchType value;
	
	protected JoinFetchImpl(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, VALUE_ADAPTER);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//*************** CacheAnnotation implementation ****************

	public JoinFetchType getValue() {
		return this.value;
	}
	
	public void setValue(JoinFetchType newValue) {
		if (attributeValueHasNotChanged(this.value, newValue)) {
			return;
		}
		JoinFetchType oldValue = this.value;
		this.value = newValue;
		this.valueAdapter.setValue(JoinFetchType.toJavaAnnotationValue(newValue));
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}

	
	public void initialize(CompilationUnit astRoot) {
		this.value = this.value(astRoot);
	}
	
	public void update(CompilationUnit astRoot) {
		this.setValue(this.value(astRoot));
	}
	
	protected JoinFetchType value(CompilationUnit astRoot) {
		return JoinFetchType.fromJavaAnnotationValue(this.valueAdapter.getValue(astRoot));
	}
	
	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_ADAPTER, astRoot);
	}
	
	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.JOIN_FETCH_VALUE, false);
	}	
	
	public static class JoinFetchAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final JoinFetchAnnotationDefinition INSTANCE = new JoinFetchAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static JoinFetchAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private JoinFetchAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new JoinFetchImpl((JavaResourcePersistentAttribute) parent, (Attribute) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new NullJoinFetchAnnotation((JavaResourcePersistentAttribute) parent);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
