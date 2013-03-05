/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.resource.java.CompleteColumnAnnotation;

/**
 * <code><ul>
 * <li>javax.persistence.Column
 * <li>javax.persistence.MapKeyColumn
 * </ul></code>
 */
public abstract class SourceCompleteColumnAnnotation
	extends SourceBaseColumnAnnotation
	implements CompleteColumnAnnotation
{
	protected DeclarationAnnotationElementAdapter<Integer> lengthDeclarationAdapter;
	protected AnnotationElementAdapter<Integer> lengthAdapter;
	protected Integer length;
	protected TextRange lengthTextRange;

	protected DeclarationAnnotationElementAdapter<Integer> precisionDeclarationAdapter;
	protected AnnotationElementAdapter<Integer> precisionAdapter;
	protected Integer precision;
	protected TextRange precisionTextRange;

	protected DeclarationAnnotationElementAdapter<Integer> scaleDeclarationAdapter;
	protected AnnotationElementAdapter<Integer> scaleAdapter;
	protected Integer scale;
	protected TextRange scaleTextRange;


	protected SourceCompleteColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		super(parent, element, daa);
		this.lengthDeclarationAdapter = this.buildLengthDeclarationAdapter();
		this.lengthAdapter = this.buildLengthAdapter();
		this.precisionDeclarationAdapter = this.buildPrecisionDeclarationAdapter();
		this.precisionAdapter = this.buildPrecisionAdapter();
		this.scaleDeclarationAdapter = this.buildScaleDeclarationAdapter();
		this.scaleAdapter = this.buildScaleAdapter();
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);

		this.length = this.buildLength(astAnnotation);
		this.lengthTextRange = this.buildLengthTextRange(astAnnotation);

		this.precision = this.buildPrecision(astAnnotation);
		this.precisionTextRange = this.buildPrecisionTextRange(astAnnotation);

		this.scale = this.buildScale(astAnnotation);
		this.scaleTextRange = this.buildScaleTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);

		this.syncLength(this.buildLength(astAnnotation));
		this.lengthTextRange = this.buildLengthTextRange(astAnnotation);

		this.syncPrecision(this.buildPrecision(astAnnotation));
		this.precisionTextRange = this.buildPrecisionTextRange(astAnnotation);

		this.syncScale(this.buildScale(astAnnotation));
		this.scaleTextRange = this.buildScaleTextRange(astAnnotation);
	}


	 // ********** ColumnAnnotation implementation **********

	// ***** length
	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		if (this.attributeValueHasChanged(this.length, length)) {
			this.length = length;
			this.lengthAdapter.setValue(length);
		}
	}

	private void syncLength(Integer astLength) {
		Integer old = this.length;
		this.length = astLength;
		this.firePropertyChanged(LENGTH_PROPERTY, old, astLength);
	}

	private Integer buildLength(Annotation astAnnotation) {
		return this.lengthAdapter.getValue(astAnnotation);
	}

	public TextRange getLengthTextRange() {
		return this.lengthTextRange;
	}
	
	protected TextRange buildLengthTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.lengthDeclarationAdapter, astAnnotation);
	}
	
	protected DeclarationAnnotationElementAdapter<Integer> buildLengthDeclarationAdapter() {
		return this.buildIntegerElementAdapter(this.getLengthElementName());
	}

	protected AnnotationElementAdapter<Integer> buildLengthAdapter() {
		return this.buildIntegerElementAdapter(this.lengthDeclarationAdapter);
	}

	protected abstract String getLengthElementName();

	// ***** precision
	public Integer getPrecision() {
		return this.precision;
	}

	public void setPrecision(Integer precision) {
		if (this.attributeValueHasChanged(this.precision, precision)) {
			this.precision = precision;
			this.precisionAdapter.setValue(precision);
		}
	}

	private void syncPrecision(Integer astPrecision) {
		Integer old = this.precision;
		this.precision = astPrecision;
		this.firePropertyChanged(PRECISION_PROPERTY, old, astPrecision);
	}

	private Integer buildPrecision(Annotation astAnnotation) {
		return this.precisionAdapter.getValue(astAnnotation);
	}

	public TextRange getPrecisionTextRange() {
		return this.precisionTextRange;
	}
	
	protected TextRange buildPrecisionTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.precisionDeclarationAdapter, astAnnotation);
	}
	
	protected DeclarationAnnotationElementAdapter<Integer> buildPrecisionDeclarationAdapter() {
		return this.buildIntegerElementAdapter(this.getPrecisionElementName());
	}

	protected AnnotationElementAdapter<Integer> buildPrecisionAdapter() {
		return this.buildIntegerElementAdapter(this.precisionDeclarationAdapter);
	}

	protected abstract String getPrecisionElementName();

	// ***** scale
	public Integer getScale() {
		return this.scale;
	}

	public void setScale(Integer scale) {
		if (this.attributeValueHasChanged(this.scale, scale)) {
			this.scale = scale;
			this.scaleAdapter.setValue(scale);
		}
	}

	private void syncScale(Integer astScale) {
		Integer old = this.scale;
		this.scale = astScale;
		this.firePropertyChanged(SCALE_PROPERTY, old, astScale);
	}

	private Integer buildScale(Annotation astAnnotation) {
		return this.scaleAdapter.getValue(astAnnotation);
	}

	public TextRange getScaleTextRange() {
		return this.scaleTextRange;
	}
	
	protected TextRange buildScaleTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.scaleDeclarationAdapter, astAnnotation);
	}
	
	protected DeclarationAnnotationElementAdapter<Integer> buildScaleDeclarationAdapter() {
		return this.buildIntegerElementAdapter(this.getScaleElementName());
	}

	protected AnnotationElementAdapter<Integer> buildScaleAdapter() {
		return this.buildIntegerElementAdapter(this.scaleDeclarationAdapter);
	}

	protected abstract String getScaleElementName();


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.length == null) &&
				(this.precision == null) &&
				(this.scale == null);
	}
}
