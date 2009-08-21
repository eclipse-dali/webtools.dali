/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.internal.context.java.AbstractJarFile;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;

/**
 * Context JAR file
 */
public class GenericJarFile
	extends AbstractJarFile
{
	public GenericJarFile(JarFileRef parent, JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot) {
		super(parent, jarResourcePackageFragmentRoot);
	}
}
