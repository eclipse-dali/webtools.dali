/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;

/**
 * javax.persistence.SequenceGenerator
 */
public abstract class SourceSequenceGeneratorAnnotation
	extends SourceGeneratorAnnotation
	implements SequenceGeneratorAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__NAME);

	private static final DeclarationAnnotationElementAdapter<Integer> INITIAL_VALUE_ADAPTER = buildIntegerAdapter(JPA.SEQUENCE_GENERATOR__INITIAL_VALUE);

	private static final DeclarationAnnotationElementAdapter<Integer> ALLOCATION_SIZE_ADAPTER = buildIntegerAdapter(JPA.SEQUENCE_GENERATOR__ALLOCATION_SIZE);

	private static final DeclarationAnnotationElementAdapter<String> SEQUENCE_NAME_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__SEQUENCE_NAME);
	private final AnnotationElementAdapter<String> sequenceNameAdapter;
	private String sequenceName;


	protected SourceSequenceGeneratorAnnotation(JavaResourceNode parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.sequenceNameAdapter = this.buildAdapter(SEQUENCE_NAME_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.sequenceName = this.buildSequenceName(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncSequenceName(this.buildSequenceName(astRoot));
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.sequenceName == null);
	}


	// ********** AbstractGeneratorAnnotation implementation **********

	@Override
	DeclarationAnnotationElementAdapter<String> getNameAdapter() {
		return NAME_ADAPTER;
	}

	@Override
	DeclarationAnnotationElementAdapter<Integer> getInitialValueAdapter() {
		return INITIAL_VALUE_ADAPTER;
	}

	@Override
	DeclarationAnnotationElementAdapter<Integer> getAllocationSizeAdapter() {
		return ALLOCATION_SIZE_ADAPTER;
	}


	// ********** SequenceGeneratorAnnotation implementation **********

	// ***** sequence name
	public String getSequenceName() {
		return this.sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		if (this.attributeValueHasChanged(this.sequenceName, sequenceName)) {
			this.sequenceName = sequenceName;
			this.sequenceNameAdapter.setValue(sequenceName);
		}
	}

	public void syncSequenceName(String astSequenceName) {
		String old = this.sequenceName;
		this.sequenceName = astSequenceName;
		this.firePropertyChanged(SEQUENCE_NAME_PROPERTY, old, astSequenceName);
	}

	private String buildSequenceName(CompilationUnit astRoot) {
		return this.sequenceNameAdapter.getValue(astRoot);
	}

	public TextRange getSequenceNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SEQUENCE_NAME_ADAPTER, astRoot);
	}

	public boolean sequenceNameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(SEQUENCE_NAME_ADAPTER, pos, astRoot);
	}


	// ********** static methods **********

	protected static DeclarationAnnotationElementAdapter<String> buildAdapter(String elementName) {
		return buildAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	protected static DeclarationAnnotationElementAdapter<Integer> buildIntegerAdapter(String elementName) {
		return buildIntegerAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

}
