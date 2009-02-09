/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.jar;

import java.util.Iterator;

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

/**
 * Dali resource for Eclipse package fragement root JAR.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JarResourcePackageFragmentRoot
	extends JarResourceNode, JpaResourceModel
{

	/**
	 * Return the corresponding JDT package fragement root.
	 */
	IPackageFragmentRoot getPackageFragmentRoot();

	/**
	 * Return all the Java resource types that are "persistable", as defined
	 * by the JPA spec.
	 */
	Iterator<JavaResourcePersistentType> persistableTypes();

	/**
	 * Return the annotation provider that supplies the annotations the resource
	 * model uses to modify the Java source.
	 */
	JpaAnnotationProvider getAnnotationProvider();

	/**
	 * Called (via a hook in change notification) whenever anything in the JPA
	 * compilation unit changes. Forwarded to the JPA file and on to
	 * various listeners (namely the JPA project).
	 */
	void resourceModelChanged();

}
