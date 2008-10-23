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
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.Type;

public class IdClassImpl extends AbstractResourceAnnotation<Type> implements IdClassAnnotation
{
	private final AnnotationElementAdapter<String> valueAdapter;

	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();

	private String value;

	private String fullyQualifiedValue;

	public IdClassImpl(JavaResourceNode parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(type, VALUE_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.value = this.value(astRoot);
		this.fullyQualifiedValue = fullyQualifiedClass(astRoot);
	}

	public String getAnnotationName() {
		return IdClassAnnotation.ANNOTATION_NAME;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String newValue) {
		if (attributeValueHasNotChanged(this.value, newValue)) {
			return;
		}
		String oldValue = this.value;
		this.value = newValue;
		this.valueAdapter.setValue(newValue);
		firePropertyChanged(IdClassAnnotation.VALUE_PROPERTY, oldValue, newValue);
	}

	public String getFullyQualifiedClass() {
		return this.fullyQualifiedValue;
	}
	
	private void setFullyQualifiedClass(String newQualifiedClass) {
		String oldQualifiedClass = this.fullyQualifiedValue;
		this.fullyQualifiedValue = newQualifiedClass;
		firePropertyChanged(IdClassAnnotation.FULLY_QUALIFIED_CLASS_PROPERTY, oldQualifiedClass, newQualifiedClass);
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_ADAPTER, astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setValue(this.value(astRoot));
		this.setFullyQualifiedClass(this.fullyQualifiedClass(astRoot));
	}

	protected String value(CompilationUnit astRoot) {
		return this.valueAdapter.getValue(astRoot);
	}
	
	private String fullyQualifiedClass(CompilationUnit astRoot) {
		if (getValue() == null) {
			return null;
		}
		return JDTTools.resolveFullyQualifiedName(this.valueAdapter.getExpression(astRoot));
	}
	
	// ********** static methods **********
	protected static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, JPA.ID_CLASS__VALUE, SimpleTypeStringExpressionConverter.instance());
	}

	
	public static class IdClassAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final IdClassAnnotationDefinition INSTANCE = new IdClassAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure single instance.
		 */
		private IdClassAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new IdClassImpl(parent, (Type) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}
		
		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
