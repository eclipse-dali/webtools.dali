/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform.generic;

import org.eclipse.jpt.core.internal.IJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.platform.base.BaseJpaPlatform;


public class GenericJpaPlatform extends BaseJpaPlatform
{
	public static String ID = "generic";
	
	
	public GenericJpaPlatform() {
		super();
	}
	
	
	// **************** Model construction / updating **************************
	
	@Override
	protected IJpaFactory buildJpaFactory() {
		return new GenericJpaFactory();
	}
	
	
	// **************** java annotation support ********************************
	
	@Override
	protected IJpaAnnotationProvider buildAnnotationProvider() {
		return new GenericJpaAnnotationProvider();
	}
}
