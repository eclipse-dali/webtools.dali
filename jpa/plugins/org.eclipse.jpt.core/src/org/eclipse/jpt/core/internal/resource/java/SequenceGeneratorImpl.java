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
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

public class SequenceGeneratorImpl
	extends GeneratorImpl
	implements SequenceGeneratorAnnotation
{
	private final AnnotationElementAdapter<String> sequenceNameAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__NAME);

	private static final DeclarationAnnotationElementAdapter<Integer> INITIAL_VALUE_ADAPTER = buildIntegerAdapter(JPA.SEQUENCE_GENERATOR__INITIAL_VALUE);

	private static final DeclarationAnnotationElementAdapter<Integer> ALLOCATION_SIZE_ADAPTER = buildIntegerAdapter(JPA.SEQUENCE_GENERATOR__ALLOCATION_SIZE);

	private static final DeclarationAnnotationElementAdapter<String> SEQUENCE_NAME_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__SEQUENCE_NAME);
	
	private String sequenceName;

	protected SequenceGeneratorImpl(JavaResourceNode parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.sequenceNameAdapter = this.buildAdapter(SEQUENCE_NAME_ADAPTER);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.sequenceName = this.sequenceName(astRoot);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	//************ GeneratorImpl implementation **************

	@Override
	protected DeclarationAnnotationElementAdapter<Integer> getAllocationSizeAdapter() {
		return ALLOCATION_SIZE_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<Integer> getInitialValueAdapter() {
		return INITIAL_VALUE_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> getNameAdapter() {
		return NAME_ADAPTER;
	}


	public String getSequenceName() {
		return this.sequenceName;
	}

	public void setSequenceName(String newSequenceName) {
		if (attributeValueHasNotChanged(this.sequenceName, newSequenceName)) {
			return;
		}
		String oldSequenceName = this.sequenceName;
		this.sequenceName = newSequenceName;
		this.sequenceNameAdapter.setValue(newSequenceName);
		firePropertyChanged(SEQUENCE_NAME_PROPERTY, oldSequenceName, newSequenceName);
	}
	
	public TextRange getSequenceNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SEQUENCE_NAME_ADAPTER, astRoot);
	}

	public boolean sequenceNameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(SEQUENCE_NAME_ADAPTER, pos, astRoot);
	}

	// ********** java annotations -> persistence model **********
	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setSequenceName(this.sequenceName(astRoot));
	}
	
	protected String sequenceName(CompilationUnit astRoot) {
		return this.sequenceNameAdapter.getValue(astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildAdapter(String elementName) {
		return buildAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildIntegerAdapter(String elementName) {
		return buildIntegerAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}
	

	// ********** annotation definition **********

	public static class SequenceGeneratorAnnotationDefinition
		implements AnnotationDefinition
	{
		// singleton
		private static final SequenceGeneratorAnnotationDefinition INSTANCE = new SequenceGeneratorAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure single instance.
		 */
		private SequenceGeneratorAnnotationDefinition() {
			super();
		}
		
		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new SequenceGeneratorImpl(parent, member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
