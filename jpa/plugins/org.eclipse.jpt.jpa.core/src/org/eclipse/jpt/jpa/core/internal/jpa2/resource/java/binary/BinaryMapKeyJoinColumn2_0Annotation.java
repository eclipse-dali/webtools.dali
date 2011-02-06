/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryBaseJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.NestableMapKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;

/**
 * javax.persistence.MapKeyJoinColumn
 */
public final class BinaryMapKeyJoinColumn2_0Annotation
	extends BinaryBaseJoinColumnAnnotation
	implements NestableMapKeyJoinColumnAnnotation
{

	public BinaryMapKeyJoinColumn2_0Annotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}


	// ********** BinaryNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__COLUMN_DEFINITION;
	}


	// ********** BinaryBaseColumnAnnotation implementation **********

	@Override
	protected String getTableElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__TABLE;
	}

	@Override
	protected String getUniqueElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__UNIQUE;
	}

	@Override
	protected String getNullableElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__NULLABLE;
	}

	@Override
	protected String getInsertableElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__INSERTABLE;
	}

	@Override
	protected String getUpdatableElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__UPDATABLE;
	}
	
	
	// ********** BinaryBaseJoinColumnAnnotation implementation **********

	@Override
	protected String getReferencedColumnNameElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME;
	}

}
