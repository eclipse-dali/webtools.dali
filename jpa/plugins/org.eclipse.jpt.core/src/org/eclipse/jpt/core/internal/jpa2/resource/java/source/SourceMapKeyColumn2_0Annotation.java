/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.core.internal.resource.java.source.SourceCompleteColumnAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyColumn2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;

/**
 * <code>javax.persistence.MapKeyColumn</code>
 */
public final class SourceMapKeyColumn2_0Annotation
	extends SourceCompleteColumnAnnotation
	implements MapKeyColumn2_0Annotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(MapKeyColumn2_0Annotation.ANNOTATION_NAME);


	public SourceMapKeyColumn2_0Annotation(JavaResourcePersistentAttribute parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return MapKeyColumn2_0Annotation.ANNOTATION_NAME;
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
