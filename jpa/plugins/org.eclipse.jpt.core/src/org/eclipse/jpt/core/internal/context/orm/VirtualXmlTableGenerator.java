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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.jpt.core.context.java.JavaGeneratorHolder;
import org.eclipse.jpt.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class VirtualXmlTableGenerator extends AbstractJpaEObject implements XmlTableGenerator
{
	JavaGeneratorHolder javaGeneratorHolder;

	protected boolean metadataComplete;
	
		
	public VirtualXmlTableGenerator(JavaGeneratorHolder javaGeneratorHolder, boolean metadataComplete) {
		super();
		this.javaGeneratorHolder = javaGeneratorHolder;
		this.metadataComplete = metadataComplete;
	}

	protected JavaTableGenerator getJavaTableGenerator() {
		return this.javaGeneratorHolder.getTableGenerator();
	}
	
	public String getCatalog() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaTableGenerator().getCatalog();
	}

	public void setCatalog(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getPkColumnName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaTableGenerator().getPkColumnName();
	}

	public void setPkColumnName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getPkColumnValue() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaTableGenerator().getPkColumnValue();
	}

	public void setPkColumnValue(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getSchema() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaTableGenerator().getSchema();
	}

	public void setSchema(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getTable() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaTableGenerator().getTable();
	}

	public void setTable(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getValueColumnName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaTableGenerator().getValueColumnName();
	}

	public void setValueColumnName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Integer getAllocationSize() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaTableGenerator().getAllocationSize();
	}

	public void setAllocationSize(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Integer getInitialValue() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaTableGenerator().getInitialValue();
	}

	public void setInitialValue(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaTableGenerator().getName();
	}

	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}



	public EList<XmlUniqueConstraint> getUniqueConstraints() {
		EList<XmlUniqueConstraint> xmlUniqueConstraints = new EObjectContainmentEList<XmlUniqueConstraint>(XmlUniqueConstraint.class, this, OrmPackage.XML_TABLE_GENERATOR__UNIQUE_CONSTRAINTS);

		for (JavaUniqueConstraint uniqueConstraint : CollectionTools.iterable(this.getJavaTableGenerator().uniqueConstraints())) {
			XmlUniqueConstraint xmlUniqueConstraint = new VirtualXmlUniqueConstraint(uniqueConstraint, this.metadataComplete);
			xmlUniqueConstraints.add(xmlUniqueConstraint);
		}

		return xmlUniqueConstraints;
	}
	
	public TextRange getNameTextRange() {
		return getValidationTextRange();
	}
	
	public boolean isVirtual() {
		return true;
	}
}
