/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.persistence;

import java.util.Iterator;

import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.connection.GenericConnection2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.options.GenericOptions2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.ClassRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.JarFileRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.MappingFileRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.Persistence2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;

/**
 * JPA 2.0 persistence-unit
 */
public class GenericPersistenceUnit2_0
	extends AbstractPersistenceUnit
	implements PersistenceUnit2_0
{
	private JpaConnection2_0 connection;
	private JpaOptions2_0 options;

	// ********** constructors **********
	public GenericPersistenceUnit2_0(Persistence2_0 parent, XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent, xmlPersistenceUnit);
	}

	// ********** properties **********
	@Override
	protected void initializeProperties() {
		super.initializeProperties();
		this.connection = new GenericConnection2_0(this);
		this.options = new GenericOptions2_0(this);
	}
	
	@Override
	public void propertyValueChanged(String propertyName, String newValue) {
		super.propertyValueChanged(propertyName, newValue);
		this.connection.propertyValueChanged(propertyName, newValue);
		this.options.propertyValueChanged(propertyName, newValue);
	}
	
	@Override
	public void propertyRemoved(String propertyName) {
		super.propertyRemoved(propertyName);
		this.connection.propertyRemoved(propertyName);
		this.options.propertyRemoved(propertyName);
	}

	public JpaConnection2_0 getConnection() {
		return this.connection;
	}

	public JpaOptions2_0 getOptions() {
		return this.options;
	}


	// ********** JPA 2.0 Static Metamodel **********

	public void synchronizeStaticMetaModel() {
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			((MappingFileRef2_0) stream.next()).synchronizeStaticMetaModel();
		}
		for (Iterator<ClassRef> stream = this.classRefs(); stream.hasNext(); ) {
			((ClassRef2_0) stream.next()).synchronizeStaticMetaModel();
		}
		for (Iterator<JarFileRef> stream = this.jarFileRefs(); stream.hasNext(); ) {
			((JarFileRef2_0) stream.next()).synchronizeStaticMetaModel();
		}
	}

}
