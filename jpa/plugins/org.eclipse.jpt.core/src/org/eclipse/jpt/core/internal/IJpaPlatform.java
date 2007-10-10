/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.content.java.IDefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMappingProvider;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.platform.IContext;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to 
 * the core JPA model.  The core JPA model will provide functinality for JPA
 * spec annotations in java and the orm.xml mapping file.  
 * The org.eclipse.jpt.core.genericPlatform extension supplies 
 * IJpaFileContentProvider for those file types.  As another vendor option you 
 * will have to supply those IJpaFileContentProviders as well or different ones 
 * as necessary.
 * 
 * See the org.eclipse.jpt.core.jpaPlatform extension point
 */
public interface IJpaPlatform
{
	/**
	 * Get the ID for this platform
	 */
	String getId();

	/**
	 * Set the ID for this platform
	 * 
	 * *************
	 * * IMPORTANT *  For INTERNAL use only!!
	 * *************
	 */
	void setId(String theId);

	/**
	 * Get the IJpaProject for this platform
	 */
	IJpaProject getProject();

	/**
	 * Set the IJpaProject on this platform
	 */
	void setProject(IJpaProject jpaProject);

	IJpaFactory getJpaFactory();

	/**
	 * Construct a JPA file for the specified file, to be
	 * added to the specified JPA project. Return null if unable to create
	 * the JPA file (e.g. the content type is unrecognized).
	 */
	IJpaFile createJpaFile(IJpaProject jpaProject, IFile file);

	// ********** Persistence Unit ********************************************
	boolean containsPersistenceUnitNamed(String name);

	PersistenceUnit persistenceUnitNamed(String name);

	Iterator<PersistenceUnit> persistenceUnits();

	int persistenceUnitSize();

	// ********** Persistent Types ********************************************
	/**
	 * Return all persistent types for the persistence unit with the given name
	 */
	Iterator<IPersistentType> persistentTypes(String persistenceUnitName);

	// ************************************************************************
	/**
	 * Get the valid persistence XML files from the project
	 */
	Iterator<IJpaFile> validPersistenceXmlFiles();

	/**
	 * Return an Iterator of IJpaFileContentProviders.  These will be used to 
	 * determine which files will be read from an IProject based on contentType.
	 * These contentProviders should have unique contentTypes. 
	 */
	Iterator<IJpaFileContentProvider> jpaFileContentProviders();

	IJpaFileContentProvider fileContentProvider(String contentTypeId);

	/**
	 * Return an Iterator of IJavaTypeMappingProviders.  These define which
	 * IJavaTypeMappings are supported and which annotation applies. 
	 */
	Iterator<IJavaTypeMappingProvider> javaTypeMappingProviders();

	IJavaTypeMappingProvider javaTypeMappingProvider(String typeMappingKey);

	/**
	 * Return an Iterator of IJavaAttributeMappingProviders.  These define which
	 * IJavaAttributeMappings are supported and which annotation applies. 
	 */
	Iterator<IJavaAttributeMappingProvider> javaAttributeMappingProviders();

	IJavaAttributeMappingProvider javaAttributeMappingProvider(String attributeMappingKey);

	/**
	 * Return a ListIterator of IDefaultJavaAttributeMappingProvider.  This is a List
	 * because the defaults are checked in order.
	 */
	ListIterator<IDefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders();

	/**
	 * Build a project context to be used when resynching the intra-model
	 * references and creating validation problems.
	 * The JPA model containment hierarchy is inappropriate to use as a context 
	 * for defaults because it is based on the IJpaProject containing files.  
	 * The defaults context for the jpa model is based on the persistence.xml 
	 * and the mapping files and classes it contains.
	 * 
	 * @see refreshDefaults(Object)
	 */
	IContext buildProjectContext();

	/**
	 * Build a type context to be used when resynching the intra-model
	 * references and creating validation problems.
	 * The JPA model containment hierarchy is inappropriate to use as a context 
	 * for defaults because it is based on the IJpaProject containing files.  
	 * The defaults context for the jpa model is based on the persistence.xml 
	 * and the mapping files and classes it contains.
	 * 
	 * @see refreshDefaults(Object)
	 * @return
	 */
	IContext buildJavaTypeContext(IContext parentContext, IJavaTypeMapping typeMapping);

	/**
	 * Build an attribute context to be used when resynching the intra-model
	 * references and creating validation problems.
	 * The JPA model containment hierarchy is inappropriate to use as a context 
	 * for defaults because it is based on the IJpaProject containing files.  
	 * The defaults context for the jpa model is based on the persistence.xml 
	 * and the mapping files and classes it contains.
	 * 
	 * @see refreshDefaults(Object)
	 * @return
	 */
	IContext buildJavaAttributeContext(IContext parentContext, IJavaAttributeMapping attributeMapping);

	/**
	 * Resynchronize intra-model connections given the context hierarchy the 
	 * IJpaPlatform built in buildContextHierarchy().
	 * This will be called each time an update to the jpa model occurs.  If an 
	 * update occurs while the resynch() job is in process, another resynch() 
	 * will be started upon completion.
	 * @param contextHierarchy
	 */
	void resynch(IContext contextHierarchy, IProgressMonitor monitor);

	/**
	 * Adds validation messages to the growing list of messages
	 */
	@SuppressWarnings("restriction")
	void addToMessages(List<org.eclipse.wst.validation.internal.provisional.core.IMessage> messages);

	/**
	 * Returns the IGeneratorRepository for the persistence unit of the
	 * given IPersistentType.  A NullGeneratorRepository should be returned
	 * if the IPersistentType is not part of a persistence unit
	 * @param persistentType
	 * @return
	 */
	//	IGeneratorRepository generatorRepository(IPersistentType persistentType);
}