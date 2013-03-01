/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer.Parent;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaGeneratorContainer;

/**
 *  EclipseLink 2.4 factory
 */
public class EclipseLink2_4JpaFactory
	extends EclipseLink2_0JpaFactory
{
	public EclipseLink2_4JpaFactory() {
		super();
	}

	@Override
	public JavaGeneratorContainer buildJavaGeneratorContainer(Parent parentAdapter) {
		return new EclipseLinkJavaGeneratorContainer(parentAdapter);
	}
}
