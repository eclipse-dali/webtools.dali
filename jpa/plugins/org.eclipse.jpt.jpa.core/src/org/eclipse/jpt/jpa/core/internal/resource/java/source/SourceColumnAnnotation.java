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

import org.eclipse.jpt.common.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.Column</code>
 */
public final class SourceColumnAnnotation
	extends SourceCompleteColumnAnnotation
	implements ColumnAnnotation
{
	// this adapter is only used by a Column annotation associated with a mapping annotation (e.g. Basic)
	public static final DeclarationAnnotationAdapter MAPPING_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);


	public SourceColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		super(parent, element, daa);
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


	// ********** static methods **********

	static ColumnAnnotation createAttributeOverrideColumn(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter attributeOverrideAnnotationAdapter) {
		return new SourceColumnAnnotation(parent, element, buildAttributeOverrideAnnotationAdapter(attributeOverrideAnnotationAdapter));
	}

	static DeclarationAnnotationAdapter buildAttributeOverrideAnnotationAdapter(DeclarationAnnotationAdapter attributeOverrideAnnotationAdapter) {
		return new NestedDeclarationAnnotationAdapter(attributeOverrideAnnotationAdapter, JPA.ATTRIBUTE_OVERRIDE__COLUMN, JPA.COLUMN);
	}
}
