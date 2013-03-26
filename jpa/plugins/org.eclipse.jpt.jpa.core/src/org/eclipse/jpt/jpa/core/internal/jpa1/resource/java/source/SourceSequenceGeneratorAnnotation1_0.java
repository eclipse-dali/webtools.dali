/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.resource.java.source;

import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceSequenceGeneratorAnnotation;

/**
 * <code>javax.persistence.SequenceGenerator</code>
 */
public final class SourceSequenceGeneratorAnnotation1_0
	extends SourceSequenceGeneratorAnnotation
{
	public SourceSequenceGeneratorAnnotation1_0(JavaResourceModel parent, AnnotatedElement element) {
		super(parent, element);
	}
}
