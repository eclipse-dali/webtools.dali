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
import org.eclipse.jpt.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.Column
 */
public final class SourceColumnAnnotation
	extends SourceBaseColumnAnnotation
	implements NestableColumnAnnotation
{
	// this adapter is only used by a Column annotation associated with a mapping annotation (e.g. Basic)
	public static final DeclarationAnnotationAdapter MAPPING_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final DeclarationAnnotationElementAdapter<Integer> lengthDeclarationAdapter;
	private final AnnotationElementAdapter<Integer> lengthAdapter;
	private Integer length;

	private final DeclarationAnnotationElementAdapter<Integer> precisionDeclarationAdapter;
	private final AnnotationElementAdapter<Integer> precisionAdapter;
	private Integer precision;

	private final DeclarationAnnotationElementAdapter<Integer> scaleDeclarationAdapter;
	private final AnnotationElementAdapter<Integer> scaleAdapter;
	private Integer scale;


	public SourceColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
		this.lengthDeclarationAdapter = this.buildIntegerElementAdapter(JPA.COLUMN__LENGTH);
		this.lengthAdapter = this.buildShortCircuitIntegerElementAdapter(this.lengthDeclarationAdapter);
		this.precisionDeclarationAdapter = this.buildIntegerElementAdapter(JPA.COLUMN__PRECISION);
		this.precisionAdapter = this.buildShortCircuitIntegerElementAdapter(this.precisionDeclarationAdapter);
		this.scaleDeclarationAdapter = this.buildIntegerElementAdapter(JPA.COLUMN__SCALE);
		this.scaleAdapter = this.buildShortCircuitIntegerElementAdapter(this.scaleDeclarationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
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


	// ********** JavaSourceNamedColumnAnnotation implementation **********

	@Override
	String getNameElementName() {
		return JPA.COLUMN__NAME;
	}

	@Override
	String getColumnDefinitionElementName() {
		return JPA.COLUMN__COLUMN_DEFINITION;
	}


	// ********** JavaSourceBaseColumnAnnotation implementation **********

	@Override
	String getTableElementName() {
		return JPA.COLUMN__TABLE;
	}

	@Override
	String getUniqueElementName() {
		return JPA.COLUMN__UNIQUE;
	}

	@Override
	String getNullableElementName() {
		return JPA.COLUMN__NULLABLE;
	}

	@Override
	String getInsertableElementName() {
		return JPA.COLUMN__INSERTABLE;
	}

	@Override
	String getUpdatableElementName() {
		return JPA.COLUMN__UPDATABLE;
	}


	 // ********** Column implementation **********

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


	 // ********** NestableAnnotation implementation **********

	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		ColumnAnnotation oldColumn = (ColumnAnnotation) oldAnnotation;
		this.setLength(oldColumn.getLength());
		this.setPrecision(oldColumn.getPrecision());
		this.setScale(oldColumn.getScale());
	}

	public void moveAnnotation(int newIndex) {
		// the only place where a column annotation is nested is in an
		// attribute override; and that only nests a single column, not an array
		// of columns; so #moveAnnotation(int) is never called
		// TODO maybe NestableAnnotation should be split up;
		// moving this method to something like IndexableAnnotation
		throw new UnsupportedOperationException();
	}


	// ********** static methods **********

	static NestableColumnAnnotation createAttributeOverrideColumn(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter attributeOverrideAnnotationAdapter) {
		return new SourceColumnAnnotation(parent, member, buildAttributeOverrideAnnotationAdapter(attributeOverrideAnnotationAdapter));
	}

	static DeclarationAnnotationAdapter buildAttributeOverrideAnnotationAdapter(DeclarationAnnotationAdapter attributeOverrideAnnotationAdapter) {
		return new NestedDeclarationAnnotationAdapter(attributeOverrideAnnotationAdapter, JPA.ATTRIBUTE_OVERRIDE__COLUMN, JPA.COLUMN);
	}

}
