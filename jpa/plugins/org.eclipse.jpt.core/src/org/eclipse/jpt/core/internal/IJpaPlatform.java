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

import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to 
 * the core JPA model.  The core JPA model will provide functionality for JPA
 * spec annotations in java, persistence.xml and (orm.xml) mapping files.
 * The org.eclipse.jpt.core.genericPlatform extension supplies 
 * resource models for those file types.  As another vendor option you 
 * will have to supply those resource models as well or different ones 
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
	
	
	// **************** Model construction / updating **************************
	
	/**
	 * Construct a JPA file for the specified file, to be added to the specified 
	 * JPA project. (Defer to the factory for actual object creation.)  
	 * Return null if unable to create the JPA file (e.g. the content type is 
	 * unrecognized).
	 */
	IJpaFile buildJpaFile(IJpaProject jpaProject, IFile file);
	
	/**
	 * Return a factory responsible for creating core (e.g. IJpaProject), resource
	 * (e.g. PersistenceResourceModel), and context (e.g. IPersistenceUnit) model
	 * objects
	 */
	IJpaFactory jpaFactory();
	
	/**
	 * Update the existing context model.
	 * This will be called when resource models change or when any project setting
	 * causes there to be a change in how resource models are combined.
	 */
	void update(IJpaProject jpaProject, IContextModel contextModel, IProgressMonitor monitor);
	
	
	
	// **************** Java annotation support ********************************
	
	/**
	 * Return an annotation provider responsible for determining what annotations
	 * are supported and constructing java resource model objects
	 */
	IJpaAnnotationProvider annotationProvider();
	
	
	// *************************************************************************
	
	/**
	 * Adds validation messages to the growing list of messages
	 */
	void addToMessages(List<IMessage> messages);
		
		
	// ********** XmlPersistence Unit ********************************************
	//	boolean containsPersistenceUnitNamed(String name);
	//	
	//	XmlPersistenceUnit persistenceUnitNamed(String name);
	//	
	//	Iterator<XmlPersistenceUnit> persistenceUnits();
	//	
	//	int persistenceUnitSize();
	// ********** Persistent Types ********************************************
	//	/**
	//	 * Return all persistent types for the persistence unit with the given name
	//	 */
	//	Iterator<IPersistentType> persistentTypes(String persistenceUnitName);
	// ************************************************************************
	//	/**
	//	 * Get the valid persistence XML files from the project
	//	 */
	//	Iterator<IJpaFile> validPersistenceXmlFiles();
	
	//	/**
	//	 * Return an Iterator of IJavaTypeMappingProviders.  These define which
	//	 * IJavaTypeMappings are supported and which annotation applies. 
	//	 */
	//	Iterator<IJavaTypeMappingProvider> javaTypeMappingProviders();
	//
	//	IJavaTypeMappingProvider javaTypeMappingProvider(String typeMappingKey);
	//
	//	/**
	//	 * Return an Iterator of IJavaAttributeMappingProviders.  These define which
	//	 * IJavaAttributeMappings are supported and which annotation applies. 
	//	 */
	//	Iterator<IJavaAttributeMappingProvider> javaAttributeMappingProviders();
	//
	//	IJavaAttributeMappingProvider javaAttributeMappingProvider(String attributeMappingKey);
	//
	//	/**
	//	 * Return a ListIterator of IDefaultJavaAttributeMappingProvider.  This is a List
	//	 * because the defaults are checked in order.
	//	 */
	//	ListIterator<IDefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders();
	//	
	//	/**
	//	 * Returns the IGeneratorRepository for the persistence unit of the
	//	 * given IPersistentType.  A NullGeneratorRepository should be returned
	//	 * if the IPersistentType is not part of a persistence unit
	//	 * @param persistentType
	//	 * @return
	//	 */
	//	IGeneratorRepository generatorRepository(IPersistentType persistentType);
}