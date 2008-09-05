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
import org.eclipse.jpt.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

public class ColumnImpl extends BaseColumnImpl implements ColumnAnnotation, NestableAnnotation
{
	// this adapter is only used by a Column annotation associated with a mapping annotation (e.g. Basic)
	public static final DeclarationAnnotationAdapter MAPPING_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	// hold this so we can get the 'length' text range
	private final DeclarationAnnotationElementAdapter<Integer> lengthDeclarationAdapter;
	
	// hold this so we can get the 'precision' text range
	private final DeclarationAnnotationElementAdapter<Integer> precisionDeclarationAdapter;
	
	// hold this so we can get the 'scale' text range
	private final DeclarationAnnotationElementAdapter<Integer> scaleDeclarationAdapter;
	
	private final AnnotationElementAdapter<Integer> lengthAdapter;

	private final AnnotationElementAdapter<Integer> precisionAdapter;

	private final AnnotationElementAdapter<Integer> scaleAdapter;

	private Integer length;
	
	private Integer precision;
	
	private Integer scale;
	
	public ColumnImpl(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
		this.lengthDeclarationAdapter = this.buildIntegerElementAdapter(JPA.COLUMN__LENGTH);
		this.lengthAdapter = this.buildShortCircuitIntegerElementAdapter(this.lengthDeclarationAdapter);
		this.precisionDeclarationAdapter = this.buildIntegerElementAdapter(JPA.COLUMN__PRECISION);
		this.precisionAdapter = this.buildShortCircuitIntegerElementAdapter(this.precisionDeclarationAdapter);
		this.scaleDeclarationAdapter = this.buildIntegerElementAdapter(JPA.COLUMN__SCALE);
		this.scaleAdapter = this.buildShortCircuitIntegerElementAdapter(this.scaleDeclarationAdapter);
	}
	
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.length = this.length(astRoot);
		this.precision = this.precision(astRoot);
		this.scale = this.scale(astRoot);
	}
	
	@Override
	protected String getNameElementName() {
		return JPA.COLUMN__NAME;
	}
	
	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.COLUMN__COLUMN_DEFINITION;
	}
	
	@Override
	protected String getTableElementName() {
		return JPA.COLUMN__TABLE;
	}

	@Override
	protected String getUniqueElementName() {
		return JPA.COLUMN__UNIQUE;
	}

	@Override
	protected String getNullableElementName() {
		return JPA.COLUMN__NULLABLE;
	}

	@Override
	protected String getInsertableElementName() {
		return JPA.COLUMN__INSERTABLE;
	}

	@Override
	protected String getUpdatableElementName() {
		return JPA.COLUMN__UPDATABLE;
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	public void moveAnnotation(int newIndex) {
		//TODO move makes no sense for Column.  maybe NestableAnnotation
		//needs to be split up and we could have IndexableAnnotation
	}
	
	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		ColumnAnnotation oldColumn = (ColumnAnnotation) oldAnnotation;
		setLength(oldColumn.getLength());
		setPrecision(oldColumn.getPrecision());
		setScale(oldColumn.getScale());
	}
	
	//************** Column implementation **************
	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer newLength) {
		if (attributeValueHasNotChanged(this.length, newLength)) {
			return;
		}
		Integer oldLength = this.length;
		this.length = newLength;
		this.lengthAdapter.setValue(newLength);
		firePropertyChanged(LENGTH_PROPERTY, oldLength, newLength);
	}

	public Integer getPrecision() {
		return this.precision;
	}

	public void setPrecision(Integer newPrecision) {
		if (attributeValueHasNotChanged(this.precision, newPrecision)) {
			return;
		}
		Integer oldPrecision = this.precision;
		this.precision = newPrecision;
		this.precisionAdapter.setValue(newPrecision);
		firePropertyChanged(PRECISION_PROPERTY, oldPrecision, newPrecision);
	}

	public Integer getScale() {
		return this.scale;
	}

	public void setScale(Integer newScale) {
		if (attributeValueHasNotChanged(this.scale, newScale)) {
			return;
		}
		Integer oldScale = this.scale;
		this.scale = newScale;
		this.scaleAdapter.setValue(newScale);
		firePropertyChanged(SCALE_PROPERTY, oldScale, newScale);
	}
	
	public TextRange getLengthTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.lengthDeclarationAdapter, astRoot);
	}
	
	public TextRange getPrecisionTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.precisionDeclarationAdapter, astRoot);
	}
	
	public TextRange getScaleTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.scaleDeclarationAdapter, astRoot);
	}

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setLength(this.length(astRoot));
		this.setPrecision(this.precision(astRoot));
		this.setScale(this.scale(astRoot));
	}

	protected Integer length(CompilationUnit astRoot) {
		return this.lengthAdapter.getValue(astRoot);
	}	
	
	protected Integer precision(CompilationUnit astRoot) {
		return this.precisionAdapter.getValue(astRoot);
	}	
	
	protected Integer scale(CompilationUnit astRoot) {
		return this.scaleAdapter.getValue(astRoot);
	}
	
	// ********** static methods **********

	static ColumnImpl createAttributeOverrideColumn(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter attributeOverrideAnnotationAdapter) {
		return new ColumnImpl(parent, member, buildAttributeOverrideAnnotationAdapter(attributeOverrideAnnotationAdapter));
	}

	static DeclarationAnnotationAdapter buildAttributeOverrideAnnotationAdapter(DeclarationAnnotationAdapter attributeOverrideAnnotationAdapter) {
		return new NestedDeclarationAnnotationAdapter(attributeOverrideAnnotationAdapter, JPA.ATTRIBUTE_OVERRIDE__COLUMN, JPA.COLUMN);
	}
	
	public static class ColumnAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final ColumnAnnotationDefinition INSTANCE = new ColumnAnnotationDefinition();


		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private ColumnAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new ColumnImpl(parent, member, ColumnImpl.MAPPING_DECLARATION_ANNOTATION_ADAPTER);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new NullColumn(parent);
		}
		
		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
