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
package org.eclipse.jpt.core.internal.context.base;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;

public interface IPersistenceUnit extends IJpaContextNode
{
	// **************** name ***************************************************
	
	public final static String NAME_PROPERTY = "name";
	
	String getName();
	
	public void setName(String name);
	
	
	// **************** mapping file refs **************************************
	
	public final static String MAPPING_FILE_REF_LIST = "mappingFileRefs";
	
	public ListIterator<IMappingFileRef> mappingFileRefs();
	
	public void addMappingFileRef(IMappingFileRef mappingFileRef);
	
	public void addMappingFileRef(int index, IMappingFileRef mappingFileRef);
	
	public void removeMappingFileRef(IMappingFileRef mappingFileRef);
	
	public void removeMappingFileRef(int index);
	
	
	// **************** class refs *********************************************
	
	public final static String CLASS_REF_LIST = "classRefs";
	
	public ListIterator<IClassRef> classRefs();
	
	public void addClassRef(IClassRef classRef);
	
	public void addClassRef(int index, IClassRef classRef);
	
	public void removeClassRef(IClassRef classRef);
	
	public void removeClassRef(int index);
	
	
	// **************** updating ***********************************************
	
	void update(XmlPersistenceUnit persistenceUnit);
}
