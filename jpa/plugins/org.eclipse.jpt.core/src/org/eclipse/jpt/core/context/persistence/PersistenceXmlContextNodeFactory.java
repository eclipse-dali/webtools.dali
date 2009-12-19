/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import org.eclipse.jpt.core.context.java.JarFile;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;

public interface PersistenceXmlContextNodeFactory
{	
	Persistence buildPersistence(PersistenceXml parent, XmlPersistence resourcePersistence);
	
	PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit resourcePersistenceUnit);
	
	/**
	 * Build a "specified" mapping file ref.
	 */
	MappingFileRef buildMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef);

	/**
	 * Build a "implied" mapping file ref.
	 */
	MappingFileRef buildImpliedMappingFileRef(PersistenceUnit parent);

	/**
	 * Build a "specified" class ref.
	 */
	ClassRef buildClassRef(PersistenceUnit parent, XmlJavaClassRef xmlClassRef);
	
	/**
	 * Build an "implied" class ref.
	 */
	ClassRef buildClassRef(PersistenceUnit parent, String className);
	
	JarFileRef buildJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef);

	JarFile buildJarFile(JarFileRef parent, JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot);

	PersistenceUnit.Property buildProperty(PersistenceUnit parent, XmlProperty property);
	
	PersistenceUnitProperties buildConnection(PersistenceUnit parent);
	
	PersistenceUnitProperties buildOptions(PersistenceUnit parent);

}
