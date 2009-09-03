/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.persistence;

import org.eclipse.jpt.core.internal.context.persistence.AbstractJarFileRef;
import org.eclipse.jpt.core.jpa2.context.java.JarFile2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.JarFileRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;

/**
 * JPA 2.0 context JAR file reference (from the persistence unit)
 */
public class GenericJarFileRef2_0
	extends AbstractJarFileRef
	implements JarFileRef2_0
{
	public GenericJarFileRef2_0(PersistenceUnit2_0 parent, XmlJarFileRef xmlJarFileRef) {
		super(parent, xmlJarFileRef);
	}

	@Override
	public JarFile2_0 getJarFile() {
		return (JarFile2_0) super.getJarFile();
	}

	public void synchronizeStaticMetamodel() {
		JarFile2_0 jf = this.getJarFile();
		if (jf != null) {
			jf.synchronizeStaticMetamodel();
		}
	}
}
