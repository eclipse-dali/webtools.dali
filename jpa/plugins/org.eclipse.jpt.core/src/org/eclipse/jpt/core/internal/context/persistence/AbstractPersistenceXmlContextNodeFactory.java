/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.java.JarFile;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.context.persistence.PersistenceXmlContextNodeFactory;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJarFile;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericClassRef;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericJarFileRef;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericMappingFileRef;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericPersistence;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericPersistenceUnit;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericPersistenceUnitProperty;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.ImpliedMappingFileRef;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;

public abstract class AbstractPersistenceXmlContextNodeFactory implements PersistenceXmlContextNodeFactory
{
	
	public Persistence buildPersistence(PersistenceXml parent, XmlPersistence xmlPersistence) {
		return new GenericPersistence(parent, xmlPersistence);
	}
	
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		return new GenericPersistenceUnit(parent, xmlPersistenceUnit);
	}
	
	public MappingFileRef buildMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef) {
		return new GenericMappingFileRef(parent, xmlMappingFileRef);
	}
	
	public MappingFileRef buildImpliedMappingFileRef(PersistenceUnit parent) {
		return new ImpliedMappingFileRef(parent, JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH );
	}
	
	public ClassRef buildClassRef(PersistenceUnit parent, XmlJavaClassRef classRef) {
		return new GenericClassRef(parent, classRef);
	}
	
	public ClassRef buildClassRef(PersistenceUnit parent, String className) {
		return new GenericClassRef(parent, className);
	}
	
	public JarFileRef buildJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		return new GenericJarFileRef(parent, xmlJarFileRef);
	}

	public JarFile buildJarFile(JarFileRef parent, JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot) {
		return new GenericJarFile(parent, jarResourcePackageFragmentRoot);
	}
	
	public PersistenceUnit.Property buildProperty(PersistenceUnit parent, XmlProperty xmlProperty) {
		return new GenericPersistenceUnitProperty(parent, xmlProperty);
	}

}
