/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceNode;


/**
 * Source convenience methods
 */
public abstract class SourceNode
	extends AbstractJavaResourceNode
{

	public SourceNode(JavaResourceNode parent) {
		super(parent);
	}

	public JavaResourceCompilationUnit getJavaResourceCompilationUnit() {
		return (JavaResourceCompilationUnit) this.getRoot();
	}

}
