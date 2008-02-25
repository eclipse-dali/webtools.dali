/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.resource.java.GeneratorAnnotation;

public interface JavaGenerator<T extends GeneratorAnnotation> extends Generator, JavaJpaContextNode
{
	void initializeFromResource(T generator);
	
	void update(T generator);

}