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
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class EnumeratedImpl extends AbstractAnnotationResource<Attribute> implements Enumerated
{
	private static final String ANNOTATION_NAME = JPA.ENUMERATED;

	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();

	private final AnnotationElementAdapter<String> valueAdapter;

	private EnumType value;
	
	protected EnumeratedImpl(JavaResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	public EnumType getValue() {
		return this.value;
	}
	
	public void setValue(EnumType value) {
		this.value = value;
		this.valueAdapter.setValue(EnumType.toJavaAnnotationValue(value));
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		setValue(EnumType.fromJavaAnnotationValue(valueAdapter.getValue(astRoot)));
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ENUMERATED__VALUE);
	}
	
	public static class EnumeratedAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final EnumeratedAnnotationDefinition INSTANCE = new EnumeratedAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private EnumeratedAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new EnumeratedImpl(parent, (Attribute) member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
