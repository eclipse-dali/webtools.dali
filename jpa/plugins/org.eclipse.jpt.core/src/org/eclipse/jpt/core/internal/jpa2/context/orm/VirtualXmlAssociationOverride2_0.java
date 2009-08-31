/*******************************************************************************
 * Copyright (c)2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.JoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlAssociationOverride;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlJoinTable;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;

public class VirtualXmlAssociationOverride2_0 extends XmlAssociationOverride
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JoiningStrategy joiningStrategy;
	
	protected final VirtualXmlAssociationOverride virtualXmlAssociationOverride;

	protected VirtualXmlAssociationOverride2_0(String name, OrmTypeMapping ormTypeMapping, JoiningStrategy joiningStrategy) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.joiningStrategy = joiningStrategy;
		this.virtualXmlAssociationOverride = new VirtualXmlAssociationOverride(name, ormTypeMapping, joiningStrategy);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}

	@Override
	public String getName() {
		return this.virtualXmlAssociationOverride.getName();
	}
	
	@Override
	public void setName(String value) {
		this.virtualXmlAssociationOverride.setName(value);
	}

	@Override
	public EList<XmlJoinColumn> getJoinColumns() {
		return this.virtualXmlAssociationOverride.getJoinColumns();
	}
	
	@Override
	public XmlJoinTable getJoinTable() {
		if (this.joiningStrategy instanceof JoinTableJoiningStrategy) {
			JoinTable joinTable = ((JoinTableJoiningStrategy) this.joiningStrategy).getJoinTable();
			if (joinTable != null) {
				return new VirtualXmlJoinTable(
					this.ormTypeMapping, 
					joinTable);
			}
		}
		return null;
	}
	
	@Override
	public void setJoinTable(XmlJoinTable newJoinTable) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
