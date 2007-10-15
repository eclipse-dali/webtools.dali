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
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class SequenceGeneratorImpl extends GeneratorImpl implements SequenceGenerator
{
	private final AnnotationElementAdapter<String> sequenceNameAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.SEQUENCE_GENERATOR);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__NAME);

	private static final DeclarationAnnotationElementAdapter<String> INITIAL_VALUE_ADAPTER = buildNumberAdapter(JPA.SEQUENCE_GENERATOR__INITIAL_VALUE);

	private static final DeclarationAnnotationElementAdapter<String> ALLOCATION_SIZE_ADAPTER = buildNumberAdapter(JPA.SEQUENCE_GENERATOR__ALLOCATION_SIZE);

	private static final DeclarationAnnotationElementAdapter<String> SEQUENCE_NAME_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__SEQUENCE_NAME);
	
	private String sequenceName;

	public SequenceGeneratorImpl(JavaResource parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.sequenceNameAdapter = this.buildAdapter(SEQUENCE_NAME_ADAPTER);
	}
	
	public String getAnnotationName() {
		return JPA.SEQUENCE_GENERATOR;
	}

	//************ GeneratorImpl implementation **************

	@Override
	protected DeclarationAnnotationElementAdapter<String> allocationSizeAdapter() {
		return ALLOCATION_SIZE_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationAdapter annotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> initialValueAdapter() {
		return INITIAL_VALUE_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> nameAdapter() {
		return NAME_ADAPTER;
	}


	public String getSequenceName() {
		return this.sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
		this.sequenceNameAdapter.setValue(sequenceName);
	}
	
	// ********** java annotations -> persistence model **********
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		setSequenceName(this.sequenceNameAdapter.getValue(astRoot));
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildAdapter(String elementName) {
		return buildAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	private static DeclarationAnnotationElementAdapter<String> buildNumberAdapter(String elementName) {
		return buildNumberAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}
}
