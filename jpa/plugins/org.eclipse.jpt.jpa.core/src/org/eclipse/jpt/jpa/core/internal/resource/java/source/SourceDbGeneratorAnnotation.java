/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NumberIntegerExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.resource.java.DbGeneratorAnnotation;

/**
 * <ul>
 * <li>javax.persistence.SequenceGenerator
 * <li>javax.persistence.TableGenerator
 * </ul>
 */
abstract class SourceDbGeneratorAnnotation
	extends SourceGeneratorAnnotation
	implements DbGeneratorAnnotation
{

	final DeclarationAnnotationElementAdapter<Integer> initialValueDeclarationAdapter;
	final AnnotationElementAdapter<Integer> initialValueAdapter;
	Integer initialValue;
	TextRange initialValueTextRange;

	final DeclarationAnnotationElementAdapter<Integer> allocationSizeDeclarationAdapter;
	final AnnotationElementAdapter<Integer> allocationSizeAdapter;
	Integer allocationSize;
	TextRange allocationSizeTextRange;


	SourceDbGeneratorAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		super(parent, element, daa);
		this.initialValueDeclarationAdapter = this.getInitialValueAdapter();
		this.initialValueAdapter = this.buildIntegerAdapter(this.initialValueDeclarationAdapter);
		this.allocationSizeDeclarationAdapter = this.getAllocationSizeAdapter();
		this.allocationSizeAdapter = this.buildIntegerAdapter(this.allocationSizeDeclarationAdapter);
	}

	protected AnnotationElementAdapter<Integer> buildIntegerAdapter(DeclarationAnnotationElementAdapter<Integer> daea) {
		return new AnnotatedElementAnnotationElementAdapter<Integer>(this.annotatedElement, daea);
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);

		this.initialValue = this.buildInitialValue(astAnnotation);
		this.initialValueTextRange = this.buildInitialValueTextRange(astAnnotation);

		this.allocationSize = this.buildAllocationSize(astAnnotation);
		this.allocationSizeTextRange = this.buildAllocationSizeTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);

		this.syncInitialValue(this.buildInitialValue(astAnnotation));
		this.initialValueTextRange = this.buildInitialValueTextRange(astAnnotation);

		this.syncAllocationSize(this.buildAllocationSize(astAnnotation));
		this.allocationSizeTextRange = this.buildAllocationSizeTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.initialValue == null) &&
				(this.allocationSize == null);
	}


	// ********** DbGeneratorAnnotation implementation **********

	// ***** initial value
	public Integer getInitialValue() {
		return this.initialValue;
	}

	public void setInitialValue(Integer initialValue) {
		if (this.attributeValueHasChanged(this.initialValue, initialValue)) {
			this.initialValue = initialValue;
			this.initialValueAdapter.setValue(initialValue);
		}
	}

	private void syncInitialValue(Integer astIinitialValue) {
		Integer old = this.initialValue;
		this.initialValue = astIinitialValue;
		this.firePropertyChanged(INITIAL_VALUE_PROPERTY, old, astIinitialValue);
	}

	private Integer buildInitialValue(Annotation astAnnotation) {
		return this.initialValueAdapter.getValue(astAnnotation);
	}

	public TextRange getInitialValueTextRange() {
		return this.initialValueTextRange;
	}

	private TextRange buildInitialValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.initialValueDeclarationAdapter, astAnnotation);
	}

	abstract DeclarationAnnotationElementAdapter<Integer> getInitialValueAdapter();

	// ***** allocation size
	public Integer getAllocationSize() {
		return this.allocationSize;
	}

	public void setAllocationSize(Integer allocationSize) {
		if (this.attributeValueHasChanged(this.allocationSize, allocationSize)) {
			this.allocationSize = allocationSize;
			this.allocationSizeAdapter.setValue(allocationSize);
		}
	}

	private void syncAllocationSize(Integer astAllocationSize) {
		Integer old = this.allocationSize;
		this.allocationSize = astAllocationSize;
		this.firePropertyChanged(ALLOCATION_SIZE_PROPERTY, old, astAllocationSize);
	}

	private Integer buildAllocationSize(Annotation astAnnotation) {
		return this.allocationSizeAdapter.getValue(astAnnotation);
	}

	public TextRange getAllocationSizeTextRange() {
		return this.allocationSizeTextRange;
	}

	private TextRange buildAllocationSizeTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.allocationSizeDeclarationAdapter, astAnnotation);
	}

	abstract DeclarationAnnotationElementAdapter<Integer> getAllocationSizeAdapter();


	// ********** static methods **********

	static DeclarationAnnotationElementAdapter<Integer> buildIntegerAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(annotationAdapter, elementName, NumberIntegerExpressionConverter.instance());
	}
}
