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
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.UniqueConstraint;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;

public class VirtualXmlJoinTable extends AbstractJpaEObject implements XmlJoinTable
{
	
	protected JavaJoinTable javaJoinTable;

	protected boolean metadataComplete;

	protected VirtualXmlJoinTable(JavaJoinTable javaJoinTable, boolean metadataComplete) {
		super();
		this.javaJoinTable = javaJoinTable;
		this.metadataComplete = metadataComplete;
	}

	public EList<XmlJoinColumn> getInverseJoinColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	public EList<XmlJoinColumn> getJoinColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCatalog() {
		if (this.metadataComplete) {
			return this.javaJoinTable.getDefaultCatalog();
		}
		return this.javaJoinTable.getCatalog();
	}

	public void setCatalog(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public String getName() {
		if (this.metadataComplete) {
			return this.javaJoinTable.getDefaultName();
		}
		return this.javaJoinTable.getName();
	}

	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getSchema() {
		if (this.metadataComplete) {
			return this.javaJoinTable.getDefaultSchema();
		}
		return this.javaJoinTable.getSchema();
	}

	public void setSchema(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public EList<UniqueConstraint> getUniqueConstraints() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update(JavaJoinTable javaJoinTable) {
		this.javaJoinTable = javaJoinTable;
	}
	
	public TextRange nameTextRange() {
		return null;
	}
	
	public TextRange catalogTextRange() {
		return null;
	}
	
	public TextRange schemaTextRange() {
		return null;
	}

}
