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
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class VirtualXmlUniqueConstraint extends AbstractJpaEObject implements XmlUniqueConstraint
{	
	protected JavaUniqueConstraint javaUniqueConstraint;

	protected boolean metadataComplete;

	protected VirtualXmlUniqueConstraint(JavaUniqueConstraint javaUniqueConstraint, boolean metadataComplete) {
		super();
		this.javaUniqueConstraint = javaUniqueConstraint;
		this.metadataComplete = metadataComplete;
	}

	public EList<String> getColumnNames() {
		EList<String> columnNames = new EDataTypeEList<String>(String.class, this, OrmPackage.XML_UNIQUE_CONSTRAINT_IMPL__COLUMN_NAMES);

		for (String columnName : CollectionTools.iterable(this.javaUniqueConstraint.columnNames())) {
			columnNames.add(columnName);
		}
		return columnNames;
	}
	
}
