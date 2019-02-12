/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
public class EclipseLinkJpaFactory2_4
	extends EclipseLinkJpaFactory2_0
{
	public EclipseLinkJpaFactory2_4() {
		super();
	}

	@Override
	public JavaGeneratorContainer buildJavaGeneratorContainer(Parent parentAdapter) {
		return new EclipseLinkJavaGeneratorContainer(parentAdapter);
	}
}
