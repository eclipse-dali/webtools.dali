/*******************************************************************************
 * Copyright (c)2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class VirtualXmlAssociationOverride extends XmlAssociationOverride
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaAssociationOverride javaAssociationOverride;
	//TODO need to support with and without a javaAssociationoverride, the java type might not be annotated.
	

	protected VirtualXmlAssociationOverride(String name, OrmTypeMapping ormTypeMapping, JavaAssociationOverride javaAssociationOverride) {
		super();
		this.name = name;
		this.ormTypeMapping = ormTypeMapping;
		this.javaAssociationOverride = javaAssociationOverride;
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}

	@Override
	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}


	@Override
	public EList<XmlJoinColumn> getJoinColumns() {
		EList<XmlJoinColumn> joinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS);
		if (this.javaAssociationOverride == null) {
			return joinColumns; //TODO need to handle building these without the javaAssociationOverride
		}
		for (JoinColumn joinColumn : 
				CollectionTools.iterable(this.javaAssociationOverride.getRelationshipReference().getJoinColumnJoiningStrategy().joinColumns())) {
			XmlJoinColumn xmlJoinColumn = new VirtualXmlJoinColumn(joinColumn, this.isOrmMetadataComplete());
			joinColumns.add(xmlJoinColumn);
		}
		return joinColumns;
	}
}
