/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;

/**
 * <code>javax.persistence.SequenceGenerator</code>
 */
public abstract class SourceSequenceGeneratorAnnotation
	extends SourceDatabaseGeneratorAnnotation
	implements SequenceGeneratorAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__NAME);

	private static final DeclarationAnnotationElementAdapter<Integer> INITIAL_VALUE_ADAPTER = buildIntegerAdapter(JPA.SEQUENCE_GENERATOR__INITIAL_VALUE);

	private static final DeclarationAnnotationElementAdapter<Integer> ALLOCATION_SIZE_ADAPTER = buildIntegerAdapter(JPA.SEQUENCE_GENERATOR__ALLOCATION_SIZE);

	private static final DeclarationAnnotationElementAdapter<String> SEQUENCE_NAME_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__SEQUENCE_NAME);
	private final AnnotationElementAdapter<String> sequenceNameAdapter;
	private String sequenceName;
	private TextRange sequenceNameTextRange;


	protected SourceSequenceGeneratorAnnotation(JavaResourceModel parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.sequenceNameAdapter = this.buildAdapter(SEQUENCE_NAME_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.sequenceName = this.buildSequenceName(astAnnotation);
		this.sequenceNameTextRange = this.buildSequenceNameTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncSequenceName(this.buildSequenceName(astAnnotation));
		this.sequenceNameTextRange = this.buildSequenceNameTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.sequenceName == null);
	}


	// ********** AbstractGeneratorAnnotation implementation **********

	@Override
	protected DeclarationAnnotationElementAdapter<String> getNameAdapter() {
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
		if (ObjectTools.notEquals(this.sequenceName, sequenceName)) {
			this.sequenceName = sequenceName;
			this.sequenceNameAdapter.setValue(sequenceName);
		}
	}

	public void syncSequenceName(String astSequenceName) {
		String old = this.sequenceName;
		this.sequenceName = astSequenceName;
		this.firePropertyChanged(SEQUENCE_NAME_PROPERTY, old, astSequenceName);
	}

	private String buildSequenceName(Annotation astAnnotation) {
		return this.sequenceNameAdapter.getValue(astAnnotation);
	}

	public TextRange getSequenceNameTextRange() {
		return this.sequenceNameTextRange;
	}

	private TextRange buildSequenceNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(SEQUENCE_NAME_ADAPTER, astAnnotation);
	}

	public boolean sequenceNameTouches(int pos) {
		return this.textRangeTouches(this.sequenceNameTextRange, pos);
	}


	// ********** static methods **********

	protected static DeclarationAnnotationElementAdapter<String> buildAdapter(String elementName) {
		return buildAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	protected static DeclarationAnnotationElementAdapter<Integer> buildIntegerAdapter(String elementName) {
		return buildIntegerAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

}
