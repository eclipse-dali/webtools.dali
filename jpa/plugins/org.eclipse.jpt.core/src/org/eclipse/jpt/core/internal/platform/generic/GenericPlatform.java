/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform.generic;

import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;

public class GenericPlatform 
	extends BaseJpaPlatform
{
	public final static String ID = "generic";
	
	@Override
	protected IJpaFactory buildJpaFactory() {
		return new GenericJpaFactory();
	}

}
