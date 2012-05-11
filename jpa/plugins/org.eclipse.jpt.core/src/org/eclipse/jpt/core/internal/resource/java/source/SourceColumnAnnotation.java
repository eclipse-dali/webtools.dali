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

import org.eclipse.jpt.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableColumnAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.Column
 */
public final class SourceColumnAnnotation
	extends SourceCompleteColumnAnnotation
	implements NestableColumnAnnotation
{
	// this adapter is only used by a Column annotation associated with a mapping annotation (e.g. Basic)
	public static final DeclarationAnnotationAdapter MAPPING_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);


	public SourceColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	// ********** SourceNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.COLUMN__COLUMN_DEFINITION;
	}


	// ********** SourceBaseColumnAnnotation implementation **********

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
	
	// ********** SourceCompleteColumnAnnotation implementation **********

	@Override
	protected String getLengthElementName() {
		return JPA.COLUMN__LENGTH;
	}
	
	@Override
	protected String getPrecisionElementName() {
		return JPA.COLUMN__PRECISION;
	}
	
	@Override
	protected String getScaleElementName() {
		return JPA.COLUMN__SCALE;
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
