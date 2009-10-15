/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryOneToOneAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;

/**
 *  BinaryOneToOne1_0Annotation
 */
public final class BinaryOneToOne1_0Annotation
	extends BinaryOneToOneAnnotation
{

	public BinaryOneToOne1_0Annotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

}