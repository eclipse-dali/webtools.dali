/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;

/**
 * Java mapped superclass
 */
public class GenericJavaMappedSuperclass
	extends AbstractJavaMappedSuperclass
{
	public GenericJavaMappedSuperclass(JavaPersistentType parent, MappedSuperclassAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
	}
}
