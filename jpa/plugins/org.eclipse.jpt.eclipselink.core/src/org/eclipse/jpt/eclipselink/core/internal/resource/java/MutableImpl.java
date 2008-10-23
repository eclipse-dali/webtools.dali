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
import org.eclipse.jpt.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
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
import org.eclipse.jpt.eclipselink.core.resource.java.MutableAnnotation;


public class MutableImpl extends AbstractResourceAnnotation<Attribute> implements MutableAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<Boolean> valueAdapter;

	private static final DeclarationAnnotationElementAdapter<Boolean> VALUE_ADAPTER = buildValueAdapter();

	
	private Boolean value;
	
	protected MutableImpl(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<Boolean>(attribute, VALUE_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.value = this.value(astRoot);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//*************** Mutable implementation ****************
	public Boolean getValue() {
		return this.value;
	}
	
	public void setValue(Boolean newValue) {
		if (attributeValueHasNotChanged(this.value, newValue)) {
			return;
		}
		Boolean oldValue = this.value;
		this.value = newValue;
		this.valueAdapter.setValue(newValue);
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_ADAPTER, astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setValue(this.value(astRoot));
	}
	
	protected Boolean value(CompilationUnit astRoot) {
		return this.valueAdapter.getValue(astRoot);
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<Boolean> buildValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.MUTABLE__VALUE, false, BooleanExpressionConverter.instance());
	}

	
	public static class MutableAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final MutableAnnotationDefinition INSTANCE = new MutableAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static MutableAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure single instance.
		 */
		private MutableAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new MutableImpl((JavaResourcePersistentAttribute) parent, (Attribute) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
