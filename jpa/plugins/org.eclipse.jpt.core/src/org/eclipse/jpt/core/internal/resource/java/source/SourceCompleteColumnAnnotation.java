/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * Abstract implementation of ColumnAnnotation to be used for JPA annotations:
 * javax.persistence.Column
 * javax.persistence.MapKeyColumn
 */
public abstract class SourceCompleteColumnAnnotation
	extends SourceBaseColumnAnnotation
	implements ColumnAnnotation
{
	protected final DeclarationAnnotationElementAdapter<Integer> lengthDeclarationAdapter;
	protected final AnnotationElementAdapter<Integer> lengthAdapter;
	protected Integer length;

	protected final DeclarationAnnotationElementAdapter<Integer> precisionDeclarationAdapter;
	protected final AnnotationElementAdapter<Integer> precisionAdapter;
	protected Integer precision;

	protected final DeclarationAnnotationElementAdapter<Integer> scaleDeclarationAdapter;
	protected final AnnotationElementAdapter<Integer> scaleAdapter;
	protected Integer scale;


	protected SourceCompleteColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
		this.lengthDeclarationAdapter = this.buildIntegerElementAdapter(this.getLengthElementName());
		this.lengthAdapter = this.buildShortCircuitIntegerElementAdapter(this.lengthDeclarationAdapter);
		this.precisionDeclarationAdapter = this.buildIntegerElementAdapter(this.getPrecisionElementName());
		this.precisionAdapter = this.buildShortCircuitIntegerElementAdapter(this.precisionDeclarationAdapter);
		this.scaleDeclarationAdapter = this.buildIntegerElementAdapter(this.getScaleElementName());
		this.scaleAdapter = this.buildShortCircuitIntegerElementAdapter(this.scaleDeclarationAdapter);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.length = this.buildLength(astRoot);
		this.precision = this.buildPrecision(astRoot);
		this.scale = this.buildScale(astRoot);
	}

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setLength(this.buildLength(astRoot));
		this.setPrecision(this.buildPrecision(astRoot));
		this.setScale(this.buildScale(astRoot));
	}


	 // ********** ColumnAnnotation implementation **********

	// ***** length
	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		if (this.attributeValueHasNotChanged(this.length, length)) {
			return;
		}
		Integer old = this.length;
		this.length = length;
		this.lengthAdapter.setValue(length);
		this.firePropertyChanged(LENGTH_PROPERTY, old, length);
	}

	private Integer buildLength(CompilationUnit astRoot) {
		return this.lengthAdapter.getValue(astRoot);
	}

	public TextRange getLengthTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.lengthDeclarationAdapter, astRoot);
	}
	
	protected abstract String getLengthElementName();

	// ***** precision
	public Integer getPrecision() {
		return this.precision;
	}

	public void setPrecision(Integer precision) {
		if (this.attributeValueHasNotChanged(this.precision, precision)) {
			return;
		}
		Integer old = this.precision;
		this.precision = precision;
		this.precisionAdapter.setValue(precision);
		this.firePropertyChanged(PRECISION_PROPERTY, old, precision);
	}

	private Integer buildPrecision(CompilationUnit astRoot) {
		return this.precisionAdapter.getValue(astRoot);
	}

	public TextRange getPrecisionTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.precisionDeclarationAdapter, astRoot);
	}
	
	protected abstract String getPrecisionElementName();

	// ***** scale
	public Integer getScale() {
		return this.scale;
	}

	public void setScale(Integer scale) {
		if (this.attributeValueHasNotChanged(this.scale, scale)) {
			return;
		}
		Integer old = this.scale;
		this.scale = scale;
		this.scaleAdapter.setValue(scale);
		this.firePropertyChanged(SCALE_PROPERTY, old, scale);
	}

	private Integer buildScale(CompilationUnit astRoot) {
		return this.scaleAdapter.getValue(astRoot);
	}

	public TextRange getScaleTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.scaleDeclarationAdapter, astRoot);
	}
	
	protected abstract String getScaleElementName();
}
