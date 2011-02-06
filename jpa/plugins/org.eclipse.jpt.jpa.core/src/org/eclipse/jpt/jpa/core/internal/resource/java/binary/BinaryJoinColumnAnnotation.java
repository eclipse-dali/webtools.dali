/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NestableJoinColumnAnnotation;

/**
 * javax.persistence.JoinColumn
 */
public final class BinaryJoinColumnAnnotation
	extends BinaryBaseJoinColumnAnnotation
	implements NestableJoinColumnAnnotation
{

	public BinaryJoinColumnAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}


	// ********** BinaryNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.JOIN_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.JOIN_COLUMN__COLUMN_DEFINITION;
	}


	// ********** BinaryBaseColumnAnnotation implementation **********

	@Override
	protected String getTableElementName() {
		return JPA.JOIN_COLUMN__TABLE;
	}

	@Override
	protected String getUniqueElementName() {
		return JPA.JOIN_COLUMN__UNIQUE;
	}

	@Override
	protected String getNullableElementName() {
		return JPA.JOIN_COLUMN__NULLABLE;
	}

	@Override
	protected String getInsertableElementName() {
		return JPA.JOIN_COLUMN__INSERTABLE;
	}

	@Override
	protected String getUpdatableElementName() {
		return JPA.JOIN_COLUMN__UPDATABLE;
	}
	
	
	// ********** BinaryBaseJoinColumnAnnotation implementation **********

	@Override
	protected String getReferencedColumnNameElementName() {
		return JPA.JOIN_COLUMN__REFERENCED_COLUMN_NAME;
	}

}
