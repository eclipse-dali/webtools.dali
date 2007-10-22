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

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Corresponds to the javax.persistence.UniqueConstraint annotation
 */
public interface UniqueConstraint extends JavaResource
{
	ListIterator<String> columnNames();

	void addColumnName(String columnName);
	
	void removeColumnName(String columnName);

	/**
	 * Return whether the specified postition touches the columnNames element.
	 * Return false if the columnNames element does not exist.
	 */
	boolean columnNamesTouches(int pos, CompilationUnit astRoot);

}
