/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.NamedConverterAnnotation;

/**
 * <code>
 * <ul>
 * <li>org.eclipse.persistence.annotations.Converter
 * <li>org.eclipse.persistence.annotations.StructConverter
 * <li>org.eclipse.persistence.annotations.TypeConverter
 * <li>org.eclipse.persistence.annotations.ObjectTypeConverter
 * </ul>
 * </code>
 */
abstract class EclipseLinkBinaryNamedConverterAnnotation
	extends BinaryAnnotation
	implements NamedConverterAnnotation
{
	String name;

	EclipseLinkBinaryNamedConverterAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** NamedConverterAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(this.getNameElementName());
	}

	public TextRange getNameTextRange() {
		throw new UnsupportedOperationException();
	}

	abstract String getNameElementName();
}
