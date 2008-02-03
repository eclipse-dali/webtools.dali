/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.jpt.core.internal.context.java.IJavaIdMapping;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;
import org.eclipse.jpt.core.internal.resource.orm.TemporalType;

/**
 * VirtualId is an implementation of Id used when there is 
 * no tag in the orm.xml and an underlying javaIdMapping exists.
 */
public class VirtualId extends JpaEObject implements Id
{
	IJavaIdMapping javaIdMapping;

	protected boolean metadataComplete;

	protected final VirtualColumn column;

	protected final VirtualGeneratedValue virtualGeneratedValue;
	
	protected final VirtualTableGenerator virtualTableGenerator;
	
	protected final VirtualSequenceGenerator virtualSequenceGenerator;
	

		
	public VirtualId(IJavaIdMapping javaIdMapping, boolean metadataComplete) {
		super();
		this.javaIdMapping = javaIdMapping;
		this.metadataComplete = metadataComplete;
		this.column = new VirtualColumn(javaIdMapping.getColumn(), metadataComplete);
		this.virtualGeneratedValue = new VirtualGeneratedValue(javaIdMapping.getGeneratedValue(), metadataComplete);
		this.virtualTableGenerator = new VirtualTableGenerator(javaIdMapping.getTableGenerator(), metadataComplete);
		this.virtualSequenceGenerator = new VirtualSequenceGenerator(javaIdMapping.getSequenceGenerator(), metadataComplete);
	}

	public String getName() {
		return this.javaIdMapping.persistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Column getColumn() {
		return this.column;
	}

	public void setColumn(Column value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public TemporalType getTemporal() {
		if (this.metadataComplete) {
			return null;
		}
		return org.eclipse.jpt.core.internal.context.base.TemporalType.toOrmResourceModel(this.javaIdMapping.getTemporal());
	}

	public void setTemporal(TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}


	public GeneratedValue getGeneratedValue() {
		if (this.metadataComplete) {
			return null;
		}
		if (this.javaIdMapping.getGeneratedValue() != null) {
			return this.virtualGeneratedValue;
		}
		return null;
	}
	
	public void setGeneratedValue(GeneratedValue value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");		
	}

	public SequenceGenerator getSequenceGenerator() {
		if (this.metadataComplete) {
			return null;
		}
		if (this.javaIdMapping.getSequenceGenerator() != null) {
			return this.virtualSequenceGenerator;
		}
		return null;
	}

	public void setSequenceGenerator(SequenceGenerator value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");		
	}

	public TableGenerator getTableGenerator() {
		if (this.metadataComplete) {
			return null;
		}
		if (this.javaIdMapping.getTableGenerator() != null) {
			return this.virtualTableGenerator;
		}
		return null;
	}

	public void setTableGenerator(TableGenerator value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");		
	}
	

	public void update(IJavaIdMapping javaIdMapping) {
		this.javaIdMapping = javaIdMapping;
		this.column.update(javaIdMapping.getColumn());
		this.virtualGeneratedValue.update(javaIdMapping.getGeneratedValue());
		this.virtualTableGenerator.update(javaIdMapping.getTableGenerator());
		this.virtualSequenceGenerator.update(javaIdMapping.getSequenceGenerator());
	}

}
