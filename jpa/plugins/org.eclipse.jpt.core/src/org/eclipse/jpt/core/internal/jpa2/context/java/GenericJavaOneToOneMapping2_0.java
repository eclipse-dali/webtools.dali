/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaOneToOneMapping;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.utility.internal.ArrayTools;

/**
 *  GenericJavaOneToOneMapping2_0
 */
public class GenericJavaOneToOneMapping2_0
	extends GenericJavaOneToOneMapping
{
	
	// ********** constructor **********
	public GenericJavaOneToOneMapping2_0(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	@Override
	protected String[] buildSupportingAnnotationNames() {
		return ArrayTools.addAll(
			super.buildSupportingAnnotationNames(),
			JPA2_0.ONE_TO_ONE__ORPHAN_REMOVAL);
	}

}