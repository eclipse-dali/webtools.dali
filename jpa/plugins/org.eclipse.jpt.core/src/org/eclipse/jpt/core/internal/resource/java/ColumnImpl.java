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
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class ColumnImpl extends AbstractColumnImpl implements Column, NestableAnnotation
{
	// this adapter is only used by a Column annotation associated with a mapping annotation (e.g. Basic)
	public static final DeclarationAnnotationAdapter MAPPING_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	// hold this so we can get the 'length' text range
	private final DeclarationAnnotationElementAdapter<String> lengthDeclarationAdapter;
	
	// hold this so we can get the 'precision' text range
	private final DeclarationAnnotationElementAdapter<String> precisionDeclarationAdapter;
	
	// hold this so we can get the 'scale' text range
	private final DeclarationAnnotationElementAdapter<String> scaleDeclarationAdapter;
	
	private final IntAnnotationElementAdapter lengthAdapter;

	private final IntAnnotationElementAdapter precisionAdapter;

	private final IntAnnotationElementAdapter scaleAdapter;

	private int length = Column.DEFAULT_LENGTH;
	
	private int precision = Column.DEFAULT_PRECISION;
	
	private int scale = Column.DEFAULT_SCALE;
	
	protected ColumnImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
		this.lengthDeclarationAdapter = this.buildNumberElementAdapter(JPA.COLUMN__LENGTH);
		this.lengthAdapter = this.buildShortCircuitIntElementAdapter(this.lengthDeclarationAdapter);
		this.precisionDeclarationAdapter = this.buildNumberElementAdapter(JPA.COLUMN__PRECISION);
		this.precisionAdapter = this.buildShortCircuitIntElementAdapter(this.precisionDeclarationAdapter);
		this.scaleDeclarationAdapter = this.buildNumberElementAdapter(JPA.COLUMN__SCALE);
		this.scaleAdapter = this.buildShortCircuitIntElementAdapter(this.scaleDeclarationAdapter);
	}
	
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.length = this.length(astRoot);
		this.precision = this.precision(astRoot);
		this.scale = this.scale(astRoot);
	}
	
	@Override
	protected String nameElementName() {
		return JPA.COLUMN__NAME;
	}
	
	@Override
	protected String columnDefinitionElementName() {
		return JPA.COLUMN__COLUMN_DEFINITION;
	}
	
	@Override
	protected String tableElementName() {
		return JPA.COLUMN__TABLE;
	}

	@Override
	protected String uniqueElementName() {
		return JPA.COLUMN__UNIQUE;
	}

	@Override
	protected String nullableElementName() {
		return JPA.COLUMN__NULLABLE;
	}

	@Override
	protected String insertableElementName() {
		return JPA.COLUMN__INSERTABLE;
	}

	@Override
	protected String updatableElementName() {
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
		Column oldColumn = (Column) oldAnnotation;
		setLength(oldColumn.getLength());
		setPrecision(oldColumn.getPrecision());
		setScale(oldColumn.getScale());
	}
	
	//************** Column implementation **************
	public int getLength() {
		return this.length;
	}

	public void setLength(int newLength) {
		int oldLength = this.length;
		this.length = newLength;
		this.lengthAdapter.setValue(newLength);
		firePropertyChanged(LENGTH_PROPERTY, oldLength, newLength);
	}

	public int getPrecision() {
		return this.precision;
	}

	public void setPrecision(int newPrecision) {
		int oldPrecision = this.precision;
		this.precision = newPrecision;
		this.precisionAdapter.setValue(newPrecision);
		firePropertyChanged(PRECISION_PROPERTY, oldPrecision, newPrecision);
	}

	public int getScale() {
		return this.scale;
	}

	public void setScale(int newScale) {
		int oldScale = this.scale;
		this.scale = newScale;
		this.scaleAdapter.setValue(newScale);
		firePropertyChanged(SCALE_PROPERTY, oldScale, newScale);
	}
	
	public ITextRange lengthTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.lengthDeclarationAdapter, astRoot);
	}
	
	public ITextRange precisionTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.precisionDeclarationAdapter, astRoot);
	}
	
	public ITextRange scaleTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.scaleDeclarationAdapter, astRoot);
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setLength(this.length(astRoot));
		this.setPrecision(this.precision(astRoot));
		this.setScale(this.scale(astRoot));
	}

	protected int length(CompilationUnit astRoot) {
		return this.lengthAdapter.getValue(astRoot);
	}	
	
	protected int precision(CompilationUnit astRoot) {
		return this.precisionAdapter.getValue(astRoot);
	}	
	
	protected int scale(CompilationUnit astRoot) {
		return this.scaleAdapter.getValue(astRoot);
	}
	
	// ********** static methods **********

	static ColumnImpl createAttributeOverrideColumn(JavaResource parent, Member member, DeclarationAnnotationAdapter attributeOverrideAnnotationAdapter) {
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

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new ColumnImpl(parent, member, ColumnImpl.MAPPING_DECLARATION_ANNOTATION_ADAPTER);
		}
		
		public Annotation buildNullAnnotation(JavaResource parent, Member member) {
			return new NullColumn(parent);
		}
		
		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
