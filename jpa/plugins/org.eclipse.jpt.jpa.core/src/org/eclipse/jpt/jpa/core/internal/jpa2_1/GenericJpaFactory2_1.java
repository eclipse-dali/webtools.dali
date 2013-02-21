/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.internal.jpa2.GenericJpaFactory2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.GenericJavaConverterType;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaFactory2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaConverterType2_1;


/**
 * Central class that allows extenders to easily replace implementations of
 * various Dali interfaces.
 */
public class GenericJpaFactory2_1
	extends GenericJpaFactory2_0
	implements JpaFactory2_1
{

	public GenericJpaFactory2_1() {
		super();
	}

	public JavaConverterType2_1 buildJavaConverterType(JpaContextNode parent, JavaResourceType jrt) {
		return new GenericJavaConverterType(parent, jrt);
	}
}
