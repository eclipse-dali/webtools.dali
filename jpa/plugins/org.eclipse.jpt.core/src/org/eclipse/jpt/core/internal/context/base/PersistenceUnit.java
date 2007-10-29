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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


public class PersistenceUnit extends JpaContextNode
	implements IPersistenceUnit
{
	protected String name;
	
	protected final List<IMappingFileRef> mappingFileRefs;
	
	protected final List<IClassRef> classRefs;
	
	
	public PersistenceUnit(IPersistence parent) {
		super(parent);
		mappingFileRefs = new ArrayList<IMappingFileRef>();
		classRefs = new ArrayList<IClassRef>();
	}
	
	public IPersistentType persistentType(String fullyQualifiedTypeName) {
		for (IClassRef classRef : CollectionTools.iterable(classRefs())) {
			if (classRef.isFor(fullyQualifiedTypeName)) {
				return classRef.getJavaPersistentType();
			}
		}
		//TODO check mappingFileRefs (probably check these first??)
		return null;
	}
	
	@Override
	public IPersistenceUnit persistenceUnit() {
		return this;
	}

	// **************** name ***************************************************
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		if (! StringTools.stringsAreEqualIgnoreCase(name, newName)) {
			String oldName = name;
			name = newName;
			firePropertyChanged(NAME_PROPERTY, oldName, newName);
		}
	}
	
	
	// **************** mapping file refs **************************************
	
	public ListIterator<IMappingFileRef> mappingFileRefs() {
		return new CloneListIterator<IMappingFileRef>(mappingFileRefs);
	}
	
	public void addMappingFileRef(IMappingFileRef mappingFileRef) {
		mappingFileRefs.add(mappingFileRef);
		fireListChanged(MAPPING_FILE_REF_LIST);
	}
	
	public void addMappingFileRef(int index, IMappingFileRef mappingFileRef) {
		mappingFileRefs.add(index, mappingFileRef);
		fireListChanged(MAPPING_FILE_REF_LIST);
	}
	
	public void removeMappingFileRef(IMappingFileRef mappingFileRef) {
		mappingFileRefs.remove(mappingFileRef);
		fireListChanged(MAPPING_FILE_REF_LIST);
	}
	
	public void removeMappingFileRef(int index) {
		mappingFileRefs.remove(index);
		fireListChanged(MAPPING_FILE_REF_LIST);
	}
	
	// **************** class refs *********************************************
	
	public ListIterator<IClassRef> classRefs() {
		return new CloneListIterator<IClassRef>(classRefs);
	}
	
	public void addClassRef(IClassRef classRef) {
		classRefs.add(classRef);
		fireListChanged(CLASS_REF_LIST);
	}
	
	public void addClassRef(int index, IClassRef classRef) {
		classRefs.add(index, classRef);
		fireListChanged(CLASS_REF_LIST);
	}
	
	public void removeClassRef(IClassRef classRef) {
		classRefs.remove(classRef);
		fireListChanged(CLASS_REF_LIST);
	}
	
	public void removeClassRef(int index) {
		classRefs.remove(index);
		fireListChanged(CLASS_REF_LIST);
	}
	
	
	// **************** updating ***********************************************
	
	public void update(XmlPersistenceUnit persistenceUnit) {
		updateName(persistenceUnit);
		updateMappingFileRefs(persistenceUnit);
		updateClassRefs(persistenceUnit);
	}
	
	public void updateName(XmlPersistenceUnit persistenceUnit) {
		setName(persistenceUnit.getName());
	}
	
	public void updateMappingFileRefs(XmlPersistenceUnit persistenceUnit) {
		Iterator<IMappingFileRef> stream = mappingFileRefs();
		Iterator<XmlMappingFileRef> stream2 = persistenceUnit.getMappingFiles().iterator();
		
		while (stream.hasNext()) {
			IMappingFileRef mappingFileRef = stream.next();
			if (stream2.hasNext()) {
				XmlMappingFileRef xmlMappingFileRef = stream2.next();
				mappingFileRef.update(xmlMappingFileRef);
			}
			else {
				removeMappingFileRef(mappingFileRef);
			}
		}
		
		while (stream2.hasNext()) {
			XmlMappingFileRef xmlMappingFileRef = stream2.next();
			IMappingFileRef mappingFileRef = jpaFactory().createMappingFileRef(this);
			addMappingFileRef(mappingFileRef);
			mappingFileRef.update(xmlMappingFileRef);
		}
	}
	
	public void updateClassRefs(XmlPersistenceUnit persistenceUnit) {
		Iterator<IClassRef> stream = classRefs();
		Iterator<XmlJavaClassRef> stream2 = persistenceUnit.getClasses().iterator();
		
		while (stream.hasNext()) {
			IClassRef classRef = stream.next();
			if (stream2.hasNext()) {
				XmlJavaClassRef xmlClassRef = stream2.next();
				classRef.update(xmlClassRef);
			}
			else {
				removeClassRef(classRef);
			}
		}
		
		while (stream2.hasNext()) {
			XmlJavaClassRef xmlClassRef = stream2.next();
			IClassRef classRef = jpaFactory().createClassRef(this);
			addClassRef(classRef);
			classRef.update(xmlClassRef);
		}
	}
}
