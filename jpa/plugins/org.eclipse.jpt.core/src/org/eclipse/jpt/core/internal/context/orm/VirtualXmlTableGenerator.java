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

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.UniqueConstraint;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;

public class VirtualXmlTableGenerator extends AbstractJpaEObject implements XmlTableGenerator
{
	JavaTableGenerator javaTableGenerator;

	protected boolean metadataComplete;
	
		
	public VirtualXmlTableGenerator(JavaTableGenerator javaTableGenerator, boolean metadataComplete) {
		super();
		this.javaTableGenerator = javaTableGenerator;
		this.metadataComplete = metadataComplete;
	}

	public String getCatalog() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaTableGenerator.getCatalog();
	}

	public void setCatalog(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getPkColumnName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaTableGenerator.getPkColumnName();
	}

	public void setPkColumnName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getPkColumnValue() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaTableGenerator.getPkColumnValue();
	}

	public void setPkColumnValue(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getSchema() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaTableGenerator.getSchema();
	}

	public void setSchema(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getTable() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaTableGenerator.getTable();
	}

	public void setTable(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getValueColumnName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaTableGenerator.getValueColumnName();
	}

	public void setValueColumnName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Integer getAllocationSize() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaTableGenerator.getAllocationSize();
	}

	public void setAllocationSize(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Integer getInitialValue() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaTableGenerator.getInitialValue();
	}

	public void setInitialValue(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaTableGenerator.getName();
	}

	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}



	public EList<UniqueConstraint> getUniqueConstraints() {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(JavaTableGenerator javaTableGenerator) {
		this.javaTableGenerator = javaTableGenerator;
	}
	
	public TextRange nameTextRange() {
		return validationTextRange();
	}
}
