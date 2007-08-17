/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IJava Attribute Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getIJavaAttributeMapping()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IJavaAttributeMapping extends IAttributeMapping
{

	void updateFromJava(CompilationUnit astRoot);

	/**
	 * Refresh defaults using the given DefaultsContext
	 */
	void refreshDefaults(DefaultsContext defaultsContext);

	Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot);
}
