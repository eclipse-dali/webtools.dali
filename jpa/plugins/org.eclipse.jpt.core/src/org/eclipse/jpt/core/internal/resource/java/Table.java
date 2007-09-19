/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;

public interface Table extends TypeAnnotation
{
	String getName();
	
	void setName(String name);
	
	String getCatalog();
	
	void setCatalog(String catalog);

	String getSchema();
	
	void setSchema(String schema);
	
	void updateFromJava(CompilationUnit astRoot);

}
