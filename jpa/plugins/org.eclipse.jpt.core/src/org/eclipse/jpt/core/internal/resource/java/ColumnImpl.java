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
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class ColumnImpl extends AbstractColumnImpl implements Column, NestableAnnotation
{
	// this adapter is only used by a Column annotation associated with a mapping annotation (e.g. Basic)
	public static final DeclarationAnnotationAdapter MAPPING_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.COLUMN);

	private final IntAnnotationElementAdapter lengthAdapter;

	private final IntAnnotationElementAdapter precisionAdapter;

	private final IntAnnotationElementAdapter scaleAdapter;

	private int length = -1;
	
	private int precision = -1;
	
	private int scale = -1;
	
	public ColumnImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
		this.lengthAdapter = this.buildShortCircuitIntElementAdapter(JPA.COLUMN__LENGTH);
		this.precisionAdapter = this.buildShortCircuitIntElementAdapter(JPA.COLUMN__PRECISION);
		this.scaleAdapter = this.buildShortCircuitIntElementAdapter(JPA.COLUMN__SCALE);
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
		return JPA.COLUMN;
	}
	
	public void moveAnnotation(int newIndex) {
		//TODO move makes no sense for Column.  maybe NestableAnnotation
		//needs to be split up and we could have IndexableAnnotation
	}
	
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		Column oldColumn = (Column) oldAnnotation;
		setLength(oldColumn.getLength());
		setPrecision(oldColumn.getPrecision());
		setScale(oldColumn.getScale());
	}
	
	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
		this.lengthAdapter.setValue(length);
	}

	public int getPrecision() {
		return this.precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
		this.precisionAdapter.setValue(precision);
	}

	public int getScale() {
		return this.scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
		this.scaleAdapter.setValue(scale);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setLength(this.lengthAdapter.getValue(astRoot));
		this.setPrecision(this.precisionAdapter.getValue(astRoot));
		this.setScale(this.scaleAdapter.getValue(astRoot));
	}

	// ********** static methods **********

	static ColumnImpl createAttributeOverrideColumn(JavaResource parent, Member member, DeclarationAnnotationAdapter attributeOverrideAnnotationAdapter) {
		return new ColumnImpl(parent, member, buildAttributeOverrideAnnotationAdapter(attributeOverrideAnnotationAdapter));
	}

	static DeclarationAnnotationAdapter buildAttributeOverrideAnnotationAdapter(DeclarationAnnotationAdapter attributeOverrideAnnotationAdapter) {
		return new NestedDeclarationAnnotationAdapter(attributeOverrideAnnotationAdapter, JPA.ATTRIBUTE_OVERRIDE__COLUMN, JPA.COLUMN);
	}
}
