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

import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualVersion is an implementation of Version used when there is 
 * no tag in the orm.xml and an underlying javaVersionMapping exists.
 */
public class VirtualXmlVersion extends VirtualXmlAttributeMapping<JavaVersionMapping> implements XmlVersion
{

	protected final VirtualXmlColumn column;
	
	public VirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		super(ormTypeMapping, javaVersionMapping);
		this.column = new VirtualXmlColumn(ormTypeMapping, javaVersionMapping.getColumn());
	}

	public String getName() {
		return this.javaAttributeMapping.getPersistentAttribute().getName();
	}

	public void setName(@SuppressWarnings("unused") String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public XmlColumn getColumn() {
		return this.column;
	}

	public void setColumn(@SuppressWarnings("unused") XmlColumn value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public TemporalType getTemporal() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getConverter().getType() == Converter.TEMPORAL_CONVERTER) {
			org.eclipse.jpt.core.context.TemporalType javaTemporalType = ((TemporalConverter) this.javaAttributeMapping.getConverter()).getTemporalType();
			return  org.eclipse.jpt.core.context.TemporalType.toOrmResourceModel(javaTemporalType);
		}
		return null;
	}

	public void setTemporal(@SuppressWarnings("unused") TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	//see eclipselink bug 247078 for info one why I made the interface XmlConvertibleMapping
	public EnumType getEnumerated() {
		throw new UnsupportedOperationException("enumerated not supported on version mappings"); //$NON-NLS-1$
	}
	
	public void setEnumerated(@SuppressWarnings("unused") EnumType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	//see eclipselink bug 247078 for info one why I made the interface XmlConvertibleMapping
	public boolean isLob() {
		throw new UnsupportedOperationException("lob not supported on version mappings"); //$NON-NLS-1$
	}
	
	public void setLob(@SuppressWarnings("unused") boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}	
	
	public TextRange getNameTextRange() {
		return null;
	}
	
	public TextRange getEnumeratedTextRange() {
		return null;
	}
	
	public TextRange getLobTextRange() {
		return null;
	}
	
	public TextRange getTemporalTextRange() {
		return null;
	}

}
