/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.GenericJavaConverterType;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.GenericJavaNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.GenericJavaStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.JavaNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.JavaQueryContainer2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.JavaStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaFactory2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.NamedStoredProcedureQuery2_1Annotation;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.StoredProcedureParameter2_1Annotation;

/**
 *  EclipseLink 2.5 factory
 */
public class EclipseLink2_5JpaFactory
	extends EclipseLink2_4JpaFactory
	implements JpaFactory2_1
{
	public EclipseLink2_5JpaFactory() {
		super();
	}

	public JavaConverterType2_1 buildJavaConverterType(JpaContextNode parent, JavaResourceType jrt) {
		return new GenericJavaConverterType(parent, jrt);
	}

	public JavaNamedStoredProcedureQuery2_1 buildJavaNamedStoredProcedureQuery2_1(
			JavaQueryContainer2_1 parent,
			NamedStoredProcedureQuery2_1Annotation namedStoredProcedureQueryAnnotation) {
		return new GenericJavaNamedStoredProcedureQuery2_1(parent, namedStoredProcedureQueryAnnotation);
	}

	public JavaStoredProcedureParameter2_1 buildJavaStoredProcedureParameter2_1(
			JavaNamedStoredProcedureQuery2_1 parent, 
			StoredProcedureParameter2_1Annotation parameterAnnotation) {
		return new GenericJavaStoredProcedureParameter2_1(parent, parameterAnnotation);
	}
}
