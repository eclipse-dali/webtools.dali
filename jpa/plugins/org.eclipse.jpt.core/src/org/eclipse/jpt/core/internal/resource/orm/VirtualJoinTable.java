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

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.internal.context.java.IJavaJoinTable;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;

public class VirtualJoinTable extends JpaEObject implements JoinTable
{
	
	protected IJavaJoinTable javaJoinTable;

	protected boolean metadataComplete;

	protected VirtualJoinTable(IJavaJoinTable javaJoinTable, boolean metadataComplete) {
		super();
		this.javaJoinTable = javaJoinTable;
		this.metadataComplete = metadataComplete;
	}

	public EList<JoinColumn> getInverseJoinColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	public EList<JoinColumn> getJoinColumns() {
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
	
	public void update(IJavaJoinTable javaJoinTable) {
		this.javaJoinTable = javaJoinTable;
	}

}
