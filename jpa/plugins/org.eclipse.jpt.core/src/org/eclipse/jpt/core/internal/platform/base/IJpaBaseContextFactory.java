/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform.base;

import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IBaseJpaContent;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.core.internal.context.base.IMappingFileRef;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.core.internal.context.base.IPersistence;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.base.IPersistenceXml;
import org.eclipse.jpt.core.internal.context.base.IProperty;
import org.eclipse.jpt.core.internal.context.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaAttributeOverride;
import org.eclipse.jpt.core.internal.context.java.IJavaBasicMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaColumnMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaEmbeddable;
import org.eclipse.jpt.core.internal.context.java.IJavaEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaEntity;
import org.eclipse.jpt.core.internal.context.java.IJavaGeneratedValue;
import org.eclipse.jpt.core.internal.context.java.IJavaIdMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.IJavaMappedSuperclass;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaSecondaryTable;
import org.eclipse.jpt.core.internal.context.java.IJavaSequenceGenerator;
import org.eclipse.jpt.core.internal.context.java.IJavaTable;
import org.eclipse.jpt.core.internal.context.java.IJavaTableGenerator;
import org.eclipse.jpt.core.internal.context.java.IJavaTransientMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaVersionMapping;

/**
 * An IJpaFactory that also assumes a base JPA project context structure 
 * corresponding to the JPA spec:
 * 
 * 	RootContent
 * 	 |- persistence.xml
 * 	     |- persistence unit(s)
 *           |- mapping file(s)  (e.g. orm.xml)
 *           |   |- persistent type mapping(s)  (e.g. Entity)
 *           |        |- persistent attribute mapping(s)  (e.g. Basic)
 *           |- persistent type mapping(s)
 *   
 *   ... and associated objects.
 */
public interface IJpaBaseContextFactory extends IJpaFactory
{
	IPersistenceXml createPersistenceXml(IBaseJpaContent parent);
	
	IPersistence createPersistence(IPersistenceXml parent);
	
	IPersistenceUnit createPersistenceUnit(IPersistence parent);
	
	IMappingFileRef createMappingFileRef(IPersistenceUnit parent);
	
	IClassRef createClassRef(IPersistenceUnit parent);
	
	IProperty createProperty(IPersistenceUnit parent);
	
	IJavaPersistentType createJavaPersistentType(IClassRef parent);
	
	IJavaPersistentAttribute createJavaPersistentAttribute(IJavaPersistentType parent);

	IJavaTypeMapping createJavaNullTypeMapping(IJavaPersistentType parent);

	IJavaEntity createJavaEntity(IJavaPersistentType parent);
	
	IJavaMappedSuperclass createJavaMappedSuperclass(IJavaPersistentType parent);
	
	IJavaEmbeddable createJavaEmbeddable(IJavaPersistentType parent);
	
	IJavaTable createJavaTable(IJavaEntity parent);
	
	IJavaColumn createJavaColumn(IJavaColumnMapping parent, IColumn.Owner owner);

	IJavaDiscriminatorColumn createJavaDiscriminatorColumn(IJavaEntity parent, INamedColumn.Owner owner);
	
	IJavaSecondaryTable createJavaSecondaryTable(IJavaEntity parent);
	
	IJavaBasicMapping createJavaBasicMapping(IJavaPersistentAttribute parent);
	
	IJavaEmbeddedMapping createJavaEmbeddedMapping(IJavaPersistentAttribute parent);
	
	IJavaIdMapping createJavaIdMapping(IJavaPersistentAttribute parent);
	
	IJavaTransientMapping createJavaTransientMapping(IJavaPersistentAttribute parent);
	
	IJavaVersionMapping createJavaVersionMapping(IJavaPersistentAttribute parent);
	
	IJavaAttributeMapping createJavaNullAttributeMapping(IJavaPersistentAttribute parent);
	
	IJavaSequenceGenerator createJavaSequenceGenerator(IJavaJpaContextNode parent);
	
	IJavaTableGenerator createJavaTableGenerator(IJavaJpaContextNode parent);
	
	IJavaGeneratedValue createJavaGeneratedValue(IJavaAttributeMapping parent);
	
	IJavaPrimaryKeyJoinColumn createJavaPrimaryKeyJoinColumn(IJavaJpaContextNode parent, IAbstractJoinColumn.Owner owner);
	
	IJavaAttributeOverride createJavaAttributeOverride(IJavaJpaContextNode parent, IOverride.Owner owner);
	
}
