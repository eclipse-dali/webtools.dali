/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.TableAnnotation;

/**
 * javax.persistence.Table
 */
public final class BinaryTableAnnotation
	extends BinaryBaseTableAnnotation
	implements TableAnnotation
{
	public BinaryTableAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}


	// ********** BinaryBaseTableAnnotation implementation **********

	@Override
	String getNameElementName() {
		return JPA.TABLE__NAME;
	}

	@Override
	String getSchemaElementName() {
		return JPA.TABLE__SCHEMA;
	}

	@Override
	String getCatalogElementName() {
		return JPA.TABLE__CATALOG;
	}

	@Override
	String getUniqueConstraintElementName() {
		return JPA.TABLE__UNIQUE_CONSTRAINTS;
	}

}
