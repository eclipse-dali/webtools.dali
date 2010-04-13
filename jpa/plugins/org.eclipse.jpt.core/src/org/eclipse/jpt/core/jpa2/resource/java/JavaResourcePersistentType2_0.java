/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.resource.java;

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

/**
 * JPA 2.0 Java source code or binary persistent type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface JavaResourcePersistentType2_0
	extends JavaResourcePersistentType
{

	// ********** metamodel **********

	/**
	 * Return the <code>javax.annotation.Generated</code> annotation.
	 */
	GeneratedAnnotation getGeneratedAnnotation();

	/**
	 * Return whether the type is a metamodel top-level type generated in the
	 * specified source folder.
	 */
	boolean isGeneratedMetamodelTopLevelType(IPackageFragmentRoot sourceFolder);

	/**
	 * Return whether the type is a generated metamodel top-level type.
	 * The type is generated and either it or one of its nested types is a
	 * metamodel type.
	 */
	boolean isGeneratedMetamodelTopLevelType();

	/**
	 * Return whether the type is a metamodel type.
	 */
	boolean isMetamodel();

	/**
	 * The value used to tag a generated type:
	 * <pre>
	 * &#64;javax.annotation.Generated(value="Dali", date="2009-11-23T13:56:06.171-0500")
	 * </pre>
	 */
	String METAMODEL_GENERATED_ANNOTATION_VALUE = "Dali"; //$NON-NLS-1$

}
