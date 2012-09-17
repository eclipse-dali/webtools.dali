/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context;

import org.eclipse.jpt.jpa.core.context.TypeMapping;

public class EclipseLinkTypeMappingValidator
	extends AbstractEclipseLinkTypeMappingValidator<TypeMapping>
{
	public EclipseLinkTypeMappingValidator(TypeMapping typeMapping) {
		super(typeMapping);
	}
}
