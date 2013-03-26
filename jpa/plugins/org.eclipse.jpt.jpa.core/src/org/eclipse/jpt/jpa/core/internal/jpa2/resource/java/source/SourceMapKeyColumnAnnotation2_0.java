/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source;

import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceCompleteColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyColumnAnnotation2_0;

/**
 * <code>javax.persistence.MapKeyColumn</code>
 */
public final class SourceMapKeyColumnAnnotation2_0
	extends SourceCompleteColumnAnnotation
	implements MapKeyColumnAnnotation2_0
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(MapKeyColumnAnnotation2_0.ANNOTATION_NAME);


	public SourceMapKeyColumnAnnotation2_0(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return MapKeyColumnAnnotation2_0.ANNOTATION_NAME;
	}


	// ********** SourceNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA2_0.MAP_KEY_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA2_0.MAP_KEY_COLUMN__COLUMN_DEFINITION;
	}


	// ********** SourceBaseColumnAnnotation implementation **********

	@Override
	protected String getTableElementName() {
		return JPA2_0.MAP_KEY_COLUMN__TABLE;
	}

	@Override
	protected String getUniqueElementName() {
		return JPA2_0.MAP_KEY_COLUMN__UNIQUE;
	}

	@Override
	protected String getNullableElementName() {
		return JPA2_0.MAP_KEY_COLUMN__NULLABLE;
	}

	@Override
	protected String getInsertableElementName() {
		return JPA2_0.MAP_KEY_COLUMN__INSERTABLE;
	}

	@Override
	protected String getUpdatableElementName() {
		return JPA2_0.MAP_KEY_COLUMN__UPDATABLE;
	}
	
	// ********** SourceCompleteColumnAnnotation implementation **********

	@Override
	protected String getLengthElementName() {
		return JPA2_0.MAP_KEY_COLUMN__LENGTH;
	}
	
	@Override
	protected String getPrecisionElementName() {
		return JPA2_0.MAP_KEY_COLUMN__PRECISION;
	}
	
	@Override
	protected String getScaleElementName() {
		return JPA2_0.MAP_KEY_COLUMN__SCALE;
	}
}
