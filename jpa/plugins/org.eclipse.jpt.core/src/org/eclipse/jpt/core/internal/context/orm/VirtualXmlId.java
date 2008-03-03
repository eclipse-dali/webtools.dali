/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;

/**
 * VirtualId is an implementation of Id used when there is 
 * no tag in the orm.xml and an underlying javaIdMapping exists.
 */
public class VirtualXmlId extends AbstractJpaEObject implements XmlId
{
	JavaIdMapping javaIdMapping;

	protected boolean metadataComplete;

	protected final VirtualXmlColumn column;

	protected final VirtualXmlGeneratedValue virtualGeneratedValue;
	
	protected final VirtualXmlTableGenerator virtualTableGenerator;
	
	protected final VirtualXmlSequenceGenerator virtualSequenceGenerator;
	

		
	public VirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping, boolean metadataComplete) {
		super();
		this.javaIdMapping = javaIdMapping;
		this.metadataComplete = metadataComplete;
		this.column = new VirtualXmlColumn(ormTypeMapping, javaIdMapping.getColumn(), metadataComplete);
		this.virtualGeneratedValue = new VirtualXmlGeneratedValue(javaIdMapping.getGeneratedValue(), metadataComplete);
		this.virtualTableGenerator = new VirtualXmlTableGenerator(javaIdMapping.getTableGenerator(), metadataComplete);
		this.virtualSequenceGenerator = new VirtualXmlSequenceGenerator(javaIdMapping.getSequenceGenerator(), metadataComplete);
	}

	public String getName() {
		return this.javaIdMapping.persistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public XmlColumn getColumn() {
		return this.column;
	}

	public void setColumn(XmlColumn value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public TemporalType getTemporal() {
		if (this.metadataComplete) {
			return null;
		}
		return org.eclipse.jpt.core.context.TemporalType.toOrmResourceModel(this.javaIdMapping.getTemporal());
	}

	public void setTemporal(TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}


	public XmlGeneratedValue getGeneratedValue() {
		if (this.metadataComplete) {
			return null;
		}
		if (this.javaIdMapping.getGeneratedValue() != null) {
			return this.virtualGeneratedValue;
		}
		return null;
	}
	
	public void setGeneratedValue(XmlGeneratedValue value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");		
	}

	public XmlSequenceGenerator getSequenceGenerator() {
		if (this.metadataComplete) {
			return null;
		}
		if (this.javaIdMapping.getSequenceGenerator() != null) {
			return this.virtualSequenceGenerator;
		}
		return null;
	}

	public void setSequenceGenerator(XmlSequenceGenerator value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");		
	}

	public XmlTableGenerator getTableGenerator() {
		if (this.metadataComplete) {
			return null;
		}
		if (this.javaIdMapping.getTableGenerator() != null) {
			return this.virtualTableGenerator;
		}
		return null;
	}

	public void setTableGenerator(XmlTableGenerator value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");		
	}
	

	public void update(JavaIdMapping javaIdMapping) {
		this.javaIdMapping = javaIdMapping;
		this.column.update(javaIdMapping.getColumn());
		this.virtualGeneratedValue.update(javaIdMapping.getGeneratedValue());
		this.virtualTableGenerator.update(javaIdMapping.getTableGenerator());
		this.virtualSequenceGenerator.update(javaIdMapping.getSequenceGenerator());
	}
	
	public TextRange nameTextRange() {
		return null;
	}

}
