/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.jpa.core.context.DeleteTypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManagedType;

/**
 * Context managed type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface OrmManagedType
	extends ManagedType, DeleteTypeRefactoringParticipant, TypeRefactoringParticipant
{
	// ********** class **********

	/**
	 * Member class names will be qualified with a <code>'$'</code>
	 * between each declaring class name and member class name.
	 * <strong>NB:</strong> This may be an unqualified name to be prefixed by the
	 * entity mappings's 'package' value.
	 * 
	 * @see EntityMappings#getPackage()
	 */
	String getClass_();

	/**
	 * @see #getClass_()
	 */
	void setClass(String class_);
		String CLASS_PROPERTY = "class"; //$NON-NLS-1$


	// ********** covariant overrides **********

	EntityMappings getParent();

	XmlManagedType getXmlManagedType();

	/**
	 * Managed types are in a sequence in the orm schema. We must keep
	 * the list of managed in the appropriate order so the wtp xml 
	 * translators will write them to the xml in that order and they
	 * will adhere to the schema.
	 * <p>
	 * Each concrete implementation must implement this
	 * method and return an int that matches its order in the schema.
	 */
	int getXmlSequence();
	
	/**
	 * Add the managed type's XML managed type to the appropriate list
	 * in the specified XML entity mappings.
	 */
	void addXmlManagedTypeTo(XmlEntityMappings entityMappings);

	/**
	 * Remove the  managed type's XML managed type from the appropriate list
	 * in the specified XML entity mappings.
	 */
	void removeXmlManagedTypeFrom(XmlEntityMappings entityMappings);


	// ********** misc **********

	/**
	 * Return the Java managed type that is referred to by the
	 * <code>orm.xml</code> managed type.
	 * Return <code>null</code> if it is missing.
	 */
	JavaManagedType getJavaManagedType();
		String JAVA_MANAGED_TYPE_PROPERTY = "javaManagedType"; //$NON-NLS-1$

	/**
	 * Return the managed type's default package, as set in its entity
	 * mappings.
	 */
	String getDefaultPackage();

	//TODO would like to remove this eventually
	void dispose();
}
