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
import org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;


public class ChangeTrackingImpl extends AbstractResourceAnnotation<Type> implements ChangeTrackingAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> valueAdapter;
	
	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	
	
	private ChangeTrackingType value;
	
	protected ChangeTrackingImpl(JavaResourcePersistentType parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(type, VALUE_ADAPTER);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//*************** CacheAnnotation implementation ****************

	public ChangeTrackingType getValue() {
		return this.value;
	}
	
	public void setValue(ChangeTrackingType newValue) {
		if (attributeValueHasNotChanged(this.value, newValue)) {
			return;
		}
		ChangeTrackingType oldValue = this.value;
		this.value = newValue;
		this.valueAdapter.setValue(ChangeTrackingType.toJavaAnnotationValue(newValue));
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}

	
	public void initialize(CompilationUnit astRoot) {
		this.value = this.value(astRoot);
	}
	
	public void update(CompilationUnit astRoot) {
		this.setValue(this.value(astRoot));
	}
	
	protected ChangeTrackingType value(CompilationUnit astRoot) {
		return ChangeTrackingType.fromJavaAnnotationValue(this.valueAdapter.getValue(astRoot));
	}
	
	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_ADAPTER, astRoot);
	}
	
	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CHANGE_TRACKING__VALUE, false);
	}	
	
	public static class ChangeTrackingAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final ChangeTrackingAnnotationDefinition INSTANCE = new ChangeTrackingAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static ChangeTrackingAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure single instance.
		 */
		private ChangeTrackingAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new ChangeTrackingImpl((JavaResourcePersistentType) parent, (Type) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
