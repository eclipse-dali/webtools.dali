/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.resource.java.source;

import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.core.internal.resource.java.source.SourceSequenceGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;

/**
 * javax.persistence.SequenceGenerator
 */
public final class SourceSequenceGenerator1_0Annotation
	extends SourceSequenceGeneratorAnnotation
{

	public SourceSequenceGenerator1_0Annotation(JavaResourceNode parent, Member member) {
		super(parent, member);
	}

}
