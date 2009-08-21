/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJarFile;
import org.eclipse.jpt.core.jpa2.context.java.JarFile2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentType2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.JarFileRef2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;

public class GenericJarFile2_0
	extends AbstractJarFile
	implements JarFile2_0
{
	public GenericJarFile2_0(JarFileRef2_0 parent, JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot) {
		super(parent, jarResourcePackageFragmentRoot);
	}

	public void synchronizeStaticMetaModel() {
		for (JavaPersistentType jpt : this.getJavaPersistentTypes()) {
			((JavaPersistentType2_0) jpt).synchronizeStaticMetaModel();
		}
	}
}
