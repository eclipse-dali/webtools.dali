/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.internal.context.base.IGeneratedValue;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValue;

public interface IJavaGeneratedValue extends IGeneratedValue, IJavaJpaContextNode
{
	void initializeFromResource(GeneratedValue generatedValue);
	
	void update(GeneratedValue generatedValue);

}