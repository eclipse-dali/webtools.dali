/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.jpa2.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 *  OneToOne2_0Annotation
 */
public interface OneToOne2_0Annotation
	extends OneToOneAnnotation
{
	// ********** orphan removal **********
	/**
	 * Corresponds to the orphanRemoval element of the OneToMany annotation.
	 * Returns null if the orphanRemoval element does not exist in java.
	 */
	Boolean getOrphanRemoval();
	
	/**
	 * Corresponds to the orphanRemoval element of the OneToMany annotation.
	 * Set to null to remove the orphanRemoval element.
	 */
	void setOrphanRemoval(Boolean orphanRemoval);
		String ORPHAN_REMOVAL_PROPERTY = "orphanRemoval"; //$NON-NLS-1$

	/**
	 * Return the {@link TextRange} for the orphanRemoval element.  If the orphanRemoval element 
	 * does not exist return the {@link TextRange} for the OneToMany annotation.
	 */
	TextRange getOrphanRemovalTextRange(CompilationUnit astRoot);

}
