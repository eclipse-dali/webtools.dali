/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AccessAnnotation;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;


public class AccessImpl extends AbstractResourceAnnotation<Member> implements AccessAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> valueAdapter;

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	
	
	private AccessType value;
	
	protected AccessImpl(JavaResourcePersistentMember parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, VALUE_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.value = this.value(astRoot);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//*************** AccessAnnotation implementation ****************
	public AccessType getValue() {
		return this.value;
	}
	
	public void setValue(AccessType newValue) {
		if (attributeValueHasNotChanged(this.value, newValue)) {
			return;
		}
		AccessType oldValue = this.value;
		this.value = newValue;
		this.valueAdapter.setValue(AccessType.toJavaAnnotationValue(newValue));
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
		
	}
	
	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_ADAPTER, astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setValue(this.value(astRoot));
	}
	
	protected AccessType value(CompilationUnit astRoot) {
		return AccessType.fromJavaAnnotationValue(this.valueAdapter.getValue(astRoot));
	}
	
	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ACCESS__VALUE);
	}
	
	public static class AccessAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final AccessAnnotationDefinition INSTANCE = new AccessAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AccessAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure single instance.
		 */
		private AccessAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new AccessImpl(parent, member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new NullAccessAnnotation(parent);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
