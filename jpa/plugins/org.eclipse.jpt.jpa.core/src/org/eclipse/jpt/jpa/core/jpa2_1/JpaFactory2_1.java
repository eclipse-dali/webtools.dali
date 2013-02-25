/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.JavaNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.JavaQueryContainer2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.JavaStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.NamedStoredProcedureQuery2_1Annotation;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.StoredProcedureParameter2_1Annotation;

/**
 * JPA 2.1 factory
 *<p> 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface JpaFactory2_1
	extends JpaFactory2_0
{
	// ********** Java Context Model **********

	JavaConverterType2_1 buildJavaConverterType(JpaContextModel parent, JavaResourceType jrt); 

	JavaNamedStoredProcedureQuery2_1 buildJavaNamedStoredProcedureQuery2_1(
			JavaQueryContainer2_1 parent,
			NamedStoredProcedureQuery2_1Annotation namedStoredProcedureQueryAnnotation);

	JavaStoredProcedureParameter2_1 buildJavaStoredProcedureParameter2_1(
			JavaNamedStoredProcedureQuery2_1 parent, 
			StoredProcedureParameter2_1Annotation parameterAnnotation);
}
