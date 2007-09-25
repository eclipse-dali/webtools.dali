/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.jdtutility.Member;

public interface MappingAnnotationProvider extends AnnotationProvider
{
	/**
	 * Return the fully qualified names of the annotations that can exist
	 * with the mapping annotation.  These will be used to create JavaTypeAnnotations
	 */
	Iterator<String> correspondingAnnotationNames();
	
	MappingAnnotation buildAnnotation(Member member, JpaPlatform jpaPlatform);
}
