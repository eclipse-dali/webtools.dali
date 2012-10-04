/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;


public abstract class AbstractJavaContextNode
		extends AbstractJaxbContextNode {
	
	// **************** constructor *******************************************
	
	protected AbstractJavaContextNode(JaxbNode parent) {
		super(parent);
	}
	
	
	@Override
	public JptResourceType getResourceType() {
		return PlatformTools.getResourceType(JavaResourceCompilationUnit.CONTENT_TYPE);
	}
}
